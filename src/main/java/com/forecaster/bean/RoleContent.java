package com.forecaster.bean;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: ChengLiang
 * @CreateTime: 2023-10-17  13:33
 * @Description: TODO
 * @Version: 1.0
 */
@Data

@NoArgsConstructor
public class RoleContent {

    public static final String ROLE_USER = "user";

    public static final String ROLE_ASSISTANT = "assistant";

    private String role;

    private String content;

    @Getter
    private static List<String> question=new ArrayList<>();
    @Getter
    private  static List<String> answer=new ArrayList<>();


    //定义一个构造函数用于构造每一次角色和对应的答案
    RoleContent(String role, String content){
        this.role=role;
        this.content=content;
    }


    public static RoleContent createUserRoleContent(String content) {
        return new RoleContent(ROLE_USER, content);
    }

    public static RoleContent createAssistantRoleContent(String content) {
        return new RoleContent(ROLE_ASSISTANT, content);
    }

    //创建一个用于记录问题的函数
    public static void recordQuestion(String text) {
        question.add(text);
    }

    //创建一个用于记录答案的函数
    public static  void recordAnswer(String GptAnswer){
        answer.add(GptAnswer);
    }

}
