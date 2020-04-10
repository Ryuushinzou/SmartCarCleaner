BACKEND - java
- user
- masini/tip masina
- programare/spalare(la cea mai apropiata locatie)
- spalatoria/locatia cu capacitatile(locuri + resurse) ei
- resurse(sapun etc) + aprovizionare
- sistem plata online(endpoint si simulat)
- sistem bonusare
- sistem programare eficienta la cel care termina cel mai repede
* simulator aprovizionare

MOBILE APP - kotlin
- auth
- contul tau(+ autovehicule) + profil
- creeaza programare noua
- programari(trecute si viitoare) cu filtre
- geolocalizare
- pct acumulate(pt sistem bonusare)
- ecran plata + ecran cod confirmare

WEB APP - REACT? 
- log in
- admin playground(CRUD pe tot)
- data analysis(interogare mai complexa si afisare rapoarte)

EMBEDDED - java(javafx)
- interfata simpla in care sa bagi codul
- te trimite la rampa corespunzatoare programarii

ENTITIES

USER
- user id
- name
- email
- number
- password
- car ids
- user type(user, admin, analyst)

VEHICLE
- car id
- model
- make
- type(berlina, motor, tir, duba etc)

WASHING STATION
- id
- location(geo)
- slots(no ramps)/capacity
- resources(soap, wax etc)
- status(open/closed/without resources)

APPOINTMENT
- id
- user id
- car id
- location
- options
- price
- needed resources
- start time
- end time
- status(to be, payed/not payed, done etc)

SUPPLY
- id
- name
- description
- washing station id
- date
- resources(resource id + quantity)
- price

RESOURCE
- id
- name
- description
- quantity
