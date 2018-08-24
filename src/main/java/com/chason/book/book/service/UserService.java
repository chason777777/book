package com.chason.book.book.service;

import com.chason.book.book.entity.User;
import com.chason.book.book.result.Result;

import java.util.List;

/**
 * Created by chason on 2018/8/23.11:08
 */
public interface UserService {
    Result addUser(User user) throws Exception;

    Result deleteUser(String userUuid) throws Exception;

    Result updateUser(User user) throws Exception;

    Result getUserByUuid(String userUuid) throws Exception;

    List<User> getAll() throws Exception;

    Result login(String loginUsername, String loginPassword) throws Exception;

    Result logout(String loginUsername) throws Exception;

    User getByLoginUsername(String loginUsername) throws Exception;
}
