import java.util.Date;

public class Pedagog {
    private String pedagogId;
    private String emri;
    private String mbiemri;
    private String email;
    private String fjalëkalimi;
    private String roli = "Pedagog";
    private String departamenti;
    private String titulliAkademik;  // Prof., Dr., Asoc.
    private String gjendjaLlogarise;
    private Date   datëRegjistrimi;

    public Pedagog() {}

    public Pedagog(String pedagogId, String emri, String mbiemri,
                   String email, String fjalëkalimi, String departamenti,
                   String titulliAkademik, String gjendjaLlogarise) {
        this.pedagogId        = pedagogId;
        this.emri             = emri;
        this.mbiemri          = mbiemri;
        this.email            = email;
        this.fjalëkalimi      = fjalëkalimi;
        this.departamenti     = departamenti;
        this.titulliAkademik  = titulliAkademik;
        this.gjendjaLlogarise = gjendjaLlogarise;
        this.datëRegjistrimi  = new Date();
    }

    public boolean login(String email, String pass) {
        return this.email.equals(email) && this.fjalëkalimi.equals(pass);
    }

    public void logout()                    { System.out.println("Pedagog " + emri + " doli."); }
    public boolean verifiko2FA(String kod)  { return kod != null && kod.length() == 6; }
    public void futNote(String nota)        { System.out.println("Nota u fut: " + nota); }
    public void ngarkoMaterial(String mat)  { System.out.println("Material ngarkuar: " + mat); }

    // ── Getters & Setters ──────────────────────────────────────────────────
    public String getPedagogId()           { return pedagogId; }
    public void   setPedagogId(String v)    { this.pedagogId = v; }
    public String getEmri()                { return emri; }
    public void   setEmri(String v)         { this.emri = v; }
    public String getMbiemri()             { return mbiemri; }
    public void   setMbiemri(String v)      { this.mbiemri = v; }
    public String getEmail()               { return email; }
    public void   setEmail(String v)        { this.email = v; }
    public String getFjalëkalimi()         { return fjalëkalimi; }
    public void   setFjalëkalimi(String v)  { this.fjalëkalimi = v; }
    public String getRoli()                { return roli; }
    public String getDepartamenti()        { return departamenti; }
    public void   setDepartamenti(String v) { this.departamenti = v; }
    public String getTitulliAkademik()     { return titulliAkademik; }
    public void   setTitulliAkademik(String v){ this.titulliAkademik = v; }
    public String getGjendjaLlogarise()    { return gjendjaLlogarise; }
    public void   setGjendjaLlogarise(String v){ this.gjendjaLlogarise = v; }
    public Date   getDatëRegjistrimi()     { return datëRegjistrimi; }

    @Override
    public String toString() {
        return "[" + pedagogId + "] " + titulliAkademik + " " + emri + " " + mbiemri + " — " + departamenti;
    }
}
