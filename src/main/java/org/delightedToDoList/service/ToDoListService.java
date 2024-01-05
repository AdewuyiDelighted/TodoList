package org.delightedToDoList.service;

import org.delightedToDoList.data.model.Task;
import org.delightedToDoList.data.model.TodoList;
import org.delightedToDoList.dtos.request.FindTaskRequest;
import org.delightedToDoList.dtos.request.*;

import java.util.List;

public interface ToDoListService {
    void register(RegisterRequest registerRequest);
    TodoList login(LoginRequest loginRequest);

    TodoList findByUserName(String username);

    List<Task> findAllTaskBelongingTo(String username);

    void addTask(AddTaskRequest addTaskRequest);

    void updateTask(UpdateTaskRequest updateTaskRequest);

    void tickOutTask(TickOutTaskRequest tickOutTaskRequest);

    Task findTask(FindTaskRequest findTaskRequest);

    void deleteTaskById(DeleteTaskByIdRequest deleteTaskByIdRequest);

    void deleteAllTask(String username);

    void deleteToDoList(String username);


}
