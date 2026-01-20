# CLAUDE.md - Upute za testiranje projekta turističkih aranžmana

## Pregled projekta

Ovo je Java projekt za upravljanje turističkim aranžmanima i rezervacijama. Projekt koristi nekoliko uzoraka dizajna:
- **Visitor** - za pretraživanje podataka
- **Memento** i **Command** - za spremanje/vraćanje stanja aranžmana
- **Observer** - za pretplate na promjene aranžmana
- **Strategy** - za ograničenje broja rezervacija
- **Null Object** - kada nema strategije ograničenja

---

## Pokretanje programa

Program se pokreće s opcionalnim zastavicama:
```
java -jar /mmarkovin21_zadaca_3_app/target/mmarkovin21_zadaca__app.jar [--ta] <datoteka aranzmana> [--rta] <datoteka rezervacija> [--jdr | --vdr]
```

- `--ta` - opcionalan parametar učitavanja turističkih aranžmana iz datoteke
- `--rta` - opcionalan parametar učitavanja rezervacija turističkih aranžmana iz datoteke
- `--jdr` - Strategija ograničenja rezervacija (jedna aktivna rezervacija po osobi)
- `--vdr` - Strategija ograničenja rezervacija (više aktivnih rezervacija po osobi)
- **Bez zastavice** - Primjenjuje se Null Object uzorak (nema ograničenja osim onih definiranih u aranžmanu)

---

## Popis svih komandi

### 1. ITAK [od do] - Pregled turističkih aranžmana

**Sintaksa:**
```
ITAK
ITAK 1.10.2025. 31.12.2025.
```

**Opis:** Ispisuje tablicu svih aranžmana. Opcionalno se može zadati razdoblje.

**Stupci tablice:** Oznaka, Naziv, Početni datum, Završni datum, Vrijeme kretanja, Vrijeme povratka, Cijena, Min broj putnika, Maks broj putnika, Status

---

### 2. ITAP oznaka - Pregled pojedinog aranžmana

**Sintaksa:**
```
ITAP 1
```

**Opis:** Ispisuje detalje odabranog turističkog aranžmana.

**Podaci:** Oznaka, Naziv, Program, Početni datum, Završni datum, Vrijeme kretanja, Vrijeme povratka, Cijena, Min broj putnika, Maks broj putnika, Broj noćenja, Doplata za jednokrevetnu sobu, Prijevoz, Broj doručka, Broj ručkova, Broj večera, Status

---

### 3. IRTA oznakaAranžmana [PA|Č|O|OD] - Pregled rezervacija za aranžman

**Sintaksa:**
```
IRTA 1 PA
IRTA 1 Č
IRTA 1 PAČO
IRTA 1 ODO
```

**Filteri statusa:**
- `PA` - Primljene i aktivne rezervacije
- `Č` - Rezervacije na čekanju
- `O` - Otkazane rezervacije
- `OD` - Odgođene rezervacije

**VAŽNO:** Filteri se mogu kombinirati (npr. `PAČO` = PA + Č + O)

**Stupci tablice:** Ime, Prezime, Datum i vrijeme, Vrsta, (Datum i vrijeme otkaza - za otkazane)

---

### 4. IRO ime prezime - Pregled rezervacija za osobu

**Sintaksa:**
```
IRO Lea Novak
```

**Opis:** Ispisuje sve rezervacije određene osobe.

**Stupci tablice:** Datum i vrijeme, Oznaka aranžmana, Naziv aranžmana, Vrsta

---

### 5. DRTA ime prezime oznaka datum vrijeme - Dodavanje rezervacije

**Sintaksa:**
```
DRTA Damir Klarić 3 21.10.2025 10:27:41
```

**Opis:** Dodaje novu rezervaciju za osobu na određeni aranžman.

**VAŽNO:**
- Ispisuje poruku o uspješnosti dodavanja
- Ograničenja ovise o aktivnoj strategiji (--jdr, --vdr, ili Null Object)
- Provjeriti ograničenja iz samog aranžmana (min/maks broj putnika)

---

### 6. ORTA ime prezime oznaka - Otkaz rezervacije

**Sintaksa:**
```
ORTA Lea Novak 3
```

**Opis:** Otkazuje rezervaciju osobe za određeni aranžman.

**VAŽNO:** Ispisuje poruku o uspješnosti otkaza.

---

### 7. OTA oznaka - Otkaz turističkog aranžmana

**Sintaksa:**
```
OTA 3
```

**Opis:** Otkazuje cijeli turistički aranžman.

**VAŽNO:**
- Automatski se otkazuju SVE rezervacije tog aranžmana
- Ažuriraju se podaci o rezervacijama kod svih osoba
- Ispisuje poruku o uspješnosti otkaza

---

### 8. IP [N|S] - Postavljanje načina sortiranja

**Sintaksa:**
```
IP N
IP S
```

**Opcije:**
- `N` - Kronološki redoslijed (stari → novi) - **INICIJALNO**
- `S` - Obrnuti kronološki redoslijed (novi → stari)

---

### 9. BP [A|R] - Brisanje podataka

**Sintaksa:**
```
BP A
BP R
```

**Opcije:**
- `A` - Briše sve aranžmane I njihove rezervacije
- `R` - Briše samo sve rezervacije

---

### 10. UP [A|R] nazivDatoteke - Učitavanje iz datoteke

**Sintaksa:**
```
UP A DZ_3_aranzmani_1.csv
UP R DZ_3_rezervacije_1.csv
```

**Opcije:**
- `A` - Učitava aranžmane
- `R` - Učitava rezervacije

---

### 11. ITAS [od do] - Statistički podaci

**Sintaksa:**
```
ITAS
ITAS 1.10.2025. 31.12.2025.
```

**Opis:** Ispisuje statističke podatke o aranžmanima.

**Stupci tablice:**
- Ukupan broj rezervacija
- Broj aktivnih rezervacija
- Broj rezervacija na čekanju
- Broj odgođenih rezervacija
- Broj otkazanih rezervacija
- Ukupan prihod = broj aktivnih rezervacija × cijena aranžmana

---

### 12. PPTAR [A|R] riječ - Pretraživanje (Visitor uzorak)

**Sintaksa:**
```
PPTAR A skijanje
PPTAR R Lea
```

**Opcije:**
- `A` - Pretražuje aranžmane (naziv i/ili opis programa)
- `R` - Pretražuje rezervacije (ime i/ili prezime osobe)

**VAŽNO:**
- Razlikuju se VELIKA i mala slova
- Traži se kao cijela riječ ILI dio riječi

---

### 13. PSTAR oznaka - Spremanje stanja (Memento + Command)

**Sintaksa:**
```
PSTAR 3
```

**Opis:** Sprema trenutno stanje aranžmana i SVIH njegovih rezervacija u spremište.

---

### 14. VSTAR oznaka - Vraćanje stanja (Memento + Command)

**Sintaksa:**
```
VSTAR 3
```

**Opis:** Vraća zadnje spremljeno stanje aranžmana i njegovih rezervacija.

**KRITIČNE NAPOMENE za testiranje:**
1. Ako aranžman NIJE imao pretplate kad je spremljen, nakon VSTAR neće imati pretplate
2. Ako se pozove `BP A` (brisanje svih aranžmana), zatim `VSTAR 1` - vraća se stari aranžman
3. Ako se pozove `BP A`, zatim `UP A` s novim aranžmanom oznake 1, zatim `VSTAR 1` - vraća se STARI aranžman (ne novi učitani!)

---

### 15. PTAR ime prezime oznaka - Pretplata na obavijesti (Observer)

**Sintaksa:**
```
PTAR Lea Novak 3
```

**Opis:** Osoba se pretplaćuje na informacije o promjenama aranžmana i njegovih rezervacija.

**VAŽNO:**
- Osoba dobiva obavijesti za BILO KOJU promjenu na aranžmanu i SVIM njegovim rezervacijama
- Osoba NE mora imati vlastitu rezervaciju na tom aranžmanu
- GDPR: U obavijestima se NE prikazuju privatni podaci drugih osoba iz rezervacija
- Format obavijesti: podaci o osobi → oznaka aranžmana → opis promjene

---

### 16. UPTAR - Ukidanje pretplate

**Sintaksa:**
```
UPTAR Lea Novak 3
UPTAR 3
```

**Opcije:**
- `UPTAR ime prezime oznaka` - Ukida pretplatu jedne osobe
- `UPTAR oznaka` - Ukida pretplate SVIH osoba za taj aranžman

---

### 17. Q - Prekid programa

**Sintaksa:**
```
Q
```

---

## Testne datoteke

### Profesorove datoteke:
- `DZ_1_aranzmani.csv` - Osnovni aranžmani za testiranje
- `DZ_1_rezervacije.csv` - Osnovne rezervacije
- `DZ_1_rezervacije_1.csv` - Dodatne rezervacije za testiranje

### Specijalne testne datoteke:
- `DZ_3_rezervacije_osobe.csv` - **Koristi se za testiranje `--vdr` opcije**

**NAPOMENA:** Svaka datoteka sadrži različite podatke! Potrebno je pregledati sadržaj svake datoteke prije testiranja kako bi se razumjeli očekivani rezultati.

---

## Logičke stvari na koje treba paziti pri testiranju

### 1. Strategije ograničenja rezervacija
- Bez zastavice = Null Object (nema ograničenja osim iz aranžmana)
- `--jdr` = jedna strategija ograničenja
- `--vdr` = druga strategija ograničenja
- Testirati sve tri varijante!

### 2. Memento/Command (PSTAR/VSTAR)
- Stanje uključuje I pretplate - ako ih nije bilo, neće ih biti nakon vraćanja
- Vraćanje radi čak i nakon brisanja svih aranžmana (BP A)
- Vraćanje PREPISUJE novi aranžman s istom oznakom

### 3. Observer (PTAR/UPTAR)
- Pretplate su NEZAVISNE od rezervacija
- GDPR zaštita - ne otkrivati privatne podatke
- Obavijesti za SVE promjene na aranžmanu i rezervacijama

### 4. Kaskadno brisanje
- `OTA oznaka` otkazuje aranžman I sve njegove rezervacije
- `BP A` briše sve aranžmane I sve rezervacije
- `BP R` briše samo rezervacije

### 5. Sortiranje (IP)
- Inicijalno je `N` (kronološki)
- Promjena utječe na SVE ispise tablica

### 6. Filteri za IRTA
- Mogu se kombinirati: `PA`, `Č`, `O`, `OD`, `PAČO`, `ODO`, itd.
- Svaki filter dodaje odgovarajuće rezervacije u ispis

### 7. Pretraživanje (PPTAR)
- Case-sensitive (razlikuje velika/mala slova)
- Traži i cijele riječi i dijelove riječi

### 8. Datumi
- Format: `dd.MM.yyyy.` (s točkom na kraju)
- Vrijeme: `HH:mm:ss`

---

## Scenariji za testiranje

1. Pokrenuti program s različitim kombinacijama zastavica (`--ta`, `--rta`, `--jdr`, `--vdr`) i provjeriti ispravno učitavanje podataka.
2. Testirati sve komande redom, provjeravajući ispravnost ispisa i funkcionalnosti.
3. Posebno testirati `DRTA` s različitim strategijama ograničenja
4. Testirati `PSTAR` i `VSTAR` komande, uključujući slučajeve s brisanjem i ponovnim učitavanjem aranžmana.
5. Testirati `PTAR` i `UPTAR` komande, provjeravajući ispravnost obavijesti i GDPR zaštitu.
6. Testirati kaskadno brisanje s `OTA` i `BP` komandama.
7. Testirati sortiranje s `IP` komandom.
8. Testirati filtere s `IRTA` komandom.
9. Testirati pretraživanje s `PPTAR` komandom.
10. Provjeriti ispravnost formata datuma i vremena u svim relevantnim komandama.
11. Izvršiti kombinirane testove koji uključuju više komandi i provjeriti međusobnu interakciju funkcionalnosti.
12. Dokumentirati sve pronađene greške ili neočekivano ponašanje za daljnju analizu i ispravke.
