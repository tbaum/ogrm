package org.ogrm.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.ogrm.internal.converter.PrimitiveConverter;
import org.ogrm.internal.converter.ValueConverter;


@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.FIELD })
public @interface Value {

	public static final String FIELD_NAME = "_fieldName";
	
 	Class<? extends ValueConverter> converter() default PrimitiveConverter.class;
	
	String value() default FIELD_NAME;
}
