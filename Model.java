import java.util.ArrayList;

public Socke {
	private Socke partnerSocke;
	private Person träger;
	
	public Person getPerson() {return träger;}
	public Socke getPartnerSocke() {return partnerSocke;}

	public void setPerson(Person p) {
        try {
            lock.lock();
			this.träger = p;
			if(this.träger != this.partnerSocke.getPerson()) {
				this.partnerSocke.setPerson(p);
			}
		} finally {
			lock.unlock();
		}
	}
	
	public void setPartnerSocke(Socke s) {	
		try {
            lock.lock();
			this.partnerSocke = s;
			if(s.getPartnerSocke() != this) {
				s.setPartnerSocke(this);
			}			
			if(s.getPerson() != this.träger) {
				s.setPerson(this.träger);
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

	public void setLinkeSocke(Socke l) {
		try {
            lock.lock();	//lock ist Reentrant Lock
			this.linkeSocke = l;
            if(this.rechteSocke != null){
                l.setPartnerSocke(this.rechteSocke)
            }
			if(l.getPerson() != this) {
				l.setPerson(this);
			}

		} finally {
			lock.unlock();
		}
	}

	public void setRechteSocke(Socke r) {
		try {
            lock.lock();
			this.rechteSocke = r;
            if(this.linkeSocke != null){
                r.setPartnerSocke(this.linkeSocke)
            }
			if(r.getPerson() != this) {
				r.setPerson(this);
			}
		} finally {
			lock.unlock();
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
		ArrayList<Person> trägerenListe = new ArrayList<Person>();
		for(int i = 0; i < verträge.length; i++) {
			trägerenListe.add(verträge[i].getAngestellter());
		}
		return trägerenListe;
	}
	
	public void addPerson(Person p) {
		Vertrag(this, p);
	}
}

class Vertrag{
	// unter der annahme, dass Klasse vertrag wie in vorlesungsfolien aussieht, und gleiche Funktionalität erfüllt	#
	// und die Klasse threadsave ist
}
