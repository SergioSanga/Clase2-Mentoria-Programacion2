
import com.juego.personajes.Cazador;
import com.juego.personajes.Guerrero;
import com.juego.personajes.Hechicero;
import com.juego.personajes.Personaje;

import java.util.*;

public class Main {
    private static List<Personaje> personajes = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static Random random = new Random();

    public static void main(String[] args) {
        boolean continuar = true;
        while (continuar) {
            mostrarMenu();
            int opcion = leerOpcion(1, 6);
            switch (opcion) {
                case 1: crearPersonaje(); break;
                case 2: listarPersonajes(); break;
                case 3: seleccionarYActuar(); break;
                case 4: mostrarEstadisticas(); break;
                case 5: simularCombate(); break;
                case 6: continuar = false; break;
            }
        }
        scanner.close();
    }

    private static void mostrarMenu() {
        System.out.println("\n┌─── MENÚ PRINCIPAL ───┐");
        System.out.println("│ 1. Crear Personaje   │");
        System.out.println("│ 2. Listar Personajes │");
        System.out.println("│ 3. Seleccionar       │");
        System.out.println("│ 4. Estadísticas      │");
        System.out.println("│ 5. Combate Auto      │");
        System.out.println("│ 6. Salir             │");
        System.out.println("└──────────────────────┘");
        System.out.print("Opción: ");
    }

    private static void crearPersonaje() {
        System.out.println("\n1. Guerrero  2. Hechicero  3. Cazador");
        System.out.print("Tipo: ");
        int tipo = leerOpcion(1, 3);
        System.out.print("Nombre: ");
        scanner.nextLine();
        String nombre = scanner.nextLine();

        Personaje p = null;
        switch (tipo) {
            case 1: p = new Guerrero(nombre); System.out.println("⚔️ Guerrero creado"); break;
            case 2: p = new Hechicero(nombre); System.out.println("🔮 Hechicero creado"); break;
            case 3: p = new Cazador(nombre); System.out.println("🏹 Cazador creado"); break;
        }
        if (p != null) {
            personajes.add(p);
            p.mostrarEstado();
        }
    }

    private static void listarPersonajes() {
        System.out.println("\n┌─── PERSONAJES ───┐");
        if (personajes.isEmpty()) {
            System.out.println("│ Sin personajes   │");
        } else {
            for (int i = 0; i < personajes.size(); i++) {
                Personaje p = personajes.get(i);
                System.out.printf("│ %d. [%s] %s Lv.%d - %d/%d HP │\n",
                        i+1, p.estaVivo() ? "✓" : "✗", p.getNombre(),
                        p.getNivel(), p.getVida(), p.getVidaMaxima());
            }
        }
        System.out.println("└──────────────────┘");
    }

    private static void seleccionarYActuar() {
        List<Personaje> vivos = new ArrayList<>();
        for (Personaje p : personajes) {
            if (p.estaVivo()) vivos.add(p);
        }
        if (vivos.isEmpty()) {
            System.out.println("No hay personajes vivos");
            return;
        }

        System.out.println("\nPersonajes vivos:");
        for (int i = 0; i < vivos.size(); i++) {
            System.out.println((i+1) + ". " + vivos.get(i).getNombre());
        }
        System.out.print("Selecciona: ");
        int idx = leerOpcion(1, vivos.size()) - 1;
        mostrarAcciones(vivos.get(idx));
    }

    private static void mostrarAcciones(Personaje p) {
        System.out.println("\n1. Atacar  2. Estado");
        if (p instanceof Guerrero) {
            System.out.println("3. Golpear  4. Curar  5. Inspirar");
        } else if (p instanceof Hechicero) {
            System.out.println("3. Curar  4. Conjurar  5. Regenerar");
        } else if (p instanceof Cazador) {
            System.out.println("3. Golpear  4. Inspirar  5. Observar");
        }
        System.out.print("Acción: ");
        int accion = leerOpcion(1, 5);
        ejecutarAccion(p, accion);
    }

    private static void ejecutarAccion(Personaje p, int accion) {
        switch (accion) {
            case 1:
                Personaje obj = seleccionarObjetivo(p);
                if (obj != null) p.atacar(obj);
                break;
            case 2:
                p.mostrarEstado();
                break;
            case 3:
                if (p instanceof Guerrero) {
                    ((Guerrero) p).golpear(seleccionarMultiples(p, 3));
                } else if (p instanceof Hechicero) {
                    Personaje aliado = seleccionarAliado();
                    if (aliado != null) ((Hechicero) p).curar(aliado);
                } else if (p instanceof Cazador) {
                    ((Cazador) p).golpear(seleccionarMultiples(p, 3));
                }
                break;
            case 4:
                if (p instanceof Guerrero) {
                    Personaje aliado = seleccionarAliado();
                    if (aliado != null) ((Guerrero) p).curar(aliado);
                } else if (p instanceof Hechicero) {
                    Personaje enemigo = seleccionarObjetivo(p);
                    if (enemigo != null) ((Hechicero) p).conjurar(enemigo);
                } else if (p instanceof Cazador) {
                    Personaje aliado = seleccionarAliado();
                    if (aliado != null) ((Cazador) p).inspirar(aliado);
                }
                break;
            case 5:
                if (p instanceof Guerrero) {
                    Personaje aliado = seleccionarAliado();
                    if (aliado != null) ((Guerrero) p).inspirar(aliado);
                } else if (p instanceof Hechicero) {
                    ((Hechicero) p).regenerarMana();
                } else if (p instanceof Cazador) {
                    Personaje cualquiera = personajes.isEmpty() ? null : personajes.get(random.nextInt(personajes.size()));
                    if (cualquiera != null) ((Cazador) p).observar(cualquiera);
                }
                break;
        }
    }

    private static Personaje seleccionarObjetivo(Personaje atacante) {
        List<Personaje> enemigos = new ArrayList<>();
        for (Personaje p : personajes) {
            if (p != atacante && p.estaVivo()) enemigos.add(p);
        }
        return enemigos.isEmpty() ? null : enemigos.get(random.nextInt(enemigos.size()));
    }

    private static Personaje seleccionarAliado() {
        List<Personaje> vivos = new ArrayList<>();
        for (Personaje p : personajes) {
            if (p.estaVivo()) vivos.add(p);
        }
        return vivos.isEmpty() ? null : vivos.get(random.nextInt(vivos.size()));
    }

    private static Personaje[] seleccionarMultiples(Personaje atacante, int max) {
        List<Personaje> objetivos = new ArrayList<>();
        for (Personaje p : personajes) {
            if (p != atacante && p.estaVivo() && objetivos.size() < max) {
                objetivos.add(p);
            }
        }
        return objetivos.toArray(new Personaje[0]);
    }

    private static void mostrarEstadisticas() {
        if (personajes.isEmpty()) {
            System.out.println("No hay personajes");
            return;
        }
        int vivos = 0, muertos = 0, nivelTotal = 0;
        for (Personaje p : personajes) {
            if (p.estaVivo()) vivos++; else muertos++;
            nivelTotal += p.getNivel();
        }
        System.out.println("\n╔══════════════════╗");
        System.out.println("║  ESTADÍSTICAS    ║");
        System.out.println("╠══════════════════╣");
        System.out.println("║ Total: " + personajes.size());
        System.out.println("║ Vivos: " + vivos);
        System.out.println("║ Muertos: " + muertos);
        System.out.printf("║ Nivel prom: %.2f\n", (double)nivelTotal / personajes.size());
        System.out.println("╚══════════════════╝");
    }

    private static void simularCombate() {
        List<Personaje> vivos = new ArrayList<>();
        for (Personaje p : personajes) {
            if (p.estaVivo()) vivos.add(p);
        }
        if (vivos.size() < 2) {
            System.out.println("Se necesitan mínimo 2 vivos");
            return;
        }

        Personaje p1 = vivos.get(random.nextInt(vivos.size()));
        Personaje p2;
        do {
            p2 = vivos.get(random.nextInt(vivos.size()));
        } while (p2 == p1);

        System.out.println("\nCOMBATE: " + p1.getNombre() + " VS " + p2.getNombre());
        int ronda = 1;
        while (p1.estaVivo() && p2.estaVivo() && ronda <= 5) {
            System.out.println("\n─── Ronda " + ronda + " ───");
            p1.atacar(p2);
            if (p2.estaVivo()) p2.atacar(p1);
            ronda++;
        }

        System.out.println("\n╔════════════════╗");
        if (!p1.estaVivo() && !p2.estaVivo()) {
            System.out.println("║ EMPATE MORTAL  ║");
        } else if (!p1.estaVivo()) {
            System.out.println("║ GANADOR: " + p2.getNombre());
        } else if (!p2.estaVivo()) {
            System.out.println("║ GANADOR: " + p1.getNombre());
        } else {
            System.out.println("║ TIEMPO AGOTADO ║");
        }
        System.out.println("╚════════════════╝");
    }

    private static int leerOpcion(int min, int max) {
        int opcion = -1;
        while (opcion < min || opcion > max) {
            try {
                opcion = scanner.nextInt();
                if (opcion < min || opcion > max) {
                    System.out.print("Inválido (" + min + "-" + max + "): ");
                }
            } catch (Exception e) {
                System.out.print("Inválido: ");
                scanner.nextLine();
            }
        }
        return opcion;
    }
}