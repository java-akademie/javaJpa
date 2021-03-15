package ch.jmildner.jpa.uebungen2;

import ch.jmildner.utils.MyUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class TestJPA22
{
	private static EntityManagerFactory emf;

	public static void main(String[] args)
	{
		emf = Persistence.createEntityManagerFactory("H2");
		{
			test1();
		}
		emf.close();
	}

	private static void test1()
	{
		MyUtils.printUeberschrift("test1", 2);

		EntityManager em = emf.createEntityManager();
		{
			em.getTransaction().begin();
			{
				AdresseJPA22 a = new AdresseJPA22("wien");
				PersonJPA22 p = new PersonJPA22("hugo");
				p.setAdresse(a);
				a.setPerson(p);

				em.persist(a);
				em.persist(p);
			}
			em.getTransaction().commit();
		}
		em.close();

		zeigeAdressenJPA22();
		zeigePersonenJPA22();
	}

	private static void zeigePersonenJPA22()
	{
		MyUtils.printUeberschrift("PersonenJPA22", 2);

		EntityManager em = emf.createEntityManager();
		{
			Query q = em.createQuery("select p from PersonJPA22 p order by p.id");

			List<?> personen = q.getResultList();

			for (Object o : personen)
			{
				PersonJPA22 p = (PersonJPA22) o;
				p.show();
			}
		}
		em.close();
	}

	private static void zeigeAdressenJPA22()
	{
		MyUtils.printUeberschrift("AdressenJPA22", 2);

		EntityManager em = emf.createEntityManager();
		{
			Query q = em.createQuery("select p from AdresseJPA22 p order by p.id");

			List<?> personen = q.getResultList();

			for (Object o : personen)
			{
				AdresseJPA22 p = (AdresseJPA22) o;
				p.show();
			}
		}
		em.close();
	}

}
