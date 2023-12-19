package org.delightedToDoList.dtos.request;

import lombok.Data;

@Data
public class UpdateTaskRequest {
   private String username;
    private String title;
    private String previousDescription;
    private String newDescription;
}
