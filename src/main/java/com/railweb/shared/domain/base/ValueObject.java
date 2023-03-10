package com.railweb.shared.domain.base;

import java.io.Serializable;

/**
 * 
 * @author Thorbj�rn Simonsen, Sando kandias
 * 
 * @param <T>
 */

public interface ValueObject<T> extends Serializable {

	boolean sameValueAs(T other);
}
