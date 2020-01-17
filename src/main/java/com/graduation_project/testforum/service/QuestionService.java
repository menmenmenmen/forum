package com.graduation_project.testforum.service;

import com.graduation_project.testforum.dto.QuestionDTO;
import com.graduation_project.testforum.mapper.QuestionMapper;
import com.graduation_project.testforum.mapper.UserMapper;
import com.graduation_project.testforum.model.Question;
import com.graduation_project.testforum.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class QuestionService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;

    public List<QuestionDTO> queryQuestionList() {
        List<Question> questionList = questionMapper.queryQuestionList();
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questionList) {
            System.out.println(question.getCreatorId());
            User user = userMapper.queryAvatarUrlById(question.getCreatorId());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        return questionDTOList;
    }
}
