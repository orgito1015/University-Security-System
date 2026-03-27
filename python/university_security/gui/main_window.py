"""Tkinter GUI for the University Security System.

Launch with::

    python -m university_security.gui
"""

from __future__ import annotations

import tkinter as tk
from tkinter import font as tkfont
from tkinter import messagebox, ttk

from ..models import AdminSistemi, IDSEngine, Pedagog, SecurityOfficer, Student, Sulmuesi

# ── Colour palette (mirrors FSHNObjectManager.java colours) ──────────────────
BG    = "#0C111B"
PNL   = "#121826"
CRD   = "#182133"
CRD2  = "#1E293F"
BLU   = "#145096"
BLU2  = "#3789DD"
TEL   = "#0C5F4A"
TEL2  = "#19946E"
RED   = "#821E1E"
RED2  = "#E24B4A"
AMB   = "#825005"
AMB2  = "#EF9F27"
PUR   = "#46379A"
PUR2  = "#8278E6"
YLW   = "#4E4E0F"
YLW2  = "#C8BE50"
TXT   = "#E6E4DA"
TXT2  = "#969488"
TXT3  = "#46443E"
BRD   = "#26344E"


def _hex(colour: str) -> str:
    """Return *colour* as-is (already a hex string)."""
    return colour


# ── Seed data (mirrors __main__.py seed helpers) ──────────────────────────────

def _seed_students() -> list[Student]:
    return [
        Student("S001", "Arta",   "Kelmendi", "arta.kelmendi@fshn.edu.al",  "pass123", "Aktive",      "Informatike"),
        Student("S002", "Besar",  "Morina",   "besar.morina@fshn.edu.al",   "qwerty",  "Locked",      "Matematike"),
        Student("S003", "Donika", "Hoxha",    "donika.hoxha@fshn.edu.al",   "abc456",  "UnderReview", "Fizike"),
        Student("S004", "Ermjon", "Berisha",  "ermjon.berisha@fshn.edu.al", "letmein", "Aktive",      "Kimi"),
        Student("S005", "Fatmir", "Sadiku",   "fatmir.sadiku@fshn.edu.al",  "fshn2024","Aktive",      "Informatike"),
    ]


def _seed_pedagoget() -> list[Pedagog]:
    return [
        Pedagog("P001", "Gentian", "Mema",    "g.mema@fshn.edu.al",    "profpass", "Informatike", "Prof. Dr.",  "Aktive"),
        Pedagog("P002", "Irena",   "Dervishi","i.dervishi@fshn.edu.al","drpass",   "Matematike",  "Dr.",        "Aktive"),
        Pedagog("P003", "Kujtim",  "Cela",    "k.cela@fshn.edu.al",   "asocpass", "Fizike",       "Asoc. Prof.","Aktive"),
    ]


def _seed_adminets() -> list[AdminSistemi]:
    return [
        AdminSistemi("A001", "Leotrim", "Gashi",  "l.gashi@fshn.edu.al",   "admin123", 3, "Aktive"),
        AdminSistemi("A002", "Mirela",  "Tirana", "m.tirana@fshn.edu.al",  "std456",   2, "Aktive"),
        AdminSistemi("A003", "Nikolin", "Bushati","n.bushati@fshn.edu.al", "readonly", 1, "Aktive"),
    ]


def _seed_officers() -> list[SecurityOfficer]:
    return [
        SecurityOfficer("O001", "Oltian",  "Rama", "o.rama@fshn.edu.al",  "soc123", 3, "Aktive", "CISSP"),
        SecurityOfficer("O002", "Pranvera","Lika",  "p.lika@fshn.edu.al",  "ceh456", 2, "Aktive", "CEH"),
        SecurityOfficer("O003", "Qemal",   "Gjata","q.gjata@fshn.edu.al", "sec789", 1, "Aktive", "CompTIA Security+"),
    ]


def _seed_sulmuesit() -> list[Sulmuesi]:
    return [
        Sulmuesi("ATK001", "192.168.1.100", "BruteForce",  5, False, "Albania", "Mozilla/5.0"),
        Sulmuesi("ATK002", "10.0.0.99",     "SQLInjection",3, True,  "Romania", "curl/7.68"),
        Sulmuesi("ATK003", "172.16.0.50",   "Phishing",    1, False, "Unknown", "python-requests/2.28"),
    ]


def _seed_ids() -> list[IDSEngine]:
    return [
        IDSEngine("IDS001", True,  100, 45,  "2.4.1", "Normal"),
        IDSEngine("IDS002", True,  200, 190, "2.4.1", "Watch"),
        IDSEngine("IDS003", False, 150, 0,   "2.3.0", "Normal"),
    ]


# ── Helpers ───────────────────────────────────────────────────────────────────

def _state_colour(state: str) -> str:
    return {
        "Aktive":      TEL2,
        "Locked":      RED2,
        "UnderReview": AMB2,
        "AKTIV":       TEL2,
        "OFF":         TXT2,
        "Normal":      TEL2,
        "Watch":       AMB2,
        "Alert":       RED2,
        "Critical":    RED2,
    }.get(state, TXT)


# ── Main window ───────────────────────────────────────────────────────────────

class MainWindow(tk.Tk):
    """Dark-themed Tkinter main window with one tab per domain class."""

    def __init__(self) -> None:
        super().__init__()
        self.title("FSHN — Sistema e Sigurisë Universitare | Command Center v2.0")
        self.configure(bg=BG)
        self.geometry("1200x750")
        self.minsize(900, 580)

        # seed data
        self.students  = _seed_students()
        self.pedagoget = _seed_pedagoget()
        self.adminets  = _seed_adminets()
        self.officers  = _seed_officers()
        self.sulmuesit = _seed_sulmuesit()
        self.ids_list  = _seed_ids()

        self._event_log: list[str] = []

        self._build_fonts()
        self._build_top_bar()
        self._build_notebook()
        self._build_status_bar()

    # ── Font setup ────────────────────────────────────────────────────────────

    def _build_fonts(self) -> None:
        self.ft  = tkfont.Font(family="TkDefaultFont", size=10, weight="bold")
        self.fl  = tkfont.Font(family="TkDefaultFont", size=9)
        self.fs  = tkfont.Font(family="TkDefaultFont", size=8)
        self.fh  = tkfont.Font(family="TkDefaultFont", size=14, weight="bold")
        self.fm  = tkfont.Font(family="TkFixedFont",   size=9)

    # ── Top bar ───────────────────────────────────────────────────────────────

    def _build_top_bar(self) -> None:
        bar = tk.Frame(self, bg=PNL, height=52)
        bar.pack(fill=tk.X, side=tk.TOP)
        bar.pack_propagate(False)

        left = tk.Frame(bar, bg=PNL)
        left.pack(side=tk.LEFT, padx=14, pady=10)
        tk.Label(left, text="🛡", bg=PNL, fg=TXT, font=self.fh).pack(side=tk.LEFT, padx=(0, 6))
        titles = tk.Frame(left, bg=PNL)
        titles.pack(side=tk.LEFT)
        tk.Label(titles, text="FSHN — Sistema e Sigurisë Universitare",
                 bg=PNL, fg=TXT, font=self.ft).pack(anchor="w")
        tk.Label(titles, text="Menaxhimi i Aktorëve UML  ·  Simulim Siguries  ·  v2.0",
                 bg=PNL, fg=TXT2, font=self.fs).pack(anchor="w")

        right = tk.Frame(bar, bg=PNL)
        right.pack(side=tk.RIGHT, padx=12)
        for label, colour in [("6 Klasa UML", BLU2), ("Python/Tkinter", TEL2), ("FSHN 2024", PUR2)]:
            tk.Label(right, text=f" {label} ", bg=colour, fg=TXT, font=self.fs,
                     padx=6, pady=2, relief=tk.FLAT).pack(side=tk.LEFT, padx=3)

    # ── Notebook (tabbed pane) ────────────────────────────────────────────────

    def _build_notebook(self) -> None:
        style = ttk.Style(self)
        style.theme_use("default")
        style.configure("TNotebook",       background=BG,  borderwidth=0)
        style.configure("TNotebook.Tab",   background=CRD, foreground=TXT2,
                        padding=[10, 4], font=("TkDefaultFont", 9, "bold"))
        style.map("TNotebook.Tab",
                  background=[("selected", CRD2)],
                  foreground=[("selected", BLU2)])

        nb = ttk.Notebook(self)
        nb.pack(fill=tk.BOTH, expand=True, padx=0, pady=0)

        nb.add(self._dashboard_tab(nb),     text="📊 Dashboard")
        nb.add(self._student_tab(nb),       text="👤 Student")
        nb.add(self._pedagog_tab(nb),       text="🎓 Pedagog")
        nb.add(self._admin_tab(nb),         text="🔧 Admin Sistemi")
        nb.add(self._officer_tab(nb),       text="🛡 Security Officer")
        nb.add(self._sulmuesi_tab(nb),      text="💀 Sulmuesi")
        nb.add(self._ids_tab(nb),           text="🤖 IDS Engine")

        self._notebook = nb

    # ── Status bar ────────────────────────────────────────────────────────────

    def _build_status_bar(self) -> None:
        bar = tk.Frame(self, bg=PNL, height=24)
        bar.pack(fill=tk.X, side=tk.BOTTOM)
        bar.pack_propagate(False)

        tk.Label(bar, text="● Aktiv", bg=PNL, fg=TEL2, font=self.fs).pack(side=tk.LEFT, padx=8)
        for label, colour, count in [
            ("Student",  BLU2, len(self.students)),
            ("Pedagog",  PUR2, len(self.pedagoget)),
            ("Admin",    TEL2, len(self.adminets)),
            ("Officer",  AMB2, len(self.officers)),
            ("Sulmues",  RED2, len(self.sulmuesit)),
            ("IDS",      YLW2, len(self.ids_list)),
        ]:
            tk.Label(bar, text=f"  {label}({count})", bg=PNL, fg=colour,
                     font=self.fm).pack(side=tk.LEFT)

    # ── Dashboard tab ─────────────────────────────────────────────────────────

    def _dashboard_tab(self, parent: tk.Widget) -> tk.Frame:
        frame = tk.Frame(parent, bg=BG)

        # Summary cards row
        cards_row = tk.Frame(frame, bg=BG)
        cards_row.pack(fill=tk.X, padx=16, pady=(16, 8))

        card_data = [
            ("👤 Students",         len(self.students),  BLU2),
            ("🎓 Pedagogë",         len(self.pedagoget), PUR2),
            ("🔧 Adminë",           len(self.adminets),  TEL2),
            ("🛡 Officers",         len(self.officers),  AMB2),
            ("💀 Sulmues",          len(self.sulmuesit), RED2),
            ("🤖 IDS Engines",      len(self.ids_list),  YLW2),
        ]

        for title, count, colour in card_data:
            card = tk.Frame(cards_row, bg=CRD, padx=16, pady=10)
            card.pack(side=tk.LEFT, padx=6, fill=tk.Y)
            tk.Label(card, text=str(count), bg=CRD, fg=colour,
                     font=tkfont.Font(family="TkDefaultFont", size=20, weight="bold")).pack()
            tk.Label(card, text=title, bg=CRD, fg=TXT2, font=self.fs).pack()

        # IDS status row
        ids_row = tk.Frame(frame, bg=BG)
        ids_row.pack(fill=tk.X, padx=16, pady=(0, 8))
        tk.Label(ids_row, text="IDS Status:", bg=BG, fg=TXT2, font=self.fs).pack(side=tk.LEFT, padx=(0, 6))
        for ids in self.ids_list:
            state = "AKTIV" if ids.eshte_aktiv else "OFF"
            col   = _state_colour(state)
            tk.Label(ids_row, text=f"  {ids.ids_id}: {state} ({ids.status_detektimit})  ",
                     bg=CRD, fg=col, font=self.fm, padx=4).pack(side=tk.LEFT, padx=4)

        # Active threats row
        blocked = sum(1 for s in self.sulmuesit if s.eshte_i_bllokuar)
        tk.Label(frame, text=f"⚠  Sulmues aktiv: {len(self.sulmuesit) - blocked}   |   "
                              f"Të bllokuar: {blocked}",
                 bg=BG, fg=AMB2, font=self.ft).pack(anchor="w", padx=16, pady=4)

        # Log area
        tk.Label(frame, text="Event Log", bg=BG, fg=TXT2, font=self.fs).pack(anchor="w", padx=16)
        log_frame = tk.Frame(frame, bg=CRD)
        log_frame.pack(fill=tk.BOTH, expand=True, padx=16, pady=(0, 10))
        self._log_area = tk.Text(log_frame, bg=CRD, fg=TEL2, font=self.fm,
                                  state=tk.DISABLED, relief=tk.FLAT, padx=8, pady=6)
        scrollbar = tk.Scrollbar(log_frame, command=self._log_area.yview, bg=CRD2)
        self._log_area.configure(yscrollcommand=scrollbar.set)
        scrollbar.pack(side=tk.RIGHT, fill=tk.Y)
        self._log_area.pack(side=tk.LEFT, fill=tk.BOTH, expand=True)
        self._log_event("Sistema FSHN u inicializua — Command Center v2.0 (Python/Tkinter)")
        self._log_event(
            f"Demo data: {len(self.students)} students, {len(self.pedagoget)} pedagoge, "
            f"{len(self.adminets)} admin, {len(self.officers)} officers, "
            f"{len(self.sulmuesit)} sulmues, {len(self.ids_list)} IDS"
        )
        return frame

    def _log_event(self, msg: str) -> None:
        import datetime as _dt
        ts = _dt.datetime.now().strftime("%H:%M:%S")
        entry = f"[{ts}] {msg}\n"
        self._event_log.append(entry)
        if hasattr(self, "_log_area"):
            self._log_area.configure(state=tk.NORMAL)
            self._log_area.insert("1.0", entry)
            self._log_area.configure(state=tk.DISABLED)

    # ── Generic list-tab builder ──────────────────────────────────────────────

    def _list_tab(
        self,
        parent:   tk.Widget,
        title:    str,
        colour:   str,
        columns:  list[tuple[str, int]],   # (header, width)
        rows:     list[tuple],
        actions:  list[tuple[str, str, object]] | None = None,  # (label, colour, callback)
    ) -> tk.Frame:
        """Build a labelled list tab with an optional action-button row."""
        frame = tk.Frame(parent, bg=BG)

        # Header
        hdr = tk.Frame(frame, bg=PNL)
        hdr.pack(fill=tk.X, padx=0, pady=0)
        tk.Label(hdr, text=f"  {title}", bg=PNL, fg=colour,
                 font=self.ft, pady=8).pack(side=tk.LEFT)

        # Table
        tbl_frame = tk.Frame(frame, bg=BG)
        tbl_frame.pack(fill=tk.BOTH, expand=True, padx=12, pady=10)

        style = ttk.Style()
        style.configure("Dark.Treeview",
                        background=CRD, foreground=TXT, fieldbackground=CRD,
                        rowheight=24, font=("TkDefaultFont", 9))
        style.configure("Dark.Treeview.Heading",
                        background=CRD2, foreground=BLU2,
                        font=("TkDefaultFont", 9, "bold"))
        style.map("Dark.Treeview", background=[("selected", BLU)])

        col_ids = [c[0] for c in columns]
        tree = ttk.Treeview(tbl_frame, columns=col_ids, show="headings",
                            style="Dark.Treeview")
        for col, width in columns:
            tree.heading(col, text=col)
            tree.column(col, width=width, minwidth=40)

        for row in rows:
            tree.insert("", tk.END, values=row)

        vsb = ttk.Scrollbar(tbl_frame, orient=tk.VERTICAL, command=tree.yview)
        tree.configure(yscrollcommand=vsb.set)
        vsb.pack(side=tk.RIGHT, fill=tk.Y)
        tree.pack(side=tk.LEFT, fill=tk.BOTH, expand=True)

        # Action buttons (optional)
        if actions:
            btn_row = tk.Frame(frame, bg=BG)
            btn_row.pack(fill=tk.X, padx=12, pady=(0, 10))
            for label, btn_colour, callback in actions:
                tk.Button(
                    btn_row, text=label, bg=btn_colour, fg=TXT,
                    font=self.fs, relief=tk.FLAT, padx=10, pady=4,
                    cursor="hand2", command=callback,
                ).pack(side=tk.LEFT, padx=4)

        return frame

    # ── Individual tabs ───────────────────────────────────────────────────────

    def _student_tab(self, parent: tk.Widget) -> tk.Frame:
        cols = [
            ("ID", 70), ("Emri", 90), ("Mbiemri", 100),
            ("Email", 200), ("Departamenti", 120),
            ("Gjendja", 100), ("Tentativa", 80),
        ]
        rows = [
            (s.student_id, s.emri, s.mbiemri, s.email,
             s.departamenti, s.gjendja_llogarise, s.num_tentativave)
            for s in self.students
        ]
        return self._list_tab(parent, "👤  Student — Lista e Studentëve", BLU2, cols, rows,
                              actions=[
                                  ("➕ Shto Student",   BLU,  lambda: self._not_impl("Shto Student")),
                                  ("🔒 Bloko Llogarinë", RED, lambda: self._not_impl("Bloko Llogarinë")),
                                  ("🔓 Riaktivizo",     TEL,  lambda: self._not_impl("Riaktivizo")),
                              ])

    def _pedagog_tab(self, parent: tk.Widget) -> tk.Frame:
        cols = [
            ("ID", 70), ("Titulli", 90), ("Emri", 90), ("Mbiemri", 100),
            ("Email", 200), ("Departamenti", 120), ("Gjendja", 100),
        ]
        rows = [
            (p.pedagog_id, p.titulliAkademik, p.emri, p.mbiemri,
             p.email, p.departamenti, p.gjendja_llogarise)
            for p in self.pedagoget
        ]
        return self._list_tab(parent, "🎓  Pedagog — Stafi Akademik", PUR2, cols, rows,
                              actions=[
                                  ("➕ Shto Pedagog", PUR,  lambda: self._not_impl("Shto Pedagog")),
                                  ("📝 Fut Notë",     BLU,  lambda: self._not_impl("Fut Notë")),
                              ])

    def _admin_tab(self, parent: tk.Widget) -> tk.Frame:
        cols = [
            ("ID", 70), ("Emri", 90), ("Mbiemri", 100),
            ("Email", 200), ("Nivel", 80), ("Label", 100), ("Gjendja", 100),
        ]
        rows = [
            (a.admin_id, a.emri, a.mbiemri, a.email,
             a.nivel_privilegjeve, a.nivel_label(), a.gjendja_llogarise)
            for a in self.adminets
        ]
        return self._list_tab(parent, "🔧  Admin Sistemi — Administratorët", TEL2, cols, rows,
                              actions=[
                                  ("🚫 Bloko IP",        RED, lambda: self._not_impl("Bloko IP")),
                                  ("🔥 Konfiguro FW",   TEL,  lambda: self._not_impl("Konfiguro Firewall")),
                                  ("📋 Gjenero Raport",  BLU, lambda: self._not_impl("Gjenero Raport")),
                              ])

    def _officer_tab(self, parent: tk.Widget) -> tk.Frame:
        cols = [
            ("ID", 70), ("Emri", 90), ("Mbiemri", 100),
            ("Email", 200), ("Nivel", 80), ("Label", 90),
            ("Certifikimi", 160), ("Gjendja", 100),
        ]
        rows = [
            (o.officer_id, o.emri, o.mbiemri, o.email,
             o.nivel_autorizimi, o.nivel_label(),
             o.certifikimi, o.gjendja_llogarise)
            for o in self.officers
        ]
        return self._list_tab(parent, "🛡  Security Officer — Ekipi SOC", AMB2, cols, rows,
                              actions=[
                                  ("🔍 Analizo Incident", AMB, lambda: self._not_impl("Analizo Incident")),
                                  ("✅ Konfirmo Alert",   TEL, lambda: self._not_impl("Konfirmo Alert")),
                              ])

    def _sulmuesi_tab(self, parent: tk.Widget) -> tk.Frame:
        cols = [
            ("ID", 80), ("IP", 130), ("Tipi Sulmit", 120),
            ("Tentativa", 80), ("Bllokuar", 80),
            ("Vendorigjina", 90), ("User-Agent", 200),
        ]
        rows = [
            (s.sulmues_id, s.ip, s.tipi_sulmit,
             s.num_tentativave,
             "Po" if s.eshte_i_bllokuar else "Jo",
             s.vend_origjines, s.user_agent)
            for s in self.sulmuesit
        ]
        return self._list_tab(parent, "💀  Sulmuesi — Aktorët Kërcënues", RED2, cols, rows,
                              actions=[
                                  ("🚫 Bloko Sulmuesin", RED, lambda: self._not_impl("Bloko Sulmuesin")),
                                  ("📊 Analizo",         AMB, lambda: self._not_impl("Analizo Sulmuesin")),
                              ])

    def _ids_tab(self, parent: tk.Widget) -> tk.Frame:
        cols = [
            ("ID", 80), ("Versioni", 80), ("Aktiv", 60),
            ("Prag", 70), ("Kerkesa/min", 100),
            ("IP Bllokuara", 100), ("Status", 100),
        ]
        rows = [
            (i.ids_id, i.versioni,
             "Po" if i.eshte_aktiv else "Jo",
             i.prag_alertit, i.kerkesa_per_minute,
             i.ip_bllokuara_totale, i.status_detektimit)
            for i in self.ids_list
        ]
        return self._list_tab(parent, "🤖  IDS Engine — Sistemi i Detektimit", YLW2, cols, rows,
                              actions=[
                                  ("▶ Aktivizo IDS",   TEL, lambda: self._not_impl("Aktivizo IDS")),
                                  ("⏹ Çaktivizo IDS",  RED, lambda: self._not_impl("Çaktivizo IDS")),
                                  ("📋 Gjenero Log",   BLU, lambda: self._not_impl("Gjenero Log")),
                              ])

    # ── Utility ───────────────────────────────────────────────────────────────

    def _not_impl(self, action: str) -> None:
        messagebox.showinfo("Aksion", f"'{action}' — funksionalitet demo.")
        self._log_event(f"Aksion: {action}")


# ── Entry point ───────────────────────────────────────────────────────────────

def main() -> None:
    app = MainWindow()
    app.mainloop()


if __name__ == "__main__":
    main()
