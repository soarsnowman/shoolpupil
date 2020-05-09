package xin.entity.comment.firstComment;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * 一级评论基础类
 * Created by snowman on 2018/1/25.
 */
@ApiModel(value = "FirstComment")
public class FirstComment {
    @ApiModelProperty(value = "被评论视频ID",required = true)
    private int videoId;
    @ApiModelProperty(value = "评论内容",required = true)
    private String commentContent;

    public int getVideoId() {
        return videoId;
    }

    public void setVideoId(int videoId) {
        this.videoId = videoId;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }
}
