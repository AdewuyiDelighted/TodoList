package org.delightedToDoList;

import org.delightedToDoList.controller.TodoListController;
import org.delightedToDoList.dtos.request.LoginRequest;
import org.delightedToDoList.dtos.request.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.gridfs.ReactiveGridFsOperations;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@SpringBootApplication
public class Main {
    private static TodoListController todoListController;
   static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        SpringApplication.run(Main.class,args);
        display();
    }
    public static void display(){
        String option = input("""
                =============================
                1.Register todolist
                2.Login todolist
                3.Add task
                4.update task
                5.Tick out task
                6.delete task
                7.exit
                ==================================""");
        switch (option){
            case "1" -> register();
            case "2" -> login();
//            case "3" -> addTask();
//            case "4" -> updateTask();
//            case "5" -> tickOutTask();
//            case "6" -> deleteTask();
//            case "7" -> exit();
        }
    }
    private static void register(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername(input("Enter your username "));
        registerRequest.setPassword(input("Enter your password"));
        System.out.println(todoListController.register(registerRequest));
    }
    private static void login(){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(input("Enter your username "));
        loginRequest.setPassword(input("Enter your password"));
        System.out.println(todoListController.login(loginRequest));
    }

    public static String input(String message){
        return scanner.nextLine();
    }
}