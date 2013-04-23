package com.outline.org.app.service;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * http://stackoverflow.com/questions/4296910/is-it-possible-to-read-the-value-of-a-annotation-in-java
 * @author alessandrots
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ClasseNegocial {
	/**
	 * Para determinar em tempo de execução se a o método de uma determinada classe é do tipo negocial ou não.
	 * @return boolean
	 */
	boolean negocial();
}
