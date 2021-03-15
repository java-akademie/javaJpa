package ch.jmildner.jpa.uebungen5;

import ch.jmildner.utils.MyTestDataUtils;
import ch.jmildner.utils.MyUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class TestJPA5b
{
	private static final int MAX = 100;
	private static EntityManagerFactory emf;

	public static void main(String[] args)
	{
		MyUtils.printUeberschrift("TestJPA5b NOT MANAGED TABLE", 2);

		emf = Persistence.createEntityManagerFactory("H2");
		{
			personenMasseninsert();

			showPersonen();
			showEinePerson(MAX / 2);
			showEinePerson(MAX / 4);
		}
		emf.close();
	}

	private static void showEinePerson(final int id)
	{
		MyUtils.printUeberschrift("showEinePerson", 2);

		EntityManager em = emf.createEntityManager();
		{
			Query q = em.createNativeQuery
					("select id, name, addr from PersonJPA5nm where id=" + id);

			Object[] result = (Object[]) q.getSingleResult();

			for (Object a : result)
			{
				System.out.print(a + " ");
			}
			System.out.println();
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
				{
					String sql = "drop table PERSON5NM if exists";
					Query q = em.createNativeQuery(sql);
					q.executeUpdate();
					System.out.println("drop OK");
				}


				{
					String sql = "create table PERSON5NM " +
							"(id int primary key, name varchar(100), addr varchar(100))";
					Query q = em.createNativeQuery(sql);
					q.executeUpdate();
					System.out.println("create OK");
				}


				{
					String sql = "insert into PERSON5NM values(?,?,?)";

					Query q = em.createNativeQuery(sql);

					for (int i = 1; i <= MAX; i++)
					{
						q.setParameter(1, i);
						q.setParameter(2, MyTestDataUtils.getName());
						q.setParameter(3, MyTestDataUtils.getAdresse());

						q.executeUpdate();
					}
					System.out.println(MAX + " inserts OK");
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
			Query q = em.createNativeQuery
					("select id, name, addr from PERSON5NM");

			List<?> result = q.getResultList();

			System.out.print(String.format("%-25s%-25s%-25s%n",
					"id", "name", "addr"));

			for (Object o : result)
			{
				for (Object a : (Object[]) o)
				{
					System.out.printf("%-25s", a);
				}
				System.out.println();
			}

			System.out.println("\n\n\n");

			System.out.print(String.format("%-25s%-25s%-25s%n",
					"id", "name", "addr"));

			for (Object o : result)
			{
				Object[] arr = (Object[]) o;
				int id = (int) arr[0];
				String name = (String) arr[1];
				String addr = (String) arr[2];
				System.out.printf("%-25s%-25s%-25s%n", id, name, addr);
			}
		}
		em.close();
	}

}
