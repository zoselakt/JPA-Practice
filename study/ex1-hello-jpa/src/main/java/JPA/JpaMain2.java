package JPA;

import hellojpa.Member;
import hellojpa.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain2 {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx =em.getTransaction();
        tx.begin();

        try {
            // 일대다 맵핑 연습
            /*
            Member1 member = new Member1();
            member.setUsername("member1");
            em.persist(member);

            Team1 team1 = new Team1();
            team1.setName("teamA");
            team1.getMembers().add(member); // 일대다에서 문제가생기는부분
            em.persist(team1);
             */
            // 일대다 단방향 정리
// - 일대다 단방향은 일대다(1:N)에서 일(1)이 연관관계의 주인
// - 테이블 일대다 관계는 항상 다(N) 쪽에 외래 키가 있음
// - 객체와 테이블의 차이 때문에 반대편 테이블의 외래 키를 관리하는 특이한 구조
// - @JoinColumn을 꼭 사용해야 함. 그렇지 않으면 조인 테이블방식을 사용함(중간에 테이블을 하나 추가함)
            
            // 일대일 맵핑연습 / 단방향은 지원

//  일대일 정리
//  • 주 테이블에 외래 키
//   - 주 객체가 대상 객체의 참조를 가지는 것 처럼 주 테이블에 외래 키를 두고 대상 테이블을 찾음
//   - 객체지향 개발자 선호 / JPA 매핑 편리
//   - 장점: 주 테이블만 조회해도 대상 테이블에 데이터가 있는지 확인 가능
//   - 단점: 값이 없으면 외래 키에 null 허용
//  • 대상 테이블에 외래 키
//   - 대상 테이블에 외래 키가 존재 / 전통적인 데이터베이스 개발자 선호
//   - 장점: 주 테이블과 대상 테이블을 일대일에서 일대다 관계로 변경할 때 테이블 구조 유지
//   - 단점: 프록시 기능의 한계로 지연 로딩으로 설정해도 항상 즉시 로딩됨(프록시는 뒤에서 설명)

            tx.commit();
        }catch(Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();
    }
}
