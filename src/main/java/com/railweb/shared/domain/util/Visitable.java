package com.railweb.shared.domain.util;

public interface Visitable<V extends Visitor> {

 void accept(V visitor);

}
