package com.lvji.lvjioj.judge.codesandboxservice;

import com.lvji.lvjioj.model.dto.questionsubmit.JudgeInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 代码沙箱响应结果包装类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExecuteCodeResponse {

    /**
     * 沙箱执行信息
     */
    private String Message;

    /**
     * 执行状态
     */
    private Integer status;

    /**
     * 判题（代码）执行信息
     */
    private JudgeInfo judgeInfo;

    /**
     * 判题结果
     */
    private List<String> outputList;

}
