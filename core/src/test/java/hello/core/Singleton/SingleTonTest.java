package hello.core.Singleton;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SingleTonTest {
    @Test
    @DisplayName("스프링 없는 순수한 DI컨테이너")
    void pureContainer(){
        AppConfig appConfig = new AppConfig();
        // 1. 조회: 호출할때 마다 객체생성
        MemberService memberService1 = appConfig.memberService();
        
        // 2. 조회: 위와 이름만다른 같은 객체
        MemberService memberService2 = appConfig.memberService();

        //참조값이 다름!
        System.out.println("memberService1 = "+memberService1 );
        System.out.println("memberService2 = "+memberService2 );

        // 객체 1과 2의 참조값이 다름
        Assertions.assertThat(memberService1).isNotSameAs(memberService2);
    }
    //SingleTonService에서 가져온 싱글톤객체
    @Test
    @DisplayName("싱글톤 패톤을 적용한 객체의 사용")
    void singletonServiceTest(){
        SingleTonService singleTonService1 = SingleTonService.getInstance();
        SingleTonService singleTonService2 = SingleTonService.getInstance();

        //객체 1과 2의 참조값이 같음!
        System.out.println("singletonService1 = "+singleTonService1);
        System.out.println("singletonService2 = "+singleTonService2);

        assertThat(singleTonService1).isSameAs(singleTonService2);
    }
    //스프링 컨테이너
    // 싱글톤 패턴을 적용하지 않아도 객체 인스턴스를 싱글톤으로 관리한다.
    // 스프링 컨테이너는 싱글톤 컨테이너 역할을 한다. 이렇게 생성하고관리하는 기능을 싱글톤 레지스트리라 한다.
    // 싱글톤 패턴의 모든 단점을 해결하면서 객체를 싱글톤으로 유지할 수 있다.
    @Test
    @DisplayName("스프링 컨테이너와 싱글톤")
    void springContainer() {
        //주석은 기존 코드
//        AppConfig appConfig = new AppConfig();
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        // 1. 조회: 호출할때 마다 객체생성
//        MemberService memberService1 = appConfig.memberService();
        MemberService memberService1 = ac.getBean("memberService", MemberService.class);
        MemberService memberService2 = ac.getBean("memberService", MemberService.class);
        
        // 참조값 확인
        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);

        Assertions.assertThat(memberService1).isSameAs(memberService2);
    }

//    참고: 스프링의 기본 빈 등록 방식은 싱글톤이지만, 싱글톤 방식만 지원하는 것은 아니다.
    //     요청할 대마다 새로운 객체를 생성해서 반환하는 기능도 제공한다.
    // 싱글톤 방식의 주의점
    // 여러클라이언트가 하나의 같은 객체 인스턴스를 공유하기 때문에 싱글톤 객체는 상태를 유지하게 설계하면 안된다.
    // - 특정 클라이언트에 의존적인 필드가 있으면 안된다.
    // - 특정 클라이언트가 값을 변경할 수 있는 필드가 있으면 안된다.
    // - 가급적 읽기만 가능해야한다.
    // - 필드 대신에 자바에서 공유되지 않는 지역변수, 파라미터, 쓰레드로컬 등을 사용해야 한다.
}
