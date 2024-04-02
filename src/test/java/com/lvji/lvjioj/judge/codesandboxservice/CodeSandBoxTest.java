package com.lvji.lvjioj.judge.codesandboxservice;

import com.lvji.lvjioj.judge.codesandboxservice.impl.ExampleCodeSandBox;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
class CodeSandBoxTest {
    @Value("${codesandbox.type}")
    private String type;

    @Test
    void executeCode() {
        CodeSandBox codeSandBox = new ExampleCodeSandBox();
        String code = "int main{}";
        List<String> inputList = Arrays.asList("1 2", "3 4");
        String language = "java";
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder().
                code(code).
                inputList(inputList).
                language(language).
                build();
        ExecuteCodeResponse executeCodeResponse = codeSandBox.executeCode(executeCodeRequest);
        Assertions.assertNotNull(executeCodeResponse);
    }

    @Test
    void executeCodeByFactory() {
        CodeSandBox codeSandBox = CodeSandBoxFactory.newInstance(type);
        String code = "int main{}";
        List<String> inputList = Arrays.asList("1 2", "3 4");
        String language = "java";
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder().
                code(code).
                inputList(inputList).
                language(language).
                build();
        ExecuteCodeResponse executeCodeResponse = codeSandBox.executeCode(executeCodeRequest);
        Assertions.assertNotNull(executeCodeResponse);
    }

    @Test
    void executeCodeByProxy() {
        CodeSandBoxProxy codeSandBoxProxy = new CodeSandBoxProxy(CodeSandBoxFactory.newInstance(type));
        String code = "int main{}";
        List<String> inputList = Arrays.asList("1 2", "3 4");
        String language = "java";
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder().
                code(code).
                inputList(inputList).
                language(language).
                build();
        ExecuteCodeResponse executeCodeResponse = codeSandBoxProxy.executeCode(executeCodeRequest);
        Assertions.assertNotNull(executeCodeResponse);
    }
}