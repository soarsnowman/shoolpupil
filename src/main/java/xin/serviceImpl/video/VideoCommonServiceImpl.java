package xin.serviceImpl.video;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xin.dao.video.VideoCommonMapper;
import xin.entity.video.VideoRequest;
import xin.entity.video.VideoResponse;
import xin.entity.video.VideoResponseList;
import xin.service.user.UserService;
import xin.service.video.VideoCommonService;

import java.io.File;
import java.util.List;

/**
 * Created by snowman on 2018/1/23.
 */
@Service(value = "videoCommonService")
public class VideoCommonServiceImpl implements VideoCommonService {

    //http路径
    private final String HTTPPATH = "http://www.flyingsnowman.xin/schoolpupil/";

    @Autowired
    private VideoCommonMapper videoCommonMapper;

    @Autowired
    private UserService userService;


    /********************上传视频***********************/
    public void uploadVideo(VideoRequest videoRequest){
        System.out.print(videoRequest.getVideoId());
        videoCommonMapper.uploadVideo(videoRequest);
        System.out.print(videoRequest.getVideoId());
        videoCommonMapper.insertLikeReportCnt(videoRequest.getVideoId());
    }

    /*************通过videoId查询视频*****************/
    public VideoResponse searchVideosByVideoId(VideoRequest videoRequest){
        List<VideoResponse> list = videoCommonMapper.searchVideosByVideoId(videoRequest.getVideoId());
        VideoResponse videoResponse = null;
        //若集合不为空则添加是否已点赞是否已举报信号量，视频和封面HTTP路径
        if (list.size()!=0){
            addInfo(list,videoRequest);
            videoResponse = list.get(0);
        }
        return videoResponse;
    }

    /**********************个人视频*****************/
    public VideoResponseList personalVideos(VideoRequest videoRequest){
        VideoResponseList videoResponseList = new VideoResponseList();
        //设置从第几条记录开始查询
        videoRequest.setCurrentPage((videoRequest.getCurrentPage()-1)*videoRequest.getAmount());
        //查询视频
        List<VideoResponse> list = videoCommonMapper.personalVideos(videoRequest);

        //设置获取集合大小
        videoResponseList.setListSize(list.size());
        videoResponseList.setVideoResponse(list);
        //若集合不为空则添加是否已点赞是否已举报信号量，视频和封面HTTP路径
        if(videoResponseList.getListSize()!=0){
            addInfo(list,videoRequest);
        }

        return videoResponseList;

    }

    /*********************删除视频*****************/
    public void deleteVideo(int videoId){
        //删除磁盘数据
        //根据视频ID查询出该视频
        List<VideoResponse> list = videoCommonMapper.searchVideosByVideoId(videoId);

        //视频具体位置
        String videoPath = userService.getProjectPath() + list.get(0).getVideoPath();
        //封面具体位置
        String coverImgPath = userService.getProjectPath() + list.get(0).getImgPath();
        //取到文件
        File video = new File(videoPath);
        File coverImg = new File(coverImgPath);
        //删除
        video.delete();
        coverImg.delete();

        //删除数据库信息
        videoCommonMapper.deleteVideo(videoId);

    }


    /*************视频点赞*****************/
    public void addLike(VideoRequest videoRequest){
        videoCommonMapper.addLike(videoRequest);
        videoCommonMapper.addLikeCnt(videoRequest);
    }

    /*************视频举报*****************/
    public void addReport(VideoRequest videoRequest){
        videoCommonMapper.addReport(videoRequest);
        videoCommonMapper.addReportCnt(videoRequest);
        //查询该视频是否达到举报数目上线,是则删除
        int item = (videoCommonMapper.searchVideosByVideoId(videoRequest.getVideoId())).get(0).getReportCnt();
        if (item>=2) {
            deleteVideo(videoRequest.getVideoId());
        }
    }

    /*************增加浏览量*****************/
    public void addBrowse(VideoRequest videoRequest){
        videoCommonMapper.addBrowse(videoRequest);
        videoCommonMapper.addBrowseCnt(videoRequest);
    }

    /*************是否已点赞*****************/
    public int inquireLiked(VideoRequest videoRequest){
        return videoCommonMapper.inquireLiked(videoRequest);
    }

    /*************是否已举报****************/
    public int inquireRepoted(VideoRequest videoRequest){
        return videoCommonMapper.inquireRepoted(videoRequest);
    }

    /****************加HTTP路径，加点赞、举报信息*************/
    public List<VideoResponse> addInfo(List<VideoResponse> list,VideoRequest videoRequest){
        for (VideoResponse x:list) {
            //加HTTP路径
            x.setVideoPath(HTTPPATH+x.getVideoPath());
            x.setImgPath(HTTPPATH+x.getImgPath());

            //设置当前视频id
            videoRequest.setVideoId(x.getVideoId());

            //添加是否已点赞是否已举报信号量
            x.setLikeInfo(inquireLiked(videoRequest));
            x.setReportInfo(inquireRepoted(videoRequest));
        }
        return list;
    }
}
