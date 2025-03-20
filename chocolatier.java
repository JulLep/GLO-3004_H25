// Classe qui représente un chocolatier
public class Chocolatier implements Runnable {
    private final String type;        // Type de chocolatier (n ou b)
    private final int id;             // Identifiant unique du chocolatier
    private final ControleTempereuse controleTempereuse;
    private final ControleMouleuse controleMouleuse;
    private final ApprovisionnementManager approManager;
    
    public Chocolatier(String type, int id, ControleTempereuse controleTempereuse, 
                      ControleMouleuse controleMouleuse, ApprovisionnementManager approManager) {
        this.type = type;
        this.id = id;
        this.controleTempereuse = controleTempereuse;
        this.controleMouleuse = controleMouleuse;
        this.approManager = approManager;
    }
    
    @Override
    public void run() {
        try {
            while (true) {
                // Vérifier s'il y a une rupture
                if (approManager.estEnRupture(type)) {
                    log("rupture");
                    approManager.attendreApprovisionnement(type);
                    log("approvisionnement");
                }
                
                // Étape de la tempéreuse
                log("requiereTempereuse");
                controleTempereuse.requiereTempereuse(type, id);
                log("tempereChocolat");
                Thread.sleep((long) (Math.random() * 1000)); // Simulation du temps de tempérage
                log("donneChocolat");
                controleTempereuse.donneChocolat(type, id);
                
                // Étape de la mouleuse
                log("requiereMouleuse");
                controleMouleuse.requiereMouleuse(type, id);
                log("remplit");
                Thread.sleep((long) (Math.random() * 500)); // Simulation du temps de remplissage
                log("garnit");
                Thread.sleep((long) (Math.random() * 300)); // Simulation du temps de garnissage
                log("ferme");
                controleMouleuse.ferme(type, id);
                
                // Pause entre cycles de production
                Thread.sleep((long) (Math.random() * 2000));
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log("interrompu");
        }
    }
    
    private void log(String action) {
        System.out.println("[" + type + "." + id + "] " + action);
    }
}
