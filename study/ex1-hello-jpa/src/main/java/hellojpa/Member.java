package hellojpa;

import javax.persistence.*;

@Entity
public class Member {
    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    //@Column(name = "TEAM_ID")
    //private Long teamId;

    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Team getTeam() {
        return team;
    }
    public void setTeam(Team team) {
        this.team = team;
    }
/*
    public void setTeam(Team team) { //김영한 기술이사는 changeTeam으로 바꿔쓰며 코드작성시 한눈에 보이게끔 작성한다.
        this.team = team;
        team.getMembers().add(this); //main에 작성하지 않고 여기에 작성하는것이 좋다
    }
 */
}
