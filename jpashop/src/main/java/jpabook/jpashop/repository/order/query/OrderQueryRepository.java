package jpabook.jpashop.repository.order.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {

    private final EntityManager em;
    //주문 조회 V4: JPA에서 DTO 직접 조회

    // 컬렉션은 별도로 조회
    // * Query: 루트 1번, 컬렉션 N 번
    // * 단건 조회에서 많이 사용하는 방식
    public List<OrderQueryDto> findOrderQueryDtos(){
        //루트 조회(toOne 코드를 모두 한번에 조회)
        List<OrderQueryDto> result = findOrders();
        //루프를 돌면서 컬렉션 추가(추가 쿼리 실행)
        result.forEach(o -> {
            List<OrderItemQueryDto> orderItems = findOrderItems(o.getOrderId());
            o.setOrderItems(orderItems);
        });
        return result;
    }

    private List<OrderItemQueryDto> findOrderItems(Long orderId) {
        return em.createQuery(
                "select new jpabook.jpashop.repository.order.query.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count)" +
                        "from OrderItem oi" +
                        "join oi.item i" +
                        "where oi.order.id = : orderId", OrderItemQueryDto.class)
                .setParameter("orderId", orderId)
                .getResultList();
    }

    public List<OrderQueryDto> findOrders(){
        return em.createQuery(
                "select new jpabook.jpashop.repository.order.query.OrderQueryDto(o.id, m.name, o.orderDate, o.status, d.address)" +
                        "select from Order o" +
                        "join o.member m" +
                        "join o.delivery d", OrderQueryDto.class
        ).getResultList();
    }

    //Query: 루트 1번, 컬렉션 N 번 실행
    //ToOne(N:1, 1:1) 관계들을 먼저 조회하고, ToMany(1:N) 관계는 각각 별도로 처리한다.
    //이런 방식을 선택한 이유는 다음과 같다.
    //ToOne 관계는 조인해도 데이터 row 수가 증가하지 않는다.
    //ToMany(1:N) 관계는 조인하면 row 수가 증가한다.
    //row 수가 증가하지 않는 ToOne 관계는 조인으로 최적화 하기 쉬우므로 한번에 조회하고, ToMany
    //관계는 최적화 하기 어려우므로 findOrderItems() 같은 별도의 메서드로 조회한

    public List<OrderQueryDto> findAllByDto_optimization() {
        List<OrderQueryDto> result = findOrders();

        List<Long> orderIds = result.stream()
                .map(o -> o.getOrderId())
                .collect(Collectors.toList());

        List<OrderItemQueryDto> orderItems = em.createQuery(
                        "select new jpabook.jpashop.repository.order.query.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count)" +
                                "from OrderItem oi" +
                                "join oi.item i" +
                                "where oi.order.id = : orderIds", OrderItemQueryDto.class)
                .setParameter("orderIds", orderIds)
                .getResultList();

        Map<Long, List<OrderItemQueryDto>> orderItemMap = orderItems.stream()
                .collect(Collectors.groupingBy(orderItemQueryDto -> orderItemQueryDto.getOrderId()));

        result.forEach(o -> o.setOrderItems(orderItemMap.get(o.getOrderId())));

        return result;

    }
    //Query: 루트 1번, 컬렉션 1번
    //ToOne 관계들을 먼저 조회하고, 여기서 얻은 식별자 orderId로 ToMany 관계인 OrderItem 을 한꺼번에 조회
    //MAP을 사용해서 매칭 성능 향상(O(1))

    public List<OrderFlatDto> findAllByDto_flat() {
        return em.createQuery(
                "select new jpabook.jpashop.repository.order.query.OrderFlatDto(o.id, m.name, o.orderDate, o.status, d.address, i.name, oi.orderPrice, oi.count)" +
                        "from Order o" +
                        "join O.member m" +
                        "join o.delivery d" +
                        "join o.orderItems oi" +
                        "join oi.item i", OrderFlatDto.class)
                .getResultList();
    }
    //Query: 1번
    //단점
    // 쿼리는 한번이지만 조인으로 인해 DB에서 애플리케이션에 전달하는 데이터에 중복 데이터가 추가되므로 상황에 따라 V5 보다 더 느릴수 도 있다.
    // 애플리케이션에서 추가 작업이 크다.
    // 페이징 불가능
}
