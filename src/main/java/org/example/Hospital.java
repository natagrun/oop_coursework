package org.example;

import java.util.ArrayList;

public class Hospital {
    private ArrayList<Patient> patients;
    private ArrayList<Doctor> doctors;
    private ArrayList<Disease> diseases;

    public Hospital(ArrayList<Patient> patients, ArrayList<Doctor> doctors, ArrayList<Disease> diseases) {
        this.patients = patients;
        this.doctors = doctors;
        this.diseases = diseases;
    }

    public ArrayList<Patient> getPatients() {
        return patients;
    }

    public void setPatients(ArrayList<Patient> patients) {
        this.patients = patients;
    }

    public ArrayList<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(ArrayList<Doctor> doctors) {
        this.doctors = doctors;
    }

    public ArrayList<Disease> getDiseases() {
        return diseases;
    }

    public void setDiseases(ArrayList<Disease> diseases) {
        this.diseases = diseases;
    }

    public boolean AddPatient(Patient patient) {
        patients.add(patient);
        return true;
    }

    public boolean AddDoctor(Doctor doctor) {
        doctors.add(doctor);
        return true;
    }

    public boolean DeleteDoctor(Doctor doctor) {
        doctors.remove(doctor);
        return true;
    }

    public boolean DeletePatient(Patient patient) {
        patients.remove(patient);
        return true;
    }

    public Doctor[] SearchDbName(String Name) {
        return new Doctor[0];
    }

    public Doctor[] SearchDbSpec(String Spec) {
        return new Doctor[0];
    }

    public Patient[] SearchPbDisease(String Disease) {
        return new Patient[0];
    }

    public void CountDiseaseP() {
        System.out.println("Жесть много заболели лол");
    }
}

