"""Student domain class — mirrors Student.java."""

from __future__ import annotations

from dataclasses import dataclass, field
from datetime import datetime

from .actor import Actor


@dataclass
class Student(Actor):
    """A university student account.

    Attributes
    ----------
    student_id:        Unique identifier (e.g. ``"S001"``).
    emri:              First name.
    mbiemri:           Last name.
    email:             University e-mail address.
    fjalekalimi:       Hashed / plain password (demo only).
    gjendja_llogarise: Account state — ``"Aktive"``, ``"Locked"``,
                       or ``"UnderReview"``.
    departamenti:      Academic department.
    num_tentativave:   Failed login attempt counter (default ``0``).
    date_regjistrimi:  Registration timestamp (default: *now*).
    """

    student_id: str
    emri: str
    mbiemri: str
    email: str
    fjalekalimi: str
    gjendja_llogarise: str
    departamenti: str
    num_tentativave: int = 0
    date_regjistrimi: datetime = field(default_factory=datetime.now)

    # ── Actor abstract property ───────────────────────────────────────────────
    @property
    def roli(self) -> str:
        return "Student"

    # ── Auth behaviours ───────────────────────────────────────────────────────
    def login(self, email: str, password: str) -> bool:
        """Return ``True`` when *email* and *password* match."""
        return self.email == email and self.fjalekalimi == password

    def logout(self) -> None:
        print(f"Student {self.emri} doli.")

    def verifiko_2fa(self, kod: str) -> bool:
        """Accept any non-``None`` six-character code."""
        return kod is not None and len(kod) == 6

    # ── Domain behaviours ─────────────────────────────────────────────────────
    def reset_fjalekalim(self) -> None:
        print(f"Reset kërkuar për {self.email}")

    def rrit_tentativa(self) -> None:
        """Increment the failed-login counter."""
        self.num_tentativave += 1

    def reset_tentativa(self) -> None:
        """Reset the failed-login counter to zero."""
        self.num_tentativave = 0

    # ── Dunder methods ────────────────────────────────────────────────────────
    def __str__(self) -> str:
        return (
            f"[{self.student_id}] {self.emri} {self.mbiemri}"
            f" <{self.email}> — {self.gjendja_llogarise}"
        )

    def __repr__(self) -> str:
        return (
            f"Student(student_id={self.student_id!r}, emri={self.emri!r},"
            f" mbiemri={self.mbiemri!r}, email={self.email!r},"
            f" gjendja_llogarise={self.gjendja_llogarise!r},"
            f" departamenti={self.departamenti!r})"
        )

    def __eq__(self, other: object) -> bool:
        if not isinstance(other, Student):
            return NotImplemented
        return self.student_id == other.student_id

    def __hash__(self) -> int:
        return hash(self.student_id)
