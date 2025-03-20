// Classe qui gère le contrôle d'accès aux mouleuses
public class ControleMouleuse {
    private int nombreMouleuses;
    private int nnOccupees = 0;       // Nombre de mouleuses occupées par des chocolatiers de type n
    private int nbOccupees = 0;       // Nombre de mouleuses occupées par des chocolatiers de type b
    private int wnEnAttente = 0;      // Nombre de chocolatiers n en attente
    private int wbEnAttente = 0;      // Nombre de chocolatiers b en attente
    private boolean dernierAUtiliser = true;  // true pour n, false pour b
    
    public ControleMouleuse(int nombreMouleuses) {
        this.nombreMouleuses = nombreMouleuses;
    }
    
    public synchronized void requiereMouleuse(String type, int id) throws InterruptedException {
        if (type.equals("n")) {
            wnEnAttente++;
            while (!(nbOccupees == 0 && (wbEnAttente == 0 || !dernierAUtiliser) && nnOccupees < nombreMouleuses)) {
                wait();
            }
            wnEnAttente--;
            nnOccupees++;
        } else { // type b
            wbEnAttente++;
            while (!(nnOccupees == 0 && (wnEnAttente == 0 || dernierAUtiliser) && nbOccupees < nombreMouleuses)) {
                wait();
            }
            wbEnAttente--;
            nbOccupees++;
        }
    }
    
    public synchronized void ferme(String type, int id) {
        if (type.equals("n")) {
            nnOccupees--;
            dernierAUtiliser = true;
        } else { // type b
            nbOccupees--;
            dernierAUtiliser = false;
        }
        notifyAll();
    }
    
    public synchronized void setNombreMouleuses(int nombreMouleuses) {
        this.nombreMouleuses = nombreMouleuses;
        System.out.println("[SYSTÈME] Nombre de mouleuses modifié à: " + nombreMouleuses);
        notifyAll();
    }
    
    public synchronized int getNombreMouleuses() {
        return nombreMouleuses;
    }
}
