package ch.jmildner.test;

import ch.jmildner.jpa.uebungen1.PersonJPA1;
import ch.jmildner.utils.MyDateTimeUtils;
import ch.jmildner.utils.MyUtils;
import org.junit.Test;

public class PersonJPA1Test
{

	@Test
	public void test1()
	{
		var p1 = new PersonJPA1("hugo");

		p1.setVersion(0);
		p1.setDatum(MyDateTimeUtils.getCurrentDate());
		p1.setZeit(MyDateTimeUtils.getCurrentTime());
		p1.setZeitstempel(MyDateTimeUtils.getCurrentTimestamp());
		p1.setShortZahl((short) 55);
		p1.setIntZahl(1000000000);
		p1.setLongZahl(MyUtils.getRandom());


		System.out.println(p1);
		System.out.println(p1.getDatum());
		System.out.println(p1.getIntZahl());
		System.out.println(p1.getLongZahl());
		System.out.println(p1.getShortZahl());
		System.out.println(p1.getVersion());
		System.out.println(p1.getZeit());
		System.out.println(p1.getZeitstempel());
		System.out.println(p1.getId());
		System.out.println(p1.getName());



		var p2 = new PersonJPA1();
		System.out.println(p2);
	}

}
