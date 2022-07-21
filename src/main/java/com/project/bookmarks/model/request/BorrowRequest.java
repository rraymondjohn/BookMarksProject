package com.project.bookmarks.model.request;

import lombok.Data;

@Data
public class BorrowRequest {
    private String userId;
    private String bookId;
}
