### Dieses kleine Programm erstellt das durch die Eingaben
- des Darlehensbetrages
- des Sollzinses (in Prozent)
- der anfänglichen Tilgung (in Prozent)
- der Zinsbindung (in Jahren)
- den Tilgungsplan eines Darlehens mit gleichbleibender Rate erstellen kann

### Benutzung:
- TilgungsplanApplication.java starten
- dann
  - entweder in der Datei ```resources/static/http-beispiele.http``` befindet sich ein Beispiel-Request
  - oder mit Postman etc. Requests an den Endpoint schicken
  - oder das/ein Frontend nutzen

### Weitere Entwicklungsmöglichkeiten:
- [ ] mehr Tests
- [ ] Geldbeträge in Cent/BigDecimal
- [ ] CORS richtig einstellen und Annotation aus der Ressource entfernen
- [ ] Bei Bedarf DB
- [ ] Bei Bedarf Security
- [ ] Eingaben im Request validieren
- [ ] ...