package jpql;

public class JPQL_grammar {
    /*내부조인
       String query = "select m from Member m join m.team t ";
       List<Member> result = em.createQuery(query, Member.class)
                     .getResultList();
     */
    /* 외부조인
       String query = "select m from Member m left join m.team t ";
       List<Member> result = em.createQuery(query, Member.class)
                     .getResultList();
     */

    //조인
    // 내부 조인: SELECT m FROM Member m [INNER] JOIN m.team t
    // 외부 조인: SELECT m FROM Member m LEFT [OUTER] JOIN m.team t
    // 세타 조인: select count(m) from Member m, Team t where m.username = t.name

    //조인 - ON 절
    //ON절을 활용한 조인(JPA 2.1부터 지원)
    // 1. 조인 대상 필터링
    // 2. 연관관계 없는 엔티티 외부 조인(하이버네이트 5.1부터)

    //1. 조인 대상 필터링
    //예) 회원과 팀을 조인하면서, 팀 이름이 A인 팀만 조인
    //JPQL
    // SELECT m, t FROM Member m LEFT JOIN m.team t on t.name = 'A'
    //SQL:
    // SELECT m.*, t.* FROM
    // Member m LEFT JOIN Team t ON m.TEAM_ID=t.id and t.name='A'

    //2. 연관관계 없는 엔티티 외부 조인
    //예) 회원의 이름과 팀의 이름이 같은 대상 외부 조인
    //JPQL
    // SELECT m, t FROM
    // Member m LEFT JOIN Team t on m.username = t.name
    //SQL
    // SELECT m.*, t.* FROM
    //  Member m LEFT JOIN Team t ON m.username = t.name
//----------------------------------------------------------------------
    //서브 쿼리
    //나이가 평균보다 많은 회원
    //select m from Member m
    //where m.age > (select avg(m2.age) from Member m2)

    //• 한 건이라도 주문한 고객
    //select m from Member m
    //where (select count(o) from Order o where m = o.member) > 0

    //서브 쿼리 지원 함수
    // [NOT] EXISTS (subquery): 서브쿼리에 결과가 존재하면 참
    // {ALL | ANY | SOME} (subquery)
    //  - ALL: 모두 만족하면 참
    //  - ANY, SOME: 같은 의미, 조건을 하나라도 만족하면 참
    //[NOT] IN (subquery): 서브쿼리의 결과 중 하나라도 같은 것이 있으면 참

    //서브 쿼리 - 예제
    //팀A 소속인 회원
    //select m from Member m
    //where exists (select t from m.team t where t.name = ‘팀A')
    //• 전체 상품 각각의 재고보다 주문량이 많은 주문들
    //select o from Order o
    //where o.orderAmount > ALL (select p.stockAmount from Product p)
    //• 어떤 팀이든 팀에 소속된 회원
    //select m from Member m
    //where m.team = ANY (select t from Team t)

    //JPA 서브 쿼리 한계
    //JPA는 WHERE, HAVING 절에서만 서브 쿼리 사용 가능
    // SELECT 절도 가능(하이버네이트에서 지원)
    // FROM 절의 서브 쿼리는 현재 JPQL에서 불가능
    // 조인으로 풀 수 있으면 풀어서 해결
//----------------------------------------------------------------------
    //기본 CASE 식
    // select case when m.age <= 10 then '학생요금'
    // when m.age >= 60 then '경로요금'
    // else '일반요금' end
    // from Member m

    //단순 CASE 식
    //select case t.name
    // when '팀A' then '인센티브110%'
    // when '팀B' then '인센티브120%'
    // else '인센티브105%' end
    //from Team t

    //COALESCE: 하나씩 조회해서 null이 아니면 반환
    //사용자 이름이 없으면 이름 없는 회원을 반환
    //select coalesce(m.username,'이름 없는 회원') from Member m

    //NULLIF: 두 값이 같으면 null 반환, 다르면 첫번째 값 반환
    //사용자 이름이 ‘관리자’면 null을 반환하고 나머지는 본인의 이름을 반환
    //select NULLIF(m.username, '관리자') from Member m
//-----------------------------------------------------------------------
    //JPQL 기본 함수
    // CONCAT
    // SUBSTRING
    // TRIM
    // LOWER, UPPER
    // LENGTH
    // LOCATE
    // ABS, SQRT, MOD
    // SIZE, INDEX(JPA 용도)

    //사용자 정의 함수 호출 (기본함수외 db내 함수를 부르고 싶을때)
    //하이버네이트는 사용전 방언에 추가해야 한다.
    //• 사용하는 DB 방언을 상속받고, 사용자 정의 함수를 등록한다.
    //select function('group_concat', i.name) from Item i

    //경로 표현식
    // .(점)을 찍어 객체 그래프를 탐색하는 것

    //경로 표현식 용어 정리
    // 상태 필드(state field): 단순히 값을 저장하기 위한 필드 (ex: m.username)
    // 연관 필드(association field): 연관관계를 위한 필드
    // 단일 값 연관 필드: @ManyToOne, @OneToOne, 대상이 엔티티(ex: m.team)
    // 컬렉션 값 연관 필드: @OneToMany, @ManyToMany, 대상이 컬렉션(ex: m.orders)

    //경로 표현식 특징
    // 상태 필드(state field): 경로 탐색의 끝, 탐색X
    // 단일 값 연관 경로: 묵시적 내부 조인(inner join) 발생, 탐색O
    // 컬렉션 값 연관 경로: 묵시적 내부 조인 발생, 탐색X
    // FROM 절에서 명시적 조인을 통해 별칭을 얻으면 별칭을 통해 탐색 가능

    //상태 필드 경로 탐색
    // JPQL: select m.username, m.age from Member m
    // SQL: select m.username, m.age from Member m
    
    //명시적 조인, 묵시적 조인
    // 명시적 조인: join 키워드 직접 사용
    // - select m from Member m join m.team t
    // 묵시적 조인: 경로 표현식에 의해 묵시적으로 SQL 조인 발생 (내부 조인만 가능)
    // - select m.team from Member m

    //경로 탐색을 사용한 묵시적 조인 시 주의사항
    // 항상 내부 조인을 사용할 것.
    // 컬렉션은 경로 탐색의 끝, 명시적 조인을 통해 별칭을 얻어야함
    // 경로 탐색은 주로 SELECT, WHERE 절에서 사용하지만 묵시적 조인으로 인해 SQL의 FROM (JOIN) 절에 영향을 줌
//---------------------------------------------------------------------------
    //페치 조인(fetch join)
    // SQL 조인 종류X
    // JPQL에서 성능 최적화를 위해 제공하는 기능
    // 연관된 엔티티나 컬렉션을 SQL 한 번에 함께 조회하는 기능

    // join fetch 명령어 사용
    // - 페치 조인 ::= [ LEFT [OUTER] | INNER ] JOIN FETCH 조인경로

    //컬렉션 페치 조인(Collection fetch join)
    // 일대다 관계, 컬렉션 페치 조인
    // [JPQL]
    // select t from Team t join fetch t.members
    // where t.name = ‘팀A'
    // [SQL]
    // SELECT T.*, M.* FROM TEAM T
    // INNER JOIN MEMBER M ON T.ID=M.TEAM_ID
    // WHERE T.NAME = '팀A'

    //페치 조인과 DISTINCT
    // SQL의 DISTINCT는 중복된 결과를 제거하는 명령
    // JPQL의 DISTINCT 2가지 기능 제공
    // 1. SQL에 DISTINCT를 추가
    // 2. 애플리케이션에서 엔티티 중복 제거
    // select distinct t from Team t join fetch t.members
    // where t.name = ‘팀A’
    // SQL에 DISTINCT를 추가하지만 데이터가 다르므로 SQL 결과에서 중복제거 실패
    // DISTINCT가 추가로 애플리케이션에서 중복 제거시도
    // 같은 식별자를 가진 Team 엔티티 제거

    //페치 조인과 일반 조인의 차이
    // 일반 조인 실행시 연관된 엔티티를 함께 조회하지 않음
    // [JPQL]
    // select t from Team t join t.members m
    // where t.name = ‘팀A'
    // [SQL]
    // SELECT T.* FROM TEAM T
    // INNER JOIN MEMBER M ON T.ID=M.TEAM_ID
    // WHERE T.NAME = '팀A'

    // JPQL은 결과를 반환할 때 연관관계 고려X
    // 단지 SELECT 절에 지정한 엔티티만 조회할 뿐
    // 여기서는 팀 엔티티만 조회하고, 회원 엔티티는 조회X
    // 페치 조인을 사용할 때만 연관된 엔티티도 함께 조회(즉시 로딩)
    // 페치 조인은 객체 그래프를 SQL 한번에 조회하는 개념

    //페치 조인의 특징과 한계
    // 페치 조인 대상에는 별칭을 줄 수 없다.
    // - 하이버네이트는 가능, 가급적 사용X
    // 둘 이상의 컬렉션은 페치 조인 할 수 없다.
    // 컬렉션을 페치 조인하면 페이징 API(setFirstResult, setMaxResults)를 사용할 수 없다.
    // - 일대일, 다대일 같은 단일 값 연관 필드들은 페치 조인해도 페이징 가능
    // - 하이버네이트는 경고 로그를 남기고 메모리에서 페이징(매우 위험)

    // 연관된 엔티티들을 SQL 한 번으로 조회 - 성능 최적화
    // 엔티티에 직접 적용하는 글로벌 로딩 전략보다 우선함
    //  - @OneToMany(fetch = FetchType.LAZY) //글로벌 로딩 전략
    // 실무에서 글로벌 로딩 전략은 모두 지연 로딩
    // 최적화가 필요한 곳은 페치 조인 적용

    //페치 조인 - 정리
    // 모든 것을 페치 조인으로 해결할 수는 없음
    // 페치 조인은 객체 그래프를 유지할 때 사용하면 효과적
    // 여러 테이블을 조인해서 엔티티가 가진 모양이 아닌 전혀 다른 결과를 내야 하면,
    // 페치 조인 보다는 일반 조인을 사용하고 필요한 데이터들만 조회해서 DTO로 반환하는 것이 효과
//--------------------------------------------------------------------------
    //JPQL - 다형성 쿼리
    //TYPE
    // 조회 대상을 특정 자식으로 한정
    // 예) Item 중에 Book, Movie를 조회
    // [JPQL]
    // select i from Item i where type(i) IN (Book, Movie)
    // [SQL]
    // select i from i where i.DTYPE in (‘B’, ‘M’)

    //TREAT(JPA 2.1)
    // 자바의 타입 캐스팅과 유사
    // 상속 구조에서 부모 타입을 특정 자식 타입으로 다룰 때 사용
    // FROM, WHERE, SELECT(하이버네이트 지원) 사용
    // 예) 부모인 Item과 자식 Book이 있다.
    // [JPQL]
    // select i from Item i where treat(i as Book).auther = ‘kim’
    // [SQL]
    // select i.* from Item i where i.DTYPE = ‘B’ and i.auther = ‘k
//----------------------------------------------------------------------
    //엔티티 직접 사용 - 기본 키 값
    // JPQL에서 엔티티를 직접 사용하면 SQL에서 해당 엔티티의 기본 키 값을 사용
    // [JPQL]
    // select count(m.id) from Member m //엔티티의 아이디를 사용
    // select count(m) from Member m //엔티티를 직접 사용
    // [SQL](JPQL 둘다 같은 다음 SQL 실행)
    // select count(m.id) as cnt from Member m
    //엔티티 직접 사용 - 외래 키 값
    //
//----------------------------------------------------------------------
    //Named 쿼리 - 정적 쿼리
    // 미리 정의해서 이름을 부여해두고 사용하는 JPQL
    // 정적 쿼리만 가능 (동적 불가)
    // 어노테이션, XML에 정의
    // 애플리케이션 로딩 시점에 초기화 후 재사용
    // 애플리케이션 로딩 시점에 쿼리를 검증
    //어노테이션 - @NamedQuery
//----------------------------------------------------------------------
    //벌크 연산
    // 쿼리 한 번으로 여러 테이블 로우 변경(엔티티)
    // executeUpdate()의 결과는 영향받은 엔티티 수 반환
    // UPDATE, DELETE 지원
    // INSERT(insert into .. select, 하이버네이트 지원)

    //벌크 연산 주의
    // 벌크 연산은 영속성 컨텍스트를 무시하고 데이터베이스에 직접 쿼리
    // 벌크 연산을 먼저 실행
    // 벌크 연산 수행 후 영속성 컨텍스트 초기화
}
