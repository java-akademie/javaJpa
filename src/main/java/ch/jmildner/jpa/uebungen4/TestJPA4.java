package ch.jmildner.jpa.uebungen4;

import ch.jmildner.utils.MyTestDataUtils;
import ch.jmildner.utils.MyUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class TestJPA4
{
	private static EntityManagerFactory emf;

	public static void main(String[] args)
	{
		emf = Persistence.createEntityManagerFactory("H2");
		{
			test1();
			test2();
		}
		emf.close();
	}

	private static void test2()
	{
		MyUtils.printUeberschrift("test2", 2);

		EntityManager em = emf.createEntityManager();
		{
			ProjektJPA4 project = insertProjekt(em, "go to mars");

			for (int i = 1; i <= 20; i++)
			{
				MitarbeiterJPA4 mitarbeiter
						= insertMitarbeiter(em, MyTestDataUtils.getName());
				projektZuordnung(em, project.getId(), mitarbeiter.getId());
			}
		}
		em.close();

		showProjekte();
		showMitarbeiter();
	}

	private static void test1()
	{
		MyUtils.printUeberschrift("test1", 2);

		EntityManager em = emf.createEntityManager();
		{
			ProjektJPA4 p1 = insertProjekt(em, "p1");
			ProjektJPA4 p2 = insertProjekt(em, "p2");
			ProjektJPA4 p3 = insertProjekt(em, "p3");

			MitarbeiterJPA4 m1 = insertMitarbeiter(em, "huber");
			MitarbeiterJPA4 m2 = insertMitarbeiter(em, "meier");
			MitarbeiterJPA4 m3 = insertMitarbeiter(em, "gruber");
			MitarbeiterJPA4 m4 = insertMitarbeiter(em, "gerber");
			MitarbeiterJPA4 m5 = insertMitarbeiter(em, "schuster");

			projektZuordnung(em, p1.getId(), m1.getId()); // huber/p1
			projektZuordnung(em, p1.getId(), m2.getId()); // meier/p1
			projektZuordnung(em, p1.getId(), m3.getId()); // gruber/p1
			projektZuordnung(em, p1.getId(), m5.getId()); // schuster/p1
			projektZuordnung(em, p2.getId(), m4.getId()); // gerber/p2
			projektZuordnung(em, p2.getId(), m5.getId()); // schuster/p2
			projektZuordnung(em, p3.getId(), m1.getId()); // huber/p3
			projektZuordnung(em, p3.getId(), m5.getId()); // schuster/p3
		}
		em.close();


		showProjekte();
		showMitarbeiter();
	}

	private static ProjektJPA4 insertProjekt(EntityManager em, String bez)
	{
		ProjektJPA4 pr = new ProjektJPA4();
		pr.setBezeichnung(bez);

		em.getTransaction().begin();
		{
			em.persist(pr);
		}
		em.getTransaction().commit();

		return pr;
	}

	private static MitarbeiterJPA4 insertMitarbeiter(EntityManager em, String name)
	{
		MitarbeiterJPA4 ma = new MitarbeiterJPA4();
		ma.setName(name);

		em.getTransaction().begin();
		{
			em.persist(ma);
		}
		em.getTransaction().commit();

		return ma;
	}

	private static void projektZuordnung(
			EntityManager em, Long projektId, Long mitarbeiterId)
	{
		ProjektJPA4 pr = em.find(ProjektJPA4.class, projektId);
		MitarbeiterJPA4 ma = em.find(MitarbeiterJPA4.class, mitarbeiterId);

		em.getTransaction().begin();
		{
			pr.getMitarbeiterListe().add(ma);
			ma.getProjektListe().add(pr);
		}
		em.getTransaction().commit();
	}

	public static void showMitarbeiter()
	{
		MyUtils.printUeberschrift("showMitarbeiter", 2);

		cacheEntleeren();

		EntityManager em = emf.createEntityManager();
		{
			Query q = em.createQuery("select t from MitarbeiterJPA4 t ");

			List<?> result = q.getResultList();

			for (Object o : result)
			{
				MitarbeiterJPA4 m = (MitarbeiterJPA4) o;
				m.show();
			}
		}
		em.close();
	}

	private static void showProjekte()
	{
		MyUtils.printUeberschrift("showProjekte", 2);

		cacheEntleeren();

		EntityManager em = emf.createEntityManager();
		{
			Query q = em.createQuery("select t from ProjektJPA4 t ");

			List<?> result = q.getResultList();

			for (Object o : result)
			{
				ProjektJPA4 p = (ProjektJPA4) o;
				p.show();
			}
		}
		em.close();
	}

	private static void cacheEntleeren()
	{
		emf.getCache().evictAll();
	}
}
