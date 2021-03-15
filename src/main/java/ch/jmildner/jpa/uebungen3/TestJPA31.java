package ch.jmildner.jpa.uebungen3;

import ch.jmildner.utils.MyUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class TestJPA31
{
	private static EntityManagerFactory emf;

	public static void main(String[] args)
	{
		MyUtils.printUeberschrift("TestJPA31", 1);

		emf = Persistence.createEntityManagerFactory("H2");
		{
			verlageUndBuecherErstellen();

			zeigeVerlage();
			zeigeBuecher();
		}
		emf.close();
	}

	private static void verlageUndBuecherErstellen()
	{
		MyUtils.printUeberschrift("verlageUndBuecherErstellen", 2);

		EntityManager em = emf.createEntityManager();
		{
			em.getTransaction().begin();
			{
				VerlagJPA31 v1 = new VerlagJPA31("hanser verlag");
				VerlagJPA31 v2 = new VerlagJPA31("rororo");
				VerlagJPA31 v3 = new VerlagJPA31("das jugendbuch");

				em.persist(v1);
				em.persist(v2);
				em.persist(v3);

				buchErstellen(em, v1, "informatik");
				buchErstellen(em, v1, "lerne java in 7 tagen");
				buchErstellen(em, v1, "java enterprise edition");
				buchErstellen(em, v1, "c sharp");
				buchErstellen(em, v1, "internteprogrammierung");

				buchErstellen(em, v2, "und jimmy ging zum regenbogen");
				buchErstellen(em, v2, "der besuch der alten dame");
				buchErstellen(em, v2, "die buddenbrooks");
				buchErstellen(em, v2, "der medicus");

				buchErstellen(em, v3, "lederstrumpf");
				buchErstellen(em, v3, "winnetou");
				buchErstellen(em, v3, "winnie puh");
			}
			em.getTransaction().commit();
		}
		em.close();

	}

	private static void buchErstellen(EntityManager em, VerlagJPA31 verlag, String titel)
	{
		BuchJPA31 buch = new BuchJPA31(titel);
		em.persist(buch);
		buch.setVerlag(verlag);
	}

	private static void zeigeVerlage()
	{
		MyUtils.printUeberschrift("zeigeVerlage", 2);

		EntityManager em = emf.createEntityManager();
		{
			Query q = em.createQuery("select v from VerlagJPA31 v order by v.id");

			List<?> verlage = q.getResultList();

			for (Object o : verlage)
			{
				VerlagJPA31 v = (VerlagJPA31) o;
				v.show();
			}
		}
		em.close();
	}

	private static void zeigeBuecher()
	{
		MyUtils.printUeberschrift("zeigeBuecher", 2);

		EntityManager em = emf.createEntityManager();
		{
			Query q = em.createQuery("select b from BuchJPA31 b order by b.id");

			List<?> buecher = q.getResultList();

			for (Object o : buecher)
			{
				BuchJPA31 b = (BuchJPA31) o;
				b.show();
			}
		}
		em.close();
	}

}
