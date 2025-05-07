package com.ecommerceAPI.controller;

import com.ecommerceAPI.core.utils.ResultHelper;
import com.ecommerceAPI.dto.request.CourierStatusRequest;
import com.ecommerceAPI.dto.request.UserRequest;
import com.ecommerceAPI.dto.response.UserResponse;
import com.ecommerceAPI.entity.User;
import com.ecommerceAPI.enums.CourierStatus;
import com.ecommerceAPI.enums.UserRole;
import com.ecommerceAPI.service.modelMapper.ModelMapperService;
import com.ecommerceAPI.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    public ResponseEntity<Map<String, Object>> save(@Valid @RequestBody UserRequest userRequest) {
        User user = this.modelMapper.forRequest().map(userRequest, User.class);
        this.userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResultHelper.created(this.modelMapper.forResponse().map(user, UserResponse.class)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> get(@PathVariable("id") Long id) {
        User user = this.userService.getById(id);
        return ResponseEntity.ok(ResultHelper.success(this.modelMapper.forResponse().map(user, UserResponse.class)));
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAll() {
        List<User> userList = this.userService.getAll();
        List<UserResponse> userResponseList = userList.stream().map(user -> this.modelMapper.forResponse().map(user, UserResponse.class)).toList();
        return ResponseEntity.ok(ResultHelper.success(userResponseList));
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<Map<String, Object>> getAllByRole(@PathVariable("role") UserRole role) {
        List<User> userList = this.userService.getAllByRole(role);
        List<UserResponse> userResponseList = userList.stream().map(user -> this.modelMapper.forResponse().map(user, UserResponse.class)).toList();
        return ResponseEntity.ok(ResultHelper.success(userResponseList));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable("id") Long id, @Valid @RequestBody UserRequest userRequest) {
        User user = this.modelMapper.forRequest().map(userRequest, User.class);
        user.setId(id);
        this.userService.update(user);
        return ResponseEntity.ok(ResultHelper.success(this.modelMapper.forResponse().map(user, UserResponse.class)));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Map<String, Object>> updateCourierStatus(@PathVariable("id") Long id, @Valid @RequestBody CourierStatusRequest statusRequest) {
        this.userService.updateCourierStatus(id, statusRequest.getStatus());
        User user = this.userService.getById(id);
        return ResponseEntity.ok(ResultHelper.success(this.modelMapper.forResponse().map(user, UserResponse.class)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable("id") Long id) {
        this.userService.delete(id);
        return ResponseEntity.ok(ResultHelper.ok());
    }
}
