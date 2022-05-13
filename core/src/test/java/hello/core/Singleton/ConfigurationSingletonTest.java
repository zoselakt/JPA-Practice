package hello.core.Singleton;

import hello.core.AppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberServiceImpl;
import hello.core.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConfigurationSingletonTest {
    @Test
    void configurationTest(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
        MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);


        MemberRepository memberRepository1 = memberService.getMemberRepository();
        MemberRepository memberRepository2 = orderService.getMemberRepository();

        //모두 같은 인스턴스를 공유되어 사용된다!
        System.out.println("memberService -> memberRepository = " + memberRepository1);
        System.out.println("orderService -> memberRepository = " + memberRepository2);
        System.out.println("memberRepository"+memberRepository);

        Assertions.assertThat(memberService.getMemberRepository()).isSameAs(memberRepository);
        Assertions.assertThat(orderService.getMemberRepository()).isSameAs(memberRepository);
    }
    @Test
    void configurationDeep(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        AppConfig bean = ac.getBean(AppConfig.class);
        System.out.println("bean = "+bean.getClass());
    }
    //@configuration
    // 위 코드를 출력하면 xxxCGLIB가 붙는다. 스프링이 CGLIB라는 바이트코드 조작 라이브러리를 사용해서
    // AppConfig 클래스를 상속받은 임의의 다른 클래스를만들고, 그 클래스를 스프링 빈으로 등록한 것이다.
    // @Bean이 붙은 메서드 마다 스프링빈이 존재하면, 존재하는 빈을 반환하고
    // 스프링 빈이 없으면 생성해서 스프링 빈으로 등록하고 반환하는 코드가 동적으로 만들어진다.
    // 덕분에 싱글톤이 보장되는 것이다.

    //@configuration을 적용하지 않고 @Bean만 적용하게 되면
    // 싱글톤이 보장되지 않고 생산한 코드 그대로 값을 출력한다.

}
