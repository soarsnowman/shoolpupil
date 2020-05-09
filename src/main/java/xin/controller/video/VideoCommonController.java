package xin.controller.video;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import xin.controller.user.UserController;
import xin.entity.ResponseInfo;
import xin.entity.user.UserResponse;
import xin.entity.video.Video;
import xin.entity.video.VideoRequest;
import xin.entity.video.VideoResponse;
import xin.entity.video.VideoResponseList;
import xin.service.video.VideoCommonService;
import xin.util.VideoUploadTool;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 共有视频模块
 * Created by snowman on 2018/1/23.
 */
@Controller
@RequestMapping(value = "/videoCommon")
@Api(value = "共有视频模块",description = "角色表示：1student（学生）,2teacher（教师）,3community（社团）,4department（院系）,5enterprise（企业）,6VIP（会员）")
public class VideoCommonController {

    @Autowired
    private VideoCommonService videoCommonService;

    @Autowired
    private UserController userController;

    @Resource(name = "videoUploadTool")
    private VideoUploadTool videoUploadTool;

    /***********************上传视频文件******************************/
    @ApiOperation(value = "上传视频文件",notes = "认证：需要。权限：123456。",httpMethod = "POST",response = ResponseInfo.class)
    @RequestMapping("/uploadVideo")
    @ResponseBody
    public ResponseInfo uploadVideo(
            @ModelAttribute Video videoMessage,
            @ApiParam(name = "video",value = "视频文件",required = true) @RequestParam(value = "video") MultipartFile video
    ){
        ResponseInfo responseInfo = new ResponseInfo();
        responseInfo.setInfo(1);

        //存进磁盘，转码，返回转码后视频相对地址，封面相对地址，转码成功信号量的map
        Map map = videoUploadTool.createFile(video);

        //转码操作结果信号量。0空，1大小不符，2类型不服，3成功
        int bflag = (Integer) map.get("bflag");
        //转码错误就返回（012）
        if(bflag==0 || bflag==1 || bflag==2){
            responseInfo.setInfo(1);
            return responseInfo;
        }

        //成功3
        //得到视频路径和封面路径
        VideoRequest videoRequest = (VideoRequest) map.get("videoRequest");

        //获取视频title,简介，类别
        videoRequest.setVideo(videoMessage);
        //得到当前用户id,address
        UserResponse userResponse = userController.gain();
        videoRequest.setCurrentUserId(userResponse.getId());
        videoRequest.setAddress(userResponse.getAddress());

        // 已有用户id,address,title,category,synopsis,videoPath,imgPath。
        //上传至数据库
        videoCommonService.uploadVideo(videoRequest);
        return responseInfo;
    }


    /******************通过videoId查询视频********************/
    @ApiOperation(value = "通过videoId查询视频",notes = "认证：不需要。权限：无。",httpMethod = "POST",response = VideoResponse.class)
    @RequestMapping(value = "/searchVideosByVideoId")
    @ResponseBody
    public VideoResponse searchVideosByVideoId(
            @ApiParam(name = "videoId",value = "视频的id",required = true) @RequestParam(value = "videoId") int videoId
    ){
        VideoRequest videoRequest = new VideoRequest();
        //默认当前用户ID为0，即游客
        videoRequest.setCurrentUserId(0);
        //若已登录则获取当前用户ID
        if(SecurityUtils.getSubject().getPrincipal()!=null){
            UserResponse userResponse = userController.gain();
            videoRequest.setCurrentUserId(userResponse.getId());
        }
        videoRequest.setVideoId(videoId);
        VideoResponse videoResponse = videoCommonService.searchVideosByVideoId(videoRequest);
        return videoResponse;
    }


    /***********************个人视频******************************/
    @ApiOperation(value = "个人视频",notes = "认证：需要。权限：123456。",httpMethod = "POST",response = VideoResponseList.class)
    @RequestMapping(value = "/personalVideos")
    @ResponseBody
    public VideoResponseList personalVideos(
            @ApiParam(name = "currentPage",value = "请求页面序数（第几页），默认为第一页",required = false) @RequestParam(value = "currentPage",required = false,defaultValue = "1") int currentPage,
            @ApiParam(name = "amount",value = "请求视频个数",required = true) @RequestParam(value = "amount") int amount,
            @ApiParam(name = "category",value = "视频类别,不传该参数则部分类别查询全部",required = false) @RequestParam(value = "category",required = false,defaultValue = "") String category
    ){
        VideoRequest videoRequest = new VideoRequest();
        Video video = new Video();
        UserResponse userResponse = userController.gain();
        //获取当前用户ID
        videoRequest.setCurrentUserId(userResponse.getId());

        videoRequest.setCurrentPage(currentPage);
        videoRequest.setAmount(amount);
        videoRequest.setVideo(video);
        videoRequest.getVideo().setCategory(category);

        VideoResponseList videoResponseList = videoCommonService.personalVideos(videoRequest);
        return videoResponseList;
    }


    /***********************视频点赞****************************/
    @ApiOperation(value = "视频点赞",notes = "认证：需要。权限：123456。",httpMethod = "POST",response = ResponseInfo.class)
    @RequestMapping(value = "/addLike")
    @ResponseBody
    public ResponseInfo addLike(
            @ApiParam(name = "videoId",value = "视频的id",required = true) @RequestParam(value = "videoId") int videoId
    ){
        ResponseInfo responseInfo = new ResponseInfo();

        VideoRequest videoRequest = new VideoRequest();
        //获得当前用户ID
        UserResponse userResponse = userController.gain();
        videoRequest.setCurrentUserId(userResponse.getId());
        //获取被点赞视频id
        videoRequest.setVideoId(videoId);
        //查询是否已点赞过，是则返回2失败
        if (videoCommonService.inquireLiked(videoRequest)==1){
            responseInfo.setInfo(2);
            return responseInfo;
        }
        //没点赞过则进行点赞
        videoCommonService.addLike(videoRequest);
        responseInfo.setInfo(1);
        return responseInfo;
    }

    /***********************视频举报****************************/
    @ApiOperation(value = "视频举报",notes = "认证：需要。权限：123456。",httpMethod = "POST",response = ResponseInfo.class)
    @RequestMapping(value = "/addReport")
    @ResponseBody
    public ResponseInfo addReport(
            @ApiParam(name = "videoId",value = "视频的id",required = true) @RequestParam(value = "videoId") int videoId
    ){
        ResponseInfo responseInfo = new ResponseInfo();
        VideoRequest videoRequest = new VideoRequest();

        //获得当前用户ID
        UserResponse userResponse = userController.gain();
        videoRequest.setCurrentUserId(userResponse.getId());
        //获取被举报视频id
        videoRequest.setVideoId(videoId);
        //查询是否已举报过，是则返回2失败
        if (videoCommonService.inquireRepoted(videoRequest)==1){
            responseInfo.setInfo(2);
            return responseInfo;
        }
        //没举报过则进行点赞
        videoCommonService.addReport(videoRequest);
        responseInfo.setInfo(1);
        return responseInfo;
    }

    /***********************增加浏览量****************************/
    @ApiOperation(value = "增加浏览量",notes = "认证：不需要。权限：无。",httpMethod = "POST",response = ResponseInfo.class)
    @RequestMapping(value = "/addBrowse")
    @ResponseBody
    public void addBrowse(
            @ApiParam(name = "videoId",value = "视频的id",required = true) @RequestParam(value = "videoId") int videoId
    ){
        ResponseInfo responseInfo = new ResponseInfo();
        VideoRequest videoRequest = new VideoRequest();

        //默认当前用户ID为0，即游客
        videoRequest.setCurrentUserId(0);
        //若已登录则获取当前用户ID
        if(SecurityUtils.getSubject().getPrincipal()!=null){
            UserResponse userResponse = userController.gain();
            videoRequest.setCurrentUserId(userResponse.getId());
        }
        //获取被点赞视频id
        videoRequest.setVideoId(videoId);

        videoCommonService.addBrowse(videoRequest);
        responseInfo.setInfo(1);
        return;
    }


    /**************删除视频***********************/
    @ApiOperation(value = "删除视频",notes = "认证：需要。权限：123456。",httpMethod = "POST",response = ResponseInfo.class)
    @RequestMapping("/deleteVideo")
    @ResponseBody
    public ResponseInfo deleteVideo(
            @ApiParam(name = "videoId",value = "视频的id",required = true) @RequestParam(value = "videoId") int videoId,
            @ApiParam(name = "userId",value = "视频发布者的id，不是当前用户的id",required = true) @RequestParam(value = "userId") int userId
    ){
        ResponseInfo responseInfo = new ResponseInfo();
        responseInfo.setInfo(1);
        //获得当前用户ID
        int currentUserId = userController.gain().getId();
        //判断不是本人删除直接返回失败信息
        if (currentUserId != userId){
            responseInfo.setInfo(2);
            return responseInfo;
        }

        //调用删除接口，删除数据库和磁盘上的信息
        videoCommonService.deleteVideo(videoId);
        return responseInfo;
    }

}
