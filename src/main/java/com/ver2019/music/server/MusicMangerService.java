package com.ver2019.music.server;

import com.ver2019.music.Contains;
import com.ver2019.music.entity.Song;
import com.ver2019.music.entity.SongList;
import com.ver2019.music.entity.User;
import com.ver2019.music.mapper.SongListMapper;
import com.ver2019.music.mapper.SongMapper;
import com.ver2019.music.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import static com.ver2019.music.Contains.upLoadPath;
import static com.ver2019.music.Contains.upimagePath;

@Service
public class MusicMangerService {
    @Autowired
    SongMapper songMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    SongListMapper songListMapper;

    /*专辑封面*/
    public boolean saveImage(MultipartFile multipartFile,String songListId){
        SongList songList=songListMapper.findSongListById(songListId);
        //文件名
        String originalFileName=multipartFile.getOriginalFilename();
        //new前缀+时间戳
        String newFileNamePrefix=songList.getListName()+System.currentTimeMillis();
        //new文件名
        String newFileName=newFileNamePrefix+originalFileName.substring(originalFileName.lastIndexOf("."));
        //创建目录
        File file = new File(upimagePath+newFileName);
        try {
            multipartFile.transferTo(file);
            songList.setImgPath(file.getPath());
            return true;
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }
    /**
     *保存单首歌曲
     * Song
     */
    public boolean savamusic(MultipartFile multipartFile,MultipartFile imagefile,User currentUser,Song song){
        if(multipartFile!=null&&!multipartFile.isEmpty()){
            //文件名
            String originalFileName=multipartFile.getOriginalFilename();
            //前缀
            //String filePrefix=originalFileName.substring(0,originalFileName.lastIndexOf("."));
            //new前缀+时间戳
            String newFileNamePrefix=song.getSongName()+System.currentTimeMillis();
            //new文件名
            String newFileName=newFileNamePrefix+originalFileName.substring(originalFileName.lastIndexOf("."));
            //创建目录
            File file = new File(upLoadPath+newFileName);

            try {
                multipartFile.transferTo(file);
                Song songInBase=Song.builder().size(multipartFile.getSize())
                        .path(file.getPath())
                        .lyric(null)
                        .isList("true")
                        .songName(song.getSongName())
                        .songCreator(currentUser).build();
                //创建对应歌单
                SongList songListInBase=SongList.builder().songIdList(songInBase.getId()+",")
                        .listName(song.getSongName())
                        .creator(currentUser)
                        .remarks(null)
                        .build();

                songInBase.setBelongList(songListInBase);
                //图片
                if(imagefile!=null&&!imagefile.isEmpty()){
                    File image=new File(upimagePath+songInBase.getId()+".jpg");
                    imagefile.transferTo(image);
                    songInBase.setImagePath(image.getPath());
                    songListInBase.setImgPath(image.getPath());
                }
                songMapper.save(songInBase);
                songListMapper.savelist(songListInBase);
                return true;
            }catch (IOException e){
                System.out.println("文件上传错误");
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    /*删除*/
    public boolean deleteById(List<Long> deleteIds){
        try{
            for(Long id:deleteIds){
                songMapper.deleteById(id);
            }
            return true;
        }catch (Exception e){
            return false;
        }

    }
    /*创建歌单*/
    public boolean creatSongList(SongList songList){
        try {
            songListMapper.creatSongList(songList);
            return true;
        }catch (Exception e){
            System.out.println("创建错误");
            e.printStackTrace();
        }
        return false;
    }

    public boolean addSongtoList(String[] ids,String listId){
        try {
            SongList songList=songListMapper.findSongListById(listId);
            String idList=songList.getSongIdList();

            for(String id:ids){
                idList+=id+",";
            }
            songListMapper.updateSongIdList(idList,listId);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
