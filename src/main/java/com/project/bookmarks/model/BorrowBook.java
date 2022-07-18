package com.project.bookmarks.model;

import lombok.Data;

@Data
public class BorrowBook {
    private Long userId;
    private Long bookId;
}
