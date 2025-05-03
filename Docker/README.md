# Example 1

Преку Git Bash се оди до датотеката која ја содржи `Main.java`, во овој случај
`~/IdeaProjects/Operating Systems/Docker/Example1`.
Командите кои можат да се искористат се

```
$ docker build -t example1 .
$ docker run --rm example1
```

Битно е во кодот од `Main.java` најгоре да не стои било каков package за да работи Dockerfile-от.

<br>

# Example 2

Преку Git Bash се оди до датотеката која ја содржи `ExecutionCounter.java`, во овој случај
`~/IdeaProjects/Operating Systems/Docker/Example2`.
Командите кои можат да се искористат се

```
$ docker build -t example2 .
$ winpty docker run -it -v "$(pwd)/data:/usr/src/myapp/data/" --rm --name counter-java example2
```
Output: 1

```
$ winpty docker run -it -v "$(pwd)/data:/usr/src/myapp/data/" --rm --name counter-java example2
```
Output: 2

```
$ winpty docker run -it -v "$(pwd)/data:/usr/src/myapp/data/" --rm --name counter-java example2
```
Output: 3

Битно е во кодот од `ExecutionCounter.java` најгоре да не стои било каков package за да работи Dockerfile-от.

<br>

# Example 3

Преку Git Bash се оди до датотеката која ја содржи `ExecutionCounter2.java`, во овој случај
`~/IdeaProjects/Operating Systems/Docker/Example3`.
Командите кои можат да се искористат се

```
$ docker build -t example3 .
$ winpty docker run -it -v "$(pwd)/data:/usr/src/myapp/data/" -e PERSISTING_FILE_PATH=/usr/src/myapp/data/data.out --rm --name counter-java example3
```
Output: 1

```
$ winpty docker run -it -v "$(pwd)/data:/usr/src/myapp/data/" -e PERSISTING_FILE_PATH=/usr/src/myapp/data/data.out --rm --name counter-java example3
```
Output: 2

```
$ winpty docker run -it -v "$(pwd)/data:/usr/src/myapp/data/" -e PERSISTING_FILE_PATH=/usr/src/myapp/data/data.out --rm --name counter-java example3
```
Output: 3

Битно е во кодот од `ExecutionCounter.java` најгоре да не стои било каков package за да работи Dockerfile-от.

**Забелешка:**
<br> доколку ```$ winpty docker run -it -v "$(pwd)/data:/usr/src/myapp/data/" -e PERSISTING_FILE_PATH=/usr/src/myapp/data/data.out --rm --name counter-java example3```
<br> прави проблеми, обиди се во cmd со следната команда <br>
```docker run -it -v "%cd%\data:/usr/src/myapp/data" -e PERSISTING_FILE_PATH=/usr/src/myapp/data/data.out --rm --name counter-java example3```

