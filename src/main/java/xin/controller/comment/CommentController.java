package xin.controller.comment;

/**
 * 一级评论控制层
 * Created by snowman on 2018/1/25.
 */

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
import xin.entity.comment.firstComment.FirstComment;
import xin.entity.comment.firstComment.FirstCommentResponse;
import xin.entity.comment.firstComment.FirstCommentResponseList;
import xin.entity.comment.firstComment.FirstCommnetRequest;
import xin.entity.user.UserResponse;
import xin.service.comment.CommentService;

@Controller
@RequestMapping(value = "/comment")
@Api(value = "一级评论模块")
public class CommentController {

    @Autowired
    private UserController userController;

    @Autowired
    private CommentService commentService;

    /***********************插入评论***************************/
    //传视频id和评论内容
    @ApiOperation(value = "对视频进行评论",notes = "认证：需要。权限：123456。返回评论的ID(0为失败)，",response = IdInfo.class,httpMethod = "POST")
    @RequestMapping(value = "/addComment",method = RequestMethod.POST)
    @ResponseBody
    public IdInfo addComment(@ModelAttribute FirstComment firstComment) {
        IdInfo idInfo = new IdInfo();
        //获取视频ID和评论内容
        FirstCommnetRequest firstCommnetRequest = new FirstCommnetRequest();
        firstCommnetRequest.setFirstComment(firstComment);
        //获取当前用户ID
        UserResponse userResponse = userController.gain();
        firstCommnetRequest.setUserId(userResponse.getId());

        //插入评论，获得该条评论的ID
        int commentId = commentService.addComment(firstCommnetRequest);

        idInfo.setInfo(commentId!=0 ? 1 : 0);
        idInfo.setId(commentId!=0 ? commentId : 0);

        return idInfo;
    }

    /***********************查询一级评论***************************/
    //传被评论视频id，请求页数序数，页面记录数
    @RequestMapping(value = "/inquireComments", method = RequestMethod.POST)
    @ApiOperation(value = "查询一级评论",notes = "认证：不需要。权限：无。查询一级评论",httpMethod = "POST",response = FirstCommentResponseList.class)
    @ResponseBody
    public FirstCommentResponseList inquireComments(
            @ApiParam(name = "videoId", value = "被评论视频id", required = true) @RequestParam ("videoId") int videoId,
            @ApiParam(name = "currentPage",value = "请求页面序数（第几页），默认为第一页",required = false) @RequestParam(value = "currentPage",required = false,defaultValue = "1") int currentPage,
            @ApiParam(name = "amount",value = "请求视频个数",required = true) @RequestParam(value = "amount") int amount
    ){

        FirstCommnetRequest firstCommnetRequest = new FirstCommnetRequest();
        firstCommnetRequest.setFirstComment(new FirstComment());
        //被评论视频id，请求页数序数，页面记录数
        firstCommnetRequest.getFirstComment().setVideoId(videoId);
        firstCommnetRequest.setCurrentPage(currentPage);
        firstCommnetRequest.setAmount(amount);

        FirstCommentResponseList firstCommentResponseList = commentService.inquireComments(firstCommnetRequest);

        //已认证则判断是否是当前用户发表的评论
        if(SecurityUtils.getSubject().getPrincipal()!=null) {
            //若已登录则获取当前用户ID
            int currentUserId = userController.gain().getId();
            for (FirstCommentResponse x : firstCommentResponseList.getFirstCommentResponseList()) {
                x.setCommentInfo(x.getUserId() == currentUserId ? 1 : 0);
            }
        }
        return firstCommentResponseList;
    }

    /***********************以commentId精确查询某一条一级评论***************************/
    @RequestMapping(value = "/inquireCommentById",method = RequestMethod.POST)
    @ApiOperation(value = "查询某一条一级评论",httpMethod = "POST",notes = "认证：不需要。权限：无。查询一级评论。以commentId精确查询某一条一级评论",response = FirstCommentResponse.class)
    @ResponseBody
    public FirstCommentResponse inquireCommentById(
            @ApiParam(name = "commentId", value = "一级评论id", required = true) @RequestParam("commentId") int commentId
    ){
        FirstCommentResponse firstCommentResponse = commentService.inquireCommentById(commentId);

        //已认证则判断是否是当前用户发表的评论
        if(SecurityUtils.getSubject().getPrincipal()!=null) {
            //若已登录则获取当前用户ID
            int currentUserId = userController.gain().getId();
            firstCommentResponse.setCommentInfo(firstCommentResponse.getUserId() == currentUserId ? 1 : 0);
        }
        return firstCommentResponse;
    }

    /***********************删除一级评论，同时删除底下的回复**************************/
    @RequestMapping(value = "/deleteComment",method = RequestMethod.POST)
    @ApiOperation(value = "删除评论",httpMethod = "POST",notes = "认证：需要。权限：123546。以一级评论id删除该条一级评论和底下的回复。",response = ResponseInfo.class)
    @ResponseBody
    public ResponseInfo deleteComment(
            @ApiParam(name = "commentId", value = "一级评论id") @RequestParam("commentId") int commentId
    ){
        ResponseInfo responseInfo = new ResponseInfo();
        responseInfo.setInfo(2);

        //获取该条评论的发布者id
        int userId = this.inquireCommentById(commentId).getUserId();
        //获取当前用户ID
        int currentUserId = userController.gain().getId();

        //判断是否删除的是自己的评论
        if (userId==currentUserId){
            commentService.deleteComment(commentId);
            responseInfo.setInfo(1);
        }

        return responseInfo;
    }

}
