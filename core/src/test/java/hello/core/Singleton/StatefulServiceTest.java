package hello.core.Singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.junit.jupiter.api.Assertions.*;

class StatefulServiceTest {
    @Test
    void statefulServiceSingleton(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1= ac.getBean(StatefulService.class);
        StatefulService statefulService2= ac.getBean(StatefulService.class);
        
        //ThreaA: A사용자 10000원 주문
        // statefulService1.order("userA", 10000);
        int userAPrice = statefulService1.order("userA", 10000);

        //ThreaB: B사용자 20000원 주문
        int userBPrice = statefulService2.order("userB", 20000);
        
        //ThreadA 주문금액조회
//        int price = statefulService1.getPrice();
//        System.out.println("price = "+price);
        System.out.println("price = "+ userAPrice);

//        Assertions.assertThat(statefulService1.getPrice()).isEqualTo(20000);
    }
    static class TestConfig{
    @Bean
    public StatefulService statefulService(){
        return new StatefulService();
        }
    }
}