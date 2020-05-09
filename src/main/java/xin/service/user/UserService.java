package xin.service.user;

import org.springframework.web.multipart.MultipartFile;
import xin.entity.user.User;
import xin.entity.user.UserResponse;
import xin.entity.user.modifyInfo.ModifyInfo;

import java.io.IOException;

/**
 * Created by snowman on 2018/1/7.
 */
public interface UserService {
    //添加用户insertUser
    public void insertUser(User user,MultipartFile certificate) throws IOException;

    //检测账号是否已经存在
    public int checkExist(String account);

    //查询用户（登录）（账号）
    public UserResponse inquireUserByAccount(String account);

    //修改用户信息（用户名/密码/头像）
    public void modifyInfo(ModifyInfo modifyInfo,MultipartFile picture) throws IOException;

    //获得项目绝对路径
    public String getProjectPath();
}
