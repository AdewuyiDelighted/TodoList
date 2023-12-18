package org.delightedToDoList.service;

import org.delightedToDoList.data.model.Task;
import org.delightedToDoList.data.model.TodoList;
import org.delightedToDoList.data.repositories.TodoListRepository;
import org.delightedToDoList.dtos.request.LoginRequest;
import org.delightedToDoList.dtos.request.RegisterRequest;
import org.delightedToDoList.exceptions.InvalidDetailExceptions;
import org.delightedToDoList.exceptions.UserExistExceptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.delightedToDoList.utils.Mapper.map;

@Service
public class ToDoListServiceImpl implements ToDoListService {

    @Autowired
    private TodoListRepository todoListRepository;
    @Autowired
    private TaskService taskService;

    @Override
    public void register(RegisterRequest registerRequest) {
        if (userExist(registerRequest.getUsername()))
            throw new UserExistExceptions(registerRequest.getUsername() + " already exist");
        TodoList todoList = map(registerRequest);
        todoListRepository.save(todoList);
    }

    private boolean userExist(String username) {
        TodoList todoList = todoListRepository.findByUsername(username);
        return todoList != null;
    }

    @Override
    public TodoList login(LoginRequest loginRequest) {
        TodoList todoList = todoListRepository.findByUsername(loginRequest.getUsername());
        validateUsernameAndPassword(loginRequest, todoList);
        todoList.setLocked(false);
        todoListRepository.save(todoList);
        return todoList;
    }


    private void validateUsernameAndPassword(LoginRequest loginRequest, TodoList todoList) {
        if (!userExist(loginRequest.getUsername())) throw new InvalidDetailExceptions();
        if (!(todoList.getPassword().equals(loginRequest.getPassword()))) throw new InvalidDetailExceptions();
    }

    @Override
    public TodoList findByUserName(String username) {
        return todoListRepository.findByUsername(username);
    }

    @Override
    public List<Task> findAllTaskBelongingTo(String username) {
        TodoList todoList = findByUserName(username);
        List<Task> thisUserTask = new ArrayList<>();
        for (Task task : taskService.findAll()) {
            if (task.getTodoListId().equals(todoList.getId())) {
                thisUserTask.add(task);
            }
        }
        return thisUserTask;
    }

    @Override
    public String addTask(String username, String description) {
        TodoList todoList = findByUserName(username);
        taskService.createTask(description, todoList.getId());
        return "task added";
    }

    @Override
    public void updateTask(String username, String description, String taskId) {
//        List<Task> tasksOf = findAllTaskBelongingTo(username);
//        for (Task task : tasksOf) {
//            if (task.getId().equals(taskId)) {
//                taskService.updateTask(description, taskId);
//            }
//        }
            Task task = taskService.findTaskById(taskId);
            taskService.updateTask(description,task.getId());

    }

    @Override
    public void tickOutTask(String id, String response) {
        Task task = taskService.findTaskById(id);
        taskService.taskIsCompleted(task.getId(),response);
    }

    @Override
    public void deleteTaskById(String taskId) {
        taskService.deleteTaskById(taskId);
    }

    @Override
    public void deleteAllTask() {
        taskService.deleteAllTasks();
    }

    @Override
    public void listOfTask(Task task) {
        List<Task> tasks = new ArrayList<>();
        tasks.add(task);
    }


}