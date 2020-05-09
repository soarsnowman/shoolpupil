package xin.entity.comment.reply;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * “回复”数据库响应类，单一
 * Created by snowman on 2018/1/27.
 */
@ApiModel
public class ReplyResponse extends Reply {
    @ApiModelProperty(value = "该条回复的id ")
    private int replyId;
    @ApiModelProperty(value = "回复者的id ")
    private int userId;
    @ApiModelProperty(value = "被回复者昵称")
    private String aimUserName;
    @ApiModelProperty(value = "回复者昵称")
    private String userName;
    @ApiModelProperty(value = "该条回复创建时间")
    private Date createTime;
    @ApiModelProperty(value = "该条回复是否属于当前用户，1是，0不是")
    private int replyInfo;

    public int getReplyId() {
        return replyId;
    }

    public void setReplyId(int replyId) {
        this.replyId = replyId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAimUserName() {
        return aimUserName;
    }

    public void setAimUserName(String aimUserName) {
        this.aimUserName = aimUserName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getReplyInfo() {
        return replyInfo;
    }

    public void setReplyInfo(int replyInfo) {
        this.replyInfo = replyInfo;
    }
}
