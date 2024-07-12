package com.forecaster.query;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserQuery extends PageQuery {
    //接收昵称信息
    private String nickname;
    //接收手机信息
    private String phone;
    //接收账号信息
    private String account;
    //接收
    private String email;
    //接收性别
    private String gender;

}
