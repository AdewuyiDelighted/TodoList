package org.delightedToDoList.controller;

import org.delightedToDoList.dtos.reponses.*;
import org.delightedToDoList.dtos.request.*;
import org.delightedToDoList.exceptions.TodolistExceptions;
import org.delightedToDoList.service.ToDoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TodoListController {
    @Autowired
    private ToDoListService toDoListService;




    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody RegisterRequest registerRequest) {
        RegisterResponse registerResponse = new RegisterResponse();
        try {
            toDoListService.register(registerRequest);
            registerResponse.setMessage("Account created");
            return new ResponseEntity<>(new ApiResponse(true, registerResponse), HttpStatus.CREATED);
        } catch (TodolistExceptions ex) {
            registerResponse.setMessage(ex.getMessage());
            return new ResponseEntity<>(new ApiResponse(false, registerResponse), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = new LoginResponse();
        try {
            toDoListService.login(loginRequest);
            loginResponse.setMessage("Account unlocked");
            return new ResponseEntity<>(new ApiResponse(true, loginResponse), HttpStatus.OK);
        } catch (TodolistExceptions ex) {
            loginResponse.setMessage(ex.getMessage());
            return new ResponseEntity<>(new ApiResponse(false, loginResponse), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/addTask")
    public ResponseEntity<?> addTask(@RequestBody AddTaskRequest addTaskRequest) {
        AddTaskResponse addTaskResponse = new AddTaskResponse();
        try {
            toDoListService.addTask(addTaskRequest);
            addTaskResponse.setMessage("Task added");
            return new ResponseEntity<>(new ApiResponse(true, addTaskResponse), HttpStatus.OK);

        } catch (TodolistExceptions ex) {
            return new ResponseEntity<>(new ApiResponse(false, addTaskResponse), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/updateTask")
    public ResponseEntity<?> updateTask(@RequestBody UpdateTaskRequest updateRequest) {
        UpdateTaskResponse updateTaskResponse = new UpdateTaskResponse();
        try {
            toDoListService.updateTask(updateRequest);
            updateTaskResponse.setMessage("Task update");
            return new ResponseEntity<>(new ApiResponse(true, updateTaskResponse), HttpStatus.OK);
        } catch (TodolistExceptions ex) {
            return new ResponseEntity<>(new ApiResponse(false, updateTaskResponse), HttpStatus.BAD_REQUEST);
        }

    }


    @DeleteMapping("/tickOutTask")
    public ResponseEntity<?> tickOutTask(@RequestBody TickOutTaskRequest tickOutTaskRequest) {
        TickOutTaskResponse tickOutTaskResponse = new TickOutTaskResponse();
        try {
            toDoListService.tickOutTask(tickOutTaskRequest);
            tickOutTaskResponse.setMessage("Task ticked out");
            return new ResponseEntity<>(new ApiResponse(true, tickOutTaskResponse), HttpStatus.OK);
        } catch (TodolistExceptions ex) {
            return new ResponseEntity<>(new ApiResponse(false, tickOutTaskResponse), HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/deleteTask")
    public ResponseEntity<?> deleteByTaskId(@RequestBody DeleteTaskByIdRequest deleteTaskByIdRequest) {
        DeleteTaskByIdResponse deleteTaskByIdResponse = new DeleteTaskByIdResponse();
        try {
            toDoListService.deleteTaskById(deleteTaskByIdRequest);
            deleteTaskByIdResponse.setMessage("Task deleted");
            return new ResponseEntity<>(new ApiResponse(true, deleteTaskByIdResponse), HttpStatus.OK);
        } catch (TodolistExceptions ex) {
            return new ResponseEntity<>(new ApiResponse(false, deleteTaskByIdResponse), HttpStatus.BAD_REQUEST);
        }

    }


    @GetMapping("/findAllTasks/{username}")
    public ResponseEntity<?> findAllTasks(@PathVariable("username") String username) {
        FindAllTaskResponse findAllTaskResponse = new FindAllTaskResponse();
        try {
            toDoListService.findAllTaskBelongingTo(username);
            findAllTaskResponse.setTasks(toDoListService.findAllTaskBelongingTo(username));
            return new ResponseEntity<>(new ApiResponse(true, findAllTaskResponse), HttpStatus.OK);
        } catch (TodolistExceptions ex) {
            return new ResponseEntity<>(new ApiResponse(false, findAllTaskResponse), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findATask")
    public ResponseEntity<?> findATask(@RequestBody FindTaskRequest findTaskRequest) {
        FindTaskResponse findTaskResponse = new FindTaskResponse();
        try {
            toDoListService.findTask(findTaskRequest);
            findTaskResponse.setMessage(findTaskRequest.getDescription());
            return new ResponseEntity<>(new ApiResponse(true, findTaskResponse), HttpStatus.OK);
        } catch (TodolistExceptions ex) {
            return new ResponseEntity<>(new ApiResponse(false, findTaskRequest), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/deleteAllTask/{username}")
    public ResponseEntity<?> deleteAllTask(@PathVariable("username") String username) {
        DeleteAllTasksResponse deleteAllTasksResponse = new DeleteAllTasksResponse();
        try {
            toDoListService.deleteAllTask(username);
            deleteAllTasksResponse.setMessage("All task deleted");
            return new ResponseEntity<>(new ApiResponse(true, deleteAllTasksResponse), HttpStatus.OK);
        } catch (TodolistExceptions ex) {
            return new ResponseEntity<>(new ApiResponse(false, deleteAllTasksResponse), HttpStatus.BAD_REQUEST);
        }

    }


    @DeleteMapping("/deleteAccount/{username}")
    public ResponseEntity<?> deleteAccount(@PathVariable("username") String username) {
        DeleteAccountResponse deleteAccountResponse = new DeleteAccountResponse();
        try {
            toDoListService.deleteToDoList(username);
            deleteAccountResponse.setMessage("All task deleted");
            return new ResponseEntity<>(new ApiResponse(true, deleteAccountResponse), HttpStatus.OK);
        } catch (TodolistExceptions ex) {
            return new ResponseEntity<>(new ApiResponse(false, deleteAccountResponse), HttpStatus.BAD_REQUEST);
        }
    }


}
