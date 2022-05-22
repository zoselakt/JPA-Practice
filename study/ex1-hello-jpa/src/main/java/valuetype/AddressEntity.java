package valuetype;

import javax.persistence.*;

@Entity
@Table(name = "ADDRESS")
public class AddressEntity {
    @Id @GeneratedValue
    private Long id;
    private Address address;

    public AddressEntity() {
    }

    public AddressEntity(String city, String street, String zipcode){
        this.address = new Address(city, street, zipcode);
    }

    public AddressEntity(Long id, Address address) {
        this.id = id;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
