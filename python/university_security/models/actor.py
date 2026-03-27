"""Abstract base class shared by all user-role domain classes."""

from __future__ import annotations

from abc import ABC, abstractmethod


class Actor(ABC):
    """Abstract actor — every user role (Student, Pedagog, Admin, SecurityOfficer)
    must implement the three core auth behaviours."""

    @abstractmethod
    def login(self, email: str, password: str) -> bool:
        """Authenticate with *email* and *password*; return True on success."""

    @abstractmethod
    def logout(self) -> None:
        """End the current session."""

    @abstractmethod
    def verifiko_2fa(self, kod: str) -> bool:
        """Verify a 2-FA *kod*; return True when the code is valid."""

    @property
    @abstractmethod
    def roli(self) -> str:
        """Human-readable role label (e.g. ``"Student"``)."""
