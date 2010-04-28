package org.ogrm.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class ReflectionHelper {

	@SuppressWarnings("unchecked")
	public static <T> T newInstance( Class<T> c ) {
		try {
			Constructor<?>[] cons = c.getDeclaredConstructors();
			// Change the accessible property of the constructor.
			cons[0].setAccessible( true );
			return (T) cons[0].newInstance();
		} catch (Exception e) {
			throw new RuntimeException( "Could not instantiate " + c.getName() + ", it must have a no-arg contructor",
					e );
		}
	}

	public static List<Field> getDeclaredFields( Class<?> c ) {
		List<Field> fields = new ArrayList<Field>();
		for (Field field : c.getDeclaredFields())
			fields.add( field );
		Class<?> cls = c;
		while (cls.getSuperclass() != Object.class && cls.getSuperclass() != null) {
			cls = cls.getSuperclass();
			for (Field field : cls.getDeclaredFields())
				fields.add( field );
		}
		return fields;
	}

	public static List<Method> getPublicMethods( Class<?> c ) {
		List<Method> list = new ArrayList<Method>();

		for (Method method : c.getMethods())
			list.add( method );

		return list;
	}

	public static Method getMethod( Class<?> c, String name ) {
		for (Method method : getPublicMethods( c )) {
			if (method.getName().equals( name ))
				return method;
		}
		return null;
	}

	public static Object invoke( Method method, Object target, Object... args ) throws Exception {
		try {
			return method.invoke( target, args );
		} catch (IllegalArgumentException iae) {
			String message = "Could not invoke method by reflection: " + toString( method );
			if (args != null && args.length > 0) {
				message += " with parameters: (" + args + ')';
			}
			message += " on: " + target.getClass().getName();
			throw new IllegalArgumentException( message, iae );
		} catch (InvocationTargetException ite) {
			if (ite.getCause() instanceof Exception) {
				throw (Exception) ite.getCause();
			} else {
				throw ite;
			}
		}
	}

	public static Object get( Field field, Object target ) throws Exception {
		try {
			field.setAccessible( true );
			return field.get( target );
		} catch (IllegalArgumentException iae) {
			String message = "Could not get field value by reflection: " + toString( field );
			throw new IllegalArgumentException( message, iae );
		}
	}

	public static void set( Field field, Object target, Object value ) throws Exception {
		try {
			field.setAccessible( true );
			field.set( target, value );
		} catch (IllegalAccessException iae) {
			// target may be null if field is static so use
			// field.getDeclaringClass() instead
			String message = "Could not set field value by reflection: " + toString( field );
			if (value == null) {
				message += " with null value";
			} else {
				message += " with value: " + value.getClass();
			}
			throw new IllegalArgumentException( message, iae );
		}
	}

	public static Object getAndWrap( Field field, Object target ) {
		try {
			return get( field, target );
		} catch (Exception e) {
			if (e instanceof RuntimeException) {
				throw (RuntimeException) e;
			} else {
				throw new IllegalArgumentException( "exception setting: " + field.getName(), e );
			}
		}
	}

	public static void setAndWrap( Field field, Object target, Object value ) {
		if (field == null || target == null)
			return;

		try {
			set( field, target, value );
		} catch (Exception e) {
			if (e instanceof RuntimeException) {
				throw (RuntimeException) e;
			} else {
				throw new IllegalArgumentException( "exception setting: " + field.getName(), e );
			}
		}
	}

	public static Object invokeAndWrap( Method method, Object target, Object... args ) {
		try {
			return invoke( method, target, args );
		} catch (Exception e) {
			if (e instanceof RuntimeException) {
				throw (RuntimeException) e;
			} else {
				throw new RuntimeException( "exception invoking: " + method.getName(), e );
			}
		}
	}

	public static boolean isStaticField( Field field ) {
		return Modifier.isStatic( field.getModifiers() );
	}

	public static boolean isTransientField( Field field ) {
		return Modifier.isTransient( field.getModifiers() );
	}

	public static String toString( Method method ) {
		return method.getDeclaringClass().getName() + '.' + method.getName() + '('
				+ Strings.build( ", ", method.getParameterTypes() ) + ')';
	}

	public static String toString( Member member ) {
		return member.getDeclaringClass().getName() + '.' + member.getName();
	}

	public static Method findSetterFor( Method getter ) {
		String name = getter.getName();
		if (name.startsWith( "get" )) {
			String setterName = "set" + name.substring( 3 );
			try {
				return getter.getDeclaringClass().getMethod( setterName, getter.getReturnType() );
			} catch (Exception e) {
				throw new IllegalArgumentException( "Could not get setter with name " + setterName + " from type "
						+ getter.getDeclaringClass(), e );
			}
		}
		try {
			return getter.getDeclaringClass().getMethod( getter.getName(), getter.getReturnType() );
		} catch (Exception e) {
			throw new IllegalArgumentException( "Could not get setter with name " + getter.getName() + " from type "
					+ getter.getDeclaringClass(), e );
		}

	}

	public static Field findFieldWithAnnotation( Class<?> type, Class<? extends Annotation> annotationClass ) {

		for (Field field : getDeclaredFields( type )) {
			if (field.isAnnotationPresent( annotationClass ))
				return field;
		}
		return null;

	}

	public static Class<?>[] getInterfacesFor( Class<?> type ) {
		return type.getInterfaces();
	}

}
