package com.iyyxx.controller.utils;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @className: DownLoadUtils
 * @description: TODO 类描述
 * @author: eric 4575252@gmail.com
 * @date: 2022/10/26/0026 8:47:20
 **/
public class DownLoadUtils {
    public static void downloadFile(String filePath, ServletContext context, HttpServletResponse response) throws Exception {
        String realPath = context.getRealPath(filePath);
        File file = new File(realPath);
        String fileName = file.getName();
        FileInputStream fileInputStream = new FileInputStream(realPath);
        String mimeType = context.getMimeType(fileName);
        response.setHeader("Content-type", mimeType);
        String fname= URLEncoder.encode(fileName, "UTF-8");
        response.setHeader("Content-disposition","attachment;filename="+fname+";filename*=utf-8''"+fname);
        ServletOutputStream servletOutputStream = response.getOutputStream();
        byte[] buff = new byte[1024 * 8];
        int len =0;
        while((len= fileInputStream.read(buff))!= -1){
            servletOutputStream.write(buff,0,len);
        }
        servletOutputStream.close();
        fileInputStream.close();
    }
}
