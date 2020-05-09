package xin.entity.video;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 响应请求的视频集合类
 * Created by snowman on 2018/1/22.
 */
@ApiModel(value = "VideoResponseList")
public class VideoResponseList {
    @ApiModelProperty(value = "视频的集合")
    private List<VideoResponse> videoResponse;
    @ApiModelProperty(value = "集合的大小（多少个视频）")
    private int listSize;

    public List getVideoResponse() {
        return videoResponse;
    }

    public void setVideoResponse(List videoResponse) {
        this.videoResponse = videoResponse;
    }

    public int getListSize() {
        return listSize;
    }

    public void setListSize(int listSize) {
        this.listSize = listSize;
    }
}
