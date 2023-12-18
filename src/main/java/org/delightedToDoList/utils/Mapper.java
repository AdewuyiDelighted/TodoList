package org.delightedToDoList.utils;

import org.delightedToDoList.data.model.TodoList;
import org.delightedToDoList.dtos.request.RegisterRequest;

public class Mapper {

    public static TodoList map(RegisterRequest registerRequest){
        TodoList todoList = new TodoList();
        todoList.setUsername(registerRequest.getUsername());
        todoList.setPassword(registerRequest.getPassword());
        return todoList;
    }
}
