package org.delightedToDoList.dtos.request;

import lombok.Data;

@Data
public class AddTaskRequest {
    private String username;
    private  String description;
}
