package xin.entity.comment;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * 返回id
 * Created by snowman on 2018/1/27.
 */
@ApiModel(value = "IdInfo")
public class IdInfo {
    @ApiModelProperty(value = "信号量。0：失败，1：成功")
    private int info;
    @ApiModelProperty(value = "评论/回复的id")
    private int id;

    public int getInfo() {
        return info;
    }

    public void setInfo(int info) {
        this.info = info;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
