<div align="center">

# University Security System
### FSHN Cybersecurity Platform — Multi-Language & Object-Oriented

[![Java](https://img.shields.io/badge/Java-17%2B-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.java.com)
[![Python](https://img.shields.io/badge/Python-3.10%2B-3776AB?style=for-the-badge&logo=python&logoColor=white)](https://www.python.org)
[![Swing](https://img.shields.io/badge/GUI-Java%20Swing-4A90E2?style=for-the-badge&logo=java&logoColor=white)](https://docs.oracle.com/javase/tutorial/uiswing/)
[![Architecture](https://img.shields.io/badge/Architecture-OOP%20%2F%20UML-6C63FF?style=for-the-badge)](https://en.wikipedia.org/wiki/Object-oriented_programming)
[![License](https://img.shields.io/badge/License-MIT-22c55e?style=for-the-badge)](LICENSE)

*A role-based cybersecurity management system for the Faculty of Natural Sciences (FSHN), simulating real-world threat actors, security personnel, and intrusion detection — implemented in Java with a Python port on the roadmap.*

</div>

---

## Table of Contents

- [Overview](#overview)
- [Architecture](#architecture)
- [Technology Stack](#technology-stack)
- [Domain Classes](#domain-classes)
- [Getting Started](#getting-started)
  - [Java (current)](#java-current)
  - [Python (planned)](#python-planned)
- [Project Structure](#project-structure)
- [Roadmap](#roadmap)
- [Contributing](#contributing)
- [Authors](#authors)
- [License](#license)

---

## Overview

The **University Security System** is an academic project that applies object-oriented design and UML modelling to simulate a university security environment. It manages six actor types — **Student**, **Pedagog**, **AdminSistemi**, **SecurityOfficer**, **Sulmuesi** (Attacker), and **IDSEngine** — through a dark-themed Java Swing GUI with one dedicated tab per actor.

The project is designed to grow beyond a single language. The long-term vision is a polyglot platform where the **same domain model and business logic** are implemented in multiple languages (Java, Python, and more), sharing a common UML specification. This makes it an ideal learning ground for comparing OOP patterns, GUI toolkits, and security concepts across ecosystems.

---

## Architecture

```
┌─────────────────────────────────────────────────────────┐
│                  Presentation Layer                     │
│   Java Swing GUI (FSHNObjectManager)                    │
│   Python CLI / Tkinter GUI  [planned]                   │
└────────────────────────┬────────────────────────────────┘
                         │
┌────────────────────────▼────────────────────────────────┐
│                   Domain Layer                          │
│  Student · Pedagog · AdminSistemi · SecurityOfficer     │
│  Sulmuesi · IDSEngine                                   │
└────────────────────────┬────────────────────────────────┘
                         │
┌────────────────────────▼────────────────────────────────┐
│              Infrastructure / Persistence               │
│  In-memory objects (v1)                                 │
│  JSON / SQLite persistence  [planned]                   │
│  REST API layer             [planned]                   │
└─────────────────────────────────────────────────────────┘
```

The domain layer is **language-agnostic by design**: every class maps directly to a UML entity so that the Python port can replicate the same behaviour with idiomatic Python code (dataclasses, type hints, `abc`, etc.).

---

## Technology Stack

| Layer | Current (Java) | Planned (Python) |
|---|---|---|
| Language | Java 17+ | Python 3.10+ |
| GUI | Java Swing | Tkinter / PyQt6 |
| Paradigm | OOP, UML | OOP, dataclasses |
| Persistence | In-memory | JSON / SQLite |
| Testing | JUnit 5 | pytest |
| Build / Run | `javac` + `java` | `pip` + `python` |
| Packaging | JAR | wheel / PyPI |

---

## Domain Classes

| Class | Role | Key Behaviours |
|---|---|---|
| `Student` | University student account | `login()`, `verifiko2FA()`, `rritTentativa()` |
| `Pedagog` | Academic staff / lecturer | `login()`, `futNote()`, `ngarkoMaterial()` |
| `AdminSistemi` | System administrator | `bllokIP()`, `konfigurojFirewall()`, `gjenerojRaport()` |
| `SecurityOfficer` | SOC analyst | `analizoIncident()`, `vendosPolitike()`, `konfirmoAlert()` |
| `Sulmuesi` | Threat actor (attacker) | `tentoBruteForce()`, `tentojSQLInj()`, `blloko()` |
| `IDSEngine` | Intrusion Detection System | `monitoroTrafikun()`, `bllokIPAuto()`, `analizoPattern()` |

---

## Getting Started

### Java (current)

#### Prerequisites

- **Java Development Kit (JDK) 17+** — [Download from Adoptium](https://adoptium.net)

#### Build & Run

```bash
# Clone the repository
git clone https://github.com/orgito1015/University-Security-System.git
cd University-Security-System

# Compile all Java sources
javac *.java

# Launch the Swing GUI
java FSHNObjectManager
```

The GUI opens with six tabs, each pre-populated with demo data for immediate exploration.

---

### Python (planned)

> ⚠️ The Python port is not yet available. The commands below illustrate the **intended developer experience** once Phase 2 is complete.

```bash
# Navigate to the Python sub-package (once created)
cd python

# Install dependencies
pip install -r requirements.txt

# Run the CLI demo
python -m university_security

# Launch the GUI (Tkinter / PyQt6)
python -m university_security.gui
```

---

## Project Structure

```
University-Security-System/
│
├── java/                                      # (future re-organisation)
│   ├── Student.java                           #  Student domain model
│   ├── Pedagog.java                           #  Academic staff domain model
│   ├── AdminSistemi.java                      #  System Administrator model
│   ├── SecurityOfficer.java                   #  SOC Officer domain model
│   ├── Sulmuesi.java                          #  Threat Actor domain model
│   ├── IDSEngine.java                         #  Intrusion Detection System
│   └── FSHNObjectManager.java                 #  Main Swing GUI entry point
│
├── python/                                    # [planned — Phase 2]
│   ├── university_security/
│   │   ├── __init__.py
│   │   ├── models/
│   │   │   ├── student.py
│   │   │   ├── pedagog.py
│   │   │   ├── admin_sistemi.py
│   │   │   ├── security_officer.py
│   │   │   ├── sulmuesi.py
│   │   │   └── ids_engine.py
│   │   ├── gui/
│   │   │   └── main_window.py
│   │   └── __main__.py
│   ├── tests/
│   │   └── test_models.py
│   └── requirements.txt
│
├── docs/                                      # [planned — Phase 3]
│   ├── api.md
│   └── architecture.md
│
├── Student.java                               # Current root-level sources
├── Pedagog.java
├── AdminSistemi.java
├── SecurityOfficer.java
├── Sulmuesi.java
├── IDSEngine.java
├── FSHNObjectManager.java
├── fshn_security_usecase_uml.svg              # UML Use Case Diagram
├── fshn_cybersecurity_project_plan.html       # Project plan document
├── fshn_usecase_dashboard.html                # Interactive UML dashboard
└── README.md
```

---

## Roadmap

> Legend: ✅ Done &nbsp;|&nbsp; 🔄 In Progress &nbsp;|&nbsp; 🗓️ Planned &nbsp;|&nbsp; 💡 Idea

### Phase 1 — Java Foundation ✅
- ✅ Define six domain classes with full OOP encapsulation
- ✅ Implement Java Swing GUI with dark theme and six actor tabs
- ✅ Pre-populate demo data for immediate exploration
- ✅ Produce UML Use-Case diagram and interactive HTML dashboard
- ✅ Write comprehensive README

### Phase 2 — Python Port 🗓️
- 🗓️ Mirror all six domain classes as Python dataclasses with type hints
- 🗓️ Implement `__str__`, `__repr__`, and equality methods
- 🗓️ Add abstract base class (`Actor`) shared by all roles
- 🗓️ Build a command-line demo (`python -m university_security`)
- 🗓️ Create a Tkinter or PyQt6 GUI matching the Java feature set
- 🗓️ Write `pytest` unit tests for every domain class
- 🗓️ Set up `requirements.txt` and packaging (`pyproject.toml`)

### Phase 3 — Persistence & API 🗓️
- 🗓️ Add JSON-based save/load for all actors (both Java and Python)
- 🗓️ Introduce SQLite storage layer
- 🗓️ Expose a lightweight REST API (Python FastAPI / Java Spring Boot)
- 🗓️ Provide example Postman / HTTPie request collections

### Phase 4 — Security Simulation Enhancements 🗓️
- 🗓️ Live attack simulation: `IDSEngine` auto-responds to `Sulmuesi` events
- 🗓️ Alert dashboard with real-time counters and log viewer
- 🗓️ 2FA code generation and verification (TOTP)
- 🗓️ Role-based access control (RBAC) enforcement layer

### Phase 5 — Additional Languages 💡
- 💡 **C# / .NET** — WPF or MAUI desktop application
- 💡 **TypeScript / Node.js** — REST back-end + React front-end
- 💡 **Go** — lightweight CLI and API server
- 💡 Cross-language integration tests to verify consistent behaviour

### Phase 6 — DevOps & Quality 🗓️
- 🗓️ GitHub Actions CI pipeline (compile Java, run pytest)
- 🗓️ Code coverage reporting
- 🗓️ Static analysis (Checkstyle for Java, `ruff`/`mypy` for Python)
- 🗓️ Automated UML diagram generation from source code

---

## Contributing

Contributions are welcome! Here is how you can help:

1. **Fork** the repository and create a feature branch:
   ```bash
   git checkout -b feature/python-student-model
   ```
2. **Implement** your changes following the existing code style.
3. **Test** your changes manually (and with unit tests if applicable).
4. **Open a Pull Request** describing what you changed and why.

### Contribution ideas
- Implement any item from the [Roadmap](#roadmap)
- Improve the Java Swing GUI (dark/light theme toggle, icons, search)
- Add JUnit 5 tests for the Java domain classes
- Write the Python port (see Phase 2 above)
- Improve documentation or translate comments to English

---

## Authors

<div align="center">
<table>
  <tr>
    <td align="center" width="50%">
      <a href="https://github.com/orgito1015">
        <img src="https://avatars.githubusercontent.com/u/86354243?v=4" width="120px;" alt="Orgito Leka"/>
        <br />
        <b>Orgito Leka</b>
      </a>
      <br />
      <sub>Computer Science · FSHN</sub>
    </td>
    <td align="center" width="50%">
      <a href="https://github.com/MateosXhukellari">
        <img src="https://avatars.githubusercontent.com/u/210402974?v=4" width="120px;" alt="Mateos Xhukellari"/>
        <br />
        <b>Mateos Xhukellari</b>
      </a>
      <br />
      <sub>Computer Science · FSHN</sub>
    </td>
  </tr>
</table>

**Institution:** Faculty of Natural Sciences (FSHN) &nbsp;·&nbsp; **Academic Year:** 2025–2026 &nbsp;·&nbsp; **Course:** UML
</div>

---

## License

This project is released under the [MIT License](LICENSE).  
Copyright © 2025 Orgito Leka & Mateos Xhukellari
