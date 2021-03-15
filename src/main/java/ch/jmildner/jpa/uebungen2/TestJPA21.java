package ch.jmildner.jpa.uebungen2;


import ch.jmildner.utils.MyUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class TestJPA21
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
				AdresseJPA21 a = new AdresseJPA21("wien");
				PersonJPA21 p = new PersonJPA21("hugo");
				p.setAdresse(a);

				em.persist(a);
				em.persist(p);

			}
			em.getTransaction().commit();
		}
		em.close();

		zeigeAdressenJPA21();
		zeigePersonenJPA21();
	}

	private static void zeigePersonenJPA21()
	{
		MyUtils.printUeberschrift("Personen", 2);

		EntityManager em = emf.createEntityManager();
		{
			Query q = em.createQuery("select p from PersonJPA21 p order by p.id");

			List<?> personen = q.getResultList();

			for (Object o : personen)
			{
				PersonJPA21 p = (PersonJPA21) o;
				p.show();
			}
		}
		em.close();
	}

	private static void zeigeAdressenJPA21()
	{
		MyUtils.printUeberschrift("AdressenJPA21", 2);

		EntityManager em = emf.createEntityManager();
		{
			Query q = em.createQuery("select p from AdresseJPA21 p order by p.id");

			List<?> personen = q.getResultList();

			for (Object o : personen)
			{
				AdresseJPA21 p = (AdresseJPA21) o;
				p.show();
			}
		}
		em.close();
	}
}
