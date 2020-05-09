package xin.serviceImpl.video;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xin.dao.video.VideoOthersMapper;
import xin.entity.video.VideoRequest;
import xin.entity.video.VideoResponse;
import xin.entity.video.VideoResponseList;
import xin.service.video.VideoCommonService;
import xin.service.video.VideoOthersService;

import java.util.List;
import java.util.Date;

/**
 * Created by snowman on 2018/1/22.
 */
@Service(value = "videoOthersService")
public class VideoOthersServiceImpl implements VideoOthersService {

    @Autowired
    private VideoOthersMapper videoOthersMapper;

    @Autowired
    private VideoCommonService videoCommonService;

    /***************查询全国/本校的视频****************/
    //1热门文娱活动,不给出类别，直接在查询数据库是直接填“生活文娱”
    //2按类别查询本校的视频（时间顺序），给出类别
    public VideoResponseList inquireVideos(VideoRequest videoRequest,int message) {
        VideoResponseList videoResponseList = new VideoResponseList();
        Session session = SecurityUtils.getSubject().getSession();
        //第一页则加载最新并储存访问时间，为下次访问做准备；否则提取上次访问时间
        if(videoRequest.getCurrentPage()==1){
            session.setAttribute("lastTime",new Date());
        }
        videoRequest.setLastTime((Date) session.getAttribute("lastTime"));

        //设置从第几条记录开始查询
        videoRequest.setCurrentPage((videoRequest.getCurrentPage()-1)*videoRequest.getAmount());

        List<VideoResponse> list;
        //判断是热门生活文娱（1）还是按类别（2）
        if(message==1) {
            list = videoOthersMapper.hotEntertainmentVideos(videoRequest);
        }else {
            list = videoOthersMapper.categoryVideos(videoRequest);
        }
        //设置获取集合大小
        videoResponseList.setListSize(list.size());
        videoResponseList.setVideoResponse(list);
        //若集合不为空则添加是否已点赞是否已举报信号量，视频和封面HTTP路径
        if(videoResponseList.getListSize()!=0){
            videoCommonService.addInfo(list,videoRequest);
        }

        return videoResponseList;
    }

    /*********************title模糊查询视频*************************/
    public VideoResponseList searchVideosByTitle(VideoRequest videoRequest){
        VideoResponseList videoResponseList = new VideoResponseList();
        Session session = SecurityUtils.getSubject().getSession();
        //第一页则加载最新并储存访问时间，为下次访问做准备；否则提取上次访问时间
        if(videoRequest.getCurrentPage()==1){
            session.setAttribute("lastTime",new Date());
        }
        videoRequest.setLastTime((Date) session.getAttribute("lastTime"));

        //设置从第几条记录开始查询
        videoRequest.setCurrentPage((videoRequest.getCurrentPage()-1)*videoRequest.getAmount());

        List<VideoResponse> list = videoOthersMapper.searchVideosByTitle(videoRequest);

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
