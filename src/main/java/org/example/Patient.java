package org.example;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("P")
public class Patient extends Person {
    private int blood_type;
    private List<Doctor> doctors = new ArrayList<>();
    private List<Disease> diseases = new ArrayList<>();

    public Patient() {
    }

    public Patient(String name, String lastName, int age, String phone_number, int blood) {
        super(name, lastName, age, phone_number);
        this.blood_type = blood;
    }

    @Column(name = "blood_type")
    public int getBloodType() {
        return blood_type;
    }

    public void setBloodType(int bloodtype) {
        blood_type = bloodtype;
    }


    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "patient_to_disease",
            joinColumns = @JoinColumn(name = "patient_id"),
            inverseJoinColumns = @JoinColumn(name = "disease_id"))

    public List<Disease> getDiseases() {
        return diseases;
    }

    public void setDiseases(List<Disease> diseases) {
        this.diseases = diseases;
    }

    public void add_disease(Disease disease) {
        diseases.add(disease);
        disease.getPatients().add(this);
        disease.setCount(disease.getCount() + 1);
    }

    public void remove_disease(Disease disease) {
        diseases.remove(disease);
        disease.getPatients().remove(this);
    }

    @ManyToMany(mappedBy = "patients")
    public List<Doctor> getDoctors() {
        return this.doctors;
    }

    public void setDoctors(List<Doctor> doctors) {
        this.doctors = doctors;
    }
}