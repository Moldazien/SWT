import java.util.ArrayList;

public Socke {
	private Socke andereSocke;
	private Person person;
	
	public Person getPerson() {return person;}
	public Socke getAndereSocke() {return andereSocke;}

	public void setPerson(Person p) {
		try {
            lock.lock();
			this.person = p;
			if(this.person != this.andereSocke.getPerson()) {
				this.andereSocke.setPerson(p);
			}
		} finally {
			lock.unlock();
		}
	}
	
	public void setAndereSocke(Socke s) {	
		try {
            lock.lock();
			this.andereSocke = s;
			if(s.getAndereSocke() != this) {
				s.setAndereSocke(this);
			}
			
			if(s.getPerson() != this.person) {
				s.setPerson(this.person);
			}
			
		}finally {
			lock.unlock();
		}
	}	
}

class Person{
	private Socke linkeSocke;
	private Socke rechteSocke;
	
	public Socke getLinkeSocke() {return linkeSocke;}
	public Socke getRechteSocke() {return rechteSocke;}

	private void setLinkeSocke(Socke l) {
		try {
            lock.lock();	//lock ist Reentrant Lock
			this.linkeSocke = l;
			if(l.getPerson() != this) {
				l.setPerson(this);
			}
		} finally {
			lock.unlock();
		}
			
	}
	private void setRechteSocke(Socke r) {
		try {
            lock.lock();
			this.rechteSocke = r;
			if(r.getPerson() != this) {
				r.setPerson(this);
			}
		} finally {
			lock.unlock();
		}
	}
	
	public void setSocken(Socke r, Socke l) {
		setRechteSocke(r);
		setLinkeSocke(l);
		if(l.getAndereSocke() != r) {
			l.setAndereSocke(r);
		}
	}
	
	public ArrayList<Firma> getFirmen() {
		Vertrag[] verträge = Vertrag.verträgeVon(this);
		ArrayList<Firma> firmenListe = new ArrayList<Firma>();
		for(int i = 0; i < verträge.length; i++) {
			firmenListe.add(verträge[i].getArbeitgeber());
		}
		return firmenListe;
	}
	
	public void addFirma(Firma f) {
		Vertrag(f, this);
	}
}


class Firma{
	public ArrayList<Person> getAngestellte() {
		Vertrag[] verträge = Vertrag.verträgeVon(this);
		ArrayList<Person> personenListe = new ArrayList<Person>();
		for(int i = 0; i < verträge.length; i++) {
			personenListe.add(verträge[i].getAngestellter());
		}
		return personenListe;
	}
	
	public void addPerson(Person p) {
		Vertrag(this, p);
	}
	
}


//class Vertrag{
	// Annahme, dass Klasse vertrag wie in vorlesungsfolien aussieht, gleiche Funktionalität erfüllt
	// und die Klasse threadsave ist
//}
