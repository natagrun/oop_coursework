package org.example;

import javax.persistence.*;


@Entity
@Table(name = "hospital_data.Person")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "BD_TYPE")
public class Person {
    protected int id;
    protected String Name;
    protected String LastName;
    protected int Age;
    protected String PhoneNumber;

    public Person() {

    }

    public Person(String name, String lastName, int age, String phoneNumber) {
        this.Name = name;
        this.LastName = lastName;
        this.Age = age;
        this.PhoneNumber = phoneNumber;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "name")
    public String getName() {
        return this.Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    @Column(name = "last_Name")
    public String getLastName() {
        return this.LastName;
    }

    public void setLastName(String name) {
        this.LastName = name;
    }

    @Column(name = "age")
    public int getAge() {
        return this.Age;
    }

    public void setAge(int age) {
        this.Age = age;
    }

    @Column(name = "phone_number")
    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }


}
