package org.delightedToDoList.service.toDoService;

import org.delightedToDoList.data.model.Task;
import org.delightedToDoList.data.repositories.TaskRepository;
import org.delightedToDoList.data.repositories.TodoListRepository;
import org.delightedToDoList.dtos.request.FindTaskRequest;
import org.delightedToDoList.dtos.request.*;
import org.delightedToDoList.exceptions.InvalidDetailExceptions;
import org.delightedToDoList.exceptions.TaskAlreadyExitException;
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

    @BeforeEach
    public void startWith() {
        todoListRepository.deleteAll();
        taskRepository.deleteAll();
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
        toDoListService.login(loginRequest);
        AddTaskRequest addTaskRequest = new AddTaskRequest();
        addTaskRequest.setUsername("username");
        addTaskRequest.setTitle("my personal work");
        addTaskRequest.setDescription("i want to cook");
        toDoListService.addTask(addTaskRequest);
        assertEquals(1, todoListRepository.count());
        assertEquals(1, taskRepository.count());
    }

    @Test
    public void registerUserAndLoginAddTwoTaskAndUpdateOneTaskTest() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        toDoListService.register(registerRequest);
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");
        toDoListService.login(loginRequest);
        AddTaskRequest addTaskRequestOne = new AddTaskRequest();
        addTaskRequestOne.setUsername("username");
        addTaskRequestOne.setTitle("my personal work");
        addTaskRequestOne.setDescription("i want to sweep");
        AddTaskRequest addTaskRequestTwo = new AddTaskRequest();
        addTaskRequestTwo.setUsername("username");
        addTaskRequestTwo.setTitle("my school work");
        addTaskRequestTwo.setDescription("i do assignment");
        toDoListService.addTask(addTaskRequestOne);
        toDoListService.addTask(addTaskRequestTwo);
        UpdateTaskRequest updateTaskRequest = new UpdateTaskRequest();
        updateTaskRequest.setUsername("username");
        updateTaskRequest.setTitle("my personal work");
        updateTaskRequest.setPreviousDescription("i want to sweep");
        updateTaskRequest.setNewDescription("Watch movies");
        toDoListService.updateTask(updateTaskRequest);
        assertEquals(1, todoListRepository.count());
        assertEquals(2, taskRepository.count());
    }

    @Test
    public void registerUserAndLoginAddTaskThatAlreadyExit() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("Delighted");
        registerRequest.setPassword("password");
        toDoListService.register(registerRequest);
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("Delighted");
        loginRequest.setPassword("password");
        toDoListService.login(loginRequest);
        AddTaskRequest addTaskRequestOne = new AddTaskRequest();
        addTaskRequestOne.setUsername("Delighted");
        addTaskRequestOne.setTitle("church errand");
        addTaskRequestOne.setDescription("Go to church");
        toDoListService.addTask(addTaskRequestOne);
        AddTaskRequest addTaskRequestTwo = new AddTaskRequest();
        addTaskRequestTwo.setUsername("Delighted");
        addTaskRequestTwo.setTitle("church errand");
        addTaskRequestTwo.setDescription("Go to church");
        assertThrows(TaskAlreadyExitException.class, () -> toDoListService.addTask(addTaskRequestTwo));
    }

    @Test
    public void registerUserAndLoginAddTwoTasksTickOutTaskTest() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("Delighted");
        registerRequest.setPassword("password");
        toDoListService.register(registerRequest);
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("Delighted");
        loginRequest.setPassword("password");
        toDoListService.login(loginRequest);
        AddTaskRequest addTaskRequestOne = new AddTaskRequest();
        addTaskRequestOne.setUsername("Delighted");
        addTaskRequestOne.setTitle("church errand");
        addTaskRequestOne.setDescription("Go to church");
        toDoListService.addTask(addTaskRequestOne);
        AddTaskRequest addTaskRequestTwo = new AddTaskRequest();
        addTaskRequestTwo.setUsername("Delighted");
        addTaskRequestTwo.setTitle("home errands");
        addTaskRequestTwo.setDescription("Cook");
        toDoListService.addTask(addTaskRequestTwo);
        TickOutTaskRequest tickOutTaskRequest = new TickOutTaskRequest();
        tickOutTaskRequest.setUsername("Delighted");
        tickOutTaskRequest.setTitle("church errand");
        tickOutTaskRequest.setDescription("Go to church");
        tickOutTaskRequest.setResponse("yes");
        toDoListService.tickOutTask(tickOutTaskRequest);
        assertEquals(1, taskRepository.count());

    }

    @Test
    public void registerUserAndLoginAddThreeTaskAndDeleteTwoTask() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("dele");
        registerRequest.setPassword("password");
        toDoListService.register(registerRequest);
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("dele");
        loginRequest.setPassword("password");
        toDoListService.login(loginRequest);
        AddTaskRequest addTaskRequestOne = new AddTaskRequest();
        addTaskRequestOne.setUsername("dele");
        addTaskRequestOne.setTitle("work errands");
        addTaskRequestOne.setDescription("submit files");
        toDoListService.addTask(addTaskRequestOne);
        AddTaskRequest addTaskRequestTwo = new AddTaskRequest();
        addTaskRequestTwo.setUsername("dele");
        addTaskRequestTwo.setTitle("hospital work");
        addTaskRequestTwo.setDescription("Get drugs");
        toDoListService.addTask(addTaskRequestTwo);
        AddTaskRequest addTaskRequestThree = new AddTaskRequest();
        addTaskRequestThree.setUsername("dele");
        addTaskRequestThree.setTitle("hospital work");
        addTaskRequestThree.setDescription("Watch patients");
        toDoListService.addTask(addTaskRequestThree);
        DeleteTaskByIdRequest deleteTaskByIdRequestOne = new DeleteTaskByIdRequest();
        deleteTaskByIdRequestOne.setUsername("dele");
        deleteTaskByIdRequestOne.setTitle("hospital work");
        deleteTaskByIdRequestOne.setDescription("Get drugs");
        DeleteTaskByIdRequest deleteTaskByIdRequestTwo = new DeleteTaskByIdRequest();
        deleteTaskByIdRequestTwo.setUsername("dele");
        deleteTaskByIdRequestTwo.setTitle("hospital work");
        deleteTaskByIdRequestTwo.setDescription("Watch patients");
        toDoListService.deleteTaskById(deleteTaskByIdRequestTwo);
        toDoListService.deleteTaskById(deleteTaskByIdRequestOne);
        assertEquals(1, taskRepository.count());

    }

    @Test
    public void registerUserAndLoginAddThreeTaskAndDeleteAll() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("dele");
        registerRequest.setPassword("password");
        toDoListService.register(registerRequest);
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("dele");
        loginRequest.setPassword("password");
        toDoListService.login(loginRequest);
        AddTaskRequest addTaskRequestOne = new AddTaskRequest();
        addTaskRequestOne.setUsername("dele");
        addTaskRequestOne.setTitle("work errands");
        addTaskRequestOne.setDescription("submit files");
        toDoListService.addTask(addTaskRequestOne);
        AddTaskRequest addTaskRequestTwo = new AddTaskRequest();
        addTaskRequestTwo.setUsername("dele");
        addTaskRequestTwo.setTitle("hospital work");
        addTaskRequestTwo.setDescription("Get drugs");
        toDoListService.addTask(addTaskRequestTwo);
        AddTaskRequest addTaskRequestThree = new AddTaskRequest();
        addTaskRequestThree.setUsername("dele");
        addTaskRequestThree.setTitle("hospital work");
        addTaskRequestThree.setDescription("Watch patients");
        toDoListService.addTask(addTaskRequestThree);
        toDoListService.deleteAllTask("dele");
        assertEquals(0, taskRepository.count());

    }

    @Test
    public void registerUserAndLoginAddThreeTaskAndDeleteToDoList() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("ola");
        registerRequest.setPassword("password");
        toDoListService.register(registerRequest);
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("ola");
        loginRequest.setPassword("password");
        toDoListService.login(loginRequest);
        AddTaskRequest addTaskRequestOne = new AddTaskRequest();
        addTaskRequestOne.setUsername("ola");
        addTaskRequestOne.setTitle("home errands");
        addTaskRequestOne.setDescription("bath for the kids");
        toDoListService.addTask(addTaskRequestOne);
        AddTaskRequest addTaskRequestTwo = new AddTaskRequest();
        addTaskRequestTwo.setUsername("ola");
        addTaskRequestTwo.setTitle("school work");
        addTaskRequestTwo.setDescription("mark class assessment");
        toDoListService.addTask(addTaskRequestTwo);
        AddTaskRequest addTaskRequestThree = new AddTaskRequest();
        addTaskRequestThree.setUsername("ola");
        addTaskRequestThree.setTitle("home errands");
        addTaskRequestThree.setDescription("wash car");
        toDoListService.addTask(addTaskRequestThree);
        toDoListService.deleteToDoList("ola");
        assertEquals(0, todoListRepository.count());

    }

    @Test
    public void registerUserAndLoginAddThreeTaskAndFindAll() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("jide");
        registerRequest.setPassword("password");
        toDoListService.register(registerRequest);
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("jide");
        loginRequest.setPassword("password");
        toDoListService.login(loginRequest);
        AddTaskRequest addTaskRequestOne = new AddTaskRequest();
        addTaskRequestOne.setUsername("jide");
        addTaskRequestOne.setTitle("home errands");
        addTaskRequestOne.setDescription("bath for the kids");
        toDoListService.addTask(addTaskRequestOne);
        AddTaskRequest addTaskRequestTwo = new AddTaskRequest();
        addTaskRequestTwo.setUsername("jide");
        addTaskRequestTwo.setTitle("school work");
        addTaskRequestTwo.setDescription("mark class assessment");
        toDoListService.addTask(addTaskRequestTwo);
        AddTaskRequest addTaskRequestThree = new AddTaskRequest();
        addTaskRequestThree.setUsername("jide");
        addTaskRequestThree.setTitle("home errands");
        addTaskRequestThree.setDescription("wash car");
        toDoListService.addTask(addTaskRequestThree);
        assertEquals(3, toDoListService.findAllTaskBelongingTo("jide").size());

    }

    @Test
    public void registerAndLoginAddTaskAndFindTask() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("ola");
        registerRequest.setPassword("password");
        toDoListService.register(registerRequest);
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("ola");
        loginRequest.setPassword("password");
        toDoListService.login(loginRequest);
        AddTaskRequest addTaskRequestOne = new AddTaskRequest();
        addTaskRequestOne.setUsername("ola");
        addTaskRequestOne.setTitle("home errands");
        addTaskRequestOne.setDescription("bath for the kids");
        toDoListService.addTask(addTaskRequestOne);
        FindTaskRequest findTaskRequest = new FindTaskRequest();
        findTaskRequest.setUsername("ola");
        findTaskRequest.setTitle("home errands");
        findTaskRequest.setDescription("bath for the kids");
        Task task = toDoListService.findTask(findTaskRequest);
        assertEquals(addTaskRequestOne.getTitle(),task.getTitle());
        assertEquals(addTaskRequestOne.getDescription(),task.getDescription());


    }
}