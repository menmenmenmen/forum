package com.graduation_project.testforum.controller;

import com.graduation_project.testforum.dto.QuestionDTO;
import com.graduation_project.testforum.mapper.QuestionMapper;
import com.graduation_project.testforum.mapper.UserMapper;
import com.graduation_project.testforum.model.Question;
import com.graduation_project.testforum.model.User;
import com.graduation_project.testforum.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {
        @Autowired
        private UserMapper userMapper;
        @Autowired
        private QuestionService questionService;
        @GetMapping("/")
        public String index(HttpServletRequest request, Model model){
                    Cookie[] cookies = request.getCookies();
            if (cookies != null&&cookies.length != 0) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("token")) {
                        String token = cookie.getValue();
                        User user = userMapper.findUserToken(token);
                        if (user != null) {
                            request.getSession().setAttribute("user", user);
                        }
                        break;
                    }
                }
            }

            List<QuestionDTO> questionList = questionService.queryQuestionList();
            model.addAttribute("questions",questionList);
            return "index";
        }
}
