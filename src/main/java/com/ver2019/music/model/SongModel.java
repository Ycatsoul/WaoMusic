package com.ver2019.music.model;

import com.ver2019.music.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SongModel extends BaseEntity {
    private String songName;
   /*private String AlbumName;
    private String AlbumId;*/
    private String songCreatorId;
    private String songCreatorName;
    private Long size;
    private String lyric;
    private String path;
}
