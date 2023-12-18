package org.delightedToDoList.dtos.request;

import lombok.Data;

@Data
public class DeleteRequest {
    private String username;
    private String password;
}
