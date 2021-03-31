package spring.servlet.web.frontcontroller.v3;

import spring.servlet.web.frontcontroller.ModelView;
import spring.servlet.web.frontcontroller.MyView;
import spring.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import spring.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import spring.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// /front-controller/v3/members/new-form
@WebServlet(name = "frontControllerServletV3", urlPatterns = "/front-controller/v3/*")
public class FrontControllerServletV3 extends HttpServlet {

    private Map<String, ControllerV3> controllerMap = new HashMap<>();

    public FrontControllerServletV3() {
        controllerMap.put("/front-controller/v3/members/new-form", new MemberFormControllerV3());
        controllerMap.put("/front-controller/v3/members/save", new MemberSaveControllerV3());
        controllerMap.put("/front-controller/v3/members", new MemberListControllerV3());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControllerServletV3.service");

        // /front-controller/v3/members
        String requestURI = request.getRequestURI();
        //부모가 controllerv3 이기에 다형성에 의해서 인터페이스로 받을수 있다
        //원래 코드는 ControllerV3 controller = new MemberListControllerV2();
        ControllerV3 controller = controllerMap.get(requestURI);
        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        //paraMap을 넘겨주어야 한다.
        Map<String, String> paraMap = createParamMap(request);
        ModelView modelView = controller.process(paraMap);

        String viewName = modelView.getViewName();// 논리이름 new-form 만 얻을 수 있다.
        MyView myView = viewResolver(viewName);
        myView.render(modelView.getModel(), request, response);
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
