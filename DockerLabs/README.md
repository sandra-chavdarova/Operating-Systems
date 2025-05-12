# Exercise 1

Потребно е да се изврши nginx контејнеризација брз база на Debian основа debian:11-slim и да се овозможи хостирање на дадената датотека именувана како products.txt. Следете ги следните чекори еден по еден:

1. Контејнеризацијата направете ја преку интерактивен режим. Именувајте ја новата Docker слика како nginx-product-host-i. 

2. Креирајте и стартувајте нов контејнер nginx-product-host-i-container врз база на новокреираната слика. Овозможете портата 80 на контејнерот да биде достапна преку порта 8080 на домаќинот. По стартувањето, проверете дали контејнерот е активен и дали портите се успешно мапирани.

3. Повторете го креирањето на Docker Image на nginx брз база на Debian основа debian:11-slim, но сега преку Dockerfile. Именувајте ја сликата како nginx-product-host.

4. Креирајте контејнер од новокреираната слика, именувајте го како nginx-product-host-container и овозможете портата 80 на контејнерот да биде достапна преку порта 8081 на домаќинот. По стартувањето, проверете дали контејнерот е активен и дали портите се успешно мапирани.

5. Рачно ископирајте ја датотеката products.txt од хостот во фолдерот /var/www/html/ на двата контејнери (nginx-product-host-container, nginx-product-host-i-container)  со помош на командата docker cp. 

6. Со помош на една од командите wget или curl во домаќинот проверете дали датотеката products.txt е достапна на двете локации http://localhost:8080/products.txt и http://localhost:8081/products.txt. Доколку е успешно, повиците треба да вратат HTTP статус код 200 и да ја преземат датотеката локално во локалниот именик, доколку не е поставена опцијата -o.

7. Доколку претходните чекори успешно ги имплементиравте, направете стопирање и отстранување на двата контејнери nginx-product-host-container и nginx-product-host-i-container.

8. Креирајте нов именик во домаќинот именуван како html. Ископирајте ја датотеката products.txt во новокреираниот именик.

9. Креирајте и стартувајте го контејнерот nginx-product-host-container, но сега потребно е да мапирате volume на тој начин што новокреираниот именик кај домаќинот html ќе се мапира на патеката /var/www/html/ кај контејнерот.

	- внимавајте да не ги заборавите при стартување да овозможете портата 80 на контејнерот да биде достапна преку порта 8081 на домаќинот

10. Проверете дали датотеката е достапна на локација http://localhost:8081/products.txt.

11. Потребно е да се контејнеризира Java апликацијата (ProductsManagement.java) со помош на Dockerfile. Апликацијата овозможува преглед на содржината на датотеката products.txt на попрегледен начин со тоа што ја изминува линија по линија и ги печати сите ќелии во рамки на редицата. Секоја ќелија е одделена со точка-запирка (;).

12. Изградете нова Docker слика врз база на новокреираниот Dockerfile со име products-viewer.

13. При стартувајте, направете мапирање на volume со тоа што именикот кај домаќинот html ќе се мапира на патеката /var/www/html/ кај контејнерот. Името на контејнерот нека биде products-viewer-container.

14. Проверете дали листањето на продуктите преку Java апликацијата е успешно.

15. Изменете го Java кодот, со што променливата path ќе се исчитува од опкружувачката променлива PRODUCT_FILE_PATH. 

16. Стопирајте и отстранете го контејнерот products-viewer-container и стартувајте го пак со тоа што ќе ја додадете вредност на опкружувачката променлива PRODUCT_FILE_PATH со помош на опцијата -e кај docker run командата.

17. Проверете дали успешно ќе се излистаат продуктите во датотеката.

Излезот од извршувањата на сите команди го ставате во формата подолу.

_products.txt_
```
Fridge;$600;5
TV;$1500;6
Microwave;$200;10
```

_ProductsManagement.java_
```
import java.io.*;
public class ProductsManagement {
    public static void main(String[] args) {
        String path = "/var/www/html/products.txt";
        BufferedReader reader = null;
        String line = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
            while ((line = reader.readLine())!=null) {
                String[] cells = line.split(";");
                if (cells.length != 3) {
                    throw new RuntimeException("Invalid row!");
                }
                System.out.printf("Product Name: %s\n", cells[0]);
                System.out.printf("Product Price: %s\n", cells[1]);
                System.out.printf("Product Quantity: %s\n", cells[2]);
                System.out.println("\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

# Exercise 2
Потребно е да се направи апликација наречена ServerRoomTemperature. За таа цел има сенозори кои прибираат податоци за температурата во различни серверски соби, се со цел да се мониторира температурата во податочниот центар.

Барања на задачата:
Да се направат два Docker Compose сервиси. 

Да се креира Јава сервис TemperatureSensor.
- запишува 5 нови бројки во датотека temperature.txt. 
  - Дали ќе ги запишувате бројките во датотеката со празно место или нов ред е оставено на Вас.
- новите бројки се рандом генрирани во рангот [5, 50].
- запишува нови бројки на секој 30 секунди.

Да се креира Јава сервис TemperatureMonitor 
- прави average на вредностите од temperature.txt
- запишува во датотека temperaturelevel.txt колкаво е нивото на температурата.
  - Дали ќе ги запишувате нивото во датотеката со празно место или нов ред е оставено на Вас.
- нивото се пресметува според просечната температура и може да е Low, Medium и High. Low (5 °C до 19 °C), Medium (19 °C to 35 °C), High (над 35 °C)
- запишува колкаво е нивото на секој 60 секунди.

Во формата поставете ги командите и излезот од командна линија.
Исто така во формата прикачете ги TemperatureSensor.java, TemperatureMonitor.java, Dockerfiles, docker-compose.yml.
После завршувањето на задачата стопирајте ги контејнерите!

# Exercise 3
Да се надгради задача 2 - (Docker Compose 1).

Барања:
1. Креирајте volumes кои што се викаат (temperature, temperaturelevel)
2. Во temperature се наоѓа temperature.txt а во temperaturelevel se наоѓа temperaturelevel.txt
3. Креирајте една мрежа со име: temperature-level-network, која го има како network driver: bridge.
4. Креирајте опкружувачки променливи: LOW_TEMPERATURE, MEDIUM_TEMPERATURE, HIGH_TEMPERATURE.
   - Заменете во Java кодот да се земаат опкружувачките променивли.  
     LOW (LOW_TEMPERATURE, MEDIUM_TEMPERATURE) <br> 
     MEDIUM (MEDIUM_TEMPERATURE, HIGH_TEMPERATURE)  <br> 
     HIGH (HIGH_TEMPERATURE, INFINITY) <br> 
6. Стартувајте го Docker compose.
7. Во командна линија излистај ја содржината на temperature и temperaturelevel volumes.
8. Во командна линија погледнете ги IPv4Address на двата контерјнери кои се во temperature-level-network.
9. Напишете на кои порти од host и кои порти внатре во контејнерот работат сервисите.
10. Во командна линија испечатете ги опкружувачки променливи на двата сервиси.
11. Променте ги вредностите на опкружувачки променливи.
12. Рестартирајте го Docker compose.

Излезот од извршувањата на командите го ставате во формата подолу.
После завршувањето на задачата, стопирајте ги контејнерите!
