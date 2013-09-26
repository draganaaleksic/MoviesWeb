1.	Opis problema
Predmet ovog rada je izrada aplikacije koja vr�i ekstrakciju strukturiranih podataka o filmovima i �uva ih u RDF bazu. Podaci o filmovi se nalaze na web stranici  IMDB . Podaci o filmovima opisani su pomo�i meta podataka koji su umetnuti u sam HTML stranice a sami meta podaci su su definisani pomo�u Microdata standarda. Ekstrahovane podatke je zatim potrebno sa�uvati u RDF repozitorijumu (bazu podataka) i omogu�iti da im se pristupa putem REST servisa.
Radi kreiranja aplikacije, potrebno je bilo ispuniti slede�e zahteve:
�	kreiranje web crawlera koji prikuplja stranice sa sajta imdb.com
�	analiziranje strukture web stranice filmova kako bi se ekstrahovali umetnuti Microdata podaci o filmu,
�	kreiranje RDF baze i �uvanje ekstrahovanih podataka o filmu u skladu sa schema.org RDF vokabularom,
�	omogu�avanje pristupa podacima u bazi pomo�u odgovaraju�ih REST servisa.

2.	Domenski model
Kreiran je domenski model u skladu sa RDF vokabularom Movie  i predstavljen je na slede�em dijagramu (slika 1):
 
![Slika 1 - Domenski model](docs/images/rdfmodel.jpg)
Slika 1: Domenski model

Klasa Movie sadr�i osnovne podatke o filmu. Ona poseduje podatke o nazivu filma, adresi, opisu, trajanju filma, nagradama, �anrovima, datumu objavljivanja kao i o slici filma. Informacije o publici filma iskazuju se preko klase Audience koja sadr�i adresu publike. Podaci o glumcima i re�iseru filma se iskazuju preko klase Person koja sadr�i ime i adresu osobe.
Klasa Organization sadr�i informacije o imenu i adresi organizacije koja kreira film.
Klasa AggregateRating sadr�i podatke o trenutnoj oceni filma, tako �to vodi ra�una o broju komentara, broju pregleda, prose�noj oceni svih komentara, najboljoj i najgoroj oceni filma.
Klasa Review predstavlja komentar korisnika na odre�eni film. Ona sadr�i  podatke o  autoru, nazivu komentara, datumu objavljivanja i opisu, odnosno samom tekstu komentara. Prilikom ostavljanja kometara, korisnik daje i ocenu samog filma, koja se predstavlja klasom Rating. Ona sadr�i podake o najmanjoj i najve�oj mogu�oj oceni, kao i o oceni koju je dao autor komentara.
3.	Re�enje
U skladu sa postavljenim problemom i specifi�nim zahtevima, kreirana je aplikacija koja omogu�ava kreiranje repozitorijuma transformacijom postoje�ih podataka. Konkretno, aplikacija prikuplja meta podatke o filmovima predstavljenim preko Microdata standarda sa web sajta IMDB. Na osnovu prikupljenih podataka kreira odgovaraju�e objekte domenskog modela, a zatim te objekte �uva u lokalnu RDF bazu. Aplikacija dalje omogu�ava pristup sa�uvanim podacima pomo�u RESTful servisa.
Aplikacija omogu�ava  dva REST servisa:
GET/api/movies - vra�a podatke o filmovima. Opcioni parametri su:
�	name - re� u nazivu filma
�	genre - �eljeni �anr (moguce vrednosti: comedy, drama, romace, mystery, history ..)
�	director - rec u nazivu rezisera
�	actor - rec u nazivu glumca
�	minAggregateRating  - minimalna ocena koju bi film trebao da ima
�	minDuration - minimalno trajanje filma
�	maxDuration - maksimalno trajanje filma
�	hasAward - samo nagradjivani filmovi (moguce vrednosti true, false)
�	minReleaseYear - minimalna godina objavljivanja
�	maxReleaseYear - maksimalna godina objavljivanja
�	limit � broj rezultata

  Primer poziva ovog servisa:
GET/api/movies?name=Igre&genre=Adventure&minReleaseYear=2010

GET/api/movies/id/reviews   vraca komentare filma sa zadatim id-jem. Opcioni parametri su: 
�	name rec u nazivu komentara 
�	author rec u nazivu autora

Primer poziva ovog servisa:
GET/api/movies/b6086735-d0fd-4e2b-8adc-6cdd98becc36/reviews?name=scary&author=www

4.	Tehni�ka realizacija
Aplikacija je ra�ena u programskom jeziku Java, u razvojnom okru�enju Eclipse. 
U aplikaciji je kori��ena Jsoup biblioteka za analiziranje web stranica i prikupljanje podataka sa njih. Jsoup biblioteka  omogu�ava parsiranje HTML stranica pomo�u pogodnog API-a za ekstrakciju podataka, kao i pretragu i manipulaciju podacima. Ona tako�e i omogu�ava pristup �eljenim DOM elementima.
Za mapiranje Java objekata u RDF putem anotacija kori��ena je Jenabean biblioteka. Biblioteka pru�a mogu�nost da se Java klase kreiraju na klasi�an na�in, a zatim se dodavanjem odgovaraju�ih anotacija defini�e na�in na koji �e se one mapirati u RDF klase
Za skladi�tenje podataka u RDF repozitorijum kori��ena je TDB komponenta Jena framework-a, koja, pored skladi�tenja, omogu�ava i izvr�avanje razli�itih vrsta SPARQL upita nad podacima u RDF formatu. 
Obezbe�ivanje i rad sa RESTful servisima implementiran je pomo�u Jersey framework-a, koji olak�ava kreiranje web servisa u Javi. Web servisi se kreiraju putem anotacija, kojima se defini�e vrsta HTTP zahteva na koju taj servis odgovara kao i putanja do odgovaraju�eg resursa. Na osnovu anotacija, odre�ene klase su predstavljene kao resursi, koji sadr�e anotacijama ozna�ene metode koje prihvataju i obra�uju HTTP zahteve. 
