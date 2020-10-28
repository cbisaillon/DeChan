package com.dechan.errors.groups;

public class InvalidGroupTransactionException extends Exception {
    public InvalidGroupTransactionException(){
        super("Invalid group transaction information");
    }
}
