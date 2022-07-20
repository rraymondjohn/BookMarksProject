package com.project.bookmarks.model.request;

import lombok.Data;

@Data
public class SearchRequest {
    private Boolean isAvailable;
    private String title;
    private String author;
    private String genre;
}
