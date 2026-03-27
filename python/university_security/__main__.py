"""Command-line demo for the University Security System.

Run with::

    python -m university_security
"""

from __future__ import annotations

from .models import AdminSistemi, IDSEngine, Pedagog, SecurityOfficer, Student, Sulmuesi


# ── Demo data (mirrors FSHNObjectManager.seedData()) ─────────────────────────

def _seed_students() -> list[Student]:
    return [
        Student("S001", "Arta",   "Kelmendi",  "arta.kelmendi@fshn.edu.al",   "pass123", "Aktive",      "Informatike"),
        Student("S002", "Besar",  "Morina",    "besar.morina@fshn.edu.al",    "qwerty",  "Locked",      "Matematike"),
        Student("S003", "Donika", "Hoxha",     "donika.hoxha@fshn.edu.al",    "abc456",  "UnderReview", "Fizike"),
        Student("S004", "Ermjon", "Berisha",   "ermjon.berisha@fshn.edu.al",  "letmein", "Aktive",      "Kimi"),
        Student("S005", "Fatmir", "Sadiku",    "fatmir.sadiku@fshn.edu.al",   "fshn2024","Aktive",      "Informatike"),
    ]


def _seed_pedagoget() -> list[Pedagog]:
    return [
        Pedagog("P001", "Gentian", "Mema",    "g.mema@fshn.edu.al",    "profpass",  "Informatike", "Prof. Dr.",  "Aktive"),
        Pedagog("P002", "Irena",   "Dervishi","i.dervishi@fshn.edu.al","drpass",    "Matematike",  "Dr.",        "Aktive"),
        Pedagog("P003", "Kujtim",  "Cela",    "k.cela@fshn.edu.al",   "asocpass",  "Fizike",      "Asoc. Prof.","Aktive"),
    ]


def _seed_adminets() -> list[AdminSistemi]:
    return [
        AdminSistemi("A001", "Leotrim", "Gashi",   "l.gashi@fshn.edu.al",   "admin123", 3, "Aktive"),
        AdminSistemi("A002", "Mirela",  "Tirana",  "m.tirana@fshn.edu.al",  "std456",   2, "Aktive"),
        AdminSistemi("A003", "Nikolin", "Bushati",  "n.bushati@fshn.edu.al", "readonly", 1, "Aktive"),
    ]


def _seed_officers() -> list[SecurityOfficer]:
    return [
        SecurityOfficer("O001", "Oltian",  "Rama",    "o.rama@fshn.edu.al",    "soc123", 3, "Aktive", "CISSP"),
        SecurityOfficer("O002", "Pranvera","Lika",    "p.lika@fshn.edu.al",    "ceh456", 2, "Aktive", "CEH"),
        SecurityOfficer("O003", "Qemal",   "Gjata",   "q.gjata@fshn.edu.al",   "sec789", 1, "Aktive", "CompTIA Security+"),
    ]


def _seed_sulmuesit() -> list[Sulmuesi]:
    return [
        Sulmuesi("ATK001", "192.168.1.100", "BruteForce",    5,  False, "Albania",  "Mozilla/5.0"),
        Sulmuesi("ATK002", "10.0.0.99",     "SQLInjection",  3,  True,  "Romania",  "curl/7.68"),
        Sulmuesi("ATK003", "172.16.0.50",   "Phishing",      1,  False, "Unknown",  "python-requests/2.28"),
    ]


def _seed_ids() -> list[IDSEngine]:
    return [
        IDSEngine("IDS001", True,  100, 45, "2.4.1", "Normal"),
        IDSEngine("IDS002", True,  200, 190, "2.4.1", "Watch"),
        IDSEngine("IDS003", False, 150, 0,  "2.3.0", "Normal"),
    ]


# ── Helpers ───────────────────────────────────────────────────────────────────

def _divider(title: str = "", char: str = "─", width: int = 70) -> None:
    if title:
        side = (width - len(title) - 2) // 2
        print(f"{char * side} {title} {char * (width - side - len(title) - 2)}")
    else:
        print(char * width)


def _section(header: str, items: list) -> None:
    _divider(header)
    for item in items:
        print(f"  {item}")
    print()


# ── Main demo ─────────────────────────────────────────────────────────────────

def main() -> None:
    """Run the interactive command-line demo."""
    print()
    _divider("FSHN — Sistema e Sigurisë Universitare  v2.0", "═")
    print("  Python port — command-line demo")
    _divider(char="═")
    print()

    students  = _seed_students()
    pedagoget = _seed_pedagoget()
    adminets  = _seed_adminets()
    officers  = _seed_officers()
    sulmuesit = _seed_sulmuesit()
    ids_list  = _seed_ids()

    _section("👤  Students", students)
    _section("🎓  Pedagogë", pedagoget)
    _section("🔧  Admin Sistemi", adminets)
    _section("🛡  Security Officers", officers)
    _section("💀  Sulmues (Threat Actors)", sulmuesit)
    _section("🤖  IDS Engines", ids_list)

    # ── Interactive demonstrations ────────────────────────────────────────────
    _divider("Demonstrim: Login & 2FA")

    student = students[0]
    print(f"\n  Student: {student}")
    ok = student.login(student.email, student.fjalekalimi)
    print(f"  login(correct credentials)  → {ok}")
    ok2 = student.login(student.email, "wrong")
    print(f"  login(wrong password)       → {ok2}")
    ok3 = student.verifiko_2fa("123456")
    print(f"  verifiko_2fa('123456')      → {ok3}")

    print()
    _divider("Demonstrim: Admin actions")
    admin = adminets[0]
    print(f"\n  Admin: {admin}  [{admin.nivel_label()}]")
    admin.blok_ip("192.168.1.99")
    admin.konfiguroj_firewall()
    admin.gjeneroj_raport()

    print()
    _divider("Demonstrim: IDS Engine")
    ids = ids_list[0]
    print(f"\n  IDS: {ids}")
    ids.monitoro_trafikun()
    ids.blok_ip_auto("10.0.0.1")
    matched = ids.analizo_pattern("SELECT * FROM users WHERE id='1' OR '1'='1'")
    print(f"  analizo_pattern(SQL-injection string) → {matched}")
    benign = ids.analizo_pattern("GET /index.html HTTP/1.1")
    print(f"  analizo_pattern(benign request)       → {benign}")

    print()
    _divider("Demonstrim: Sulmuesi")
    atk = sulmuesit[0]
    print(f"\n  Sulmuesi: {atk}")
    atk.tento_brute_force()
    atk.blloko()
    print(f"  After blloko(): {atk}")

    print()
    _divider()
    print("  Demo i përfunduar.  Hapni GUI-në me:  python -m university_security.gui")
    _divider()
    print()


if __name__ == "__main__":
    main()
