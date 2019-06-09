package com.ver2019.music.entity;

import lombok.*;


import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "user")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {
    private String userName;
    private String password;
    private String userType="admin";//角色  管理员，音乐人，普通用户
    private String sex;
    private String email;	// 邮箱
    private String mobile;	// 手机

}
