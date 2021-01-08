package com.yuhan.service.store.service;

import com.yuhan.service.store.domain.User;

import java.util.UUID;

/**
 * @author yuhan
 * @date 10.11.2020 - 15:28
 * @purpose
 */
public interface UserService {
    //新建用户
    public User createUser(User user);

    //查找用户
    public User getUserById(int userUid);

    //用户是否存在
    public Boolean checkUserExists(int userUid);
}
