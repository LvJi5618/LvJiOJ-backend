package com.lvji.lvjioj.judge.judgeservice.strategy;


import com.lvji.lvjioj.model.dto.question.JudgeCase;
import com.lvji.lvjioj.model.dto.question.JudgeConfig;
import com.lvji.lvjioj.model.dto.questionsubmit.JudgeInfo;
import com.lvji.lvjioj.model.entity.Question;
import lombok.Data;

import java.util.List;

@Data
public class JudgeContext {

    /**
     * 判题用例
     */
    private List<JudgeCase> judgeCases;

    /**
     * 代码沙箱执行结果
     */
    private List<String> actualOutputList;

    /**
     * 判题执行信息
     */
    private JudgeInfo judgeInfo;

    /**
     * 判题配置
     */
    private JudgeConfig judgeConfig;

}
