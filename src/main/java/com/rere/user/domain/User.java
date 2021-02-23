package com.rere.user.domain;


import com.rere.user.dto.UserRequest;

import javax.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    protected User() {
    }

    private User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    private User(Long id, String email, String password, String name) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public static User of(UserRequest userRequest) {
        return new User(userRequest.getId(), userRequest.getEmail(), userRequest.getPassword(), userRequest.getName());
    }

    public static User of() {
        return new User( null, null, null);
    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void changeUser(User user) {
        this.name = user.name;
        this.email = user.email;
        this.password = user.password;
    }

}
