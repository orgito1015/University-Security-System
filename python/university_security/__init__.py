"""university_security — FSHN University Security System (Python port).

Run the CLI demo with::

    python -m university_security

Launch the Tkinter GUI with::

    python -m university_security.gui
"""

from .models import (
    Actor,
    AdminSistemi,
    IDSEngine,
    Pedagog,
    SecurityOfficer,
    Student,
    Sulmuesi,
)

__version__ = "2.0.0"
__all__ = [
    "Actor",
    "AdminSistemi",
    "IDSEngine",
    "Pedagog",
    "SecurityOfficer",
    "Student",
    "Sulmuesi",
]
