package com.forecaster.controller;

import com.forecaster.pojo.Result;
import com.forecaster.utils.PythonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/algorithm")
public class AlgorithmController {

    @Autowired
    private PythonUtils pythonUtils;

    @GetMapping("/heart")
    public Result algorithm(Integer uid, Integer index) {
        return pythonUtils.userheartAlgorithm(uid, index);
    }


    @GetMapping("/question")
    public Result questionPython(String question){

        return pythonUtils.pythonQuestion(question);
    }




}
