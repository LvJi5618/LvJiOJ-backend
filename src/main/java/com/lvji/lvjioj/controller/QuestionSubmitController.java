package com.lvji.lvjioj.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lvji.lvjioj.annotation.AuthCheck;
import com.lvji.lvjioj.common.BaseResponse;
import com.lvji.lvjioj.common.ErrorCode;
import com.lvji.lvjioj.common.ResultUtils;
import com.lvji.lvjioj.constant.UserConstant;
import com.lvji.lvjioj.exception.BusinessException;
import com.lvji.lvjioj.model.dto.question.QuestionQueryRequest;
import com.lvji.lvjioj.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.lvji.lvjioj.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.lvji.lvjioj.model.entity.Question;
import com.lvji.lvjioj.model.entity.QuestionSubmit;
import com.lvji.lvjioj.model.entity.User;
import com.lvji.lvjioj.model.vo.QuestionSubmitVO;
import com.lvji.lvjioj.service.QuestionSubmitService;
import com.lvji.lvjioj.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 题目提交接口
 *
 */
@RestController
@RequestMapping("/question_submit")
@Slf4j
public class QuestionSubmitController {

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Resource
    private UserService userService;

    /**
     * 提交题目
     *
     * @param questionSubmitAddRequest
     * @param request
     * @return 提交记录的id
     */
    @PostMapping("/")
    public BaseResponse<Long> doQuestionSubmit(@RequestBody QuestionSubmitAddRequest questionSubmitAddRequest,
            HttpServletRequest request) {
        if (questionSubmitAddRequest == null || questionSubmitAddRequest.getQuestionId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 登录才能提交题目
        final User loginUser = userService.getLoginUser(request);
        long result = questionSubmitService.doQuestionSubmit(questionSubmitAddRequest, loginUser);
        return ResultUtils.success(result);
    }

    /**
     * 分页获取题目提交列表（非管理员、非本人不能查看题目提交信息中的提交代码）
     *
     * @param questionSubmitQueryRequest
     * @return
     */
    @PostMapping("/list/page")
    public BaseResponse<Page<QuestionSubmitVO>> listQuestionByPage(@RequestBody QuestionSubmitQueryRequest questionSubmitQueryRequest, HttpServletRequest request) {
        long current = questionSubmitQueryRequest.getCurrent();
        long size = questionSubmitQueryRequest.getPageSize();
        // 1.先查询
        Page<QuestionSubmit> questionSubmitPage = questionSubmitService.page(new Page<>(current, size),
                questionSubmitService.getQueryWrapper(questionSubmitQueryRequest));
        final User loginUser = userService.getLoginUser(request);
        // 2.再根据权限脱敏
        Page<QuestionSubmitVO> questionSubmitVOPage = questionSubmitService.getQuestionSubmitVOPage(questionSubmitPage, loginUser);
        return ResultUtils.success(questionSubmitVOPage);
    }
}
