package ch.jmildner.jpa.uebungen2;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class PersonJPA21 implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@OneToOne
	@JoinColumn(unique = true)
	private AdresseJPA21 adresse;

	private String name;

	public PersonJPA21()
	{
	}

	public PersonJPA21(String name)
	{
		this.name = name;
	}

	public AdresseJPA21 getAdresse()
	{
		return adresse;
	}

	public void setAdresse(AdresseJPA21 adresse)
	{
		this.adresse = adresse;
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
		return "PersonJPA21 [id=" + id + ", name=" + name + ", adresse="
				+ adresse + "]";
	}
}
