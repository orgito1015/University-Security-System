"""Sulmuesi domain class — mirrors Sulmuesi.java (threat-actor model)."""

from __future__ import annotations

from dataclasses import dataclass, field
from datetime import datetime


@dataclass
class Sulmuesi:
    """Threat actor / attacker entity.

    Sulmuesi is *not* an :class:`~university_security.models.actor.Actor`
    because it does not represent a legitimate user role; it models an
    external adversary and therefore has no authentication flow.

    Attributes
    ----------
    sulmues_id:     Unique identifier (e.g. ``"ATK001"``).
    ip:             Source IP address.
    tipi_sulmit:    Attack type — ``"BruteForce"``, ``"SQLInjection"``,
                    or ``"Phishing"``.
    num_tentativave: Number of attack attempts so far.
    eshte_i_bllokuar: ``True`` when this attacker has been blocked.
    vend_origjines:  Country / region of origin.
    user_agent:     HTTP User-Agent string captured during the attack.
    timestamp_sulm: Timestamp of the first recorded attack (default: *now*).
    """

    sulmues_id: str
    ip: str
    tipi_sulmit: str
    num_tentativave: int
    eshte_i_bllokuar: bool
    vend_origjines: str
    user_agent: str
    timestamp_sulm: datetime = field(default_factory=datetime.now)

    # ── Domain behaviours ─────────────────────────────────────────────────────
    def tento_brute_force(self) -> None:
        """Record a brute-force attempt."""
        self.num_tentativave += 1
        print(f"Brute-force tentativë #{self.num_tentativave}")

    def tentoj_sql_inj(self) -> None:
        print(f"SQL Injection tentativë nga {self.ip}")

    def tentoj_phishing(self) -> None:
        print(f"Phishing tentativë nga {self.ip}")

    def blloko(self) -> None:
        """Block this attacker."""
        self.eshte_i_bllokuar = True

    # ── Dunder methods ────────────────────────────────────────────────────────
    def __str__(self) -> str:
        status = "BLLOKUAR" if self.eshte_i_bllokuar else "Aktiv"
        return (
            f"[{self.sulmues_id}] IP:{self.ip}"
            f" | {self.tipi_sulmit} | {status}"
        )

    def __repr__(self) -> str:
        return (
            f"Sulmuesi(sulmues_id={self.sulmues_id!r}, ip={self.ip!r},"
            f" tipi_sulmit={self.tipi_sulmit!r},"
            f" num_tentativave={self.num_tentativave!r},"
            f" eshte_i_bllokuar={self.eshte_i_bllokuar!r},"
            f" vend_origjines={self.vend_origjines!r})"
        )

    def __eq__(self, other: object) -> bool:
        if not isinstance(other, Sulmuesi):
            return NotImplemented
        return self.sulmues_id == other.sulmues_id

    def __hash__(self) -> int:
        return hash(self.sulmues_id)
