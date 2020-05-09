package xin.entity.user;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * 用户基本类
 * Created by snowman on 2018/1/7.
 */
@ApiModel(value = "User")
public class User {
    @ApiModelProperty(value = "角色id。角色代号。1学生，2教师，3社团，4院系，5企业",required = true)
    private int roleId;
    @ApiModelProperty(value="地址。企业为地址，其他为学校",required = true)
    private String address;
    @ApiModelProperty(value="账号。学生为手机号码，其他角色为邮箱",required = true)
    private String account;
    @ApiModelProperty(value="用户名",required = true)
    private String userName;
    @ApiModelProperty(value="密码",required = true)
    private String password;

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
