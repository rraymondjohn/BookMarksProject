package com.project.bookmarks.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String userName;
    private String password;
    private String email;
    private Integer contactNo;

}
