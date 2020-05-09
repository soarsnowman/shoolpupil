package xin.service.video;

import xin.entity.video.VideoRequest;
import xin.entity.video.VideoResponseList;

/**
 * Created by snowman on 2018/1/24.
 */
public interface VideoEnterpiseService {
    //查询全国的热门校园招聘视频
    public VideoResponseList countrywideRecruitHotVideos(VideoRequest videoRequest);

    //查询某个学校的校园招聘视频（时间顺序）
    public VideoResponseList searchUniversityRecruitVideos(VideoRequest videoRequest);
}
