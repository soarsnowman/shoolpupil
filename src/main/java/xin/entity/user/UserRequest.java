package xin.entity.user;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import org.apache.ibatis.jdbc.Null;

/**
 * 用户接收实体类
 * Created by snowman on 2018/1/7.
 */
@ApiModel(value = "UsertRequest")
public class UserRequest {
    @ApiModelProperty(value = "用户类")
    private User user;
    @ApiModelProperty(value = "证书（学生角色不需要）")
    private String certificate = null;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

}
