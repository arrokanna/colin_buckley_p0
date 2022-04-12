package dev.colin.utilities;

// create a list array
public interface list<T> {

    // add element
    void add(T element);

    // get value at index
    T get(int index);

    // get size of list
    int size();

}
