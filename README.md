# Reto técnico Yape 
***
## Contenido
1. [Informacion General](#informacion-general)
2. [Pasos del Escenario de Prueba](#pasos-del-escenario-de-prueba)
3. [Instalacion](#Instalacion)
4. [Tecnologias](#tecnologias)
5. [FAQs](#faqs)

### Informacion General
***
Las pruebas estan Basada en una aplicacion de reserva, en este escenario se tomo en cuenta la reservacion de habitacion para una familia de 2 adultos y un niño a la ciudad de Cusco, Peru

### Pasos del Escenario de Prueba
***
```
Scenario: book a reservation
    Given User is on Main app screen
    Then User sets Destination textbox as CUSCO
    And User sets travel duration dates From: 14 February 2023 to 28 February 2023
    And User sets following Occupancy values
      | room       | 1 |
      | adults     | 2 |
    And User sets following Children values
      | 5 years old |
    Then User click on Search button
    When User selects option number 1 of result list
  ```
* **Given User is on Main app screen** 
Verifica que se encuentra en la pantalla principal de la aplicacion

* **Then User sets Destination textbox as CUSCO**
Se selecciona el destino de preferencia

* **And User sets travel duration dates From: 14...**
Se elige la fecha desde y hasta de la reserva

* **And User sets following Occupancy values**
Se selecciona la cantidad de Habitaciones y adultos para la reserva
* **And User sets following Children values**
Se introduce la cantidad de niños y la edad correspondiente
* **Then User click on Search button**
Hace Tap en buscar
* **When User selects option number 1 of result list**
Se elige el lugar de preferencia para la reserva

### Tecnologías
***
El proyecto esta basado en Cucumber Archetype
*https://cucumber.io/docs/guides/10-minute-tutorial/?lang=java*

```powershell
mvn archetype:generate                                  \
  -DarchetypeGroupId=io.cucumber                        \
  -DarchetypeArtifactId=cucumber-archetype              \
  -DarchetypeVersion=7.11.0                             \
```
### Intalación
***
**Repositorio**
```
$ git clone https://github.com/ortizmarijo/desafioYape.git
```
**Maven**
```
mvn clean intall
```
* Ejecicion de test
```
mvn test 
```