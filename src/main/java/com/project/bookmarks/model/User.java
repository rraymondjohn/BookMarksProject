package com.project.bookmarks.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String username;
    private String password;
    private String email;
    private Integer contactNo;
    private String userType;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Book> books;
}
