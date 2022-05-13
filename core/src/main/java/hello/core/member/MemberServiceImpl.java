package hello.core.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;
    //private final MemberRepository memberRepository = new MemoryMemberRepository();
    //appconfig에 작성하여 생성자를 통하여 가져오기 때문에 (인터페이스)생성만 하고 비워둔다.

    // 생성자를 통해 어떤 구현 객체가 들어올지는 알 수 없다. / memberRepository 인터페이스에만 의존하게 된다.
    // MemberServiceImpl은 이제부터 의존관계에 대한 고민은 외부에 맡기고 실행에만 집중하게 된다.
    // 객체의 생성과 연결은 appconfig가 담당한다.
    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    //Autowired
    // - 생성자를 통해서 의존관계를 주입받는 방법
    // - Setter의 필드값을 변경하는 수정자 메서드를 통해서 의존관계를 주입하는 방법
    // - 필드를 통하여 생성자를 주입받는 방법 / 사용하지 말자! - Test시 사용
    // - 일반 메서드에 대해서도 주입가능하다, 스프링 컨테이너가 관리하는 스프링 빈이어야 동작한다. / 일반적으로 사용하지 않는다.
    // @Autowired의 기본동작은 주입할 대상이 없으면 오류가 발생한다. / 주입할 대상이 없어도 동작하게 하려면 @Autowired(required = false)로 지정

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

    // @configuration와싱글톤 테스트용도
    public MemberRepository getMemberRepository(){
        return memberRepository;
    }
}
