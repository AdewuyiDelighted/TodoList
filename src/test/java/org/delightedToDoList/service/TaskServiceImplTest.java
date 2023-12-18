package org.delightedToDoList.service;

import org.delightedToDoList.data.model.Task;
import org.delightedToDoList.data.model.TodoList;
import org.delightedToDoList.data.repositories.TaskRepository;
import org.delightedToDoList.data.repositories.TodoListRepository;
import org.delightedToDoList.dtos.request.LoginRequest;
import org.delightedToDoList.dtos.request.RegisterRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TaskServiceImplTest {
    @Autowired
    TaskService taskService;
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    ToDoListService toDoListService;
    @Autowired
    TodoListRepository  todoListRepository;

    @BeforeEach public void startWithThis(){
        todoListRepository.deleteAll();
        taskRepository.deleteAll();

    }

    @Test
    public void testWhenTaskIsCreated(){
        TodoList todoList = new TodoList();
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setPassword("delighted");
        registerRequest.setPassword("password");
        toDoListService.register(registerRequest);
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword("delighted");
        loginRequest.setPassword("password");
        toDoListService.login(loginRequest);
        taskService.createTask("wash plate",todoList.getId());
        System.out.println(todoList.getId());
        assertEquals(1,taskRepository.count());

    }
    @Test
    public void testThatTaskUpdateTaskThatHasBeenSetBefore(){
        TodoList todoList = new TodoList();
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setPassword("delighted");
        registerRequest.setPassword("password");
        toDoListService.register(registerRequest);
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword("delighted");
        loginRequest.setPassword("password");
        toDoListService.login(loginRequest);
        Task task = taskService.createTask("wash cloth",todoList.getId());
        assertEquals(1,taskRepository.count());
        taskService.updateTask("go to school",task.getId());
        assertEquals(1,taskRepository.count());
    }
    @Test
    public void testThatWhenICreateTwoTask(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setPassword("delighted");
        registerRequest.setPassword("password");
        toDoListService.register(registerRequest);
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword("delighted");
        loginRequest.setPassword("password");
        TodoList todoList = toDoListService.login(loginRequest);
        Task task1 = taskService.createTask("wash cloth",todoList.getId());
        Task task2 = taskService.createTask("watch movie",todoList.getId());
        assertEquals(2,taskService.findAll().size());
    }
    @Test
    public void findTaskByIdTest(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setPassword("delighted");
        registerRequest.setPassword("password");
        toDoListService.register(registerRequest);
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword("delighted");
        loginRequest.setPassword("password");
        TodoList todoList = toDoListService.login(loginRequest);
        Task task1 = taskService.createTask("make dinner",todoList.getId());
        Task task2 = taskService.createTask("Lay bed",todoList.getId());
        assertEquals(task1,taskService.findTaskById(task1.getId()));
        assertEquals(task2,taskService.findTaskById(task2.getId()));
    }


    @Test public void testWhenTaskIsCompleted(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setPassword("delighted");
        registerRequest.setPassword("password");
        toDoListService.register(registerRequest);
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword("delighted");
        loginRequest.setPassword("password");
        TodoList todoList = toDoListService.login(loginRequest);
        Task task1 = taskService.createTask("make dinner",todoList.getId());
        Task task2 = taskService.createTask("Lay bed",todoList.getId());
        taskService.taskIsCompleted(task1.getId(),"YES");
       // assertTrue(task1.isCompleted());
        assertEquals(2,taskRepository.count());
      //  assertTrue(task1.isCompleted());


    }



}