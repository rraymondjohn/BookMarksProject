package com.project.bookmarks.model.request;

import lombok.Data;

@Data
public class ReserveRequest {
    private String userId;
    private String bookId;
}
