package com.ver2019.music.entity;

import com.ver2019.music.Contains;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "songlist")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SongList extends BaseEntity {
    private String imgPath=Contains.imagePath;//图标路径
    private String listName;
    private User creator;
    private String songIdList;
    private String remarks;
}
