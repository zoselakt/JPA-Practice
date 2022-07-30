package hellojpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team extends BaseEntity{
    @Id @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;
    private String name;

    @OneToMany(mappedBy = "team")
    private List<Member> members = new ArrayList<>();

    // mappedBy
    // mappedBy 옵션은 객체간 양방향 연관관계일 경우에 보통 사용한다.
    // 일반적으로 외래키는 ManyToOne이 가지고 있으므로 연관관계의 주인은 ManyToOne으로 생각하면 된다 / 1 대 다의 경우 List나 Set으로 받아야한다.
    // 주인은 mappedBy 속성 사용X / 주인이 아니면 mappedBy 속성으로 주인 지정 / 주인은 외래키가 있는 곳으로 지정
    public void addMember(Member member) {
        member.setTeam(this);
        members.add(member);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }
}
