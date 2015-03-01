package com.askokov.module.order;

/**
 * Interface for order manager implementation
 */
public interface OrderManager {

    /**
     * Retrieve new current position
     *
     * @param current current position
     * @return new position
     */
    int get(int current);

}
