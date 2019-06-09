package com.ver2019.music.mapper;

import com.ver2019.music.entity.Song;
import com.ver2019.music.entity.Song;
import com.ver2019.music.entity.SongList;
import com.ver2019.music.model.SongModel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SongMapper {
    @Select("select * from song where id=#{id} and delflag=0")
    @Results({
            @Result(property="songCreator",column="songCreatorId",one=@One(select="com.ver2019.music.mapper.UserMapper.findById")),
            @Result(property="belongList",column="belongListId",one=@One(select="com.ver2019.music.mapper.SongListMapper.findSongListById"))
    })
    Song findById(String id);

    @Select("select * from song where songName like '%"+"${songName}"+"%' and delflag=0")
    @Results({
            @Result(property="songCreator",column="songCreatorId",one=@One(select="com.ver2019.music.mapper.UserMapper.findById")),
            @Result(property="belongList",column="belongListId",one=@One(select="com.ver2019.music.mapper.SongListMapper.findSongListById"))
    })
    List<Song> findAllBySongName(@Param("songName") String songName);

    @Select("select * from song where songName=#{songName} and delflag=0 limit 1")
    @Results({
            @Result(property="songCreator",column="songCreatorId",one=@One(select="com.ver2019.music.mapper.UserMapper.findById")),
            @Result(property="belongList",column="belongListId",one=@One(select="com.ver2019.music.mapper.SongListMapper.findSongListById"))
    })
    Song findOneBySongName(String songName);

    @Select("select * from song where songCreatorId=#{id} and delflag=0")
    @Results({
            @Result(property="songCreator",column="songCreatorId",one=@One(select="com.ver2019.music.mapper.UserMapper.findById")),
            @Result(property="belongList",column="belongListId",one=@One(select="com.ver2019.music.mapper.SongListMapper.findSongListById"))
    })
    List<Song> findByCreatorId(String id);

    @Select("select * from song where songCreatorName=#{creatorName} and delflag=0")
    @Results({
            @Result(property="songCreator",column="songCreatorId",one=@One(select="com.ver2019.music.mapper.UserMapper.findById")),
            @Result(property="belongList",column="belongListId",one=@One(select="com.ver2019.music.mapper.SongListMapper.findSongListById"))
    })
    List<Song> findByCreatorName(String creatorName);

    /*@SelectKey(keyProperty = "song.id",resultType = String.class, before = true,
            statement = "select replace(uuid(), '-', '')")
    @Options(keyProperty = "song.id", useGeneratedKeys = true)
    @Insert("insert into song(id,songName,songCreatorName,songCreatorId,size,lyric,path,createTime,updateTime)"
            +"value(#{song.id},#{song.songName},#{song.songCreatorName},#{song.songCreatorId},#{song.size},#{song.lyric},#{song.path},now(),now())")
    Integer save(@Param("song") SongModel song);*/


    /*@SelectKey(keyProperty = "id",resultType = String.class, before = true,
            statement = "select replace(uuid(), '-', '')")
    @Options(keyProperty = "id", useGeneratedKeys = true)*/
    @Insert("insert into song(id,songName,songCreatorName,songCreatorId,isList,belongListId,size,lyric,path,imagePath,createTime,updateTime)"
            +"value(#{id},#{songName},#{songCreator.userName},#{songCreator.id},#{isList},#{belongList.id},#{size},#{lyric},#{path},#{imagePath},now(),now())")
    Integer save(Song song);
    /*@Delete("delete form Song where id=#{id}")
    String deleteById(String id);*/

    @Update("update song set delflag=1,updateTime=now() where id=#{id}")
    Boolean deleteById(@Param("id")long id);


}
