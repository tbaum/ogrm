package org.ogrm.internal.context;

import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Map;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.PropertyContainer;
import org.neo4j.graphdb.Relationship;
import org.ogrm.config.Configuration;
import org.ogrm.internal.proxy.ContainerWrapper;
import org.ogrm.internal.proxy.PropertyContainerProxyHandler;
import org.ogrm.internal.wrap.NodeTypeWrapper;
import org.ogrm.internal.wrap.RelationshipTypeWrapper;
import org.ogrm.internal.wrap.TypeWrapper;
import org.ogrm.util.Lists;
import org.ogrm.util.ReflectionHelper;

import com.bsc.commons.collections.Maps;
import com.bsc.commons.functors.transformer.Transformer;

public class PersistenceContextImpl implements PersistenceContext {

	private static final String PROPERTYNAME_TYPE = "_class";

	private Map<String, List<Class<?>>> interfacesCache;
	private Map<String, Class<?>> classCache;
	private Map<String, TypeWrapper<Node>> nodeWrapperCache;
	private Map<String, TypeWrapper<Relationship>> relWrapperCache;

	private Configuration config;

	public PersistenceContextImpl(Configuration configuration) {
		this.config = configuration;

		classCache = createClassCache( config );

		interfacesCache = createInterfacesCache( classCache );

		nodeWrapperCache = createNodeWrapperCache( classCache );

		relWrapperCache = createRelWrapperCache( classCache );
	}

	@Override
	public Configuration getConfiguration() {
		return config;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T createNode( Class<T> type ) {
		Node node = config.getGraph().createNode();
		setMetaDataOnContainer( node, type );
		return (T) getEntity( node );
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T createTypedRelationship( Relationship rel, Class<T> classType ) {
		setMetaDataOnContainer( rel, classType );
		return (T) getTypedRelation( rel );
	}

	@Override
	public Object getEntity( Node node ) {
		Object instance = newInstance( node );
		TypeWrapper<Node> wrapper = nodeWrapperCache.get( instance.getClass().getName() );
		Object proxy = createProxy( node, wrapper, instance );
		return proxy;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Node getNode( Object entity ) {
		return ((ContainerWrapper<Node>) entity).getContainer();
	}

	@Override
	public Object getTypedRelation( Relationship relationship ) {
		Object instance = newInstance( relationship );
		TypeWrapper<Relationship> wrapper = relWrapperCache.get( instance.getClass().getName() );
		Object proxy = createProxy( relationship, wrapper, instance );
		return proxy;
	}

	private Map<String, Class<?>> createClassCache( final Configuration conf ) {
		return Maps.lazyMap( Maps.<String, Class<?>> newMap(), new Transformer<String, Class<?>>() {

			@Override
			public Class<?> transform( String input ) {
				return conf.getResourceLoader().classForName( input );
			}
		} );
	}

	private Map<String, List<Class<?>>> createInterfacesCache( final Map<String, Class<?>> classCache ) {
		return Maps.lazyMap( Maps.<String, List<Class<?>>> newMap(), new Transformer<String, List<Class<?>>>() {

			@Override
			public List<Class<?>> transform( String input ) {
				List<Class<?>> list = Lists.newList( ReflectionHelper.getInterfacesFor( classCache.get( input ) ) );
				list.add( ContainerWrapper.class );
				return list;
			}
		} );
	}

	private Map<String, TypeWrapper<Node>> createNodeWrapperCache( final Map<String, Class<?>> classCache ) {

		return Maps.<String, TypeWrapper<Node>> lazyMap( Maps.<String, TypeWrapper<Node>> newMap(),
				new Transformer<String, TypeWrapper<Node>>() {

					@Override
					public TypeWrapper<Node> transform( String input ) {
						return createNodeWrapper( input, classCache );
					}
				} );
	}

	private Map<String, TypeWrapper<Relationship>> createRelWrapperCache( final Map<String, Class<?>> classCache ) {
		return Maps.<String, TypeWrapper<Relationship>> lazyMap( Maps.<String, TypeWrapper<Relationship>> newMap(),
				new Transformer<String, TypeWrapper<Relationship>>() {

					@Override
					public TypeWrapper<Relationship> transform( String input ) {
						return createRelationshipWrapper( input, classCache );
					}
				} );
	}

	private void setMetaDataOnContainer( PropertyContainer container, Class<?> type ) {
		container.setProperty( PROPERTYNAME_TYPE, type.getName() );
	}

	private Object newInstance( PropertyContainer container ) {
		String typeName = (String) container.getProperty( PROPERTYNAME_TYPE );

		Class<?> type = classCache.get( typeName );

		Object instance = ReflectionHelper.newInstance( type );

		return instance;
	}

	private <T extends PropertyContainer> Object createProxy( T container, TypeWrapper<T> wrapper, Object instance ) {

		wrapper.onLoad( instance, container );

		PropertyContainerProxyHandler<T> handler = new PropertyContainerProxyHandler<T>( container, wrapper, instance );

		List<Class<?>> interfaces = Lists.newList( interfacesCache.get( instance.getClass().getName() ) );

		Object proxy = Proxy.newProxyInstance( config.getResourceLoader().getClassLoader(), interfaces
				.toArray( new Class<?>[0] ), handler );

		return proxy;
	}

	private TypeWrapper<Node> createNodeWrapper( String typeName, Map<String, Class<?>> classCache ) {
		return new NodeTypeWrapper( classCache.get( typeName ), this );
	}
	
	private TypeWrapper<Relationship> createRelationshipWrapper( String typeName, Map<String, Class<?>> classCache ) {
		return new RelationshipTypeWrapper( classCache.get( typeName ), this );
	}
}
