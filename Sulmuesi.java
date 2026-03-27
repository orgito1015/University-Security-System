import java.util.Date;

public class Sulmuesi {
    private String sulmuesId;
    private String ip;
    private String tipiSulmit;        // BruteForce, SQLInjection, Phishing
    private int    numTentativave;
    private boolean eshteIBllokuar;
    private Date   timestampSulm;
    private String vendOrigjines;
    private String userAgent;

    public Sulmuesi() {}

    public Sulmuesi(String sulmuesId, String ip, String tipiSulmit,
                    int numTentativave, boolean eshteIBllokuar,
                    String vendOrigjines, String userAgent) {
        this.sulmuesId      = sulmuesId;
        this.ip             = ip;
        this.tipiSulmit     = tipiSulmit;
        this.numTentativave = numTentativave;
        this.eshteIBllokuar = eshteIBllokuar;
        this.vendOrigjines  = vendOrigjines;
        this.userAgent      = userAgent;
        this.timestampSulm  = new Date();
    }

    public void tentoBruteForce()  { numTentativave++; System.out.println("Brute-force tentativë #" + numTentativave); }
    public void tentojSQLInj()     { System.out.println("SQL Injection tentativë nga " + ip); }
    public void tentojPhishing()   { System.out.println("Phishing tentativë nga " + ip); }
    public void blloko()           { this.eshteIBllokuar = true; }

    // ── Getters & Setters ──────────────────────────────────────────────────
    public String  getSulmuesId()           { return sulmuesId; }
    public void    setSulmuesId(String v)    { this.sulmuesId = v; }
    public String  getIp()                  { return ip; }
    public void    setIp(String v)           { this.ip = v; }
    public String  getTipiSulmit()          { return tipiSulmit; }
    public void    setTipiSulmit(String v)   { this.tipiSulmit = v; }
    public int     getNumTentativave()       { return numTentativave; }
    public void    setNumTentativave(int v)  { this.numTentativave = v; }
    public boolean isEshteIBllokuar()        { return eshteIBllokuar; }
    public void    setEshteIBllokuar(boolean v){ this.eshteIBllokuar = v; }
    public Date    getTimestampSulm()        { return timestampSulm; }
    public String  getVendOrigjines()        { return vendOrigjines; }
    public void    setVendOrigjines(String v) { this.vendOrigjines = v; }
    public String  getUserAgent()            { return userAgent; }
    public void    setUserAgent(String v)    { this.userAgent = v; }

    @Override
    public String toString() {
        return "[" + sulmuesId + "] IP:" + ip + " | " + tipiSulmit + " | " + (eshteIBllokuar ? "BLLOKUAR" : "Aktiv");
    }
}
