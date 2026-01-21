package com.juego.personajes;

public class Guerrero extends Personaje implements Curable, Atacante {
    // ↑ Solo UNA clase padre                  ↑ MÚLTIPLES interfaces

    private int fuerza;
    private int resistencia;

    public Guerrero(String nombre) {
        super(nombre, 150, 25, 20); // ← Llamada al constructor padre
        //      ↑      ↑    ↑   ↑
        //   nombre  vida atq def
        this.fuerza = 15;// Luego lo específico
        this.resistencia = 12;
        this.ataqueBase += fuerza;
        this.defensaBase += resistencia / 2;
        this.vidaMaxima += resistencia * 5;
        this.vida = this.vidaMaxima;
    }
    // CON @Override
    @Override
    public void atacar(Personaje objetivo) { // ← Typo
        // ERROR DE COMPILACIÓN
        // "No se encuentra método para sobrescribir"
        if (!estaVivo || !objetivo.estaVivo()) return;
        System.out.println(nombre + " ataca con espada a " + objetivo.getNombre());
        int danio = calcularDanio(objetivo);
        objetivo.recibirDanio(danio);
        ganarExperiencia(!objetivo.estaVivo() ? 50 : 10);
    }

    @Override
    public void golpear(Personaje[] personajes) {
        if (!estaVivo) return;
        System.out.println(nombre + " golpea en área");
        for (Personaje p : personajes) {
            if (p != null && p.estaVivo() && p != this) {
                p.recibirDanio(calcularDanio(p) / 2);
            }
        }
        ganarExperiencia(15);
    }

    @Override
    public void curar(Personaje personaje) {
        if (!estaVivo) return;// ← Validación importante
        System.out.println(nombre + " cura a " + personaje.getNombre());
        personaje.aumentarVida(personaje.getVidaMaxima() / 3);
        //                    ↑ 33% de curación                                                 ↑
        //                     Recupera 1/3 de vida máxima
        ganarExperiencia(8);
    }

    public void inspirar(Personaje personaje) {
        if (!estaVivo || !personaje.estaVivo()) return;
        System.out.println("🔥 " + nombre + " inspira a " + personaje.getNombre());
        int bonus = personaje.getAtaqueBase() / 3;
        personaje.setAtaqueBase(personaje.getAtaqueBase() + bonus);
        System.out.println(personaje.getNombre() + " gana +" + bonus + " ataque");
        ganarExperiencia(5);
    }

    @Override
    protected void mostrarEstadisticasAdicionales() {
        System.out.println("  Fuerza:      " + fuerza);// ← Extra
        System.out.println("  Resistencia: " + resistencia);
    }

    @Override
    public void subirNivel() {
        super.subirNivel();
        this.fuerza += 2;
        this.resistencia += 2;
    }
}