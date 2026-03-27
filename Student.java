import java.util.Date;

public class Student {
    private String studentId;
    private String emri;
    private String mbiemri;
    private String email;
    private String fjalëkalimi;
    private String roli = "Student";
    private String gjendjaLlogarise; // Aktive, Locked, UnderReview
    private int numTentativave;
    private Date datëRegjistrimi;
    private String departamenti;

    public Student() {}

    public Student(String studentId, String emri, String mbiemri,
                   String email, String fjalëkalimi, String gjendjaLlogarise,
                   String departamenti) {
        this.studentId       = studentId;
        this.emri            = emri;
        this.mbiemri         = mbiemri;
        this.email           = email;
        this.fjalëkalimi     = fjalëkalimi;
        this.gjendjaLlogarise = gjendjaLlogarise;
        this.departamenti    = departamenti;
        this.numTentativave  = 0;
        this.datëRegjistrimi = new Date();
    }

    public boolean login(String email, String pass) {
        return this.email.equals(email) && this.fjalëkalimi.equals(pass);
    }

    public void logout()              { System.out.println("Student " + emri + " doli."); }
    public boolean verifiko2FA(String kod) { return kod != null && kod.length() == 6; }
    public void resetFjalëkalim()     { System.out.println("Reset kërkuar për " + email); }
    public void rritTentativa()       { this.numTentativave++; }
    public void resetTentativa()      { this.numTentativave = 0; }

    // ── Getters & Setters ──────────────────────────────────────────────────
    public String getStudentId()         { return studentId; }
    public void   setStudentId(String v)  { this.studentId = v; }
    public String getEmri()              { return emri; }
    public void   setEmri(String v)       { this.emri = v; }
    public String getMbiemri()           { return mbiemri; }
    public void   setMbiemri(String v)    { this.mbiemri = v; }
    public String getEmail()             { return email; }
    public void   setEmail(String v)      { this.email = v; }
    public String getFjalëkalimi()       { return fjalëkalimi; }
    public void   setFjalëkalimi(String v){ this.fjalëkalimi = v; }
    public String getRoli()              { return roli; }
    public String getGjendjaLlogarise() { return gjendjaLlogarise; }
    public void   setGjendjaLlogarise(String v){ this.gjendjaLlogarise = v; }
    public int    getNumTentativave()    { return numTentativave; }
    public void   setNumTentativave(int v){ this.numTentativave = v; }
    public Date   getDatëRegjistrimi()   { return datëRegjistrimi; }
    public String getDepartamenti()      { return departamenti; }
    public void   setDepartamenti(String v){ this.departamenti = v; }

    @Override
    public String toString() {
        return "[" + studentId + "] " + emri + " " + mbiemri + " <" + email + "> — " + gjendjaLlogarise;
    }
}
