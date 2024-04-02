package com.lvji.lvjioj.judge.judgeservice.impl;

import cn.hutool.json.JSONUtil;
import com.lvji.lvjioj.common.ErrorCode;
import com.lvji.lvjioj.exception.BusinessException;
import com.lvji.lvjioj.judge.codesandboxservice.CodeSandBoxFactory;
import com.lvji.lvjioj.judge.codesandboxservice.CodeSandBoxProxy;
import com.lvji.lvjioj.judge.codesandboxservice.ExecuteCodeRequest;
import com.lvji.lvjioj.judge.codesandboxservice.ExecuteCodeResponse;
import com.lvji.lvjioj.judge.judgeservice.JudgeService;
import com.lvji.lvjioj.judge.judgeservice.strategy.JudgeContext;
import com.lvji.lvjioj.judge.judgeservice.strategy.JudgeStrategyManager;
import com.lvji.lvjioj.model.dto.question.JudgeCase;
import com.lvji.lvjioj.model.dto.question.JudgeConfig;
import com.lvji.lvjioj.model.dto.questionsubmit.JudgeInfo;
import com.lvji.lvjioj.model.entity.Question;
import com.lvji.lvjioj.model.entity.QuestionSubmit;
import com.lvji.lvjioj.model.enums.QuestionSubmitJudgeInfoMessageEnum;
import com.lvji.lvjioj.model.enums.QuestionSubmitStatusEnum;
import com.lvji.lvjioj.service.QuestionService;
import com.lvji.lvjioj.service.QuestionSubmitService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JudgeServiceImpl implements JudgeService {

    @Resource
    QuestionSubmitService questionSubmitService;

    @Resource
    QuestionService questionService;

    @Value("${codesandbox.type}")
    private String type;

    @Resource
    JudgeStrategyManager judgeStrategyManager;

    /**
     * 判题
     * @param questionSubmitId
     * @return
     */
    @Override
    public QuestionSubmit doJudge(long questionSubmitId) {
        QuestionSubmit questionSubmitUpdate = new QuestionSubmit();
        /**
         * 1. 从question_submit表中，获取到对应的题目id、用户代码执行信息（JudgeInfo）
         */
        QuestionSubmit questionSubmit = questionSubmitService.getById(questionSubmitId);
        if(questionSubmit == null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"题目提交信息不存在");
        }
        String language = questionSubmit.getLanguage();
        Long questionId = questionSubmit.getQuestionId();
        Question question = questionService.getById(questionId);
        if(question == null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"题目不存在");
        }
        String judgeConfigStr = question.getJudgeConfig();
        JudgeConfig judgeConfig = JSONUtil.toBean(judgeConfigStr, JudgeConfig.class);
        String judgeInfoStr = questionSubmit.getJudgeInfo();
        JudgeInfo judgeInfo = JSONUtil.toBean(judgeInfoStr, JudgeInfo.class);
        /**
         * 2. 若题目提交状态不为 “等待中” ，无需重复执行判题服务
         */
        Integer status = questionSubmit.getStatus();
        if(QuestionSubmitStatusEnum.WAITING.getValue() != status){
            throw new BusinessException(ErrorCode.OPERATION_ERROR,"该题目正在判题中,无需重复提交");
        }
        /**
         * 3. 修改题目提交状态为“判题中”，防止重复执行判题服务
         */
        status = QuestionSubmitStatusEnum.RUNNING.getValue();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(status);
        boolean update = questionSubmitService.updateById(questionSubmitUpdate);
        if(!update){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目状态更新错误");
        }
        /**
         * 4. 调用代码沙箱服务，获取到沙箱执行结果
         */
        CodeSandBoxProxy codeSandBoxProxy = new CodeSandBoxProxy(CodeSandBoxFactory.newInstance(type));
        String judgeCaseStr = question.getJudgeCase();
        List<JudgeCase> judgeCases = JSONUtil.toList(judgeCaseStr, JudgeCase.class);
        List<String> inputList = judgeCases.stream().map(JudgeCase::getInput).collect(Collectors.toList());
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder().
                code(questionSubmit.getCode()).
                language(language).
                inputList(inputList).
                build();
        ExecuteCodeResponse executeCodeResponse = codeSandBoxProxy.executeCode(executeCodeRequest);
        List<String> actualOutputList = executeCodeResponse.getOutputList();
        /**
         * 5. 根据设定的判题策略,判断沙箱执行结果
         */
        JudgeContext judgeContext = new JudgeContext();
        judgeContext.setJudgeCases(judgeCases);
        judgeContext.setActualOutputList(actualOutputList);
        judgeContext.setJudgeInfo(executeCodeResponse.getJudgeInfo());
        judgeContext.setJudgeConfig(judgeConfig);
        JudgeInfo judgeInfoResult = judgeStrategyManager.doJudge(judgeContext, language);
        /**
         * 6. 设置题目的判题状态和判题执行信息
          */
        status = QuestionSubmitStatusEnum.SUCCEED.getValue();
        questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(status);
        String judgeInfoResultStr = JSONUtil.toJsonStr(judgeInfoResult);
        questionSubmitUpdate.setJudgeInfo(judgeInfoResultStr);
        update = questionSubmitService.updateById(questionSubmitUpdate);
        if(!update){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目状态更新错误");
        }
        QuestionSubmit questionSubmitResult = questionSubmitService.getById(questionSubmitId);
        return questionSubmitResult;
    }
}
