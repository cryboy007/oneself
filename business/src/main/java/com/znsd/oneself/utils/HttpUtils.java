package com.znsd.oneself.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * @ClassName HttpUtils
 * @Author tao.he
 * @Since 2022/6/27 12:26
 */
public class HttpUtils {

    public static void writeData(String fileName,Object data, HttpServletResponse response) throws IOException {
        response.setContentType("application/x-download;charset=utf-8");
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString()));


    }

}
