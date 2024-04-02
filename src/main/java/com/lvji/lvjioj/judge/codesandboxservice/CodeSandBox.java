package com.lvji.lvjioj.judge.codesandboxservice;

/**
 * 代码沙箱服务接口
 */
public interface CodeSandBox {

    /**
     * 执行代码
     * @param executeCodeRequest
     * @return
     */
    ExecuteCodeResponse executeCode (ExecuteCodeRequest executeCodeRequest);
}
