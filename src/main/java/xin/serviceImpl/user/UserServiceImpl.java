package xin.serviceImpl.user;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import xin.dao.user.UserMapper;
import xin.entity.user.User;
import xin.entity.user.UserRequest;
import xin.entity.user.UserResponse;
import xin.entity.user.modifyInfo.ModifyInfo;
import xin.service.user.UserService;

import java.io.File;
import java.io.IOException;
import java.util.UUID;


/**
 * Created by snowman on 2018/1/7.
 */
@Service(value = "userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;


    //证件文件夹名称
    private final String CERTIFICATE = "certificate/";

    //头像文件夹名称
    private final String IMAGES = "images/";

    //http路径
    private final String HTTPPATH = "http://www.flyingsnowman.xin/schoolpupil/";

    /*************************添加用户insertUser***************************/
    public void insertUser(User user,MultipartFile certificate) throws IOException {

        UserRequest userRequest = new UserRequest();
        //md5盐值加密
        Md5Hash hash = new Md5Hash(user.getPassword(),user.getAccount(),1024);
        user.setPassword(hash.toString());

        //有证件才执行操作
        if(certificate!=null) {
            //获取图片的新文件名
            String pictureName = getPictureName(certificate);
            //存进磁盘,传文件名，文件夹名，文件
            savePic(pictureName, CERTIFICATE, certificate);
            //文件名存进实体类
            userRequest.setCertificate(pictureName);
        }

        userRequest.setUser(user);
        userMapper.insertUser(userRequest);
    }
    /****************修改用户信息（用户名/密码/头像）***************/
    public void modifyInfo(ModifyInfo modifyInfo,MultipartFile picture) throws IOException {
        //修改图片才执行操作
        if(modifyInfo.getWhichOne()==3) {
            //若不是默认头像则删除
            deletePic(modifyInfo.getAccount());

            //获取图片的新文件名
            String pictureName = getPictureName(picture);
            //存进磁盘,传文件名，文件夹名，文件
            savePic(pictureName, IMAGES, picture);
            //文件名存进实体类
            modifyInfo.setInformation(pictureName);
        }

        //修改密码，MD5盐值加密
        if(modifyInfo.getWhichOne()==2){
            //md5盐值加密
            Md5Hash hash = new Md5Hash(modifyInfo.getInformation(),modifyInfo.getAccount(),1024);
            modifyInfo.setInformation(hash.toString());
        }

        userMapper.modifyInfo(modifyInfo);
    }

    /*************************检测账号是否已经存在***********************/
    public int checkExist(String account){
        //0未被注册，1已被注册
        return userMapper.checkExist(account);
    }

    /********************查询用户（登录）（by账号）********************/
    public UserResponse inquireUserByAccount(String account){
        UserResponse userRsponse;
        userRsponse = userMapper.inquireUserByAccount(account);
        //为头像设置完整的HTTP路径
        if(userRsponse!=null) userRsponse.setPicture(HTTPPATH+IMAGES+userRsponse.getPicture());
        return userRsponse;
    }











    ///////////////////////////////////////////////////////////////////////////////////////////////////


    /***********************获得当前项目绝对路径************************/
    public String getProjectPath(){
        //当前类的绝对路径
        //C:\Users\snowman\IdeaProjects\shoolpupil\target\schoolpupil\WEB-INF\classes
        String a = this.getClass().getResource("/").getPath();
        //C:\Users\snowman\IdeaProjects\shoolpupil\target\schoolpupil\
        String projectPath = StringUtils.substringBeforeLast(a,"WEB-INF");
        return projectPath;
    }

    /***********************获得图片的新文件名（证件和头像）******************************/
    public String getPictureName(MultipartFile file){
        String pic = file.getOriginalFilename();
        //新文件名=随机字符+.扩展名
        String pictureName = UUID.randomUUID() + pic.substring(pic.lastIndexOf("."));
        return pictureName;
    }

    /**********************图片存进磁盘（证件和头像）********************************/
    //Folder为文件夹，证件为certificate，头像为images
    public void savePic(String pictureName,String Folder,MultipartFile file) throws IOException {

        //证件文件夹绝对路径,前半部分C:\Users\snowman\IdeaProjects\shoolpupil\target\shoolpupil\
        String cerRealPath = getProjectPath();

        //证件图片完整绝对路径.项目路径+文件夹名称+图片名
        String wholeCerRealPath = cerRealPath + Folder + pictureName;

        //存放照片的文件夹路径（上一层）
        File beforeFile= new File(cerRealPath + Folder);
        //判断路径是否存在，不存在则创建
        if(!beforeFile.exists()){ beforeFile.mkdir(); }
        //保存文件
        file.transferTo(new File(wholeCerRealPath));
    }

    /******************************根据account删除磁盘上的头像照片****************************/
    public void deletePic(String account){
        UserResponse userRsponse;
        //获取原头像照片名
        userRsponse = userMapper.inquireUserByAccount(account);
        String pic = userRsponse.getPicture();
        //默认照片不删除
        if (!pic.equals("default.jpg")){
            //照片路径=存放的绝对路径+照片名（path形参中的path就是存放的绝对路径）
            String realPic = getProjectPath() + IMAGES + pic;

            //得到照片
            File picFile = new File(realPic);

            //删除之
            picFile.delete();
        }
    }

}
