package com.ver2019.music.server;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.ver2019.music.entity.Song;
import com.ver2019.music.entity.SongList;
import com.ver2019.music.mapper.SongListMapper;
import com.ver2019.music.mapper.SongMapper;
import com.ver2019.music.units.DataGridResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/*
* 用于搜索 新歌推荐 歌单推荐
* */
@Service
public class MusicService {
    @Autowired
    SongMapper songMapper;
    @Autowired
    SongListMapper songListMapper;
    //搜索
    public PageInfo<Song> findBySongNameWithPage(String songName, int page, int rows){
        if(!songName.isEmpty()){
            PageHelper.startPage(page,rows);
            List<Song> list=new ArrayList<Song>();
            list=songMapper.findAllBySongName(songName);
            PageInfo<Song> pageInfo=new PageInfo<Song>(list);
            return pageInfo;
        }else{
            return null;
        }
    }
    //搜索
    public List<Song> findBySongName(String songName){
        if(!songName.isEmpty()){
            List<Song> list=songMapper.findAllBySongName(songName);
            return list;
        }else{
            return null;
        }
    }
    //返回Song
    public Song findBySongId(String id){
        if(!id.isEmpty()) {
            return songMapper.findById(id);
        }
        return null;
    }
    //返回歌单Song 列表
    public List<Song> findListById(String id){

        SongList list= songListMapper.findSongListById(id);
        String[] songIdList=list.getSongIdList().split(",");
        List<Song> songList=new ArrayList<>();
        for (String tid:songIdList){
            songList.add(songMapper.findById(tid));
        }
        return songList;
    }
    /*返回歌单*/
    public SongList findSongListById(String id){
        SongList list=songListMapper.findSongListById(id);
        return list;
    }

}
