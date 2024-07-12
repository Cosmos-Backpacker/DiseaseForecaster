package com.forecaster.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author CosmosBackpacker
 * @since 2024-01-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("advice")
@AllArgsConstructor
@NoArgsConstructor
public class Advice implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String diet;

    private String motion;

    private String habits;

    private String psychology;


    public Advice(String diet, String motion, String habits, String psychology)
    {
        this.diet=diet;
        this.motion=motion;
        this.habits=habits;
        this.psychology=psychology;

    }

}
