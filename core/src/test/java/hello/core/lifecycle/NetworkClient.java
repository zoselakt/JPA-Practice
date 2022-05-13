package hello.core.lifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class NetworkClient{
// public class NetworkClient implements InitializingBean, DisposableBean{

    private String url;

    public NetworkClient() {
        System.out.println("생성자 호출, Url = " + url);
    }
    public void setUrl(String url){
        this.url = url;
    }
    //서비스 시작시 호출
    public void connect(){
        System.out.println("connect: "+url);
    }
    public void call(String message){
        System.out.println("call: "+url+ "message: "+message);
    }

    //서비스 종료시 호출
    public void disconnect(){
        System.out.println("close: "+url);
    }
//    스프링은 크게 3가지 방법으로 빈 생명주기 콜백을 지원한다.
//    인터페이스(InitializingBean, DisposableBean)
//    설정 정보에 초기화 메서드, 종료 메서드 지정
//    @PostConstruct, @PreDestroy 애노테이션 지원

//    초기화, 소멸 인터페이스 단점
//    이 인터페이스는 스프링 전용 인터페이스다. 해당 코드가 스프링 전용 인터페이스에 의존한다
//    초기화, 소멸 메서드의 이름을 변경할 수 없다.
//    내가 코드를 고칠 수 없는 외부 라이브러리에 적용할 수 없다.
    // 참고: 인터페이스를 사용하는 초기화, 종료 방법은 스프링 초창기에 나온 방법들이고, 지금은 다음의 더
    //나은 방법들이 있어서 거의 사용하지 않는다

//    인터페이스(InitializingBean, DisposableBean)
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        // 의존관계가 끝나면 주입하겠다.
//        connect();
//        call("초기화 연결 메세지");
//        System.out.println("NetworkClient.afterPropertiesSet");
//    }

//    @Override
//    public void destroy() throws Exception {
//        disconnect();
//        System.out.println("NetworkClient.destroy");
//    }

    //설정정보 사용
    //특징
    //메서드 이름을 자유롭게 줄 수 있다.
    // 스프링 빈이 스프링 코드에의존하지 않는다.
    // 코드가 아니라 설정정보를 사용하기 때문에 코드를 고칠 수 없는 외부 라이브러리에도 초기화, 종료메서드를 적용할 수 있다.

    //종료메서드 추론
    // 대부분 close, shutdown이라는 이름의 종료메서드를 사용한다.
    // @Bean의 destroyMethod는 기본값이 (inferred) (추론)으로 등록되어 있다.
    // 이 추론 기능은 close, shutdown 라는 이름의 메서드를 자동으로 호출한다.

//    public void init() {
//        connect();
//        call("초기화 연결 메세지");
//        System.out.println("NetworkClient.init");
//    }
//
//    public void close(){
//        disconnect();
//        System.out.println("NetworkClient.close");
//    }

//@PostConstruct, @PreDestroy
    @PostConstruct
    public void init() {
        connect();
        call("초기화 연결 메세지");
        System.out.println("NetworkClient.init");
    }
    @PreDestroy
    public void close(){
        disconnect();
        System.out.println("NetworkClient.close");
    }
    // 최신 스프링에서 가장 권장하는 방법, 애노테이션만 붙이면 된다.
    // 스프링에 종속적인 기술이 아니라 자바표준 기술이다. 스프링이 아닌 다른 컨테이너 에서도 동작한다.
    // 유일한 단점은 외부 라이브러리에는 적용하지 못한다.
    // 외부라이브러리를 초기화, 종료해야하면 @Bean의 initMethod, destroyMethod를 이용하자


}
