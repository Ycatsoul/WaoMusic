package com.ver2019.music.controller;

import com.alibaba.fastjson.JSONObject;
import com.ver2019.music.entity.Song;
import com.ver2019.music.entity.SongList;
import com.ver2019.music.server.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/test")
public class TestController {
    @Autowired
    MusicService musicService;
    @RequestMapping("/list")
    public String showlist(Model model){
        JSONObject list=new JSONObject();
        List<Song> songList=musicService.findListById("1c6df9b949f44deb9f97c356af1cdc98");
        SongList List1=musicService.findSongListById("1c6df9b949f44deb9f97c356af1cdc98");
        list.put("songlist",songList);
        model.addAttribute("list",List1);
        model.addAttribute("result",songList);
        return "test";
    }

    @RequestMapping("/testserach")
    public String TE(Model model){
        List<Song> list= musicService.findBySongName("改变");
        model.addAttribute("songlist",list);
        System.out.println(list.toString());
        return "serachResult";
    }
}
