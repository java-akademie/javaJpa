package ch.jmildner.jpa.first;

import ch.jmildner.utils.MyUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class TestPerson1 {
    private static EntityManagerFactory EMF = null;

    public static void main(String[] args) {
        MyUtils.printUeberschrift("TestPerson1", 1);

        EMF = Persistence.createEntityManagerFactory("H2");
        {
            test1();
            test2();
            test3();
        }
        EMF.close();
    }

    private static void test1() {
        MyUtils.printUeberschrift("--- test1 ---", 3);

        EntityManager em = EMF.createEntityManager();
        {
            Person1 p1 = new Person1(1L, "urs");
            Person1 p2 = new Person1("max");
            Person1 p3 = new Person1("erwin");

            em.getTransaction().begin();
            {
                em.persist(p1);
                em.persist(p2);
                em.persist(p3);
            }
            em.getTransaction().commit();

            showPeople();

            MyUtils.printUeberschrift("(clear) after update id 2 to 'karl'", 1);
            // em.clear();

            System.out.println(p2 + " IS" +
                    (em.contains(p2) ? "" : " NOT") + " MANAGED");

            em.getTransaction().begin();
            {
                p2.setName("karl");
            }
            em.getTransaction().commit();

            showPeople("after update");
        }
    }


    private static void test2() {
        MyUtils.printUeberschrift("--- test2 ---", 3);

        EntityManager em = EMF.createEntityManager();

        em.getTransaction().begin();
        {
            Person1 p1 = em.find(Person1.class, 1L);
            p1.setName("fritz");
        }
        em.getTransaction().commit();

        showPeople("after Find/Update id:1 fritz");
    }

    private static void test3() {
        MyUtils.printUeberschrift("--- test3 ---", 3);

        EntityManager em = EMF.createEntityManager();

        em.getTransaction().begin();
        {
            Person1 p1 = new Person1();
            p1.setName("friedrich");
            p1.setId(111111111L);
            em.persist(p1);
            System.out.println(p1.getId());
        }
        em.getTransaction().commit();

        showPeople();
    }

    private static void showPeople() {
        showPeople("");
    }

    private static void showPeople(final String msg) {
        MyUtils.printUeberschrift("People " + msg, 1);

        EntityManager em = EMF.createEntityManager();
        {
            Query q = em.createQuery("select p from Person1 p order by p.id");

            List<?> people = q.getResultList();

            for (Object o : people) {
                System.out.println(o);
            }
        }
        em.close();
    }

}
