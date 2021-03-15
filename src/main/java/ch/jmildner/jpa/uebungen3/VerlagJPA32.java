package ch.jmildner.jpa.uebungen3;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
public class VerlagJPA32
{
	// INVERSE Seite
	@OneToMany(mappedBy = "verlag")
	@OrderBy("titel")
	List<BuchJPA32> buecher = new ArrayList<BuchJPA32>();
	@Id
	@GeneratedValue
	private Long id;
	private String name;

	public VerlagJPA32()
	{
	}

	public VerlagJPA32(String name)
	{
		this.name = name;
	}

	public Collection<BuchJPA32> getBuecher()
	{
		return buecher;
	}

	public void setBuecher(List<BuchJPA32> buecher)
	{
		this.buecher = buecher;
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

		for (Object o : this.getBuecher())
		{
			System.out.println("    " + o);
		}
	}

	@Override
	public String toString()
	{
		return String.format("VerlagJPA32 [%3d    %-30s anzahlBuecher=%d]",
				id, name, buecher.size());
	}
}
