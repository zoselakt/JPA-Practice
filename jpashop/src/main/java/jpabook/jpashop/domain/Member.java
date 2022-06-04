package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @NotEmpty
    private String name;

    @Embedded // 내장타입으로 맵핑했다. 알림
    private Address address;

    @JsonIgnore //회원조회API / 주문을 제외하고 회원정보만 추출하고 싶을때 / 무한루프 방지
    @OneToMany(mappedBy = "member") //읽기전용!
    private List<Order> orders = new ArrayList<>();
}
