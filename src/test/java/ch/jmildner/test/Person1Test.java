package ch.jmildner.test;

import ch.jmildner.jpa.first.Person1;
import org.junit.*;

public class Person1Test
{

	@Test
	public void test1()
	{
		Person1 p1 = new Person1();
		Person1 p2 = new Person1("max");
		Person1 p3 = new Person1(1L,"moritz");
		System.out.println(p1);
		System.out.println(p2);
		System.out.println(p3);
	}

}
