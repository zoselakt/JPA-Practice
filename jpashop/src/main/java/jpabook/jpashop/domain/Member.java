package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded // 내장타입으로 맵핑했다. 알림
    private Address address;

    @OneToMany(mappedBy = "member") //읽기전용!
    private List<Order> orders = new ArrayList<>();
}
