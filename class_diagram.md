# FSHN — Sistema e Sigurisë Universitare
## Diagrama e Klasave UML

```mermaid
classDiagram
    direction TB

    class Actor {
        <<abstract>>
        +login(email, password)* bool
        +logout()* void
        +verifiko2FA(kod)* bool
        +roli()* str
    }

    class Student {
        -String studentId
        -String emri
        -String mbiemri
        -String email
        -String fjalekalimi
        -String roli
        -String gjendjaLlogarise
        -int numTentativave
        -Date dateRegjistrimi
        -String departamenti
        +login(email, pass) bool
        +logout() void
        +verifiko2FA(kod) bool
        +resetFjalekalim() void
        +rritTentativa() void
        +resetTentativa() void
    }

    class Pedagog {
        -String pedagogId
        -String emri
        -String mbiemri
        -String email
        -String fjalekalimi
        -String roli
        -String departamenti
        -String titulliAkademik
        -String gjendjaLlogarise
        -Date dateRegjistrimi
        +login(email, pass) bool
        +logout() void
        +verifiko2FA(kod) bool
        +futNote(nota) void
        +ngarkoMaterial(mat) void
    }

    class SecurityOfficer {
        -String officerId
        -String emri
        -String mbiemri
        -String email
        -String fjalekalimi
        -String roli
        -int nivelAutorizimi
        -String gjendjaLlogarise
        -String certifikimi
        -Date dateRegjistrimi
        +login(email, pass) bool
        +analizoIncident(id) void
        +vendosPolitike(pol) void
        +konfirmoAlert(id) void
        +getNivelLabel() String
    }

    class AdminSistemi {
        -String adminId
        -String emri
        -String mbiemri
        -String email
        -String fjalekalimi
        -String roli
        -int nivelPrivilegjeve
        -String gjendjaLlogarise
        -Date dateRegjistrimi
        +login(email, pass) bool
        +bllokIP(ip) void
        +riaktivizoLlogari(id) void
        +konfigurojFirewall() void
        +gjenerojRaport() void
        +getNivelLabel() String
    }

    class Sulmuesi {
        -String sulmuesId
        -String ip
        -String tipiSulmit
        -int numTentativave
        -boolean eshteIBllokuar
        -Date timestampSulm
        -String vendOrigjines
        -String userAgent
        +tentoBruteForce() void
        +tentojSQLInj() void
        +tentojPhishing() void
        +blloko() void
    }

    class IDSEngine {
        -String idsId
        -boolean eshteAktiv
        -int pragAlertit
        -int kerkesaPerMinute
        -int ipBllokuaraTotale
        -String versioni
        -Date ngritjeHeren
        -String statusDetektimit
        +monitoroTrafikun() void
        +bllokIPAuto(ip) void
        +analizoPattern(req) boolean
        +gjenerojLog() void
        +aktivizo() void
        +caktivizo() void
    }

    class FSHNObjectManager {
        <<JFrame>>
        +List~Student~ studentet
        +List~Pedagog~ pedagoget
        +List~AdminSistemi~ adminet
        +List~SecurityOfficer~ officeret
        +List~Sulmuesi~ sulmuesit
        +List~IDSEngine~ idsEngines
        +FSHNObjectManager()
        +seedData() void
    }

    Actor <|-- Student
    Actor <|-- Pedagog
    Actor <|-- SecurityOfficer
    Actor <|-- AdminSistemi

    FSHNObjectManager "1" o-- "0..*" Student
    FSHNObjectManager "1" o-- "0..*" Pedagog
    FSHNObjectManager "1" o-- "0..*" AdminSistemi
    FSHNObjectManager "1" o-- "0..*" SecurityOfficer
    FSHNObjectManager "1" o-- "0..*" Sulmuesi
    FSHNObjectManager "1" o-- "0..*" IDSEngine

    IDSEngine ..> Sulmuesi : detects / blocks
```

### Legjenda e Marrëdhënieve

| Simboli | Lloji | Përshkrimi |
|---------|-------|------------|
| `<\|--` | Trashëgimi (Generalizim) | Student, Pedagog, SecurityOfficer dhe AdminSistemi zgjerojnë klasën abstrakte Actor |
| `o--`   | Agregim (1 me shumë) | FSHNObjectManager mban lista të objekteve të çdo klase |
| `..>`   | Varësi (Dependency) | IDSEngine zbule dhe bllokon objekte Sulmuesi |

### Klasat dhe Rolet e Tyre

| Klasa | Roli |
|-------|------|
| `Actor` | Klasë abstrakte bazë për të gjithë aktorët me llogari (Python) |
| `Student` | Studentët e universitetit; login me 2FA, kontrolli i tentativave |
| `Pedagog` | Pedagogët; fu​t nota, ngarkon materiale |
| `SecurityOfficer` | Oficeri i sigurisë; analizon incidente, vendos politika |
| `AdminSistemi` | Admini i sistemit; bllokon IP, konfiguron firewall |
| `Sulmuesi` | Aktori kërcënues i jashtëm; BruteForce / SQLInjection / Phishing |
| `IDSEngine` | Sistemi i zbulimit të ndërhyrjeve (IDS); monitoron trafik, bllokon IP automatikisht |
| `FSHNObjectManager` | Menaxheri GUI (Swing/JFrame); agregon të gjitha objektet e sistemit |
