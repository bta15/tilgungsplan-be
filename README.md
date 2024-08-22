# Tilgungsplan - Backend

### Dieses kleine Programm erstellt einen Tilgungsplan unter Berücksichtigung der Eingaben
- des Darlehensbetrages
- des Sollzinses (in Prozent)
- der anfänglichen Tilgung (in Prozent)
- der Zinsbindung (in Jahren)
- den Tilgungsplan eines Darlehens mit gleichbleibender Rate erstellen kann
- Startdatum

### Benutzung:
- ```TilgungsplanApplication.java``` starten
- dann
  - entweder das Request-Beispiel in der Datei ```resources/static/http-beispiele.http``` ausführen
  - oder mit Postman etc. Requests an den Endpoint schicken
  - oder das Frontend nutzen

### Weitere Entwicklungsmöglichkeiten:
- [ ] mehr Tests
- [ ] Startdatum im Request optional machen und ansonsten das aktuelle Datum nehmen
- [ ] CORS richtig einstellen
- [ ] Bei Bedarf Datenbank
- [ ] Bei Bedarf Security
- [ ] Eingaben im Request validieren
- [ ] ...
