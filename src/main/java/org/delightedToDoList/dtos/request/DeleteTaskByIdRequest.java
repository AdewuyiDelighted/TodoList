package org.delightedToDoList.dtos.request;

import lombok.Data;

@Data
public class DeleteTaskByIdRequest {
    private String username;
    private String title;
    private String description;
}
