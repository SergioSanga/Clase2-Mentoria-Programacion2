package com.juego.personajes;

public class Hechicero extends Personaje implements Curable {
    private int inteligencia;
    private int mana;
    private int manaMaximo;

    public Hechicero(String nombre) {
        super(nombre, 80, 15, 10);
        this.inteligencia = 20;
        this.manaMaximo = 100;
        this.mana = manaMaximo;
        this.ataqueBase += inteligencia;
        this.manaMaximo += inteligencia * 2;
        this.mana = this.manaMaximo;
    }

    @Override
    public void atacar(Personaje objetivo) {
        if (!estaVivo || !objetivo.estaVivo()) return;
        if (mana < 20) { // ← Verificar recursos
            System.out.println(nombre + " no tiene mana");
            regenerarMana();
            return;// ← NO ataca si no hay mana
        }
        System.out.println(nombre + " lanza bola de fuego a " + objetivo.getNombre());
        mana -= 20;// ← Consumir recurso
        // ... realizar ataque
        objetivo.recibirDanio(calcularDanio(objetivo) + inteligencia);
        ganarExperiencia(!objetivo.estaVivo() ? 50 : 10);
    }

    @Override
    public void curar(Personaje personaje) {
        if (!estaVivo) return;
        if (mana < 25) {
            System.out.println(nombre + " no tiene mana para curar");
            regenerarMana();
            return;
        }
        System.out.println("✨ " + nombre + " cura mágicamente a " + personaje.getNombre());
        mana -= 25;
        personaje.aumentarVida((personaje.getVidaMaxima() / 3) + (inteligencia / 2));
        ganarExperiencia(12);
    }

    public void conjurar(Personaje objetivo) {
        if (!estaVivo || !objetivo.estaVivo()) return;
        if (mana < 30) {
            System.out.println(nombre + " no tiene mana para conjurar");
            regenerarMana();
            return;
        }
        System.out.println(nombre + " conjura sueño sobre " + objetivo.getNombre());
        mana -= 30;
        int reduccion = objetivo.getDefensaBase() / 2;
        objetivo.setDefensaBase(objetivo.getDefensaBase() - reduccion);
        System.out.println(objetivo.getNombre() + " está adormecido (Defensa -" + reduccion + ")");
        ganarExperiencia(15);
    }

    public void regenerarMana() {
        int regen = manaMaximo / 4;
        mana = Math.min(mana + regen, manaMaximo);
        System.out.println("✨ " + nombre + " regeneró " + regen + " mana (" + mana + "/" + manaMaximo + ")");
    }

    @Override
    protected void mostrarEstadisticasAdicionales() {
        System.out.println("  Inteligencia: " + inteligencia);
        System.out.println("  Mana:         " + mana + "/" + manaMaximo);// ← Diferente extra
    }

    @Override
    public void subirNivel() {
        super.subirNivel();
        this.inteligencia += 3;
        this.manaMaximo += 20;
        this.mana = this.manaMaximo;
    }
}