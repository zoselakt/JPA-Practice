package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable //어딘가에 저장될수 있다.
@Getter
public class Address {
    private String city;
    private String street;
    private String zipcode;

    protected Address(){ }
    //참고: 값 타입은 변경 불가능하게 설계해야 한다
    //jpa 스펙상 setter를 쓰지않는 경우 넣어야한다. protected 또는 기본생성자를 만든다.
    // 기본생성자의 public으로 두는것보다 protected로 설정하는것이 낫다.

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
