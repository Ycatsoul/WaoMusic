package com.ver2019.music.controller;

import com.alibaba.fastjson.JSONObject;
import com.ver2019.music.annotation.UserLoginToken;
import com.ver2019.music.entity.Song;
import com.ver2019.music.entity.SongList;
import com.ver2019.music.entity.User;
import com.ver2019.music.server.MusicMangerService;
import com.ver2019.music.server.MusicService;
import com.ver2019.music.server.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@UserLoginToken
@Controller
@RequestMapping("/mymusic")
public class MusicMangerController {
    @Autowired
    UserService userService;
    @Autowired
    MusicService musicService;
    @Autowired
    MusicMangerService musicMangerService;

    @RequestMapping("/index")
    public String index(){
        return "index";
    }
    @RequestMapping("/touplad")
    public String toupld(){
        return "upload";
    }

    @RequestMapping("/upimage")
    @ResponseBody
    public JSONObject upimage(@RequestParam("image")MultipartFile multipartFile,
                              HttpServletRequest request,Model model){
        JSONObject upresult=new JSONObject();
        String originalFileName=multipartFile.getOriginalFilename();
        String fileSuffix=originalFileName.substring(originalFileName.lastIndexOf("."));
        if(!fileSuffix.equals("jpg")||!fileSuffix.equals("JPG")){
            upresult.put("message","只支持jpg类型");
        }else{
            String id=request.getParameter("listId");
            if(!id.isEmpty()){
                if(musicMangerService.saveImage(multipartFile,id)){
                    upresult.put("message","上传成功");

                }
            }
        }
        return upresult;

    }
    @PostMapping("/upload")
    //@ResponseBody
    public String upload(@RequestParam("file")MultipartFile multipartFile,
                         @Nullable @RequestParam("imagefile")
                         MultipartFile imageFile, Song song,
                         HttpServletRequest request, HttpSession session, Model model){
        JSONObject result=new JSONObject();
        //获取当前用户
        User currentUser=userService.getCurrentUser(session);
        //System.out.println("上传用户"+currentUser.getUserName()+currentUser.getId());
        String originalFileName=multipartFile.getOriginalFilename();
        String fileSuffix=originalFileName.substring(originalFileName.lastIndexOf("."));
        if(fileSuffix.equals(".mp3")||fileSuffix.equals(".MP3")){
            result.put("message","文件类型错误");
        }
        if(multipartFile.getSize()>100000){
            result.put("message","文件过大");
        }
        if(multipartFile.isEmpty()){
            result.put("message","文件大小不能为0");
        }
        if (currentUser!=null) {
            if(musicMangerService.savamusic(multipartFile,imageFile,currentUser,song)){
                result.put("message","上传成功");
            }else{
                result.put("message","文件上传错误");
            }
        }
        model.addAttribute("result",result);
        return "upload";
    }

    @RequestMapping("/delete")
    @ResponseBody
    public JSONObject delete(HttpServletRequest request){
        JSONObject result=new JSONObject();
        String deletes=request.getParameter("deletes");
        if(deletes.isEmpty()){
            result.put("message","请选择");
        }else{
            String[] d=deletes.split(",");
            List<Long> deletesList=new ArrayList<>();
            for (String id:d){
                deletesList.add(Long.parseLong(id));
            }
                 musicMangerService.deleteById(deletesList);
            result.put("message","delete success");
        }
        return result;
    }

    @PostMapping("/createSongList")
    @ResponseBody
    public JSONObject createSongList(SongList songList,Model model,HttpSession session){
        User currentUser=userService.getCurrentUser(session);
        JSONObject result=new JSONObject();
        if(songList!=null){
            songList.setCreator(currentUser);
            if(musicMangerService.creatSongList(songList)){
                result.put("message","创建成功");

            }else{
                result.put("message","创建失败");
            }
        }
        //跳转到歌单页面
        return result;
    }
    @RequestMapping("/addSongtoList")
    public String addSongtoList(HttpServletRequest request,Model model){
        JSONObject addResult=new JSONObject();
        String songIds=request.getParameter("songIdS");
        String listId=request.getParameter("listId");
        String[] songIdsArray=songIds.split(",");
        if(songIdsArray.length!=0){
            if(musicMangerService.addSongtoList(songIdsArray,listId)){
                addResult.put("addResult","success");
                model.addAttribute("addResult",addResult);
                return "歌单页面";
            }
        }
        return "erro";
    }
}
