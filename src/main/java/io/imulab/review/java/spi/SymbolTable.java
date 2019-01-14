package io.imulab.review.java.spi;

import java.util.Iterator;

public interface SymbolTable<Key extends Comparable<Key>, Value> {

    void put(Key key, Value value);

    Value get(Key key);

    void delete(Key key);

    int count();

    Iterator<Value> iterator();
}
