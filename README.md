<div align="center">

# University Security System
### FSHN Cybersecurity Platform — Object-Oriented Java

[![Java](https://img.shields.io/badge/Java-17%2B-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.java.com)
[![Swing](https://img.shields.io/badge/GUI-Java%20Swing-4A90E2?style=for-the-badge&logo=java&logoColor=white)](https://docs.oracle.com/javase/tutorial/uiswing/)
[![Architecture](https://img.shields.io/badge/Architecture-OOP%20%2F%20UML-6C63FF?style=for-the-badge)](https://en.wikipedia.org/wiki/Object-oriented_programming)
[![License](https://img.shields.io/badge/License-MIT-22c55e?style=for-the-badge)](LICENSE)

*A role-based cybersecurity management system for the Faculty of Natural Sciences (FSHN), simulating real-world threat actors, security personnel, and intrusion detection through a Java Swing desktop interface.*

</div>

---

## Table of Contents

- [Overview](#overview)
- [Technology Stack](#technology-stack)
- [Domain Classes](#domain-classes)
- [Getting Started](#getting-started)
- [Project Structure](#project-structure)
- [Authors](#authors)
- [License](#license)

---

## Overview

The **University Security System** is an academic project that applies object-oriented design and UML modelling to simulate a university security environment. It manages six actor types — **Student**, **Pedagog**, **AdminSistemi**, **SecurityOfficer**, **Sulmuesi** (Attacker), and **IDSEngine** — through a dark-themed Java Swing GUI with one dedicated tab per actor.

---

## Technology Stack

| Layer | Technology |
|---|---|
| Language | Java 17+ |
| GUI Framework | Java Swing (`JFrame`, `JTabbedPane`, `JTable`) |
| Paradigm | Object-Oriented Programming (OOP) |
| Modelling | Unified Modelling Language (UML) |
| Build | `javac` (standard JDK compiler) |

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

### Prerequisites

- **Java Development Kit (JDK) 17+** — [Download from Adoptium](https://adoptium.net)

### Build & Run

```bash
# Clone and compile
git clone https://github.com/orgito1015/University-Security-System.git
cd University-Security-System
javac *.java

# Launch the application
java FSHNObjectManager
```

The GUI opens with six tabs, each pre-populated with demo data for immediate exploration.

---

## Project Structure

```
University-Security-System/
├── Student.java                         # Student domain model
├── Pedagog.java                         # Academic staff domain model
├── AdminSistemi.java                    # System Administrator domain model
├── SecurityOfficer.java                 # SOC Officer domain model
├── Sulmuesi.java                        # Threat Actor domain model
├── IDSEngine.java                       # Intrusion Detection System model
├── FSHNObjectManager.java               # Main GUI application (Swing)
├── fshn_security_usecase_uml.svg        # UML Use Case Diagram
├── fshn_cybersecurity_project_plan.html # Project plan document
├── fshn_usecase_dashboard.html          # Interactive UML dashboard
└── README.md
```

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
