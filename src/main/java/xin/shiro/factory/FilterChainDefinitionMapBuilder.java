package xin.shiro.factory;

import java.util.LinkedHashMap;

/**
 * Created by snowman on 2017/11/20.
 */
public class FilterChainDefinitionMapBuilder {

    public LinkedHashMap<String,String> buildFilterChainDefinitionMap(){
        LinkedHashMap<String,String> map = new LinkedHashMap<String, String>();

        /*********************** swagger ********************************/
        map.put("/swagger/**","anon");
        map.put("/api-docs","anon");



        /*********************** User ********************************/
        map.put("/user/login","anon");
        map.put("/user/checkExist","anon");
        map.put("/user/register","anon");
        map.put("/user/notLoggedIn","anon");
        map.put("/user/noPermission","anon");


        /*********************** Video ********************************/
        //videoOthers
        map.put("/videoOthers/localHotEntertainmentVideos","roles[admin,student,teacher,community,department,VIP]");
        map.put("/videoOthers/countrywideEntertainmentHotVideos","anon");
        map.put("/videoOthers/localCategoryVideos","roles[admin,student,teacher,community,department,VIP]");
//        map.put("/videoOthers/countrywideCategoryVideos","roles[admin,teacher,community,department,VIP]");
        map.put("/videoOthers/countrywideCategoryVideos","roles[admin,student,teacher,community,department,VIP]");
        map.put("/videoOthers/searchVideosByTitle","roles[admin,student,teacher,community,department,VIP]");
        map.put("/videoOthers/uploadVideo","roles[admin,student,teacher,community,department,VIP]");
        //videoCommon
        map.put("/videoCommon/searchVideosByVideoId","anon");
        map.put("/videoCommon/addBrowse","anon");
        //videoEnterprise
        map.put("/videoEnterprise/countrywideRecruitHotVideos","roles[admin,enterprise]");
        map.put("/videoEnterprise/searchUniversityRecruitVideos","roles[admin,enterprise]");


        /*********************** Comment ********************************/
        //comment
        map.put("/comment/inquireComments","anon");
        map.put("/comment/inquireCommentById","anon");
        //reply
        map.put("/commentReply/inquireReplys","anon");
        map.put("/commentReply/inquireReplyById","anon");


        /*********************** 头像/视频/视频封面 ********************************/
        map.put("/film/**","anon");
        map.put("/images/**","anon");
        map.put("/certificate/**","anon");


        /*********************** 全部拦截 ********************************/
        map.put("/**","authc");

        return map;
    }
}
