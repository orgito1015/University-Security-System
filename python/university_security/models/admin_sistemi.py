"""AdminSistemi domain class — mirrors AdminSistemi.java."""

from __future__ import annotations

from dataclasses import dataclass, field
from datetime import datetime

from .actor import Actor


@dataclass
class AdminSistemi(Actor):
    """System administrator account.

    Attributes
    ----------
    admin_id:           Unique identifier (e.g. ``"A001"``).
    emri:               First name.
    mbiemri:            Last name.
    email:              University e-mail address.
    fjalekalimi:        Hashed / plain password (demo only).
    nivel_privilegjeve: Privilege level — ``1`` ReadOnly, ``2`` Standard,
                        ``3`` Superadmin.
    gjendja_llogarise:  Account state — ``"Aktive"``, ``"Locked"``,
                        or ``"UnderReview"``.
    date_regjistrimi:   Registration timestamp (default: *now*).
    """

    admin_id: str
    emri: str
    mbiemri: str
    email: str
    fjalekalimi: str
    nivel_privilegjeve: int
    gjendja_llogarise: str
    date_regjistrimi: datetime = field(default_factory=datetime.now)

    # ── Actor abstract property ───────────────────────────────────────────────
    @property
    def roli(self) -> str:
        return "Admin"

    # ── Auth behaviours ───────────────────────────────────────────────────────
    def login(self, email: str, password: str) -> bool:
        return self.email == email and self.fjalekalimi == password

    def logout(self) -> None:
        print(f"Admin {self.emri} doli.")

    def verifiko_2fa(self, kod: str) -> bool:
        return kod is not None and len(kod) == 6

    # ── Domain behaviours ─────────────────────────────────────────────────────
    def blok_ip(self, ip: str) -> None:
        print(f"IP bllokuar: {ip}")

    def riaktivizo_llogari(self, user_id: str) -> None:
        print(f"Llogaria riaktivizuar: {user_id}")

    def konfiguroj_firewall(self) -> None:
        print("Firewall konfiguruar.")

    def gjeneroj_raport(self) -> None:
        print("Raport gjeneruar.")

    def nivel_label(self) -> str:
        """Return a human-readable privilege-level label."""
        return {1: "ReadOnly", 2: "Standard", 3: "Superadmin"}.get(
            self.nivel_privilegjeve, "Unknown"
        )

    # ── Dunder methods ────────────────────────────────────────────────────────
    def __str__(self) -> str:
        return (
            f"[{self.admin_id}] {self.emri} {self.mbiemri}"
            f" — Nivel {self.nivel_privilegjeve}"
        )

    def __repr__(self) -> str:
        return (
            f"AdminSistemi(admin_id={self.admin_id!r}, emri={self.emri!r},"
            f" mbiemri={self.mbiemri!r}, email={self.email!r},"
            f" nivel_privilegjeve={self.nivel_privilegjeve!r},"
            f" gjendja_llogarise={self.gjendja_llogarise!r})"
        )

    def __eq__(self, other: object) -> bool:
        if not isinstance(other, AdminSistemi):
            return NotImplemented
        return self.admin_id == other.admin_id

    def __hash__(self) -> int:
        return hash(self.admin_id)
