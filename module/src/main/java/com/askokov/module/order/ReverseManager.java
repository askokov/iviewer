package com.askokov.module.order;

/**
 * Show items in reverse mode
 */
public class ReverseManager implements OrderManager {

    private int size;

    public ReverseManager(final int size) {
        this.size = size;
    }

    @Override
    public int get(int current) {
        return size - current - 1;
    }
}
