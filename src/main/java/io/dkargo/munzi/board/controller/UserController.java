package io.dkargo.munzi.board.controller;

import io.dkargo.munzi.board.dto.request.ReqCreateUserDto;
import io.dkargo.munzi.board.dto.request.ReqUpdateUserDto;
import io.dkargo.munzi.board.dto.response.ResCreateUserDto;
import io.dkargo.munzi.board.dto.response.ResGetUserDetailDto;
import io.dkargo.munzi.board.dto.response.ResGetUserListDto;
import io.dkargo.munzi.board.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("")
    public ResponseEntity<ResCreateUserDto> createUser(@Validated @RequestBody ReqCreateUserDto dto) {
        ResCreateUserDto result = userService.createUser(dto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("")
    public ResponseEntity<ResGetUserListDto> getUserList(@RequestParam("page") int page, @RequestParam("size") int size) {
        ResGetUserListDto result = userService.getUserList(page, size);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ResGetUserDetailDto> getUserDetail(@PathVariable("userId") long userId) {
        ResGetUserDetailDto result = userService.getUserDetail(userId);
        return ResponseEntity.ok(result);
    }

    @PatchMapping("")
    public ResponseEntity<Boolean> updateUser(@Validated @RequestBody ReqUpdateUserDto dto) {
        boolean result = userService.updateUser(dto);
        return ResponseEntity.ok(result);
    }
}
