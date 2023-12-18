package org.delightedToDoList.service.toDoService;

import org.delightedToDoList.data.model.TodoList;
import org.delightedToDoList.data.repositories.TaskRepository;
import org.delightedToDoList.data.repositories.TodoListRepository;
import org.delightedToDoList.dtos.request.LoginRequest;
import org.delightedToDoList.dtos.request.RegisterRequest;
import org.delightedToDoList.exceptions.InvalidDetailExceptions;
import org.delightedToDoList.exceptions.UserExistExceptions;
import org.delightedToDoList.service.ToDoListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ToDoListServiceImplTest {
    @Autowired
    ToDoListService toDoListService;
    @Autowired
    TodoListRepository todoListRepository;
    @Autowired
    TaskRepository taskRepository;

    @BeforeEach public void startWith(){
        todoListRepository.deleteAll();
    }

    @Test
    public void registerUserAndRegisterTheSameUserAgainTest() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        toDoListService.register(registerRequest);
        assertThrows(UserExistExceptions.class, () -> toDoListService.register(registerRequest));
    }

    @Test
    public void registerUserAndLoginTheSameUserAgainTest() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        toDoListService.register(registerRequest);
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");
        toDoListService.login(loginRequest);
        assertEquals(1, todoListRepository.count());
    }

    @Test
    @DisplayName("register and login with incorrect details test")
    public void registerAndLoginWithIncorrectPasswordTest() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        toDoListService.register(registerRequest);
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("WrongPassword");
        assertThrows(InvalidDetailExceptions.class, () -> toDoListService.login(loginRequest));
    }

    @Test
    @DisplayName("register and login with incorrect details test")
    public void registerAndLoginWithIncorrectUsernameTest() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        toDoListService.register(registerRequest);
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("userName");
        loginRequest.setPassword("password");
        assertThrows(InvalidDetailExceptions.class, () -> toDoListService.login(loginRequest));
    }
    @Test
    public void registerUserAndLoginAddTask() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        toDoListService.register(registerRequest);
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");
        TodoList todoList = toDoListService.login(loginRequest);
        toDoListService.addTask("wash plate",todoList.getId());
        assertEquals(1, todoListRepository.count());
        assertEquals(1,taskRepository.count());
    }



}