package xin.dao.comment;

import xin.entity.comment.firstComment.FirstCommentResponse;
import xin.entity.comment.firstComment.FirstCommnetRequest;

import java.util.List;

/**
 * Created by snowman on 2018/1/25.
 */
public interface CommentMapper {
    //插入评论
    public void addComment(FirstCommnetRequest firstCommnetRequest);

    //查询一级评论(videoId,lastTime,currentPage,amount)
    public List<FirstCommentResponse> inquireComments(FirstCommnetRequest firstCommnetRequest);

    //查询某条一级评论有多少条回复
    public int inquireReplyCnt(int commentId);

    //以commentId精确查询某一条一级评论
    public FirstCommentResponse inquireCommentById(int commentId);

    //删除一级评论
    public void deleteComment(int commentId);
}
