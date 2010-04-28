package org.ogrm.internal.proxy;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import org.ogrm.util.ReflectionHelper;

public class FieldProperty implements Property {

	private Field field;

	public FieldProperty(Field field) {
		this.field = field;
	}

	@Override
	public Object get( Object source ) {
		return ReflectionHelper.getAndWrap( field, source );
	}

	@Override
	public String getName() {
		return field.getName();
	}

	@Override
	public void set( Object target, Object value ) {
		ReflectionHelper.setAndWrap( field, target, value );
	}

	@Override
	public Class<?> getType() {
		return field.getType();
	}

	@Override
	public <T extends Annotation> T getAnnotation( Class<T> annotationClass ) {
		return field.getAnnotation( annotationClass );
	}

	@Override
	public Annotation[] getAnnotations() {
		return field.getAnnotations();
	}

	@Override
	public Annotation[] getDeclaredAnnotations() {
		return field.getDeclaredAnnotations();
	}

	@Override
	public boolean isAnnotationPresent( Class<? extends Annotation> annotationClass ) {
		return field.isAnnotationPresent( annotationClass );
	}

	@Override
	public String toString() {
		return "Field[" + getName() + "]";
	}
}
