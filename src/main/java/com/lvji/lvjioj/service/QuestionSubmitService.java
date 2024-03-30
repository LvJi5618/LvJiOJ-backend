package com.lvji.lvjioj.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lvji.lvjioj.model.dto.question.QuestionQueryRequest;
import com.lvji.lvjioj.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.lvji.lvjioj.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.lvji.lvjioj.model.entity.Question;
import com.lvji.lvjioj.model.entity.QuestionSubmit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lvji.lvjioj.model.entity.User;
import com.lvji.lvjioj.model.vo.QuestionSubmitVO;
import com.lvji.lvjioj.model.vo.QuestionVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author 常俊杰
* @description 针对表【question_submit(题目提交)】的数据库操作Service
* @createDate 2024-03-30 20:03:00
*/
public interface QuestionSubmitService extends IService<QuestionSubmit> {
    /**
     * 题目提交
     *
     * @param questionSubmitAddRequest
     * @param loginUser
     * @return
     */
    long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser);

    /**
     * 获取查询条件
     *
     * @param questionSubmitQueryRequest
     * @return
     */
    QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest);

    /**
     * 获取题目提交信息封装对象
     *
     * @param questionSubmit
     * @param loginUser
     * @return
     */
    QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit, User loginUser);

    /**
     * 分页获取题目提交信息封装对象
     *
     * @param questionSubmitPage
     * @param loginUser
     * @return
     */
    Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage, User loginUser);
}
