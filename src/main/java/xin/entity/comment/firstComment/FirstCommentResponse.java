package xin.entity.comment.firstComment;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * 一级评论数据库响应类，单一
 * Created by snowman on 2018/1/25.
 */
@ApiModel(value = "FirstCommentResponse")
public class FirstCommentResponse extends FirstComment{
    @ApiModelProperty(value = "该条评论的ID")
    private int commentId;
    @ApiModelProperty(value = "该条评论发布者的ID")
    private int userId;
    @ApiModelProperty(value = "该条评论创建时间")
    private Date createTime;
    @ApiModelProperty(value = "该条评论发布者的昵称")
    private String userName;
    @ApiModelProperty(value = "该条评论发布者的头像")
    private String picture;
    @ApiModelProperty(value = "该条评论下共有多少条回复")
    private int replyCnt;
    @ApiModelProperty(value = "该条评论是否属于当前用户，1是，0不是")
    private int commentInfo;

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getReplyCnt() {
        return replyCnt;
    }

    public void setReplyCnt(int replyCnt) {
        this.replyCnt = replyCnt;
    }

    public int getCommentInfo() {
        return commentInfo;
    }

    public void setCommentInfo(int commentInfo) {
        this.commentInfo = commentInfo;
    }
}
