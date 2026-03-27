"""pytest unit tests for every domain class.

Run with::

    cd python
    pytest tests/ -v
"""

from __future__ import annotations

import sys
import os

# Make sure the package is importable when running tests from the python/ dir
sys.path.insert(0, os.path.dirname(os.path.dirname(__file__)))

import pytest
from university_security.models import (
    AdminSistemi,
    IDSEngine,
    Pedagog,
    SecurityOfficer,
    Student,
    Sulmuesi,
)
from university_security.models.actor import Actor


# ── Fixtures ──────────────────────────────────────────────────────────────────

@pytest.fixture
def student() -> Student:
    return Student(
        student_id="S001",
        emri="Arta",
        mbiemri="Kelmendi",
        email="arta@fshn.edu.al",
        fjalekalimi="pass123",
        gjendja_llogarise="Aktive",
        departamenti="Informatike",
    )


@pytest.fixture
def pedagog() -> Pedagog:
    return Pedagog(
        pedagog_id="P001",
        emri="Gentian",
        mbiemri="Mema",
        email="g.mema@fshn.edu.al",
        fjalekalimi="profpass",
        departamenti="Informatike",
        titulliAkademik="Prof. Dr.",
        gjendja_llogarise="Aktive",
    )


@pytest.fixture
def admin() -> AdminSistemi:
    return AdminSistemi(
        admin_id="A001",
        emri="Leotrim",
        mbiemri="Gashi",
        email="l.gashi@fshn.edu.al",
        fjalekalimi="admin123",
        nivel_privilegjeve=3,
        gjendja_llogarise="Aktive",
    )


@pytest.fixture
def officer() -> SecurityOfficer:
    return SecurityOfficer(
        officer_id="O001",
        emri="Oltian",
        mbiemri="Rama",
        email="o.rama@fshn.edu.al",
        fjalekalimi="soc123",
        nivel_autorizimi=3,
        gjendja_llogarise="Aktive",
        certifikimi="CISSP",
    )


@pytest.fixture
def sulmuesi() -> Sulmuesi:
    return Sulmuesi(
        sulmues_id="ATK001",
        ip="192.168.1.100",
        tipi_sulmit="BruteForce",
        num_tentativave=5,
        eshte_i_bllokuar=False,
        vend_origjines="Albania",
        user_agent="Mozilla/5.0",
    )


@pytest.fixture
def ids_engine() -> IDSEngine:
    return IDSEngine(
        ids_id="IDS001",
        eshte_aktiv=True,
        prag_alertit=100,
        kerkesa_per_minute=45,
        versioni="2.4.1",
        status_detektimit="Normal",
    )


# ── Actor abstract base class ─────────────────────────────────────────────────

class TestActor:
    def test_actor_is_abstract(self) -> None:
        """Actor cannot be instantiated directly."""
        with pytest.raises(TypeError):
            Actor()  # type: ignore[abstract]

    def test_student_is_actor(self, student: Student) -> None:
        assert isinstance(student, Actor)

    def test_pedagog_is_actor(self, pedagog: Pedagog) -> None:
        assert isinstance(pedagog, Actor)

    def test_admin_is_actor(self, admin: AdminSistemi) -> None:
        assert isinstance(admin, Actor)

    def test_officer_is_actor(self, officer: SecurityOfficer) -> None:
        assert isinstance(officer, Actor)

    def test_sulmuesi_is_not_actor(self, sulmuesi: Sulmuesi) -> None:
        assert not isinstance(sulmuesi, Actor)

    def test_ids_engine_is_not_actor(self, ids_engine: IDSEngine) -> None:
        assert not isinstance(ids_engine, Actor)


# ── Student ───────────────────────────────────────────────────────────────────

class TestStudent:
    def test_roli(self, student: Student) -> None:
        assert student.roli == "Student"

    def test_login_correct(self, student: Student) -> None:
        assert student.login("arta@fshn.edu.al", "pass123") is True

    def test_login_wrong_password(self, student: Student) -> None:
        assert student.login("arta@fshn.edu.al", "wrong") is False

    def test_login_wrong_email(self, student: Student) -> None:
        assert student.login("other@fshn.edu.al", "pass123") is False

    def test_logout(self, student: Student, capsys: pytest.CaptureFixture) -> None:
        student.logout()
        assert "Arta" in capsys.readouterr().out

    def test_verifiko_2fa_valid(self, student: Student) -> None:
        assert student.verifiko_2fa("123456") is True

    def test_verifiko_2fa_short(self, student: Student) -> None:
        assert student.verifiko_2fa("123") is False

    def test_verifiko_2fa_none(self, student: Student) -> None:
        assert student.verifiko_2fa(None) is False  # type: ignore[arg-type]

    def test_rrit_tentativa(self, student: Student) -> None:
        student.rrit_tentativa()
        assert student.num_tentativave == 1

    def test_reset_tentativa(self, student: Student) -> None:
        student.num_tentativave = 3
        student.reset_tentativa()
        assert student.num_tentativave == 0

    def test_str(self, student: Student) -> None:
        s = str(student)
        assert "S001" in s
        assert "Arta" in s
        assert "Aktive" in s

    def test_repr(self, student: Student) -> None:
        r = repr(student)
        assert "Student(" in r
        assert "S001" in r

    def test_equality_same_id(self, student: Student) -> None:
        other = Student("S001", "X", "Y", "x@y.com", "pw", "Locked", "Math")
        assert student == other

    def test_equality_different_id(self, student: Student) -> None:
        other = Student("S999", "Arta", "Kelmendi", "arta@fshn.edu.al", "pass123", "Aktive", "Informatike")
        assert student != other

    def test_hashable(self, student: Student) -> None:
        s = {student}
        assert student in s

    def test_default_num_tentativave(self, student: Student) -> None:
        assert student.num_tentativave == 0

    def test_date_regjistrimi_set(self, student: Student) -> None:
        assert student.date_regjistrimi is not None


# ── Pedagog ───────────────────────────────────────────────────────────────────

class TestPedagog:
    def test_roli(self, pedagog: Pedagog) -> None:
        assert pedagog.roli == "Pedagog"

    def test_login_correct(self, pedagog: Pedagog) -> None:
        assert pedagog.login("g.mema@fshn.edu.al", "profpass") is True

    def test_login_wrong(self, pedagog: Pedagog) -> None:
        assert pedagog.login("g.mema@fshn.edu.al", "bad") is False

    def test_verifiko_2fa(self, pedagog: Pedagog) -> None:
        assert pedagog.verifiko_2fa("654321") is True
        assert pedagog.verifiko_2fa("short") is False

    def test_fut_note(self, pedagog: Pedagog, capsys: pytest.CaptureFixture) -> None:
        pedagog.fut_note("10")
        assert "10" in capsys.readouterr().out

    def test_ngarko_material(self, pedagog: Pedagog, capsys: pytest.CaptureFixture) -> None:
        pedagog.ngarko_material("slide.pdf")
        assert "slide.pdf" in capsys.readouterr().out

    def test_str(self, pedagog: Pedagog) -> None:
        s = str(pedagog)
        assert "P001" in s
        assert "Prof. Dr." in s
        assert "Informatike" in s

    def test_repr(self, pedagog: Pedagog) -> None:
        assert "Pedagog(" in repr(pedagog)

    def test_equality(self, pedagog: Pedagog) -> None:
        clone = Pedagog("P001", "X", "Y", "x@y.com", "pw", "Math", "Dr.", "Locked")
        assert pedagog == clone

    def test_inequality(self, pedagog: Pedagog) -> None:
        other = Pedagog("P999", "G", "M", "g@m.com", "pw", "Math", "Dr.", "Aktive")
        assert pedagog != other

    def test_hashable(self, pedagog: Pedagog) -> None:
        assert pedagog in {pedagog}


# ── AdminSistemi ──────────────────────────────────────────────────────────────

class TestAdminSistemi:
    def test_roli(self, admin: AdminSistemi) -> None:
        assert admin.roli == "Admin"

    def test_login_correct(self, admin: AdminSistemi) -> None:
        assert admin.login("l.gashi@fshn.edu.al", "admin123") is True

    def test_login_wrong(self, admin: AdminSistemi) -> None:
        assert admin.login("l.gashi@fshn.edu.al", "bad") is False

    def test_nivel_label_superadmin(self, admin: AdminSistemi) -> None:
        assert admin.nivel_label() == "Superadmin"

    def test_nivel_label_standard(self, admin: AdminSistemi) -> None:
        admin.nivel_privilegjeve = 2
        assert admin.nivel_label() == "Standard"

    def test_nivel_label_readonly(self, admin: AdminSistemi) -> None:
        admin.nivel_privilegjeve = 1
        assert admin.nivel_label() == "ReadOnly"

    def test_nivel_label_unknown(self, admin: AdminSistemi) -> None:
        admin.nivel_privilegjeve = 99
        assert admin.nivel_label() == "Unknown"

    def test_blok_ip(self, admin: AdminSistemi, capsys: pytest.CaptureFixture) -> None:
        admin.blok_ip("1.2.3.4")
        assert "1.2.3.4" in capsys.readouterr().out

    def test_konfiguroj_firewall(self, admin: AdminSistemi, capsys: pytest.CaptureFixture) -> None:
        admin.konfiguroj_firewall()
        assert "Firewall" in capsys.readouterr().out

    def test_gjeneroj_raport(self, admin: AdminSistemi, capsys: pytest.CaptureFixture) -> None:
        admin.gjeneroj_raport()
        assert "Raport" in capsys.readouterr().out

    def test_str(self, admin: AdminSistemi) -> None:
        s = str(admin)
        assert "A001" in s
        assert "3" in s

    def test_repr(self, admin: AdminSistemi) -> None:
        assert "AdminSistemi(" in repr(admin)

    def test_equality(self, admin: AdminSistemi) -> None:
        clone = AdminSistemi("A001", "X", "Y", "x@y.com", "pw", 1, "Locked")
        assert admin == clone

    def test_hashable(self, admin: AdminSistemi) -> None:
        assert admin in {admin}


# ── SecurityOfficer ───────────────────────────────────────────────────────────

class TestSecurityOfficer:
    def test_roli(self, officer: SecurityOfficer) -> None:
        assert officer.roli == "SecurityOfficer"

    def test_login_correct(self, officer: SecurityOfficer) -> None:
        assert officer.login("o.rama@fshn.edu.al", "soc123") is True

    def test_login_wrong(self, officer: SecurityOfficer) -> None:
        assert officer.login("o.rama@fshn.edu.al", "bad") is False

    def test_nivel_label_chief(self, officer: SecurityOfficer) -> None:
        assert officer.nivel_label() == "Chief"

    def test_nivel_label_analyst(self, officer: SecurityOfficer) -> None:
        officer.nivel_autorizimi = 2
        assert officer.nivel_label() == "Analyst"

    def test_nivel_label_viewer(self, officer: SecurityOfficer) -> None:
        officer.nivel_autorizimi = 1
        assert officer.nivel_label() == "Viewer"

    def test_analizo_incident(self, officer: SecurityOfficer, capsys: pytest.CaptureFixture) -> None:
        officer.analizo_incident("INC-001")
        assert "INC-001" in capsys.readouterr().out

    def test_vendos_politike(self, officer: SecurityOfficer, capsys: pytest.CaptureFixture) -> None:
        officer.vendos_politike("No USB")
        assert "No USB" in capsys.readouterr().out

    def test_konfirmo_alert(self, officer: SecurityOfficer, capsys: pytest.CaptureFixture) -> None:
        officer.konfirmo_alert("ALT-99")
        assert "ALT-99" in capsys.readouterr().out

    def test_str(self, officer: SecurityOfficer) -> None:
        s = str(officer)
        assert "O001" in s
        assert "CISSP" in s

    def test_repr(self, officer: SecurityOfficer) -> None:
        assert "SecurityOfficer(" in repr(officer)

    def test_equality(self, officer: SecurityOfficer) -> None:
        clone = SecurityOfficer("O001", "X", "Y", "x@y.com", "pw", 1, "Locked", "CEH")
        assert officer == clone

    def test_hashable(self, officer: SecurityOfficer) -> None:
        assert officer in {officer}


# ── Sulmuesi ──────────────────────────────────────────────────────────────────

class TestSulmuesi:
    def test_tento_brute_force(self, sulmuesi: Sulmuesi, capsys: pytest.CaptureFixture) -> None:
        initial = sulmuesi.num_tentativave
        sulmuesi.tento_brute_force()
        assert sulmuesi.num_tentativave == initial + 1
        assert "Brute-force" in capsys.readouterr().out

    def test_tentoj_sql_inj(self, sulmuesi: Sulmuesi, capsys: pytest.CaptureFixture) -> None:
        sulmuesi.tentoj_sql_inj()
        assert sulmuesi.ip in capsys.readouterr().out

    def test_tentoj_phishing(self, sulmuesi: Sulmuesi, capsys: pytest.CaptureFixture) -> None:
        sulmuesi.tentoj_phishing()
        assert sulmuesi.ip in capsys.readouterr().out

    def test_blloko(self, sulmuesi: Sulmuesi) -> None:
        assert sulmuesi.eshte_i_bllokuar is False
        sulmuesi.blloko()
        assert sulmuesi.eshte_i_bllokuar is True

    def test_str_active(self, sulmuesi: Sulmuesi) -> None:
        s = str(sulmuesi)
        assert "ATK001" in s
        assert "Aktiv" in s

    def test_str_blocked(self, sulmuesi: Sulmuesi) -> None:
        sulmuesi.blloko()
        assert "BLLOKUAR" in str(sulmuesi)

    def test_repr(self, sulmuesi: Sulmuesi) -> None:
        assert "Sulmuesi(" in repr(sulmuesi)

    def test_equality(self, sulmuesi: Sulmuesi) -> None:
        clone = Sulmuesi("ATK001", "10.0.0.1", "Phishing", 0, True, "X", "Y")
        assert sulmuesi == clone

    def test_inequality(self, sulmuesi: Sulmuesi) -> None:
        other = Sulmuesi("ATK999", "192.168.1.100", "BruteForce", 5, False, "Albania", "UA")
        assert sulmuesi != other

    def test_hashable(self, sulmuesi: Sulmuesi) -> None:
        assert sulmuesi in {sulmuesi}


# ── IDSEngine ─────────────────────────────────────────────────────────────────

class TestIDSEngine:
    def test_monitoro_trafikun(self, ids_engine: IDSEngine, capsys: pytest.CaptureFixture) -> None:
        ids_engine.monitoro_trafikun()
        assert "IDS" in capsys.readouterr().out

    def test_blok_ip_auto(self, ids_engine: IDSEngine, capsys: pytest.CaptureFixture) -> None:
        initial = ids_engine.ip_bllokuara_totale
        ids_engine.blok_ip_auto("5.5.5.5")
        assert ids_engine.ip_bllokuara_totale == initial + 1
        assert "5.5.5.5" in capsys.readouterr().out

    def test_analizo_pattern_sql_quote(self, ids_engine: IDSEngine) -> None:
        assert ids_engine.analizo_pattern("SELECT * FROM t WHERE id='1'") is True

    def test_analizo_pattern_sql_comment(self, ids_engine: IDSEngine) -> None:
        assert ids_engine.analizo_pattern("admin'--") is True

    def test_analizo_pattern_benign(self, ids_engine: IDSEngine) -> None:
        assert ids_engine.analizo_pattern("GET /index.html HTTP/1.1") is False

    def test_gjeneroj_log(self, ids_engine: IDSEngine, capsys: pytest.CaptureFixture) -> None:
        ids_engine.gjeneroj_log()
        assert "Log" in capsys.readouterr().out

    def test_aktivizo(self, ids_engine: IDSEngine) -> None:
        ids_engine.eshte_aktiv = False
        ids_engine.aktivizo()
        assert ids_engine.eshte_aktiv is True
        assert ids_engine.status_detektimit == "Normal"

    def test_caktivizo(self, ids_engine: IDSEngine) -> None:
        ids_engine.caktivizo()
        assert ids_engine.eshte_aktiv is False

    def test_str_active(self, ids_engine: IDSEngine) -> None:
        s = str(ids_engine)
        assert "IDS001" in s
        assert "AKTIV" in s

    def test_str_inactive(self, ids_engine: IDSEngine) -> None:
        ids_engine.caktivizo()
        assert "OFF" in str(ids_engine)

    def test_repr(self, ids_engine: IDSEngine) -> None:
        assert "IDSEngine(" in repr(ids_engine)

    def test_equality(self, ids_engine: IDSEngine) -> None:
        clone = IDSEngine("IDS001", False, 50, 0, "1.0", "Alert")
        assert ids_engine == clone

    def test_inequality(self, ids_engine: IDSEngine) -> None:
        other = IDSEngine("IDS999", True, 100, 45, "2.4.1", "Normal")
        assert ids_engine != other

    def test_hashable(self, ids_engine: IDSEngine) -> None:
        assert ids_engine in {ids_engine}

    def test_default_ip_bllokuara(self, ids_engine: IDSEngine) -> None:
        assert ids_engine.ip_bllokuara_totale == 0
