package com.ver2019.music;

import com.ver2019.music.entity.User;
import com.ver2019.music.mapper.UserMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ver2019.music.mapper")
public class MusicApplication {


    public static void main(String[] args) {
        SpringApplication.run(MusicApplication.class, args);

    }

}
