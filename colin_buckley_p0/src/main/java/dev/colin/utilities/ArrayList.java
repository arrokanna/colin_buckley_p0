package dev.colin.utilities;

import java.util.Arrays;

public class ArrayList<T> implements list<T> {

    private Object [] elements;
    private int currentIndex;

    public ArrayList() {
        this.elements = new Object[16];
        this.currentIndex = 0;
    }

    @Override
    public void add(T element) {
        if (this.currentIndex >= this.elements.length) {
            this.elements = Arrays.copyOf(this.elements, (this.elements.length + 1) * 2);
        }
        this.elements[this.currentIndex] = element;
        this.currentIndex++;

    }

    @Override
    public T get(int index) {
        return (T) this.elements[index];
    }

    @Override
    public int size() {
        return this.currentIndex;
    }
}
