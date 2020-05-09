package xin.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import xin.entity.video.Video;
import xin.entity.video.VideoRequest;
import xin.service.user.UserService;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * 转换视频格式
 * Created by snowman on 2017/10/26.
 * fileDir=相对路径/film/video/新文件名. 扩展名
 * logoPathDir = "/film/video/"
 * logoRealPathDir=文件夹C:\Users\snowman\IdeaProjects\pupil\target\pupil\film\video\
 * newFileName=新的转码之前文件名,信息提前后文件名
 * fileEnd=文件扩展名
 * filedirs  fileNamedirs=绝对路径C:\Users\snowman\IdeaProjects\pupil\target\pupil\film \video\新文件名. 扩展名
 * fileDir=相对路径/film /video/新文件名. 扩展名
 * finalFileDir = video/video/新文件名. 扩展名
 * aviPath=源文件保存路径filedirs.getAbsolutePath()
 * coverPathDir= 封面相对路径/film/video/名称.拓展名
 * coverPathDir= 封面绝对路径
 */
public class VideoUploadTool {

    @Resource(name = "transfMediaTool")
    private TransfMediaTool transfMediaTool;

    @Autowired
    private UserService userService;


    //文件最大500M
    private static long upload_maxsize = 500 * 1024 * 1024;
    // 文件允许格式
    private static String[] allowFiles = {
            ".avi", ".mpg", ".wmv", ".3gp", ".mov", ".asf", ".asx", ".vob", ".mp4",".flv",
            ".AVI", ".MPG", ".WMV", ".3GP", ".MOV", ".ASF", ".ASX", ".VOB", ".MP4",".FLV"
            //,".wmv9", ".rm", ".rmvb"
    };
    // 允许转码的视频格式（ffmpeg）
    private static String[] allowMP4 = {".avi", ".mpg", ".wmv", ".3gp", ".mov", ".asf", ".asx", ".vob", ".mp4",".flv",".AVI", ".MPG", ".WMV", ".3GP", ".MOV", ".ASF", ".ASX", ".VOB", ".MP4",".FLV"};
    // 允许的视频转码格式(mencoder)
//    private static String[] allowAVI = { ".wmv9", ".rm", ".rmvb" };

    public Map createFile(MultipartFile multipartFile) {

        VideoRequest videoRequest = new VideoRequest();
        Map map = new HashMap();
        int bflag = 0;
        String fileName = multipartFile.getOriginalFilename().toString();
        // 判断文件不为空
        if (multipartFile != null && !multipartFile.isEmpty()) {
            // 判断文件大小
            if (multipartFile.getSize() <= upload_maxsize) {
                // 文件类型判断
                if (this.checkFileType(fileName)) {
                    bflag = 3;
                } else {
                    System.out.println("文件类型不允许");
                    bflag = 2;
                }
            } else {
                System.out.println("文件大小超范围");
                bflag = 1;
            }
        } else {
            System.out.println("文件为空");
        }

        if (bflag == 3) {
            //视频路径片段
            String logoPathDir = "/film/video/";
            //封面路径片段
            String coverPathDir = "/film/coverImg/";
            //绝对路径前半部分（文件夹）C:\Users\snowman\IdeaProjects\pupil\target\pupil\film\video\
            String logoRealPathDir = userService.getProjectPath() + logoPathDir;
            // 上传到本地磁盘
            File logoSaveFile = new File(logoRealPathDir);
            if (!logoSaveFile.exists()) {
                logoSaveFile.mkdirs();
            }
            //绝对路径前半部分（文件夹）C:\Users\snowman\IdeaProjects\pupil\target\pupil\film\coverImg\
            String coverRealPathDir = userService.getProjectPath() + coverPathDir;
            // 上传到本地磁盘
            File logoSaveCoverFile = new File(coverRealPathDir);
            if (!logoSaveCoverFile.exists()) {
                logoSaveCoverFile.mkdirs();
            }
            // 新的转码之前文件名,,信息提前后文件名
            String newFileName = this.getName();
            // 文件扩展名
            String fileEnd = this.getFileExt(fileName);
            // 绝对路径C:\Users\snowman\IdeaProjects\pupil\target\pupil\video\文件名
            String fileNamedirs = logoRealPathDir + newFileName + fileEnd;
            System.out.println("保存的绝对路径：" + fileNamedirs);
            File filedirs = new File(fileNamedirs);
            // 转入文件
            try {
                multipartFile.transferTo(filedirs);
            } catch (IllegalStateException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            //转码后视频相对路径
            String fileDir = "";
            String finalFileDir = "";

            //转码后信息在尾部的文件名，中转站
            String newAfterFileName = this.getName();

            //封面图片 相对路径
            String coverDir = "";
            String finalcoverDir = "";

            // 源文件保存路径
            String aviPath = filedirs.getAbsolutePath();
            // 转码Avi
            //        if (this.checkAVIType(fileEnd)) {
            //            // 设置转换为AVI格式后文件的保存路径
            //            String codcAviPath = logoRealPathDir + File.separator + newFileName + ".avi";
            //            // 获取配置的转换工具（mencoder.exe）的存放路径
            //            String mencoderPath = request.getServletContext().getRealPath("/tools/mencoder.exe");
            //            aviPath = transfMediaTool.processAVI(mencoderPath, filedirs.getAbsolutePath(), codcAviPath);
            //            fileEnd = this.getFileExt(codcAviPath);
            //        }
            if (aviPath != null) {
                // 转码mp4
                if (this.checkMediaType(fileEnd)) {
                    try {
                        // 设置转换为mp4格式后文件的保存路径
                        String codcFilePath = logoRealPathDir + File.separator + newAfterFileName + ".mp4";
                        //设置封面照片保存路径
                        String coverPath = coverRealPathDir + File.separator + newFileName + ".jpg";
                        // 获取配置的转换工具（ffmpeg.exe）的存放路径
                        String ffmpegPath = userService.getProjectPath() + "/tools/ffmpeg";
                        //转换工具路径，视频源路径，转码后视频路径，封面照片路径
                        transfMediaTool.processMP4(ffmpegPath, aviPath, codcFilePath, coverPath);
                        //转码成功删除原视频，新起的名字实际不存在，给信息提前后的文件用
                        filedirs.delete();

                        //转码MP4后信息在尾部，将他提前才能边加载边播放
                        if (codcFilePath!=null){
                            // 设置转换为mp4格式后文件的保存路径
                            String moveUpPath = logoRealPathDir + File.separator + newFileName + ".mp4";
                            // 获取配置的尾部信息前置工具（qt-faststart）的存放路径
                            String qtfaststartPath = userService.getProjectPath() + "/tools/qt-faststart";
                            //转换工具路径，视频源路径，转码后视频路径，封面照片路径
                            transfMediaTool.processMoveUp(qtfaststartPath, codcFilePath, moveUpPath);
                            //信息提前成功删除原转码后mp4视频
                            File codcFile = new File(codcFilePath);
                            codcFile.delete();

                            //转码后视频相对路径
                            fileDir = logoPathDir + newFileName + ".mp4";
                            StringBuilder builder1 = new StringBuilder(fileDir);
                            finalFileDir = builder1.substring(1);
                            videoRequest.setVideoPath(finalFileDir);

                            //转码后视频封面相对路径
                            coverDir = coverPathDir + newFileName + ".jpg";
                            StringBuilder builder2 = new StringBuilder(coverDir);
                            finalcoverDir = builder2.substring(1);
                            videoRequest.setImgPath(finalcoverDir);
                            map.put("videoRequest",videoRequest);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        }
        map.put("bflag",bflag);

        return map;
    }

    /*********************************获取新文件名*************************************/
    private String getName() {
        Random random = new Random();
        return "" + random.nextInt(10000) + System.currentTimeMillis();
    }

    /*********************************获取文件扩展名*************************************/
    private String getFileExt(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /*********************************检查文件类型*************************************/
    private boolean checkFileType(String fileName) {
        Iterator<String> type = Arrays.asList(allowFiles).iterator();
        while (type.hasNext()) {
            String ext = type.next();
            if (fileName.toLowerCase().endsWith(ext)) {
                return true;
            }
        }
        return false;
    }

//    /*********************************检查文件类型（转码AVI）*************************************/
//    private boolean checkAVIType(String fileEnd) {
//        Iterator<String> type = Arrays.asList(allowAVI).iterator();
//        while (type.hasNext()) {
//            String ext = type.next();
//            if (fileEnd.equals(ext)) {
//                return true;
//            }
//        }
//        return true;
//    }

    /*********************************检查文件类型（转码mp4）*************************************/
    private boolean checkMediaType(String fileEnd) {
        Iterator<String> type = Arrays.asList(allowMP4).iterator();
        while (type.hasNext()) {
            String ext = type.next();
            if (fileEnd.equals(ext)) {
                return true;
            }
        }
        return false;
    }
}
