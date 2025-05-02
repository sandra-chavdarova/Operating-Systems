# Example 1

Преку Git Bash се оди до датотеката која ја содржи `Main.java`, во овој случај
`~/IdeaProjects/Operating Systems/Docker/Example1`.
Командите кои можат да се искористат се

```
$ docker build -t example1 .
$ docker run --rm example1
```

Битно е во кодот од `Main.java` најгоре да не стои било каков package за да работи Dockerfile-от.


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
