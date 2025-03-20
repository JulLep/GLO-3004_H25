// Classe qui gère les ruptures et approvisionnements
public class ApprovisionnementManager {
    private boolean ruptureN = false;
    private boolean ruptureB = false;
    
    public synchronized boolean estEnRupture(String type) {
        return type.equals("n") ? ruptureN : ruptureB;
    }
    
    public synchronized void declencherRupture(String type) {
        if (type.equals("n")) {
            ruptureN = true;
            System.out.println("[SYSTÈME] Rupture de chocolat de type n");
        } else {
            ruptureB = true;
            System.out.println("[SYSTÈME] Rupture de chocolat de type b");
        }
    }
    
    public synchronized void approvisionner(String type) {
        if (type.equals("n")) {
            ruptureN = false;
            System.out.println("[SYSTÈME] Approvisionnement en chocolat de type n");
        } else {
            ruptureB = false;
            System.out.println("[SYSTÈME] Approvisionnement en chocolat de type b");
        }
        notifyAll();
    }
    
    public synchronized void attendreApprovisionnement(String type) throws InterruptedException {
        while (estEnRupture(type)) {
            wait();
        }
    }
}
