package com.Code.servlet;

import com.Code.bean.Edu;
import com.Code.bean.Result;
import com.Code.service.DBService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/v1/edu/insert")
public class EduServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1、设置编码
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");

        //2、获取提交数据

        int userId = 0;
        String userIdText = request.getParameter("userid");
        if(userIdText != null){
            try {
                userId = Integer.parseInt(userIdText);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        String start = request.getParameter("start");
        String end = request.getParameter("end");
        String school = request.getParameter("school");
        String study = request.getParameter("study");
        String description = request.getParameter("description");

        //3、更新到数据库，返回更新结果
        Edu edu = new Edu(userId,start,end,school,study,description);
        int row = DBService.insertEdu(edu);
        Result result = null;
        if(row>0){
            //插入成功
            result = new Result(0,"学历新增成功");
        }else{
            //插入失败
            result = new Result(-1,"学历新增失败");
        }
        //4、返回结果（json类型）给前台
        response.getWriter().append(result.toJSON());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
