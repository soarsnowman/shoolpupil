package xin.entity.comment.reply;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * "回复"最终响应类，含有多个一级评论数据库响应类(list)
 * Created by snowman on 2018/1/27.
 */
@ApiModel
public class ReplyResponseList {
    @ApiModelProperty(value = "回复的list集合")
    private List<ReplyResponse> replyResponse;
    @ApiModelProperty(value = "该回复集合有多少里边条回复")
    private int listSize;

    public List<ReplyResponse> getReplyResponse() {
        return replyResponse;
    }

    public void setReplyResponse(List<ReplyResponse> replyResponse) {
        this.replyResponse = replyResponse;
    }

    public int getListSize() {
        return listSize;
    }

    public void setListSize(int listSize) {
        this.listSize = listSize;
    }
}
