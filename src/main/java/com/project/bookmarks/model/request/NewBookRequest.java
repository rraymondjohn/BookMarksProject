package com.project.bookmarks.model.request;

import lombok.Data;

@Data
public class NewBookRequest {
    private String title;
    private String description;
    private String author;
    private String imageSource;
    private String genre;
}
