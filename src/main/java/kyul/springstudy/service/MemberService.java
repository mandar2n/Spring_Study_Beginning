package kyul.springstudy.service;

import kyul.springstudy.domain.Member;
import kyul.springstudy.repository.MemberRepository;
import kyul.springstudy.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional //JPA 쓸 때 필수적
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    // 회원 가입
    public Long join(Member member){

        // 같은 이름의 중복 회원 X
        validateDuplicateMember(member); // extract method (ctrl+alt+m) 이용해서 함수로 빼둠

        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
    /*Optional<Member> result = memberRepository.findByName(member.getName());
    result.ifPresent(m -> {
        throw new IllegalStateException("이미 존재하는 회원입니다.");
    });*/
        memberRepository.findByName(member.getName())
                        .ifPresent(m -> {
                            throw new IllegalStateException("이미 존재하는 회원입니다.");
                        });
    }

    // 전체 회원 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById((memberId));
    }
}
