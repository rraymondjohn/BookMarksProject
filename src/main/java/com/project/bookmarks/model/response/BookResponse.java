package com.project.bookmarks.model.response;

import com.project.bookmarks.model.User;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BookResponse {
    private Long id;
    private String title;
    private String description;
    private String author;
    private String imageSource;
    private String genre;
    private String status;
    private LocalDateTime borrowedDate;
    private LocalDateTime dueDate;
    private User user;

}
