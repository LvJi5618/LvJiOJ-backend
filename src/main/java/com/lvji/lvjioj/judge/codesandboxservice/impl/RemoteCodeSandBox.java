package com.lvji.lvjioj.judge.codesandboxservice.impl;

import com.lvji.lvjioj.judge.codesandboxservice.CodeSandBox;
import com.lvji.lvjioj.judge.codesandboxservice.ExecuteCodeRequest;
import com.lvji.lvjioj.judge.codesandboxservice.ExecuteCodeResponse;

/**
 * 远程代码沙箱 -> 判题业务实际调用的代码沙箱接口
 */
public class RemoteCodeSandBox implements CodeSandBox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("远程代码沙箱");
        return null;
    }
}
