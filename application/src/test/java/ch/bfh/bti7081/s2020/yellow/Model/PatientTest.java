package ch.bfh.bti7081.s2020.yellow.Model;

import ch.bfh.bti7081.s2020.yellow.Model.Utils.HibernateUtil;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

public class PatientTest {


    @Before
    public void PatientTest() {
    }

    @Test
    public void DatabaseTest() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Patient patient = new Patient("first", "last", "email");
        session.save(patient);

        session.getTransaction().commit();

        Query q = session.createQuery("From ch.bfh.bti7081.s2020.yellow.Model.Patient ");

        List<Patient> resultList = q.list();
        System.out.println("num of patient:" + resultList.size());
        for (Patient next : resultList) {
            System.out.println("next patient: " + next);
        }
    }
}