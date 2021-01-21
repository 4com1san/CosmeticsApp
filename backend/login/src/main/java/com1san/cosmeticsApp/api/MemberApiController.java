package com1san.cosmeticsApp.api;

import com1san.cosmeticsApp.domain.Member;
import com1san.cosmeticsApp.domain.SkinStatus;
import com1san.cosmeticsApp.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;

    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Validated Member member)
    {
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }
    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Validated
                                                     CreateMemberRequest request) {
        Member member = new Member();
        member.setName(request.getName());
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }
    @Data
    static class CreateMemberRequest {
        private String nickname;
        private String name;
        private Long password;

    }
    @Data
    class CreateMemberResponse {
        private Long id;
        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }
    /*
    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(@PathVariable("id") Long id,
                                               @RequestBody @Validated UpdateMemberRequest request) {
        memberService.update(id, request.getName(), request.getNickname(), request.getPassword());
        Member findMember = memberService.findOne(id);
        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    }
    */
    @Data
    static class UpdateMemberRequest {
        private String name;
        private String nickname;
        private Long password;
    }
    @Data
    @AllArgsConstructor
    class UpdateMemberResponse {
        private Long id;
        private String name;
    }
    @GetMapping("/api/v2/members")
    public Result membersV2() {
        List<Member> findMembers = memberService.findMembers();
        //엔티티 -> DTO 변환
        List<MemberDto> collect = findMembers.stream()
                .map(m -> new MemberDto(m.getName()))
                .collect(Collectors.toList());
        return new Result(collect);
    }
    @Data
    @AllArgsConstructor
    class Result<T> {
        private T data;
    }
    @Data
    @AllArgsConstructor
    class MemberDto {
        private String name;
    }

    @PostMapping("/api/members")
    public CreateMemberResponse saveMember(@RequestBody @Validated
                                                     CreateMemberRequest request) {
        Member member = new Member();
        member.setName(request.getName());
        member.setNickname(request.getNickname());
        member.setPassword(request.getPassword());
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PutMapping("/api/members/skin/{id}")
    public UpdateMemberResponse updateMemberSkin(@PathVariable("id") Long id,
                                             @RequestBody @Validated UpdateMemberSkinRequest request) {
        memberService.updateSkin(id, request.getSkin());
        Member findMember = memberService.findOne(id);
        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    }
    @Data
    static class UpdateMemberSkinRequest {
        private SkinStatus skin;
    }
    @PutMapping("/api/members/nickname/{id}")
    public UpdateMemberResponse updateMemberNickname(@PathVariable("id") Long id,
                                                 @RequestBody @Validated UpdateMemberNicknameRequest request) {
        memberService.updateNickname(id, request.getNickname());
        Member findMember = memberService.findOne(id);
        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    }
    @Data
    static class UpdateMemberNicknameRequest {
        private String nickname;
    }
}

