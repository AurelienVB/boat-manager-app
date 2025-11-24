# boat-manager-app

To start the application, first launch the backend project:
```
cd ./backend
mvn package
java -jar target/boat-api-0.0.1-SNAPSHOT.jar
```
(will listen on localhost:8080)

Then launch the frontend project:
```
cd ./frontend
npm install
npm run start
```
It should open automatically in your default browser (http://localhost:4200)

To login successfully:
```
username=admin
password=admin
```
