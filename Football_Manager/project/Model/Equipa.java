package Model;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;
import java.util.Random;
import java.util.stream.Collectors;

public class Equipa implements Serializable{
    private String nome;
    private List <Jogador> jogadores;
    private int pontos;
    private EquipaTecnica treinador;

    public Equipa (){
        this.nome = "";
        this.jogadores = new ArrayList<>();
        this.pontos = 0;
        this.treinador = new EquipaTecnica();
    }

    public Equipa (String nome){
        this.nome = nome;
        this.jogadores = new ArrayList<>();
        this.pontos = 0;
        this.treinador = new EquipaTecnica();
    }

    public Equipa(String nome, ArrayList<Jogador> jogadores, int p){
        this.nome = nome;
        this.jogadores = new ArrayList<>();
        for(Jogador j : jogadores) this.jogadores.add(j.clone());
        this.pontos = p;
        this.treinador = new EquipaTecnica();
    }

    public Equipa(Equipa e){
        this.nome = e.getNome();
        this.jogadores = e.getJogadores();
        this.pontos = e.getPontos();
        this.treinador = e.getTreinador();
    }

    public EquipaTecnica getTreinador() {
        return this.treinador.clone();
    }

    public void setTreinador(EquipaTecnica treinador) {
        this.treinador = treinador.clone();
    }

    public String getNome(){
        return this.nome;
    }
    
    public void setNome(String nome){
        this.nome = nome;
    }

    public List<Jogador> getJogadores(){
        List<Jogador> res = new ArrayList<>();

        for(Jogador j: this.jogadores){
            res.add(j.clone());
        }
        return this.jogadores;
    }

    public void setJogadores (ArrayList<Jogador> j){
        this.jogadores = new ArrayList<>();
        for(Jogador jogador : j)
            this.jogadores.add(jogador.clone());
    }
    
    public int getPontos(){
        return this.pontos;
    }
    
    public void setPontos(int p){
        this.pontos = p;
    }
    
    public void addPontos(int p){
        this.pontos += p;
    }

    public Equipa clone(){
        return new Equipa(this);
    }

    public void removeJogador (int numCamisola){
        this.jogadores.removeIf(j -> j.getNumeroCamisola()==numCamisola);
    }

    public Jogador procuraJogador (int numCamisola){
        Jogador resultado = null;

        for (Jogador j: this.jogadores){
            if (j.getNumeroCamisola() == numCamisola) resultado = j.clone();
        }
        return resultado;
    }

    public void adicionaJogador (Jogador j){
        Random rand = new Random();
        while (this.jogadores.stream().anyMatch(r->r.getNumeroCamisola() == j.getNumeroCamisola())){
            j.setNumeroCamisola(rand.nextInt(100));
        }
        this.jogadores.add(j.clone());
        j.addCarrer(this.nome);
    }

    public int numeroJogadores(){
        return this.jogadores.size();
    }

    public String toString (){
        StringBuilder sb = new StringBuilder();
        sb.append("Nome: ").append(getNome()).append("\nEquipa Técnica: ").append(this.getTreinador().toString()).append(" \nJogadores:\n");
        for (Jogador j : getJogadores()){
            sb.append(j.getClass().getSimpleName()+": "+j.toString() +"\n");
        }
        return sb.toString();
    }
    
    public boolean equals(Object o){
        if (this == o){
            return true;
        }
        if ((o == null) || (o.getClass()!=this.getClass()))
            return false;
        Equipa e = (Equipa) o;
        return  this.nome == e.getNome() 
                && this.jogadores.equals(e.getJogadores())
                && this.pontos == e.getPontos() && this.treinador.equals(e.getTreinador());
    }  
    
    public static Equipa parse(String s){
        String[] campos = s.split(",");
        Equipa e = new Equipa();
        e.setNome(campos[0]);       
        return e;
    }
}