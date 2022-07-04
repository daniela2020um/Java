package Model;

import java.util.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.util.stream.Collectors;
import Model.Exceptions.*;

public class Campeonato implements Serializable
{
    private List<Jogo> jogos;
    private List<Jogador> paraContratar; //jogadores que nao estao associados a nenhuma equipa e podem ser contratados
    private Map<Integer, Equipa> equipas; //cada equipa esta associada a um numero, facilita a consulta
  
    public static final int Vitoria = 3;
    public static final int Empate = 1;
    
    public Campeonato(){
        this.jogos = new ArrayList<>();
        this.paraContratar = new ArrayList<>();
        this.equipas = new LinkedHashMap<>();
    }
    
    public Campeonato (Campeonato c){
        this.jogos = c.getJogos();
        this.paraContratar = c.getParaContratar();
        this.equipas = c.getEquipas();
    }
    
    public List<Jogo> getJogos(){
        List<Jogo> res = new ArrayList<>();
        for (Jogo j: this.jogos){
            res.add(j.clone());
        }
        return res;
    }
    
    public void setJogos(ArrayList<Jogo> j){
        this.jogos = new ArrayList<>();

        for (Jogo a : j){
            this.jogos.add(a.clone());
        }
    }

    public void addJogo(Jogo j){
        Equipa e1, e2;
        int gC,gF;
        try{
            e1 = procuraEquipa(j.getEquipaCasa().getNome());
            e2 = procuraEquipa(j.getEquipaFora().getNome());
            gC = j.getGolosCasa();
            gF = j.getGolosFora();
        }
        catch(EquipaInexistente erro){
            return;
        }
        try{
            if(gC > gF) setPontos(e1,Vitoria);
            if(gC == gF) {
                setPontos(e1,Empate);
                setPontos(e2,Empate);
            }
            if(gC < gF) setPontos(e2,Vitoria);
        }
        catch(EquipaInexistente e){
        }
        this.jogos.add(j.clone());
        Map<Integer, Equipa> classificacao = this.equipas.entrySet().stream()
                .sorted(Comparator.comparingInt(e -> -e.getValue().getPontos()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> { throw new AssertionError(); },
                        LinkedHashMap::new
                ));
        setEquipas(classificacao);
    }

    public Jogo obterJogo(int i) throws JogoInexistente {
        if ( i < 0 || i > this.jogos.size()) throw new JogoInexistente();
        return this.jogos.get(i).clone();
    }
    
    public void setPontos(Equipa w, int pontos) throws EquipaInexistente {
        for (Equipa e : this.equipas.values()){
            if (e.equals(w)) e.addPontos(pontos);
        }
    }
    
    public List<Jogador> getParaContratar(){
        List<Jogador> res = new ArrayList<>();
        for (Jogador j: this.paraContratar){
            res.add(j.clone());
        }
        return res;
    }

    public void setParaContratar(ArrayList<Jogador> j){
        this.paraContratar = new ArrayList<>();
        for (Jogador a: j){
            this.paraContratar.add(a.clone());
        }
    }
    
    public void addJogador(Jogador j){
        if (j != null) this.paraContratar.add(j.clone());
    }

    public Map<Integer,Equipa> getEquipas(){
        Map<Integer, Equipa> novo = new LinkedHashMap<>();
        for (Map.Entry<Integer, Equipa> entry : this.equipas.entrySet()){
            novo.put(entry.getKey(),  entry.getValue().clone());
        }
        return novo;
    }

    public void setEquipas(Map<Integer,Equipa> e) {
        Map<Integer, Equipa> novo = new LinkedHashMap<>();
        for (Map.Entry<Integer, Equipa> entry : e.entrySet()) {
            novo.put(entry.getKey(), entry.getValue().clone());
            this.equipas = novo;
        }
    }
    
    public void addEquipa(Equipa eq){
        int a = this.equipas.size();
        this.equipas.put(a,eq.clone());        
    }

    //faz a procura de uma equipa pelo o seu numero no campeonato
    public Equipa consultaEquipa(int i) throws EquipaInexistente{
        if(i < 0 || i >= this.equipas.size()) throw new EquipaInexistente();
        else return this.equipas.get(i).clone();
    }

    //procura uma equipa pelo nome
    public Equipa procuraEquipa(String nome) throws EquipaInexistente{
        Equipa resultado = null;
        for(Equipa e: this.equipas.values()){
            if(e.getNome().equals(nome)) resultado = e.clone();
        }
        if (resultado == null) throw new EquipaInexistente();
        else return resultado;
    }

    //Verifica se uma equipa não existe e cria uma nova com o nome dado
    public int criarEquipa(String nome){
        if(this.equipas.values().stream().anyMatch(r->r.getNome().equals(nome))) return -1;
        else{
            Equipa nova = new Equipa(nome);
            addEquipa(nova);
            return this.equipas.size()-1;
        }
    }
    
    public void adicionarJogadorAEquipa(int equipa, int jogador) throws JogadorInexistente, EquipaInexistente{
        Jogador a;
        Equipa e;
        if (jogador < 0 || jogador > this.paraContratar.size()) throw new JogadorInexistente("Jogador nº "+jogador+" nao existe.");
        else{
            if(equipa < 0 || equipa > this.equipas.size()) throw new EquipaInexistente("Equipa nº "+equipa+" nao existe.");
            else{
                a = this.paraContratar.get(jogador-1);
                e = this.equipas.get(equipa);
                this.paraContratar.remove(jogador-1);
                e.adicionaJogador(a.clone());
            }
        }
    }

    public void  transferencia (int eSai, int eEntra, int numCamisola) throws JogadorInexistente, EquipaInexistente{
        Equipa f = this.equipas.get(eSai);
        if (f == null) throw new EquipaInexistente();
        Jogador aux;
        if ((aux = f.procuraJogador(numCamisola)) == null)
            throw new JogadorInexistente("o Jogador " + numCamisola + " nao existe na equipa nº: " + eSai);

        if (eEntra != -1) {
            Equipa e = this.equipas.get(eEntra);
            if (e == null) throw new EquipaInexistente();
            f.removeJogador(numCamisola);
            e.adicionaJogador(aux);
        }
        else {
            f.removeJogador(numCamisola);
            addJogador(aux);

        }
    }
    
    public String toString (){
        StringBuilder sb = new StringBuilder();
        
        for(Integer e: this.equipas.keySet()){
            sb.append(e+ ": "+this.equipas.get(e).getNome()+" -> "+ this.equipas.get(e).getPontos()).append("\n");
        }
        
        for (Jogador j: this.paraContratar){
            sb.append(j.toString()+"\n");
        }
        
        for(Jogo j : this.jogos){
            sb.append(j.getEquipaCasa().getNome()+" X "+j.getEquipaFora().getNome()+"\n");
        }
        
        return sb.toString();
    }
    
    public String equipasToString(){
        StringBuilder s = new StringBuilder();
        for(Integer e: this.equipas.keySet()){
            s.append("("+e+") "+this.equipas.get(e).getNome()+": "+ this.equipas.get(e).getPontos()).append("\n");
        }
        return s.toString();
    }
    
    public String jogosToString(){
        StringBuilder s = new StringBuilder();
        int i = 0;
        for(Jogo j : this.jogos){
            s.append(i+":" +j.getEquipaCasa().getNome()+" X "+j.getEquipaFora().getNome()+": ");
            s.append(j.getGolosCasa()+":"+j.getGolosFora()+"  "+j.getDate()+"\n");
            i++;
        }
        return s.toString();
    }
    
    public List<String> JogadoresParaContratar(){
        List<String> resultado = new ArrayList<String>();
        for (Jogador j: this.paraContratar){
            resultado.add(j.toString());
        }
        return resultado;
    }
    
    public static List<String> lerTodasLinhas(String filepath) throws FileNotFoundException{
        List<String> l;
        try{ l = Files.readAllLines(Paths.get(filepath) , StandardCharsets.UTF_8); }
        catch (IOException exc) { throw new FileNotFoundException();}
        return l;
    }
    
    public static Campeonato parse(String file) throws FileNotFoundException{
        Campeonato novo = new Campeonato();
        List<String> l;
        try{l = lerTodasLinhas(file);}
        catch (IOException exc) {throw new FileNotFoundException();}
        Equipa ultima = null; Jogador j = null;
        String[] linhaPartida;
            
        for(String linha: l){
            linhaPartida = linha.split(":",2);
            switch(linhaPartida[0]){
                case "Equipa": 
                    Equipa e = Equipa.parse(linhaPartida[1]);
                    ultima = e;
                    novo.addEquipa(e);
                    break;
                case "Guarda-Redes":
                    GuardaRedes g = GuardaRedes.parse(linhaPartida[1]);
                    ultima.adicionaJogador(g);
                    break;
                case "Defesa": 
                    Defesas d = Defesas.parse(linhaPartida[1]);
                    ultima.adicionaJogador(d);
                    break;                    
                case "Medio":
                    Medios m = Medios.parse(linhaPartida[1]);
                    ultima.adicionaJogador(m);
                    break;
                case "Lateral":
                    Laterais k = Laterais.parse(linhaPartida[1]);
                    ultima.adicionaJogador(k);
                    break;
                case "Avancado":
                    Avancados a = Avancados.parse(linhaPartida[1]);
                    ultima.adicionaJogador(a);
                    break; 
                case "Jogo":
                    String[] campos = linhaPartida[1].split(",");
                    Equipa equipa1, equipa2;
                    try{
                        equipa1 = novo.procuraEquipa(campos[0]);
                        equipa2 = novo.procuraEquipa(campos[1]);  
                    }
                    catch(EquipaInexistente erro){
                        System.out.println("Erro!");
                        return novo;
                    }
                    Jogo game = Jogo.parse(equipa1, equipa2, linhaPartida[1]);
                    novo.addJogo(game);
                    break;
            }
        }
        
        return novo;      
    }
    
    public void saveToBinary (String fn) throws FileNotFoundException, IOException{
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fn));
        oos.writeObject(this);
        //oos.writeObject(DriveIt.comparators);
        oos.close();
    }
    
    public static Campeonato loadFromBinary(String fn) throws FileNotFoundException, IOException,ClassNotFoundException{
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fn));
        Campeonato di = (Campeonato)ois.readObject(); 
        ois.close();
        return di;
    }
}
