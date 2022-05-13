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

//        try{
        //추가
//            Member member = new Member();
//            member.setId(1L);
//            member.setName("HelloA");
//            em.persist(member);
        //검색
//            Member findMember = em.find(Member.class, 1L);
//            findMember.setName("helloJPA");

        //JPQL : SQL을 추상화한 객체지향쿼리언어
        // select, groupby, join, having 등 지원
//            List<Member> result = em.createQuery("select m from Member as m", Member.class)
//                    .setFirstResult(1)
//                    .setMaxResults(10)
//                            .getResultList();
//            for (Member member : result){
//                System.out.println("member.name = "+member.getName());
//            }
//-------------------------------------------------------------------
//  영속성 컨텍스트의 이점
//        1차 캐시
//        동일성(identity) 보장
//        트랜잭션을 지원하는 쓰기 지연 (transactional write-behind)
//        변경 감지(Dirty Checking)
//        지연 로딩(Lazy Loading)

        //비영속상태
//        Member member = new Member();
//        member.setId(101L);
//        member.setName("HelloJPA");

        //영속상태 : persist는 DB에 저장하기 전 준비상태
//        em.persist(member);

        //회원 엔티티를 영속성 컨텍스트에서 분리, 준영속 상태
//        em.detach(member);
        //객체를 삭제한 상태(삭제)
//        em.remove(member);

//        Member findMember = em.find(Member.class, 101L);
//        System.out.println("findMember.id = "+ findMember.getId());
//        System.out.println("findMember.name = "+ findMember.getName());

        //영속 엔티티의 동일성보장
//        Member findMember1 = em.find(Member.class, "member1");
//        Member findMember2 = em.find(Member.class, "member1");
//        System.out.println("result = "+(findMember1 == findMember2));
        try {
            // 엔티티 수정, 변경감지
//            Member member = em.find(Member.class, 150L);
//            member.setName("zzZzz"); //데이터 수정
//            System.out.println("=================================");
            //            em.persist(member); / 이걸 호출안하는게 정답!!! / 다른거 없이 위코드가 가장 좋은 코드다.

            // 필드와 컬럼매핑
            Member member = new Member();
            member.setId(1L);
            member.setUsername("A");
            member.setRoleType(RoleType.USER);

            em.persist(member);

            // 엔티티 삭제
//            Member memberA = em.find(Member.class, “memberA");

            //DB에 저장
            tx.commit();
        }catch(Exception e){
            tx.rollback();
        }finally {
            em.close();
        }

        emf.close();
    }
    // 플러시 발생
    // 변경 감지 / 영속성 컨텍스트를 비우지 않음
    // 수정된 엔티티 쓰기 지연 SQL 저장소에 등록
    // 쓰기 지연 SQL 저장소의 쿼리를 데이터베이스에 전송 (등록, 수정, 삭제 쿼리)
    // -> 커밋직전에만 동기화 하면됨.
//  방법
    // em.flush() - 직접 호출
    // 트랜잭션 커밋 - 플러시 자동 호출
    // JPQL 쿼리 실행 - 플러시 자동 호출
//  호출    
//    em.flush();
    
    // 준영속 상태
    // 영속 -> 준영속
    // 영속 상태의 엔티티가 영속성 컨텍스트에서 분리(detached)
    // 영속성 컨텍스트가 제공하는 기능을 사용 못함
//    방법
    // em.detach(entity) / 특정 엔티티만 준영속 상태로 전환
    // em.clear() / 영속성 컨텍스트를 완전히 초기화 / 테스트 작성시 도움됨.
    // em.close() / 영속성 컨텍스트를 종료

}
