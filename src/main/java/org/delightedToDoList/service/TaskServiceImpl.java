package org.delightedToDoList.service;

import org.delightedToDoList.data.model.Task;
import org.delightedToDoList.data.model.TodoList;
import org.delightedToDoList.data.repositories.TaskRepository;
import org.delightedToDoList.dtos.request.AddTaskRequest;
import org.delightedToDoList.exceptions.TaskNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private ToDoListService toDoListService;


    @Override
    public Task createTask(String description, String todoListId) {
        Task task = new Task();
        task.setDescription(description);
        task.setTodoListId(todoListId);
        taskRepository.save(task);
        toDoListService.listOfTask(task);
        return task;
    }

    @Override
    public void updateTask(String description, String taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException("Task not found!!!!!"));
        task.setDescription(description);
        taskRepository.save(task);
    }

    @Override
    public Task findTaskById(String taskId) {
        return taskRepository.findById(taskId).get();
    }


    @Override
    public void taskIsCompleted(String taskId, String response) {
        Task task = findTaskById(taskId);
        if(response.equalsIgnoreCase("yes")) {
            task.setCompleted(true);
            taskRepository.save(task);
        }
    }


    @Override
    public List<Task> findAll(){
        return taskRepository.findAll();
    }

    @Override
    public void deleteTaskById(String taskId){
        taskRepository.deleteById(taskId);
    }

    @Override
    public void deleteAllTasks(){
        taskRepository.deleteAll();
    }


    }
