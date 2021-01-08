package com.yuhan.service.store.service;

import com.yuhan.service.store.domain.User;
import com.yuhan.service.store.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

/**
 * @author yuhan
 * @date 11.11.2020 - 12:20
 * @purpose
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Transactional
    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User getUserById(int userUid) {
        return userRepository.findByUserUid(userUid)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User with uid %s not found", userUid)));
    }

    @Override
    public Boolean checkUserExists(int userUid) {
        return userRepository.findByUserUid(userUid).isPresent();
    }
}
