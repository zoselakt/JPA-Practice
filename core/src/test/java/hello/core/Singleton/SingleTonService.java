package hello.core.Singleton;

public class SingleTonService {
    //싱글톤 패턴 설계
    //1.static영역에 객체 instance를 미리 하나 생성해서 올려둔다.
    //2.이 객체 인스턴스가 필요하면 오직 getInstance()메서드를 통해서만 조회할 수있다.
    //  이 메서드를 호출하면 항상 같은 인스턴스를 반환한다.
    //3. 딱 1개의 객체인스턴스만 존재해야 하므로, 생성자를 private으로 막아서
    // 혹시라도 외부에서 new 키워드로 객체 인스턴스가 생성되는 것을 막는다.

    private static final SingleTonService instance = new SingleTonService();

    public static SingleTonService getInstance(){
        return instance;
    }
    private SingleTonService(){
        
    }
    public void logic() {
        System.out.println("싱글톤 객체 로직 호출");
    }
}

//문제점
// -싱글톤 패턴을 구현하는 코드 자체 logic()구현하려면 그 위까지 모든 코드를 작성해야한다.
// - 의존관계상 클라이언트가 구체 클래스에 의존한다.-DIP위반
// - 구체클래스에 의존해서 OCP원칙을 위반할 가능성이 높다.
// - 테스트하기 어렵다.
// - 내부속성을 변경하거나 초기화하기 어렵다.
// - private 생성자로 자식 클래스를 만들기 어렵다.
// - 유연성이 떨어진다.
// - 안티패턴으로 불리기도 한다.

//이러한문제점을 싱글톤 컨테이너로 해결할수 있다