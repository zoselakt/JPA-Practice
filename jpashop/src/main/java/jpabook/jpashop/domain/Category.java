package jpabook.jpashop.domain;

import jpabook.jpashop.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
public class Category {
    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToMany // 다대다 연습 예제로 만든것
    @JoinTable(name = "category_item",
        joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    )  // 조인테이블이 있어야 가능!
    private List<Item> items = new ArrayList<>();

    // 셀프로 양방향 관계 매핑
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent; //부모타입

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>(); //자식타입

    //연관관계 메서드
    public void addChildCategory(Category child){
        this.child.add(child);
        child.setParent(this);
    }
}
