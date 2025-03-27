package com.getirApp.getirAppBackend.service.user;

import com.getirApp.getirAppBackend.core.exception.NotFoundException;
import com.getirApp.getirAppBackend.core.utils.Msg;
import com.getirApp.getirAppBackend.entity.User;
import com.getirApp.getirAppBackend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public User save(User user) {
        return this.userRepository.save(user);
    }

    @Override
    public User getById(Long id) {
        return this.userRepository.findById(id).orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));
    }

    @Override
    public List<User> getUserList() {
        return this.userRepository.findAllByOrderByIdAsc();
    }

    @Transactional
    @Override
    public User update(User user) {
        this.getById(user.getId());
        return this.userRepository.save(user);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        User user = this.getById(id);
        this.userRepository.delete(user);
    }
}
