package com.Code.servlet;

import com.Code.bean.Edu;
import com.Code.bean.Result;
import com.Code.service.DBService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;//该注解用于将一个类声明为Servlet类
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/v1/edu/insert")//指定servlet的name属性,等价于<Servlet-name>.如果没有显示指定，则该servlet的取值即为类的全限定名
public class EduServlet extends HttpServlet {
    //HttpServlet是Servlet接口的一个实现类，并且它是一个抽象类，servlet.http包中定义了采用HTTP通信协议(一个无状态协议)的HttpServlet类。

    /*  响应流程
1.Web客户向Servlet容器发出Http请求
2.Servlet容器解析Web客户的Http请求
3.Servlet容器创建一个HttpRequest对象，在这个对象中封装Http请求信息
4.Servlet容器创建一个HttpResponse对象
5.Servlet容器调用HttpServlet的service方法，把HttpRequest和HttpResponse对象作为service方法的参数传给HttpServlet对象
6.HttpServlet调用HttpRequest的有关方法，获取HTTP请求信息
7.HttpServlet调用HttpResponse的有关方法，生成响应数据
8.Servlet容器把HttpServlet的响应结果传给Web客户
     */
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
