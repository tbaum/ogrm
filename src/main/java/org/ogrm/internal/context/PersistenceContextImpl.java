package org.ogrm.internal.context;

import java.util.List;
import java.util.Map;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.PropertyContainer;
import org.neo4j.graphdb.Relationship;
import org.ogrm.config.Configuration;
import org.ogrm.internal.proxy.CglibProxyFactory;
import org.ogrm.internal.proxy.ContainerWrapper;
import org.ogrm.internal.proxy.ProxyFactory;
import org.ogrm.internal.wrap.NodeTypeWrapper;
import org.ogrm.internal.wrap.RelationshipTypeWrapper;
import org.ogrm.internal.wrap.TypeWrapper;
import org.ogrm.util.Lists;
import org.ogrm.util.Maps;
import org.ogrm.util.ReflectionHelper;
import org.ogrm.util.Transformer;

public class PersistenceContextImpl implements PersistenceContext {

	private static final String PROPERTYNAME_TYPE = "_class";

	private Map<String, List<Class<?>>> interfacesCache;
	private Map<String, Class<?>> classCache;
	private Map<String, TypeWrapper<Node>> nodeWrapperCache;
	private Map<String, TypeWrapper<Relationship>> relWrapperCache;
	private ProxyFactory proxyFactory;
	private Configuration config;

	public PersistenceContextImpl(Configuration configuration) {
		this.config = configuration;

		classCache = createClassCache( config );

		interfacesCache = createInterfacesCache( classCache );

		nodeWrapperCache = createNodeWrapperCache( classCache );

		relWrapperCache = createRelWrapperCache( classCache );
		
		proxyFactory = new CglibProxyFactory( config );
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
		Class<?> instanceType = getClassOf( node );
		TypeWrapper<Node> wrapper = nodeWrapperCache.get( instanceType.getName() );
		Object proxy = proxyFactory.createProxy( node, wrapper, instanceType );
		return proxy;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Node getNode( Object entity ) {
		return ((ContainerWrapper<Node>) entity).getContainer();
	}

	@Override
	public Object getTypedRelation( Relationship relationship ) {
		Class<?> instanceType = getClassOf( relationship );
		TypeWrapper<Relationship> wrapper = relWrapperCache.get( instanceType.getName() );
		Object proxy = proxyFactory.createProxy( relationship, wrapper, instanceType );
		return proxy;
	}

	private Map<String, Class<?>> createClassCache( final Configuration conf ) {
		return Maps.asLazy( Maps.<String, Class<?>> newMap(), new Transformer<String, Class<?>>() {

			@Override
			public Class<?> transform( String input ) {
				return conf.getResourceLoader().classForName( input );
			}
		} );
	}

	private Map<String, List<Class<?>>> createInterfacesCache( final Map<String, Class<?>> classCache ) {
		return Maps.asLazy( Maps.<String, List<Class<?>>> newMap(), new Transformer<String, List<Class<?>>>() {

			@Override
			public List<Class<?>> transform( String input ) {
				List<Class<?>> list = Lists.newList( ReflectionHelper.getInterfacesFor( classCache.get( input ) ) );
				list.add( ContainerWrapper.class );
				return list;
			}
		} );
	}

	private Map<String, TypeWrapper<Node>> createNodeWrapperCache( final Map<String, Class<?>> classCache ) {

		return Maps.<String, TypeWrapper<Node>> asLazy( Maps.<String, TypeWrapper<Node>> newMap(),
				new Transformer<String, TypeWrapper<Node>>() {

					@Override
					public TypeWrapper<Node> transform( String input ) {
						return createNodeWrapper( input, classCache );
					}
				} );
	}

	private Map<String, TypeWrapper<Relationship>> createRelWrapperCache( final Map<String, Class<?>> classCache ) {
		return Maps.<String, TypeWrapper<Relationship>> asLazy( Maps.<String, TypeWrapper<Relationship>> newMap(),
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

	private Class<?> getClassOf( PropertyContainer container ) {
		String typeName = (String) container.getProperty( PROPERTYNAME_TYPE );

		Class<?> type = classCache.get( typeName );

		return type;
	}

	private TypeWrapper<Node> createNodeWrapper( String typeName, Map<String, Class<?>> classCache ) {
		return new NodeTypeWrapper( classCache.get( typeName ), this );
	}

	private TypeWrapper<Relationship> createRelationshipWrapper( String typeName, Map<String, Class<?>> classCache ) {
		return new RelationshipTypeWrapper( classCache.get( typeName ), this );
	}
}
