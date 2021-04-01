package spring.servlet.web.frontcontroller.v4;

import spring.servlet.web.frontcontroller.ModelView;
import spring.servlet.web.frontcontroller.MyView;
import spring.servlet.web.frontcontroller.v3.ControllerV3;
import spring.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import spring.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import spring.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import spring.servlet.web.frontcontroller.v4.controller.MemberFormControllerV4;
import spring.servlet.web.frontcontroller.v4.controller.MemberListControllerV4;
import spring.servlet.web.frontcontroller.v4.controller.MemberSaveControllerV4;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// /front-controller/v3/members/new-form
@WebServlet(name = "frontControllerServletV4", urlPatterns = "/front-controller/v4/*")
public class FrontControllerServletV4 extends HttpServlet {

    private Map<String, ControllerV4> controllerMap = new HashMap<>();

    public FrontControllerServletV4() {
        controllerMap.put("/front-controller/v4/members/new-form", new MemberFormControllerV4());
        controllerMap.put("/front-controller/v4/members/save", new MemberSaveControllerV4());
        controllerMap.put("/front-controller/v4/members", new MemberListControllerV4());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControllerServletV4.service");

        // /front-controller/v4/members
        String requestURI = request.getRequestURI();
        //부모가 controllerv4 이기에 다형성에 의해서 인터페이스로 받을수 있다
        //원래 코드는 ControllerV4 controller = new MemberListControllerV2();
        ControllerV4 controller = controllerMap.get(requestURI);
        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        //paraMap을 넘겨주어야 한다.
        Map<String, String> paraMap = createParamMap(request);
        Map<String, Object> model = new HashMap<>();
        String viewName = controller.process(paraMap, model);

        MyView myView = viewResolver(viewName);
        myView.render(model, request, response);
    }

    private MyView viewResolver(String viewName) {
        MyView myView = new MyView("/WEB-INF/views/" + viewName + ".jsp");
        return myView;
    }

    private Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paraMap = new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(paraName -> paraMap.put(paraName, request.getParameter(paraName)));
        return paraMap;
    }
}
