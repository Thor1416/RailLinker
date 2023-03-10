package com.railweb.shared.domain.base;

import java.io.Serializable;

/**
 * 
 * @author Thorbjørn Simonsen, Sando kandias
 * 
 * @param <T>
 */

public interface ValueObject<T> extends Serializable {

	boolean sameValueAs(T other);
}
