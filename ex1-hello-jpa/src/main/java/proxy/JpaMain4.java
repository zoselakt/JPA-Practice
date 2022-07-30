package proxy;

import Mapping.Movie;
import hellojpa.Member;
import hellojpa.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain4 {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx =em.getTransaction();
        tx.begin();
        // 프록시 기초
        // em.find() vs em.getReference()
        // em.find(): 데이터베이스를 통해서 실제 엔티티 객체 조회
        // em.getReference(): 데이터베이스 조회를 미루는 가짜(프록시) 엔티티 객체 조회

        //프록시 특징
        // 실제 클래스를 상속 받아서 만들어짐
        // 실제 클래스와 겉 모양이 같다.
        // 사용하는 입장에서는 진짜 객체인지 프록시 객체인지 구분하지 않고 사용하면 됨(이론상)
        // 프록시 객체는 실제 객체의 참조(target)를 보관
        // 프록시 객체를 호출하면 프록시 객체는 실제 객체의 메소드 호출

        //프록시 객체의 초기화
        // -프록시 객체는 처음 사용할 때 한 번만 초기화
        // -프록시 객체를 초기화 할 때, 프록시 객체가 실제 엔티티로 바뀌는 것은 아님,
        // 초기화되면 프록시 객체를 통해서 실제 엔티티에 접근 가능
        // -프록시 객체는 원본 엔티티를 상속받음, 따라서 타입 체크시 주의해야함
        // (== 비교 실패, 대신 instance of 사용)
        // -영속성 컨텍스트에 찾는 엔티티가 이미 있으면 em.getReference()를 호출해도 실제 엔티티 반환
        // -영속성 컨텍스트의 도움을 받을 수 없는 준영속 상태일 때, 프록시를 초기화하면 문제 발생
        // (하이버네이트는 org.hibernate.LazyInitializationException 예외를 터트림)

        // 프록시 인스턴스의 초기화 여부 확인
        // PersistenceUnitUtil.isLoaded(Object entity)
        // 프록시 클래스 확인 방법
        // entity.getClass().getName() 출력(..javasist.. or HibernateProxy…)
        // 프록시 강제 초기화
        // org.hibernate.Hibernate.initialize(entity);
        // 참고: JPA 표준은 강제 초기화 없음
        // 강제 호출: member.getName()

        try {
            //프록시와 즉시로딩 주의
            // 가급적 지연 로딩만 사용(특히 실무에서 즉시로딩 하면 안됨.)
            // 즉시 로딩을 적용하면 예상하지 못한 SQL이 발생
            // 즉시 로딩은 JPQL에서 N+1 문제를 일으킨다.
            // @ManyToOne, @OneToOne은 기본이 즉시 로딩 -> LAZY로 설정
            //• @OneToMany, @ManyToMany는 기본이 지연 로딩

            //모든 연관관계에 지연 로딩을 사용해라!
            //JPQL fetch 조인이나, 엔티티 그래프 기능을 사용해라

            /*즉시로딩과 지연로딩*/
            Team team1 = new Team();
            team1.setName("teamA");
            em.persist(team1);

            Team team2 = new Team();
            team2.setName("teamB");
            em.persist(team2);

            Member3 member1 = new Member3();
            member1.setUsername("member1");
            member1.setTeam(team1);
            em.persist(member1);

            Member3 member2 = new Member3();
            member2.setUsername("member2");
            member2.setTeam(team2);
            em.persist(member2);

//            Member3 m = em.find(Member3.class, member.getId());
//            System.out.println("m = "+ m.getTeam().getClass());

//            System.out.println("============================");
//            System.out.println("teamName = " + m.getTeam().getName()); //초기화
            em.flush();
            em.clear();

            List<Member> members = em.createQuery("select m from Member3 m join fetch m.team", Member.class)
                    .getResultList();
            /*즉시로딩과 지연로딩 끝*/

            /*find와 reference 비교*/
            //Member findMember = em.find(Member.class, member.getId()); // 실제 엔티티객체
//            Member findMember = em.getReference(Member.class, member.getId()); //가짜 엔티티객체
//            System.out.println("findMember = " + findMember.getClass()); //프록시 클래스
//            System.out.println("findMember.id = " + findMember.getId());
//            System.out.println("findMember.username = " + findMember.getUsername());
            /*비교 끝*/
            /*프록시 비교*/
//            Member3 refMember = em.getReference(Member3.class, member.getId());
//            System.out.println("refMember = " + refMember.getClass());  //proxy
//
//            Member3 findMember = em.find(Member3.class, member.getId());
//            System.out.println("findMember = " + findMember.getClass()); //Member
//            System.out.println("refMember == findMember : " + (findMember == refMember));
            /*프록시 비교 끝*/
            tx.commit();
        }catch(Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();
    }
    private static void printMember(Member3 member){
        System.out.println("member = "+member.getUsername());
    }

    private static void printMemberAndTeam(Member3 member){
        String username = member.getUsername();
        System.out.println("username = "+ username);

        Team team = member.getTeam();
        System.out.println("team = " + team.getName());
    }
    private static void logic(Member3 m1, Member3 m2){
        System.out.println("m1 == m2 :" + (m1 instanceof Member3)); // 비교는 ==비교로 하면 안된다.
    }
}
