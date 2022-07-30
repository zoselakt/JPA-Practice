package Mapping;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity()
@DiscriminatorValue("A")
public class Album extends Item{
    private String artist;
}

//슈퍼타입 서브타입 논리 모델을 실제 물리 모델로 구현하는 방법

// 각각 테이블로 변환 -> 조인 전략
// 통합 테이블로 변환 -> 단일 테이블 전략
// 서브타입 테이블로 변환 -> 구현 클래스마다 테이블 전략

// 주요 어노테이션
// @Inheritance(strategy=InheritanceType.XXX)
// -JOINED: 조인 전략
// -SINGLE_TABLE: 단일 테이블 전략
// -TABLE_PER_CLASS: 구현 클래스마다 테이블 전략
// @DiscriminatorColumn(name=“DTYPE”)
// @DiscriminatorValue(“XXX”)

// 조인 전략
// 장점
// 테이블 정규화
// 외래 키 참조 무결성 제약조건 활용가능
// 저장공간 효율화
// 단점
// 조회시 조인을 많이 사용, 성능 저하
// 조회 쿼리가 복잡함
// 데이터 저장시 INSERT SQL 2번 호출

// 단일 테이블 전략
// 장점
// 조인이 필요 없으므로 일반적으로 조회 성능이 빠름
// 조회 쿼리가 단순함
// 단점
// 자식 엔티티가 매핑한 컬럼은 모두 null 허용
// 단일 테이블에 모든 것을 저장하므로 테이블이 커질 수 있다. 상황에 따라서 조회 성능이 오히려 느려질 수 있다

// 구현 클래스마다 테이블 전략
// - 이 전략은 데이터베이스 설계자와 ORM 전문가 둘 다 추천X