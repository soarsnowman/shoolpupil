package xin.service.comment;

import xin.entity.comment.firstComment.FirstCommentResponse;
import xin.entity.comment.firstComment.FirstCommentResponseList;
import xin.entity.comment.firstComment.FirstCommnetRequest;

/**
 * 一级评论服务层
 * Created by snowman on 2018/1/25.
 */
public interface CommentService {
    //插入评论
    public int addComment(FirstCommnetRequest firstCommnetRequest);

    //查询一级评论(videoId,lastTime,currentPage,amount)
    public FirstCommentResponseList inquireComments(FirstCommnetRequest firstCommnetRequest);

    //以commentId精确查询某一条一级评论
    public FirstCommentResponse inquireCommentById(int commentId);

    //删除一级评论
    public void deleteComment(int commentId);
}
