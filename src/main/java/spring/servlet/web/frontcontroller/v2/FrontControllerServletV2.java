package spring.servlet.web.frontcontroller.v2;

import spring.servlet.web.frontcontroller.MyView;
import spring.servlet.web.frontcontroller.v2.controller.MemberFormControllerV2;
import spring.servlet.web.frontcontroller.v2.controller.MemberListControllerV2;
import spring.servlet.web.frontcontroller.v2.controller.MemberSaveControllerV2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// /front-controller/v2/members/new-form
@WebServlet(name = "frontControllerServletV2", urlPatterns = "/front-controller/v2/*")
public class FrontControllerServletV2 extends HttpServlet {

    private Map<String, ControllerV2> controllerMap = new HashMap<>();

    public FrontControllerServletV2() {
        controllerMap.put("/front-controller/v2/members/new-form", new MemberFormControllerV2());
        controllerMap.put("/front-controller/v2/members/save", new MemberSaveControllerV2());
        controllerMap.put("/front-controller/v2/members", new MemberListControllerV2());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControllerServletV2.service");

        // /front-controller/v2/members
        String requestURI = request.getRequestURI();
        //부모가 controllerv2 이기에 다형성에 의해서 인터페이스로 받을수 있다
        //원래 코드는 ControllerV2 controller = new MemberListControllerV2();
        ControllerV2 controller = controllerMap.get(requestURI);
        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        //다형성에 의해 override 된 메서드가 호출이 된다.
        MyView view = controller.process(request, response);
        view.render(request, response);
    }
}
