package Model;
import java.io.Serializable;

public class Laterais extends Jogador implements Serializable{
    private int cruzamentos;

    public Laterais (){
        super();
        this.cruzamentos = 0;
    }
    
    public Laterais(String nome, int n, int v, int r, int d, int i, int jc, int remate, int cpasse, int cruzamentos){
        super(nome,n,v, r, d, i, jc, remate, cpasse,null);
        this.cruzamentos = super.limita(cruzamentos);
        calculaQualidade();
    }
    
    public Laterais (Laterais m){
        super(m);
        this.cruzamentos = m.getCruzamentos();
    }
    
    public int getCruzamentos(){
        return this.cruzamentos;
    }
    
    public void setCruzamentos(int r){
        this.cruzamentos = r;
    }

    public void diminuiCruzamentos(int quanto){
        this.cruzamentos = limita(this.cruzamentos - quanto);
    }
    
    public void calculaQualidade (){
        super.setQualidade(super.getCapacidade_passe()*0.2 + super.getDestreza()*0.025 + super.getImpulsao()*0.025 + super.getJogo_de_cabeca()*0.025+ super.getRemate()*0.025+ super.getResistencia()*0.2+super.getVelocidade()*0.2+this.getCruzamentos()*0.3);
    }

    public void fadiga(){
        super.diminuiVelocidade(2);
        super.diminuiResistência(3);
        super.diminuiDestreza(2);
        super.diminuiImpulsao(1);
        super.diminuiJogoCabeca(1);
        super.diminuiRemate(1);
        super.diminuiCapacidadePasse(1);
        diminuiCruzamentos(3);
        calculaQualidade();
    }

    public Laterais clone (){
        return new Laterais(this);
    }

    public boolean equals(Object o){
        if (this == o){
            return true;
        }
        if ((o == null) || (o.getClass()!=this.getClass()))
            return false;
        Laterais m = (Laterais) o;
        return super.equals(m) && this.cruzamentos == m.getCruzamentos();
    }
    
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()).append(this.getCruzamentos()).append(",").append(super.getQualidade()).append(",").append(super.getCarreer());
        return sb.toString();
    }
    
    public static Laterais parse(String s){
        String[] campos = s.split(",");
        return new Laterais(campos[0], Integer.parseInt(campos[1]),
                Integer.parseInt(campos[2]),
                Integer.parseInt(campos[3]),
                Integer.parseInt(campos[4]),
                Integer.parseInt(campos[5]),
                Integer.parseInt(campos[6]),
                Integer.parseInt(campos[7]),
                Integer.parseInt(campos[8]),
                Integer.parseInt(campos[9]));     
    }
}