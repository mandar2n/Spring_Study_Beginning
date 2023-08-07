package kyul.springstudy.service;

import kyul.springstudy.domain.Member;
import kyul.springstudy.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional //test 끝나면 roll back 해줌. test에서 DB에 저장한 값 다 지워줌.
class MemberServiceIntegrationTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Commit //커밋히면 jpa가 진짜 스스로 sql문 만들어서 table에 저장하는 거 확인 가능
    void join() {
        //given
        Member member = new Member();
        member.setName("spring11");

        //when
        Long saveId = memberService.join(member);

        //then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    // Test의 경우, 예외 flow도 항상 중요!
    @Test
    public void 중복회원_예외(){ // test명의 경우, 한글 가능

        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        //then
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

        /*try{
            memberService.join(member2);
            fail();
        } catch (IllegalStateException e){
            assertThat(e.getMessage()).isEqualTo(("이미 존재하는 회원입니다."));
        }*/
    }

}

