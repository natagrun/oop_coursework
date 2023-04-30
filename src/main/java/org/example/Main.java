package org.example;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("test_persistence");

        EntityManager em = emf.createEntityManager();

        System.out.println("Start test!");

        em.getTransaction().begin();//открываем транзакцию - гарантируем целостность данных -

        Doctor d = new Doctor("Grey", "Smot", 43, "234343", "Psychologist", 39, "Sunday, Monday", "13:00-16:00");
//        Disease dis = new Disease("Schizophrenia", 0);
//        Patient p = new Patient("Melman", "Dobl", 23, "294585765", 1);
//        Patient h = new Patient("Felicia", "Narrow", 19, "876543456", 2);

//        d.add_patient(p);
//        p.add_disease(dis);
//        h.add_disease(dis);

//        Doctor f = em.find(Doctor.class,2);
//        f.add_patient(h);
//        f.add_patient(p);

        em.persist(d);
//        em.persist(dis);
//        em.persist(p);
//        em.persist(h);

        em.getTransaction().commit();

        System.out.println("New person is " + d.getName());


    }

}