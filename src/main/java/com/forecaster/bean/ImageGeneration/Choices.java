package com.forecaster.bean.ImageGeneration;

import java.util.List;

import lombok.Data;


@Data
public class Choices {
    private int status;
    private int seq;
    private List<Text> text;
}
