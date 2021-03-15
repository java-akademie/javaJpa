package ch.jmildner.jpa.uebungen3;

import ch.jmildner.utils.MyUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class TestJPA32
{
	private static EntityManagerFactory emf;

	public static void main(String[] args)
	{
		MyUtils.printUeberschrift("TestJPA32", 2);

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

		em.getTransaction().begin();
		{
			VerlagJPA32 v1 = new VerlagJPA32("hanser verlag");
			VerlagJPA32 v2 = new VerlagJPA32("rororo");
			VerlagJPA32 v3 = new VerlagJPA32("das jugendbuch");

			em.persist(v1);
			em.persist(v2);
			em.persist(v3);

			buchErstellen(em, v1, "html und css");
			buchErstellen(em, v1, "servlets und jsp");
			buchErstellen(em, v1, "java server faces");
			buchErstellen(em, v1, "php und mysql");
			buchErstellen(em, v1, "informatik");
			buchErstellen(em, v1, "lerne java in 7 tagen");
			buchErstellen(em, v1, "java enterprise edition");
			buchErstellen(em, v1, "c sharp");
			buchErstellen(em, v1, "internteprogrammierung");

			buchErstellen(em, v2, "und jimmy ging zum regenbogen");
			buchErstellen(em, v2, "der besuch der alten dame");
			buchErstellen(em, v2, "die buddenbrooks");
			buchErstellen(em, v2, "der medicus");

			buchErstellen(em, v3, "winnetou");
			buchErstellen(em, v3, "abenteuer am amazonas");
			buchErstellen(em, v3, "pippi langstrumpf");

			em.getTransaction().commit();
		}
		em.close();

	}

	private static void buchErstellen(EntityManager em, VerlagJPA32 verlag, String titel)
	{
		BuchJPA32 buch = new BuchJPA32(titel);
		em.persist(buch);
		buch.setVerlag(verlag);
	}


	private static void zeigeVerlage()
	{
		MyUtils.printUeberschrift("zeigeVerlage", 2);

		cacheEntleeren();

		EntityManager em = emf.createEntityManager();
		{
			Query q = em.createQuery("select v from VerlagJPA32 v order by v.id");
			List<?> verlage = q.getResultList();
			for (Object o : verlage)
			{
				VerlagJPA32 v = (VerlagJPA32) o;
				v.show();
			}
		}
		em.close();
	}

	private static void zeigeBuecher()
	{
		MyUtils.printUeberschrift("zeigeBuecher", 2);

		cacheEntleeren();

		EntityManager em = emf.createEntityManager();
		{
			Query q = em.createQuery("select b from BuchJPA32 b order by b.titel");
			List<?> buecher = q.getResultList();
			for (Object o : buecher)
			{
				BuchJPA32 b = (BuchJPA32) o;
				b.show();
			}
		}
		em.close();
	}

	private static void cacheEntleeren()
	{
		emf.getCache().evictAll();
	}

}
