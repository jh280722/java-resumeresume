package com.rere.user.dto;

public class UserRequest {
    private Long id;
    private String email;
    private String password;
    private String passwordConfirm;
    private String name;

    protected UserRequest() {
    }

    private UserRequest(Long id, String email, String password, String passwordConfirm, String name) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        this.name = name;
    }

    public static UserRequest of(String email, String password, String passwordConfirm, String name) {
        return new UserRequest(null, email, password, passwordConfirm, name);
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
