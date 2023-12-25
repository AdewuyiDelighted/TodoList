package org.delightedToDoList.service;

import org.delightedToDoList.data.model.Task;
import org.delightedToDoList.data.model.TodoList;
import org.delightedToDoList.data.repositories.TodoListRepository;
import org.delightedToDoList.dtos.request.FindTaskRequest;
import org.delightedToDoList.dtos.request.*;
import org.delightedToDoList.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

import static org.delightedToDoList.utils.Mapper.map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class ToDoListServiceImpl implements ToDoListService {

    @Autowired
    private TodoListRepository todoListRepository;
    @Autowired
    private TaskService taskService;

    @Override
    public void register(RegisterRequest registerRequest) {
        if (userExist(registerRequest.getUsername()))throw new UserExistExceptions(registerRequest.getUsername() + " already exist");
        passwordChecker(registerRequest);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());
        registerRequest.setPassword(encodedPassword);
        TodoList todoList = map(registerRequest);
        todoListRepository.save(todoList);
    }

    private static void passwordChecker(RegisterRequest registerRequest) {
        String regex = "^(?=.*[0-9])"+"(?=.*[a-z])"+"(?=.*[A-Z])"+"(?=.*[@#$%^&-+=()])"+"(?=\\S+$).{8,20}$";
        if(!Pattern.matches(regex, registerRequest.getPassword()))throw new PasswordTooWeakException("Password too weak");
    }

    private boolean userExist(String username) {
        TodoList todoList = todoListRepository.findByUsername(username);
        return todoList != null;
    }

    @Override
    public TodoList login(LoginRequest loginRequest) {
        TodoList todoList = todoListRepository.findByUsername(loginRequest.getUsername());
        validateUsernameAndPassword(loginRequest,todoList);
        todoList.setLocked(false);
        todoListRepository.save(todoList);
        return todoList;

    }


    private void validateUsernameAndPassword(LoginRequest loginRequest,TodoList todoList) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!userExist(loginRequest.getUsername())) throw new InvalidDetailExceptions();
        if (!passwordEncoder.matches(loginRequest.getPassword(), todoList.getPassword()))throw new InvalidDetailExceptions();

    }

    @Override
    public TodoList findByUserName(String username) {
        return todoListRepository.findByUsername(username);
    }

    @Override
    public List<Task> findAllTaskBelongingTo(String username) {
        TodoList todoList = findByUserName(username);
        return taskService.findByToDoListId(todoList.getId());
    }


    @Override
    public void addTask(AddTaskRequest addTaskRequest) {
        TodoList todoList = validateTaskExistence(addTaskRequest);
        if (todoList.isLocked()) throw new TodoListLockedExceptions("Todolist locked");
        taskService.createTask(addTaskRequest.getTitle(), addTaskRequest.getDescription(), todoList.getId());

    }

    private TodoList validateTaskExistence(AddTaskRequest addTaskRequest) {
        TodoList todoList = findByUserName(addTaskRequest.getUsername());
        List<Task> tasks = taskService.findByToDoListId(todoList.getId());
        for (Task task : tasks) {
            if (task.getTitle().equals(addTaskRequest.getTitle()) && task.getDescription().equals(addTaskRequest.getDescription())) {
                throw new TaskAlreadyExitException("Task already exist");
            }
        }
        return todoList;
    }


    @Override
    public void updateTask(UpdateTaskRequest updateTaskRequest) {
        TodoList todoList = findByUserName(updateTaskRequest.getUsername());
        for (Task tasks : taskService.findByToDoListId(todoList.getId())) {
            if (tasks.getTitle().equals(updateTaskRequest.getTitle()) && tasks.getDescription().equals(updateTaskRequest.getPreviousDescription())) {
                taskService.updateTask(updateTaskRequest.getNewDescription(), tasks.getId());
            }
        }

    }

    @Override
    public void tickOutTask(TickOutTaskRequest tickOutTaskRequest) {
        TodoList todoList = findByUserName(tickOutTaskRequest.getUsername());
        List<Task> tasks = taskService.findByToDoListId(todoList.getId());
        for (Task task : tasks) {
            if (task.getTitle().equals(tickOutTaskRequest.getTitle()) && task.getDescription().equals(tickOutTaskRequest.getDescription())) {
                taskService.taskIsCompleted(task.getId(), tickOutTaskRequest.getResponse());
            }
        }
    }

    @Override
    public Task findTask(FindTaskRequest findTaskRequest) {
        TodoList todoList = findByUserName(findTaskRequest.getUsername());
        for (Task tasks : taskService.findByToDoListId(todoList.getId())) {
            if (tasks.getTitle().equals(findTaskRequest.getTitle()) && tasks.getDescription().equals(findTaskRequest.getDescription())) {
                return taskService.findTaskById(tasks.getId());
            }
        }
        return null;
    }


    @Override
    public void deleteTaskById(DeleteTaskByIdRequest deleteTaskByIdRequest) {
        TodoList todoList = findByUserName(deleteTaskByIdRequest.getUsername());
        for (Task tasks : taskService.findByToDoListId(todoList.getId())) {
            if (tasks.getTitle().equals(deleteTaskByIdRequest.getTitle()) && tasks.getDescription().equals(deleteTaskByIdRequest.getDescription())) {
                taskService.deleteTaskById(tasks.getId());
            }
        }

    }

    @Override
    public void deleteAllTask(String username) {
        TodoList todoList = findByUserName(username);
        List<Task> tasksOfUsername = taskService.findByToDoListId(todoList.getId());
        taskService.deleteAllTasks(tasksOfUsername);
    }


    @Override
    public void deleteToDoList(String username) {
        TodoList todoList = findByUserName(username);
        todoListRepository.delete(todoList);
    }


}