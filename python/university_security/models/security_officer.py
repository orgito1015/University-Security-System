"""SecurityOfficer domain class — mirrors SecurityOfficer.java."""

from __future__ import annotations

from dataclasses import dataclass, field
from datetime import datetime

from .actor import Actor


@dataclass
class SecurityOfficer(Actor):
    """SOC analyst / Security Officer account.

    Attributes
    ----------
    officer_id:        Unique identifier (e.g. ``"O001"``).
    emri:              First name.
    mbiemri:           Last name.
    email:             University e-mail address.
    fjalekalimi:       Hashed / plain password (demo only).
    nivel_autorizimi:  Authorisation level — ``1`` Viewer, ``2`` Analyst,
                       ``3`` Chief.
    gjendja_llogarise: Account state — ``"Aktive"``, ``"Locked"``,
                       or ``"UnderReview"``.
    certifikimi:       Certification held (e.g. ``"CISSP"``, ``"CEH"``).
    date_regjistrimi:  Registration timestamp (default: *now*).
    """

    officer_id: str
    emri: str
    mbiemri: str
    email: str
    fjalekalimi: str
    nivel_autorizimi: int
    gjendja_llogarise: str
    certifikimi: str
    date_regjistrimi: datetime = field(default_factory=datetime.now)

    # ── Actor abstract property ───────────────────────────────────────────────
    @property
    def roli(self) -> str:
        return "SecurityOfficer"

    # ── Auth behaviours ───────────────────────────────────────────────────────
    def login(self, email: str, password: str) -> bool:
        return self.email == email and self.fjalekalimi == password

    def logout(self) -> None:
        print(f"SecurityOfficer {self.emri} doli.")

    def verifiko_2fa(self, kod: str) -> bool:
        return kod is not None and len(kod) == 6

    # ── Domain behaviours ─────────────────────────────────────────────────────
    def analizo_incident(self, incident_id: str) -> None:
        print(f"Analizoj incidentin: {incident_id}")

    def vendos_politike(self, politika: str) -> None:
        print(f"Politika e re: {politika}")

    def konfirmo_alert(self, alert_id: str) -> None:
        print(f"Alert konfirmuar: {alert_id}")

    def nivel_label(self) -> str:
        """Return a human-readable authorisation-level label."""
        return {1: "Viewer", 2: "Analyst", 3: "Chief"}.get(
            self.nivel_autorizimi, "Unknown"
        )

    # ── Dunder methods ────────────────────────────────────────────────────────
    def __str__(self) -> str:
        return (
            f"[{self.officer_id}] {self.emri} {self.mbiemri}"
            f" — {self.certifikimi}"
        )

    def __repr__(self) -> str:
        return (
            f"SecurityOfficer(officer_id={self.officer_id!r}, emri={self.emri!r},"
            f" mbiemri={self.mbiemri!r}, email={self.email!r},"
            f" nivel_autorizimi={self.nivel_autorizimi!r},"
            f" gjendja_llogarise={self.gjendja_llogarise!r},"
            f" certifikimi={self.certifikimi!r})"
        )

    def __eq__(self, other: object) -> bool:
        if not isinstance(other, SecurityOfficer):
            return NotImplemented
        return self.officer_id == other.officer_id

    def __hash__(self) -> int:
        return hash(self.officer_id)
