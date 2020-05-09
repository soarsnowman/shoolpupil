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
import xin.entity.video.Video;
import xin.entity.video.VideoRequest;
import xin.entity.video.VideoResponseList;
import xin.service.video.VideoOthersService;


/**
 * 其他角色视频模块控制层
 * Created by snowman on 2018/1/20.
 */
@Controller
@RequestMapping(value = "/videoOthers")
@Api(value = "其他角色(1234)视频模块",description = "角色表示：1student（学生）,2teacher（教师）,3community（社团）,4department（院系）,5enterprise（企业）,6VIP（会员）")
public class VideoOthersController {

    @Autowired
    private VideoOthersService videoOthersService;

    @Autowired
    private UserController userController;

    /****************查询本校的热门文娱活动视频*****************/
    @ApiOperation(value = "查询本校的热门文娱活动视频",notes = "认证：需要。权限：12346。对应“主页”",httpMethod = "POST",response = VideoResponseList.class)
    @RequestMapping(value = "/localHotEntertainmentVideos")
    @ResponseBody
    public VideoResponseList localHotEntertainmentVideos(
            @ApiParam(name = "currentPage",value = "请求页面序数（第几页），默认为第一页",required = false) @RequestParam(value = "currentPage",required = false,defaultValue = "1") int currentPage,
            @ApiParam(name = "amount",value = "请求视频个数",required = true) @RequestParam(value = "amount") int amount
    ) {
        UserResponse userResponse = userController.gain();
        VideoRequest videoRequest = new VideoRequest();
        videoRequest.setCurrentPage(currentPage);
        videoRequest.setAmount(amount);
        //当前用户ID
        videoRequest.setCurrentUserId(userResponse.getId());
        //当前用户所在学校
        videoRequest.setAddress(userResponse.getAddress());
        //传1表示查询全国/本校的热门文娱活动视频，区分按类别查询本校的视频（时间顺序）
        VideoResponseList videoResponseList = videoOthersService.inquireVideos(videoRequest,1);
        return videoResponseList;
    }


    /****************查询全国的热门文娱活动视频*****************/
    @ApiOperation(value = "查询全国的热门文娱活动视频",notes = "认证：不需要。权限：无。对应“主页”",httpMethod = "POST",response = VideoResponseList.class)
    @RequestMapping(value = "/countrywideEntertainmentHotVideos")
    @ResponseBody
    public VideoResponseList countrywideHotEntertainmentVideos(
            @ApiParam(name = "currentPage",value = "请求页面序数（第几页），默认为第一页",required = false) @RequestParam(value = "currentPage",required = false,defaultValue = "1") int currentPage,
            @ApiParam(name = "amount",value = "请求视频个数",required = true) @RequestParam(value = "amount") int amount
    ) {
        VideoRequest videoRequest = new VideoRequest();
        //默认当前用户ID为0，即游客
        videoRequest.setCurrentUserId(0);
        //若已登录则获取当前用户ID
        if(SecurityUtils.getSubject().getPrincipal()!=null){
            UserResponse userResponse = userController.gain();
            videoRequest.setCurrentUserId(userResponse.getId());
        }

        videoRequest.setCurrentPage(currentPage);
        videoRequest.setAmount(amount);
        //传1表示查询全国/本校的热门文娱活动视频，区分按类别查询本校的视频（时间顺序）
        VideoResponseList videoResponseList = videoOthersService.inquireVideos(videoRequest,1);
        return videoResponseList;
    }


    /****************按类别查询本校的视频（时间顺序）*****************/
    @ApiOperation(value = "按类别查询本校的视频（时间顺序）",notes = "认证：需要。权限：12346。对应“发现”",httpMethod = "POST",response = VideoResponseList.class)
    @RequestMapping(value = "/localCategoryVideos")
    @ResponseBody
    public VideoResponseList localCategoryVideos(
            @ApiParam(name = "currentPage",value = "请求页面序数（第几页），默认为第一页",required = false) @RequestParam(value = "currentPage",required = false,defaultValue = "1") int currentPage,
            @ApiParam(name = "amount",value = "请求视频个数",required = true) @RequestParam(value = "amount") int amount,
            @ApiParam(name = "category",value = "视频类别",required = true) @RequestParam(value = "category") String category
    ) {
        UserResponse userResponse = userController.gain();
        VideoRequest videoRequest = new VideoRequest();
        videoRequest.setVideo(new Video());
        videoRequest.setCurrentPage(currentPage);
        videoRequest.setAmount(amount);
        //当前用户ID
        videoRequest.setCurrentUserId(userResponse.getId());
        //当前用户所在学校
        videoRequest.setAddress(userResponse.getAddress());
        videoRequest.getVideo().setCategory(category);
        //传2按类别查询本校的视频（时间顺序），区分表示查询全国/本校的热门文娱活动视频
        VideoResponseList videoResponseList = videoOthersService.inquireVideos(videoRequest,2);
        return videoResponseList;
    }


    /****************按类别查询全国的视频（时间顺序）*****************/
    @ApiOperation(value = "按类别查询全国的视频（时间顺序）",notes = "认证：需要。权限：2346。对应“发现”",httpMethod = "POST",response = VideoResponseList.class)
    @RequestMapping(value = "/countrywideCategoryVideos")
    @ResponseBody
    public VideoResponseList countrywideCategoryVideos(
        @ApiParam(name = "currentPage",value = "请求页面序数（第几页），默认为第一页",required = false) @RequestParam(value = "currentPage",required = false,defaultValue = "1") int currentPage,
        @ApiParam(name = "amount",value = "请求视频个数",required = true) @RequestParam(value = "amount") int amount,
        @ApiParam(name = "category",value = "视频类别",required = true) @RequestParam(value = "category") String category
    ) {
        VideoRequest videoRequest = new VideoRequest();
        videoRequest.setVideo(new Video());
        //获取当前用户ID
        UserResponse userResponse = userController.gain();
        videoRequest.setCurrentUserId(userResponse.getId());
        videoRequest.setCurrentPage(currentPage);
        videoRequest.setAmount(amount);
        videoRequest.getVideo().setCategory(category);
        //传2按类别查询本校的视频（时间顺序），区分表示查询全国/本校的热门文娱活动视频
        VideoResponseList videoResponseList = videoOthersService.inquireVideos(videoRequest,2);
        return videoResponseList;
    }


    /****************按标题搜索视频*****************/
    @ApiOperation(value = "按标题搜索视频",notes = "认证：需要。权限：12346。1搜索本校，2346搜索全国。对应“搜索”",httpMethod = "POST",response = VideoResponseList.class)
    @RequestMapping(value = "/searchVideosByTitle")
    @ResponseBody
    public VideoResponseList searchVideosByTitle(
            @ApiParam(name = "currentPage",value = "请求页面序数（第几页），默认为第一页",required = false) @RequestParam(value = "currentPage",required = false,defaultValue = "1") int currentPage,
            @ApiParam(name = "amount",value = "请求视频个数",required = true) @RequestParam(value = "amount") int amount,
            @ApiParam(name = "title",value = "视频标题",required = true) @RequestParam(value = "title",defaultValue = "") String title
    ) {
        VideoRequest videoRequest = new VideoRequest();
        UserResponse userResponse = userController.gain();
        //获取当前用户ID
        videoRequest.setCurrentUserId(userResponse.getId());
        //获取当前用户学校
        videoRequest.setAddress(userResponse.getAddress());

        videoRequest.setVideo(new Video());
        videoRequest.setCurrentPage(currentPage);
        videoRequest.setAmount(amount);
        //模糊标题
        title = "%"+title+"%";
        videoRequest.getVideo().setTitle(title);
        //判断查询全国（"student"角色）还是本校（非"student"的其他角色），1还是2346
        videoRequest.setCurrentUserRoleId(SecurityUtils.getSubject().hasRole("student") ? 1 : 2);
        VideoResponseList videoResponseList = videoOthersService.searchVideosByTitle(videoRequest);
        return videoResponseList;
    }

}