package ch.jmildner.jpa.uebungen3;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class BuchJPA32
{
	@Id
	@GeneratedValue
	private Long id;

	private String titel;

	// OWNER Seite
	@ManyToOne
	private VerlagJPA32 verlag;

	public BuchJPA32()
	{
	}

	public BuchJPA32(String titel)
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

	public VerlagJPA32 getVerlag()
	{
		return verlag;
	}

	public void setVerlag(VerlagJPA32 verlag)
	{
		this.verlag = verlag;
		this.verlag.getBuecher().add(this);
	}

	@Override
	public String toString()
	{
		return String.format("BuchJPA31 [%3d    %-30s %-30s]", id, titel, verlag);
	}

	public void show()
	{
		System.out.println(this);
	}
}
