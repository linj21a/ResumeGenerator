package com.Code.servlet;

import com.Code.bean.Result;
import com.Code.bean.User;
import com.Code.service.DBService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/v1/user/insert")
public class UserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.    处理乱码,把编码字典设置为utf-8
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");
        //2.    接收前端发送过来的数据内容
        String name = request.getParameter("name");
        String ageText = request.getParameter("age");
        int age = 0;
        try {
            age = Integer.parseInt(ageText);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        String city = request.getParameter("city");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String weixin = request.getParameter("weixin");
        String qq = request.getParameter("qq");
        String weibo = request.getParameter("weibo");
        String sex = request.getParameter("sex");
        String description = request.getParameter("description");
        //3.    把接收的内容组装为bean
        User user = new User(name,age,city,address,email,phone,weixin,qq,weibo,sex,description);
        //4.    把bean交给service（service->dao->dbutil->数据库）
        int id = DBService.insertUser(user);
        //5.    根据service代码的执行结果，组装Result
        Result result = null;
        if(id>0){
            //存储新用户基本信息成功
            result = new Result(0,"用户新增成功",id);
        }else{
            //存储新用户基本信息失败
            result = new Result(-1,"用户新增失败",id);
        }
        //6.    把Result的JSON格式文字，回复给前端
        response.getWriter().append(result.toJSON());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
