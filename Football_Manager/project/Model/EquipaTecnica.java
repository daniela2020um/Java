package Model;

import java.io.Serializable;
import java.util.Random;

public class EquipaTecnica implements Serializable {
    private int ataque;
    private int meiocampo;
    private int defesa;
    private int preparaçaofisica;
    private int liderança;
    private int estrategia;
    private int empatia;
    private double qualidade;
    private double boost;

    public EquipaTecnica(){
        Random r = new Random();
        this.ataque = r.nextInt(100);
        this.meiocampo = r.nextInt(100);
        this.defesa = r.nextInt(100);
        this.preparaçaofisica = r.nextInt(100);
        this.liderança = r.nextInt(100);
        this.estrategia = r.nextInt(100);
        this.empatia = r.nextInt(100);
        calculaQualidade();
        boostcalc();
    }

    public EquipaTecnica(int ataq, int mc, int def, int prepfisica, int liderança, int strat, int empatia){
        this.ataque = ataq;
        this.meiocampo = mc;
        this.defesa = def;
        this.preparaçaofisica = prepfisica;
        this.liderança = liderança;
        this.estrategia = strat;
        this.empatia = empatia;
        calculaQualidade();
        boostcalc();
    }

    public EquipaTecnica(EquipaTecnica t){
        this.ataque = t.getAtaque();
        this.defesa = t.getDefesa();
        this.meiocampo = t.getMeiocampo();
        this.preparaçaofisica = t.getPreparaçaofisica();
        this.liderança = t.getLiderança();
        this.estrategia = t.getEstrategia();
        this.empatia = t.getEmpatia();
        this.qualidade = t.getQualidade();
        this.boost = t.getBoost();
    }

    public int getAtaque() {
        return this.ataque;
    }

    public int getDefesa() {
        return this.defesa;
    }

    public int getMeiocampo() {
        return this.meiocampo;
    }

    public int getEmpatia() {
        return this.empatia;
    }

    public int getEstrategia() {
        return this.estrategia;
    }

    public int getLiderança() {
        return this.liderança;
    }

    public int getPreparaçaofisica() {
        return this.preparaçaofisica;
    }

    public double getBoost() {
        return this.boost;
    }

    public double getQualidade() {
        return this.qualidade;
    }

    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    public void setDefesa(int defesa) {
        this.defesa = defesa;
    }

    public void setEmpatia(int empatia) {
        this.empatia = empatia;
    }

    public void setEstrategia(int estrategia) {
        this.estrategia = estrategia;
    }

    public void setLiderança(int liderança) {
        this.liderança = liderança;
    }

    public void setMeiocampo(int meiocampo) {
        this.meiocampo = meiocampo;
    }

    public void setPreparaçaofisica(int preparaçaofisica) {
        this.preparaçaofisica = preparaçaofisica;
    }

    public void setBoost(double boost) {
        this.boost = boost;
    }

    public void setQualidade(double qualidade) {
        this.qualidade = qualidade;
    }

    //qualidade corresponde à media das variáveis
    public void calculaQualidade(){
        int sumQualidades = this.getAtaque() + this.getDefesa() + this.getEmpatia() + this.getEstrategia() + this.getLiderança() + this.getMeiocampo() + this.getPreparaçaofisica();
        setQualidade(sumQualidades / 7);
    }

    //boost que a equipa técnica dá à equipa, proporcinal à qualidade (0-15)
    public void boostcalc(){
        double boost = (this.getQualidade() * 15) / 100;
        setBoost(boost);
    }

    public EquipaTecnica clone(){
        return new EquipaTecnica(this);
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Qualidade: "+this.getQualidade());
    return sb.toString();
    }

    public boolean equals(Object o){
        if (this == o){
            return true;
        }
        if ((o == null) || (o.getClass()!=this.getClass()))
            return false;
        EquipaTecnica e = (EquipaTecnica) o;
        return  this.ataque == e.getAtaque() && this.meiocampo == e.getMeiocampo() &&
                this.defesa == e.getDefesa() && this.preparaçaofisica == e.getPreparaçaofisica() &&
                this.liderança == e.getLiderança() && this.estrategia == e.getEstrategia() &&
                this.empatia == e.getEmpatia() && this.qualidade == e.getQualidade() && this.boost == e.getBoost();
    }
}
