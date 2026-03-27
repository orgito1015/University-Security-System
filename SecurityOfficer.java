import java.util.Date;

public class SecurityOfficer {
    private String officerId;
    private String emri;
    private String mbiemri;
    private String email;
    private String fjalëkalimi;
    private String roli = "SecurityOfficer";
    private int    nivelAutorizimi;   // 1=Viewer, 2=Analyst, 3=Chief
    private String gjendjaLlogarise;
    private String certifikimi;       // CISSP, CEH, etj.
    private Date   datëRegjistrimi;

    public SecurityOfficer() {}

    public SecurityOfficer(String officerId, String emri, String mbiemri,
                           String email, String fjalëkalimi,
                           int nivelAutorizimi, String gjendjaLlogarise,
                           String certifikimi) {
        this.officerId        = officerId;
        this.emri             = emri;
        this.mbiemri          = mbiemri;
        this.email            = email;
        this.fjalëkalimi      = fjalëkalimi;
        this.nivelAutorizimi  = nivelAutorizimi;
        this.gjendjaLlogarise = gjendjaLlogarise;
        this.certifikimi      = certifikimi;
        this.datëRegjistrimi  = new Date();
    }

    public boolean login(String email, String pass) {
        return this.email.equals(email) && this.fjalëkalimi.equals(pass);
    }

    public void analizoIncident(String incidentId) { System.out.println("Analizoj incidentin: " + incidentId); }
    public void vendosPolitike(String politika)     { System.out.println("Politika e re: " + politika); }
    public void konfirmoAlert(String alertId)       { System.out.println("Alert konfirmuar: " + alertId); }

    // ── Getters & Setters ──────────────────────────────────────────────────
    public String getOfficerId()            { return officerId; }
    public void   setOfficerId(String v)     { this.officerId = v; }
    public String getEmri()                 { return emri; }
    public void   setEmri(String v)          { this.emri = v; }
    public String getMbiemri()              { return mbiemri; }
    public void   setMbiemri(String v)       { this.mbiemri = v; }
    public String getEmail()                { return email; }
    public void   setEmail(String v)         { this.email = v; }
    public String getFjalëkalimi()          { return fjalëkalimi; }
    public void   setFjalëkalimi(String v)   { this.fjalëkalimi = v; }
    public String getRoli()                 { return roli; }
    public int    getNivelAutorizimi()      { return nivelAutorizimi; }
    public void   setNivelAutorizimi(int v)  { this.nivelAutorizimi = v; }
    public String getGjendjaLlogarise()     { return gjendjaLlogarise; }
    public void   setGjendjaLlogarise(String v){ this.gjendjaLlogarise = v; }
    public String getCertifikimi()          { return certifikimi; }
    public void   setCertifikimi(String v)   { this.certifikimi = v; }
    public Date   getDatëRegjistrimi()      { return datëRegjistrimi; }

    public String getNivelLabel() {
        switch(nivelAutorizimi){
            case 1: return "Viewer";
            case 2: return "Analyst";
            case 3: return "Chief";
            default: return "Unknown";
        }
    }

    @Override
    public String toString() {
        return "[" + officerId + "] " + emri + " " + mbiemri + " — " + certifikimi;
    }
}
