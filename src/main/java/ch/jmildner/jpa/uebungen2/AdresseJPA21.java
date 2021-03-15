package ch.jmildner.jpa.uebungen2;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class AdresseJPA21 implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	private String ort;

	public AdresseJPA21()
	{
	}

	public AdresseJPA21(String ort)
	{
		this.ort = ort;
	}

	public Long getId()
	{
		return id;
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

	@Override
	public String toString()
	{
		return "AdresseJPA21 [id=" + id + ", ort=" + ort + "]";
	}

}
