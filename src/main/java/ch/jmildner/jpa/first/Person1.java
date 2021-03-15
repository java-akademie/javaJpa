package ch.jmildner.jpa.first;

import ch.jmildner.utils.MyUtils;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Person1 implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue // wenn nicht gefuellt dann AutoGenerated
	private Long id;

	private String name;

	public Person1()
	{
	}

	public Person1(Long id, String name)
	{
		this.name = name;
		this.id = id;
	}

	public Person1(String name)
	{
		this.name = name;
		id = MyUtils.getRandomLong();
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	@Override
	public String toString()
	{
		return "Person1 [id=" + getId() + ", name=" + getName() + "]";
	}

}