package org.delightedToDoList.service;

import org.delightedToDoList.data.model.Task;

import java.util.List;

public interface TaskService {
    Task createTask(String title,String description, String todoListId);
    void taskIsCompleted(String taskId, String response);
    void updateTask(String description, String taskId);
    Task findTaskById(String taskId);
    List<Task> findByToDoListId(String todoListId);
    void deleteTaskById(String taskId);
    void deleteAllTasks(List<Task> tasks);


}
