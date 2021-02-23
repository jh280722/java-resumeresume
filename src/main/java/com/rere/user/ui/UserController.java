package com.rere.user.ui;

import com.rere.auth.domain.AuthenticationPrincipal;
import com.rere.auth.exception.InvalidTokenException;
import com.rere.user.application.UserService;
import com.rere.user.domain.LoginUser;
import com.rere.user.dto.UserRequest;
import com.rere.user.dto.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService UserService) {
        this.userService = UserService;
    }

    @PostMapping
    public ResponseEntity createItem(@RequestBody UserRequest userRequest) {
        //@AuthenticationPrincipal LoginMember loginMember 사용해서 인증
        return ResponseEntity.created(URI.create("/items/" + userService.save(userRequest).getId()))
                .build();
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getItems() {
        return ResponseEntity.ok().body(userService.findAll());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> showItem(@PathVariable Long id) {
        return ResponseEntity.ok().body(userService.findById(id));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity updateItem(@RequestBody UserRequest userRequest, @PathVariable Long id) {
        userService.updateUser(id, userRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteItem(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/profile")
    public ResponseEntity<UserResponse> findUserOfMine(@AuthenticationPrincipal LoginUser loginUser) {
        return ResponseEntity.ok().body(userService.findUser(loginUser.getId()));
    }

    @PutMapping("/profile")
    public ResponseEntity<UserResponse> updateUserOfMine(@AuthenticationPrincipal LoginUser loginUser, @RequestBody UserRequest userRequest) {
        userService.updateUser(loginUser.getId(), userRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/profile")
    public ResponseEntity<UserResponse> deleteUserOfMine(@AuthenticationPrincipal LoginUser loginUser) {
        userService.deleteById(loginUser.getId());
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<Void> invalidToken() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
