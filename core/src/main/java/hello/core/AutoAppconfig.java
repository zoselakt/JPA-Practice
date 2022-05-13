package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

// 컴포넌트 스캔을 사용하려면 먼저 @ComponentScan을 설정정보에 붙여주면 된다.
// 기존의 AppConfig와는 다르게 @Bean으로 등록한 클래스가 하나도 없다!.

//ComponentScan은 등록된 @Component가 붙은 코드를 찾아 스프링 컨테이너에 자동으로 등록한다.
@Configuration
@ComponentScan(
        basePackages = "hello.core.member", //member 패키지만 탐색 / 탐색할 패키지의 시작위치를 지정
        basePackageClasses = AutoAppconfig.class, 
        //권장하는 방법: 패키지위치를 지정하지 않고, 프로젝트 최상단에 두어 사용. /스프링 부트도 이 방법을 기본으로 제공
        //            스프링부트 사용시 @SpringBootApplication를 프로젝트 시작루트 위치에 두는것이 관례
        // componentScan은 다음의 내용도 추가로 대상에 포함되어있다
        // - @component: 컴포넌트스캔에서 사용
        // - @Controller: 스프링 MVC 컨트롤러에서 사용
        // - @Service: 스프링 비즈니스 로직에서 사용 / 특별한 처리를하지 않는다. 대신 주석처럼 사용된다.
        // - @Repository: 스프링 데이터 접근 계층에서 사용 / 데이터 계층의 예외를 스프링 예외로 변환해준다.(??)
        // - @Configuration: 스프링 설정 정보에서 사용
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class) //

)
public class AutoAppconfig {

}
