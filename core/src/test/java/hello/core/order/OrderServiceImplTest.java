package hello.core.order;

import hello.core.discount.FixDiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemoryMemberRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class OrderServiceImplTest {
    //생성자주입선택
    // 불변: 대부분의 의존관계 주입은 한번 일어나면 애플리케이션 종료시점까지 의존관계를 변경할 일이 없다. 오히려 종료 전까지 변하면 안된다.(불변)
//          생성자 주입은 객체를 생성할때 딱 1번만 호출되므로 이후에는 호출되는 일이 없다. 따라서 불변하게 설계할수 있다.
    // 누락: 프레임워크 없이 순수한 자바 코드를 단위 테이스 하는 경우에 @Autowired가 동작할때 의존관계가 없으면 오류가 발생하지만,
    //      순수한 자바 코드로만 단위 테스트를 수행하고 있다. 하지만 막상 실행결과는 NPE가 발생하는데 의존관계 주입이 누락되었기 때문이다.
    // final키워드: 생성자 주입을 사용하면 필드에 final키워드를 사용할수 있다. 혹시라도 값이 설정되지 않는 오류를 컴파일 시점에서 막아준다.

    // 수정자 주입을 포함한 나머지 주입 방식은 모두 생성자 이후에 호출되므로 필드에 final키워드를 사용할 수 없다.
    // 오직 생성자 주입만 final키워드를 사용할 수 있다.
    // 항상 생성자 주입을 선택하자. 필드 주입은 사용하지 않는게 좋다.
    @Test
    void createOrder(){
        MemoryMemberRepository memberRepository = new MemoryMemberRepository();
        memberRepository.save(new Member(1L, "name", Grade.VIP));

        OrderServiceImpl orderService = new OrderServiceImpl(memberRepository, new FixDiscountPolicy());
        Order order = orderService.createOrder(1L, "itemA", 10000);
        assertThat(order.getDiscountPrice()).isEqualTo(1000);
    }
}