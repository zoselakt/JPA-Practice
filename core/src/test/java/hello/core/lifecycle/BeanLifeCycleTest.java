package hello.core.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycleTest {

    @Test
    public void lifeCycleTest(){
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        NetworkClient client = ac.getBean(NetworkClient.class);
        ac.close();
    }
    @Configuration
    static class LifeCycleConfig{

//        @Bean(initMethod = "init", destroyMethod = "close") //설정정보
        @Bean
        public NetworkClient networkClient(){
            NetworkClient networkClient = new NetworkClient();
            networkClient.setUrl("http://hello-spring.dev");
            return networkClient;
        }
    }
}   // 스프링 빈은 다음과 같은 라이프사이클을 가진다 : 객체생성 → 의존관계 주입
    // 초기화 작업업은 의존관계 주입이 모두 완료되고 난 다음에 호출해야 한다.
    // 그런데 개발자가 완료시점을 어떻게 알수 있을까? 스프링은 의존관계 주입이 완료되면
    // 스프링 빈에게 콜백메서드를 통해서 초기화 시점을 알려주는 다양한 기능을 제공한다.
    // 또한 스프링은 스프링 컨테이너가 종료되기 직적에 소멸 콜백을 준다. 따라서 안전하게 종료작업을 진행 할 수 있다.

    // 스프링 빈의 이벤트라이프 사이클
    // 스프링 컨테이너 생성 -> 스프링 빈 생성 -> 의존관계 주입 -> 초기화 콜백 -> 사용 -> 소멸전 콜백 -> 스프링 종료

    //참고
    // 객체의 생성과 초기화를 분리하자. / 생성자 안에서 무거운 초기화 작업을 함께하는 것 보다는 객체를 생성하는 부분과
    // 초기화하는 부분을 명확하게 나누는 것이 유지보수관점에서 좋다