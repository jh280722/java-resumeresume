package com.rere.user.domain;


public class LoginUser {
    private Long id;
    private String email;

    public LoginUser() {
    }

    private LoginUser(Long id, String email) {
        this.id = id;
        this.email = email;
    }

    public static LoginUser of(User user) {
        return new LoginUser(user.getId(), user.getEmail());
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
}
