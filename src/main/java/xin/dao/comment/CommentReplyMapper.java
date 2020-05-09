package xin.dao.comment;

import xin.entity.comment.reply.ReplyRequest;
import xin.entity.comment.reply.ReplyResponse;

import java.util.List;

/**
 * Created by snowman on 2018/1/25.
 */
public interface CommentReplyMapper {
    //插入回复，返回自增主键“回复”id
    public void addReply(ReplyRequest replyRequest);

    //查询回复,没有被回复者昵称
    public List<ReplyResponse> inquireReplys(ReplyRequest replyRequest);

    //查询被回复者昵称
    public String inquireReplyAimUserName(int aimUserId);

    //以replyId精确查询某一条一级评论
    public ReplyResponse inquireReplyById(int replyId);

    //删除回复
    public void deleteReply(int replyId);
}
