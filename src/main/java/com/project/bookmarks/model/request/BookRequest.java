package com.project.bookmarks.model.request;

import com.project.bookmarks.model.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookRequest {
    private String title;
    private String description;
    private String author;
    private String imageSource;
    private String genre;
    private String status;
//    private LocalDateTime borrowedDate;
//    private LocalDateTime dueDate; // 14 days after borrowedDate
//    private Long userId;
}
