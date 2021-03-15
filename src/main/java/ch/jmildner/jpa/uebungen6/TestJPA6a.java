package ch.jmildner.jpa.uebungen6;

import ch.jmildner.utils.MyDateTimeUtils;
import ch.jmildner.utils.MyTestDataUtils;
import ch.jmildner.utils.MyUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

public class TestJPA6a
{
	private static final int MAX = 100;
	private static EntityManagerFactory emf;

	public static void main(String[] args) throws Exception
	{
		MyUtils.printUeberschrift("TestJPA6a", 2);

		emf = Persistence.createEntityManagerFactory("H2");
		{
			personenMasseninsert();
			criteriaTest1();
			showPersonenOhneOrderBy();
			showPersonenMitOrderBy();
		}
		emf.close();
	}

	private static void criteriaTest1()
	{
		MyUtils.printUeberschrift("criteriaTest1", 2);

		EntityManager em = emf.createEntityManager();
		{
			{
				System.out.println("\n\nvorName=johann");
				CriteriaBuilder cb = em.getCriteriaBuilder();
				CriteriaQuery<PersonJPA6> cq = cb.createQuery(PersonJPA6.class);
				Root<PersonJPA6> root = cq.from(PersonJPA6.class);
				Path<Object> vorName = root.get("vorName");


				Predicate p = cb.equal(vorName, "johann");
				cq.select(root).where(p);
				TypedQuery<PersonJPA6> tq = em.createQuery(cq);
				show(tq.getResultList());
			}

			{
				System.out.println("\n\nvorName=johann oder nachName=vogel");
				CriteriaBuilder cb = em.getCriteriaBuilder();
				CriteriaQuery<PersonJPA6> cq = cb.createQuery(PersonJPA6.class);
				Root<PersonJPA6> root = cq.from(PersonJPA6.class);
				Path<String> vorName = root.get("vorName");
				Path<String> nachName = root.get("nachName");
				Predicate p1 = cb.equal(vorName, "johann");
				Predicate p2 = cb.equal(nachName, "vogel");
				Predicate p3 = cb.or(p1, p2);
				cq.select(root).where(p3);
				TypedQuery<PersonJPA6> tq = em.createQuery(cq);
				show(tq.getResultList());
			}

			{
				System.out.println("\n\n(vorName=johann or nachName=vogel) "
						+ "and gewicht >= 50 and longZahl >= 50");

				CriteriaBuilder cb = em.getCriteriaBuilder();
				CriteriaQuery<PersonJPA6> cq = cb.createQuery(PersonJPA6.class);
				Root<PersonJPA6> root = cq.from(PersonJPA6.class);

				Predicate pName = cb.or(cb.equal(root.get("vorName"), "johann"),
						cb.equal(root.get("nachName"), "vogel"));
				Predicate pGewicht = cb.ge(root.get("gewicht"), 50);
				Predicate pZahl = cb.ge(root.get("longZahl"), 50);
				Predicate p = cb.and(pName, pGewicht, pZahl);

				cq.select(root).where(p);
				TypedQuery<PersonJPA6> tq = em.createQuery(cq);
				show(tq.getResultList());
			}
		}
		em.close();
	}

	private static void personenMasseninsert() throws Exception
	{
		MyUtils.printUeberschrift("personenErstellen", 2);
		EntityManager em = emf.createEntityManager();
		{
			em.getTransaction().begin();
			{
				for (int i = 1; i <= MAX; i++)
				{
					PersonJPA6 p = new PersonJPA6(MyTestDataUtils.getVorname(),
							MyTestDataUtils.getNachname());
					p.setIntZahl(MyUtils.getRandom(1, 10));
					p.setLongZahl(MyUtils.getRandom(1, 100));
					p.setDatum(MyDateTimeUtils.makeRandomDate(1960, 2017));
					p.setZeit(MyDateTimeUtils.makeRandomTime());
					p.setZeitstempel(MyDateTimeUtils.makeRandomTimestamp(2017));
					p.setGewicht((short) MyUtils.getRandom(50, 90));
					em.persist(p);
				}
			}
			em.getTransaction().commit();
		}
		em.close();
	}

	private static void showPersonenOhneOrderBy()
	{
		MyUtils.printUeberschrift("showPersonenOhneOrderBy", 2);

		EntityManager em = emf.createEntityManager();
		{
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<PersonJPA6> cq = cb.createQuery(PersonJPA6.class);
			Root<PersonJPA6> root = cq.from(PersonJPA6.class);
			cq.select(root);
			TypedQuery<PersonJPA6> tq = em.createQuery(cq);
			show(tq.getResultList());
		}
		em.close();
	}

	private static void showPersonenMitOrderBy()
	{
		MyUtils.printUeberschrift("showPersonenMitOrderBy (nachName,vorName,id(desc)", 2);

		EntityManager em = emf.createEntityManager();
		{
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<PersonJPA6> cq = cb.createQuery(PersonJPA6.class);
			Root<PersonJPA6> root = cq.from(PersonJPA6.class);
			cq.select(root);
			cq.orderBy(
					cb.asc(root.get("nachName")),
					cb.asc(root.get("vorName")),
					cb.desc(root.get("id")));

			TypedQuery<PersonJPA6> tq = em.createQuery(cq);

			for (PersonJPA6 person : tq.getResultList())
			{
				person.show();
			}
		}
		em.close();
	}

	private static void show(List<PersonJPA6> resultList)
	{
		System.out.println(PersonJPA6.header());

		for (PersonJPA6 person : resultList)
		{
			person.show();
		}
	}

}
