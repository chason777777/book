package com.chason.book.book.service.impl;

import com.chason.book.book.constants.Constants;
import com.chason.book.book.entity.User;
import com.chason.book.book.entity.UserExample;
import com.chason.book.book.repository.UserMapper;
import com.chason.book.book.result.Result;
import com.chason.book.book.service.UserService;
import com.chason.book.book.service.UserService;
import com.chason.book.book.util.DateTimeUtil;
import com.chason.book.book.util.MD5Util;
import com.chason.book.book.util.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by chason on 2018/8/23.11:08
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public Result addUser(User user) throws Exception {
        if (null == user || StringUtils.isEmpty(user.getName())) {
            return Result.error("参数不能为空");
        }

        user.setUuid(UuidUtil.randomUUID());
        user.setUpdateTime(DateTimeUtil.nowDate());
        user.setCreateTime(DateTimeUtil.nowDate());
        if (!StringUtils.isEmpty(user.getLoginPassword())) {
            user.setLoginRealpassword(user.getLoginPassword());
            user.setLoginPassword(MD5Util.getMD5(user.getLoginPassword()));
        }

        boolean result = add(user);
        if (result) {
            return Result.success("保存成功");
        } else {
            return Result.error("保存失败");
        }
    }

    @Override
    public Result deleteUser(String userUuid) throws Exception {
        if (StringUtils.isEmpty(userUuid)) {
            return Result.error("参数不能为空");
        }

        User user = new User();
        user.setUuid(userUuid);
        user.setStatus(Constants.STATUS_DEL);

        boolean result = updateByUuid(user);
        if (result) {
            return Result.success("删除成功");
        } else {
            return Result.error("删除失败");
        }
    }

    @Override
    public Result updateUser(User user) throws Exception {
        if (null == user || StringUtils.isEmpty(user.getUuid())) {
            return Result.error("参数不能为空");
        }

        if (!StringUtils.isEmpty(user.getLoginPassword())) {
            user.setLoginRealpassword(user.getLoginPassword());
            user.setLoginPassword(MD5Util.getMD5(user.getLoginPassword()));
        }

        boolean result = updateByUuid(user);
        if (result) {
            return Result.success("修改成功");
        } else {
            return Result.error("修改失败");
        }
    }

    @Override
    public Result getUserByUuid(String userUuid) throws Exception {
        if (StringUtils.isEmpty(userUuid)) {
            return Result.error("参数不能为空");
        }

        User user = getByUuid(userUuid);
        if (null != user) {
            return Result.success(user, "查询成功");
        } else {
            return Result.error("查询失败");
        }
    }

    @Override
    public List<User> getAll() throws Exception {
        UserExample userExample = new UserExample();
        userExample.setOrderByClause("create_time desc");
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andStatusEqualTo(Constants.STATUS_OK);
        return userMapper.selectByExample(userExample);
    }

    @Override
    public Result login(String loginUsername, String loginPassword) throws Exception {
        if (StringUtils.isEmpty(loginUsername) || StringUtils.isEmpty(loginPassword)) {
            return Result.error("参数不能为空");
        }

        loginPassword = MD5Util.getMD5(loginPassword);

        User user = getByLoginUsernameAndLoginPassword(loginUsername, loginPassword);
        if (null == user) {
            return Result.error("账号或密码错误");
        } else if (user.getStatus() == Constants.STATUS_FROZEN) {
            return Result.error("账号被冻结");
        } else {
            user.setToken(UuidUtil.randomUUID());
            user.setLastLoginTime(DateTimeUtil.nowDate());
            boolean result = updateByUuid(user);
            if (result) {
                return Result.success(user.getToken(), "登录成功");
            } else {
                return Result.error("登录失败");
            }
        }
    }

    @Override
    public Result logout(String loginUsername) throws Exception {
        if (StringUtils.isEmpty(loginUsername)) {
            return Result.error("参数不能为空");
        }

        User user = new User();
        user.setLoginUsername(loginUsername);
        user.setToken("");

        boolean result = updateByLoginUsername(user);
        if (result) {
            return Result.success("登出成功");
        } else {
            return Result.error("登出失败");
        }
    }

    @Override
    public User getByLoginUsername(String loginUsername) throws Exception {
        if (StringUtils.isEmpty(loginUsername)) {
            return null;
        }

        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andLoginUsernameEqualTo(loginUsername).andStatusNotEqualTo(Constants.STATUS_OK);
        List<User> list = userMapper.selectByExample(userExample);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    private boolean add(User user) throws Exception {
        return userMapper.insertSelective(user) > 0;
    }

    private boolean updateByUuid(User user) throws Exception {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUuidEqualTo(user.getUuid());

        user.setUpdateTime(DateTimeUtil.nowDate());
        return userMapper.updateByExampleSelective(user, userExample) > 0;
    }

    private User getByUuid(String userUuid) throws Exception {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUuidEqualTo(userUuid);

        List<User> list = userMapper.selectByExample(userExample);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    private User getByLoginUsernameAndLoginPassword(String loginUsername, String loginPassword) throws Exception {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andLoginUsernameEqualTo(loginUsername).andLoginPasswordEqualTo(loginPassword).andStatusNotEqualTo(Constants.STATUS_DEL);
        List<User> list = userMapper.selectByExample(userExample);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    private boolean updateByLoginUsername(User user) throws Exception {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andLoginUsernameEqualTo(user.getLoginUsername());

        user.setUpdateTime(DateTimeUtil.nowDate());
        return userMapper.updateByExampleSelective(user, userExample) > 0;
    }
}
