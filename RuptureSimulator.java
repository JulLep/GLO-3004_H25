// Simulateur de ruptures
public class RuptureSimulator implements Runnable {
    private final ApprovisionnementManager approManager;
    private final int nombreChocolatiers;
    
    public RuptureSimulator(ApprovisionnementManager approManager, int nombreChocolatiers) {
        this.approManager = approManager;
        this.nombreChocolatiers = nombreChocolatiers;
    }
    
    @Override
    public void run() {
        try {
            while (true) {
                // Attendre un moment avant de déclencher une rupture
                Thread.sleep(10000);
                
                // Déclencher une rupture aléatoirement pour n ou b
                String type = Math.random() < 0.5 ? "n" : "b";
                approManager.declencherRupture(type);
                
                // Attendre un moment avant de réapprovisionner
                Thread.sleep(5000);
                
                // Réapprovisionner
                approManager.approvisionner(type);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
