package hello.core.autowired;

import hello.core.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.lang.Nullable;

import java.util.Optional;

public class AutowiredTest {
    //옵션처리 : 주입할 스프링 빈이 없어도 동작해야 할 때가 있다.
    //      그런데 required 옵션의 기본값이 true로 되어 있어서 자동주입대상이 없으면 오류가 발생한다.
    @Test
    void AutowiredOption(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);
    }
    static class TestBean{
        @Autowired(required = false)
        public void setNoBean1(Member noBean1){
            System.out.println("noBean1 =" + noBean1);
            // 반환값: 자동 주입할 대상이 없으면 수정자 메서드 자체가 호출안됨.
        }

        @Autowired
        public void setNoBean2(@Nullable Member noBean2){
            System.out.println("noBean2 =" + noBean2);
            //반환값: noBean2 =null
            // @Nullable Optional은 스프링 전반에 걸쳐서 지원된다.
            // 생성자 자동 주입에서 특정 필드에만 사용해도 된다.
        }

        @Autowired
        public void setNoBean3(Optional<Member> noBean3){
            System.out.println("noBean3 =" + noBean3);
            // 반환값: noBean3 =Optional.empty
        }
    }
}
