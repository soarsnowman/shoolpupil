package xin.entity.comment.reply;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * “回复”请求类
 * Created by snowman on 2018/1/26.
 */
@ApiModel(value = "ReplyRequest")
public class ReplyRequest {
    @ApiModelProperty(value = "Reply")
    private Reply reply;
    @ApiModelProperty(value = "该条“回复”ID")
    private int replyId;
    @ApiModelProperty(value = "回复者ID")
    private int userId;
    @ApiModelProperty(value = "上次加载最新评论的时间")
    private Date lastTime;
    @ApiModelProperty(value = "请求的当前页")
    private int currentPage;
    @ApiModelProperty(value = "每页记录数")
    private int amount;

    @ApiModelProperty(value = "当前用户ID")
    private int currentUserId;

    public Reply getReply() {
        return reply;
    }

    public void setReply(Reply reply) {
        this.reply = reply;
    }

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

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(int currentUserId) {
        this.currentUserId = currentUserId;
    }
}
