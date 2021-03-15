package ch.jmildner.jpa.uebungen5;

import ch.jmildner.utils.MyDateTimeUtils;
import ch.jmildner.utils.MyTestDataUtils;
import ch.jmildner.utils.MyUtils;

import javax.persistence.*;
import java.util.List;

public class TestJPA5a
{
	private static final int MAX = 100;
	private static EntityManagerFactory emf;

	public static void main(String[] args)
	{
		MyUtils.printUeberschrift("TestJPA5a", 2);

		emf = Persistence.createEntityManagerFactory("H2");
		{
			personenMasseninsert();

			dynamischeQueries();
			parametrisierteQueries();
			benannteQueries();
			nativeQueries();
			massenUpdatesUndDeletes();
			aggregatFunktionen();
			groupByHaving();

			showPersonen();
		}
		emf.close();
	}

	private static void groupByHaving()
	{
		MyUtils.printUeberschrift("groupBy/groupByHaving", 2);

		EntityManager em = emf.createEntityManager();
		{
			{
				System.out.println("\nGROUP BY");

				String sql = "select count(p) , avg(p.gewicht), p.vorName "
						+ "from PersonJPA5 p "
						+ "group by p.vorName order by p.vorName";

				Query q = em.createQuery(sql);

				List<?> erg = q.getResultList();

				System.out.print(String.format("%-15s%-15s%-15s%n",
						"count", "avg-gewicht", "vorname"));

				for (Object o : erg)
				{
					for (Object a : (Object[]) o)
					{
						System.out.print(String.format("%-15s", a));
					}
					System.out.println();
				}
			}

			{
				System.out.println("\n\nGROUP BY HAVING count>4");

				String sql = "select count(p) , avg(p.gewicht), p.vorName "
						+ "from PersonJPA5 p "
						+ "group by p.vorName having count(p) > 4 "
						+ "order by p.vorName";

				Query q = em.createQuery(sql);

				List<?> erg = q.getResultList();

				System.out.print(String.format("%-15s%-15s%-15s%n",
						"count", "avg-gewicht", "vorname"));

				for (Object o : erg)
				{
					for (Object a : (Object[]) o)
					{
						System.out.print(String.format("%-15s", a));
					}
					System.out.println();
				}
			}
		}
		em.close();
	}

	private static void aggregatFunktionen()
	{
		MyUtils.printUeberschrift("aggregatFunktionen", 2);

		EntityManager em = emf.createEntityManager();
		{
			{
				System.out.println("\nSINGLE RESULT: SUM");

				String sql = "select sum(p.gewicht) from PersonJPA5 p";

				Query q = em.createQuery(sql);

				Long summe = (Long) q.getSingleResult();

				System.out.println(summe);
			}

			{
				System.out.println
						("\n\nRESULT LIST: COUNT, SUM, AVG, MAX, MIN");
				String sql = "select count(p), sum(p.gewicht) , avg(p.gewicht) , "
						+ "max(p.gewicht) , min(p.gewicht) from PersonJPA5 p";

				Query q = em.createQuery(sql);

				Object[] o = (Object[]) q.getSingleResult();

				System.out.printf
						("count=%s, sum=%s, avg=%s, max=%s, min=%s %n",
								o[0], o[1], o[2], o[3], o[4]);
			}
		}
		em.close();
	}

	private static void massenUpdatesUndDeletes()
	{
		MyUtils.printUeberschrift("massenUpdatesUndDeletes", 2);

		EntityManager em = emf.createEntityManager();
		{
			em.getTransaction().begin();
			{
				{
					System.out.println("\n\nbruno,gerhard,peter sollen 88 kg wiegen");
					String sql = "update PersonJPA5 p set p.gewicht=88 "
							+ "where p.vorName IN ('peter','bruno','gerhard')";

					Query q = em.createQuery(sql);
					int erg = q.executeUpdate();
					System.out.println("Anzahl auf 88 kg upgedated: " + erg);
				}

				{
					System.out.println("\n\nheidi,karin,gerlinde sollen 66 kg wiegen");
					String sql = "update PersonJPA5 p set p.gewicht=66 "
							+ "where p.vorName in ('heidi','karin','gerlinde')";

					Query q = em.createQuery(sql);
					int erg = q.executeUpdate();
					System.out.println("Anzahl auf 66 kg upgedated: " + erg);
				}

				{
					System.out.println("\n\nloeschen aller vornamen die mit a,b,c,d enden");
					String sql = "delete from PersonJPA5 p "
							+ "where (p.vorName like '%a') "  // eva, maria, amma
							+ "   or (p.vorName like '%b') "  // ahab
							+ "   or (p.vorName like '%c') "  // marc, luc
							+ "   or (p.vorName like '%d') "; // gerhard
					Query q = em.createQuery(sql);
					int erg = q.executeUpdate();
					System.out.println("Anzahl geloescht: " + erg);
				}
			}
			em.getTransaction().commit();
		}
		em.close();

	}

	private static void nativeQueries()
	{
		MyUtils.printUeberschrift("nativeQueries", 2);

		EntityManager em = emf.createEntityManager();
		{

			// native sql: gross/kleinschreibung wird nicht beachtet
			String sql = "select p.ID, p.VORNAME, p.NACHNAME "
					+ "from PERSONJPA5 p where p.VORNAME='bruno'";

			Query q = em.createNativeQuery(sql);

			List<?> erg = q.getResultList();

			for (Object o : erg)
			{
				for (Object a : (Object[]) o)
				{
					System.out.print(a + " ");
				}
				System.out.println();
			}

		}
		em.close();

	}

	private static void benannteQueries()
	{
		MyUtils.printUeberschrift("benannteQueries", 2);

		EntityManager em = emf.createEntityManager();
		{
			{
				System.out.println("\n\nPersonJPA5.getCount");
				Query q = em.createNamedQuery("PersonJPA5.getCount");
				Long count = (Long) q.getSingleResult();
				System.out.println("   Count: " + count);
			}

			{
				System.out.println("\n\nPersonJPA5.findByNachVorname josef");
				TypedQuery<PersonJPA5> q = em.createNamedQuery
						("PersonJPA5.findByVorName", PersonJPA5.class);
				q.setParameter("persVorName", "josef");
				List<PersonJPA5> list = q.getResultList();
				System.out.println(PersonJPA5.header());
				for (PersonJPA5 p : list)
				{
					p.show();
				}
			}

			{
				System.out.println("\n\nPersonJPA5.findByNachName huber");
				TypedQuery<PersonJPA5> q = em.createNamedQuery
						("PersonJPA5.findByNachName", PersonJPA5.class);
				q.setParameter("persNachName", "huber");
				List<PersonJPA5> list = q.getResultList();
				System.out.println(PersonJPA5.header());
				for (PersonJPA5 p : list)
				{
					p.show();
				}
			}
		}
		em.close();

	}

	private static void parametrisierteQueries()
	{
		MyUtils.printUeberschrift("parametrisierteQueries", 2);

		EntityManager em = emf.createEntityManager();
		{
			{
				System.out.println("\n\nSINGLE RESULT id=15");
				String sql = "select p from PersonJPA5 p where p.id=:persId";
				Query q = em.createQuery(sql);
				q.setParameter("persId", 15);
				PersonJPA5 p = (PersonJPA5) q.getSingleResult();
				System.out.println(PersonJPA5.header());
				p.show();
			}

			{
				System.out.println("\n\nSINGLE RESULT id=16 (TYPED QUERY)");
				String sql = "select p from PersonJPA5 p where p.id=:persId";
				TypedQuery<PersonJPA5> q = em.createQuery(sql, PersonJPA5.class);
				q.setParameter("persId", 16);
				PersonJPA5 p = q.getSingleResult();
				System.out.println(PersonJPA5.header());
				p.show();
			}

			{
				System.out.println("\n\nRESULT LIST vorname=peter");
				String sql = "select p from PersonJPA5 p where p.vorName=:persVorName";
				Query q = em.createQuery(sql);
				q.setParameter("persVorName", "peter");
				List<?> list = q.getResultList();
				System.out.println(PersonJPA5.header());
				for (Object o : list)
				{
					PersonJPA5 p = (PersonJPA5) o;
					p.show();
				}
			}

			{
				System.out.println("\n\nRESULT LIST vorname=friedrich (TYPED QUERY)");
				String sql = "select p from PersonJPA5 p where p.vorName=:persVorName";
				TypedQuery<PersonJPA5> q = em.createQuery(sql, PersonJPA5.class);
				q.setParameter("persVorName", "friedrich");
				List<PersonJPA5> list = q.getResultList();
				System.out.println(PersonJPA5.header());
				for (PersonJPA5 o : list)
				{
					o.show();
				}
			}
		}
		em.close();
	}

	private static void dynamischeQueries()
	{
		MyUtils.printUeberschrift("dynamischeQueries", 2);

		EntityManager em = emf.createEntityManager();
		{
			{
				System.out.println("\n\nSINGLE RESULT id=15");
				String sql = "select p from PersonJPA5 p where p.id=15";
				Query q = em.createQuery(sql);
				PersonJPA5 p = (PersonJPA5) q.getSingleResult();
				System.out.println(PersonJPA5.header());
				p.show();
			}

			{
				System.out.println("\n\nSINGLE RESULT id=16 (TYPED QUERY)");
				String sql = "select p from PersonJPA5 p " + "where p.id=16";
				TypedQuery<PersonJPA5> q = em.createQuery(sql, PersonJPA5.class);
				PersonJPA5 p = q.getSingleResult();
				System.out.println(PersonJPA5.header());
				p.show();
			}

			{
				System.out.println("\n\nRESULT LIST vorname=johann");
				String sql = "select p from PersonJPA5 p where p.vorName='johann'";
				Query q = em.createQuery(sql);
				List<?> list = q.getResultList();
				System.out.println(PersonJPA5.header());
				for (Object o : list)
				{
					PersonJPA5 p = (PersonJPA5) o;
					p.show();
				}
			}

			{
				System.out.println("\n\nRESULT LIST vorname=friedrich (TYPED QUERY)");
				String sql = "select p from PersonJPA5 p where p.vorName='friedrich'";
				TypedQuery<PersonJPA5> q = em.createQuery(sql, PersonJPA5.class);
				List<PersonJPA5> list = q.getResultList();
				System.out.println(PersonJPA5.header());
				for (PersonJPA5 p : list)
				{
					p.show();
				}
			}
		}
		em.close();

	}

	private static void personenMasseninsert()
	{
		MyUtils.printUeberschrift("personenErstellen", 2);

		EntityManager em = emf.createEntityManager();
		{
			em.getTransaction().begin();
			{
				for (int i = 1; i <= MAX; i++)
				{
					PersonJPA5 p = new PersonJPA5(MyTestDataUtils.getVorname(),
							MyTestDataUtils.getNachname());
					p.setIntZahl(MyUtils.getRandom());
					p.setLongZahl(MyUtils.getRandom());
					try
					{
						p.setDatum(MyDateTimeUtils.makeRandomDate(1960, 2017));
						p.setZeit(MyDateTimeUtils.makeRandomTime());
						p.setZeitstempel(MyDateTimeUtils.makeRandomTimestamp(2017));
					} catch (Exception e)
					{
						// kann nicht passieren!
					}
					p.setGewicht((short) MyUtils.getRandom(50, 90));
					em.persist(p);
				}
			}
			em.getTransaction().commit();
		}
		em.close();

	}

	private static void showPersonen()
	{
		MyUtils.printUeberschrift("showPersonen", 2);

		EntityManager em = emf.createEntityManager();
		{
			Query q = em.createQuery("select p from PersonJPA5 p "
					+ " order by p.vorName"
			);

			List<?> result = q.getResultList();

			System.out.println(PersonJPA5.header());

			for (Object o : result)
			{
				System.out.println(o);
			}
		}
		em.close();
	}
}
