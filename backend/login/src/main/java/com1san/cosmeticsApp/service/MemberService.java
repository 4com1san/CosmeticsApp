package com1san.cosmeticsApp.service;

import com1san.cosmeticsApp.domain.Member;
import com1san.cosmeticsApp.domain.SkinStatus;
import com1san.cosmeticsApp.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional //변경
    public Long join(Member member) {
        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }
    private void validateDuplicateMember(Member member) {
        List<Member> findMembers =
                memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }
    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
    /**
     * 회원 수정
     */
    @Transactional
    public void update(Long id, String name) {
        Member member = memberRepository.findOne(id);
        member.setName(name);
    }
    // 로그인정보로 member.getid 해서 skin,민감성 넣어줌, 테스트필요
    public void setSkin(Long memberId,Long skinCnt){
        if (skinCnt<=1) {
            memberRepository.findOne(memberId).setSkin_status(SkinStatus.akkun);
        }
        else if(skinCnt<=4){
            memberRepository.findOne(memberId).setSkin_status(SkinStatus.kun);
        }
        else if(skinCnt<=7){
            memberRepository.findOne(memberId).setSkin_status(SkinStatus.joong);
        }
        else if(skinCnt<=10){
            memberRepository.findOne(memberId).setSkin_status(SkinStatus.ji);
        }
        else{
            memberRepository.findOne(memberId).setSkin_status(SkinStatus.akji);
        }
    }
    // 13번 접촉민 14번 화학민 13,14 극민감  둘다안체크는 non-sensitive
    public void setSensitive(Long memberId,Long sensitiveCnt){

    }
}
