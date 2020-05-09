package xin.dao.video;

import xin.entity.video.VideoRequest;
import xin.entity.video.VideoResponse;

import java.util.List;

/**
 * Created by snowman on 2018/1/21.
 */
public interface VideoOthersMapper {
    //查询本校的热门文娱活动视频
    public List<VideoResponse> hotEntertainmentVideos(VideoRequest videoRequest);

    //按类别查询全国/本校的视频（时间顺序）
    public List<VideoResponse> categoryVideos(VideoRequest videoRequest);

    //title模糊查询视频
    public List<VideoResponse> searchVideosByTitle(VideoRequest videoRequest);

}
