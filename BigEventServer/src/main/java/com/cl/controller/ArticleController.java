package com.cl.controller;

import com.cl.pojo.Result;
import com.cl.service.ArticleService;
import com.cl.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/list")
    public Result list(@RequestHeader(name = "Authorization") String token, HttpServletResponse response){
        //验证token
        try {
            Map<String, Object> claims = JwtUtil.parseToken(token);
            return Result.success("查询所有文章数据");
        }catch (Exception e){
            response.setStatus(401);
            return Result.error("未登录或登录信息过期！");
        }

    }
}
