package xin.entity.video;


import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * 基本视频类（接收请求）
 * Created by snowman on 2018/1/22.
 */
@ApiModel(value = "Video")
public class Video {
    @ApiModelProperty(value = "视频标题",required = true)
    private String title;
    @ApiModelProperty(value = "视频简介",required = false)
    private String synopsis;
    @ApiModelProperty(value = "视频类别",required = true)
    private String category;



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
