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



    @Override
    public Task createTask(String title,String description, String todoListId) {
        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setTodoListId(todoListId);
        taskRepository.save(task);
        return task;
    }
   @Override
    public List<Task> findByToDoListId(String todoListId){
        List<Task> tasksOfUser = new ArrayList<>();
        for(Task task : taskRepository.findAll()){
            if(task.getTodoListId().equals(todoListId)){
                tasksOfUser.add(task);
            }
        }
        return tasksOfUser;
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
    public void taskIsCompleted(String taskId, String response){
        Task task = findTaskById(taskId);
        if(response.equalsIgnoreCase("yes")){
            task.setCompleted(true);
            taskRepository.delete(task);
        }
    }

    @Override
    public void deleteTaskById(String taskId){
        taskRepository.deleteById(taskId);
    }

    @Override
    public void deleteAllTasks(List<Task> tasks){
        taskRepository.deleteAll(tasks);
    }


    }
