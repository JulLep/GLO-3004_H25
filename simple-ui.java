import java.util.Scanner;

// Interface utilisateur simple pour modifier les paramètres
public class SimpleUI implements Runnable {
    private final ControleTempereuse controleTempereuse;
    private final ControleMouleuse controleMouleuse;
    private final Scanner scanner;
    
    public SimpleUI(ControleTempereuse controleTempereuse, ControleMouleuse controleMouleuse) {
        this.controleTempereuse = controleTempereuse;
        this.controleMouleuse = controleMouleuse;
        this.scanner = new Scanner(System.in);
    }
    
    @Override
    public void run() {
        System.out.println("\n=== Interface de contrôle de la chocolaterie ===");
        System.out.println("Commandes disponibles:");
        System.out.println("  t <nombre> : Modifier le nombre de tempéreuses");
        System.out.println("  m <nombre> : Modifier le nombre de mouleuses");
        System.out.println("  q          : Quitter le programme");
        
        boolean continuer = true;
        while (continuer) {
            System.out.print("\nEntrez une commande: ");
            String commande = scanner.nextLine().trim();
            
            if (commande.startsWith("t ")) {
                try {
                    int nombre = Integer.parseInt(commande.substring(2).trim());
                    if (nombre > 0) {
                        controleTempereuse.setNombreTempereuses(nombre);
                    } else {
                        System.out.println("Le nombre doit être positif");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Format invalide");
                }
            } else if (commande.startsWith("m ")) {
                try {
                    int nombre = Integer.parseInt(commande.substring(2).trim());
                    if (nombre > 0) {
                        controleMouleuse.setNombreMouleuses(nombre);
                    } else {
                        System.out.println("Le nombre doit être positif");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Format invalide");
                }
            } else if (commande.equals("q")) {
                System.out.println("Arrêt du programme...");
                System.exit(0);
                continuer = false;
            } else {
                System.out.println("Commande non reconnue");
            }
        }
        
        scanner.close();
    }
}
