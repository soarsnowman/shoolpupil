package xin.entity.user.modifyInfo;


import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * 修改用户名或密码或头像
 * Created by snowman on 2018/1/7.
 */
@ApiModel(value = "ModifyInfo")
public class ModifyInfo {
    @ApiModelProperty(value = "信号量。1新用户名，2新密码，3新头像")
    private int whichOne;
    @ApiModelProperty(value = "信息")
    private String information;
    @ApiModelProperty(value = "当前用户账号")
    private String account;

    public int getWhichOne() {
        return whichOne;
    }

    public void setWhichOne(int whichOne) {
        this.whichOne = whichOne;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
