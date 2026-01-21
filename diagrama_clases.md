classDiagram
    class Personaje {
        <<abstract>>
        #String nombre
        #int nivel
        #int experiencia
        #int vida
        #int vidaMaxima
        #int ataqueBase
        #int defensaBase
        #boolean estaVivo
        #static int EXP_POR_NIVEL
        +Personaje(String, int, int, int)
        +atacar(Personaje)*
        +ganarExperiencia(int)
        +subirNivel()
        +mostrarEstado()
        #mostrarEstadisticasAdicionales()
        +recibirDanio(int)
        +aumentarVida(int)
        #calcularDanio(Personaje) int
        +getNombre() String
        +getNivel() int
        +getVida() int
        +getVidaMaxima() int
        +estaVivo() boolean
        +getAtaqueBase() int
        +getDefensaBase() int
        +setAtaqueBase(int)
        +setDefensaBase(int)
    }

    class Guerrero {
        -int fuerza
        -int resistencia
        +Guerrero(String)
        +atacar(Personaje)
        +golpear(Personaje[])
        +curar(Personaje)
        +inspirar(Personaje)
        #mostrarEstadisticasAdicionales()
        +subirNivel()
    }

    class Hechicero {
        -int inteligencia
        -int mana
        -int manaMaximo
        +Hechicero(String)
        +atacar(Personaje)
        +curar(Personaje)
        +conjurar(Personaje)
        +regenerarMana()
        #mostrarEstadisticasAdicionales()
        +subirNivel()
    }

    class Cazador {
        -int velocidad
        -int inteligencia
        +Cazador(String)
        +atacar(Personaje)
        +golpear(Personaje[])
        +inspirar(Personaje)
        +observar(Personaje)
        #mostrarEstadisticasAdicionales()
        +subirNivel()
    }

    class Atacante {
        <<interface>>
        +golpear(Personaje[])
    }

    class Curable {
        <<interface>>
        +curar(Personaje)
    }

    class Observador {
        <<interface>>
        +observar(Personaje)
    }

    class Main {
        -static List~Personaje~ personajes
        -static Scanner scanner
        -static Random random
        +main(String[])
        -mostrarMenu()
        -crearPersonaje()
        -listarPersonajes()
        -seleccionarYActuar()
        -mostrarAcciones(Personaje)
        -ejecutarAccion(Personaje, int)
        -seleccionarObjetivo(Personaje) Personaje
        -seleccionarAliado() Personaje
        -seleccionarMultiples(Personaje, int) Personaje[]
        -mostrarEstadisticas()
        -simularCombate()
        -leerOpcion(int, int) int
    }

    Guerrero --|> Personaje
    Hechicero --|> Personaje
    Cazador --|> Personaje

    Guerrero ..|> Atacante
    Guerrero ..|> Curable
    
    Hechicero ..|> Curable
    
    Cazador ..|> Atacante
    Cazador ..|> Observador

    Main ..> Personaje : uses
    Main ..> Guerrero : creates
    Main ..> Hechicero : creates
    Main ..> Cazador : creates