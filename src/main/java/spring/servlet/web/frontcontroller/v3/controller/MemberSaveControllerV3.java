package spring.servlet.web.frontcontroller.v3.controller;

import spring.servlet.domain.member.Member;
import spring.servlet.domain.member.MemberRepository;
import spring.servlet.web.frontcontroller.ModelView;
import spring.servlet.web.frontcontroller.v3.ControllerV3;

import java.util.Map;

public class MemberSaveControllerV3 implements ControllerV3 {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public ModelView process(Map<String, String> paraMap) {
        String username = paraMap.get("username");
        int age = Integer.parseInt(paraMap.get("age"));

        Member member = new Member(username, age);
        memberRepository.save(member);

        ModelView modelView = new ModelView("save-result");
        modelView.getModel().put("member", member);
        return modelView;
    }
}
