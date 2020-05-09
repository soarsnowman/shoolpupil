package xin.serviceImpl.video;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xin.dao.video.VideoEnterpiseMapper;
import xin.entity.video.VideoRequest;
import xin.entity.video.VideoResponse;
import xin.entity.video.VideoResponseList;
import xin.service.video.VideoCommonService;
import xin.service.video.VideoEnterpiseService;

import java.util.Date;
import java.util.List;

/**
 * Created by snowman on 2018/1/24.
 */
@Service(value = "videoEnterpiseService")
public class VideoEnterpiseServiceImpl implements VideoEnterpiseService {

    @Autowired
    private VideoEnterpiseMapper videoEnterpiseMapper;

    @Autowired
    private VideoCommonService videoCommonService;

    /*****************查询全国的热门校园招聘视频********************/
    public VideoResponseList countrywideRecruitHotVideos(VideoRequest videoRequest){
        VideoResponseList videoResponseList = new VideoResponseList();
        Session session = SecurityUtils.getSubject().getSession();
        //第一页则加载最新并储存访问时间，为下次访问做准备；否则提取上次访问时间
        if(videoRequest.getCurrentPage()==1){
            session.setAttribute("lastTime",new Date());
        }
        videoRequest.setLastTime((Date) session.getAttribute("lastTime"));

        //设置从第几条记录开始查询
        videoRequest.setCurrentPage((videoRequest.getCurrentPage()-1)*videoRequest.getAmount());

        List<VideoResponse> list = videoEnterpiseMapper.countrywideRecruitHotVideos(videoRequest);

        //设置获取集合大小
        videoResponseList.setListSize(list.size());
        videoResponseList.setVideoResponse(list);
        //若集合不为空则添加是否已点赞是否已举报信号量，视频和封面HTTP路径
        if(videoResponseList.getListSize()!=0){
            videoCommonService.addInfo(list,videoRequest);
        }
        return videoResponseList;
    }

    /*********************查询某个学校的校园招聘视频（时间顺序）*********************/
    public VideoResponseList searchUniversityRecruitVideos(VideoRequest videoRequest){
        VideoResponseList videoResponseList = new VideoResponseList();
        Session session = SecurityUtils.getSubject().getSession();
        //第一页则加载最新并储存访问时间，为下次访问做准备；否则提取上次访问时间
        if(videoRequest.getCurrentPage()==1){
            session.setAttribute("lastTime",new Date());
        }
        videoRequest.setLastTime((Date) session.getAttribute("lastTime"));

        //设置从第几条记录开始查询
        videoRequest.setCurrentPage((videoRequest.getCurrentPage()-1)*videoRequest.getAmount());

        List<VideoResponse> list = videoEnterpiseMapper.searchUniversityRecruitVideos(videoRequest);

        //设置获取集合大小
        videoResponseList.setListSize(list.size());
        videoResponseList.setVideoResponse(list);
        //若集合不为空则添加是否已点赞是否已举报信号量，视频和封面HTTP路径
        if(videoResponseList.getListSize()!=0){
            videoCommonService.addInfo(list,videoRequest);
        }
        return videoResponseList;
    }
}
