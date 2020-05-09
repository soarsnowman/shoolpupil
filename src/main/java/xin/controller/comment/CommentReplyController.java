package xin.controller.comment;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import xin.controller.user.UserController;
import xin.entity.ResponseInfo;
import xin.entity.comment.IdInfo;
import xin.entity.comment.reply.Reply;
import xin.entity.comment.reply.ReplyRequest;
import xin.entity.comment.reply.ReplyResponse;
import xin.entity.comment.reply.ReplyResponseList;
import xin.entity.user.UserResponse;
import xin.service.comment.CommentReplyService;

/**
 * "回复"控制层
 * Created by snowman on 2018/1/25.
 */
@Controller
@RequestMapping(value = "/commentReply")
@Api(value = "评论回复模块")
public class CommentReplyController {

    @Autowired
    private UserController userController;

    @Autowired
    private CommentReplyService commentReplyService;

    /***********************对评论/回复进行回复***************************/
    //传一级评论id、被回复者id和回复内容
    @ApiOperation(value = "对评论/回复进行回复",notes = "认证：需要。权限：123456。返回该条回复的ID",response = IdInfo.class,httpMethod = "POST")
    @RequestMapping(value = "/addReply",method = RequestMethod.POST)
    @ResponseBody
    public IdInfo addReply(@ModelAttribute Reply reply) {
        IdInfo idInfo = new IdInfo();
        //获取一级评论的ID、被回复者ID和评论内容
        ReplyRequest replyRequest = new ReplyRequest();
        replyRequest.setReply(reply);
        //获取当前用户ID
        UserResponse userResponse = userController.gain();
        replyRequest.setUserId(userResponse.getId());

        //插入评论，获得该条评论的ID
        int replyId = commentReplyService.addReply(replyRequest);

        idInfo.setInfo(replyId!=0 ? 1 : 0);
        idInfo.setId(replyId!=0 ? replyId : 0);

        return idInfo;
    }

    /***********************查询回复***************************/
    //传一级评论id，请求页数序数，页面记录数
    @ApiOperation(value = "查询回复",notes = "认证：不需要。权限：0。",response = ReplyResponseList.class,httpMethod = "POST")
    @RequestMapping(value = "/inquireReplys",method = RequestMethod.POST)
    @ResponseBody
    public ReplyResponseList inquireReplys(
            @ApiParam(name = "commentId", value = "一级评论id", required = true) @RequestParam("commentId") int commentId,
            @ApiParam(name = "currentPage",value = "请求页面序数（第几页），默认为第一页",required = false) @RequestParam(value = "currentPage",required = false,defaultValue = "1") int currentPage,
            @ApiParam(name = "amount",value = "请求视频个数",required = true) @RequestParam(value = "amount") int amount
    ){
        ReplyRequest replyRequest = new ReplyRequest();
        replyRequest.setReply(new Reply());

        //默认当前用户ID为0，即游客
        replyRequest.setCurrentUserId(0);
        //若已登录则获取当前用户ID
        if(SecurityUtils.getSubject().getPrincipal()!=null){
            UserResponse userResponse = userController.gain();
            replyRequest.setCurrentUserId(userResponse.getId());
        }

        //commentId,currentPage,amount
        replyRequest.setCurrentPage(currentPage);
        replyRequest.setAmount(amount);
        replyRequest.getReply().setCommentId(commentId);

        ReplyResponseList replyResponseList = commentReplyService.inquireReplys(replyRequest);
        return replyResponseList;
    }

    /****************以replyId精确查询某一条回复********************/
    @RequestMapping(value = "/inquireReplyById",method = RequestMethod.POST)
    @ApiOperation(value = "查询某一条“回复”",httpMethod = "POST",notes = "认证：不需要。权限：无。以replyId精确查询某一条回复",response = ReplyResponse.class)
    @ResponseBody
    public ReplyResponse inquireReplyById(
            @ApiParam(name = "replyId", value = "“回复”的id", required = true) @RequestParam("replyId") int replyId
    ){
        ReplyResponse replyResponse = commentReplyService.inquireReplyById(replyId);

        //已认证则判断是否是当前用户发表的评论
        if(SecurityUtils.getSubject().getPrincipal()!=null) {
            //若已登录则获取当前用户ID
            int currentUserId = userController.gain().getId();
            replyResponse.setReplyInfo(replyResponse.getUserId() == currentUserId ? 1 : 0);
        }
        return replyResponse;
    }

    /***********************删除回复**************************/
    @RequestMapping(value = "/deleteReply",method = RequestMethod.POST)
    @ApiOperation(value = "删除回复",httpMethod = "POST",notes = "认证：需要。权限：123546。以“回复”id删除该条回复。",response = ResponseInfo.class)
    @ResponseBody
    public ResponseInfo deleteReply(
            @ApiParam(name = "replyId", value = "回复id",required = true) @RequestParam("replyId") int replyId
    ){
        ResponseInfo responseInfo = new ResponseInfo();
        responseInfo.setInfo(2);

        //获取该条评论的发布者id
        int userId = this.inquireReplyById(replyId).getUserId();
        //获取当前用户ID
        int currentUserId = userController.gain().getId();

        //判断是否删除的是自己的评论
        if (userId==currentUserId){
            commentReplyService.deleteReply(replyId);
            responseInfo.setInfo(1);
        }

        return responseInfo;
    }

}
