package com.lvji.lvjioj.judge.judgeservice.strategy;

import com.lvji.lvjioj.model.dto.questionsubmit.JudgeInfo;

/**
 * 判题策略
 */
public interface JudgeStrategy {

    JudgeInfo doJudge(JudgeContext judgeContext);
}
