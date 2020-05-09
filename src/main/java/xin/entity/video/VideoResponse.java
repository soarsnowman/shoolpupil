package xin.entity.video;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * 接收数据库查询出来的视频类
 * Created by snowman on 2018/1/22.
 */
@ApiModel(value = "VideoResponse")
public class VideoResponse extends Video {
    @ApiModelProperty(value="视频ID")
    private int videoId;
    @ApiModelProperty(value = "视频所属用户的id")
    private int userId;
    @ApiModelProperty(value="上传时间")
    private Date uploadTime;
    @ApiModelProperty(value="视频HTTP路径")
    private String videoPath;
    @ApiModelProperty(value="视频封面HTTP路径")
    private String imgPath;

    @ApiModelProperty(value="视频发布者所在地址（学校）")
    private String address;

    @ApiModelProperty(value="点赞数")
    private int likeCnt;
    @ApiModelProperty(value="是否点过赞,0/1=否/是")
    private int likeInfo;
    @ApiModelProperty(value="举报数")
    private int reportCnt;
    @ApiModelProperty(value="是否举报过,0/1=否/是")
    private int reportInfo;
    @ApiModelProperty(value="浏览数")
    private int browseCnt;

    public int getVideoId() {
        return videoId;
    }

    public void setVideoId(int videoId) {
        this.videoId = videoId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getLikeCnt() {
        return likeCnt;
    }

    public void setLikeCnt(int likeCnt) {
        this.likeCnt = likeCnt;
    }

    public int getLikeInfo() {
        return likeInfo;
    }

    public void setLikeInfo(int likeInfo) {
        this.likeInfo = likeInfo;
    }

    public int getReportCnt() {
        return reportCnt;
    }

    public void setReportCnt(int reportCnt) {
        this.reportCnt = reportCnt;
    }

    public int getReportInfo() {
        return reportInfo;
    }

    public void setReportInfo(int reportInfo) {
        this.reportInfo = reportInfo;
    }

    public int getBrowseCnt() {
        return browseCnt;
    }

    public void setBrowseCnt(int browseCnt) {
        this.browseCnt = browseCnt;
    }
}
