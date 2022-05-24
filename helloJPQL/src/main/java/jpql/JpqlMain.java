package jpql;

import javax.persistence.*;
import java.util.List;

public class JpqlMain {
    public static void main(String[] args) {
        //JPQL
        // 테이블이 아닌 객체를 대상으로 검색하는 객체 지향 쿼리
        // SQL을 추상화해서 특정 데이터베이스 SQL에 의존X
        // JPQL을 한마디로 정의하면 객체 지향 SQL

        //JPQL 문법
        // select m from Member as m where m.age > 18
        // 엔티티와 속성은 대소문자 구분O (Member, age)
        // JPQL 키워드는 대소문자 구분X (SELECT, FROM, where)
        // 별칭은 필수(m) (as는 생략가능)

        //결과 조회 API
        // query.getResultList(): 결과가 하나 이상일 때, 리스트 반환결과가 없으면 빈 리스트 반환
        // query.getSingleResult(): 결과가 정확히 하나, 단일 객체 반환
        // 결과가 없으면: javax.persistence.NoResultException
        // 둘 이상이면: javax.persistence.NonUniqueResultException
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        //TypeQuery, Query
        // TypeQuery: 반환 타입이 명확할 때 사용
        // Query: 반환 타입이 명확하지 않을 때 사용

        //프로젝션
        //- SELECT 절에 조회할 대상을 지정하는 것
        // 프로젝션 대상: 엔티티, 임베디드 타입, 스칼라 타입(숫자, 문자등 기본 데이터 타입)
        // SELECT m FROM Member m -> 엔티티 프로젝션
        // SELECT m.team FROM Member m -> 엔티티 프로젝션
        // SELECT m.address FROM Member m -> 임베디드 타입 프로젝션
        // SELECT m.username, m.age FROM Member m -> 스칼라 타입 프로젝션
        // DISTINCT로 중복 제거

        //프로젝션 - 여러 값 조회
        //SELECT m.username, m.age FROM Member m
        // 1. Query 타입으로 조회 //  Query: 반환 타입이 명확하지 않을 때 사용 (TypeQuery 와 비교기술)
        // 2. Object[] 타입으로 조회 // object[] 배열에 담는다. / List<Object> result = em.createQuery ...
        // 3. new 명령어로 조회
        // 1) DTO생성 후 객체 생성
        // 2) SELECT new jpabook.jpql.UserDTO(m.username, m.age) FROM Member m
        // 패키지 명을 포함한 전체 클래스 명 입력 / 순서와 타입이 일치하는 생성자 필요

        try{
            Member member = new Member();
            member.setUsername("member");
            member.setAge(10);
            em.persist(member);

            em.flush();
            em.clear();

            /* 임베디드 타입
            List<Member> result = em.createQuery("select t from Member m join m.team t", Member.class)
                            .getResultList();
             */
            /*페이징쿼리
            em.createQuery("select m from Member m order by m.age desc", Member.class)
                    .setFirstResult(0)
                    .setMaxResults(10)
                    .getResultList();
            */
            /*
            단일값 연관 경로 / 묵시적 내부 조인 발생, 탐색 O
            String query = "select m.team From Member m";

            컬렉션 값 연관 경로 / 묵시적 내부 조인 발생, 탐색 X
            String query = "select t.members From Team t";
            */
            /*
            페치 조인 사용 코드
            String jpql = "select m from Member m join fetch m.team";
            List<Member> members = em.createQuery(jpql, Member.class)
                    .getResultList();
            for (Member member : members) {
            // 페치 조인으로 회원과 팀을 함께 조회해서 지연 로딩X
                System.out.println("username = " + member.getUsername() + ", " +
                "teamName = " + member.getTeam().name());
             }
             */
            /*
            컬렉션 페치 조인 사용 코드
            String jpql = "select t from Team t join fetch t.members where t.name = '팀A'"
            List<Team> teams = em.createQuery(jpql, Team.class).getResultList();
                    for(Team team : teams) {
            System.out.println("teamname = " + team.getName() + ", team = " + team);
            for (Member member : team.getMembers()) {
            //페치 조인으로 팀과 회원을 함께 조회해서 지연 로딩 발생 안함
                 System.out.println(“-> username = " + member.getUsername()+ ", member = " + member);
                 }
            }
             */
            /*
            엔티티를 파라미터로 전달
            String query = "select m from Member m where m = :member";
            List resultList = em.createQuery(jpql)
                         .setParameter("member", member)
                         .getResultList();
             */
            /*
            식별자를 직접 전달
            String jpql = “select m from Member m where m.id = :memberId”;
            List resultList = em.createQuery(jpql)
                     .setParameter("memberId", memberId)
                    .getResultList();
             */
            /*
            List<Member> resultList = em.createNamedQuery("Member.findByUsername", Member.class)
                        .setParameter("username", "회원1")
                        .getResultList();
             */
            tx.commit();
        }catch (Exception e){
            tx.rollback();
            e.printStackTrace();
        }finally {
            em.close();
        }
    }
}
