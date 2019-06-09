package com.ver2019.music.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.ver2019.music.annotation.UserLoginToken;
import com.ver2019.music.entity.Song;
import com.ver2019.music.entity.SongList;
import com.ver2019.music.entity.User;
import com.ver2019.music.server.MusicService;
import com.ver2019.music.server.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
/*
* 无登陆状态*/

@Controller
@RequestMapping("/music")
public class MusicController {
    @Autowired
    UserService userService;
    @Autowired
    MusicService musicService;

    public void checklogin(HttpSession session,Model model){
        User user=(User) session.getAttribute("user");
        JSONObject result=new JSONObject();
        if(user!=null){
            result.put("islogin",true);
        }else{
            result.put("islogin",false);
            model.addAttribute("registe",false);
        }
        model.addAttribute("result",result);
    }

    @GetMapping("/doserach")
    public String serach(HttpServletRequest request,HttpSession session,Model model){
        checklogin(session,model);
        String word=request.getParameter("word");
        if(!word.isEmpty()){
            System.out.println(word);
            List<Song> list= musicService.findBySongName(word);
            model.addAttribute("songlist",list);
            System.out.println(list.toString());
        }else{
            return "index";
        }

        return "serachResult";
    }

    @GetMapping("/getpath")
    @ResponseBody
    public String getPath(HttpServletRequest request){
        String id=request.getParameter("id");
        if(!id.isEmpty()){
            return musicService.findBySongId(id).getPath();
        }
        return null;
    }

    @GetMapping("/openSongList")
    public String open(HttpServletRequest request, HttpSession session, Model model){
        User user=(User) session.getAttribute("user");
        JSONObject result=new JSONObject();
        if(user!=null){
            result.put("islogin",true);
        }else{
            result.put("islogin",false);
        }
        model.addAttribute("result",result);
        String listId=request.getParameter("listId");
        String cruListId=(String) session.getAttribute("curListId");
        System.out.println("now"+listId);
        System.out.println("session"+cruListId);
        if (!listId.equals(cruListId)){
             listId=request.getParameter("listId");
            session.setAttribute("curListId",listId);
            cruListId=listId;
             System.out.println(listId);
        }
        System.out.println("fine"+cruListId);
            List<Song> songList = musicService.findListById(cruListId);
            SongList list = musicService.findSongListById(cruListId);
            model.addAttribute("list", list);
            model.addAttribute("songList", songList);
        /*System.out.println(list.toString());
        System.out.println(songList.toString());*/
        return "openSongList";
    }


    @RequestMapping("/getmp3")
    @ResponseBody
    public  void video(HttpServletRequest request, HttpServletResponse response)throws Exception{
        try{
            response.setHeader("ContentType","audio/mp3");
            String id=request.getParameter("id");
            String path=musicService.findBySongId(id).getPath();
            loadFile(path,response);
        }catch (Exception e){

        }
    }

    @RequestMapping("/getimage")
    @ResponseBody
    public  void image(HttpServletRequest request,HttpSession session, HttpServletResponse response)throws Exception{
        try{
            String id=request.getParameter("songid");
            if(id==null){
                id=(String) session.getAttribute("curListId");
            }
            String path=musicService.findSongListById(id).getImgPath();
            loadFile(path,response);
        }catch (Exception e){

        }
    }
    public void loadFile(String path,HttpServletResponse response){
        try {
            File file = new File(path);
            FileInputStream in = new FileInputStream(file);
            ServletOutputStream out = response.getOutputStream();
            byte[] b = null;
            while(in.available() >0) {
                if(in.available()>10240) {
                    b = new byte[10240];
                }else {
                    b = new byte[in.available()];
                }
                in.read(b, 0, b.length);
                out.write(b, 0, b.length);
            }
            response.addHeader("Content-Length",String.valueOf(file.length()));
            in.close();
            out.flush();
            out.close();
        }catch (IOException e){

        }

    }
}
