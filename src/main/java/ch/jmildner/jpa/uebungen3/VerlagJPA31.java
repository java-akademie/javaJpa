package ch.jmildner.jpa.uebungen3;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class VerlagJPA31
{
	@Id
	@GeneratedValue
	private Long id;
	private String name;

	public VerlagJPA31()
	{
	}

	public VerlagJPA31(String name)
	{
		this.name = name;
	}

	public Long getId()
	{
		return id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void show()
	{
		System.out.println(this);
	}

	@Override
	public String toString()
	{
		return String.format("VerlagJPA31 [%3d    %-30s]", id, name);
	}
}

