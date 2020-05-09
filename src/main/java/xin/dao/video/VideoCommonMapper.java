package xin.dao.video;

import xin.entity.video.VideoRequest;
import xin.entity.video.VideoResponse;

import java.util.List;

/**
 * Created by snowman on 2018/1/23.
 */
public interface VideoCommonMapper {
    //上传视频
    public void uploadVideo(VideoRequest videoRequest);

    //插入点赞举报浏览总数表
    public void insertLikeReportCnt(int videoId);

    //通过videoId查询视频
    public List<VideoResponse> searchVideosByVideoId(int videoId);

    //查询是否点赞过
    public int inquireLiked(VideoRequest videoRequest);

    //查询是否举报过
    public int inquireRepoted(VideoRequest videoRequest);

    //视频点赞
    public void addLike(VideoRequest videoRequest);

    //视频点赞总数加1
    public void addLikeCnt(VideoRequest videoRequest);

    //视频举报
    public void addReport(VideoRequest videoRequest);

    //举报总数加1
    public void addReportCnt(VideoRequest videoRequest);

    //增加浏览量
    public void addBrowse(VideoRequest videoRequest);

    //浏览量总数加1
    public void addBrowseCnt(VideoRequest videoRequest);

    //个人视频
    public List<VideoResponse> personalVideos(VideoRequest videoRequest);

    //删除视频
    public void deleteVideo(int videoId);

}
