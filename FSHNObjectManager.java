import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

public class FSHNObjectManager extends JFrame {

    // --- COLORS ---
    static final Color BG    = new Color(12,  17,  27);
    static final Color PNL   = new Color(18,  24,  38);
    static final Color CRD   = new Color(24,  33,  51);
    static final Color CRD2  = new Color(30,  41,  63);
    static final Color BLU   = new Color(20,  80,  150);
    static final Color BLU2  = new Color(55,  138, 221);
    static final Color TEL   = new Color(12,  95,  74);
    static final Color TEL2  = new Color(25,  148, 110);
    static final Color RED   = new Color(130, 30,  30);
    static final Color RED2  = new Color(226, 75,  74);
    static final Color AMB   = new Color(130, 80,  5);
    static final Color AMB2  = new Color(239, 159, 39);
    static final Color PUR   = new Color(70,  55,  160);
    static final Color PUR2  = new Color(130, 120, 230);
    static final Color YLW   = new Color(80,  78,  15);
    static final Color YLW2  = new Color(200, 190, 80);
    static final Color TXT   = new Color(230, 228, 218);
    static final Color TXT2  = new Color(150, 148, 140);
    static final Color TXT3  = new Color(70,  68,  62);
    static final Color BRD   = new Color(38,  52,  78);
    static final Color DASH  = new Color(14,  19,  30);

    static final Font FT  = new Font("SansSerif",  Font.BOLD,  13);
    static final Font FL  = new Font("SansSerif",  Font.PLAIN, 12);
    static final Font FS  = new Font("SansSerif",  Font.PLAIN, 11);
    static final Font FH  = new Font("SansSerif",  Font.BOLD,  22);
    static final Font FM  = new Font("Monospaced", Font.PLAIN, 11);
    static final SimpleDateFormat SDF  = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    static final SimpleDateFormat SDFT = new SimpleDateFormat("HH:mm:ss");

    // --- OBJECT LISTS ---
    java.util.List<Student>         studentët  = new ArrayList<>();
    java.util.List<Pedagog>         pedagogët  = new ArrayList<>();
    java.util.List<AdminSistemi>    adminët    = new ArrayList<>();
    java.util.List<SecurityOfficer> officerët  = new ArrayList<>();
    java.util.List<Sulmuesi>        sulmuesit  = new ArrayList<>();
    java.util.List<IDSEngine>       idsEngines = new ArrayList<>();

    // --- DASHBOARD FIELDS ---
    final java.util.List<String> eventLog = new ArrayList<>();
    JTextArea logArea;
    JLabel    clockLabel;
    JLabel    threatLabel;
    JLabel    idsStatusLabel;
    JLabel[]  statCounts = new JLabel[6];
    JLabel[]  statSubs   = new JLabel[6];

    private JTabbedPane tabs;

    public FSHNObjectManager() {
        setTitle("FSHN \u2014 Sistema e Sigurise Universitare | Command Center v2.0");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1300, 800);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(1000, 640));
        getContentPane().setBackground(BG);
        setLayout(new BorderLayout());

        add(topBar(), BorderLayout.NORTH);

        tabs = new JTabbedPane();
        tabs.setBackground(CRD);
        tabs.setForeground(TXT);
        tabs.setFont(FT);

        seedData();

        tabs.addTab("\uD83D\uDCCA Dashboard",         dashboardTab());
        tabs.addTab("\uD83D\uDC64 Student",           rewire("student"));
        tabs.addTab("\uD83C\uDF93 Pedagog",           rewire("pedagog"));
        tabs.addTab("\uD83D\uDD27 Admin Sistemi",     rewire("admin"));
        tabs.addTab("\uD83D\uDEE1 Security Officer",  rewire("officer"));
        tabs.addTab("\uD83D\uDC80 Sulmuesi",          rewire("sulmues"));
        tabs.addTab("\uD83E\uDD16 IDS Engine",        rewire("ids"));

        add(tabs, BorderLayout.CENTER);
        add(statusBar(), BorderLayout.SOUTH);

        new javax.swing.Timer(1000, e -> updateClock()).start();
        logEvent("Sistema FSHN u inicializua \u2014 Command Center v2.0");
        logEvent("Demo data: " + studentët.size() + " studente, " + pedagogët.size()
                 + " pedagoge, " + adminët.size() + " admin, " + officerët.size()
                 + " officer, " + sulmuesit.size() + " sulmues, " + idsEngines.size() + " IDS");
    }

    // --- TOP BAR ---
    JPanel topBar() {
        JPanel b = new JPanel(new BorderLayout());
        b.setBackground(PNL);
        b.setBorder(new MatteBorder(0, 0, 2, 0, BRD));
        b.setPreferredSize(new Dimension(0, 58));

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 14, 0));
        left.setOpaque(false);
        JLabel ic = lbl("\uD83D\uDEE1", TXT, new Font("SansSerif", Font.PLAIN, 24));
        ic.setBorder(BorderFactory.createEmptyBorder(16, 8, 0, 0));
        left.add(ic);
        JPanel title = col();
        title.setBorder(BorderFactory.createEmptyBorder(12, 0, 0, 0));
        title.add(lbl("FSHN \u2014 Sistema e Sigurise Universitare", TXT, FT));
        title.add(lbl("Menaxhimi i Aktoreve UML  \u00b7  Simulim Sigurie  \u00b7  v2.0", TXT2, FS));
        left.add(title);
        b.add(left, BorderLayout.WEST);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 18));
        right.setOpaque(false);
        right.add(badge("6 Klasa UML", BLU2));
        right.add(badge("Java Swing",  TEL2));
        right.add(badge("FSHN 2024",   PUR2));
        b.add(right, BorderLayout.EAST);
        return b;
    }

    // --- STATUS BAR ---
    JPanel statusBar() {
        JPanel b = new JPanel(new BorderLayout());
        b.setBackground(PNL);
        b.setBorder(new MatteBorder(1, 0, 0, 0, BRD));
        b.setPreferredSize(new Dimension(0, 26));

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 4));
        left.setOpaque(false);
        left.add(lbl("\u25cf Aktiv", TEL2, FS));
        left.add(sep());
        left.add(lbl("Student(" + studentët.size() + ")", BLU2, FM));
        left.add(sep());
        left.add(lbl("Pedagog(" + pedagogët.size() + ")", PUR2, FM));
        left.add(sep());
        left.add(lbl("Admin(" + adminët.size() + ")", TEL2, FM));
        left.add(sep());
        left.add(lbl("Officer(" + officerët.size() + ")", AMB2, FM));
        left.add(sep());
        left.add(lbl("Sulmues(" + sulmuesit.size() + ")", RED2, FM));
        left.add(sep());
        left.add(lbl("IDS(" + idsEngines.size() + ")", YLW2, FM));
        b.add(left, BorderLayout.WEST);

        clockLabel = lbl(SDFT.format(new Date()), TXT2, FM);
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 4));
        right.setOpaque(false);
        right.add(lbl("\u23f1", TXT3, FS));
        right.add(clockLabel);
        b.add(right, BorderLayout.EAST);
        return b;
    }

    void refreshStatus() {
        BorderLayout bl = (BorderLayout) getContentPane().getLayout();
        Component south = bl.getLayoutComponent(BorderLayout.SOUTH);
        if (south != null) getContentPane().remove(south);
        getContentPane().add(statusBar(), BorderLayout.SOUTH);
        refreshDashboard();
        revalidate();
        repaint();
    }

    void updateClock() {
        if (clockLabel != null) clockLabel.setText(SDFT.format(new Date()));
    }

    // --- EVENT LOG ---
    void logEvent(String msg) {
        String entry = "[" + SDFT.format(new Date()) + "] " + msg;
        eventLog.add(0, entry);
        if (eventLog.size() > 300) eventLog.remove(eventLog.size() - 1);
        if (logArea != null) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < Math.min(eventLog.size(), 80); i++)
                sb.append(eventLog.get(i)).append("\n");
            logArea.setText(sb.toString());
            logArea.setCaretPosition(0);
        }
    }

    // --- DASHBOARD ---
    JPanel dashboardTab() {
        JPanel root = new JPanel(new BorderLayout(0, 12));
        root.setBackground(DASH);
        root.setBorder(BorderFactory.createEmptyBorder(16, 16, 12, 16));

        JPanel titleRow = new JPanel(new BorderLayout());
        titleRow.setOpaque(false);
        titleRow.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));
        titleRow.add(lbl("\uD83D\uDCCA  Pasqyra e Sistemit \u2014 Security Dashboard", TXT,
                         new Font("SansSerif", Font.BOLD, 15)), BorderLayout.WEST);
        root.add(titleRow, BorderLayout.NORTH);

        JPanel center = new JPanel(new BorderLayout(14, 0));
        center.setOpaque(false);

        JPanel leftCol = new JPanel(new BorderLayout(0, 10));
        leftCol.setOpaque(false);

        JPanel cardsGrid = new JPanel(new GridLayout(2, 3, 10, 10));
        cardsGrid.setOpaque(false);
        String[] titles = {"\uD83D\uDC64 Studente", "\uD83C\uDF93 Pedagoge", "\uD83D\uDD27 Admine",
                           "\uD83D\uDEE1 Officers", "\uD83D\uDC80 Sulmues", "\uD83E\uDD16 IDS Engines"};
        Color[]  colors = {BLU2, PUR2, TEL2, AMB2, RED2, YLW2};
        for (int i = 0; i < 6; i++) cardsGrid.add(buildStatCard(i, titles[i], colors[i]));
        leftCol.add(cardsGrid, BorderLayout.NORTH);

        JPanel statusRow = new JPanel(new GridLayout(1, 2, 10, 0));
        statusRow.setOpaque(false);
        statusRow.add(buildThreatCard());
        statusRow.add(buildIDSStatusCard());
        leftCol.add(statusRow, BorderLayout.CENTER);
        center.add(leftCol, BorderLayout.CENTER);

        JPanel logPanel = new JPanel(new BorderLayout(0, 6));
        logPanel.setBackground(CRD);
        logPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BRD),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        logPanel.setPreferredSize(new Dimension(355, 0));
        logPanel.add(lbl("\uD83D\uDCCB Activity Log", TXT, FT), BorderLayout.NORTH);

        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setBackground(new Color(9, 13, 21));
        logArea.setForeground(new Color(72, 199, 142));
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 10));
        logArea.setLineWrap(true);
        logArea.setWrapStyleWord(true);
        logArea.setBorder(BorderFactory.createEmptyBorder(4, 6, 4, 6));
        JScrollPane logScroll = new JScrollPane(logArea);
        logScroll.setBorder(BorderFactory.createLineBorder(BRD));
        logScroll.getViewport().setBackground(new Color(9, 13, 21));
        logPanel.add(logScroll, BorderLayout.CENTER);
        center.add(logPanel, BorderLayout.EAST);
        root.add(center, BorderLayout.CENTER);

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        btnRow.setBackground(PNL);
        btnRow.setBorder(new MatteBorder(1, 0, 0, 0, BRD));
        JButton simBtn = dashBtn("\u26a1 Simulate Random Attack", RED2, RED);
        simBtn.addActionListener(e -> simulateRandomAttack());
        btnRow.add(simBtn);
        JButton idsBtn = dashBtn("\uD83E\uDD16 Run IDS Scan", YLW2, YLW);
        idsBtn.addActionListener(e -> runIDSScan());
        btnRow.add(idsBtn);
        JButton refreshBtn = dashBtn("\uD83D\uDD04 Refresh Dashboard", TEL2, TEL);
        refreshBtn.addActionListener(e -> { refreshDashboard(); logEvent("Dashboard u rifreskua manualisht"); });
        btnRow.add(refreshBtn);
        JButton clearBtn = dashBtn("\uD83D\uDDD1 Clear Log", TXT2, CRD2);
        clearBtn.addActionListener(e -> { eventLog.clear(); if (logArea != null) logArea.setText(""); });
        btnRow.add(clearBtn);
        root.add(btnRow, BorderLayout.SOUTH);

        return root;
    }

    JPanel buildStatCard(int idx, String title, Color color) {
        JPanel card = new JPanel(new BorderLayout(0, 4));
        card.setBackground(CRD);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(color.getRed(), color.getGreen(), color.getBlue(), 70)),
            BorderFactory.createEmptyBorder(0, 12, 10, 12)));
        JPanel accent = new JPanel();
        accent.setPreferredSize(new Dimension(0, 3));
        accent.setBackground(color);
        card.add(accent, BorderLayout.NORTH);
        JPanel body = new JPanel(new BorderLayout(0, 2));
        body.setOpaque(false);
        body.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));
        statCounts[idx] = new JLabel("0");
        statCounts[idx].setFont(FH);
        statCounts[idx].setForeground(color);
        statSubs[idx] = new JLabel("\u2014");
        statSubs[idx].setFont(FS);
        statSubs[idx].setForeground(TXT2);
        body.add(lbl(title, TXT2, FS), BorderLayout.NORTH);
        body.add(statCounts[idx], BorderLayout.CENTER);
        body.add(statSubs[idx], BorderLayout.SOUTH);
        card.add(body, BorderLayout.CENTER);
        updateStatCard(idx);
        return card;
    }

    void updateStatCard(int idx) {
        if (statCounts[idx] == null) return;
        switch (idx) {
            case 0:
                statCounts[idx].setText(String.valueOf(studentët.size()));
                statSubs[idx].setText(studentët.stream().filter(s -> "Locked".equals(s.getGjendjaLlogarise())).count() + " Locked");
                break;
            case 1:
                statCounts[idx].setText(String.valueOf(pedagogët.size()));
                statSubs[idx].setText(pedagogët.size() + " regjistruar");
                break;
            case 2:
                statCounts[idx].setText(String.valueOf(adminët.size()));
                statSubs[idx].setText(adminët.stream().filter(a -> a.getNivelPrivilegjeve() == 3).count() + " Superadmin");
                break;
            case 3:
                statCounts[idx].setText(String.valueOf(officerët.size()));
                statSubs[idx].setText(officerët.stream().filter(o -> o.getNivelAutorizimi() == 3).count() + " Chief");
                break;
            case 4:
                statCounts[idx].setText(String.valueOf(sulmuesit.size()));
                statSubs[idx].setText(sulmuesit.stream().filter(Sulmuesi::isEshteIBllokuar).count() + "/" + sulmuesit.size() + " Bllokuar");
                break;
            case 5:
                statCounts[idx].setText(String.valueOf(idsEngines.size()));
                statSubs[idx].setText(idsEngines.stream().filter(IDSEngine::isEshteAktiv).count() + " Aktiv");
                break;
        }
    }

    JPanel buildThreatCard() {
        JPanel card = new JPanel(new BorderLayout(0, 6));
        card.setBackground(CRD);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(RED2.getRed(), RED2.getGreen(), RED2.getBlue(), 60)),
            BorderFactory.createEmptyBorder(12, 14, 12, 14)));
        JPanel accent = new JPanel(); accent.setPreferredSize(new Dimension(0, 3)); accent.setBackground(RED2);
        card.add(accent, BorderLayout.NORTH);
        JPanel body = new JPanel(new BorderLayout(0, 4));
        body.setOpaque(false);
        body.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));
        body.add(lbl("\u26a0 Niveli i Kercenimit", TXT2, FS), BorderLayout.NORTH);
        threatLabel = new JLabel(getThreatText());
        threatLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        threatLabel.setForeground(getThreatColor());
        body.add(threatLabel, BorderLayout.CENTER);
        body.add(lbl("Bazuar ne sulmuese aktive", TXT2, FS), BorderLayout.SOUTH);
        card.add(body, BorderLayout.CENTER);
        return card;
    }

    JPanel buildIDSStatusCard() {
        JPanel card = new JPanel(new BorderLayout(0, 6));
        card.setBackground(CRD);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(YLW2.getRed(), YLW2.getGreen(), YLW2.getBlue(), 60)),
            BorderFactory.createEmptyBorder(12, 14, 12, 14)));
        JPanel accent = new JPanel(); accent.setPreferredSize(new Dimension(0, 3)); accent.setBackground(YLW2);
        card.add(accent, BorderLayout.NORTH);
        JPanel body = new JPanel(new BorderLayout(0, 4));
        body.setOpaque(false);
        body.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));
        body.add(lbl("\uD83E\uDD16 Statusi i IDS", TXT2, FS), BorderLayout.NORTH);
        idsStatusLabel = new JLabel(getIDSStatusText());
        idsStatusLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
        idsStatusLabel.setForeground(getIDSStatusColor());
        body.add(idsStatusLabel, BorderLayout.CENTER);
        long totalBlocked = idsEngines.stream().mapToLong(IDSEngine::getIpBllokuaraTotale).sum();
        body.add(lbl("IP Bllokuar Totale: " + totalBlocked, TXT2, FS), BorderLayout.SOUTH);
        card.add(body, BorderLayout.CENTER);
        return card;
    }

    String getThreatText() {
        long unblocked = sulmuesit.stream().filter(s -> !s.isEshteIBllokuar()).count();
        if (unblocked == 0) return "\u25cf NORMAL";
        if (unblocked == 1) return "\u25b2 MESATAR";
        if (unblocked <= 3) return "\u26a0 I LARTE";
        return "\uD83D\uDEA8 KRITIK";
    }
    Color getThreatColor() {
        long unblocked = sulmuesit.stream().filter(s -> !s.isEshteIBllokuar()).count();
        if (unblocked == 0) return TEL2;
        if (unblocked == 1) return AMB2;
        if (unblocked <= 3) return new Color(230, 120, 40);
        return RED2;
    }
    String getIDSStatusText() {
        long aktiv = idsEngines.stream().filter(IDSEngine::isEshteAktiv).count();
        if (idsEngines.isEmpty()) return "\u25cb Asnje IDS";
        if (aktiv == 0) return "\u25cb Joaktiv";
        String worst = idsEngines.stream().filter(IDSEngine::isEshteAktiv)
                .map(IDSEngine::getStatusDetektimit).reduce("Normal", this::worstStatus);
        return "\u25cf " + aktiv + " Aktiv \u2014 " + worst;
    }
    Color getIDSStatusColor() {
        if (idsEngines.isEmpty()) return TXT2;
        if (idsEngines.stream().anyMatch(i -> "Critical".equals(i.getStatusDetektimit()))) return RED2;
        if (idsEngines.stream().anyMatch(i -> "Alert".equals(i.getStatusDetektimit()))) return AMB2;
        if (idsEngines.stream().anyMatch(i -> "Watch".equals(i.getStatusDetektimit()))) return new Color(230, 180, 40);
        return TEL2;
    }
    String worstStatus(String a, String b) {
        String[] levels = {"Normal", "Watch", "Alert", "Critical"};
        int ra = 0, rb = 0;
        for (int i = 0; i < levels.length; i++) {
            if (levels[i].equals(a)) ra = i;
            if (levels[i].equals(b)) rb = i;
        }
        return ra >= rb ? a : b;
    }
    void refreshDashboard() {
        for (int i = 0; i < 6; i++) updateStatCard(i);
        if (threatLabel    != null) { threatLabel.setText(getThreatText()); threatLabel.setForeground(getThreatColor()); }
        if (idsStatusLabel != null) { idsStatusLabel.setText(getIDSStatusText()); idsStatusLabel.setForeground(getIDSStatusColor()); }
    }

    // --- SIMULATE ATTACK ---
    void simulateRandomAttack() {
        String[] types   = {"BruteForce","SQLInjection","Phishing","DDoS","XSS","Spoofing"};
        String[] origins = {"Russia","China","Iran","North Korea","Romania","Brazil"};
        Random rnd = new Random();
        String id   = String.format("ATK-%03d", sulmuesit.size() + 1);
        String ip   = rnd.nextInt(256)+"."+rnd.nextInt(256)+"."+rnd.nextInt(256)+"."+rnd.nextInt(256);
        String type   = types[rnd.nextInt(types.length)];
        int attempts  = rnd.nextInt(15) + 1;
        String origin = origins[rnd.nextInt(origins.length)];
        String ua     = "python-requests/" + (rnd.nextInt(4) + 1) + "." + rnd.nextInt(5);
        Sulmuesi atk  = new Sulmuesi(id, ip, type, attempts, false, origin, ua);
        sulmuesit.add(atk);
        logEvent("\u26a1 SULM I RI: " + type + " nga " + ip + " [" + origin + "] \u2014 " + attempts + " tentativa");
        boolean idsActive = idsEngines.stream().anyMatch(IDSEngine::isEshteAktiv);
        boolean blocked = false;
        java.util.Optional<IDSEngine> firstActive = idsEngines.stream().filter(IDSEngine::isEshteAktiv).findFirst();
        if (idsActive && firstActive.isPresent()) {
            IDSEngine ids = firstActive.get();
            if (attempts >= ids.getPragAlertit()) {
                ids.bllokIPAuto(ip);
                atk.blloko();
                blocked = true;
                logEvent("\uD83E\uDD16 IDS " + ids.getIdsId() + " bllokoi automatikisht IP: " + ip);
            }
        }
        refreshStatus();
        String msg = "Sulm i simuluar me sukses!\n\n"
                   + "  Tipi:      " + type + "\n"
                   + "  IP:        " + ip + "\n"
                   + "  Origjina:  " + origin + "\n"
                   + "  Tentativa: " + attempts + "\n\n"
                   + (blocked ? "\u2705 IDS e bllokoi automatikisht IP-në!"
                               : (idsActive ? "\u26a0 IDS aktiv por nen pragjen e alertit."
                                            : "\u26d4 IDS nuk eshte aktiv \u2014 IP jo e bllokuar!"));
        JOptionPane.showMessageDialog(this, msg, "Simulim Sulmi",
                blocked ? JOptionPane.WARNING_MESSAGE : JOptionPane.INFORMATION_MESSAGE);
    }

    void runIDSScan() {
        if (idsEngines.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nuk ka IDS Engine te konfiguruar!", "IDS Scan", JOptionPane.WARNING_MESSAGE); return;
        }
        long active = idsEngines.stream().filter(IDSEngine::isEshteAktiv).count();
        if (active == 0) {
            JOptionPane.showMessageDialog(this, "Asnje IDS Engine nuk eshte aktiv!", "IDS Scan", JOptionPane.WARNING_MESSAGE); return;
        }
        int blocked = 0;
        for (IDSEngine ids : idsEngines) {
            if (!ids.isEshteAktiv()) continue;
            ids.monitoroTrafikun();
            for (Sulmuesi s : sulmuesit) {
                if (!s.isEshteIBllokuar() && s.getNumTentativave() >= ids.getPragAlertit()) {
                    ids.bllokIPAuto(s.getIp());
                    s.blloko();
                    blocked++;
                    logEvent("\uD83E\uDD16 IDS " + ids.getIdsId() + " bllokoi: " + s.getIp() + " [" + s.getTipiSulmit() + "]");
                }
            }
            ids.gjenerojLog();
        }
        logEvent("\u2705 IDS Scan kompletuar \u2014 " + active + " engine aktiv, " + blocked + " IP bllokuar");
        refreshStatus();
        JOptionPane.showMessageDialog(this,
            "IDS Scan kompletuar!\n\nEngine aktiv:  " + active + "\nIP te reja bllokuar: " + blocked,
            "IDS Scan", JOptionPane.INFORMATION_MESSAGE);
    }

    // --- INTERACTIVE DETAIL PANELS ---
    JPanel studentDetailI(Student s, java.util.List<Student> list, Runnable onModify) {
        JPanel p = detailCard("Detajet e Objektit: Student");
        addDetail(p, "studentId",       s.getStudentId());
        addDetail(p, "emri",            s.getEmri());
        addDetail(p, "mbiemri",         s.getMbiemri());
        addDetail(p, "email",           s.getEmail());
        addDetail(p, "roli",            s.getRoli());
        addDetail(p, "departamenti",    s.getDepartamenti());
        addDetail(p, "gjendje",         s.getGjendjaLlogarise());
        addDetail(p, "numTentativave",  String.valueOf(s.getNumTentativave()));
        addDetail(p, "dateRegjistrimi", s.getDatëRegjistrimi() == null ? "\u2014" : SDF.format(s.getDatëRegjistrimi()));
        p.add(vg(10));
        addActionSection(p);

        JButton toggleBtn = actionBtn(
            "Locked".equals(s.getGjendjaLlogarise()) ? "\u25b6 Aktivizo Llogarise" : "\u2298 Blloko Llogarise",
            "Locked".equals(s.getGjendjaLlogarise()) ? TEL2 : AMB2);
        toggleBtn.addActionListener(e -> {
            if ("Locked".equals(s.getGjendjaLlogarise())) { s.setGjendjaLlogarise("Aktive"); logEvent("\u2714 Student aktivizua: " + s.getEmri() + " " + s.getMbiemri()); }
            else { s.setGjendjaLlogarise("Locked"); logEvent("\u2298 Student bllokua: " + s.getEmri() + " " + s.getMbiemri()); }
            onModify.run(); refreshStatus(); refreshDashboard();
        });
        p.add(toggleBtn); p.add(vg(4));

        JButton resetBtn = actionBtn("\u21ba Reset Tentativa (" + s.getNumTentativave() + ")", BLU2);
        resetBtn.addActionListener(e -> { s.resetTentativa(); logEvent("\u21ba Tentativat u resetuan: " + s.getEmri()); onModify.run(); });
        p.add(resetBtn); p.add(vg(4));

        JButton loginBtn = actionBtn("\u26a1 Simulim Login", PUR2);
        loginBtn.addActionListener(e -> {
            JPasswordField pf = new JPasswordField(15);
            int opt = JOptionPane.showConfirmDialog(this, pf, "Vendos fjalëkalimin:", JOptionPane.OK_CANCEL_OPTION);
            if (opt == JOptionPane.OK_OPTION) {
                String pass = new String(pf.getPassword());
                if (s.login(s.getEmail(), pass)) {
                    logEvent("\u2714 Login sukses \u2014 Student: " + s.getEmri());
                    JOptionPane.showMessageDialog(this, "\u2714 Login i suksesshem!", "Login OK", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    s.rritTentativa();
                    logEvent("\u2718 Login deshtoi \u2014 Student: " + s.getEmri() + " (tentativa " + s.getNumTentativave() + ")");
                    JOptionPane.showMessageDialog(this, "\u2718 Login deshtoi!\nTentativa: " + s.getNumTentativave(), "Login Error", JOptionPane.WARNING_MESSAGE);
                    onModify.run();
                }
            }
        });
        p.add(loginBtn); p.add(vg(8));

        JButton delBtn = actionBtn("\u2715 Fshi Studentin", RED2);
        delBtn.setBackground(RED);
        delBtn.addActionListener(e -> {
            int c = JOptionPane.showConfirmDialog(this, "Fshi studentin " + s.getEmri() + " " + s.getMbiemri() + "?",
                    "Konfirmo Fshirjen", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (c == JOptionPane.YES_OPTION) {
                logEvent("\u2715 Student u fshi: " + s.getEmri() + " [" + s.getStudentId() + "]");
                list.remove(s); onModify.run(); refreshStatus(); refreshDashboard();
            }
        });
        p.add(delBtn);
        return p;
    }

    JPanel pedagogDetailI(Pedagog pg, java.util.List<Pedagog> list, Runnable onModify) {
        JPanel p = detailCard("Detajet e Objektit: Pedagog");
        addDetail(p, "pedagogId",       pg.getPedagogId());
        addDetail(p, "emri",            pg.getEmri());
        addDetail(p, "mbiemri",         pg.getMbiemri());
        addDetail(p, "email",           pg.getEmail());
        addDetail(p, "titulliAkademik", pg.getTitulliAkademik());
        addDetail(p, "departamenti",    pg.getDepartamenti());
        addDetail(p, "roli",            pg.getRoli());
        addDetail(p, "gjendje",         pg.getGjendjaLlogarise());
        addDetail(p, "dateRegjistrimi", pg.getDatëRegjistrimi() == null ? "\u2014" : SDF.format(pg.getDatëRegjistrimi()));
        p.add(vg(10));
        addActionSection(p);

        JButton toggleBtn = actionBtn(
            "Locked".equals(pg.getGjendjaLlogarise()) ? "\u25b6 Aktivizo Llogarise" : "\u2298 Blloko Llogarise",
            "Locked".equals(pg.getGjendjaLlogarise()) ? TEL2 : AMB2);
        toggleBtn.addActionListener(e -> {
            if ("Locked".equals(pg.getGjendjaLlogarise())) { pg.setGjendjaLlogarise("Aktive"); logEvent("\u2714 Pedagog aktivizua: " + pg.getTitulliAkademik() + " " + pg.getEmri()); }
            else { pg.setGjendjaLlogarise("Locked"); logEvent("\u2298 Pedagog bllokua: " + pg.getTitulliAkademik() + " " + pg.getEmri()); }
            onModify.run(); refreshStatus();
        });
        p.add(toggleBtn); p.add(vg(4));

        JButton gradeBtn = actionBtn("\uD83D\uDCDD Fut Note", TEL2);
        gradeBtn.addActionListener(e -> {
            String nota = JOptionPane.showInputDialog(this, "Nota (p.sh. 9.5):", "Fut Note", JOptionPane.QUESTION_MESSAGE);
            if (nota != null && !nota.trim().isEmpty()) {
                pg.futNote(nota.trim());
                logEvent("\uD83D\uDCDD Pedagog " + pg.getEmri() + " futi noten: " + nota.trim());
                JOptionPane.showMessageDialog(this, "Nota '" + nota.trim() + "' u regjistrua.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        p.add(gradeBtn); p.add(vg(4));

        JButton matBtn = actionBtn("\uD83D\uDCCE Ngarko Material", PUR2);
        matBtn.addActionListener(e -> {
            String mat = JOptionPane.showInputDialog(this, "Emri i materialit:", "Ngarko Material", JOptionPane.QUESTION_MESSAGE);
            if (mat != null && !mat.trim().isEmpty()) {
                pg.ngarkoMaterial(mat.trim());
                logEvent("\uD83D\uDCCE Material nga " + pg.getEmri() + ": " + mat.trim());
                JOptionPane.showMessageDialog(this, "Materiali '" + mat.trim() + "' u ngarkua.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        p.add(matBtn); p.add(vg(8));

        JButton delBtn = actionBtn("\u2715 Fshi Pedagogun", RED2);
        delBtn.setBackground(RED);
        delBtn.addActionListener(e -> {
            int c = JOptionPane.showConfirmDialog(this, "Fshi " + pg.getTitulliAkademik() + " " + pg.getEmri() + "?",
                    "Konfirmo Fshirjen", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (c == JOptionPane.YES_OPTION) {
                logEvent("\u2715 Pedagog u fshi: " + pg.getTitulliAkademik() + " " + pg.getEmri() + " [" + pg.getPedagogId() + "]");
                list.remove(pg); onModify.run(); refreshStatus(); refreshDashboard();
            }
        });
        p.add(delBtn);
        return p;
    }

    JPanel adminDetailI(AdminSistemi a, java.util.List<AdminSistemi> list, Runnable onModify) {
        JPanel p = detailCard("Detajet e Objektit: AdminSistemi");
        addDetail(p, "adminId",           a.getAdminId());
        addDetail(p, "emri",              a.getEmri());
        addDetail(p, "mbiemri",           a.getMbiemri());
        addDetail(p, "email",             a.getEmail());
        addDetail(p, "roli",              a.getRoli());
        addDetail(p, "nivelPrivilegjeve", a.getNivelPrivilegjeve() + " \u2014 " + a.getNivelLabel());
        addDetail(p, "gjendje",           a.getGjendjaLlogarise());
        addDetail(p, "dateRegjistrimi",   a.getDatëRegjistrimi() == null ? "\u2014" : SDF.format(a.getDatëRegjistrimi()));
        p.add(vg(10));
        addActionSection(p);

        JButton toggleBtn = actionBtn(
            "Locked".equals(a.getGjendjaLlogarise()) ? "\u25b6 Aktivizo Llogarise" : "\u2298 Blloko Llogarise",
            "Locked".equals(a.getGjendjaLlogarise()) ? TEL2 : AMB2);
        toggleBtn.addActionListener(e -> {
            if ("Locked".equals(a.getGjendjaLlogarise())) { a.setGjendjaLlogarise("Aktive"); logEvent("\u2714 Admin aktivizua: " + a.getEmri() + " [" + a.getNivelLabel() + "]"); }
            else { a.setGjendjaLlogarise("Locked"); logEvent("\u2298 Admin bllokua: " + a.getEmri() + " [" + a.getNivelLabel() + "]"); }
            onModify.run(); refreshStatus();
        });
        p.add(toggleBtn); p.add(vg(4));

        JButton blockIPBtn = actionBtn("\u26d4 Bloko IP", RED2);
        blockIPBtn.addActionListener(e -> {
            String ip = JOptionPane.showInputDialog(this, "IP adresa:", "Bloko IP", JOptionPane.QUESTION_MESSAGE);
            if (ip != null && !ip.trim().isEmpty()) {
                a.bllokIP(ip.trim());
                logEvent("\u26d4 Admin " + a.getEmri() + " bllokoi IP: " + ip.trim());
                JOptionPane.showMessageDialog(this, "IP '" + ip.trim() + "' u bllokua nga " + a.getEmri() + ".", "IP Bllokuar", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        p.add(blockIPBtn); p.add(vg(4));

        JButton reportBtn = actionBtn("\uD83D\uDCCA Gjenero Raport", TEL2);
        reportBtn.addActionListener(e -> {
            a.gjenerojRaport();
            logEvent("\uD83D\uDCCA Admin " + a.getEmri() + " gjeneroi raport sigurie");
            JOptionPane.showMessageDialog(this,
                "Raporti u gjenerua:\n  Admin: " + a.getEmri() + " " + a.getMbiemri() + "\n  Nivel: " + a.getNivelLabel() + "\n  Koha:  " + SDF.format(new Date()),
                "Raport Gjeneruar", JOptionPane.INFORMATION_MESSAGE);
        });
        p.add(reportBtn); p.add(vg(8));

        JButton delBtn = actionBtn("\u2715 Fshi Adminin", RED2);
        delBtn.setBackground(RED);
        delBtn.addActionListener(e -> {
            int c = JOptionPane.showConfirmDialog(this, "Fshi adminin " + a.getEmri() + "?",
                    "Konfirmo Fshirjen", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (c == JOptionPane.YES_OPTION) {
                logEvent("\u2715 Admin u fshi: " + a.getEmri() + " " + a.getMbiemri() + " [" + a.getAdminId() + "]");
                list.remove(a); onModify.run(); refreshStatus(); refreshDashboard();
            }
        });
        p.add(delBtn);
        return p;
    }

    JPanel officerDetailI(SecurityOfficer o, java.util.List<SecurityOfficer> list, Runnable onModify) {
        JPanel p = detailCard("Detajet e Objektit: SecurityOfficer");
        addDetail(p, "officerId",       o.getOfficerId());
        addDetail(p, "emri",            o.getEmri());
        addDetail(p, "mbiemri",         o.getMbiemri());
        addDetail(p, "email",           o.getEmail());
        addDetail(p, "roli",            o.getRoli());
        addDetail(p, "nivelAutorizimi", o.getNivelAutorizimi() + " \u2014 " + o.getNivelLabel());
        addDetail(p, "certifikimi",     o.getCertifikimi());
        addDetail(p, "gjendje",         o.getGjendjaLlogarise());
        addDetail(p, "dateRegjistrimi", o.getDatëRegjistrimi() == null ? "\u2014" : SDF.format(o.getDatëRegjistrimi()));
        p.add(vg(10));
        addActionSection(p);

        JButton toggleBtn = actionBtn(
            "Locked".equals(o.getGjendjaLlogarise()) ? "\u25b6 Aktivizo Llogarise" : "\u2298 Blloko Llogarise",
            "Locked".equals(o.getGjendjaLlogarise()) ? TEL2 : AMB2);
        toggleBtn.addActionListener(e -> {
            if ("Locked".equals(o.getGjendjaLlogarise())) { o.setGjendjaLlogarise("Aktive"); logEvent("\u2714 SecurityOfficer aktivizua: " + o.getEmri() + " [" + o.getNivelLabel() + "]"); }
            else { o.setGjendjaLlogarise("Locked"); logEvent("\u2298 SecurityOfficer bllokua: " + o.getEmri()); }
            onModify.run(); refreshStatus();
        });
        p.add(toggleBtn); p.add(vg(4));

        JButton analyzeBtn = actionBtn("\uD83D\uDD0D Analizo Incident", AMB2);
        analyzeBtn.addActionListener(e -> {
            String inc = JOptionPane.showInputDialog(this, "ID Incidentit (p.sh. INC-001):", "Analizo Incident", JOptionPane.QUESTION_MESSAGE);
            if (inc != null && !inc.trim().isEmpty()) {
                o.analizoIncident(inc.trim());
                logEvent("\uD83D\uDD0D Officer " + o.getEmri() + " analizoi incidentin: " + inc.trim());
                JOptionPane.showMessageDialog(this, "Incidenti '" + inc.trim() + "' u analizua nga " + o.getEmri() + " (" + o.getCertifikimi() + ").", "Analize Kompletuar", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        p.add(analyzeBtn); p.add(vg(4));

        JButton alertBtn = actionBtn("\uD83D\uDEA8 Konfirmo Alert", RED2);
        alertBtn.addActionListener(e -> {
            String alertId = JOptionPane.showInputDialog(this, "ID Alertit (p.sh. ALT-001):", "Konfirmo Alert", JOptionPane.QUESTION_MESSAGE);
            if (alertId != null && !alertId.trim().isEmpty()) {
                o.konfirmoAlert(alertId.trim());
                logEvent("\uD83D\uDEA8 Officer " + o.getEmri() + " konfirmoi alertin: " + alertId.trim());
                JOptionPane.showMessageDialog(this, "Alerti '" + alertId.trim() + "' u konfirmua nga " + o.getEmri() + ".", "Alert Konfirmuar", JOptionPane.WARNING_MESSAGE);
            }
        });
        p.add(alertBtn); p.add(vg(8));

        JButton delBtn = actionBtn("\u2715 Fshi Oficerin", RED2);
        delBtn.setBackground(RED);
        delBtn.addActionListener(e -> {
            int c = JOptionPane.showConfirmDialog(this, "Fshi oficerin " + o.getEmri() + "?",
                    "Konfirmo Fshirjen", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (c == JOptionPane.YES_OPTION) {
                logEvent("\u2715 SecurityOfficer u fshi: " + o.getEmri() + " " + o.getMbiemri() + " [" + o.getOfficerId() + "]");
                list.remove(o); onModify.run(); refreshStatus(); refreshDashboard();
            }
        });
        p.add(delBtn);
        return p;
    }

    JPanel sulmuesDetailI(Sulmuesi s, java.util.List<Sulmuesi> list, Runnable onModify) {
        JPanel p = detailCard("Detajet e Objektit: Sulmuesi");
        addDetail(p, "sulmuesId",      s.getSulmuesId());
        addDetail(p, "ip",             s.getIp());
        addDetail(p, "tipiSulmit",     s.getTipiSulmit());
        addDetail(p, "numTentativave", String.valueOf(s.getNumTentativave()));
        addDetail(p, "vendOrigjines",  s.getVendOrigjines());
        addDetail(p, "userAgent",      s.getUserAgent());
        addDetail(p, "eshteIBllokuar", s.isEshteIBllokuar() ? "\u2716 Po \u2014 Bllokuar" : "\u2714 Jo \u2014 Aktiv");
        addDetail(p, "timestampSulm",  s.getTimestampSulm() == null ? "\u2014" : SDF.format(s.getTimestampSulm()));
        p.add(vg(10));
        addActionSection(p);

        JButton blockBtn = actionBtn(s.isEshteIBllokuar() ? "\u2714 Zhblloko IP" : "\u26d4 Bloko IP",
                                     s.isEshteIBllokuar() ? TEL2 : RED2);
        blockBtn.addActionListener(e -> {
            if (s.isEshteIBllokuar()) { s.setEshteIBllokuar(false); logEvent("\u2714 Sulmuesi zhbllokua: " + s.getIp() + " [" + s.getTipiSulmit() + "]"); }
            else { s.blloko(); logEvent("\u26d4 Sulmuesi bllokua: " + s.getIp() + " [" + s.getTipiSulmit() + "]"); }
            onModify.run(); refreshDashboard();
        });
        p.add(blockBtn); p.add(vg(4));

        JButton simBtn = actionBtn("\u26a1 Simulo Sulm", AMB2);
        simBtn.addActionListener(e -> {
            switch (s.getTipiSulmit()) {
                case "BruteForce":   s.tentoBruteForce(); break;
                case "SQLInjection": s.tentojSQLInj();    break;
                case "Phishing":     s.tentojPhishing();  break;
                default:             s.tentoBruteForce(); break;
            }
            logEvent("\u26a1 Sulm simuluar: " + s.getTipiSulmit() + " nga " + s.getIp() + " [tentativa " + s.getNumTentativave() + "]");
            onModify.run();
            JOptionPane.showMessageDialog(this,
                "Sulm i simuluar:\n  Tipi:      " + s.getTipiSulmit() + "\n  IP:        " + s.getIp() + "\n  Tentativa: " + s.getNumTentativave(),
                "Simulim", JOptionPane.WARNING_MESSAGE);
        });
        p.add(simBtn); p.add(vg(8));

        JButton delBtn = actionBtn("\u2715 Fshi Sulmuësin", RED2);
        delBtn.setBackground(RED);
        delBtn.addActionListener(e -> {
            int c = JOptionPane.showConfirmDialog(this, "Fshi sulmuësin " + s.getIp() + " [" + s.getTipiSulmit() + "]?",
                    "Konfirmo Fshirjen", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (c == JOptionPane.YES_OPTION) {
                logEvent("\u2715 Sulmuesi u fshi: " + s.getSulmuesId() + " IP:" + s.getIp());
                list.remove(s); onModify.run(); refreshStatus(); refreshDashboard();
            }
        });
        p.add(delBtn);
        return p;
    }

    JPanel idsDetailI(IDSEngine ids, java.util.List<IDSEngine> list, Runnable onModify) {
        JPanel p = detailCard("Detajet e Objektit: IDSEngine");
        addDetail(p, "idsId",             ids.getIdsId());
        addDetail(p, "versioni",          ids.getVersioni());
        addDetail(p, "eshteAktiv",        ids.isEshteAktiv() ? "\u25cf Po \u2014 Aktiv" : "\u25cb Jo \u2014 Joaktiv");
        addDetail(p, "pragAlertit",       ids.getPragAlertit() + " kerk/sek");
        addDetail(p, "kerkesaPerMinute",  String.valueOf(ids.getKerkesaPerMinute()));
        addDetail(p, "ipBllokuaraTotale", String.valueOf(ids.getIpBllokuaraTotale()));
        addDetail(p, "statusDetektimit",  ids.getStatusDetektimit());
        addDetail(p, "ngritjeHeren",      ids.getNgritjeHerën() == null ? "\u2014" : SDF.format(ids.getNgritjeHerën()));
        p.add(vg(10));
        addActionSection(p);

        JButton toggleBtn = actionBtn(
            ids.isEshteAktiv() ? "\u25cb Caktivizo IDS" : "\u25cf Aktivizo IDS",
            ids.isEshteAktiv() ? AMB2 : TEL2);
        toggleBtn.addActionListener(e -> {
            if (ids.isEshteAktiv()) { ids.çaktivizo(); logEvent("\u25cb IDS caktivizua: " + ids.getIdsId()); }
            else                    { ids.aktivizo();  logEvent("\u25cf IDS aktivizua: " + ids.getIdsId()); }
            onModify.run(); refreshDashboard();
        });
        p.add(toggleBtn); p.add(vg(4));

        JButton blockIPBtn = actionBtn("\u26d4 Bloko IP Manual", RED2);
        blockIPBtn.addActionListener(e -> {
            String ip = JOptionPane.showInputDialog(this, "IP adresa:", "Bloko IP", JOptionPane.QUESTION_MESSAGE);
            if (ip != null && !ip.trim().isEmpty()) {
                ids.bllokIPAuto(ip.trim());
                logEvent("\u26d4 IDS " + ids.getIdsId() + " bllokoi IP manual: " + ip.trim());
                onModify.run();
                JOptionPane.showMessageDialog(this, "IP '" + ip.trim() + "' u bllokua. Total: " + ids.getIpBllokuaraTotale(), "IP Bllokuar", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        p.add(blockIPBtn); p.add(vg(4));

        JButton patternBtn = actionBtn("\uD83D\uDD0D Analizo Pattern", BLU2);
        patternBtn.addActionListener(e -> {
            String req = JOptionPane.showInputDialog(this, "Vendos kerkesen:", "Analizo Pattern", JOptionPane.QUESTION_MESSAGE);
            if (req != null) {
                boolean threat = ids.analizoPattern(req);
                logEvent((threat ? "\u26a0 Kercenim zbuluar" : "\u2714 Pattern normal") + " \u2014 IDS " + ids.getIdsId());
                JOptionPane.showMessageDialog(this,
                    threat ? "\u26a0 KERCENIM ZBULUAR!\n\nPattern i dyshimte: '" + req + "'" : "\u2714 Pattern normal \u2014 asnje kercenim.",
                    threat ? "Kercenim!" : "Normal",
                    threat ? JOptionPane.WARNING_MESSAGE : JOptionPane.INFORMATION_MESSAGE);
            }
        });
        p.add(patternBtn); p.add(vg(4));

        JButton logBtn = actionBtn("\uD83D\uDCCB Gjenero Log", YLW2);
        logBtn.addActionListener(e -> {
            ids.gjenerojLog();
            logEvent("\uD83D\uDCCB IDS " + ids.getIdsId() + " gjeneroi log sigurie");
            JOptionPane.showMessageDialog(this, "Log i sigurise u gjenerua!\n  IDS ID: " + ids.getIdsId() + "\n  Versioni: " + ids.getVersioni() + "\n  Koha: " + SDF.format(new Date()), "Log Gjeneruar", JOptionPane.INFORMATION_MESSAGE);
        });
        p.add(logBtn); p.add(vg(8));

        JButton delBtn = actionBtn("\u2715 Fshi IDS Engine", RED2);
        delBtn.setBackground(RED);
        delBtn.addActionListener(e -> {
            int c = JOptionPane.showConfirmDialog(this, "Fshi IDS Engine " + ids.getIdsId() + "?",
                    "Konfirmo Fshirjen", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (c == JOptionPane.YES_OPTION) {
                logEvent("\u2715 IDS Engine u fshi: " + ids.getIdsId() + " v" + ids.getVersioni());
                list.remove(ids); onModify.run(); refreshStatus(); refreshDashboard();
            }
        });
        p.add(delBtn);
        return p;
    }

    // --- SEED DATA ---
    void seedData() {
        studentët.add(new Student("STU-001","Ardit","Hoxha","a.hoxha@fshn.edu.al","pass123","Aktive","Informatike"));
        studentët.add(new Student("STU-002","Besa","Koci","b.koci@fshn.edu.al","pass456","Aktive","Matematike"));
        studentët.add(new Student("STU-003","Cen","Duka","c.duka@fshn.edu.al","pass789","Locked","Fizike"));

        pedagogët.add(new Pedagog("PED-001","Petrit","Marku","p.marku@fshn.edu.al","ped123","Informatike","Prof. Dr.","Aktive"));
        pedagogët.add(new Pedagog("PED-002","Elona","Sino","e.sino@fshn.edu.al","ped456","Matematike","Dr.","Aktive"));

        adminët.add(new AdminSistemi("ADM-001","Blerim","Gashi","admin@fshn.edu.al","adminpass",3,"Aktive"));
        adminët.add(new AdminSistemi("ADM-002","Flutura","Lleshi","admin2@fshn.edu.al","admin2pw",2,"Aktive"));

        officerët.add(new SecurityOfficer("SEC-001","Arjola","Dema","secoff@fshn.edu.al","secpass",3,"Aktive","CISSP"));

        sulmuesit.add(new Sulmuesi("ATK-001","85.204.33.12","BruteForce",8,true,"Russia","python-requests/2.28"));
        sulmuesit.add(new Sulmuesi("ATK-002","85.100.22.3","SQLInjection",3,true,"China","curl/7.68.0"));
        sulmuesit.add(new Sulmuesi("ATK-003","172.16.0.8","Phishing",1,false,"Unknown","Mozilla/5.0"));

        idsEngines.add(new IDSEngine("IDS-001",true,5,847,"3.2.1","Normal"));
    }

    // --- FORM BUILDERS ---
    JPanel buildStudentForm(java.util.List<Student> list, DefaultTableModel model, Runnable refresh) {
        JPanel p = formCard("Krijo objekt Student");
        JTextField idF    = fld("STU-001");
        JTextField emriF  = fld("Ardit");
        JTextField mbiF   = fld("Hoxha");
        JTextField emlF   = fld("a.hoxha@fshn.edu.al");
        JPasswordField pwF = new JPasswordField("pass123"); styleInp(pwF);
        JComboBox<String> depCB  = combo(new String[]{"Informatike","Matematike","Fizike","Kimi","Biologji"});
        JComboBox<String> statCB = combo(new String[]{"Aktive","Locked","UnderReview","Terminated"});
        addFormRow(p,"studentId :",    idF);
        addFormRow(p,"emri :",         emriF);
        addFormRow(p,"mbiemri :",      mbiF);
        addFormRow(p,"email :",        emlF);
        addFormRow(p,"fjalëkalimi :",  pwF);
        addFormRow(p,"departamenti :", depCB);
        addFormRow(p,"gjendje :",      statCB);
        p.add(vg(10));
        JButton add = bigBtn("+ Krijo Student", BLU, BLU2);
        add.addActionListener(e -> {
            if(emriF.getText().trim().isEmpty()||idF.getText().trim().isEmpty()){
                JOptionPane.showMessageDialog(this,"Plotëso ID dhe Emrin!","Gabim",JOptionPane.WARNING_MESSAGE); return;
            }
            Student s = new Student(idF.getText().trim(),emriF.getText().trim(),mbiF.getText().trim(),
                    emlF.getText().trim(),new String(pwF.getPassword()),(String)statCB.getSelectedItem(),(String)depCB.getSelectedItem());
            list.add(s); refresh.run(); refreshStatus(); refreshDashboard();
            logEvent("\u271a Student u krijua: " + s.getEmri() + " " + s.getMbiemri() + " [" + s.getStudentId() + "]");
            JOptionPane.showMessageDialog(this,"Objekti Student u krijua:\n"+s.toString(),"Objekt i ri",JOptionPane.INFORMATION_MESSAGE);
        });
        p.add(add);
        return p;
    }

    JPanel buildPedagogForm(java.util.List<Pedagog> list, DefaultTableModel model, Runnable refresh) {
        JPanel p = formCard("Krijo objekt Pedagog");
        JTextField idF    = fld("PED-001"); JTextField emriF = fld("Petrit"); JTextField mbiF = fld("Marku");
        JTextField emlF   = fld("p.marku@fshn.edu.al");
        JPasswordField pwF = new JPasswordField("pass456"); styleInp(pwF);
        JComboBox<String> titCB  = combo(new String[]{"Prof. Dr.","Dr.","Asoc. Prof.","Lekt."});
        JComboBox<String> depCB  = combo(new String[]{"Informatike","Matematike","Fizike","Kimi","Biologji"});
        JComboBox<String> statCB = combo(new String[]{"Aktive","Locked","UnderReview"});
        addFormRow(p,"pedagogId :",      idF);
        addFormRow(p,"emri :",           emriF);
        addFormRow(p,"mbiemri :",        mbiF);
        addFormRow(p,"email :",          emlF);
        addFormRow(p,"fjalëkalimi :",    pwF);
        addFormRow(p,"titulliAkademik :",titCB);
        addFormRow(p,"departamenti :",   depCB);
        addFormRow(p,"gjendje :",        statCB);
        p.add(vg(10));
        JButton add = bigBtn("+ Krijo Pedagog", PUR, PUR2);
        add.addActionListener(e -> {
            if(emriF.getText().trim().isEmpty()){
                JOptionPane.showMessageDialog(this,"Plotëso Emrin!","Gabim",JOptionPane.WARNING_MESSAGE); return;
            }
            Pedagog ped = new Pedagog(idF.getText().trim(),emriF.getText().trim(),mbiF.getText().trim(),
                    emlF.getText().trim(),new String(pwF.getPassword()),(String)depCB.getSelectedItem(),(String)titCB.getSelectedItem(),(String)statCB.getSelectedItem());
            list.add(ped); refresh.run(); refreshStatus(); refreshDashboard();
            logEvent("\u271a Pedagog u krijua: " + ped.getTitulliAkademik() + " " + ped.getEmri() + " [" + ped.getPedagogId() + "]");
            JOptionPane.showMessageDialog(this,"Objekti Pedagog u krijua:\n"+ped.toString(),"Objekt i ri",JOptionPane.INFORMATION_MESSAGE);
        });
        p.add(add);
        return p;
    }

    JPanel buildAdminForm(java.util.List<AdminSistemi> list, DefaultTableModel model, Runnable refresh) {
        JPanel p = formCard("Krijo objekt AdminSistemi");
        JTextField idF    = fld("ADM-001"); JTextField emriF = fld("Blerim"); JTextField mbiF = fld("Gashi");
        JTextField emlF   = fld("admin@fshn.edu.al");
        JPasswordField pwF = new JPasswordField("adminpass"); styleInp(pwF);
        JComboBox<String> nivCB  = combo(new String[]{"1 \u2014 ReadOnly","2 \u2014 Standard","3 \u2014 Superadmin"});
        JComboBox<String> statCB = combo(new String[]{"Aktive","Locked"});
        addFormRow(p,"adminId :",          idF);
        addFormRow(p,"emri :",             emriF);
        addFormRow(p,"mbiemri :",          mbiF);
        addFormRow(p,"email :",            emlF);
        addFormRow(p,"fjalëkalimi :",      pwF);
        addFormRow(p,"nivelPrivilegjeve :",nivCB);
        addFormRow(p,"gjendje :",          statCB);
        p.add(vg(10));
        JButton add = bigBtn("+ Krijo Admin", TEL, TEL2);
        add.addActionListener(e -> {
            if(emriF.getText().trim().isEmpty()){
                JOptionPane.showMessageDialog(this,"Plotëso Emrin!","Gabim",JOptionPane.WARNING_MESSAGE); return;
            }
            AdminSistemi a = new AdminSistemi(idF.getText().trim(),emriF.getText().trim(),mbiF.getText().trim(),
                    emlF.getText().trim(),new String(pwF.getPassword()),nivCB.getSelectedIndex()+1,(String)statCB.getSelectedItem());
            list.add(a); refresh.run(); refreshStatus(); refreshDashboard();
            logEvent("\u271a Admin u krijua: " + a.getEmri() + " [" + a.getNivelLabel() + "]");
            JOptionPane.showMessageDialog(this,"Objekti AdminSistemi u krijua:\n"+a.toString(),"Objekt i ri",JOptionPane.INFORMATION_MESSAGE);
        });
        p.add(add);
        return p;
    }

    JPanel buildOfficerForm(java.util.List<SecurityOfficer> list, DefaultTableModel model, Runnable refresh) {
        JPanel p = formCard("Krijo objekt SecurityOfficer");
        JTextField idF    = fld("SEC-001"); JTextField emriF = fld("Arjola"); JTextField mbiF = fld("Dema");
        JTextField emlF   = fld("secoff@fshn.edu.al");
        JPasswordField pwF = new JPasswordField("secpass"); styleInp(pwF);
        JComboBox<String> nivCB  = combo(new String[]{"1 \u2014 Viewer","2 \u2014 Analyst","3 \u2014 Chief"});
        JComboBox<String> certCB = combo(new String[]{"CISSP","CEH","CISM","CompTIA Security+","ISO 27001"});
        JComboBox<String> statCB = combo(new String[]{"Aktive","Locked"});
        addFormRow(p,"officerId :",      idF);
        addFormRow(p,"emri :",           emriF);
        addFormRow(p,"mbiemri :",        mbiF);
        addFormRow(p,"email :",          emlF);
        addFormRow(p,"fjalëkalimi :",    pwF);
        addFormRow(p,"nivelAutorizimi :",nivCB);
        addFormRow(p,"certifikimi :",    certCB);
        addFormRow(p,"gjendje :",        statCB);
        p.add(vg(10));
        JButton add = bigBtn("+ Krijo SecurityOfficer", AMB, AMB2);
        add.addActionListener(e -> {
            if(emriF.getText().trim().isEmpty()){
                JOptionPane.showMessageDialog(this,"Plotëso Emrin!","Gabim",JOptionPane.WARNING_MESSAGE); return;
            }
            SecurityOfficer o = new SecurityOfficer(idF.getText().trim(),emriF.getText().trim(),mbiF.getText().trim(),
                    emlF.getText().trim(),new String(pwF.getPassword()),nivCB.getSelectedIndex()+1,(String)statCB.getSelectedItem(),(String)certCB.getSelectedItem());
            list.add(o); refresh.run(); refreshStatus(); refreshDashboard();
            logEvent("\u271a SecurityOfficer u krijua: " + o.getEmri() + " [" + o.getCertifikimi() + "]");
            JOptionPane.showMessageDialog(this,"Objekti SecurityOfficer u krijua:\n"+o.toString(),"Objekt i ri",JOptionPane.INFORMATION_MESSAGE);
        });
        p.add(add);
        return p;
    }

    JPanel buildSulmuesForm(java.util.List<Sulmuesi> list, DefaultTableModel model, Runnable refresh) {
        JPanel p = formCard("Regjistro objekt Sulmuesi");
        JTextField idF    = fld("ATK-001"); JTextField ipF = fld("85.204.33.12");
        JComboBox<String> tipCB = combo(new String[]{"BruteForce","SQLInjection","Phishing","DDoS","XSS","Spoofing"});
        JSpinner tentS = new JSpinner(new SpinnerNumberModel(1,1,1000,1));
        tentS.setBackground(CRD2); tentS.setForeground(TXT); tentS.setFont(FL);
        JTextField vendF  = fld("Russia"); JTextField uaF = fld("curl/7.68.0");
        JCheckBox bllCB   = new JCheckBox("eshteIBllokuar"); bllCB.setOpaque(false); bllCB.setForeground(TXT2); bllCB.setFont(FL);
        addFormRow(p,"sulmuesId :",      idF);
        addFormRow(p,"ip :",             ipF);
        addFormRow(p,"tipiSulmit :",     tipCB);
        addFormRow(p,"numTentativave :", tentS);
        addFormRow(p,"vendOrigjines :",  vendF);
        addFormRow(p,"userAgent :",      uaF);
        p.add(vg(4)); p.add(bllCB); p.add(vg(8));
        JButton add = bigBtn("+ Regjistro Sulmuesi", RED, RED2);
        add.addActionListener(e -> {
            if(ipF.getText().trim().isEmpty()){
                JOptionPane.showMessageDialog(this,"Plotëso IP-në!","Gabim",JOptionPane.WARNING_MESSAGE); return;
            }
            Sulmuesi s = new Sulmuesi(idF.getText().trim(),ipF.getText().trim(),(String)tipCB.getSelectedItem(),
                    (Integer)tentS.getValue(),bllCB.isSelected(),vendF.getText().trim(),uaF.getText().trim());
            list.add(s); refresh.run(); refreshStatus(); refreshDashboard();
            logEvent("\u271a Sulmuesi u regjistrua: " + s.getTipiSulmit() + " IP:" + s.getIp() + " [" + s.getSulmuesId() + "]");
            JOptionPane.showMessageDialog(this,"Objekti Sulmuesi u regjistrua:\n"+s.toString(),"Objekt i ri",JOptionPane.WARNING_MESSAGE);
        });
        p.add(add);
        return p;
    }

    JPanel buildIDSForm(java.util.List<IDSEngine> list, DefaultTableModel model, Runnable refresh) {
        JPanel p = formCard("Krijo objekt IDSEngine");
        JTextField idF = fld("IDS-001"); JTextField verF = fld("3.2.1");
        JSpinner pragS = new JSpinner(new SpinnerNumberModel(5,1,100,1));
        pragS.setBackground(CRD2); pragS.setForeground(TXT); pragS.setFont(FL);
        JSpinner kpS   = new JSpinner(new SpinnerNumberModel(500,1,10000,50));
        kpS.setBackground(CRD2); kpS.setForeground(TXT); kpS.setFont(FL);
        JComboBox<String> statCB = combo(new String[]{"Normal","Watch","Alert","Critical"});
        JCheckBox aktivCB = new JCheckBox("eshteAktiv",true); aktivCB.setOpaque(false); aktivCB.setForeground(TXT2); aktivCB.setFont(FL);
        addFormRow(p,"idsId :",            idF);
        addFormRow(p,"versioni :",         verF);
        addFormRow(p,"pragAlertit :",      pragS);
        addFormRow(p,"kerkesaPerMinute :", kpS);
        addFormRow(p,"statusDetektimit :", statCB);
        p.add(vg(4)); p.add(aktivCB); p.add(vg(8));
        JButton add = bigBtn("+ Krijo IDSEngine", YLW, YLW2);
        add.addActionListener(e -> {
            if(idF.getText().trim().isEmpty()){
                JOptionPane.showMessageDialog(this,"Plotëso ID!","Gabim",JOptionPane.WARNING_MESSAGE); return;
            }
            IDSEngine ids = new IDSEngine(idF.getText().trim(),aktivCB.isSelected(),(Integer)pragS.getValue(),(Integer)kpS.getValue(),verF.getText().trim(),(String)statCB.getSelectedItem());
            list.add(ids); refresh.run(); refreshStatus(); refreshDashboard();
            logEvent("\u271a IDSEngine u krijua: " + ids.getIdsId() + " v" + ids.getVersioni() + " [" + ids.getStatusDetektimit() + "]");
            JOptionPane.showMessageDialog(this,"Objekti IDSEngine u krijua:\n"+ids.toString(),"Objekt i ri",JOptionPane.INFORMATION_MESSAGE);
        });
        p.add(add);
        return p;
    }

    // --- REWIRE WITH SEARCH + INTERACTIVE DETAILS ---
    interface DetailFactory { JPanel make(int idx, Runnable onModify); }

    JPanel rewire(String type) {
        JPanel root = tabRoot();
        switch (type) {
            case "student": {
                String[] cols = {"ID","Emri","Mbiemri","Email","Roli","Departamenti","Gjendje","Tentativa"};
                DefaultTableModel m = new DefaultTableModel(cols,0){public boolean isCellEditable(int r,int c){return false;}};
                JTable t = styledTable(m);
                TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(m); t.setRowSorter(sorter);
                Runnable r = ()->{m.setRowCount(0);for(Student s:studentët)m.addRow(new Object[]{s.getStudentId(),s.getEmri(),s.getMbiemri(),s.getEmail(),s.getRoli(),s.getDepartamenti(),s.getGjendjaLlogarise(),s.getNumTentativave()});};
                r.run();
                root.add(splitPane(buildStudentForm(studentët,m,r), withSearch(t,sorter,new JScrollPane(t)), t, studentët, r, (idx,om)->studentDetailI(studentët.get(idx),studentët,om)));
                break;
            }
            case "pedagog": {
                String[] cols = {"ID","Titulli","Emri","Mbiemri","Email","Departamenti","Gjendje"};
                DefaultTableModel m = new DefaultTableModel(cols,0){public boolean isCellEditable(int r,int c){return false;}};
                JTable t = styledTable(m);
                TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(m); t.setRowSorter(sorter);
                Runnable r = ()->{m.setRowCount(0);for(Pedagog pg:pedagogët)m.addRow(new Object[]{pg.getPedagogId(),pg.getTitulliAkademik(),pg.getEmri(),pg.getMbiemri(),pg.getEmail(),pg.getDepartamenti(),pg.getGjendjaLlogarise()});};
                r.run();
                root.add(splitPane(buildPedagogForm(pedagogët,m,r), withSearch(t,sorter,new JScrollPane(t)), t, pedagogët, r, (idx,om)->pedagogDetailI(pedagogët.get(idx),pedagogët,om)));
                break;
            }
            case "admin": {
                String[] cols = {"ID","Emri","Mbiemri","Email","Roli","Nivel","Gjendje"};
                DefaultTableModel m = new DefaultTableModel(cols,0){public boolean isCellEditable(int r,int c){return false;}};
                JTable t = styledTable(m);
                TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(m); t.setRowSorter(sorter);
                Runnable r = ()->{m.setRowCount(0);for(AdminSistemi a:adminët)m.addRow(new Object[]{a.getAdminId(),a.getEmri(),a.getMbiemri(),a.getEmail(),a.getRoli(),a.getNivelLabel(),a.getGjendjaLlogarise()});};
                r.run();
                root.add(splitPane(buildAdminForm(adminët,m,r), withSearch(t,sorter,new JScrollPane(t)), t, adminët, r, (idx,om)->adminDetailI(adminët.get(idx),adminët,om)));
                break;
            }
            case "officer": {
                String[] cols = {"ID","Emri","Mbiemri","Email","Nivel","Certifikim","Gjendje"};
                DefaultTableModel m = new DefaultTableModel(cols,0){public boolean isCellEditable(int r,int c){return false;}};
                JTable t = styledTable(m);
                TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(m); t.setRowSorter(sorter);
                Runnable r = ()->{m.setRowCount(0);for(SecurityOfficer o:officerët)m.addRow(new Object[]{o.getOfficerId(),o.getEmri(),o.getMbiemri(),o.getEmail(),o.getNivelLabel(),o.getCertifikimi(),o.getGjendjaLlogarise()});};
                r.run();
                root.add(splitPane(buildOfficerForm(officerët,m,r), withSearch(t,sorter,new JScrollPane(t)), t, officerët, r, (idx,om)->officerDetailI(officerët.get(idx),officerët,om)));
                break;
            }
            case "sulmues": {
                String[] cols = {"ID","IP","Tipi Sulmit","Tentativa","Vendi","Bllokuar","Timestamp"};
                DefaultTableModel m = new DefaultTableModel(cols,0){public boolean isCellEditable(int r,int c){return false;}};
                JTable t = styledTable(m);
                TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(m); t.setRowSorter(sorter);
                Runnable r = ()->{m.setRowCount(0);for(Sulmuesi s:sulmuesit)m.addRow(new Object[]{s.getSulmuesId(),s.getIp(),s.getTipiSulmit(),s.getNumTentativave(),s.getVendOrigjines(),s.isEshteIBllokuar()?"\u2716 Po":"\u2714 Jo",s.getTimestampSulm()==null?"\u2014":SDF.format(s.getTimestampSulm())});};
                r.run();
                root.add(splitPane(buildSulmuesForm(sulmuesit,m,r), withSearch(t,sorter,new JScrollPane(t)), t, sulmuesit, r, (idx,om)->sulmuesDetailI(sulmuesit.get(idx),sulmuesit,om)));
                break;
            }
            case "ids": {
                String[] cols = {"ID","Versioni","Aktiv","Prag","Kerk/min","IP Blok.","Status"};
                DefaultTableModel m = new DefaultTableModel(cols,0){public boolean isCellEditable(int r,int c){return false;}};
                JTable t = styledTable(m);
                TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(m); t.setRowSorter(sorter);
                Runnable r = ()->{m.setRowCount(0);for(IDSEngine i:idsEngines)m.addRow(new Object[]{i.getIdsId(),i.getVersioni(),i.isEshteAktiv()?"\u25cf Aktiv":"\u25cb Off",i.getPragAlertit(),i.getKerkesaPerMinute(),i.getIpBllokuaraTotale(),i.getStatusDetektimit()});};
                r.run();
                root.add(splitPane(buildIDSForm(idsEngines,m,r), withSearch(t,sorter,new JScrollPane(t)), t, idsEngines, r, (idx,om)->idsDetailI(idsEngines.get(idx),idsEngines,om)));
                break;
            }
        }
        return root;
    }

    JComponent withSearch(JTable t, TableRowSorter<DefaultTableModel> sorter, JScrollPane ts) {
        JTextField sf = fld("");
        sf.getDocument().addDocumentListener(new DocumentListener() {
            private void filter() {
                String q = sf.getText().trim();
                if (q.isEmpty()) { sorter.setRowFilter(null); return; }
                try { sorter.setRowFilter(RowFilter.regexFilter("(?i)" + Pattern.quote(q))); }
                catch (Exception ex) { sorter.setRowFilter(null); }
            }
            public void insertUpdate(DocumentEvent e) { filter(); }
            public void removeUpdate(DocumentEvent e) { filter(); }
            public void changedUpdate(DocumentEvent e) { filter(); }
        });
        JPanel searchBar = new JPanel(new BorderLayout(6, 0));
        searchBar.setBackground(PNL);
        searchBar.setBorder(BorderFactory.createCompoundBorder(
            new MatteBorder(0, 0, 1, 0, BRD),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)));
        searchBar.add(lbl("\uD83D\uDD0D ", TXT2, FS), BorderLayout.WEST);
        searchBar.add(sf, BorderLayout.CENTER);
        JPanel wrapper = new JPanel(new BorderLayout(0, 0));
        wrapper.setBackground(BG);
        wrapper.add(searchBar, BorderLayout.NORTH);
        ts.getViewport().setBackground(CRD);
        ts.setBorder(BorderFactory.createLineBorder(BRD));
        wrapper.add(ts, BorderLayout.CENTER);
        return wrapper;
    }

    JComponent splitPane(JPanel form, JComponent tableArea, JTable t,
                         java.util.List<?> list, Runnable tableRefresh, DetailFactory factory) {
        JScrollPane formScroll = new JScrollPane(form);
        formScroll.setBorder(BorderFactory.createEmptyBorder());
        formScroll.getViewport().setBackground(BG);
        formScroll.setBackground(BG);
        formScroll.setPreferredSize(new Dimension(250, 0));

        JPanel placeholder = new JPanel(new BorderLayout());
        placeholder.setBackground(CRD);
        placeholder.add(lbl("  Kliko nje rresht ne tabele per detaje...", TXT2, FS), BorderLayout.CENTER);

        JPanel detailHolder = new JPanel(new BorderLayout());
        detailHolder.setBackground(CRD);
        detailHolder.add(placeholder, BorderLayout.CENTER);

        final Runnable[] onModify = {null};
        onModify[0] = () -> {
            tableRefresh.run();
            t.clearSelection();
            detailHolder.removeAll();
            detailHolder.add(placeholder, BorderLayout.CENTER);
            detailHolder.revalidate();
            detailHolder.repaint();
        };

        t.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && t.getSelectedRow() >= 0) {
                int modelIdx = (t.getRowSorter() != null) ? t.convertRowIndexToModel(t.getSelectedRow()) : t.getSelectedRow();
                if (modelIdx >= 0 && modelIdx < list.size()) {
                    detailHolder.removeAll();
                    JScrollPane ds = new JScrollPane(factory.make(modelIdx, onModify[0]));
                    ds.setBorder(BorderFactory.createEmptyBorder());
                    ds.getViewport().setBackground(CRD);
                    detailHolder.add(ds, BorderLayout.CENTER);
                    detailHolder.revalidate();
                    detailHolder.repaint();
                }
            }
        });

        JSplitPane tableDetail = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tableArea, detailHolder);
        tableDetail.setDividerLocation(210);
        tableDetail.setDividerSize(4);
        tableDetail.setBorder(BorderFactory.createEmptyBorder());
        tableDetail.setBackground(BG);

        JPanel center = new JPanel(new BorderLayout());
        center.setBackground(BG);
        center.setBorder(BorderFactory.createEmptyBorder(12, 8, 12, 12));
        center.add(tableDetail, BorderLayout.CENTER);

        JSplitPane main = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, formScroll, center);
        main.setDividerLocation(250);
        main.setDividerSize(4);
        main.setBorder(BorderFactory.createEmptyBorder());
        main.setBackground(BG);
        return main;
    }

    // --- UI HELPERS ---
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
            new MatteBorder(0, 0, 0, 1, BRD),
            BorderFactory.createEmptyBorder(14, 14, 14, 14)));
        JPanel hdr = new JPanel(new BorderLayout());
        hdr.setBackground(CRD2); hdr.setBorder(BorderFactory.createEmptyBorder(6,8,6,8));
        hdr.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        JLabel tl = lbl(title, TXT, FT); tl.setAlignmentX(LEFT_ALIGNMENT);
        hdr.add(tl, BorderLayout.CENTER); p.add(hdr); p.add(vg(12));
        return p;
    }

    JPanel detailCard(String title) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(CRD);
        p.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 12));
        p.add(lbl(title, TXT, FT)); p.add(vg(6));
        JSeparator sep = new JSeparator(); sep.setForeground(BRD);
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1)); p.add(sep); p.add(vg(8));
        return p;
    }

    void addFormRow(JPanel p, String label, JComponent field) {
        JLabel lbl = lbl(label, TXT2, FS); lbl.setAlignmentX(LEFT_ALIGNMENT);
        p.add(lbl); p.add(vg(2));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
        field.setAlignmentX(LEFT_ALIGNMENT);
        p.add(field); p.add(vg(7));
    }

    void addDetail(JPanel p, String key, String val) {
        JPanel row = new JPanel(new BorderLayout(8, 0));
        row.setBackground(CRD2); row.setOpaque(true);
        row.setBorder(BorderFactory.createEmptyBorder(3, 8, 3, 8));
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 24));
        row.add(lbl(key, TXT2, FM), BorderLayout.WEST);
        JLabel v = lbl(val, TXT, FM); v.setHorizontalAlignment(SwingConstants.RIGHT);
        row.add(v, BorderLayout.EAST);
        row.setAlignmentX(LEFT_ALIGNMENT);
        p.add(row); p.add(vg(2));
    }

    void addActionSection(JPanel p) {
        p.add(vg(8));
        JSeparator sep = new JSeparator(); sep.setForeground(BRD);
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1)); p.add(sep); p.add(vg(6));
        JLabel lbl = lbl("\u25b8 Veprime:", TXT2, FS); lbl.setAlignmentX(LEFT_ALIGNMENT); p.add(lbl); p.add(vg(6));
    }

    JButton actionBtn(String text, Color fg) {
        JButton b = new JButton(text);
        b.setForeground(fg); b.setBackground(CRD2); b.setFont(FS);
        b.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BRD),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
        b.setAlignmentX(LEFT_ALIGNMENT);
        return b;
    }

    JButton dashBtn(String text, Color fg, Color bg) {
        JButton b = new JButton(text);
        b.setForeground(fg); b.setBackground(bg); b.setFont(FS);
        b.setBorder(BorderFactory.createEmptyBorder(6, 14, 6, 14));
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }

    JTable styledTable(DefaultTableModel model) {
        JTable t = new JTable(model);
        t.setBackground(CRD); t.setForeground(TXT); t.setFont(FL); t.setRowHeight(28);
        t.setGridColor(BRD); t.setSelectionBackground(new Color(24,95,165,110)); t.setSelectionForeground(TXT);
        t.setShowHorizontalLines(true); t.setShowVerticalLines(false);
        t.getTableHeader().setBackground(CRD2); t.getTableHeader().setForeground(TXT2);
        t.getTableHeader().setFont(FS); t.getTableHeader().setBorder(new MatteBorder(0,0,1,0,BRD));
        t.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
            public Component getTableCellRendererComponent(JTable tb,Object v,boolean sel,boolean foc,int r,int c){
                super.getTableCellRendererComponent(tb,v,sel,foc,r,c);
                setBackground(sel?new Color(24,95,165,110):(r%2==0?CRD:CRD2));
                setForeground(TXT); setFont(FL); setBorder(BorderFactory.createEmptyBorder(0,8,0,8));
                String s=v==null?"":v.toString();
                if(s.contains("Locked")||s.contains("\u2716")||s.contains("Bllokuar")||s.contains("Critical")) setForeground(RED2);
                else if(s.contains("Watch")||s.contains("Alert")||s.contains("UnderReview")) setForeground(AMB2);
                else if(s.contains("Aktive")||s.contains("Aktiv")||s.contains("Normal")||s.contains("\u25cf")||s.contains("\u2714")) setForeground(TEL2);
                return this;
            }
        });
        return t;
    }

    JTextField fld(String txt) { JTextField f = new JTextField(txt); styleInp(f); return f; }
    void styleInp(JTextField f) {
        f.setBackground(CRD2); f.setForeground(TXT); f.setCaretColor(TXT); f.setFont(FL);
        f.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(BRD),BorderFactory.createEmptyBorder(3,6,3,6)));
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

    // --- MAIN ---
    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); } catch(Exception ignore){}
        SwingUtilities.invokeLater(() -> new FSHNObjectManager().setVisible(true));
    }
}
