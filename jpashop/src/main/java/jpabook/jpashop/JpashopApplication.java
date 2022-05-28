package jpabook.jpashop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JpashopApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpashopApplication.class, args);
	}
	//엔티티 클래스 개발
	//예제에서는 설명을 쉽게하기 위해 엔티티 클래스에 Getter, Setter를 모두 열고, 최대한 단순하게 설계
	//실무에서는 가급적 Getter는 열어두고, Setter는 꼭 필요한 경우에만 사용하는 것을 추천

	//연관된 엔티티를 함께 DB에서 조회해야 하면, fetch join 또는 엔티티 그래프 기능을 사용한다.
	//@XToOne(OneToOne, ManyToOne) 관계는 기본이 즉시로딩이므로 직접 지연로딩으로 설정해야 한다

	//컬렉션은 필드에서 초기화 하자.
	// 컬렉션은 필드에서 바로 초기화 하는 것이 안전하다. (null 문제에서 안전하다.)
	// 하이버네이트는 엔티티를 영속화 할 때, 컬랙션을 감싸서 하이버네이트가 제공하는 내장 컬렉션으로 변경한
	// 다. 만약 getOrders() 처럼 임의의 메서드에서 컬력션을 잘못 생성하면 하이버네이트 내부 메커니즘에 문
	// 제가 발생할 수 있다. 따라서 필드레벨에서 생성하는 것이 가장 안전하고, 코드도 간결하다.

	//Member member = new Member();
	//System.out.println(member.getOrders().getClass());
	//em.persist(team);
	//System.out.println(member.getOrders().getClass());
	//출력 결과
	//class java.util.ArrayList
	//class org.hibernate.collection.internal.PersistentBag

	//하이버네이트 기존 구현: 엔티티의 필드명을 그대로 테이블의 컬럼명으로 사용
	//( SpringPhysicalNamingStrategy )

	//스프링 부트 신규 설정 (엔티티(필드) 테이블(컬럼))
	//1. 카멜 케이스 언더스코어(memberPoint member_point)
	//2. .(점) _(언더스코어)
	//3. 대문자 소문자
}
