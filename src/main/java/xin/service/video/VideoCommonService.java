package xin.service.video;

import xin.entity.video.VideoRequest;
import xin.entity.video.VideoResponse;
import xin.entity.video.VideoResponseList;

import java.util.List;

/**
 * Created by snowman on 2018/1/23.
 */
public interface VideoCommonService {
    //上传视频
    public void uploadVideo(VideoRequest videoRequest);

    //通过videoId查询视频
    public VideoResponse searchVideosByVideoId(VideoRequest videoRequest);

    //视频点赞
    public void addLike(VideoRequest videoRequest);

    //视频举报
    public void addReport(VideoRequest videoRequest);

    //增加浏览量
    public void addBrowse(VideoRequest videoRequest);

    //是否已点赞
    public int inquireLiked(VideoRequest videoRequest);

    //是否已举报
    public int inquireRepoted(VideoRequest videoRequest);

    //个人视频
    public VideoResponseList personalVideos(VideoRequest videoRequest);

    //删除视频
    public void deleteVideo(int videoId);

    //加HTTP路径，加点赞、举报信息
    public List<VideoResponse> addInfo(List<VideoResponse> list, VideoRequest videoRequest);
}
