package org.delightedToDoList.exceptions;

public class UserDontExistException extends TodolistExceptions{
    public UserDontExistException(String message) {
        super(message);
    }
}
