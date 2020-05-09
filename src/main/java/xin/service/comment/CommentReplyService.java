package xin.service.comment;

import xin.entity.comment.reply.ReplyRequest;
import xin.entity.comment.reply.ReplyResponse;
import xin.entity.comment.reply.ReplyResponseList;


/**
 * “回复”服务层
 * Created by snowman on 2018/1/25.
 */
public interface CommentReplyService {
    //插入回复，返回自增主键“回复”id
    public int addReply(ReplyRequest replyRequest);

    //查询回复,没有被回复者昵称
    public ReplyResponseList inquireReplys(ReplyRequest replyRequest);

    //以replyId精确查询某一条一级评论
    public ReplyResponse inquireReplyById(int replyId);

    //删除回复
    public void deleteReply(int replyId);
}
