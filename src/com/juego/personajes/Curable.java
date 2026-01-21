package com.juego.personajes;

/**
 * Interface que define la capacidad de curar a otros personajes
 */
public interface Curable {
    /**
     * Cura a un personaje objetivo
     * @param personaje El personaje a curar
     */
    void curar(Personaje personaje);
}
// Guerrero y Hechicero pueden curar
// Cazador NO puede curar
// ¿Cómo hacemos que solo algunos puedan curar? R. Solucionamos con interfaces