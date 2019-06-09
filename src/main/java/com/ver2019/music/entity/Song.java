package com.ver2019.music.entity;

import com.ver2019.music.model.SongModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "song")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Song extends BaseEntity {
    private String songName;
    private User songCreator;
    private String isList;
    private SongList belongList;
    private Long size;
    private String lyric;
    private String imagePath;
    private String path;



    public static SongModel changeEntityToModel(Song entity){
        SongModel entityModel = new SongModel();
        if(null != entity)
        {
            User create = entity.getSongCreator();
            if(null != create)
            {
                entityModel.setSongCreatorId(create.getId());
                entityModel.setSongCreatorName(create.getUserName());
            }
            /*SongList album= entity.getAlbum();
            if(null != album)
            {
                entityModel.setAlbumId(album.getId());
                entityModel.setAlbumName(album.getListName());
            }*/
            BeanUtils.copyProperties(entity, entityModel);

            return entityModel;
        }
        return entityModel;
    }

    public static List<SongModel> changeToModel(List<Song> list)
    {
        if(null != list && list.size()>0)
        {
            List<SongModel> modelList = new ArrayList<SongModel>();
            for(Song entity : list)
            {
                modelList.add(changeEntityToModel(entity));
            }
            return modelList;
        }
        return null;
    }
    
}
