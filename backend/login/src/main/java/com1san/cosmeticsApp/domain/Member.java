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
