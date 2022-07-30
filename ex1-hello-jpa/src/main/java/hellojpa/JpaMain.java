package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx =em.getTransaction();
        tx.begin();

        try {
            //저장
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
//            member.setTeam(team); // 필수작성 /
            em.persist(member);

            //역방향(주인이 아닌 방향)만 연관관계 설정
            // team.getMembers().add(member); // 필수작성 / Member에 setter에 작성하면 더 수월하다

            // 주의: 양방향 매핑시 실수
            // 1. 연관관계의 주인에 값을 입력하지 않음
            // 2. 순수한 객체 관계를 고려하면 항상 양쪽다 값을 입력해야 한다.

            team.addMember(member); //

            em.flush();
            em.clear();
            
            
            //양방향 매핑
            Member findMember = em.find(Member.class, member.getId()); // 1차캐시
            List<Member> members = findMember.getTeam().getMembers();

            for (Member m: members){
                System.out.println("m = " + m.getUsername());
            }
            // 양방향 매핑 규칙
            // - 객체의 두 관계중 하나를 연관관계의 주인으로 지정
            // - 연관관계의 주인만이 외래 키를 관리(등록, 수정)
            // - 주인이 아닌쪽은 읽기만 가능
            // - 주인은 mappedBy 속성 사용X
            // - 주인이 아니면 mappedBy 속성으로 주인 지정


            tx.commit();
        }catch(Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();
    }
}
