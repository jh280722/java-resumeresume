package com.rere.user.dto;

public class UserRequest {
    private Long id;
    private String email;
    private String password;
    private String name;

    protected UserRequest() {
    }

    private UserRequest(Long id, String email, String password, String name) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public static UserRequest of(String email, String password, String name) {
        return new UserRequest(null, email, password, name);
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }
}
