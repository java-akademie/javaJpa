
package ch.jmildner.jpa.uebungen4;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
@Entity
public class MitarbeiterJPA4
{
	@Id
	@GeneratedValue
	private Long id;
	protected String name;
	/**
	 * INVERSE Seite
	 */
	@ManyToMany(mappedBy = "mitarbeiterListe")
	@OrderBy("bezeichnung DESC")
	private List<ProjektJPA4> projektListe = new ArrayList<ProjektJPA4>();
	public Long getId()
	{
		return id;
	}
	public String getName()
	{
		return name;
	}
	public List<ProjektJPA4> getProjektListe()
	{
		return projektListe;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public void setProjektListe(List<ProjektJPA4> projektListe)
	{
		this.projektListe = projektListe;
	}


	public void show()
	{
		System.out.println(this);
		
		for (Object o : this.getProjektListe())
		{
			System.out.println("    " + o);
		}
	}
	@Override
	public String toString()
	{
		return "Mitarbeiter [id=" + id + ", name=" + name 
 						+ ", projekte=" + projektListe.size() + "]";
	}
}
