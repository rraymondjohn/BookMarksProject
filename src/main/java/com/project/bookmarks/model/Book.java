package com.project.bookmarks.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name="books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String author;
    private String imageSource;
    private String genre;
    private String status;
    private LocalDateTime borrowedDate;
    private LocalDateTime dueDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

//    public Book(String title, String description, String author, String genre, String status, LocalDateTime borrowedDate, LocalDateTime dueDate, User user) {
//        this.title = title;
//        this.description = description;
//        this.author = author;
//        this.genre = genre;
//        this.status = status;
//        this.borrowedDate = borrowedDate;
//        this.dueDate = dueDate;
//        this.user = user;
//    }
}
