package com.forecaster.controller;
import com.forecaster.pojo.Advice;
import com.forecaster.pojo.User;
import com.forecaster.service.IAdviceService;
import com.forecaster.service.IUserService;
import com.forecaster.utils.PdfUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author CosmosBackpacker
 * @since 2024-01-29
 */
@RestController
@RequestMapping("/advice")

public class AdviceController {
    @Autowired
    private IAdviceService adviceService;

    @Autowired
    private IUserService userService;

    @GetMapping("/heartPdf")
    public void downloadPDF(HttpServletRequest request, HttpServletResponse response, Integer id) throws Exception {

        //确定用户
        User user = userService.getById(id);
        //先通过已有的数据随机组合成一个新建议的数据
        Advice heartAdvice = adviceService.getHeartByRandom();
        PdfUtil.ExportPdf(request, response, user, heartAdvice);
        System.out.println("调用成功");
    }


}
