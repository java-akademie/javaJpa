package ch.jmildner.test;

import org.junit.*;

import static org.junit.Assert.assertTrue;

public class FirstTest
{
	@BeforeClass
	public static void beforeClass()
	{
		System.out.println("Before Class");
	}

	@AfterClass
	public static void afterClass()
	{
		System.out.println("After Class");
	}

	@Before
	public void beforeTest()
	{
		System.out.println("Before Test");
	}

	@After
	public void afterTest()
	{
		System.out.println("After Test");
	}

	@Test
	public void test1()
	{
		System.out.println("    Test1");
		assertTrue(true);
	}

	@Test(expected = ArithmeticException.class)
	public void test2()
	{
		System.out.println("    Test2");
		System.out.println(5 / 0);
		assertTrue(false);
	}

}
