package spring.servlet.web.frontcontroller.v4;

import java.util.Map;

public interface ControllerV4 {

    /**
     * @param paraMap
     * @param model
     * @return
     */
    String process(Map<String, String> paraMap, Map<String, Object> model);
}
