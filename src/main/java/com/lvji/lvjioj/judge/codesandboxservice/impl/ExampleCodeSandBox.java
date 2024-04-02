package com.lvji.lvjioj.judge.codesandboxservice.impl;
import com.lvji.lvjioj.model.dto.questionsubmit.JudgeInfo;

import com.lvji.lvjioj.judge.codesandboxservice.CodeSandBox;
import com.lvji.lvjioj.judge.codesandboxservice.ExecuteCodeRequest;
import com.lvji.lvjioj.judge.codesandboxservice.ExecuteCodeResponse;
import com.lvji.lvjioj.model.enums.QuestionSubmitJudgeInfoMessageEnum;
import com.lvji.lvjioj.model.enums.QuestionSubmitStatusEnum;

import java.util.List;

/**
 * 示例代码沙箱 -> 仅为了跑通业务
 */
public class ExampleCodeSandBox implements CodeSandBox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("示例代码沙箱");
        List<String> outputList = executeCodeRequest.getInputList();
        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        executeCodeResponse.setMessage("测试示例代码沙箱执行成功");
        executeCodeResponse.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setMessage(QuestionSubmitJudgeInfoMessageEnum.ACCEPTED.getText());
        judgeInfo.setTime(1000l);
        judgeInfo.setMemory(1000l);
        executeCodeResponse.setJudgeInfo(judgeInfo);
        executeCodeResponse.setOutputList(outputList);
        return executeCodeResponse;
    }
}
