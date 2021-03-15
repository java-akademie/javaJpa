package ch.jmildner.jpa.uebungen1;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

@Entity
public class PersonJPA1 implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@Version
	private int version;

	private String name;
	private short shortZahl;
	private int intZahl;
	private long longZahl;

	private Date datum;
	private Time zeit;
	private Timestamp zeitstempel;

	public PersonJPA1()
	{
	}

	public PersonJPA1(String name)
	{
		this.name = name;
	}

	public Date getDatum() { return datum; }

	public void setDatum(Date datum) { this.datum = datum; }

	public Long getId() { return id; }

	public int getIntZahl() { return intZahl; }

	public void setIntZahl(int intZahl) { this.intZahl = intZahl; }

	public long getLongZahl() { return longZahl; }

	public void setLongZahl(long longZahl) { this.longZahl = longZahl;}

	public String getName() { return name; }

	public void setName(String name) { this.name = name; }

	public short getShortZahl() { return shortZahl; }

	public void setShortZahl(short shortZahl) { this.shortZahl = shortZahl; }

	public Time getZeit() { return zeit; }

	public void setZeit(Time zeit) { this.zeit = zeit; }

	public Timestamp getZeitstempel() { return zeitstempel; }

	public void setZeitstempel(Timestamp zeitstempel) {this.zeitstempel = zeitstempel;}

	public void show()
	{
		System.out.println(this);
	}

	public int getVersion()
	{
		return version;
	}

	public void setVersion(int version)
	{
		this.version = version;
	}

	@Override
	public String toString()
	{
		return String.format(
				"%5d %3d  %5d %13d %13d    %-11s %-9s %-24s %-20s",
				getId(), getVersion(), getShortZahl(), getIntZahl(), getLongZahl(), getDatum(), getZeit(),
				getZeitstempel(), getName());
	}

	public void setId(Long id)
	{
		this.id = id;
	}
}
