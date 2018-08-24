package com.chason.book.book.controller;

import com.chason.book.book.entity.User;
import com.chason.book.book.pageHelper.PageInfo;
import com.chason.book.book.result.Result;
import com.chason.book.book.service.UserService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by chason on 2018/8/23.11:01
 */
@RestController
@CrossOrigin
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("addUser")
    public Result addUser(User user) throws Exception {
        return userService.addUser(user);
    }

    @RequestMapping("deleteByUuid")
    public Result deleteByUuid(@RequestParam(value = "uuid", required = true) String userUuid) throws Exception {
        return userService.deleteUser(userUuid);
    }

    @RequestMapping("getByUuid")
    public Result getByUuid(@RequestParam(value = "uuid", required = true) String userUuid) throws Exception {
        return userService.getUserByUuid(userUuid);
    }

    @RequestMapping("updateByUuid")
    public Result updateByUuid(User user) throws Exception {
        return userService.updateUser(user);
    }

    @RequestMapping("getAll")
    public Result getAll() throws Exception {
        List<User> list = userService.getAll();
        if (CollectionUtils.isEmpty(list)) {
            return Result.error("无数据");
        } else {
            return Result.success(list, "查询成功");
        }
    }

    @RequestMapping("get4Page")
    public Result getAll4Page(@RequestParam(value = "page", required = true, defaultValue = "1") Integer page,
                              @RequestParam(value = "pageSize", required = true, defaultValue = "1") Integer pageSize) throws Exception {
        PageHelper.startPage(page, pageSize);
        List<User> list = userService.getAll();
        if (CollectionUtils.isEmpty(list)) {
            return Result.error("无数据");
        } else {
            return Result.success(new PageInfo<User>(list), "查询成功");
        }
    }

    @RequestMapping("login")
    public Result login(@RequestParam(value = "loginUsername", required = true) String loginUsername,
                        @RequestParam(value = "loginPassword", required = true) String loginPassword) throws Exception {
        return userService.login(loginUsername, loginPassword);
    }

    @RequestMapping("logout")
    public Result logout(@RequestParam(value = "loginUsername", required = true) String loginUsername) throws Exception {
        return userService.logout(loginUsername);
    }
}
