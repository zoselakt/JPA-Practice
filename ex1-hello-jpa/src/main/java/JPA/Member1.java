package JPA;

import hellojpa.RoleType;
import hellojpa.Team;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Member1 {
    // 제약조건 추가: 회원 이름은 필수, 10자 초과X
    // DDL 생성 기능은 DDL을 자동 생성할 때만 사용되고 JPA의 실행 로직에는 영향을 주지 않는다
    // @Column(unique = true, length = 10)

//    @Id
//    private Long id;
//    private String name;
//    private int age;

    //    public Member(long id, String name) {
//        this.id = id;
//        this.name = name;
//    }

    //getter / setter
//    public long getId() { return id; }

    //public void setId(long id) { this.id = id; }
    //public String getUsername() {
//    public String getName() {
//        return name;
//        return username;
//    }
//    public void setUsername(String username) {
//    public void setName(String name) {
//        this.name = name;
//        this.username = username;
//    }
    //getter / setter 끝

    @Id
    private Long id;

    @Column(name = "name")
//    private String username;
    private Integer age;


    //일대다 연습
//    @Column(name = "USERNAME")
//    private String username;
//    public String getUsername() { return username; }
//    public void setUsername(String username) { this.username = username; }
    //일대다연습 끝


    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }


    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @Lob
    private String description;

    public Member1() {
        //JPA에서는 constructor만들때 빈 생성자를 추가로 만들어야 한다.
    }

//    @Column : 컬럼 매핑
//    @Temporal : 날짜 타입 매핑 / 참고: LocalDate, LocalDateTime을 사용할 때는 생략 가능(최신 하이버네이트 지원)
//    @Enumerated : enum 타입 매핑 / 주의! ORDINAL 사용X
//    @Lob : BLOB, CLOB 매핑 / 매핑하는 필드 타입이 문자면 CLOB 매핑, 나머지는 BLOB 매핑
       //    CLOB: String, char[], java.sql.CLOB / BLOB: byte[], java.sql. BLOB
//    @Transient : 특정 필드를 컬럼에 매핑하지 않음(매핑 무시) / 주로 메모리상에서만 임시로 어떤 값을 보관하고 싶을 때 사용
}

//    기본 키 매핑 방법
//    직접 할당: @Id만 사용
//    자동 생성(@GeneratedValue)
//      • IDENTITY: 데이터베이스에 위임, MYSQL
//      • SEQUENCE: 데이터베이스 시퀀스 오브젝트 사용, ORACLE / @SequenceGenerator 필요
//      • TABLE: 키 생성용 테이블 사용, 모든 DB에서 사용 / @TableGenerator 필요
//      • AUTO: 방언에 따라 자동 지정, 기본값

// IDENTITY 전략 - 특징
//  - 기본 키 생성을 데이터베이스에 위임
//    • 주로 MySQL, PostgreSQL, SQL Server, DB2에서 사용(예: MySQL의 AUTO_ INCREMENT)
//    • JPA는 보통 트랜잭션 커밋 시점에 INSERT SQL 실행
//    • AUTO_ INCREMENT는 데이터베이스에 INSERT SQL을 실행한 이후에 ID 값을 알 수 있음
//    • IDENTITY 전략은 em.persist() 시점에 즉시 INSERT SQL 실행하고 DB에서 식별자를 조회
    // 데이터베이스 시퀀스는 유일한 값을 순서대로 생성하는 특별한 데이터베이스 오브젝트

// SEQUENCE전략 - @SequenceGenerator 괄호 - 기본값
// - name:  식별자 생성기 이름 (필수)
// - sequenceName: 데이터베이스에 등록되어 있는 시퀀스 이름 (hibernate_sequence)
// - initialValue : DDL 생성 시에만 사용됨, 시퀀스 DDL을 생성할 때 처음 1 시작하는수를 지정한다. (1)
// - allocationSize : 시퀀스 한 번 호출에 증가하는 수(성능 최적화에 사용됨
//                    데이터베이스 시퀀스 값이 하나씩 증가하도록 설정되어 있으면 이 값을 반드시 1로 설정해야 한다 (50)
// - catalog, schema : 데이터베이스 catalog, schema 이름

//    TABLE 전략
//    키 생성 전용 테이블을 하나 만들어서 데이터베이스 시퀀스를 흉내내는 전략
//        • 장점: 모든 데이터베이스에 적용 가능
//        • 단점: 성능
// @TableGenerator - 속성
// - name: 식별자 생성기 이름 (필수)
// - table:  키생성 테이블명 (hibernate_sequences)
// - pkColumnName: 시퀀스 컬럼명 (sequence_name)
// - valueColumnName: 시퀀스 값 컬럼명 (next_val)
// - pkColumnValue: 키로 사용할 값 이름 (엔티티 이름)
// - initialValue:  초기 값, 마지막으로 생성된 값이 기준이다. (0)
// - allocationSize: 시퀀스 한 번 호출에 증가하는 수(성능 최적화에 사용됨) (50)
// - catalog, schema 데이터베이스 catalog, schema 이름
// - uniqueConstraints(DDL): 유니크 제약 조건을 지정할 수 있다.
//
// 권장하는 식별자 전략
// - 기본 키 제약 조건: null 아님, 유일, 변하면 안된다. / 대리키(대체키)를 사용하자.
// - 권장: Long형 + 대체키 + 키 생성전략 사용