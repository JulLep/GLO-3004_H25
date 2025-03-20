// Classe qui gère le contrôle d'accès aux tempéreuses
public class ControleTempereuse {
    private int nombreTempereuses;
    private int nnOccupees = 0;       // Nombre de tempéreuses occupées par des chocolatiers de type n
    private int nbOccupees = 0;       // Nombre de tempéreuses occupées par des chocolatiers de type b
    private int wnEnAttente = 0;      // Nombre de chocolatiers n en attente
    private int wbEnAttente = 0;      // Nombre de chocolatiers b en attente
    private boolean dernierAUtiliser = true;  // true pour n, false pour b
    
    public ControleTempereuse(int nombreTempereuses) {
        this.nombreTempereuses = nombreTempereuses;
    }
    
    public synchronized void requiereTempereuse(String type, int id) throws InterruptedException {
        if (type.equals("n")) {
            wnEnAttente++;
            while (!(nbOccupees == 0 && (wbEnAttente == 0 || !dernierAUtiliser) && nnOccupees < nombreTempereuses)) {
                wait();
            }
            wnEnAttente--;
            nnOccupees++;
        } else { // type b
            wbEnAttente++;
            while (!(nnOccupees == 0 && (wnEnAttente == 0 || dernierAUtiliser) && nbOccupees < nombreTempereuses)) {
                wait();
            }
            wbEnAttente--;
            nbOccupees++;
        }
    }
    
    public synchronized void donneChocolat(String type, int id) {
        if (type.equals("n")) {
            nnOccupees--;
            dernierAUtiliser = true;
        } else { // type b
            nbOccupees--;
            dernierAUtiliser = false;
        }
        notifyAll();
    }
    
    public synchronized void setNombreTempereuses(int nombreTempereuses) {
        this.nombreTempereuses = nombreTempereuses;
        System.out.println("[SYSTÈME] Nombre de tempéreuses modifié à: " + nombreTempereuses);
        notifyAll();
    }
    
    public synchronized int getNombreTempereuses() {
        return nombreTempereuses;
    }
}
