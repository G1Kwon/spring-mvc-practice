package spring.servlet.web.frontcontroller.v5.adapter;

import spring.servlet.web.frontcontroller.ModelView;
import spring.servlet.web.frontcontroller.v3.ControllerV3;
import spring.servlet.web.frontcontroller.v5.MyHandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ControllerV3HandlerAdapter implements MyHandlerAdapter {

    @Override
    public boolean supports(Object handler) {
        return (handler instanceof ControllerV3);
    }

    @Override
    public ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {
        ControllerV3 controller = (ControllerV3) handler;

        Map<String, String> paraMap = createParamMap(request);
        ModelView modelView = controller.process(paraMap);

        return modelView;
    }

    private Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paraMap = new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(paraName -> paraMap.put(paraName, request.getParameter(paraName)));
        return paraMap;
    }

}
