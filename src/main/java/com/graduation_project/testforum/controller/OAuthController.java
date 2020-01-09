package com.graduation_project.testforum.controller;

import com.graduation_project.testforum.dto.AccessTokenDTO;
import com.graduation_project.testforum.dto.GitHubUserDTO;
import com.graduation_project.testforum.mapper.UserMapper;
import com.graduation_project.testforum.model.User;
import com.graduation_project.testforum.provider.GitHubProvider;
import org.apache.catalina.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class OAuthController {
    @Autowired
    private GitHubProvider gitHubProvider;
    @Value("${github.client.id}")
    private String client_id;
    @Value("${github.client.secret}")
    private String client_secret;
    @Value("${github.redirect.uri}")
    private String redirect_uri;
    @Autowired
    private UserMapper userMapper;
    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code, @RequestParam(name = "state") String state, HttpServletResponse response){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(client_id);
        accessTokenDTO.setClient_secret(client_secret);
        accessTokenDTO.setRedirect_uri(redirect_uri);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        String accessToken = gitHubProvider.getAccessToken(accessTokenDTO);
        GitHubUserDTO gitHubUserDTO = gitHubProvider.getUser(accessToken);
        if (gitHubUserDTO!=null){
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setName(gitHubUserDTO.getName());
            user.setToken(token);
            user.setAccountId(String.valueOf(gitHubUserDTO.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
            response.addCookie(new Cookie("token",token));
            //request.getSession().setAttribute("user",gitHubUserDTO);
            return "redirect:/index";
        }else {
            return "redirect:/index";
        }
    }
}
