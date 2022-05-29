package jpabook.jpashop.repository;

import jpabook.jpashop.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {
    private final EntityManager em;

    public void save(Item item){
        if(item.getId() == null){ //persist에 저장하기 전까지 id값이 없다.
            em.persist(item);
        }else {     //persist에 저장이 되어있다면 이미있는 회원
            em.merge(item); //merge로 강제로 업데이트
        }
    }

    public Item findOne(Long id){
        return em.find(Item.class, id);
    }
    public List<Item> findAll(){
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
    //상품 리포지토리의 저장 메서드 분석 ItemRepository
    // save() 메서드는 식별자 값이 없으면( null ) 새로운 엔티티로 판단해서 영속화(persist)하고 식별자가 있으면 병합(merge)
    // 지금처럼 준영속 상태인 상품 엔티티를 수정할 때는 id 값이 있으므로 병합 수행

    //새로운 엔티티 저장과 준영속 엔티티 병합을 편리하게 한번에 처리
    // 상품 리포지토리에선 save() 메서드를 유심히 봐야 하는데, 이 메서드 하나로 저장과 수정(병합)을 다 처리한다.
    // 코드를 보면 식별자 값이 없으면 새로운 엔티티로 판단해서 persist() 로 영속화하고 만약 식별자값이 있으면 이미 한번 영속화 되었던 엔티티로 판단해서 merge() 로 수정(병합)한다.
    // 결국 여기서의 저장(save)이라는 의미는 신규 데이터를 저장하는 것뿐만 아니라 변경된 데이터의 저장이라는 의미도 포함한다.
    // 이렇게 함으로써 이 메서드를 사용하는 클라이언트는 저장과 수정을 구분하지 않아도 되므로 클라이언트의 로직이 단순해진다.
    // 여기서 사용하는 수정(병합)은 준영속 상태의 엔티티를 수정할 때 사용한다.
    // 영속 상태의 엔티티는 변경 감지(dirty checking)기능이 동작해서 트랜잭션을 커밋할 때 자동으로 수정되므로 별도의 수정 메서드를 호출할 필요가 없고 그런 메서드도 없다.
}
