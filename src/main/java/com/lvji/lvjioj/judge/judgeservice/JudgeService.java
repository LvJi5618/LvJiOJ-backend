package com.lvji.lvjioj.judge.judgeservice;

import com.lvji.lvjioj.model.entity.QuestionSubmit;

/**
 * 判题服务接口
 */
public interface JudgeService {

    /**
     * 判题
     * @param questionSubmitId
     * @return
     */
    QuestionSubmit doJudge(long questionSubmitId);

}
