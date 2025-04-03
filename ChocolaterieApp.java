public class ChocolaterieApp {
    public static void main(String[] args) {
        // Analyser les paramètres de ligne de commande
        int nombreChocolatiers = 3; // Valeur par défaut
        int nombreTempereuses = 1;  // Valeur par défaut
        int nombreMouleuses = 1;    // Valeur par défaut
        
        // Traiter les arguments de ligne de commande s'ils existent
        if (args.length > 0) {
            try {
                nombreChocolatiers = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.out.println("Erreur: Le nombre de chocolatiers doit être un entier.");
                System.exit(1);
            }
        }
        if (args.length > 1) {
            try {
                nombreTempereuses = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.out.println("Erreur: Le nombre de tempéreuses doit être un entier.");
                System.exit(1);
            }
        }
        if (args.length > 2) {
            try {
                nombreMouleuses = Integer.parseInt(args[2]);
            } catch (NumberFormatException e) {
                System.out.println("Erreur: Le nombre de mouleuses doit être un entier.");
                System.exit(1);
            }
        }
        
        // Vérifier la validité des paramètres
        if (nombreChocolatiers <= 0 || nombreTempereuses <= 0 || nombreMouleuses <= 0) {
            System.out.println("Erreur: Les nombres de chocolatiers, tempéreuses et mouleuses doivent être positifs.");
            System.exit(1);
        }
        
        // Créer les gestionnaires de ressources
        ControleTempereuse controleTempereuse = new ControleTempereuse(nombreTempereuses);
        ControleMouleuse controleMouleuse = new ControleMouleuse(nombreMouleuses);
        ApprovisionnementManager approManager = new ApprovisionnementManager();
        
        System.out.println("=== Lancement de la chocolaterie ===");
        System.out.println("Nombre de chocolatiers: " + nombreChocolatiers);
        System.out.println("Nombre de tempéreuses: " + nombreTempereuses);
        System.out.println("Nombre de mouleuses: " + nombreMouleuses);
        
        // Créer et démarrer les threads des chocolatiers
        Thread[] threadsChocolatiers = new Thread[nombreChocolatiers * 2]; // Pour les deux types (n et b)
        for (int i = 0; i < nombreChocolatiers; i++) {
            // Créer un chocolatier de type n
            Chocolatier chocN = new Chocolatier("n", i + 1, controleTempereuse, controleMouleuse, approManager);
            threadsChocolatiers[i] = new Thread(chocN);
            threadsChocolatiers[i].start();
            
            // Créer un chocolatier de type b
            Chocolatier chocB = new Chocolatier("b", i + 1, controleTempereuse, controleMouleuse, approManager);
            threadsChocolatiers[i + nombreChocolatiers] = new Thread(chocB);
            threadsChocolatiers[i + nombreChocolatiers].start();
        }
        
        // Démarrer le simulateur de ruptures
        RuptureSimulator ruptureSimulator = new RuptureSimulator(approManager, nombreChocolatiers);
        Thread ruptureThread = new Thread(ruptureSimulator);
        ruptureThread.setDaemon(true); // Thread en arrière-plan
        ruptureThread.start();
        
        // Attendre que les threads des chocolatiers se terminent (ce qui ne devrait pas arriver)
        for (Thread t : threadsChocolatiers) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}