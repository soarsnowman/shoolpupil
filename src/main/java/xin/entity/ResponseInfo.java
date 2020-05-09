package xin.entity;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import org.springframework.stereotype.Service;

/**
 * 返回信号量
 * Created by snowman on 2018/1/7.
 */
@Service("responseInfo")
@ApiModel(value = "ResponseInfo")
public class ResponseInfo {
    @ApiModelProperty(value = "信号量。1：成功/可以/允许，2：失败/不可以/不允许，3:没有权限，0：未登录")
    private int info;

    public int getInfo() {
        return info;
    }

    public void setInfo(int info) {
        this.info = info;
    }
}
