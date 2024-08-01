package com.forecaster.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

public class ImageUtils {

    /**
     * 将解码后的字节数组保存为图片文件。
     *
     * @param imageBytes 解码后的字节数组。
     * @param filePath   要保存图片的文件路径。
     * @throws IOException 如果写入文件时发生错误。
     */
    public static void saveImageFromBytes(byte[] imageBytes, String filePath) throws IOException {
        // 使用 ByteArrayInputStream 从字节数组读取图片
        ByteArrayInputStream bais = new ByteArrayInputStream(imageBytes);
        BufferedImage image = ImageIO.read(bais);

        // 确保文件路径的目录存在
        File file = new File(filePath).getParentFile();
        if (!file.exists()) {
            file.mkdirs();
        }

        // 将图片保存到指定的文件路径
        ImageIO.write(image, "png", new File(filePath));
    }
}