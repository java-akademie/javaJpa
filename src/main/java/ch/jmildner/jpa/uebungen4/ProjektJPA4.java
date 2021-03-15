
package ch.jmildner.jpa.uebungen4;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
@Entity
public class ProjektJPA4
{
	@Id
	@GeneratedValue
	private Long id;
	private String bezeichnung;

	/**
	 * OWNER Seite
	 */
	@ManyToMany // (fetch = FetchType.EAGER)
	@OrderBy("name DESC, id DESC")
	private Collection<MitarbeiterJPA4> mitarbeiterListe 
 							= new ArrayList<MitarbeiterJPA4>();
	public String getBezeichnung()
	{
		return bezeichnung;
	}
	public Long getId()
	{
		return id;
	}
	public Collection<MitarbeiterJPA4> getMitarbeiterListe()
	{
		return mitarbeiterListe;
	}
	public void setBezeichnung(String bezeichnung)
	{
		this.bezeichnung = bezeichnung;
	}



 	public void setMitarbeiterListe(List<MitarbeiterJPA4> mitarbeiterListe)
	{
		this.mitarbeiterListe = mitarbeiterListe;
	}
	public void show()
	{
		System.out.println(this);

		for (Object o : this.getMitarbeiterListe())
		{
			System.out.println("    " + o);
		}
	}
	@Override
	public String toString()
	{
		return "Projekt [id=" + id + ", bezeichnung=" + bezeichnung 
 						+ ", mitarbeiter=" + mitarbeiterListe.size() + "]";
	}
}
