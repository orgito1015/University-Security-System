import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class FSHNObjectManager extends JFrame {

    // ─── NGJYRAT ──────────────────────────────────────────────────────────
    static final Color BG    = new Color(15,  20,  30);
    static final Color PNL   = new Color(22,  30,  45);
    static final Color CRD   = new Color(28,  38,  58);
    static final Color CRD2  = new Color(32,  44,  66);
    static final Color BLU   = new Color(24,  95,  165);
    static final Color BLU2  = new Color(55,  138, 221);
    static final Color TEL   = new Color(15,  110, 86);
    static final Color TEL2  = new Color(29,  158, 117);
    static final Color RED2  = new Color(226, 75,  74);
    static final Color AMB2  = new Color(239, 159, 39);
    static final Color PUR2  = new Color(130, 120, 230);
    static final Color YLW2  = new Color(200, 190, 80);
    static final Color TXT   = new Color(230, 228, 218);
    static final Color TXT2  = new Color(150, 148, 140);
    static final Color TXT3  = new Color(70,  68,  62);
    static final Color BRD   = new Color(40,  55,  80);

    static final Font FT = new Font("SansSerif",  Font.BOLD,  13);
    static final Font FL = new Font("SansSerif",  Font.PLAIN, 12);
    static final Font FS = new Font("SansSerif",  Font.PLAIN, 11);
    static final Font FM = new Font("Monospaced", Font.PLAIN, 11);
    static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    // ─── LISTAT E OBJEKTEVE ───────────────────────────────────────────────
    java.util.List<Student>        studentët    = new ArrayList<>();
    java.util.List<Pedagog>        pedagogët    = new ArrayList<>();
    java.util.List<AdminSistemi>   adminët      = new ArrayList<>();
    java.util.List<SecurityOfficer>officerët    = new ArrayList<>();
    java.util.List<Sulmuesi>       sulmuesit    = new ArrayList<>();
    java.util.List<IDSEngine>      idsEngines   = new ArrayList<>();

    private JTabbedPane tabs;

    // ─────────────────────────────────────────────────────────────────────
    public FSHNObjectManager() {
        setTitle("FSHN — Menaxhimi i Objekteve të Aktorëve");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1100, 680);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BG);
        setLayout(new BorderLayout());

        add(topBar(), BorderLayout.NORTH);

        tabs = new JTabbedPane();
        tabs.setBackground(CRD);
        tabs.setForeground(TXT);
        tabs.setFont(FT);
        styleTab(tabs, "👤 Student",          BLU2,  studentTab());
        styleTab(tabs, "🎓 Pedagog",          PUR2,  pedagogTab());
        styleTab(tabs, "🔧 Admin Sistemi",    TEL2,  adminTab());
        styleTab(tabs, "🛡 Security Officer", AMB2,  officerTab());
        styleTab(tabs, "💀 Sulmuesi",         RED2,  sulmuesTab());
        styleTab(tabs, "🤖 IDS Engine",       YLW2,  idsTab());
        add(tabs, BorderLayout.CENTER);

        add(statusBar(), BorderLayout.SOUTH);

        // Popullojmë me objekte fillestare demo
        seedData();
    }

    // ─── TOP BAR ──────────────────────────────────────────────────────────
    JPanel topBar() {
        JPanel b = new JPanel(new BorderLayout());
        b.setBackground(PNL); b.setBorder(new MatteBorder(0,0,1,0,BRD));
        b.setPreferredSize(new Dimension(0,48));
        JPanel l = new JPanel(new FlowLayout(FlowLayout.LEFT,12,0));
        l.setOpaque(false);
        JLabel ic = lbl("🛡",TXT,new Font("SansSerif",Font.PLAIN,18));
        ic.setBorder(BorderFactory.createEmptyBorder(10,0,0,0)); l.add(ic);
        JPanel t = col(); t.setBorder(BorderFactory.createEmptyBorder(7,0,0,0));
        t.add(lbl("Menaxhimi i Klasave dhe Objekteve — FSHN",TXT,FT));
        t.add(lbl("Popullo, modifiko dhe shiko objekte të aktorëve UML",TXT2,FS));
        l.add(t); b.add(l, BorderLayout.WEST);
        JPanel r = new JPanel(new FlowLayout(FlowLayout.RIGHT,10,10));
        r.setOpaque(false);
        r.add(badge("6 Klasa UML", BLU2));
        b.add(r, BorderLayout.EAST);
        return b;
    }

    // ─── STATUS BAR ───────────────────────────────────────────────────────
    JPanel statusBar() {
        JPanel b = new JPanel(new FlowLayout(FlowLayout.LEFT,12,4));
        b.setBackground(PNL); b.setBorder(new MatteBorder(1,0,0,0,BRD));
        b.setPreferredSize(new Dimension(0,26));
        b.add(lbl("Objektet në memorie:",TXT2,FS));
        b.add(lbl("Student("+studentët.size()+")",BLU2,FM));
        b.add(sep()); b.add(lbl("Pedagog("+pedagogët.size()+")",PUR2,FM));
        b.add(sep()); b.add(lbl("Admin("+adminët.size()+")",TEL2,FM));
        b.add(sep()); b.add(lbl("Officer("+officerët.size()+")",AMB2,FM));
        b.add(sep()); b.add(lbl("Sulmues("+sulmuesit.size()+")",RED2,FM));
        b.add(sep()); b.add(lbl("IDS("+idsEngines.size()+")",YLW2,FM));
        return b;
    }

    void refreshStatus() {
        // rebuild status bar
        getContentPane().remove(((BorderLayout)getContentPane().getLayout()).getLayoutComponent(BorderLayout.SOUTH));
        add(statusBar(), BorderLayout.SOUTH);
        revalidate(); repaint();
    }

    void styleTab(JTabbedPane tp, String title, Color c, JPanel content) {
        tp.addTab(title, content);
    }

    // ═════════════════════════════════════════════════════════════════════
    //  TAB: STUDENT
    // ═════════════════════════════════════════════════════════════════════
    JPanel studentTab() {
        JPanel root = tabRoot();
        String[] cols = {"ID","Emri","Mbiemri","Email","Roli","Departamenti","Gjendje","Tentativa"};
        DefaultTableModel model = new DefaultTableModel(cols, 0){public boolean isCellEditable(int r,int c){return false;}};
        JTable table = styledTable(model);

        Runnable refresh = () -> {
            model.setRowCount(0);
            for(Student s : studentët)
                model.addRow(new Object[]{s.getStudentId(),s.getEmri(),s.getMbiemri(),
                    s.getEmail(),s.getRoli(),s.getDepartamenti(),
                    s.getGjendjaLlogarise(),s.getNumTentativave()});
        };
        refresh.run();

        JPanel form = buildStudentForm(studentët, model, refresh);
        JPanel detail = detailPanel(table, studentët, (idx) -> studentDetail(studentët.get(idx)));

        root.add(splitPane(form, new JScrollPane(table), detail));
        return root;
    }

    JPanel buildStudentForm(java.util.List<Student> list, DefaultTableModel model, Runnable refresh) {
        JPanel p = formCard("Krijo objekt Student");

        JTextField idF   = fld("STU-001");
        JTextField emriF = fld("Ardit");
        JTextField mbiF  = fld("Hoxha");
        JTextField emlF  = fld("a.hoxha@fshn.edu.al");
        JPasswordField pwF = new JPasswordField("pass123"); styleInp(pwF);
        String[] deps = {"Informatikë","Matematikë","Fizikë","Kimi","Biologji"};
        JComboBox<String> depCB = combo(deps);
        String[] states = {"Aktive","Locked","UnderReview","Terminated"};
        JComboBox<String> statCB = combo(states);

        addFormRow(p, "studentId :",   idF);
        addFormRow(p, "emri :",        emriF);
        addFormRow(p, "mbiemri :",     mbiF);
        addFormRow(p, "email :",       emlF);
        addFormRow(p, "fjalëkalimi :", pwF);
        addFormRow(p, "departamenti :",depCB);
        addFormRow(p, "gjendje :",     statCB);
        p.add(vg(10));

        JButton add = bigBtn("+ Krijo objekt Student", BLU, BLU2);
        add.addActionListener(e -> {
            if(emriF.getText().trim().isEmpty()||idF.getText().trim().isEmpty()){
                JOptionPane.showMessageDialog(this,"Plotëso të paktën ID dhe Emri!","Gabim",JOptionPane.WARNING_MESSAGE); return;
            }
            Student s = new Student(
                idF.getText().trim(), emriF.getText().trim(), mbiF.getText().trim(),
                emlF.getText().trim(), new String(pwF.getPassword()),
                (String)statCB.getSelectedItem(), (String)depCB.getSelectedItem()
            );
            list.add(s); refresh.run(); refreshStatus();
            JOptionPane.showMessageDialog(this,
                "Objekti Student u krijua:\n" + s.toString(),
                "Objekt i ri", JOptionPane.INFORMATION_MESSAGE);
        });
        p.add(add);
        return p;
    }

    JPanel studentDetail(Student s) {
        JPanel p = detailCard("Detajet e Objektit: Student");
        addDetail(p,"studentId",       s.getStudentId());
        addDetail(p,"emri",            s.getEmri());
        addDetail(p,"mbiemri",         s.getMbiemri());
        addDetail(p,"email",           s.getEmail());
        addDetail(p,"roli",            s.getRoli());
        addDetail(p,"departamenti",    s.getDepartamenti());
        addDetail(p,"gjendjaLlogarise",s.getGjendjaLlogarise());
        addDetail(p,"numTentativave",  String.valueOf(s.getNumTentativave()));
        addDetail(p,"datëRegjistrimi", s.getDatëRegjistrimi()==null?"—":SDF.format(s.getDatëRegjistrimi()));
        p.add(vg(8));
        p.add(methodsLabel(new String[]{"login()","logout()","verifiko2FA()","resetFjalëkalim()","rritTentativa()"}));
        return p;
    }

    // ═════════════════════════════════════════════════════════════════════
    //  TAB: PEDAGOG
    // ═════════════════════════════════════════════════════════════════════
    JPanel pedagogTab() {
        JPanel root = tabRoot();
        String[] cols = {"ID","Titulli","Emri","Mbiemri","Email","Departamenti","Gjendje"};
        DefaultTableModel model = new DefaultTableModel(cols, 0){public boolean isCellEditable(int r,int c){return false;}};
        JTable table = styledTable(model);

        Runnable refresh = () -> {
            model.setRowCount(0);
            for(Pedagog p : pedagogët)
                model.addRow(new Object[]{p.getPedagogId(),p.getTitulliAkademik(),p.getEmri(),
                    p.getMbiemri(),p.getEmail(),p.getDepartamenti(),p.getGjendjaLlogarise()});
        };
        refresh.run();

        JPanel form = buildPedagogForm(pedagogët, model, refresh);
        JPanel detail = detailPanel(table, pedagogët, (idx) -> pedagogDetail(pedagogët.get(idx)));
        root.add(splitPane(form, new JScrollPane(table), detail));
        return root;
    }

    JPanel buildPedagogForm(java.util.List<Pedagog> list, DefaultTableModel model, Runnable refresh) {
        JPanel p = formCard("Krijo objekt Pedagog");
        JTextField idF    = fld("PED-001");
        JTextField emriF  = fld("Petrit");
        JTextField mbiF   = fld("Marku");
        JTextField emlF   = fld("p.marku@fshn.edu.al");
        JPasswordField pwF = new JPasswordField("pass456"); styleInp(pwF);
        String[] tit = {"Prof. Dr.","Dr.","Asoc. Prof.","Lekt."};
        JComboBox<String> titCB = combo(tit);
        String[] deps = {"Informatikë","Matematikë","Fizikë","Kimi","Biologji"};
        JComboBox<String> depCB = combo(deps);
        String[] states = {"Aktive","Locked","UnderReview"};
        JComboBox<String> statCB = combo(states);

        addFormRow(p,"pedagogId :",      idF);
        addFormRow(p,"emri :",           emriF);
        addFormRow(p,"mbiemri :",        mbiF);
        addFormRow(p,"email :",          emlF);
        addFormRow(p,"fjalëkalimi :",    pwF);
        addFormRow(p,"titulliAkademik :",titCB);
        addFormRow(p,"departamenti :",   depCB);
        addFormRow(p,"gjendje :",        statCB);
        p.add(vg(10));

        JButton add = bigBtn("+ Krijo objekt Pedagog", new Color(60,50,150), PUR2);
        add.addActionListener(e -> {
            if(emriF.getText().trim().isEmpty()){
                JOptionPane.showMessageDialog(this,"Plotëso Emrin!","Gabim",JOptionPane.WARNING_MESSAGE); return;
            }
            Pedagog ped = new Pedagog(
                idF.getText().trim(), emriF.getText().trim(), mbiF.getText().trim(),
                emlF.getText().trim(), new String(pwF.getPassword()),
                (String)depCB.getSelectedItem(), (String)titCB.getSelectedItem(),
                (String)statCB.getSelectedItem()
            );
            list.add(ped); refresh.run(); refreshStatus();
            JOptionPane.showMessageDialog(this,"Objekti Pedagog u krijua:\n"+ped.toString(),"Objekt i ri",JOptionPane.INFORMATION_MESSAGE);
        });
        p.add(add);
        return p;
    }

    JPanel pedagogDetail(Pedagog pg) {
        JPanel p = detailCard("Detajet e Objektit: Pedagog");
        addDetail(p,"pedagogId",      pg.getPedagogId());
        addDetail(p,"emri",           pg.getEmri());
        addDetail(p,"mbiemri",        pg.getMbiemri());
        addDetail(p,"email",          pg.getEmail());
        addDetail(p,"titulliAkademik",pg.getTitulliAkademik());
        addDetail(p,"departamenti",   pg.getDepartamenti());
        addDetail(p,"roli",           pg.getRoli());
        addDetail(p,"gjendjaLlogarise",pg.getGjendjaLlogarise());
        addDetail(p,"datëRegjistrimi",pg.getDatëRegjistrimi()==null?"—":SDF.format(pg.getDatëRegjistrimi()));
        p.add(vg(8));
        p.add(methodsLabel(new String[]{"login()","logout()","verifiko2FA()","futNote()","ngarkoMaterial()"}));
        return p;
    }

    // ═════════════════════════════════════════════════════════════════════
    //  TAB: ADMIN SISTEMI
    // ═════════════════════════════════════════════════════════════════════
    JPanel adminTab() {
        JPanel root = tabRoot();
        String[] cols = {"ID","Emri","Mbiemri","Email","Roli","Nivel Privilegji","Gjendje"};
        DefaultTableModel model = new DefaultTableModel(cols, 0){public boolean isCellEditable(int r,int c){return false;}};
        JTable table = styledTable(model);

        Runnable refresh = () -> {
            model.setRowCount(0);
            for(AdminSistemi a : adminët)
                model.addRow(new Object[]{a.getAdminId(),a.getEmri(),a.getMbiemri(),
                    a.getEmail(),a.getRoli(),a.getNivelLabel(),a.getGjendjaLlogarise()});
        };
        refresh.run();

        JPanel form = buildAdminForm(adminët, model, refresh);
        JPanel detail = detailPanel(table, adminët, (idx) -> adminDetail(adminët.get(idx)));
        root.add(splitPane(form, new JScrollPane(table), detail));
        return root;
    }

    JPanel buildAdminForm(java.util.List<AdminSistemi> list, DefaultTableModel model, Runnable refresh) {
        JPanel p = formCard("Krijo objekt AdminSistemi");
        JTextField idF   = fld("ADM-001");
        JTextField emriF = fld("Blerim");
        JTextField mbiF  = fld("Gashi");
        JTextField emlF  = fld("admin@fshn.edu.al");
        JPasswordField pwF = new JPasswordField("adminpass"); styleInp(pwF);
        String[] nivelet = {"1 — ReadOnly","2 — Standard","3 — Superadmin"};
        JComboBox<String> nivCB = combo(nivelet);
        String[] states = {"Aktive","Locked"};
        JComboBox<String> statCB = combo(states);

        addFormRow(p,"adminId :",           idF);
        addFormRow(p,"emri :",              emriF);
        addFormRow(p,"mbiemri :",           mbiF);
        addFormRow(p,"email :",             emlF);
        addFormRow(p,"fjalëkalimi :",       pwF);
        addFormRow(p,"nivelPrivilegjeve :", nivCB);
        addFormRow(p,"gjendje :",           statCB);
        p.add(vg(10));

        JButton add = bigBtn("+ Krijo objekt Admin", TEL, TEL2);
        add.addActionListener(e -> {
            if(emriF.getText().trim().isEmpty()){
                JOptionPane.showMessageDialog(this,"Plotëso Emrin!","Gabim",JOptionPane.WARNING_MESSAGE); return;
            }
            int nivel = nivCB.getSelectedIndex()+1;
            AdminSistemi a = new AdminSistemi(
                idF.getText().trim(), emriF.getText().trim(), mbiF.getText().trim(),
                emlF.getText().trim(), new String(pwF.getPassword()),
                nivel, (String)statCB.getSelectedItem()
            );
            list.add(a); refresh.run(); refreshStatus();
            JOptionPane.showMessageDialog(this,"Objekti AdminSistemi u krijua:\n"+a.toString(),"Objekt i ri",JOptionPane.INFORMATION_MESSAGE);
        });
        p.add(add);
        return p;
    }

    JPanel adminDetail(AdminSistemi a) {
        JPanel p = detailCard("Detajet e Objektit: AdminSistemi");
        addDetail(p,"adminId",           a.getAdminId());
        addDetail(p,"emri",              a.getEmri());
        addDetail(p,"mbiemri",           a.getMbiemri());
        addDetail(p,"email",             a.getEmail());
        addDetail(p,"roli",              a.getRoli());
        addDetail(p,"nivelPrivilegjeve", a.getNivelPrivilegjeve()+" — "+a.getNivelLabel());
        addDetail(p,"gjendjaLlogarise",  a.getGjendjaLlogarise());
        addDetail(p,"datëRegjistrimi",   a.getDatëRegjistrimi()==null?"—":SDF.format(a.getDatëRegjistrimi()));
        p.add(vg(8));
        p.add(methodsLabel(new String[]{"menaxhoLlogarite()","bllokIP()","riaktivizoLlogari()","konfigurojFirewall()","gjenerojRaport()"}));
        return p;
    }

    // ═════════════════════════════════════════════════════════════════════
    //  TAB: SECURITY OFFICER
    // ═════════════════════════════════════════════════════════════════════
    JPanel officerTab() {
        JPanel root = tabRoot();
        String[] cols = {"ID","Emri","Mbiemri","Email","Nivel","Certifikim","Gjendje"};
        DefaultTableModel model = new DefaultTableModel(cols, 0){public boolean isCellEditable(int r,int c){return false;}};
        JTable table = styledTable(model);

        Runnable refresh = () -> {
            model.setRowCount(0);
            for(SecurityOfficer o : officerët)
                model.addRow(new Object[]{o.getOfficerId(),o.getEmri(),o.getMbiemri(),
                    o.getEmail(),o.getNivelLabel(),o.getCertifikimi(),o.getGjendjaLlogarise()});
        };
        refresh.run();

        JPanel form = buildOfficerForm(officerët, model, refresh);
        JPanel detail = detailPanel(table, officerët, (idx) -> officerDetail(officerët.get(idx)));
        root.add(splitPane(form, new JScrollPane(table), detail));
        return root;
    }

    JPanel buildOfficerForm(java.util.List<SecurityOfficer> list, DefaultTableModel model, Runnable refresh) {
        JPanel p = formCard("Krijo objekt SecurityOfficer");
        JTextField idF   = fld("SEC-001");
        JTextField emriF = fld("Arjola");
        JTextField mbiF  = fld("Dema");
        JTextField emlF  = fld("secoff@fshn.edu.al");
        JPasswordField pwF = new JPasswordField("secpass"); styleInp(pwF);
        String[] nivelet = {"1 — Viewer","2 — Analyst","3 — Chief"};
        JComboBox<String> nivCB = combo(nivelet);
        String[] certs = {"CISSP","CEH","CISM","CompTIA Security+","ISO 27001"};
        JComboBox<String> certCB = combo(certs);
        String[] states = {"Aktive","Locked"};
        JComboBox<String> statCB = combo(states);

        addFormRow(p,"officerId :",      idF);
        addFormRow(p,"emri :",           emriF);
        addFormRow(p,"mbiemri :",        mbiF);
        addFormRow(p,"email :",          emlF);
        addFormRow(p,"fjalëkalimi :",    pwF);
        addFormRow(p,"nivelAutorizimi :",nivCB);
        addFormRow(p,"certifikimi :",    certCB);
        addFormRow(p,"gjendje :",        statCB);
        p.add(vg(10));

        JButton add = bigBtn("+ Krijo objekt SecurityOfficer", new Color(100,70,5), AMB2);
        add.addActionListener(e -> {
            if(emriF.getText().trim().isEmpty()){
                JOptionPane.showMessageDialog(this,"Plotëso Emrin!","Gabim",JOptionPane.WARNING_MESSAGE); return;
            }
            SecurityOfficer o = new SecurityOfficer(
                idF.getText().trim(), emriF.getText().trim(), mbiF.getText().trim(),
                emlF.getText().trim(), new String(pwF.getPassword()),
                nivCB.getSelectedIndex()+1, (String)statCB.getSelectedItem(),
                (String)certCB.getSelectedItem()
            );
            list.add(o); refresh.run(); refreshStatus();
            JOptionPane.showMessageDialog(this,"Objekti SecurityOfficer u krijua:\n"+o.toString(),"Objekt i ri",JOptionPane.INFORMATION_MESSAGE);
        });
        p.add(add);
        return p;
    }

    JPanel officerDetail(SecurityOfficer o) {
        JPanel p = detailCard("Detajet e Objektit: SecurityOfficer");
        addDetail(p,"officerId",      o.getOfficerId());
        addDetail(p,"emri",           o.getEmri());
        addDetail(p,"mbiemri",        o.getMbiemri());
        addDetail(p,"email",          o.getEmail());
        addDetail(p,"roli",           o.getRoli());
        addDetail(p,"nivelAutorizimi",o.getNivelAutorizimi()+" — "+o.getNivelLabel());
        addDetail(p,"certifikimi",    o.getCertifikimi());
        addDetail(p,"gjendjaLlogarise",o.getGjendjaLlogarise());
        addDetail(p,"datëRegjistrimi",o.getDatëRegjistrimi()==null?"—":SDF.format(o.getDatëRegjistrimi()));
        p.add(vg(8));
        p.add(methodsLabel(new String[]{"login()","analizoIncident()","vendosPolitike()","konfirmoAlert()"}));
        return p;
    }

    // ═════════════════════════════════════════════════════════════════════
    //  TAB: SULMUESI
    // ═════════════════════════════════════════════════════════════════════
    JPanel sulmuesTab() {
        JPanel root = tabRoot();
        String[] cols = {"ID","IP","Tipi Sulmit","Tentativa","Vendi","Bllokuar","Timestamp"};
        DefaultTableModel model = new DefaultTableModel(cols, 0){public boolean isCellEditable(int r,int c){return false;}};
        JTable table = styledTable(model);

        Runnable refresh = () -> {
            model.setRowCount(0);
            for(Sulmuesi s : sulmuesit)
                model.addRow(new Object[]{s.getSulmuesId(),s.getIp(),s.getTipiSulmit(),
                    s.getNumTentativave(),s.getVendOrigjines(),
                    s.isEshteIBllokuar()?"✖ Po":"✔ Jo",
                    s.getTimestampSulm()==null?"—":SDF.format(s.getTimestampSulm())});
        };
        refresh.run();

        JPanel form = buildSulmuesForm(sulmuesit, model, refresh);
        JPanel detail = detailPanel(table, sulmuesit, (idx)->sulmuesDetail(sulmuesit.get(idx)));
        root.add(splitPane(form, new JScrollPane(table), detail));
        return root;
    }

    JPanel buildSulmuesForm(java.util.List<Sulmuesi> list, DefaultTableModel model, Runnable refresh) {
        JPanel p = formCard("Regjistro objekt Sulmuesi");
        JTextField idF    = fld("ATK-001");
        JTextField ipF    = fld("85.204.33.12");
        String[] tipi = {"BruteForce","SQLInjection","Phishing","DDoS","XSS","Spoofing"};
        JComboBox<String> tipCB = combo(tipi);
        JSpinner tentS = new JSpinner(new SpinnerNumberModel(1,1,1000,1));
        tentS.setBackground(CRD2); tentS.setForeground(TXT); tentS.setFont(FL);
        JTextField vendF  = fld("Russia");
        JTextField uaF    = fld("curl/7.68.0");
        JCheckBox  bllCB  = new JCheckBox("eshteIBllokuar"); bllCB.setOpaque(false); bllCB.setForeground(TXT2); bllCB.setFont(FL);

        addFormRow(p,"sulmuesId :",      idF);
        addFormRow(p,"ip :",             ipF);
        addFormRow(p,"tipiSulmit :",     tipCB);
        addFormRow(p,"numTentativave :", tentS);
        addFormRow(p,"vendOrigjines :",  vendF);
        addFormRow(p,"userAgent :",      uaF);
        p.add(vg(4)); p.add(bllCB); p.add(vg(8));

        JButton add = bigBtn("+ Regjistro Sulmuesi", new Color(100,20,20), RED2);
        add.addActionListener(e -> {
            if(ipF.getText().trim().isEmpty()){
                JOptionPane.showMessageDialog(this,"Plotëso IP-në!","Gabim",JOptionPane.WARNING_MESSAGE); return;
            }
            Sulmuesi s = new Sulmuesi(
                idF.getText().trim(), ipF.getText().trim(),
                (String)tipCB.getSelectedItem(),
                (Integer)tentS.getValue(), bllCB.isSelected(),
                vendF.getText().trim(), uaF.getText().trim()
            );
            list.add(s); refresh.run(); refreshStatus();
            JOptionPane.showMessageDialog(this,"Objekti Sulmuesi u regjistrua:\n"+s.toString(),"Objekt i ri",JOptionPane.WARNING_MESSAGE);
        });
        p.add(add);
        return p;
    }

    JPanel sulmuesDetail(Sulmuesi s) {
        JPanel p = detailCard("Detajet e Objektit: Sulmuesi");
        addDetail(p,"sulmuesId",     s.getSulmuesId());
        addDetail(p,"ip",            s.getIp());
        addDetail(p,"tipiSulmit",    s.getTipiSulmit());
        addDetail(p,"numTentativave",String.valueOf(s.getNumTentativave()));
        addDetail(p,"vendOrigjines", s.getVendOrigjines());
        addDetail(p,"userAgent",     s.getUserAgent());
        addDetail(p,"eshteIBllokuar",s.isEshteIBllokuar()?"✖ Po":"✔ Jo");
        addDetail(p,"timestampSulm", s.getTimestampSulm()==null?"—":SDF.format(s.getTimestampSulm()));
        p.add(vg(8));
        p.add(methodsLabel(new String[]{"tentoBruteForce()","tentojSQLInj()","tentojPhishing()","blloko()"}));
        return p;
    }

    // ═════════════════════════════════════════════════════════════════════
    //  TAB: IDS ENGINE
    // ═════════════════════════════════════════════════════════════════════
    JPanel idsTab() {
        JPanel root = tabRoot();
        String[] cols = {"ID","Versioni","Aktiv","Prag Alert","Kerkesa/min","IP Bllok.","Status"};
        DefaultTableModel model = new DefaultTableModel(cols, 0){public boolean isCellEditable(int r,int c){return false;}};
        JTable table = styledTable(model);

        Runnable refresh = () -> {
            model.setRowCount(0);
            for(IDSEngine ids : idsEngines)
                model.addRow(new Object[]{ids.getIdsId(),ids.getVersioni(),
                    ids.isEshteAktiv()?"● Aktiv":"○ Off",
                    ids.getPragAlertit(),ids.getKerkesaPerMinute(),
                    ids.getIpBllokuaraTotale(),ids.getStatusDetektimit()});
        };
        refresh.run();

        JPanel form = buildIDSForm(idsEngines, model, refresh);
        JPanel detail = detailPanel(table, idsEngines, (idx)->idsDetail(idsEngines.get(idx)));
        root.add(splitPane(form, new JScrollPane(table), detail));
        return root;
    }

    JPanel buildIDSForm(java.util.List<IDSEngine> list, DefaultTableModel model, Runnable refresh) {
        JPanel p = formCard("Krijo objekt IDSEngine");
        JTextField idF  = fld("IDS-001");
        JTextField verF = fld("3.2.1");
        JSpinner pragS  = new JSpinner(new SpinnerNumberModel(5,1,100,1));
        pragS.setBackground(CRD2); pragS.setForeground(TXT); pragS.setFont(FL);
        JSpinner kpS    = new JSpinner(new SpinnerNumberModel(500,1,10000,50));
        kpS.setBackground(CRD2); kpS.setForeground(TXT); kpS.setFont(FL);
        String[] statuses = {"Normal","Watch","Alert","Critical"};
        JComboBox<String> statCB = combo(statuses);
        JCheckBox aktivCB = new JCheckBox("eshteAktiv",true); aktivCB.setOpaque(false); aktivCB.setForeground(TXT2); aktivCB.setFont(FL);

        addFormRow(p,"idsId :",           idF);
        addFormRow(p,"versioni :",        verF);
        addFormRow(p,"pragAlertit :",     pragS);
        addFormRow(p,"kerkesaPerMinute :",kpS);
        addFormRow(p,"statusDetektimit :",statCB);
        p.add(vg(4)); p.add(aktivCB); p.add(vg(8));

        JButton add = bigBtn("+ Krijo objekt IDSEngine", new Color(60,60,10), YLW2);
        add.addActionListener(e -> {
            if(idF.getText().trim().isEmpty()){
                JOptionPane.showMessageDialog(this,"Plotëso ID!","Gabim",JOptionPane.WARNING_MESSAGE); return;
            }
            IDSEngine ids = new IDSEngine(
                idF.getText().trim(), aktivCB.isSelected(),
                (Integer)pragS.getValue(), (Integer)kpS.getValue(),
                verF.getText().trim(), (String)statCB.getSelectedItem()
            );
            list.add(ids); refresh.run(); refreshStatus();
            JOptionPane.showMessageDialog(this,"Objekti IDSEngine u krijua:\n"+ids.toString(),"Objekt i ri",JOptionPane.INFORMATION_MESSAGE);
        });
        p.add(add);
        return p;
    }

    JPanel idsDetail(IDSEngine ids) {
        JPanel p = detailCard("Detajet e Objektit: IDSEngine");
        addDetail(p,"idsId",            ids.getIdsId());
        addDetail(p,"versioni",         ids.getVersioni());
        addDetail(p,"eshteAktiv",       ids.isEshteAktiv()?"● Po":"○ Jo");
        addDetail(p,"pragAlertit",      ids.getPragAlertit()+" kerk/sek");
        addDetail(p,"kerkesaPerMinute", String.valueOf(ids.getKerkesaPerMinute()));
        addDetail(p,"ipBllokuaraTotale",String.valueOf(ids.getIpBllokuaraTotale()));
        addDetail(p,"statusDetektimit", ids.getStatusDetektimit());
        addDetail(p,"ngritjeHerën",     ids.getNgritjeHerën()==null?"—":SDF.format(ids.getNgritjeHerën()));
        p.add(vg(8));
        p.add(methodsLabel(new String[]{"monitoroTrafikun()","detektoAnomali()","bllokIPAuto()","dergojAlert()","gjenerojLog()","analizoPattern()"}));
        return p;
    }

    // ═════════════════════════════════════════════════════════════════════
    //  SEED DATA — objekte demo fillestare
    // ═════════════════════════════════════════════════════════════════════
    void seedData() {
        studentët.add(new Student("STU-001","Ardit","Hoxha","a.hoxha@fshn.edu.al","pass123","Aktive","Informatikë"));
        studentët.add(new Student("STU-002","Besa","Koçi","b.koci@fshn.edu.al","pass456","Aktive","Matematikë"));
        studentët.add(new Student("STU-003","Cen","Duka","c.duka@fshn.edu.al","pass789","Locked","Fizikë"));

        pedagogët.add(new Pedagog("PED-001","Petrit","Marku","p.marku@fshn.edu.al","ped123","Informatikë","Prof. Dr.","Aktive"));
        pedagogët.add(new Pedagog("PED-002","Elona","Sino","e.sino@fshn.edu.al","ped456","Matematikë","Dr.","Aktive"));

        adminët.add(new AdminSistemi("ADM-001","Blerim","Gashi","admin@fshn.edu.al","adminpass",3,"Aktive"));
        adminët.add(new AdminSistemi("ADM-002","Flutura","Lleshi","admin2@fshn.edu.al","admin2pw",2,"Aktive"));

        officerët.add(new SecurityOfficer("SEC-001","Arjola","Dema","secoff@fshn.edu.al","secpass",3,"Aktive","CISSP"));

        sulmuesit.add(new Sulmuesi("ATK-001","85.204.33.12","BruteForce",8,true,"Russia","python-requests/2.28"));
        sulmuesit.add(new Sulmuesi("ATK-002","85.100.22.3","SQLInjection",3,true,"China","curl/7.68.0"));
        sulmuesit.add(new Sulmuesi("ATK-003","172.16.0.8","Phishing",1,false,"Unknown","Mozilla/5.0"));

        idsEngines.add(new IDSEngine("IDS-001",true,5,847,"3.2.1","Normal"));
    }

    // ─── HELPERS UI ───────────────────────────────────────────────────────

    interface DetailFactory { JPanel make(int idx); }

    JComponent splitPane(JPanel form, JScrollPane table, JPanel detailPlaceholder) {
        // Left: form
        JScrollPane formScroll = new JScrollPane(form);
        formScroll.setBorder(BorderFactory.createEmptyBorder());
        formScroll.getViewport().setBackground(BG);
        formScroll.setBackground(BG);
        formScroll.setPreferredSize(new Dimension(240,0));

        // Center: table + bottom detail
        JPanel center = new JPanel(new BorderLayout(0,8));
        center.setBackground(BG); center.setBorder(BorderFactory.createEmptyBorder(12,8,12,12));
        table.getViewport().setBackground(CRD); table.setBorder(BorderFactory.createLineBorder(BRD));
        center.add(table, BorderLayout.CENTER);

        JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, formScroll, center);
        sp.setDividerLocation(240);
        sp.setDividerSize(4);
        sp.setBorder(BorderFactory.createEmptyBorder());
        sp.setBackground(BG);
        return sp;
    }

    JPanel detailPanel(JTable table, java.util.List<?> list, DetailFactory factory) {
        // We attach a listener to the table so clicking a row shows details
        // The "detail" panel lives below the table in a south JSplitPane
        // We return a placeholder; actual wiring done by caller
        return new JPanel();
    }

    // Overloaded splitPane that includes detail panel wired to table selection
    JComponent splitPane(JPanel form, JScrollPane tableScroll, JTable table, java.util.List<?> list, DetailFactory factory) {
        JScrollPane formScroll = new JScrollPane(form);
        formScroll.setBorder(BorderFactory.createEmptyBorder());
        formScroll.getViewport().setBackground(BG); formScroll.setBackground(BG);
        formScroll.setPreferredSize(new Dimension(240,0));

        JPanel detailHolder = new JPanel(new BorderLayout());
        detailHolder.setBackground(CRD);
        detailHolder.add(lbl("  Kliko një rresht në tabelë për të parë detajet e objektit...", TXT2, FS), BorderLayout.CENTER);

        table.getSelectionModel().addListSelectionListener(e -> {
            if(!e.getValueIsAdjusting() && table.getSelectedRow() >= 0 && table.getSelectedRow() < list.size()) {
                detailHolder.removeAll();
                JScrollPane ds = new JScrollPane(factory.make(table.getSelectedRow()));
                ds.setBorder(BorderFactory.createEmptyBorder());
                ds.getViewport().setBackground(CRD);
                detailHolder.add(ds, BorderLayout.CENTER);
                detailHolder.revalidate(); detailHolder.repaint();
            }
        });

        tableScroll.getViewport().setBackground(CRD);
        tableScroll.setBorder(BorderFactory.createLineBorder(BRD));

        JSplitPane tableDetail = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tableScroll, detailHolder);
        tableDetail.setDividerLocation(200); tableDetail.setDividerSize(4);
        tableDetail.setBorder(BorderFactory.createEmptyBorder()); tableDetail.setBackground(BG);

        JPanel center = new JPanel(new BorderLayout());
        center.setBackground(BG); center.setBorder(BorderFactory.createEmptyBorder(12,8,12,12));
        center.add(tableDetail, BorderLayout.CENTER);

        JSplitPane main = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, formScroll, center);
        main.setDividerLocation(245); main.setDividerSize(4);
        main.setBorder(BorderFactory.createEmptyBorder()); main.setBackground(BG);
        return main;
    }

    // Re-wire tabs to use full split pane with detail
    JPanel studentTab2() {
        JPanel root = tabRoot();
        String[] cols = {"ID","Emri","Mbiemri","Email","Roli","Departamenti","Gjendje","Tentativa"};
        DefaultTableModel model = new DefaultTableModel(cols, 0){public boolean isCellEditable(int r,int c){return false;}};
        JTable table = styledTable(model);
        JScrollPane tableScroll = new JScrollPane(table);

        Runnable refresh = () -> {
            model.setRowCount(0);
            for(Student s : studentët)
                model.addRow(new Object[]{s.getStudentId(),s.getEmri(),s.getMbiemri(),
                    s.getEmail(),s.getRoli(),s.getDepartamenti(),s.getGjendjaLlogarise(),s.getNumTentativave()});
        };
        refresh.run();

        JPanel form = buildStudentForm(studentët, model, refresh);
        root.add(splitPane(form, tableScroll, table, studentët, idx -> studentDetail(studentët.get(idx))));
        return root;
    }

    JPanel tabRoot() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(BG); p.setBorder(BorderFactory.createEmptyBorder());
        return p;
    }

    JPanel formCard(String title) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(CRD);
        p.setBorder(BorderFactory.createCompoundBorder(
            new MatteBorder(0,0,0,1,BRD),
            BorderFactory.createEmptyBorder(14,14,14,14)));
        // Class header strip
        JPanel hdr = new JPanel(new BorderLayout());
        hdr.setBackground(CRD2); hdr.setBorder(BorderFactory.createEmptyBorder(6,8,6,8));
        hdr.setMaximumSize(new Dimension(Integer.MAX_VALUE,38));
        JLabel tl = lbl(title, TXT, FT); tl.setAlignmentX(LEFT_ALIGNMENT);
        hdr.add(tl, BorderLayout.CENTER); p.add(hdr); p.add(vg(12));
        return p;
    }

    JPanel detailCard(String title) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(CRD); p.setBorder(BorderFactory.createEmptyBorder(10,12,10,12));
        p.add(lbl(title, TXT, FT)); p.add(vg(6));
        JSeparator sep = new JSeparator(); sep.setForeground(BRD);
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE,1)); p.add(sep); p.add(vg(8));
        return p;
    }

    void addFormRow(JPanel p, String label, JComponent field) {
        JLabel lbl = lbl(label, TXT2, FS); lbl.setAlignmentX(LEFT_ALIGNMENT);
        p.add(lbl); p.add(vg(2));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE,28));
        field.setAlignmentX(LEFT_ALIGNMENT);
        p.add(field); p.add(vg(7));
    }

    void addDetail(JPanel p, String key, String val) {
        JPanel row = new JPanel(new BorderLayout(8,0));
        row.setBackground(CRD2); row.setOpaque(true);
        row.setBorder(BorderFactory.createEmptyBorder(3,8,3,8));
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE,24));
        row.add(lbl(key, TXT2, FM), BorderLayout.WEST);
        JLabel v = lbl(val, TXT, FM); v.setHorizontalAlignment(SwingConstants.RIGHT);
        row.add(v, BorderLayout.EAST);
        row.setAlignmentX(LEFT_ALIGNMENT);
        p.add(row); p.add(vg(2));
    }

    JPanel methodsLabel(String[] methods) {
        JPanel p = new JPanel(); p.setOpaque(false);
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setAlignmentX(LEFT_ALIGNMENT);
        JSeparator sep = new JSeparator(); sep.setForeground(BRD);
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE,1)); p.add(sep); p.add(vg(6));
        p.add(lbl("Metodat:", TXT2, FS)); p.add(vg(4));
        for(String m : methods) {
            JLabel l = lbl("  + " + m, BLU2, FM); l.setAlignmentX(LEFT_ALIGNMENT); p.add(l); p.add(vg(2));
        }
        return p;
    }

    JTable styledTable(DefaultTableModel model) {
        JTable t = new JTable(model);
        t.setBackground(CRD); t.setForeground(TXT); t.setFont(FL); t.setRowHeight(26);
        t.setGridColor(BRD); t.setSelectionBackground(new Color(24,95,165,90)); t.setSelectionForeground(TXT);
        t.setShowHorizontalLines(true); t.setShowVerticalLines(false);
        t.getTableHeader().setBackground(CRD2); t.getTableHeader().setForeground(TXT2);
        t.getTableHeader().setFont(FS); t.getTableHeader().setBorder(new MatteBorder(0,0,1,0,BRD));
        t.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
            public Component getTableCellRendererComponent(JTable tb,Object v,boolean sel,boolean foc,int r,int c){
                super.getTableCellRendererComponent(tb,v,sel,foc,r,c);
                setBackground(sel?new Color(24,95,165,90):(r%2==0?CRD:CRD2));
                setForeground(TXT); setFont(FL); setBorder(BorderFactory.createEmptyBorder(0,6,0,6));
                String s=v==null?"":v.toString();
                if(s.contains("Locked")||s.contains("✖")||s.contains("Bllokuar")||s.contains("Critical"))  setForeground(RED2);
                else if(s.contains("Watch")||s.contains("Alert")||s.contains("UnderReview"))  setForeground(AMB2);
                else if(s.contains("Aktive")||s.contains("Aktiv")||s.contains("Normal")||s.contains("●")) setForeground(TEL2);
                return this;
            }
        });
        return t;
    }

    JTextField fld(String txt) {
        JTextField f = new JTextField(txt); styleInp(f); return f;
    }
    void styleInp(JTextField f) {
        f.setBackground(CRD2); f.setForeground(TXT); f.setCaretColor(TXT); f.setFont(FL);
        f.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(BRD), BorderFactory.createEmptyBorder(3,6,3,6)));
    }
    JComboBox<String> combo(String[] items) {
        JComboBox<String> c = new JComboBox<>(items);
        c.setBackground(CRD2); c.setForeground(TXT); c.setFont(FL);
        c.setBorder(BorderFactory.createLineBorder(BRD)); return c;
    }
    JButton bigBtn(String txt, Color bg, Color fg) {
        JButton b = new JButton(txt); b.setBackground(bg); b.setForeground(fg.brighter()); b.setFont(FT);
        b.setBorder(BorderFactory.createEmptyBorder(7,14,7,14)); b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setMaximumSize(new Dimension(Integer.MAX_VALUE,34)); b.setAlignmentX(LEFT_ALIGNMENT); return b;
    }
    JLabel badge(String t, Color c) {
        JLabel l = new JLabel(" "+t+" "); l.setFont(FS); l.setOpaque(true);
        l.setBackground(new Color(c.getRed(),c.getGreen(),c.getBlue(),35)); l.setForeground(c);
        l.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(c.getRed(),c.getGreen(),c.getBlue(),80)),BorderFactory.createEmptyBorder(2,6,2,6)));
        return l;
    }
    JLabel lbl(String t, Color c, Font f) { JLabel l=new JLabel(t); l.setForeground(c); l.setFont(f); return l; }
    JPanel col() { JPanel p=new JPanel(); p.setOpaque(false); p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS)); return p; }
    JLabel sep() { JLabel l=new JLabel("|"); l.setForeground(TXT3); l.setFont(FS); return l; }
    Component vg(int h) { return Box.createVerticalStrut(h); }

    // ─── MAIN ─────────────────────────────────────────────────────────────
    public static void main(String[] args) {
        try{UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());}catch(Exception ignore){}
        SwingUtilities.invokeLater(() -> {
            FSHNObjectManager app = new FSHNObjectManager();

            // Re-wire tabs with full detail panel
            app.tabs.removeAll();
            app.styleTab(app.tabs,"👤 Student",         BLU2,  app.rewire("student"));
            app.styleTab(app.tabs,"🎓 Pedagog",         PUR2,  app.rewire("pedagog"));
            app.styleTab(app.tabs,"🔧 Admin Sistemi",   TEL2,  app.rewire("admin"));
            app.styleTab(app.tabs,"🛡 Security Officer",AMB2,  app.rewire("officer"));
            app.styleTab(app.tabs,"💀 Sulmuesi",        RED2,  app.rewire("sulmues"));
            app.styleTab(app.tabs,"🤖 IDS Engine",      YLW2,  app.rewire("ids"));

            app.setVisible(true);
        });
    }

    JPanel rewire(String type) {
        JPanel root = tabRoot();
        switch(type) {
            case "student": {
                String[] cols={"ID","Emri","Mbiemri","Email","Roli","Departamenti","Gjendje","Tentativa"};
                DefaultTableModel m=new DefaultTableModel(cols,0){public boolean isCellEditable(int r,int c){return false;}};
                JTable t=styledTable(m); JScrollPane ts=new JScrollPane(t);
                Runnable r=()->{m.setRowCount(0);for(Student s:studentët)m.addRow(new Object[]{s.getStudentId(),s.getEmri(),s.getMbiemri(),s.getEmail(),s.getRoli(),s.getDepartamenti(),s.getGjendjaLlogarise(),s.getNumTentativave()});};
                r.run();
                root.add(splitPane(buildStudentForm(studentët,m,r),ts,t,studentët,idx->studentDetail(studentët.get(idx))));
                break;
            }
            case "pedagog": {
                String[] cols={"ID","Titulli","Emri","Mbiemri","Email","Departamenti","Gjendje"};
                DefaultTableModel m=new DefaultTableModel(cols,0){public boolean isCellEditable(int r,int c){return false;}};
                JTable t=styledTable(m); JScrollPane ts=new JScrollPane(t);
                Runnable r=()->{m.setRowCount(0);for(Pedagog p:pedagogët)m.addRow(new Object[]{p.getPedagogId(),p.getTitulliAkademik(),p.getEmri(),p.getMbiemri(),p.getEmail(),p.getDepartamenti(),p.getGjendjaLlogarise()});};
                r.run();
                root.add(splitPane(buildPedagogForm(pedagogët,m,r),ts,t,pedagogët,idx->pedagogDetail(pedagogët.get(idx))));
                break;
            }
            case "admin": {
                String[] cols={"ID","Emri","Mbiemri","Email","Roli","Nivel","Gjendje"};
                DefaultTableModel m=new DefaultTableModel(cols,0){public boolean isCellEditable(int r,int c){return false;}};
                JTable t=styledTable(m); JScrollPane ts=new JScrollPane(t);
                Runnable r=()->{m.setRowCount(0);for(AdminSistemi a:adminët)m.addRow(new Object[]{a.getAdminId(),a.getEmri(),a.getMbiemri(),a.getEmail(),a.getRoli(),a.getNivelLabel(),a.getGjendjaLlogarise()});};
                r.run();
                root.add(splitPane(buildAdminForm(adminët,m,r),ts,t,adminët,idx->adminDetail(adminët.get(idx))));
                break;
            }
            case "officer": {
                String[] cols={"ID","Emri","Mbiemri","Email","Nivel","Certifikim","Gjendje"};
                DefaultTableModel m=new DefaultTableModel(cols,0){public boolean isCellEditable(int r,int c){return false;}};
                JTable t=styledTable(m); JScrollPane ts=new JScrollPane(t);
                Runnable r=()->{m.setRowCount(0);for(SecurityOfficer o:officerët)m.addRow(new Object[]{o.getOfficerId(),o.getEmri(),o.getMbiemri(),o.getEmail(),o.getNivelLabel(),o.getCertifikimi(),o.getGjendjaLlogarise()});};
                r.run();
                root.add(splitPane(buildOfficerForm(officerët,m,r),ts,t,officerët,idx->officerDetail(officerët.get(idx))));
                break;
            }
            case "sulmues": {
                String[] cols={"ID","IP","Tipi Sulmit","Tentativa","Vendi","Bllokuar","Timestamp"};
                DefaultTableModel m=new DefaultTableModel(cols,0){public boolean isCellEditable(int r,int c){return false;}};
                JTable t=styledTable(m); JScrollPane ts=new JScrollPane(t);
                Runnable r=()->{m.setRowCount(0);for(Sulmuesi s:sulmuesit)m.addRow(new Object[]{s.getSulmuesId(),s.getIp(),s.getTipiSulmit(),s.getNumTentativave(),s.getVendOrigjines(),s.isEshteIBllokuar()?"✖ Po":"✔ Jo",s.getTimestampSulm()==null?"—":SDF.format(s.getTimestampSulm())});};
                r.run();
                root.add(splitPane(buildSulmuesForm(sulmuesit,m,r),ts,t,sulmuesit,idx->sulmuesDetail(sulmuesit.get(idx))));
                break;
            }
            case "ids": {
                String[] cols={"ID","Versioni","Aktiv","Prag","Kerk/min","IP Blok.","Status"};
                DefaultTableModel m=new DefaultTableModel(cols,0){public boolean isCellEditable(int r,int c){return false;}};
                JTable t=styledTable(m); JScrollPane ts=new JScrollPane(t);
                Runnable r=()->{m.setRowCount(0);for(IDSEngine i:idsEngines)m.addRow(new Object[]{i.getIdsId(),i.getVersioni(),i.isEshteAktiv()?"● Aktiv":"○ Off",i.getPragAlertit(),i.getKerkesaPerMinute(),i.getIpBllokuaraTotale(),i.getStatusDetektimit()});};
                r.run();
                root.add(splitPane(buildIDSForm(idsEngines,m,r),ts,t,idsEngines,idx->idsDetail(idsEngines.get(idx))));
                break;
            }
        }
        return root;
    }
}
