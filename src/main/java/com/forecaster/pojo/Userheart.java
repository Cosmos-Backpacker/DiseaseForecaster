package com.forecaster.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import com.baomidou.mybatisplus.core.toolkit.reflect.SpringReflectionHelper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author CosmosBackpacker
 * @since 2024-01-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("userheart")
public class Userheart implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id,和用户信息绑定
     */
    @TableId(value = "id", type = IdType.INPUT)
    private Integer id;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 性别
     */
    private String gender;

    /**
     * 胸部疼痛情况（1：典型心绞痛；2：非典型心绞痛；3：没有心绞痛；4：无症状）
     */
    private Integer cp;

    /**
     * 静息血压
     */
    private Integer trestbps;

    /**
     * 胆固醇
     */
    private Integer chol;

    /**
     * 空腹血糖>120mg（1：true；2：false）
     */
    private Integer fbs;

    /**
     * 静息心电图测量（0：普通；1：ST-T波异常；2：左心室肥大）
     */
    private Integer restecg;

    /**
     * 最高心跳率
     */
    private Integer thalach;

    /**
     * 运动诱发心绞痛（1：yes；2：no）
     */
    private Integer exang;

    /**
     * 运动相对于休息引起的ST抑制
     */
    private Float oldpeak;

    /**
     * 运动ST段的峰值斜率（1：上坡；2：平的；3：下坡）
     */
    private Integer slope;

    /**
     * 主要血管数目（0、1、2、3、4）
     */
    private Integer ca;

    /**
     * 一种血液疾病（1：正常；2：固定缺陷；3：可逆的缺陷）
     */
    private Integer thal;

    /**
     * 0.不患病，1.患病
     */
    private Integer target;
    /**
     * uid用于绑定用户id
     */
    private Integer uid;

    public String dataToString() {

        String data = (this.age.toString() + " " + this.gender + " " + this.cp.toString() + " " + this.trestbps.toString()
                + " " + this.chol.toString() + " " + this.fbs.toString() + " " + this.restecg.toString() + " " + this.thalach.toString()
                + " " + this.exang.toString() + " " + this.oldpeak.toString() + " " + this.slope.toString() + " " + this.ca.toString()
                + " " + this.thal.toString());


        return data;

    }


}
