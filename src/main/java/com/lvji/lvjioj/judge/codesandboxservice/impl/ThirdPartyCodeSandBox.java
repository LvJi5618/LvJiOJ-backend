package com.lvji.lvjioj.judge.codesandboxservice.impl;

import com.lvji.lvjioj.judge.codesandboxservice.CodeSandBox;
import com.lvji.lvjioj.judge.codesandboxservice.ExecuteCodeRequest;
import com.lvji.lvjioj.judge.codesandboxservice.ExecuteCodeResponse;

/**
 * 第三方代码沙箱 -> 调用网上写好的代码沙箱服务
 */
public class ThirdPartyCodeSandBox implements CodeSandBox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("第三方代码沙箱");
        return null;
    }
}
