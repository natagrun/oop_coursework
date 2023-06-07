package org.example;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

import static org.example.NewMainWindow.service;

public class Service {
    private final EntityManager manager = Persistence.createEntityManagerFactory("test_persistence").createEntityManager();
    private Query query;

    public void startConnection() {
        manager.getTransaction().begin();
    }

    public void isActive() {
        if (!manager.getTransaction().isActive()) startConnection();
    }

    public List<Doctor> getAllDoctors() {
        isActive();
        query = manager.createNativeQuery("SELECT * FROM Person where bd_type ='D'", Doctor.class);
        return (List<Doctor>) query.getResultList();
    }

    public List<Patient> getAllPatients() {
        isActive();
        query = manager.createNativeQuery("SELECT * FROM Person where bd_type ='P'", Patient.class);
        return (List<Patient>) query.getResultList();
    }

    public List<Disease> getAllDiseases() {
        isActive();
        query = manager.createNativeQuery("SELECT * FROM Disease ORDER BY Count DESC ", Disease.class);
        return (List<Disease>) query.getResultList();
    }

    public void addDoctor(String name, String lastName, int age, String phone_number, String specialization, int cabinet, String work_days, String work_time) {
        isActive();
        Doctor doctor = new Doctor(name, lastName, age, phone_number, specialization, cabinet, work_days, work_time);
        manager.persist(doctor);
        manager.getTransaction().commit();
    }

    public void addPatient(String name, String lastName, int age, String phone_number, int blood) {
        isActive();
        Patient patient = new Patient(name, lastName, age, phone_number, blood);
        manager.persist(patient);
        manager.getTransaction().commit();
    }

    public void addDisease(String name) {
        isActive();
        Disease disease = new Disease(name);
        disease.setCount(0);
        manager.persist(disease);
        manager.getTransaction().commit();
    }

    public void removePerson(int id) {
        isActive();
        Person person = manager.find(Person.class, id);
        query = manager.createNativeQuery("DELETE FROM doctor_to_patient where doctor_id =?1");
        query.setParameter(1, person.id);
        query.executeUpdate();
        query = manager.createNativeQuery("DELETE FROM doctor_to_patient where patient_id =?1");
        query.setParameter(1, person.id);
        query.executeUpdate();
        query = manager.createNativeQuery("DELETE FROM patient_to_disease where patient_id =?1");
        query.setParameter(1, person.id);
        query.executeUpdate();
        query = manager.createNativeQuery("DELETE FROM Person where id =?1");
        query.setParameter(1, person.id);
        query.executeUpdate();
        manager.getTransaction().commit();
    }

    public void editDoctor(Doctor doctor, String name, String lastName, int age, String phone_number, String specialization, int cabinet, String work_days, String work_time) {
        isActive();
        doctor.setName(name);
        doctor.setLastName(lastName);
        doctor.setAge(age);
        doctor.setPhoneNumber(phone_number);
        doctor.setSpecialization(specialization);
        doctor.setCabinet(cabinet);
        doctor.setWork_days(work_days);
        doctor.setWork_time(work_time);
        manager.merge(doctor);
        manager.getTransaction().commit();
    }

    public void editPatient(Patient patient, String name, String lastName, int age, String phone_number, int blood) {
        isActive();
        patient.setName(name);
        patient.setLastName(lastName);
        patient.setAge(age);
        patient.setPhoneNumber(phone_number);
        patient.setBloodType(blood);
        manager.merge(patient);
        manager.getTransaction().commit();
    }


    public List<Patient> getDoctorsPatients(int doctor_id) {
        isActive();
        Doctor doctor = manager.find(Doctor.class, doctor_id);
        return doctor.getPatients();
    }

    public List<Disease> getPatientsDiseases(int patient_id) {
        isActive();
        Patient patient = manager.find(Patient.class, patient_id);
        return patient.getDiseases();
    }

    public Person getPerson(int id) {
        isActive();
        return manager.find(Person.class, id);
    }

    public Doctor getDoctor(int id) {
        isActive();
        return manager.find(Doctor.class, id);
    }

    public Patient getPatient(int id) {
        isActive();
        return manager.find(Patient.class, id);
    }

    public Disease getDisease(int id) {
        isActive();
        return manager.find(Disease.class, id);
    }

    public void addPatientToDoctor(Patient patient, Doctor doctor) {
        isActive();
        doctor.add_patient(patient);
        manager.merge(doctor);
        manager.getTransaction().commit();
    }

    public void addDiseaseToPatient(Patient patient, Disease disease) {
        isActive();
        patient.add_disease(disease);
        manager.merge(patient);
        manager.getTransaction().commit();
    }

    public boolean getDoctorPatientConnections(int doctor_id, int patient_id){
        query = manager.createNativeQuery("SELECT * FROM doctor_to_patient where doctor_id =?1 and patient_id =?2");
        query.setParameter(1, doctor_id);
        query.setParameter(2, patient_id);
        List liss = query.getResultList();
        return liss.size() == 0;

    }

    public boolean getPatientDiseaseConnections(int disease_id, int patient_id){
        query = manager.createNativeQuery("SELECT * FROM patient_to_disease where disease_id =?1 and patient_id =?2");
        query.setParameter(1, disease_id);
        query.setParameter(2, patient_id);
        List liss = query.getResultList();
        return liss.size() == 0;

    }

    public void deletePatientFromDoctor(int doctor_id, int patient_id) {
        isActive();
        query = manager.createNativeQuery("DELETE FROM doctor_to_patient where doctor_id =?1 and patient_id =?2");
        query.setParameter(1, doctor_id);
        query.setParameter(2, patient_id);
        query.executeUpdate();
        manager.getTransaction().commit();
    }

    public void deleteDiseaseFromPatient(int disease_id, int patient_id) {
        isActive();
        query = manager.createNativeQuery("DELETE FROM patient_to_disease where disease_id =?1 and patient_id =?2");
        query.setParameter(1, disease_id);
        query.setParameter(2, patient_id);
        query.executeUpdate();
        manager.getTransaction().commit();
    }


}
