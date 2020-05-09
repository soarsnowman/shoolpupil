package xin.entity.user;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 用户响应实体类
 * Created by snowman on 2018/1/7.
 */
@Service
@ApiModel(value = "UserResponse")
public class UserResponse extends User {
    @ApiModelProperty(value="数据库标识用户的ID")
    private int id;
    @ApiModelProperty(value="注册时间")
    private Date createTime;
    @ApiModelProperty(value="头像")
    private String picture;
    @ApiModelProperty(value = "角色")
    private String role;
    @ApiModelProperty(value = "登录信号量。1成功；2失败，账号或密码错误；3失败，该账号还在审核中；4失败，该账号封禁中")
    private String info = "1";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
