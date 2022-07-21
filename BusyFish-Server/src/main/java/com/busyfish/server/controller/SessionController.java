package com.busyfish.server.controller;

import com.busyfish.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * author:junjian.chen
 * 上午11:34
 */
@Controller
@RequestMapping("/session")
public class SessionController {
    @Autowired
    UserService userService;


    @RequestMapping(value = "/getSessionId")
    @ResponseBody
    public String getSessionId(HttpServletRequest request){
        int port= request.getServerPort();
        String sessionId=request.getSession().getId();
        String userId=request.getSession().getAttribute("userId").toString();

        return "Port: "+ port
                + "<br/>sessionId: "+sessionId
                +"<br/>userId: "+userId;
    }

    @RequestMapping(value = "/setSessionId")
    @ResponseBody
    public String setSessionId(HttpServletRequest request,
                               @RequestParam("userId") Integer userId){
        request.getSession().setAttribute("userId",userId);
        return "Session setting successfully. <br/> userId: "+userId;
    }
}
