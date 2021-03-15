package ch.jmildner.jpa.uebungen2;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class AdresseJPA22 implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	private String ort;

	@OneToOne(mappedBy = "adresse")
	@JoinColumn(unique = true)
	private PersonJPA22 person;

	public AdresseJPA22()
	{
	}

	public AdresseJPA22(String ort)
	{
		this.ort = ort;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getOrt()
	{
		return ort;
	}

	public void setOrt(String ort)
	{
		this.ort = ort;
	}

	public void show()
	{
		System.out.println(this);
	}

	public PersonJPA22 getPerson()
	{
		return person;
	}

	public void setPerson(PersonJPA22 person)
	{
		this.person = person;
	}

	@Override
	public String toString()
	{
		return "AdresseJPA22 [id=" + id + ", ort=" + ort
				+ ", person=" + person.getId() + "]";
	}
}
