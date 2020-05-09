package xin.entity.video;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * 查询数据库类
 * Created by snowman on 2018/1/22.
 */
@ApiModel(value = "VideoRequest")
public class VideoRequest {
    @ApiModelProperty(value = "Video")
    private Video video;
    @ApiModelProperty(value = "视频的id")
    private int videoId;
    @ApiModelProperty(value = "视频路径")
    private String videoPath;
    @ApiModelProperty(value = "封面路径")
    private String imgPath;
    @ApiModelProperty(value = "上一次访问的时间")
    private Date lastTime;

    @ApiModelProperty(value = "请求页面序数（第几页），默认为第一页",required = false,notes = "一定要先请求过第一页后才能请求非第一页")
    private int currentPage;
    @ApiModelProperty(value = "请求视频个数")
    private int amount;

    @ApiModelProperty(value = "地址（学校）")
    private String address;

    @ApiModelProperty(value = "当前用户ID")
    private int currentUserId;
    @ApiModelProperty(value = "当前用户角色ID")
    private int currentUserRoleId;

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public int getVideoId() {
        return videoId;
    }

    public void setVideoId(int videoId) {
        this.videoId = videoId;
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

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }


    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(int currentUserId) {
        this.currentUserId = currentUserId;
    }

    public int getCurrentUserRoleId() {
        return currentUserRoleId;
    }

    public void setCurrentUserRoleId(int currentUserRoleId) {
        this.currentUserRoleId = currentUserRoleId;
    }

}
