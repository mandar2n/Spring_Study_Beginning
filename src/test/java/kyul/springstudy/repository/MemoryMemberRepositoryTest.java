package kyul.springstudy.repository;

import kyul.springstudy.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach      // Test 여러 개 실행할 때, 각각의 Test 실행할 때마다 반복할 것
    public void afterEach(){
        repository.clearStore();    // 여기서는 중복해 member1, member2를 저장함으로써 이전에 가리킨 값(findAll()에서 저장된 값)을 가리켜 findByName() 테스트에서 오류 나는 것 방지하기 위해 각각의 test 끝난 이후 clear 해주는 거임
    }

    @Test
    public void save(){


        Member member = new Member();
        member.setName("Kyuree");

        repository.save(member);

        Member result = repository.findById(member.getId()).get();
        //System.out.println("result = " + (result == member));     // 맞을 경우, "result = true" 출력

        //Assertions.assertEquals(member, result);      // 맞을 경우, 초록 체크가
        //Assertions.assertEquals(member, null);        // 틀릴 경우, 빨간 엑스 뜸

        assertThat(member).isEqualTo(result);   // alt+enter 치면 static import 가능해서 Assertions 추가 안 해줘도 됨
    }

    @Test
    public void findByName(){

        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        Member result = repository.findByName("spring1").get();

        assertThat(result).isEqualTo(member1);

    }

    @Test
    public void findAll(){

        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2);
    }

}
