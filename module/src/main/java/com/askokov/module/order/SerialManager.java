package com.askokov.module.order;

/**
 * Show items in serial mode
 */
public class SerialManager implements OrderManager {

    @Override
    public int get(int current) {
        return current;
    }
}
