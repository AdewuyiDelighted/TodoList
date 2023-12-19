package org.delightedToDoList.dtos.request;

import lombok.Data;

@Data
public class TickOutTaskRequest {
    private String username;
    private String title;
    private String description;
    private String response;
}
