package org.delightedToDoList.controller;

import org.delightedToDoList.dtos.request.LoginRequest;
import org.delightedToDoList.dtos.request.RegisterRequest;
import org.delightedToDoList.exceptions.TodolistExceptions;
import org.delightedToDoList.service.ToDoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TodoListController {
    @Autowired
    private ToDoListService toDoListService;



    @GetMapping("/register")
    public String register(@RequestBody RegisterRequest registerRequest) {
        try {
            toDoListService.register(registerRequest);
            return "Account created";
        } catch (TodolistExceptions ex) {
            return ex.getMessage();
        }
    }
    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest){
        try{
            toDoListService.login(loginRequest);
            return "TodoListController unlocked";
        }catch (TodolistExceptions ex){
            return ex.getMessage();
        }
    }



}
