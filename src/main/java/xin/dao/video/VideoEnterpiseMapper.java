package xin.dao.video;

import xin.entity.video.VideoRequest;
import xin.entity.video.VideoResponse;

import java.util.List;

/**
 * Created by snowman on 2018/1/24.
 */
public interface VideoEnterpiseMapper {
    //查询全国的热门校园招聘视频
    public List<VideoResponse> countrywideRecruitHotVideos(VideoRequest videoRequest);

    //查询某个学校的校园招聘视频（时间顺序）
    public List<VideoResponse> searchUniversityRecruitVideos(VideoRequest videoRequest);
}
