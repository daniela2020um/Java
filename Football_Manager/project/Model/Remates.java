package Model;

import java.io.Serializable;

public class Remates implements Serializable {
    private int rematesEquipaCasa;
    private int rematesEquipaFora;
    private int rematesPerigososEquipaCasa;
    private int rematesPerigososEquipaFora;

    public Remates(){
        this.rematesEquipaCasa = 0;
        this.rematesEquipaFora = 0;
        this.rematesPerigososEquipaCasa = 0;
        this.rematesPerigososEquipaFora = 0;
    }

    public Remates(int rematesEC, int rematesEF, int rPerigososEC, int rPerigososEF){
        this.rematesEquipaCasa = rematesEC;
        this.rematesEquipaFora = rematesEF;
        this.rematesPerigososEquipaCasa = rPerigososEC;
        this.rematesPerigososEquipaFora = rPerigososEF;
    }

    public Remates(Remates r){
        this.rematesEquipaCasa = r.getRematesEquipaCasa();
        this.rematesEquipaFora = r.getRematesEquipaFora();
        this.rematesPerigososEquipaCasa = r.getRematesPerigososEquipaCasa();
        this.rematesPerigososEquipaFora = r.getRematesPerigososEquipaFora();
    }

    public int getRematesEquipaCasa() {
        return this.rematesEquipaCasa;
    }

    public int getRematesEquipaFora() {
        return this.rematesEquipaFora;
    }

    public int getRematesPerigososEquipaCasa() {
        return this.rematesPerigososEquipaCasa;
    }

    public int getRematesPerigososEquipaFora() {
        return this.rematesPerigososEquipaFora;
    }

    public void setRematesEquipaCasa(int rematesEquipaCasa) {
        this.rematesEquipaCasa = rematesEquipaCasa;
    }

    public void setRematesEquipaFora(int rematesEquipaFora) {
        this.rematesEquipaFora = rematesEquipaFora;
    }

    public void setRematesPerigososEquipaCasa(int rematesPerigososEquipaCasa) {
        this.rematesPerigososEquipaCasa = rematesPerigososEquipaCasa;
    }

    public void setRematesPerigososEquipaFora(int rematesPerigososEquipaFora) {
        this.rematesPerigososEquipaFora = rematesPerigososEquipaFora;
    }

    public void adicionaRemateEquipa(int casaOuFora){
        if (casaOuFora == 0) setRematesEquipaCasa(getRematesEquipaCasa() + 1);
        else if (casaOuFora == 2) setRematesEquipaFora(getRematesEquipaFora() + 1);
    }

    public void adicionaRematePerigosoEquipa(int casaOuFora){
        if (casaOuFora == 0) setRematesPerigososEquipaCasa(getRematesPerigososEquipaCasa() + 1);
        else if(casaOuFora == 2) setRematesPerigososEquipaFora(getRematesPerigososEquipaFora() + 1);
    }

    public Remates clone() {
        return new Remates(this);
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("Remates da Equipa da Casa  : " + this.getRematesEquipaCasa() + " | " + "Remates perigosos da Equipa da Casa  : " + this.getRematesPerigososEquipaCasa() + "\n");
        s.append("Remates da Equipa Visitante: " + this.getRematesEquipaFora() + " | " + "Remates perigosos da Equipa Visitante: " + this.getRematesPerigososEquipaFora() + "\n");
        return s.toString();
    }

    public boolean equals(Object o){
        if (this == o){
            return true;
        }
        if ((o == null) || (o.getClass()!=this.getClass()))
            return false;
        Remates m = (Remates) o;
        return this.rematesEquipaCasa == m.rematesEquipaCasa && this.rematesEquipaFora == m.getRematesEquipaFora()
                && this.rematesPerigososEquipaCasa == getRematesPerigososEquipaFora() && this.rematesPerigososEquipaFora == getRematesPerigososEquipaFora();
    }


}
