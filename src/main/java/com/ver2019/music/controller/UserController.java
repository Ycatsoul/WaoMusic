package com.ver2019.music.controller;

import com.alibaba.fastjson.JSONObject;
import com.ver2019.music.entity.User;
import com.ver2019.music.server.TokenService;
import com.ver2019.music.server.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    TokenService tokenService;
    public void checklogin(HttpSession session,Model model){
        User user=(User) session.getAttribute("user");
        JSONObject result=new JSONObject();
        if(user!=null){
            result.put("islogin",true);
            model.addAttribute("registe",false);
        }else{
            result.put("islogin",false);
            model.addAttribute("registe",false);
        }
        model.addAttribute("result",result);
    }
    /*
    * 登陆
    * */
    @RequestMapping("/index")
    public String index(HttpSession session,Model model){
        checklogin(session,model);
        return "index";
    }

    @RequestMapping("/iframe")
    public String to(HttpSession session,Model model){
        checklogin(session,model);
        return "iframe";
    }
    @PostMapping("/login1")
    public String login1(User user, Model model, HttpSession session){
        JSONObject result=new JSONObject();
        User userInBase=userService.getUserByname(user.getUserName());
        if(userInBase==null){
            result.put("islogin",false);
            result.put("message","登录失败，用户不存在");
        }else {
            if(!user.getPassword().equals(userInBase.getPassword())){
                result.put("islogin",false);
                result.put("message","登录失败，密码错误");
            }else {
                String token=tokenService.getToken(userInBase);
                result.put("message","登陆成功");
                result.put("islogin",true);
                session.setAttribute("user",userInBase);
                session.setAttribute("token",token);

                System.out.println("登陆"+user.getUserName());
                System.out.println(token);
            }
        }
        model.addAttribute("result",result);
        return "index";

    }
    /*
    * 注销
    * */
    @GetMapping("/Logout")
    public String Logout(HttpServletRequest request, HttpSession session, Model model){
            JSONObject result=new JSONObject();
            session.removeAttribute("user");
            session.removeAttribute("token");
            result.put("islogin",false);
            model.addAttribute("result",result);

        return "index";
    }
    /*
    * 注册
    * 信息已经过前端js验证*/
    @PostMapping("/registe")
    public String registe(User user,HttpSession session,Model model){
        checklogin(session,model);
        JSONObject registeResult=new JSONObject();
        try{
            if(user!=null){
                if(userService.resign(user)){
                    registeResult.put("message","注册成功");
                }else{
                    registeResult.put("message","注册失败");
                }
            }
        }catch (Exception e){
            System.out.println(e);
        }
        model.addAttribute("registe",true);
        model.addAttribute("registeResult",registeResult);
        return "index";
    }

}
