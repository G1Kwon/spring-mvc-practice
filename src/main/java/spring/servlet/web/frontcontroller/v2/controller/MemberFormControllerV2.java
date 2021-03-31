package spring.servlet.web.frontcontroller.v2.controller;

import spring.servlet.web.frontcontroller.MyView;
import spring.servlet.web.frontcontroller.v2.ControllerV2;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MemberFormControllerV2 implements ControllerV2 {

    @Override
    public MyView  process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
/*        MyView myView = new MyView("WEB-INF/views/new-form.jsp");
        return myView;*/
        return new MyView("/WEB-INF/views/new-form.jsp");
    }
}
