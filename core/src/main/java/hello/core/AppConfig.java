package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@configuration: 설정파일을 만들기 위한 애노테이션 or Bean을 등록하기 위한 애노테이션이다.
@Configuration
public class AppConfig {
    // 객체의 생성과 연결은 appconfig가 담당한다. / 공연기획자
    // appconfig객체는 memomyMemberRepo에 객체를 생성하고 그 참조값을
    // memberServiceImpl을 생성하면서 생성자로 전달한다.
    // 클라이언트인 memberServiceImpl에서는 의존관계를 외부에서 주입해주는 것과 같다고해서
    // DI, 의존관계주입/의존성 주입이라 한다.

//    Bean: Spring IoC 컨테이너가 관리하는 자바 객체를 빈(Bean)이라는 용어로 부른다.
//          Spring에서의 빈은 ApplicationContext가 알고있는 객체, 즉 ApplicationContext가 만들어서 그 안에 담고있는 객체를 의미한다.
//          주의: 빈 이름은 항상 다른이름을 부여해야한다.

    //생성자 주입 - memberservice
    @Bean
    public MemberService memberService(){
        System.out.println(" call AppConfig.memberService");
        return new MemberServiceImpl(memberRepository());
    }
    @Bean
    public MemoryMemberRepository memberRepository() {
        System.out.println(" call AppConfig.memberRepository");
        return new MemoryMemberRepository();
    }

    //생성자 주입 - orderService
    @Bean
    public OrderService orderService(){
        System.out.println(" call AppConfig.orderService");
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean
    public DiscountPolicy discountPolicy(){
//        return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }
    // 스프링 컨테이너
    // ApllicationContext를 스프링 컨테이너라 하고, 인터페이스이다.
    // 스프링컨테이너를 생성하면 스프링 빈 저장소가 있고 내부에 빈이름, 빈객체라는 것이있다.
    // 빈 이름은 객체이름 / 빈 객체는 return 값이 된다
    // 설정 정보를 참고해서 의존관계를 주입한다.

    // 스프링은 빈을 생성하고 의존관계를 주입하는 단계가 나누어져있다. 그런데 자바코드로 스프링 빈을 등록하면
    // 생성자를 호출하면서 의존관계 주입도 한번에 처리된다.
}
