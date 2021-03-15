package ch.jmildner.jpa.uebungen6;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

@Entity
public class PersonJPA6 implements Serializable
{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Long id;
	@Version
	private int version;
	private String vorName;
	private String nachName;
	private short gewicht;
	private int intZahl;
	private long longZahl;
	private Date datum;
	private Time zeit;
	private Timestamp zeitstempel;

	public PersonJPA6()
	{
	}

	public PersonJPA6(String vorName, String nachName)
	{
		this.vorName = vorName;
		this.nachName = nachName;
	}

	public static String header()
	{
		return String.format(
				"%5s %4s  %5s %13s %13s    %-11s %-9s %-24s %-20s %-20s",
				"ID", "Vers", "Gew", "INT", "LONG",
				"DATE", "TIME", "TIMESTAMP", "Vorname", "Nachname");
	}

	@Override
	public String toString()
	{
		return String.format(
				"%5d %4d  %5d %13d %13d    %-11s %-9s %-24s %-20s %-20s",
				id, version, gewicht, intZahl, longZahl, datum,
				zeit, zeitstempel, vorName, nachName);
	}

	public String getVorName()
	{
		return vorName;
	}

	public void setVorName(String vorName)
	{
		this.vorName = vorName;
	}

	public String getNachName()
	{
		return nachName;
	}

	public void setNachName(String nachName)
	{
		this.nachName = nachName;
	}

	public int getVersion()
	{
		return version;
	}

	public Date getDatum()
	{
		return datum;
	}

	public void setDatum(Date datum)
	{
		this.datum = datum;
	}

	public Long getId()
	{
		return id;
	}

	public int getIntZahl()
	{
		return intZahl;
	}

	public void setIntZahl(int intZahl)
	{
		this.intZahl = intZahl;
	}

	public long getLongZahl()
	{
		return longZahl;
	}

	public void setLongZahl(long longZahl)
	{
		this.longZahl = longZahl;
	}

	public Time getZeit()
	{
		return zeit;
	}

	public void setZeit(Time zeit)
	{
		this.zeit = zeit;
	}

	public Timestamp getZeitstempel()
	{
		return zeitstempel;
	}

	public void setZeitstempel(Timestamp zeitstempel)
	{
		this.zeitstempel = zeitstempel;
	}

	public short getGewicht()
	{
		return gewicht;
	}

	public void setGewicht(short gewicht)
	{
		this.gewicht = gewicht;
	}

	public void show()
	{
		System.out.println(this);
	}

}
