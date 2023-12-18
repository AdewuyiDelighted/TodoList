package org.delightedToDoList.service;

import org.delightedToDoList.data.model.Task;
import org.delightedToDoList.data.model.TodoList;
import org.delightedToDoList.dtos.request.LoginRequest;
import org.delightedToDoList.dtos.request.RegisterRequest;

import java.util.List;

public interface ToDoListService {

    void register(RegisterRequest registerRequest);
    TodoList login(LoginRequest loginRequest);
    TodoList findByUserName(String username);
    List<Task> findAllTaskBelongingTo(String username);
    String addTask(String description,String todolistId);
    void updateTask(String username,String description,String taskId);
    void tickOutTask(String id,String response);
    void deleteTaskById(String taskId);
    void deleteAllTask();
    void listOfTask(Task task);


}
