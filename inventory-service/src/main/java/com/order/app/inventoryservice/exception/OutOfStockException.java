package com.order.app.inventoryservice.exception;

public class OutOfStockException extends RuntimeException{

    public OutOfStockException(String msg) {
        super(msg);
    }
}
