package com.juego.personajes;

public abstract class Personaje {
    protected String nombre;
    protected int nivel;
    protected int experiencia;
    protected int vida;
    protected int vidaMaxima;
    protected int ataqueBase;
    protected int defensaBase;
    protected boolean estaVivo;

    protected static final int EXP_POR_NIVEL = 100;

    public Personaje(String nombre, int vida, int ataqueBase, int defensaBase) {
        this.nombre = nombre;
        this.nivel = 1;
        this.experiencia = 0;
        this.vida = vida;
        this.vidaMaxima = vida;
        this.ataqueBase = ataqueBase;
        this.defensaBase = defensaBase;
        this.estaVivo = true;
    }
    // ABSTRACTO - Cada personaje ataca diferente
    public abstract void atacar(Personaje objetivo);
    // CONCRETO - Todos ganan experiencia igual
    public void ganarExperiencia(int exp) {
        if (!estaVivo) return;
        this.experiencia += exp;
        System.out.println(nombre + " ganó " + exp + " XP");
        while (this.experiencia >= EXP_POR_NIVEL) {
            subirNivel();
        }
    }

    public void subirNivel() {
        this.nivel++;
        this.experiencia -= EXP_POR_NIVEL;
        this.vidaMaxima += 20;
        this.vida = this.vidaMaxima;
        this.ataqueBase += 5;
        this.defensaBase += 3;
        System.out.println("¡" + nombre + " subió a nivel " + nivel + "!");
    }

    public void mostrarEstado() {
        System.out.println("═══════════════════════════════════");
        System.out.println("  " + nombre + " (" + getClass().getSimpleName() + ")");
        System.out.println("───────────────────────────────────");
        System.out.println("  Nivel:       " + nivel);
        System.out.println("  Experiencia: " + experiencia + "/" + EXP_POR_NIVEL);
        System.out.println("  Vida:        " + vida + "/" + vidaMaxima);
        System.out.println("  Ataque:      " + ataqueBase);
        System.out.println("  Defensa:     " + defensaBase);
        System.out.println("  Estado:      " + (estaVivo ? "✓ Vivo" : "✗ Muerto"));
        mostrarEstadisticasAdicionales();
        System.out.println("═══════════════════════════════════");
    }
    // Método vacío que las hijas PUEDEN sobrescribir
    protected void mostrarEstadisticasAdicionales() {}

    public void recibirDanio(int danio) {
        if (!estaVivo) return;
        this.vida -= danio;
        System.out.println(nombre + " recibió " + danio + " de daño (Vida: " + Math.max(0, vida) + "/" + vidaMaxima + ")");
        if (this.vida <= 0) {
            this.vida = 0;
            this.estaVivo = false;
            System.out.println("💀 " + nombre + " ha sido derrotado");
        }
    }

    public void aumentarVida(int cantidad) {
        if (!estaVivo) {
            System.out.println("No se puede curar a " + nombre + " (muerto)");
            return;
        }
        int vidaAntes = this.vida;
        this.vida = Math.min(this.vida + cantidad, this.vidaMaxima);
        int vidaCurada = this.vida - vidaAntes;
        if (vidaCurada > 0) {
            System.out.println(nombre + " recuperó " + vidaCurada + " HP (Vida: " + vida + "/" + vidaMaxima + ")");
        } else {
            System.out.println(nombre + " ya tiene vida al máximo");
        }
    }

    protected int calcularDanio(Personaje objetivo) {
        int danio = this.ataqueBase - (objetivo.defensaBase / 2);
        return Math.max(danio, 5);// ← Garantiza mínimo 5
    }

    // Getters
    public String getNombre() { return nombre; }
    public int getNivel() { return nivel; }
    public int getVida() { return vida; }
    public int getVidaMaxima() { return vidaMaxima; }
    public boolean estaVivo() { return estaVivo; }
    public int getAtaqueBase() { return ataqueBase; }
    public int getDefensaBase() { return defensaBase; }

    // Setters
    public void setAtaqueBase(int ataque) { this.ataqueBase = ataque; }
    public void setDefensaBase(int defensa) { this.defensaBase = defensa; }
}