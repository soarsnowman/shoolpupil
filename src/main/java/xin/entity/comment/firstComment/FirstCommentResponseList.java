package xin.entity.comment.firstComment;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 一级评论最终响应类，含有多个一级评论数据库响应类(list)
 * Created by snowman on 2018/1/25.
 */
@ApiModel(value = "FirstCommentResponseList")
public class FirstCommentResponseList {
    @ApiModelProperty(value = "List集合")
    private List<FirstCommentResponse> firstCommentResponseList;
    @ApiModelProperty(value = "该一级评论集合有多少里边条评论")
    private int listSize;

    public List<FirstCommentResponse> getFirstCommentResponseList() {
        return firstCommentResponseList;
    }

    public void setFirstCommentResponseList(List<FirstCommentResponse> firstCommentResponseList) {
        this.firstCommentResponseList = firstCommentResponseList;
    }

    public int getListSize() {
        return listSize;
    }

    public void setListSize(int listSize) {
        this.listSize = listSize;
    }
}
