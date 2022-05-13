package hello.core.order;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor //Lombok 애노테이션
public class OrderServiceImpl implements OrderService {

//    private final MemberRepository memberRepository = new MemoryMemberRepository();
//  private final DiscountPolicy discountPolicy = new FixDiscountPolicy(); //고정할인
//클라이언트 코드인 orderServiceImpl은 DiscountPolicy인터페이스와 구체클래스도 함께 의존하게되어
//인터페이스에만 의존하도록 설계를 변경 / DIP위반 → 추상에만 의존하도록 변경

    @Autowired
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;
    // 롬복을 사용하려면 주석처리풀어야한다.

    //자바빈 (프로퍼티) : 자바빈에 저장되어 있는 값
    // 모듈화된 MVC에서 View가 JSP라면, M(Model)에 해당하는 것이 Bean 입니다.
    // 모델이 프로그램 로직을 가지고 있고 DB와 연동을 해서 작업을 하듯이 자바빈도 동일한 역할을 합니다.
    // 자바코드를 최대한 줄이기 위해 사용

//    public OrderServiceImpl(MemberRepository memberRepository, @MainDiscountPolicy DiscountPolicy discountPolicy) {
//        this.memberRepository = memberRepository;
//        this.discountPolicy = discountPolicy;
//    }    //MainDiscountPolicy 애노테이션은 MainDiscountPolicy에서 직접만든 애노테이션
    // 롬복을 위한 주석처리
    // 롬복은 위 생성자를 만들어 준다. / final 붙은 필드를 불러와 생성자를 만든것과 동일하게 처리 / 필요시 생성자 생성
    // 최근에는 생성자를 딱 1개두고, @Autowired를 생략하는 방법을 주로 사용한다.
    // 여기에 롬복과 함께 사용하면 기능은 다 제공하면서 코드는 깔끔하게 사용할 수 있다.

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member,itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    // @configuration와싱글톤 테스트용도
    public MemberRepository getMemberRepository(){
        return memberRepository;
    }
}
