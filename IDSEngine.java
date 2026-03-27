import java.util.Date;

public class IDSEngine {
    private String  idsId;
    private boolean eshteAktiv;
    private int     pragAlertit;        // kerkesa per sekonde para alertit
    private int     kerkesaPerMinute;
    private int     ipBllokuaraTotale;
    private String  versioni;
    private Date    ngritjeHerën;       // kur u nis sistemi
    private String  statusDetektimit;   // Normal, Watch, Alert, Critical

    public IDSEngine() {}

    public IDSEngine(String idsId, boolean eshteAktiv, int pragAlertit,
                     int kerkesaPerMinute, String versioni, String statusDetektimit) {
        this.idsId              = idsId;
        this.eshteAktiv         = eshteAktiv;
        this.pragAlertit        = pragAlertit;
        this.kerkesaPerMinute   = kerkesaPerMinute;
        this.ipBllokuaraTotale  = 0;
        this.versioni           = versioni;
        this.statusDetektimit   = statusDetektimit;
        this.ngritjeHerën       = new Date();
    }

    public void monitoroTrafikun()  { System.out.println("IDS: Duke monitoruar trafikun..."); }
    public void bllokIPAuto(String ip){ ipBllokuaraTotale++; System.out.println("IDS: IP bllokuar automatikisht: " + ip); }
    public boolean analizoPattern(String kerkese) { return kerkese.contains("'") || kerkese.contains("--"); }
    public void gjenerojLog()       { System.out.println("IDS: Log i sigurisë gjeneruar."); }
    public void aktivizo()          { this.eshteAktiv = true; this.statusDetektimit = "Normal"; }
    public void çaktivizo()         { this.eshteAktiv = false; }

    // ── Getters & Setters ──────────────────────────────────────────────────
    public String  getIdsId()                { return idsId; }
    public void    setIdsId(String v)         { this.idsId = v; }
    public boolean isEshteAktiv()            { return eshteAktiv; }
    public void    setEshteAktiv(boolean v)   { this.eshteAktiv = v; }
    public int     getPragAlertit()          { return pragAlertit; }
    public void    setPragAlertit(int v)      { this.pragAlertit = v; }
    public int     getKerkesaPerMinute()     { return kerkesaPerMinute; }
    public void    setKerkesaPerMinute(int v) { this.kerkesaPerMinute = v; }
    public int     getIpBllokuaraTotale()    { return ipBllokuaraTotale; }
    public void    setIpBllokuaraTotale(int v){ this.ipBllokuaraTotale = v; }
    public String  getVersioni()             { return versioni; }
    public void    setVersioni(String v)      { this.versioni = v; }
    public Date    getNgritjeHerën()         { return ngritjeHerën; }
    public String  getStatusDetektimit()     { return statusDetektimit; }
    public void    setStatusDetektimit(String v){ this.statusDetektimit = v; }

    @Override
    public String toString() {
        return "[" + idsId + "] v" + versioni + " | " + (eshteAktiv ? "AKTIV" : "OFF") + " | " + statusDetektimit;
    }
}
