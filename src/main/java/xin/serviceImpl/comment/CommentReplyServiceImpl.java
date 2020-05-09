package xin.serviceImpl.comment;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xin.dao.comment.CommentReplyMapper;
import xin.entity.comment.reply.ReplyRequest;
import xin.entity.comment.reply.ReplyResponse;
import xin.entity.comment.reply.ReplyResponseList;
import xin.service.comment.CommentReplyService;

import java.util.Date;
import java.util.List;

/**
 * “回复”服务层
 * Created by snowman on 2018/1/25.
 */
@Service(value = "commentReplyService")
public class CommentReplyServiceImpl implements CommentReplyService {

    @Autowired
    private CommentReplyMapper commentReplyMapper;


    /***********插入回复，返回自增主键“回复”id***********/
    public int addReply(ReplyRequest replyRequest){
        commentReplyMapper.addReply(replyRequest);
        return replyRequest.getReplyId();
    }

    /***************查询回复,没有被回复者昵称*******************/
    public ReplyResponseList inquireReplys(ReplyRequest replyRequest){
        ReplyResponseList replyResponseList = new ReplyResponseList();
        //获得上次访问时间
        Session session = SecurityUtils.getSubject().getSession();
        //第一页则加载最新并储存访问时间，为下次访问做准备；否则提取上次访问时间
        if(replyRequest.getCurrentPage()==1){
            session.setAttribute("lastTime",new Date());
        }
        replyRequest.setLastTime((Date) session.getAttribute("lastTime"));

        //设置从第几条记录开始查询
        replyRequest.setCurrentPage((replyRequest.getCurrentPage()-1)*replyRequest.getAmount());

        //查询数据库
        List<ReplyResponse> list =  commentReplyMapper.inquireReplys(replyRequest);

        //加被回复者昵称,回复是否为当前用户发布
        if (list.size()!=0){
            for (ReplyResponse x:list){
                x.setAimUserName(commentReplyMapper.inquireReplyAimUserName(x.getAimUserId()));
                x.setReplyInfo(x.getUserId() == replyRequest.getCurrentUserId() ? 1 : 0);
            }
            //获取list集合
            replyResponseList.setReplyResponse(list);
        }
        //获取list大小
        replyResponseList.setListSize(list.size());

        return replyResponseList;
    }

    /***************以replyId精确查询某一条一级评论*******************/
    public ReplyResponse inquireReplyById(int replyId){
        ReplyResponse replyResponse = commentReplyMapper.inquireReplyById(replyId);
        //加被回复者昵称
        replyResponse.setAimUserName(commentReplyMapper.inquireReplyAimUserName(replyId));
        return replyResponse;
    }

    /***************删除回复*****************/
    public void deleteReply(int replyId){
        commentReplyMapper.deleteReply(replyId);
    }
}
