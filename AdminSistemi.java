import java.util.Date;

public class AdminSistemi {
    private String adminId;
    private String emri;
    private String mbiemri;
    private String email;
    private String fjalëkalimi;
    private String roli = "Admin";
    private int    nivelPrivilegjeve;   // 1=ReadOnly, 2=Standard, 3=Super
    private String gjendjaLlogarise;
    private Date   datëRegjistrimi;

    public AdminSistemi() {}

    public AdminSistemi(String adminId, String emri, String mbiemri,
                        String email, String fjalëkalimi,
                        int nivelPrivilegjeve, String gjendjaLlogarise) {
        this.adminId           = adminId;
        this.emri              = emri;
        this.mbiemri           = mbiemri;
        this.email             = email;
        this.fjalëkalimi       = fjalëkalimi;
        this.nivelPrivilegjeve = nivelPrivilegjeve;
        this.gjendjaLlogarise  = gjendjaLlogarise;
        this.datëRegjistrimi   = new Date();
    }

    public boolean login(String email, String pass) {
        return this.email.equals(email) && this.fjalëkalimi.equals(pass);
    }

    public void bllokIP(String ip)              { System.out.println("IP bllokuar: " + ip); }
    public void riaktivizoLlogari(String userId) { System.out.println("Llogaria riaktivizuar: " + userId); }
    public void konfigurojFirewall()             { System.out.println("Firewall konfiguruar."); }
    public void gjenerojRaport()                 { System.out.println("Raport gjeneruar."); }

    // ── Getters & Setters ──────────────────────────────────────────────────
    public String getAdminId()              { return adminId; }
    public void   setAdminId(String v)       { this.adminId = v; }
    public String getEmri()                 { return emri; }
    public void   setEmri(String v)          { this.emri = v; }
    public String getMbiemri()              { return mbiemri; }
    public void   setMbiemri(String v)       { this.mbiemri = v; }
    public String getEmail()                { return email; }
    public void   setEmail(String v)         { this.email = v; }
    public String getFjalëkalimi()          { return fjalëkalimi; }
    public void   setFjalëkalimi(String v)   { this.fjalëkalimi = v; }
    public String getRoli()                 { return roli; }
    public int    getNivelPrivilegjeve()    { return nivelPrivilegjeve; }
    public void   setNivelPrivilegjeve(int v){ this.nivelPrivilegjeve = v; }
    public String getGjendjaLlogarise()     { return gjendjaLlogarise; }
    public void   setGjendjaLlogarise(String v){ this.gjendjaLlogarise = v; }
    public Date   getDatëRegjistrimi()      { return datëRegjistrimi; }

    public String getNivelLabel() {
        switch(nivelPrivilegjeve) {
            case 1: return "ReadOnly";
            case 2: return "Standard";
            case 3: return "Superadmin";
            default: return "Unknown";
        }
    }

    @Override
    public String toString() {
        return "[" + adminId + "] " + emri + " " + mbiemri + " — Nivel " + nivelPrivilegjeve;
    }
}
