package com.ver2019.music.server;


import com.ver2019.music.entity.User;
import com.ver2019.music.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User getCurrentUser(HttpSession session){
        User user=(User) session.getAttribute("user");
        if(user!=null){
            System.out.println("当前用户："+user.getUserName());
            return user;
        }
        return null;
    }
    public User getUserById(String id){
        User user=userMapper.findById(id);
        if(user!=null){
            //用户存在
            return user;
        }
        else{
            return null;
        }
    }
    public User getUserByname(String username){
        User user=userMapper.findOneByUserName(username);
        if(user!=null){
            //用户存在
            return user;
        }
        else{
            return null;
        }
    }
    public List<User> getUserListByname(String username){
        List<User> user=userMapper.findAllByUserName(username);
        if(user!=null){
            //用户存在
            return user;
        }
        else{
            return null;
        }
    }



    public boolean resign(User user){
        try{
            User exitUser=userMapper.checkIsExit(user.getUserName(),user.getPassword());
            if (exitUser==null){
                userMapper.save(user);
                System.out.println("注册成功"+user.toString());
                return true;
            }else {
                return false;
            }
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("userMapper----注册失败");
            return false;
        }
    }
}
