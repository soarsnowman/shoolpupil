package xin.service.video;

import xin.entity.video.VideoRequest;
import xin.entity.video.VideoResponseList;



/**
 * Created by snowman on 2018/1/22.
 */
public interface VideoOthersService {
    //查询视频
    public VideoResponseList inquireVideos(VideoRequest videoRequest,int message);

    //title模糊查询视频
    public VideoResponseList searchVideosByTitle(VideoRequest videoRequest);

}
