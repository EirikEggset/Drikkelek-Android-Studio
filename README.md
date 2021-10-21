# Drikkelek-Android-Studio

Dette er en App med flere spill, ment for når man er ute og drikker.
Prosjektet her lagde jeg for å lære meg Android studio og Java, dermed har jeg kommentert mindre og skrevet mindre ryddig kode her en jeg gjør nå. 
Prosjektet her er satt på vent da jeg jobber med å bygge den samme appen fra bunnen av i React Native.

FOR Å KOMME TIL relevante filer (JAVA, XML...): 
  * JAVA-filer: 'app/src/main/java/com/example/drikkelek'
  * Andre filer (i undermapper): 'app/src/main/java/com/example/res'

Appen har 4 spilltyper:

Blanding:
  * Spillet starter med at brukeren legger inn navnene til alle spillerne
  * Deretter velger man hvilke type spørsmål man vil ha (Per dags dato er kategoriene: "Vorset", "Fylla", og "Drøy")
  * Tilfeldige spørsmål blir valgt fra kategorien. Spørsmålene er lagret i csv filer basert på spørsmålstypene: Kategorier, Vanlige spørsmål, pekelek, regel og tommel opp eller ned
  * Spørsmålene vises ett og ett, der tilfeldige navn blir brukt. Alle reglene som er i gang vises på bunnen av skjermen.
  * Appen husker hvilke spørsmål som er tatt til neste runde
  
100 Spørsmål:
  * Variant av Blanding, bruker mye lik kode og samme hovedfil
  * Spillet velger 100 tilfeldige spørsmål (20 akkuratt nå for testing)
  * En person starter, gjerne med en gjenstand i hånda (typisk en snusboks). Appen kommer med en påstand/spørsmål, personen velger (kaster gjenstanden/snusboksen til) den som 
  passer utsagnet her best.
 
Ring of Fire:
  * Spilleren kan trykke på en bunke spillekort. Hver gang vil det komme opp et tilfeldig kort og en beskrivelse.
  * Alle korttyper har en egen betydning, som blir skrevet. Spillet fortsetter til fire konger er valgt.
  * Kun alle ruterkortene er lagd inn i denne versjonen
  
Terning (IKKE FERDIG!!):
  * En terning vises på skjermen. Når du trykker spilles en animasjon så får du et tilfeldig tall.
  * Spillet går ut på å skru på en sang (OPUS anbefales). Alle spillerene sitter i ring og en eller flere har en terningapp. Du gir mobilen din til venstre for deg 
  når du får en 6. De med terning på droppet til sangen må drikke resten av glasset/enheten sin.
  * Jobber med å laste inn et 3D objekt og animere det.
