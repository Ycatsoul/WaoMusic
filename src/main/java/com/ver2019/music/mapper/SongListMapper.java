package com.ver2019.music.mapper;

import com.ver2019.music.entity.SongList;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SongListMapper {

    @SelectKey(keyProperty = "id",resultType = String.class, before = true,
            statement = "select replace(uuid(), '-', '')")
    @Options(keyProperty = "id", useGeneratedKeys = true)
    @Insert("insert into songlist(id,listName,creatorId,remarks,creatorName,createTime,updateTime)"+
            "value(#{id},#{listName},#{creator.id},#{creator.userName},#{remarks},now(),now())")
    Integer creatSongList(SongList songList);
    //-------------------------------------------------------------------

    /*@SelectKey(keyProperty = "id",resultType = String.class, before = true,
            statement = "select replace(uuid(), '-', '')")
    @Options(keyProperty = "id", useGeneratedKeys = true)*/
    @Insert("insert into songlist(id,imgPath,listName,creatorId,creatorName,songIdList,remarks,createTime,updateTime)"
            +"value(#{id},#{imgPath},#{listName},#{creator.id},#{creator.userName},#{songIdList},#{remarks},now(),now())")
    Integer savelist(SongList songlist);
    //-------------------------------------------------------------------
    @Select("select * from songlist where id=#{id} and delflag=0")
    @Results({
            @Result(property="creator",column="creatorId",one=@One(select="com.ver2019.music.mapper.UserMapper.findById"))
    })
    SongList findSongListById(String id);
    //-------------------------------------------------------------------
    @Select("select * from songlist where listName=#{listName} and delflag=0")
    @Results({
            @Result(property="creator",column="creatorId",one=@One(select="com.ver2019.music.mapper.UserMapper.findById"))
    })
    SongList findSongListByName(String listName);
    //-------------------------------------------------------------------

    @Update("update songlist set songIdList=#{songIdList},updateTime=now()" +
            "where id=#{listId} and delflag=0")
    Integer updateSongIdList(@Param("songIdList") String songIdList,@Param("listId")String listId);
    //-------------------------------------------------------------------
}
