package com.iyyxx.controller;

import com.iyyxx.controller.utils.DownLoadUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @className: TestController
 * @description: TODO 类描述
 * @author: eric 4575252@gmail.com
 * @date: 2022/10/24/0024 16:59:57
 **/
@Controller
@SessionAttributes({"xxx"})
public class TestController {

    @RequestMapping("/hello")
    public String hello(String name){
        System.out.println("==================/hello");
        System.out.println(name);
        return "/success.jsp";
    }


    @RequestMapping("/testJump")
    public String testJump(){
        return "/success.jsp";
    }

    @RequestMapping("/testJump2")
    public String testJump2(){
        return "forward:/success.jsp";
    }

    @RequestMapping("/testJump3")
    public String testJump3(){
        return "redirect:/success.jsp";
    }

    @RequestMapping("/testJumpJsp")
    public String testJumpJsp(){
        return "forward:/WEB-INF/page/test.jsp";
    }

    @RequestMapping("/testJumpJsp2")
    public String testJumpJsp2(){
        return "test";
    }

    @RequestMapping("/testRequestScope")
    public String testRequestScope(Model model){
        model.addAttribute("xxx","hello");
        model.addAttribute("yyy","model");
        return "requestScope";
    }

    @RequestMapping("/testRequestScope2")
    public ModelAndView testRequestScope2(ModelAndView modelAndView){
        modelAndView.addObject("xxx","hello");
        modelAndView.addObject("yyy","model and view");
        modelAndView.setViewName("requestScope");
        return modelAndView;
    }

    @RequestMapping("/testSessionScope")
    public String testSessionScope(Model model){
        model.addAttribute("xxx","hello");
        model.addAttribute("yyy","model");
        return "requestScope";
    }

    @RequestMapping(value = "/testUpload", method = RequestMethod.POST)
    public String testUpload(MultipartFile uploadFile, HttpServletRequest request) throws IOException {
        String filename = UUID.randomUUID().toString().replace("-","") + uploadFile.getOriginalFilename().toString();
        uploadFile.transferTo(new File(request.getSession().getServletContext().getRealPath("/")
                +"/WEB-INF/file/"+filename));
        return "forward:/success.jsp";
    }

    @RequestMapping(value = "/testDownload")
    public void testDownload(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DownLoadUtils.downloadFile("/WEB-INF/file/中文测试.sql",
                request.getServletContext(), response);
    }
}
