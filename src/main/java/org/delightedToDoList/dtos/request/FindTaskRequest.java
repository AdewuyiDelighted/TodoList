package org.delightedToDoList.dtos.request;

import lombok.Data;

@Data
public class FindTaskRequest {
    private String username;
    private String title;
    private String description;
}
