package com.project.bookmarks.model.request;

import lombok.Data;

@Data
public class UserRequest {
    private String name;
    private String userName;
    private String password;
    private String email;
    private Integer contactNo;
}
