package xin.serviceImpl.comment;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xin.dao.comment.CommentMapper;
import xin.entity.comment.firstComment.FirstCommentResponse;
import xin.entity.comment.firstComment.FirstCommentResponseList;
import xin.entity.comment.firstComment.FirstCommnetRequest;
import xin.service.comment.CommentService;

import java.util.Date;
import java.util.List;

/**
 * 一级评论服务层
 * Created by snowman on 2018/1/25.
 */
@Service(value = "commentService")
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    //头像文件夹名称
    private final String IMAGES = "images/";

    //http路径
    private final String HTTPPATH = "http://www.flyingsnowman.xin/schoolpupil/";

    /******************插入评论********************/
    public int addComment(FirstCommnetRequest firstCommnetRequest){
        commentMapper.addComment(firstCommnetRequest);
        //返回主键（评论ID）
        return firstCommnetRequest.getCommentId();
    }

    /****************查询一级评论(videoId,lastTime,currentPage,amount)*********************/
    public FirstCommentResponseList inquireComments(FirstCommnetRequest firstCommnetRequest){
        FirstCommentResponseList firstCommentResponseList = new FirstCommentResponseList();
        //获得上次访问时间
        Session session = SecurityUtils.getSubject().getSession();
        //第一页则加载最新并储存访问时间，为下次访问做准备；否则提取上次访问时间
        if(firstCommnetRequest.getCurrentPage()==1){
            session.setAttribute("lastTime",new Date());
        }
        firstCommnetRequest.setLastTime((Date) session.getAttribute("lastTime"));

        //设置从第几条记录开始查询
        firstCommnetRequest.setCurrentPage((firstCommnetRequest.getCurrentPage()-1)*firstCommnetRequest.getAmount());

        List<FirstCommentResponse> list = commentMapper.inquireComments(firstCommnetRequest);

        //获得评论的回复数量以及添加头像的HTTP路径
        if (list.size()!=0){
            for (FirstCommentResponse x:list){
                x.setPicture(HTTPPATH + IMAGES + x.getPicture());
                x.setReplyCnt(commentMapper.inquireReplyCnt(x.getCommentId()));
            }
        }
        firstCommentResponseList.setFirstCommentResponseList(list);
        firstCommentResponseList.setListSize(list.size());
        return firstCommentResponseList;
    }

    /*************以commentId精确查询某一条一级评论**************/
    public FirstCommentResponse inquireCommentById(int commentId){
        FirstCommentResponse firstCommentResponse = commentMapper.inquireCommentById(commentId);
        ////获得评论的回复数量以及添加头像的HTTP路径
        if (firstCommentResponse!=null){
            firstCommentResponse.setPicture(HTTPPATH + IMAGES + firstCommentResponse.getPicture());
            firstCommentResponse.setReplyCnt(commentMapper.inquireReplyCnt(commentId));
        }
        return firstCommentResponse;
    }

    /*******************删除一级评论，同时删除底下的回复*****************/
    public void deleteComment(int commentId){
        commentMapper.deleteComment(commentId);
    }
}
