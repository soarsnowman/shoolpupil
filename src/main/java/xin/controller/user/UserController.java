package xin.controller.user;

import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xin.entity.ResponseInfo;
import xin.entity.user.User;
import xin.entity.user.UserResponse;
import xin.entity.user.modifyInfo.ModifyInfo;
import xin.service.user.UserService;

import java.io.IOException;


/**
 * Created by snowman on 2018/1/7.
 */
@Controller
@RequestMapping(value = "/user")
@Api(value = "用户模块",description = "角色表示：1student（学生）,2teacher（教师）,3community（社团）,4department（院系）,5enterprise（企业）,6VIP（会员）")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ResponseInfo responseInfo;


    /*******************注册*************************/
    @ApiOperation(value = "用户注册",response = ResponseInfo.class,httpMethod = "POST",notes = "认证：不需要。权限：无。用户注册")
    @RequestMapping(value = "/register")
    @ResponseBody
    public ResponseInfo register(
            @ModelAttribute User user,
            @ApiParam(name = "certificate",value = "证书（学生角色不需要）,文件类型",required = false) @RequestParam(value = "certificate",required = false) MultipartFile certificate) throws IOException {

        //检测账号是否已经存在
        responseInfo = checkExist(user.getAccount());
        if(responseInfo.getInfo()==2){
            return responseInfo;
        }
        userService.insertUser(user,certificate);

        return responseInfo;
    }

    /********************检测账号是否已经存在********************/
    @ApiOperation(value = "检测账号是否已经存在",response = ResponseInfo.class,httpMethod = "POST",notes = "认证：不需要。权限：无。学生角色为手机号码，其他角色为邮箱")
    @RequestMapping(value = "/checkExist")
    @ResponseBody
    public ResponseInfo checkExist(@ApiParam(name = "account",value = "账号",required = true) @RequestParam("account") String account){
        responseInfo.setInfo(userService.checkExist(account)==0 ? 1 : 2);
        return responseInfo;
    }


    /******************用户登录***********************/
    @ApiOperation(value = "用户登录",response = UserResponse.class,httpMethod = "POST",notes = "认证：不需要。权限：无。用户登录,账号一栏学生角色为手机号码，其他角色为邮箱")
    @RequestMapping(value="/login",method = RequestMethod.POST)
    @ResponseBody
    public UserResponse inquireUser(
            @ApiParam(name = "account",value = "账号",required = true) @RequestParam("account") String account,
            @ApiParam(name = "password",value = "密码",required = true) @RequestParam("password") String password
    ) {
        UserResponse userResponse = new UserResponse();
        Subject currentUser = SecurityUtils.getSubject();
        //是否已登录
        if (!currentUser.isAuthenticated()){
            //把账号密码封装到UsernamePasswordToken对象
            UsernamePasswordToken token = new UsernamePasswordToken(account,password);
            try{
                //登录
                currentUser.login(token);
            }catch (IncorrectCredentialsException ie){//密码错误
                userResponse.setInfo("2");
                return userResponse;
            }catch (AuthenticationException ae){
                //登陆失败，账号错误，账号异常
                userResponse.setInfo(ae.getMessage());
                return userResponse;
            }
        }

        userResponse = userService.inquireUserByAccount(account);

        return userResponse;
    }

    /******************获取用户信息***********************/
    @ApiOperation(value = "获取用户信息",response = UserResponse.class,httpMethod = "POST",notes = "认证：需要。权限：123456。")
    @RequestMapping(value="/gain",method = RequestMethod.POST)
    @ResponseBody
    public UserResponse gain() {
        //获取当前用户
        Subject currentUser = SecurityUtils.getSubject();
        //获取当前用户账号
        String account = currentUser.getPrincipals().toString();
        System.out.print(account);
        UserResponse userResponse = userService.inquireUserByAccount(account);
        return userResponse;
    }

    /******************修改信息***********************/
    @ApiOperation(value = "修改信息",response = ResponseInfo.class,httpMethod = "POST",notes = "认证：需要。权限：123456。每次修改“用户名/密码/头像”其中一个。information、picture选其一")
    @RequestMapping(value="/modifyInfo",method = RequestMethod.POST)
    @ResponseBody
    public ResponseInfo modifyInfo(
            @ApiParam(name = "whichOne",value = "信号量。1新用户名，2新密码，3新头像",required = true) @RequestParam(value = "whichOne",required = true) int whichOne,
            @ApiParam(name = "information",value = "用户名/密码,1和2传这个",required = false) @RequestParam(value = "information",required = false) String information,
            @ApiParam(name = "picture",value = "头像,3传这个",required = false) @RequestParam(value = "picture",required = false) MultipartFile picture
    ) throws IOException {
        //获取当前用户账号
        String account = SecurityUtils.getSubject().getPrincipals().toString();

        ModifyInfo modifyInfo = new ModifyInfo();
        modifyInfo.setWhichOne(whichOne);
        modifyInfo.setAccount(account);

        if(whichOne!=3){
            modifyInfo.setInformation(information);
        }else if(picture==null||picture.isEmpty()){
            //若是修改图片保证内容不为空
            responseInfo.setInfo(2);
            return responseInfo;
        }
        userService.modifyInfo(modifyInfo, picture);
        responseInfo.setInfo(1);
        return responseInfo;
    }

    /**************************登出*************************/
    @ApiOperation(value = "登出（退出）",response = ResponseInfo.class,httpMethod = "POST",notes = "认证：需要。权限：123456。")
    @RequestMapping(value="/logout",method = RequestMethod.POST)
    @ResponseBody
    public ResponseInfo logout(){
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            // session 会销毁，在SessionListener监听session销毁，清理权限缓存
            subject.logout();
        }
        responseInfo.setInfo(1);
        return responseInfo;
    }


    /**************************提示未登录*************************/
    //由shiro拦截器调用
    @ApiIgnore
    @RequestMapping("/notLoggedIn")
    @ResponseBody
    public ResponseInfo notLoggedIn(){
        responseInfo.setInfo(0);
        return responseInfo;
    }

    /**************************提示没有权限*************************/
    //由shiro拦截器调用
    @ApiIgnore
    @RequestMapping("/noPermission")
    @ResponseBody
    public ResponseInfo noPermission(){
        responseInfo.setInfo(3);
        return responseInfo;
    }

}
