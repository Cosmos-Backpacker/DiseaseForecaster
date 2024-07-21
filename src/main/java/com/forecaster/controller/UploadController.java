package com.forecaster.controller;

import com.forecaster.pojo.Result;

import com.forecaster.service.XFService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/upload")
public class UploadController {

    //测试
    @Autowired
    private com.forecaster.service.XFService XFService;

    @PostMapping("/image")
    public Result handleFileUpload(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                // 获取文件名
                String fileName = file.getOriginalFilename();
                // 获取文件的二进制字节
                byte[] bytes = file.getBytes();

                System.out.println(bytes.toString());
                //假设保存成功
                return Result.success("文件上传成功：" + fileName);
            } catch (Exception e) {
                return Result.error("文件上传失败：" + e.getMessage());
            }
        } else {
            return Result.error("文件为空，请选择文件上传");
        }
    }
}