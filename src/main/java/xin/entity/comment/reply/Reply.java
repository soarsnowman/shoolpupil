package xin.entity.comment.reply;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * "回复"基础类
 * Created by snowman on 2018/1/25.
 */
@ApiModel(value = "Reply")
public class Reply {
    @ApiModelProperty(value = "一级评论id",required = true)
    private int commentId;
    @ApiModelProperty(value = "目标用户id（被回复者id）",required = true)
    private int aimUserId;
    @ApiModelProperty(value = "“回复”内容",required = true)
    private String replyContent;

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getAimUserId() {
        return aimUserId;
    }

    public void setAimUserId(int aimUserId) {
        this.aimUserId = aimUserId;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }
}
