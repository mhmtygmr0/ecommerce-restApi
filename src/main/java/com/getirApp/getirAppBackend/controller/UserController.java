package com.getirApp.getirAppBackend.controller;

import com.getirApp.getirAppBackend.core.utils.Result;
import com.getirApp.getirAppBackend.core.utils.ResultData;
import com.getirApp.getirAppBackend.core.utils.ResultHelper;
import com.getirApp.getirAppBackend.dto.request.UserRequest;
import com.getirApp.getirAppBackend.dto.response.UserResponse;
import com.getirApp.getirAppBackend.entity.User;
import com.getirApp.getirAppBackend.service.modelMapper.ModelMapperService;
import com.getirApp.getirAppBackend.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final ModelMapperService modelMapper;

    public UserController(UserService userService, ModelMapperService modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<UserResponse> save(@Valid @RequestBody UserRequest userRequest) {
        User user = this.modelMapper.forRequest().map(userRequest, User.class);
        this.userService.save(user);
        return ResultHelper.created(this.modelMapper.forResponse().map(user, UserResponse.class));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<UserResponse> get(@PathVariable("id") Long id) {
        User user = this.userService.getById(id);
        return ResultHelper.success(this.modelMapper.forResponse().map(user, UserResponse.class));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<UserResponse>> getAll() {
        List<User> userList = this.userService.getUserList();
        List<UserResponse> userResponseList = userList.stream().map(user -> this.modelMapper.forResponse().map(user, UserResponse.class)).toList();
        return ResultHelper.success(userResponseList);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<UserResponse> update(@PathVariable Long id, @Valid @RequestBody UserRequest userRequest) {
        User user = this.modelMapper.forRequest().map(userRequest, User.class);
        user.setId(id);
        this.userService.update(user);
        return ResultHelper.success(this.modelMapper.forResponse().map(user, UserResponse.class));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") Long id) {
        this.userService.delete(id);
        return ResultHelper.ok();
    }
}
