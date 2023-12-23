package jpa.board.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String username;
    private String password;
    private String phoneNum;
    private int age;

    @Column(name = "user_role")
    @Enumerated(EnumType.STRING)
    private Authority authority;               // 권한

    @OneToMany(mappedBy = "member")
    private List<Board> Boards = new ArrayList<>();

    @Builder
    public Member (String username, String phoneNum, int age, Authority authority) {
        this.username = username;
        this.phoneNum = phoneNum;
        this.age = age;
        this.authority = authority;
    }
    
}
