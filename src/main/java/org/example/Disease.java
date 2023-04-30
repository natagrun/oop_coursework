package org.example;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "hospital_data.disease")
public class Disease {
    private int id;
    private String Name;
    private int count;

    private List<Patient> patients = new ArrayList<>();

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public Disease(String name, int count) {
        Name = name;
        this.count = count;
    }

    public Disease() {
    }


    @Column(name = "name")
    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }

    @ManyToMany(mappedBy = "diseases")
    public List<Patient> getPatients(){
        return this.patients;
    }
    public void setPatients(List<Patient> patients){
        this.patients=patients;
    }


    @Column(name = "count")
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }

    public void Incidense(int i){
        this.count+=i;

    }


}
