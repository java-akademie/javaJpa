package ch.jmildner.jpa.uebungen3;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class BuchJPA31
{
	@Id
	@GeneratedValue
	private Long id;

	private String titel;

	@ManyToOne
	private VerlagJPA31 verlag;

	public BuchJPA31()
	{
	}

	public BuchJPA31(String titel)
	{
		this.titel = titel;
	}

	public Long getId()
	{
		return id;
	}

	public String getTitel()
	{
		return titel;
	}

	public void setTitel(String titel)
	{
		this.titel = titel;
	}

	public VerlagJPA31 getVerlag()
	{
		return verlag;
	}

	public void setVerlag(VerlagJPA31 verlag)
	{
		this.verlag = verlag;
	}

	public void show()
	{
		System.out.println(this);
	}

	@Override
	public String toString()
	{
		return String.format("BuchJPA31 [%3d    %-30s %-30s]", id, titel, verlag);
	}
}
