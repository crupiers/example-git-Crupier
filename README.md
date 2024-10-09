# Ejemplo de Git
Este repositorio refleja el codigo de ejemplo para una aplicacion representativa de Springboot en el **taller de Workflows de Git** de la UTN FRVM.

Accede a una lista sencilla de todos los comandos mas usados de git [**en este recurso compartido**](https://training.github.com/downloads/es_ES/github-git-cheat-sheet.pdf) o lee mas abajo para obtener una explicacion mas indagada de cada uno.

## Pre-requisitos
- Instalar **git** ([Windows](https://git-scm.com/download/win), Linux viene incluido en muchos distros pero si no lo tienes usa `sudo apt update && sudo apt install git-all -y`)
- Instalar **JDK 21 LTS** (Seguir tutorial [en este link](https://github.com/Programacion-Avanzada-UTN-FRVM/getting-started))

## Comandos de Git
En este archivo queda una constancia de los comandos utilizados durante la demostracion del workflow y sus usos.

Para una guia extensiva de todos los comandos que ofrece git, puedes indagar mas por tu cuenta [en la documentacion oficial de Git](https://git-scm.com/docs/git/es) o [esta super-simplificacion](https://rogerdudler.github.io/git-guide/index.es.html).

### Comandos Basicos
`git init`
Inicializa un repositorio de git nuevo en la carpeta donde uno esta parado actualmente. Esto es para cuando se crea un repositorio desde 0.

`git clone <url>`
Clona un repositorio de git desde un remoto a una carpeta local, configurando automaticamente su destino remoto.

`git remote add origin <url>`
Agrega al repositorio de git actual un **destino remoto** a donde enviar cambios o donde comparar la copia local/staging. Este comando se utiliza unicamente cuando se desea **reapuntar** el remoto de un repositorio existente, o recientemente creamos uno nuevo que solo existe en el local y no sabe a donde esta su remoto.

`git status`
Valida el estado actual del repositorio y la rama con respecto al **remoto**. Esto permite visualizar las diferencias entre ambas para conocer cuales son los cambios activos en la copia local.
- Este comando tambien muestra **la rama actual** en donde esta parado el repositorio y otra informacion relevante sobre el mismo.

`git pull`
Consulta al repositorio remoto en la rama actual por los ultimos cambios de la misma, y los sobreescribe en la copia local (siempre y cuando no hayan conflictos).

### Comandos para Ramas
`git branch`
Lista todas las ramas de un repositorio.
- Si una rama se creo en el remoto pero no en el local, primero actualizar el repositorio con `git pull` para recibir la creacion de dicha rama.

`git checkout`
Realiza un cambio de rama en el repositorio actual, o crea una nueva si no existe y se indica con el parametro `-b`.
- Si se quiere crear una rama nueva, se utiliza el comando `git checkout -b <nombreRama>`
    - Recordar que al crear una rama nueva, siempre se debe basar en una existente. Por lo que primero **pararse en la rama de referencia** y luego crear la branch nueva **que es una copia** de la actual en ese momento.
- Si se quiere cambiar a una branch existente, utilizar el comando `git checkout <nombreRama>`

### Comandos para Committing
`git add`
Envia cambios al **area local** para esperar a que sean **commiteados**.
- Cuando escribimos `git add .` estamos referenciando con el `.` a **la carpeta actual donde estamos parados**. Se desaconseja usar siempre esta convencion, ya que no permite contextualizar los commits correctamente.
- Es conveniente utilizar `git status` para saber que cosas deberian entrar en un commit y dejar el resto fuera.

`git restore`
Elimina uno o varios cambios activos en la copia local del repositorio para una rama especifica.
- Si se utiliza `git restore .` se borraran todos los cambios actuales, **cuidado con este comando**.

`git commit`
Revisa el area local y toma el conjunto de cambios para enviarlos al **area de staging**.
- El uso **principiante** de este comando debe tener el formato `git commit -m "<mensaje>"`, donde `<mensaje>` es el titulo de tu commit (resumen muy corto de tu conjunto de cambios)
- Si cometiste un error en tu mensaje de commit, puedes corregirlo sin borrar tus cambios actuales con `git commit --ammend -m "<mensajeCorregido>"`
- Si se escribe sin parametros, se abrira un resumen del conjunto de cambios en donde puedes proveer un nombre y una descripcion para tu commit.
    - Normalmente el editor por defecto es `vim`, pero puedes configurar para que git utilice `nano` u otro por conveniencia.
    - Para mas informacion sobre `vim` o el editor de bash para tus commits completos y su uso cuando se realiza el comando `git commit`, revisar [este tutorial](https://www.youtube.com/watch?v=MOfBw3eGC08) como guia.

```bash
$ git add .
$ git commit -m "implementacion de entidades y controladores"
```

`git reset`
Revierte el commit nuevamente al area local, basicamente borrando el commit pero conservando el conjunto de cambios nuevamente en el area local.

`git push`
Envia los cambios desde el area de staging **hacia el remoto** en la rama que se esta actualmente parado.

### Concepto de Stash
En git, un stash representa a un espacio temporal donde se guardan cambios. Este espacio temporal sirve como guardavidas en casos comunes como:
1. Existan conflictos entre el remoto y tu copia local.
2. Se necesiten guardar ciertos cambios para retomarlos luego en la copia local.
3. Se necesitan trasladar cambios realizados en una rama hacia otra.

Este stash se administra con el comando `git stash`.
- Si se tienen cambios locales que desean guardarse en el stash, se utiliza `git stash -u`
    - El parametro `-u` indica que incluya aquellos cambios que no estan actualmente seguidos por el remoto, como por ejemplo archivos nuevos.
- Para recuperar el conjunto de cambios de un stash en la rama actual que se esta parado, se utiliza el comando `git stash pop`
- Cada vez que se "stashean" cambios, se crea un nuevo stash en el listado de stashes que puede verse con el comando `git stash list`
```bash
$ git stash list
stash@{0}: WIP on main: 5002d47 our new homepage
stash@{1}: WIP on main: 5002d47 our new homepage
stash@{2}: WIP on main: 5002d47 our new homepage
```

- Tener cuidado con la acumulacion de los stashes.
