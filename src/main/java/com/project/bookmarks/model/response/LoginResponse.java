package com.project.bookmarks.model.response;

import lombok.Data;

@Data
public class LoginResponse {
    private Long id;
    private String role;
    private String token;
}
