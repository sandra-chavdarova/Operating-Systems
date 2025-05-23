# Exercise 1 (TCPClientSide.java)

Да се имплементира чет клиент кој ќе се поврзе со централен сервер. Серверот го користи TCP протоколот и слуша за дојдовни конекции на порта 9753 на следната адреса: 194.149.135.49.

Вашата прва порака која ќе треба да ја испратите да се логирате на серверот треба да биде: hello:123456 доколку 123456 е вашиот индекс. За успешно логирање, ќе добиете повратна порака од серверот во остварената конекција преку socket. Доколку ова е неуспешно, потребно е да ја терминирате конекцијата и да пробате од ново да се конектирате.


По добивањето на потврда дека сте успешно поврзани, следна порака која е испратена до серверот треба да биде hello:123456 доколку 123456 е вашиот индекс. По добивање на потврда после оваа порака може да комуницирате со ваши колеги кои во тој момент се истотака логирани на серверот. За да испратите порака на друг колега, (пример, со индекс 111111) таа треба да биде во формат: 111111: nekoja poraka, . Услов за успешно праќање на пораката е колегата во тоа време да е логиран на чет серверот. За тестирање можете да си испраќате и ехо пораки на самите себе (каде примачот ќе биде вашиот индекс)


Помош: за непречена комуникација користете посебна нитка за праќање, а посебна за примање на пораки од серверот.

Напомена: не заборавајте секоја испратена порака да ја терминирате со карактер за нова линија "\n" !

# Exercise 2 (UDPClientSide.java)
Да се имплементира UDP клиент кој праќа текстуална порака на серверот кој слуша на порта 9753 на следната адреса: 194.149.135.49. Пораката која ќе ја испратите треба да содржи само стринг од 6 карактери- вашиот број на индекс. За успешно остварена конекција серверот ќе ви врати повратна информација од истиот UDP socket.

# Exercise 3
Потребно е да се имплементира мрежна апликација која ќе прифаќа неограничен број клиенти и ќе ги обработува нивните пораки.
<br><br>
Клиентот воспоставува конекција со тоа што испраќа пораки во вид на HTTP барање:
```
GET /hello HTTP/1.1
Host: developer.mozilla.org
User agent: OS Client
```
Серверот ги парсира овие пораки во вид на
```
GET /hello OS Client
```
и ги запишува во фајл ```logging.txt```. <br>
Исто така, серверот испраќа порака до клиентот дека врската е успешно воспоставена.
На крај, серверот испраќа порака ```Logged out```.

# Exercise 4
Потребно е да се имплементира мрежна апликација која ќе прифаќа неограничен број клиенти и ќе ги обработува нивните пораки.
<br><br>
Клиентот воспоставува конекција со серверот со тоа што испраќа порака "HANDSHAKE" за да иницира комуникација.
Серверот ја прима пораката и враќа потврда во форма:
```Logged in <IP_ADDRESS_OF_CLIENT>```
каде што ```<IP_ADDRESS_OF_CLIENT>``` е IP адресата на клиентот кој ја воспоставил комуникацијата.
<br><br>
Клиентот започнува со испраќање на пораки во текстуален формат кои се состојат само од по еден трицифрен број, секој во нов ред (на пример: 101, 203, 107...). Броевите се читаат од фајл ```numbers.txt``` којшто се наоѓа во директориумот во кој се наоѓа апликацијата.
<br><br>
Сите добиени броеви серверот ги сумира и чува во една променлива - вкупна сума од сите броеви кои ги добил.
- На пример: ако еден клиент испрати 101, 103 и 107 — серверот треба да чува збир 311.


Секој добиен број серверот треба да го запише во текстуален лог-фајл, заедно со тековниот timestamp и IP адресата на клиентот од кој е добиен бројот. Името на фајлот е ```log.txt``` и локацијата на лог фајлот е директориумот во кој се наоѓа апликацијата.
<br><br>
Кога клиентот ќе ја испрати пораката "STOP", серверот му ја испраќа моменталната вредност на збирот од броевите на клиентот, праќа "Logged out" порака и ја затвора врската со клиентот.
<br><br>
Серверот треба да работи на порта 7391.
Апликацијата треба да биде во можност да обработува паралелни клиенти со користење на посебна нишка (Worker) за секој клиент.
