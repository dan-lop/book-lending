# Book Lending Service
Hier eine kleine Anleitung zum Starten und Benutzen dieses Service.

## Vorausgesetzte Software
Installiere zunächst folgende Software (getestete Versionen in den Klammern)

- Maven (3.8.6)
- Java JDK (17)
- PostgreSQL (14.5)

## Datenbank einrichten (Annahme: Ubuntu-Linux als Betriebssystem)
1. Terminal öffnen und zum automatisch generierten user "postgres" wechseln mit: `sudo -i -u postgres`
2. Im Terminal die psql CLI starten mit dem Befehl: `psql`
3. Das Passwort des Users "postgres" zu "test123" ändern: `ALTER USER postgres WITH PASSWORD 'test123';`
4. Die Datenbank erstellen mit: `CREATE DATABASE librarydb;`

Die Tabelle(n) werden später automatisch mit Flyway erstellt.

## Abhängigkeiten Installieren und Anwendung Starten
1. Lade die Abhängigkeiten herunter und baue das Projekt mit folgendem Befehl: `mvn install`
- Oder falls die Datenbank nicht läuft kann dafür auch die h2 DB verwendet werden: `mvn install -DargLine="-Dspring.profiles.active=it"`
2. Starte die Anwendung mit: `java -jar target/book-lending-0.0.1-SNAPSHOT.jar`

## Dokumentation mit Swagger
Die Schnittstellen und Benutzung dieser wurde mit Swagger dokumentiert. Die Schnittstellen können darüber auch benutzt werden.

Wenn die Anwendung läuft, kann die Swagger-Dokumentation direkt unter [localhost:8080](http://localhost:8080/) aufgerufen werden.
