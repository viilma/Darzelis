package darzelis.model;

public class Vaikas {
	private int id;
	private String user;
	private String vardas;
	private String pavarde;
	private String ak;
	private String ugdymas;
	private String grupe;
	private String uzsiemimai;
	
	public Vaikas() {
	}

	public Vaikas(String user, String vardas, String pavarde, String ak, String ugdymas, String grupe,
			String uzsiemimai) {
		this.user = user;
		this.vardas = vardas;
		this.pavarde = pavarde;
		this.ak = ak;
		this.ugdymas = ugdymas;
		this.grupe = grupe;
		this.uzsiemimai = uzsiemimai;
	}

	public Vaikas(int id, String user, String vardas, String pavarde, String ak, String ugdymas, String grupe,
			String uzsiemimai) {
		this.id = id;
		this.user = user;
		this.vardas = vardas;
		this.pavarde = pavarde;
		this.ak = ak;
		this.ugdymas = ugdymas;
		this.grupe = grupe;
		this.uzsiemimai = uzsiemimai;
	}

	public int getId() {
		return id;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getVardas() {
		return vardas;
	}

	public void setVardas(String vardas) {
		this.vardas = vardas;
	}

	public String getPavarde() {
		return pavarde;
	}

	public void setPavarde(String pavarde) {
		this.pavarde = pavarde;
	}

	public String getAk() {
		return ak;
	}

	public void setAk(String ak) {
		this.ak = ak;
	}

	public String getUgdymas() {
		return ugdymas;
	}

	public void setUgdymas(String ugdymas) {
		this.ugdymas = ugdymas;
	}

	public String getGrupe() {
		return grupe;
	}

	public void setGrupe(String grupe) {
		this.grupe = grupe;
	}

	public String getUzsiemimai() {
		return uzsiemimai;
	}

	public void setUzsiemimai(String uzsiemimai) {
		this.uzsiemimai = uzsiemimai;
	}
	
	
	

}
