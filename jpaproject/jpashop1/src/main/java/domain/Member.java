package domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;
//Entity?
//JPA에서는 엔티티는 테이블에 대응하는 하나의 클래스

// 객체와 테이블매핑시 사용하는 어노테이션
// 기본생성자필수이며 접근제어자는 public 또는 protected이여야함.
// final클래스에는 사용불가
//Entity와 table
// 여기서 Entity는 각각의 변수를 필드를 가지고 있는 클래스이고
// table은 DB와 직접 연결할 컬럼을 가지고있으며 DB와 대응하고
// member_id 의 이름으로 Entity의 필드와 DB의 컬럼명을 맞춘다.
// 맞추기위해서 @Column 어노테이션을 사용하여 컬럼명을 맞추며 PK값과 매칭하여야한다.


//lombok
//반복되는 메서드 작성 코드를 줄여주는 코드 라이브러리
// 여러가지 어노테이션을 제공하고 이를 기반으로 코드를
// 컴파일과정에서 생성해 주는 방식으로 동작하는 라이브러리

// Lombok의 @Data 어노테이션이나 @ToString 어노테이션으로 자동 생성되는 toString()메서드는
// 순환 참조 또는 무한 재귀 호출 문제로 인해 StackOverflowError가 발생할수도 있습니다.

//종류
//@Getter/@Setter	코드가 컴파일 될 때, Getter/Setter 메소드를 생성합니다.
//@ToString	toString() 메소드를 생성합니다. @ToString(exclude={"제외할 값"}) 처럼 원하지 않는 속성은 제외할 수 있습니다.
//@EqualsAndHashCode	해당 객체의 equals()와 hashCode() 메소드를 생성합니다.
//@NoArgsConstructor	파라미터를 받지 않는 생성자를 만들어 줍니다.
//@RequiredArgsConstructor	지정된 속성들에 대해서만 생성자를 만듭니다.
//@AllArgsConstructor	모든 속성에 대해서 생성자를 만들어 냅니다.
//@Data	@ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor를 합쳐 둔 어노테이션입니다.
//@Value	불변 클래스를 생성할 때 사용합니다.

//JPA 기본 키 매핑 전략, @Id
// 1)직접 할당 : 기본 키를 애플리케이션에서 직접 엔티티클래스의 @Id 필드에 set해준다.
// 2)자동 생성 : 대리 키 사용 방식
// - IDENTITY : 기본 키 생성을 데이터베이스에 위임한다.
// - SEQUENCE : 데이터베이스 시퀀스를 사용해서 기본 키를 할당한다.
// - TABLE : 키 생성 테이블을 사용한다.



@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = member_id)
    private Long id;

    private String name;

    private Address address;

    private List<Order> orders = new ArrayList<>();
}