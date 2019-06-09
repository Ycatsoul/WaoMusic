package com.ver2019.music;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ver2019.music.entity.Song;
import com.ver2019.music.entity.SongList;
import com.ver2019.music.entity.User;
import com.ver2019.music.mapper.SongListMapper;
import com.ver2019.music.mapper.SongMapper;
import com.ver2019.music.mapper.UserMapper;
import com.ver2019.music.server.MusicService;
import com.ver2019.music.server.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpRequest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.ver2019.music.Contains.upLoadPath;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MusicApplicationTests {
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserService userService;
    @Autowired
    MusicService musicService;
    @Autowired
    SongMapper songMapper;
    @Autowired
    SongListMapper songListMapper;

    //@Test
    public void contextLoads() {

    }

    @Test
    public void Test1(){
       /* User user=userMapper.findOneByUserName("goo");
        System.out.println(user.toString());*/
        User user=User.builder().userName("ysmind").userType("admin")
                .email("49189063@qq.com").mobile("123312313").password("123456").sex("男").build();

       userMapper.save(user);
       // songMapper.save(songInBase);
    }
    @Test
    public void Test2(){
       /* Song songInBase=Song.builder()
                .size(null)
                .path(null)
                .lyric(null)
                .songName("asd123")
                .songCreator(userMapper.findOneByUserName("goo1234"))
                .build();
        songMapper.save(songInBase);
        Song song=songMapper.findOneBySongName("asd123");
        System.out.println(song.getSongCreator().toString());*/
       List<Song> list=songMapper.findAllBySongName("asd123");
       for(Song s:list){
           System.out.println(s.toString());
       }
    }

    @Test
    public void test3(){
        PageHelper.startPage(1,5);
        List<User> list=new ArrayList<User>();
        list=userMapper.findAllByUserName("goo12");
        PageInfo<User> pageInfo=new PageInfo<User>(list);
        System.out.println(pageInfo.getPageNum());
        System.out.println(pageInfo.getSize());
        System.out.println(pageInfo.getPageNum());
        System.out.println(pageInfo.isHasNextPage());
    }
    @Test
    public void test4(){
       /* User user=userMapper.findOneByUserName("goo12");
        Song song= Song.builder().songCreater(user).songName("lll").lyric("aaa")
                .path("sadasada").size("123").build();
        songMapper.save(song);*/
       String path="src/main/resources/song/";
       File file=new File(path+"1aaa.txt");
        if(!file.exists()){
            try {

                file.createNewFile();
            }catch (IOException E){

            }
        }
        System.out.println(file.getPath());

    }
    @Test
    public void test5(){
       /* SongList songList=SongList.builder().listName("asa")
                .creator(userMapper.findOneByUserName("goo123"))
                .remarks("songlist").build();
        songListMapper.creatSongList(songList);
        SongList re=songListMapper.findSongListByName("asa");
        System.out.println(re.toString());*/

    }
}
