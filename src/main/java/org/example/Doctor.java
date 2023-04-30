package org.example;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("D")
public class Doctor extends Person {
    private String specialization;
    private int cabinet;
    private String work_days;
    private String work_time;
    private List<Patient> patients = new ArrayList<>();

    public Doctor(String name, String lastName, int age, String phone_number, String specialization, int cabinet, String work_days, String work_time) {
        super(name, lastName, age, phone_number);
        this.specialization = specialization;
        this.cabinet = cabinet;
        this.work_days = work_days;
        this.work_time = work_time;
    }

    public Doctor() {
    }


    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "doctor_to_patient",
            joinColumns = @JoinColumn(name = "doctor_id"),
            inverseJoinColumns = @JoinColumn(name = "patient_id")
    )
    public List<Patient> getPatients() {
        return this.patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

    public void add_patient(Patient patient) {
        patients.add(patient);
        patient.getDoctors().add(this);
    }

    public void remove_patient(Patient patient) {
        patients.remove(patient);
        patient.getDoctors().remove(this);
    }


    @Column(name = "specialization")
    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization0) {
        this.specialization = specialization0;
    }

    @Column(name = "cabinet")
    public int getCabinet() {
        return cabinet;
    }

    public void setCabinet(int cabinet) {
        this.cabinet = cabinet;
    }

    @Column(name = "work_days")
    public String getWork_days() {
        return work_days;
    }

    public void setWork_days(String work_days) {
        this.work_days = work_days;
    }

    @Column(name = "work_time")
    public String getWork_time() {
        return work_time;
    }


    public void setWork_time(String work_time) {
        this.work_time = work_time;
    }


}
