package xin.entity.comment.firstComment;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * 一级评论请求类
 * Created by snowman on 2018/1/25.
 */
@ApiModel(value = "FirstCommnetRequest")
public class FirstCommnetRequest {
    @ApiModelProperty(value = "FirstCommnet")
    private FirstComment firstComment;
    @ApiModelProperty(value = "一级评论ID")
    private int commentId;
    @ApiModelProperty(value = "评论者ID")
    private int userId;
    @ApiModelProperty(value = "加载最新评论的时间")
    private Date lastTime;
    @ApiModelProperty(value = "请求的当前页")
    private int currentPage;
    @ApiModelProperty(value = "每页记录数")
    private int amount;

    public FirstComment getFirstComment() {
        return firstComment;
    }

    public void setFirstComment(FirstComment firstComment) {
        this.firstComment = firstComment;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
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
}
