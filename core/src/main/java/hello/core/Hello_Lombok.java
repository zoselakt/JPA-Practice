package hello.core;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Hello_Lombok {
    private String name;
    private int age;

    public static void main(String[] args) {
        Hello_Lombok helloLombok = new Hello_Lombok();
        helloLombok.setName("lombok_name");

        String name = helloLombok.getName();
        System.out.println("name = "+ name);

        System.out.println("helloLombok" + helloLombok);
    }
}
