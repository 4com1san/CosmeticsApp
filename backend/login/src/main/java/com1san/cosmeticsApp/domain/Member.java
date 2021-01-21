package com1san.cosmeticsApp.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Member {
    @Id @GeneratedValue
    @Column(name="member_id")
    private Long id;

    private String name;

    private Long password;

    private String nickname;

    @Enumerated(EnumType.STRING)
    private SkinStatus skin_status;

    @Enumerated(EnumType.STRING)
    private SensitiveStatus sensitive_status;
}
/*
Name: '' 로그인id
PW: '' 로그인password
PWT: '' 비번확인
EMAIL: '' 이멜(보류)
NickNAME: '' 닉네임
list:"지성" 피부타입
skinBool0,1,2,...:"" boolean으로 피부고민
api 명세
 */
