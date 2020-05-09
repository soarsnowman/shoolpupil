package xin.controller.video;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import xin.controller.user.UserController;
import xin.entity.user.UserResponse;
import xin.entity.video.VideoRequest;
import xin.entity.video.VideoResponseList;
import xin.service.video.VideoEnterpiseService;

/**
 * 企业角色的视频模块控制层
 * Created by snowman on 2018/1/20.
 */
@Controller
@RequestMapping(value = "/videoEnterprise")
@Api(value = "企业角色视频模块")
public class VideoEnterpriseController {

    @Autowired
    private UserController userController;

    @Autowired
    private VideoEnterpiseService videoEnterpiseService;

    /***********************查询全国的热门校园招聘视频******************************/
    @ApiOperation(value = "查询全国的热门校园招聘视频",notes = "认证：需要。权限：5。对应企业角色“主页”",httpMethod = "POST",response = VideoResponseList.class)
    @RequestMapping("/countrywideRecruitHotVideos")
    @ResponseBody
    public VideoResponseList uploadVideo(
            @ApiParam(name = "currentPage",value = "请求页面序数（第几页），默认为第一页",required = false) @RequestParam(value = "currentPage",required = false,defaultValue = "1") int currentPage,
            @ApiParam(name = "amount",value = "请求视频个数",required = true) @RequestParam(value = "amount") int amount
    ){
        VideoRequest videoRequest = new VideoRequest();
        //获取当前用户ID
        UserResponse userResponse = userController.gain();
        videoRequest.setCurrentUserId(userResponse.getId());

        videoRequest.setCurrentPage(currentPage);
        videoRequest.setAmount(amount);
        //传1表示查询全国/本校的热门文娱活动视频，区分按类别查询本校的视频（时间顺序）
        VideoResponseList videoResponseList = videoEnterpiseService.countrywideRecruitHotVideos(videoRequest);
        return videoResponseList;
    }

    @ApiOperation(value = "查询某个学校的校园招聘视频（时间顺序）",notes = "认证：需要。权限：5。对应企业角色“主页”",httpMethod = "POST",response = VideoResponseList.class)
    @RequestMapping("/searchUniversityRecruitVideos")
    @ResponseBody
    public VideoResponseList uploadsearchUniversityRecruitVideosVideo(
            @ApiParam(name = "currentPage",value = "请求页面序数（第几页），默认为第一页",required = false) @RequestParam(value = "currentPage",required = false,defaultValue = "1") int currentPage,
            @ApiParam(name = "amount",value = "请求视频个数",required = true) @RequestParam(value = "amount") int amount,
            @ApiParam(name = "address",value = "查询某个特定学校",required = true) @RequestParam(value = "address") String address
    ){
        VideoRequest videoRequest = new VideoRequest();
        //若已登录则获取当前用户ID
        UserResponse userResponse = userController.gain();
        videoRequest.setCurrentUserId(userResponse.getId());

        videoRequest.setCurrentPage(currentPage);
        videoRequest.setAmount(amount);
        videoRequest.setAddress(address);
        //传1表示查询全国/本校的热门文娱活动视频，区分按类别查询本校的视频（时间顺序）
        VideoResponseList videoResponseList = videoEnterpiseService.searchUniversityRecruitVideos(videoRequest);
        return videoResponseList;
    }

}
