package ch.jmildner.jpa.uebungen1;

import ch.jmildner.utils.MyDateTimeUtils;
import ch.jmildner.utils.MyUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class TestJPA1
{
	private static EntityManagerFactory EMF = null;

	public static void main(String[] args) throws Exception
	{
		MyUtils.printUeberschrift("TestPersonJPA1", 2);

		EMF = Persistence.createEntityManagerFactory("H2");
		{
			test1();
			test2();
		}
		EMF.close();
	}

	private static void test1()
	{
		MyUtils.printUeberschrift("test1", 2);

		PersonJPA1 p1 = new PersonJPA1("urs");
		PersonJPA1 p2 = new PersonJPA1("max");
		PersonJPA1 p3 = new PersonJPA1("erich");

		EntityManager em = EMF.createEntityManager();
		{
			em.getTransaction().begin();
			{
				em.persist(p1);
				em.persist(p2);
				em.persist(p3);
			}
			em.getTransaction().commit();

			showPersonen();

			MyUtils.printUeberschrift("(clear) und update id 2 wird zu karl");
			// em.clear();
			System.out.println(p2 + " IST" + (em.contains(p2) ? "" : " NICHT")
					+ " GEMANAGED\n");

			p2.setName("karl");

			showPersonen("vor transaktionsklammer");

			em.getTransaction().begin();
			{
				// eine Besonderheit
				// =================
				// p2.setName("karl");
				// muss nicht innerhalb der Transaktionsklammer stehen
				// der EntityManager macht dennoch den update
			}
			em.getTransaction().commit();

			showPersonen("nach transkationsklammer");
		}
		em.close();
	}

	private static void test2() throws Exception
	{
		MyUtils.printUeberschrift("test2", 2);

		EntityManager em = EMF.createEntityManager();
		{
			PersonJPA1 p1 = em.find(PersonJPA1.class, 1L);
			PersonJPA1 p2 = em.find(PersonJPA1.class, 2L);
			PersonJPA1 p3 = em.find(PersonJPA1.class, 3L);

			showPersonen("vor set Attribute");

			em.getTransaction().begin();
			{
				p1.setDatum(MyDateTimeUtils.getCurrentDate());
				p1.setZeit(MyDateTimeUtils.getCurrentTime());
				p1.setZeitstempel(MyDateTimeUtils.getCurrentTimestamp());

				p2.setDatum(MyDateTimeUtils.makeDate(1988, 12, 24));
				p2.setZeit(MyDateTimeUtils.makeTime(7, 50, 30));
				p2.setZeitstempel(MyDateTimeUtils.makeTimestamp(
						1988, 12, 24,
						6, 30, 15));

				p3.setShortZahl((short) 55);
				p3.setIntZahl(1000000000);
				p3.setLongZahl(MyUtils.getRandom());
			}
			em.getTransaction().commit();

			showPersonen("nach set Attribute");

			MyUtils.printUeberschrift("(clear) und remove id 2");

			// em.clear();

			System.out.println(p2 + " IST" + (em.contains(p2) ? "" : " NICHT")
					+ " GEMANAGED\n");

			em.remove(p2);

			em.getTransaction().begin();
			{
				// auch hier steht der Remove
				// nicht innerhalb der Transaktionsklammer
				// und wird trotzdem gemacht!
			}
			em.getTransaction().commit();

			showPersonen("nach Transaktionsklammer");
		}
		em.close();
	}


	private static void showPersonen()
	{
		showPersonen("");
	}

	private static void showPersonen(final String msg)
	{
		MyUtils.printUeberschrift("Personen " + msg, 2);

		EntityManager em = EMF.createEntityManager();
		{
			Query q = em.createQuery("select p from PersonJPA1 p order by p.id");

			List<?> personen = q.getResultList();

			for (Object o : personen)
			{
				PersonJPA1 p = (PersonJPA1) o;
				p.show();
			}

			System.out.println();
		}
		em.close();
	}
}
