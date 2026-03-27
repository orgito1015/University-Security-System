"""IDSEngine domain class — mirrors IDSEngine.java."""

from __future__ import annotations

from dataclasses import dataclass, field
from datetime import datetime


@dataclass
class IDSEngine:
    """Intrusion Detection System engine.

    IDSEngine is *not* an :class:`~university_security.models.actor.Actor`
    because it is an automated system component, not a user role.

    Attributes
    ----------
    ids_id:               Unique identifier (e.g. ``"IDS001"``).
    eshte_aktiv:          ``True`` when the engine is running.
    prag_alertit:         Requests-per-second threshold before an alert fires.
    kerkesa_per_minute:   Current observed request rate.
    ip_bllokuara_totale:  Cumulative count of auto-blocked IPs.
    versioni:             Software version string.
    status_detektimit:    Detection status — ``"Normal"``, ``"Watch"``,
                          ``"Alert"``, or ``"Critical"``.
    ngritje_heren:        Engine start timestamp (default: *now*).
    """

    ids_id: str
    eshte_aktiv: bool
    prag_alertit: int
    kerkesa_per_minute: int
    versioni: str
    status_detektimit: str
    ip_bllokuara_totale: int = 0
    ngritje_heren: datetime = field(default_factory=datetime.now)

    # ── Domain behaviours ─────────────────────────────────────────────────────
    def monitoro_trafikun(self) -> None:
        print("IDS: Duke monitoruar trafikun...")

    def blok_ip_auto(self, ip: str) -> None:
        """Automatically block *ip* and increment the blocked-IP counter."""
        self.ip_bllokuara_totale += 1
        print(f"IDS: IP bllokuar automatikisht: {ip}")

    def analizo_pattern(self, kerkese: str) -> bool:
        """Return ``True`` when *kerkese* contains SQL-injection markers."""
        return "'" in kerkese or "--" in kerkese

    def gjeneroj_log(self) -> None:
        print("IDS: Log i sigurisë gjeneruar.")

    def aktivizo(self) -> None:
        """Activate the IDS engine."""
        self.eshte_aktiv = True
        self.status_detektimit = "Normal"

    def caktivizo(self) -> None:
        """Deactivate the IDS engine."""
        self.eshte_aktiv = False

    # ── Dunder methods ────────────────────────────────────────────────────────
    def __str__(self) -> str:
        state = "AKTIV" if self.eshte_aktiv else "OFF"
        return (
            f"[{self.ids_id}] v{self.versioni}"
            f" | {state} | {self.status_detektimit}"
        )

    def __repr__(self) -> str:
        return (
            f"IDSEngine(ids_id={self.ids_id!r}, eshte_aktiv={self.eshte_aktiv!r},"
            f" prag_alertit={self.prag_alertit!r},"
            f" kerkesa_per_minute={self.kerkesa_per_minute!r},"
            f" versioni={self.versioni!r},"
            f" status_detektimit={self.status_detektimit!r})"
        )

    def __eq__(self, other: object) -> bool:
        if not isinstance(other, IDSEngine):
            return NotImplemented
        return self.ids_id == other.ids_id

    def __hash__(self) -> int:
        return hash(self.ids_id)
