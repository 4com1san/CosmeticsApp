package com1san.cosmeticsApp.controller;

import com1san.cosmeticsApp.domain.Member;
import com1san.cosmeticsApp.service.MemberService;
import com1san.cosmeticsApp.web.MemberForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping(value="/members/new")
    public String createForm(Model model){
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }
    @PostMapping(value="/members/new")
    public String create(@Validated MemberForm form, BindingResult result){
        if (result.hasErrors()) {
            return "members/createMemberForm";
        }
        Member member = new Member();
        member.setName(form.getName());
        member.setPassword(form.getPassword());
        member.setNickname(form.getNickname());
        memberService.join(member);
        return "redirect:/";
    }
    @PostMapping(value="/login")
    public String login(Model model){
        return "logintest";
    }
}
