package jpa.board;

import jakarta.annotation.PostConstruct;
import jpa.board.entity.Authority;
import jpa.board.entity.Board;
import jpa.board.entity.Member;
import jpa.board.repository.BoardRepository;
import jpa.board.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class InitDB {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.userDbInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final MemberRepository memberRepository;
        private final BoardRepository boardRepository;

        public void userDbInit() {
            List<Member> memberList = memberRepository.findAll();

            if(memberList.size() == 0){
                Member member = Member.builder()
                        .username("관리자")
                        .phoneNum("010-1111-2222")
                        .age(29)
                        .authority(Authority.ROLE_ADMIN)
                        .build();
                //member 저장
                memberRepository.save(member);
            }
        }
    }
}
