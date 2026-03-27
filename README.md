<div align="center">

#  University Security System
### FSHN Cybersecurity Platform — Object-Oriented Java

[![Java](https://img.shields.io/badge/Java-17%2B-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.java.com)
[![Swing](https://img.shields.io/badge/GUI-Java%20Swing-4A90E2?style=for-the-badge&logo=java&logoColor=white)](https://docs.oracle.com/javase/tutorial/uiswing/)
[![Architecture](https://img.shields.io/badge/Architecture-OOP%20%2F%20UML-6C63FF?style=for-the-badge)](https://en.wikipedia.org/wiki/Object-oriented_programming)
[![License](https://img.shields.io/badge/License-MIT-22c55e?style=for-the-badge)](LICENSE)

*A comprehensive, role-based cybersecurity management system for the Faculty of Natural Sciences (FSHN), modelling real-world threat actors, security personnel, and automated intrusion detection through a rich Java Swing desktop interface.*

</div>


## Authors
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
      <br />
    </td>
    <td align="center" width="50%">
      <a href="https://github.com/MateosXhukellari">
        <img src="https://avatars.githubusercontent.com/u/210402974?v=4" width="120px;" alt="Mateos Xhukellari"/>
        <br />
        <b>Mateos Xhukellari</b>
      </a>
      <br />
      <sub>Computer Science · FSHN</sub>
      <br />
    </td>
  </tr>
</table>

> **Institution:** Faculty of Natural Sciences (FSHN)  
> **Academic Year:** 2025–2026  
> **Course:** UML  
---
---

##  Table of Contents

- [Overview](#-overview)
- [System Architecture](#-system-architecture)
- [Domain Classes (UML Actors)](#-domain-classes-uml-actors)
- [Features](#-features)
- [Project Structure](#-project-structure)
- [Getting Started](#-getting-started)
  - [Prerequisites](#prerequisites)
  - [Compilation](#compilation)
  - [Running the Application](#running-the-application)
- [Screenshots & Diagrams](#-screenshots--diagrams)
- [Security Threat Model](#-security-threat-model)
- [Authors](#-authors)
- [License](#-license)

---

##  Overview

The **University Security System** is an academic cybersecurity project designed for the Faculty of Natural Sciences (FSHN). It demonstrates how object-oriented principles and UML modelling can be applied to simulate and manage a real-world institutional security environment.

The system tracks six distinct actor types — Students, Pedagogues, System Administrators, Security Officers, Attackers, and an Intrusion Detection System (IDS) Engine — all managed through a professional dark-themed Java Swing GUI.

> **Purpose:** To model, visualise, and manage the complete lifecycle of security actors and threats within a university network using OOP best practices.

---

##  System Architecture

```
┌─────────────────────────────────────────────────────────────────────┐
│                    FSHN University Security System                  │
│                                                                     │
│   ┌───────────┐  ┌───────────┐  ┌──────────────┐  ┌─────────────┐   │
│   │  Student  │  │  Pedagog  │  │ AdminSistemi │  │SecurityOffic│   │
│   │  (User)   │  │  (Staff)  │  │  (Sys Admin) │  │  (SOC Role) │   │
│   └─────┬─────┘  └─────┬─────┘  └──────┬───────┘  └──────┬──────┘   │
│         │              │               │                  │         │
│         └──────────────┴───────────────┴──────────────────┘         │
│                                    │                                │
│                         ┌──────────▼──────────┐                     │
│                         │   FSHNObjectManager  │  ← Swing GUI       │
│                         │   (Main Controller)  │                    │
│                         └──────────┬──────────┘                     │
│                                    │                                │
│              ┌─────────────────────┴──────────────────┐             │
│              │                                         │            │
│   ┌──────────▼──────────┐               ┌─────────────▼────────────┐│
│   │      Sulmuesi        │               │        IDSEngine         │ 
│   │  (Threat Actor)      │◄─ detected ───│  (Intrusion Detection)   │ 
│   └─────────────────────┘               └──────────────────────────┘│
└─────────────────────────────────────────────────────────────────────┘
```

### Technology Stack

| Layer | Technology |
|---|---|
| Language | Java 17+ |
| GUI Framework | Java Swing (JFrame, JTabbedPane, JTable) |
| Paradigm | Object-Oriented Programming (OOP) |
| Modelling | Unified Modelling Language (UML) |
| Build | `javac` (standard JDK compiler) |

---

##  Domain Classes (UML Actors)

### 1. `Student` — University Student
Represents a student account within the university portal. Tracks login attempts and account state to detect brute-force activity.

| Field | Type | Description |
|---|---|---|
| `studentId` | `String` | Unique identifier |
| `email` | `String` | Institutional email |
| `gjendjaLlogarise` | `String` | Account state: `Aktive`, `Locked`, `UnderReview` |
| `numTentativave` | `int` | Failed login attempts counter |
| `departamenti` | `String` | Faculty department |

**Key Behaviours:** `login()`, `logout()`, `verifiko2FA()`, `resetFjalëkalim()`, `rritTentativa()`

---

### 2. `Pedagog` — Academic Staff (Lecturer / Professor)
Models a teaching staff member with an academic title and department affiliation. Supports grade entry and material uploads.

| Field | Type | Description |
|---|---|---|
| `pedagogId` | `String` | Unique identifier |
| `departamenti` | `String` | Academic department |
| `titulliAkademik` | `String` | Academic title: `Prof.`, `Dr.`, `Asoc.` |

**Key Behaviours:** `login()`, `logout()`, `verifiko2FA()`, `futNote()`, `ngarkoMaterial()`

---

### 3. `AdminSistemi` — System Administrator
Manages the university IT infrastructure with tiered privilege levels.

| Field | Type | Description |
|---|---|---|
| `adminId` | `String` | Unique identifier |
| `nivelPrivilegjeve` | `int` | `1` = ReadOnly · `2` = Standard · `3` = Superadmin |

**Key Behaviours:** `login()`, `bllokIP()`, `riaktivizoLlogari()`, `konfigurojFirewall()`, `gjenerojRaport()`

---

### 4. `SecurityOfficer` — SOC Security Officer
Represents a Security Operations Centre analyst with professional certifications.

| Field | Type | Description |
|---|---|---|
| `officerId` | `String` | Unique identifier |
| `nivelAutorizimi` | `int` | `1` = Viewer · `2` = Analyst · `3` = Chief |
| `certifikimi` | `String` | Professional cert: `CISSP`, `CEH`, etc. |

**Key Behaviours:** `login()`, `analizoIncident()`, `vendosPolitike()`, `konfirmoAlert()`

---

### 5. `Sulmuesi` — Threat Actor (Attacker)
Models a malicious actor attempting various attacks against the university network.

| Field | Type | Description |
|---|---|---|
| `sulmuesId` | `String` | Attacker identifier |
| `ip` | `String` | Source IP address |
| `tipiSulmit` | `String` | `BruteForce`, `SQLInjection`, `Phishing` |
| `eshteIBllokuar` | `boolean` | Whether the attacker is blocked |
| `vendOrigjines` | `String` | Geographic origin |

**Key Behaviours:** `tentoBruteForce()`, `tentojSQLInj()`, `tentojPhishing()`, `blloko()`

---

### 6. `IDSEngine` — Intrusion Detection System
An automated engine that monitors network traffic, detects suspicious patterns, and auto-blocks malicious IPs.

| Field | Type | Description |
|---|---|---|
| `idsId` | `String` | Engine identifier |
| `pragAlertit` | `int` | Request-per-second threshold before alert |
| `statusDetektimit` | `String` | `Normal`, `Watch`, `Alert`, `Critical` |
| `ipBllokuaraTotale` | `int` | Total IPs blocked since start |

**Key Behaviours:** `monitoroTrafikun()`, `bllokIPAuto()`, `analizoPattern()`, `gjenerojLog()`, `aktivizo()`

---

##  Features

- ** Rich Dark-Themed GUI** — Professional Java Swing dashboard with tabbed panels, data tables, and colour-coded badges for each actor type
- ** Live Object Management** — Add, view, and inspect live in-memory objects for all 6 domain classes
- ** Role-Based Access Simulation** — Each actor class has its own privilege model (ReadOnly → Superadmin, Viewer → Chief)
- ** Automated IDS Engine** — Pattern-based detection (`SQL Injection`, `Brute Force`) with auto-IP blocking
- ** Threat Actor Modelling** — Full attacker lifecycle: creation, attack simulation, and IP block
- ** UML-Aligned Design** — Every class maps directly to a UML actor defined in the system's Use Case Diagram
- ** Project Documentation** — HTML project plan and SVG UML diagram included for academic reference
- ** 6 Dedicated Tabs** — One management tab per actor (Student, Pedagog, Admin, Officer, Attacker, IDS)

---

##  Project Structure

```
University-Security-System/
│
├── 📄 Student.java                        # Student domain model
├── 📄 Pedagog.java                        # Pedagogue/Staff domain model
├── 📄 AdminSistemi.java                   # System Administrator domain model
├── 📄 SecurityOfficer.java                # SOC Officer domain model
├── 📄 Sulmuesi.java                       # Threat Actor domain model
├── 📄 IDSEngine.java                      # Intrusion Detection System model
│
├── 📄 FSHNObjectManager.java              # Main GUI application (Swing JFrame)
│
├── 📊 fshn_security_usecase_uml.svg       # UML Use Case Diagram (SVG)
├── 📑 fshn_cybersecurity_project_plan.html # Full project plan document
├── 📑 fshn_usecase_dashboard.html         # Interactive UML dashboard
│
├── .gitignore                             # Excludes build artefacts (*.class, *.jar)
└── README.md                              # This file
```

---

##  Getting Started

### Prerequisites

- **Java Development Kit (JDK) 17 or higher**
  - Download: [https://adoptium.net](https://adoptium.net)
  - Verify installation: `java -version`

### Compilation

Clone the repository and compile all source files in a single command:

```bash
git clone https://github.com/orgito1015/University-Security-System.git
cd University-Security-System

# Compile all Java source files
javac *.java
```

### Running the Application

```bash
# Launch the Swing GUI application
java FSHNObjectManager
```

The application window will open with six tabs — one for each domain actor — pre-populated with sample data so you can explore all functionality immediately.

> **Tip:** The application is pre-seeded with demo objects for all 6 actor types. Use the **Add** buttons in each tab to create additional objects.

---

##  Screenshots & Diagrams

The repository includes the following visual references:

| File | Description |
|---|---|
| `fshn_security_usecase_uml.svg` | Full UML Use Case Diagram showing all actors and system interactions |
| `fshn_usecase_dashboard.html` | Interactive HTML dashboard for the Use Case specification |
| `fshn_cybersecurity_project_plan.html` | 4-phase project plan with deliverables and threat model |

Open the HTML files in any modern web browser and the SVG in any vector viewer (browser, Inkscape, etc.).

---

##  Security Threat Model

The system models the following real-world threat vectors relevant to a university environment:

| Threat | Actor | Mitigation Modelled |
|---|---|---|
| **Brute-Force Login** | `Sulmuesi` | Login attempt counter + account lock (`Student.rritTentativa()`) |
| **SQL Injection** | `Sulmuesi` | Pattern detection in `IDSEngine.analizoPattern()` |
| **Phishing** | `Sulmuesi` | User awareness + 2FA (`verifiko2FA()` in Student & Pedagog) |
| **Unauthorised Access** | External | Role-based privilege levels (Admin / Officer) |
| **Credential Theft** | External | Account state tracking (`gjendjaLlogarise`) |
| **Network Intrusion** | `Sulmuesi` | Automated IP blocking (`IDSEngine.bllokIPAuto()`) |

---


## 📜 License

This project is released under the [MIT License](LICENSE).

```
MIT License — Copyright (c) 2025 Orgito Leka & Mateos Xhukellari
Permission is hereby granted, free of charge, to any person obtaining a copy
of this software to use, copy, modify, merge, and distribute, subject to the
conditions of the MIT License.
```
