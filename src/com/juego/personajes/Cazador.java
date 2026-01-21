package com.juego.personajes;

public class Cazador extends Personaje implements Atacante, Observador {
    private int velocidad;
    private int inteligencia;

    public Cazador(String nombre) {
        super(nombre, 100, 20, 15);
        this.velocidad = 18;
        this.inteligencia = 12;
        this.ataqueBase += velocidad / 2;
        this.defensaBase += velocidad / 3;
    }

    @Override
    public void atacar(Personaje objetivo) {
        if (!estaVivo || !objetivo.estaVivo()) return;
        System.out.println(nombre + " dispara flecha a " + objetivo.getNombre());
        objetivo.recibirDanio(calcularDanio(objetivo) + (velocidad / 4));
        //                     ↑ Base común          ↑ Bonus único
        ganarExperiencia(!objetivo.estaVivo() ? 50 : 10);
    }

    @Override
    public void golpear(Personaje[] personajes) {
        if (!estaVivo) return;
        System.out.println(nombre + " dispara flechas múltiples");
        int atacados = 0;
        for (Personaje p : personajes) {
            if (p != null && p.estaVivo() && p != this && atacados < 3) {
                //  ↑           ↑              ↑              ↑
                //  existe      vivo        no yo         máx 3
                p.recibirDanio((calcularDanio(p) * 2) / 3);
                atacados++;
            }
        }
        if (atacados > 0) ganarExperiencia(12);
    }

    public void inspirar(Personaje personaje) {
        if (!estaVivo || !personaje.estaVivo()) return;
        System.out.println("🛡️ " + nombre + " inspira defensa a " + personaje.getNombre());
        int bonus = personaje.getDefensaBase() / 3;
        personaje.setDefensaBase(personaje.getDefensaBase() + bonus);
        System.out.println(personaje.getNombre() + " gana +" + bonus + " defensa");
        ganarExperiencia(5);
    }

    @Override
    public void observar(Personaje personaje) {
        if (!estaVivo) return;
        System.out.println("🔍 " + nombre + " observa a " + personaje.getNombre());
        personaje.mostrarEstado();// ← Reutiliza método existente
        ganarExperiencia(3);
    }

    @Override
    protected void mostrarEstadisticasAdicionales() {
        System.out.println("  Velocidad:    " + velocidad);
        System.out.println("  Inteligencia: " + inteligencia);
    }

    @Override
    public void subirNivel() {
        super.subirNivel();
        this.velocidad += 2;
        this.inteligencia += 1;
    }
}