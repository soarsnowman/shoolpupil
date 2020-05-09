package xin.dao.user;

import xin.entity.user.UserRequest;
import xin.entity.user.UserResponse;
import xin.entity.user.modifyInfo.ModifyInfo;

/**
 * Created by snowman on 2018/1/7.
 */
public interface UserMapper {
    //添加用户insertUser
    public void insertUser(UserRequest userRequest);

    //检测账号是否已经存在
    public int checkExist(String account);

    //查询用户（登录）（账号）
    public UserResponse inquireUserByAccount(String account);

    //修改用户信息（用户名/密码/头像）
    public void modifyInfo(ModifyInfo modifyInfo);
}
