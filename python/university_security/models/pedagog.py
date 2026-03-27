"""Pedagog domain class — mirrors Pedagog.java."""

from __future__ import annotations

from dataclasses import dataclass, field
from datetime import datetime

from .actor import Actor


@dataclass
class Pedagog(Actor):
    """An academic staff member / lecturer.

    Attributes
    ----------
    pedagog_id:        Unique identifier (e.g. ``"P001"``).
    emri:              First name.
    mbiemri:           Last name.
    email:             University e-mail address.
    fjalekalimi:       Hashed / plain password (demo only).
    departamenti:      Academic department.
    titulliAkademik:   Academic title — ``"Prof."``, ``"Dr."``, ``"Asoc."`` …
    gjendja_llogarise: Account state — ``"Aktive"``, ``"Locked"``,
                       or ``"UnderReview"``.
    date_regjistrimi:  Registration timestamp (default: *now*).
    """

    pedagog_id: str
    emri: str
    mbiemri: str
    email: str
    fjalekalimi: str
    departamenti: str
    titulliAkademik: str
    gjendja_llogarise: str
    date_regjistrimi: datetime = field(default_factory=datetime.now)

    # ── Actor abstract property ───────────────────────────────────────────────
    @property
    def roli(self) -> str:
        return "Pedagog"

    # ── Auth behaviours ───────────────────────────────────────────────────────
    def login(self, email: str, password: str) -> bool:
        return self.email == email and self.fjalekalimi == password

    def logout(self) -> None:
        print(f"Pedagog {self.emri} doli.")

    def verifiko_2fa(self, kod: str) -> bool:
        return kod is not None and len(kod) == 6

    # ── Domain behaviours ─────────────────────────────────────────────────────
    def fut_note(self, nota: str) -> None:
        print(f"Nota u fut: {nota}")

    def ngarko_material(self, material: str) -> None:
        print(f"Material ngarkuar: {material}")

    # ── Dunder methods ────────────────────────────────────────────────────────
    def __str__(self) -> str:
        return (
            f"[{self.pedagog_id}] {self.titulliAkademik} {self.emri}"
            f" {self.mbiemri} — {self.departamenti}"
        )

    def __repr__(self) -> str:
        return (
            f"Pedagog(pedagog_id={self.pedagog_id!r}, emri={self.emri!r},"
            f" mbiemri={self.mbiemri!r}, email={self.email!r},"
            f" departamenti={self.departamenti!r},"
            f" titulliAkademik={self.titulliAkademik!r},"
            f" gjendja_llogarise={self.gjendja_llogarise!r})"
        )

    def __eq__(self, other: object) -> bool:
        if not isinstance(other, Pedagog):
            return NotImplemented
        return self.pedagog_id == other.pedagog_id

    def __hash__(self) -> int:
        return hash(self.pedagog_id)
