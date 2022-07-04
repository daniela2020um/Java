package Model;

import java.util.*;
import Model.Exceptions.*;
import java.util.AbstractMap.SimpleEntry;

public class Equipa_Jogo extends Equipa
{
    private List<Jogador> jogadoresCampo;
    private List<Jogador> jogadoresBanco;
    private String tactic; // ex: 4-3-3
    private double qualidade_equipa;
    private Map<Integer, SimpleEntry<Integer, Integer>> substituicao; //minuto da substituição, jSai, jEntra

    public Equipa_Jogo(){
        super();
        this.jogadoresCampo = new ArrayList();
        this.jogadoresBanco = new ArrayList();
        this.tactic = "";
        this.qualidade_equipa = 0;
        this.substituicao = new TreeMap<>();
    }

    public Equipa_Jogo(Equipa a, ArrayList <Jogador> campo, ArrayList <Jogador>banco, TreeMap<Integer, SimpleEntry<Integer, Integer>> subst){
        super(a);
        this.jogadoresCampo = campo;
        this.jogadoresBanco = banco;
        this.tactic = "unknown";
        this.substituicao = subst;
        this.qualidade_equipa = calculaQualidade();
    }
    
    public Equipa_Jogo(Equipa e){
        super(e);
        this.tactic = "4-3-3";
        this.jogadoresCampo = startinglineup(this.tactic);
        this.jogadoresBanco =  atualizarBanco();
        this.qualidade_equipa = calculaQualidade();
        this.substituicao = new TreeMap<>();
    } 
    
    public Equipa_Jogo (Equipa_Jogo e){
        super(e);
        this.jogadoresCampo = e.getJogadoresCampo();
        this.jogadoresBanco = e.getJogadoresBanco();
        this.tactic = e.getTactic();
        this.qualidade_equipa = e.getQualidadeEquipa();
        this.substituicao = e.getSubstituicao();
    }
    
    public List<Jogador> getJogadoresCampo(){
        List<Jogador> resultado = new ArrayList<>();
        for (Jogador j: this.jogadoresCampo) {
            resultado.add(j.clone());
        }
        return resultado;
    }
    
    public void setJogadoresCampo(ArrayList <Jogador> l){
        this.jogadoresCampo = new ArrayList<>();
        for (Jogador j: l) {
            this.jogadoresCampo.add(j.clone());
        }
    }
    
    public List<Jogador> getJogadoresBanco(){
        List<Jogador> resultado = new ArrayList<>();
        for (Jogador j: this.jogadoresBanco) {
            resultado.add(j.clone());
        }
        return resultado;
    }
    
    public void setJogadoresBanco(ArrayList <Jogador> l){
        this.jogadoresBanco = new ArrayList<>();
        for (Jogador j: l) {
            this.jogadoresBanco.add(j.clone());
        }
    }

    public boolean validTactic(String s){
        String[] tactic = s.split("-");

        return Integer.parseInt(tactic[0]) + Integer.parseInt(tactic[1]) + Integer.parseInt(tactic[2]) == 10;

    }

    public String getTactic(){
        return this.tactic;
    }
    
    public void setTactic(String t) {
        this.tactic = t;
    }

    //recebendo uma nova tática e se esta for válida, faz as alterações necessárias...
    public void atualizaTatica (String t) throws TacticInvalid{
        if (!validTactic(t)) throw new TacticInvalid();
        setTactic(t);
        this.jogadoresCampo = startinglineup(this.tactic);
        this.jogadoresBanco =  atualizarBanco();
        this.qualidade_equipa = calculaQualidade();
    }

    public double getQualidadeEquipa(){
        return this.qualidade_equipa;
    }

    public void setQualidadeEquipa(double qualidade){
        if (qualidade > 100) this.qualidade_equipa = 100;
        else this.qualidade_equipa = qualidade;
    }
    
    public double calculaQualidade(){
        double qualidade = (qualidadeAtaque()+qualidadeDefesa()+qualidadeMeio())/3 + super.getTreinador().getBoost();
        if(qualidade > 100) qualidade = 100;
        return qualidade;
    }

    /*
    qualidade da equipa ---- defesa / meio campo / ataque / total(media do resto)
    defesa ---- GK,RB,CB / CM / FW
    meio campo ---- CM / FB,CB,FW / GK     0.45 0.35 0.20
    ataque ---- FW / FB,CM / GK,CB
    */
    public double qualidadeDefesa(){
        double high = 0, counth = 0, med = 0, countm = 0, low = 0, countl = 0, resultado = 0;
        for(Jogador j: this.jogadoresCampo){
            if(j instanceof GuardaRedes || j instanceof Defesas){
                high += j.getQualidade();
                counth++;
            }
            else if(j instanceof Medios){
                med += j.getQualidade();
                countm++;
            }
            else if(j instanceof Avancados || j instanceof Laterais){
                low += j.getQualidade();
                countl++;
            }
        }
        if (counth != 0) resultado += (high/counth)*0.45;
        if (countm != 0) resultado += (med/countm)*0.35;
        if (countl != 0) resultado += (low/countl)*0.20;
        return resultado;
    }

    public double qualidadeMeio(){
        double high = 0, counth = 0, med = 0, countm = 0, low = 0, countl = 0, resultado = 0;
        for(Jogador j: this.jogadoresCampo){
            if(j instanceof Medios){
                high += j.getQualidade();
                counth++;
            }
            else if(j instanceof Avancados || j instanceof Laterais){
                med += j.getQualidade();
                countm++;
            }
            else if(j instanceof GuardaRedes || j instanceof Defesas){
                low += j.getQualidade();
                countl++;
            }
        }
        if (counth != 0) resultado += (high/counth)*0.45;
        if (countm != 0) resultado += (med/countm)*0.35;
        if (countl != 0) resultado += (low/countl)*0.20;
        return resultado;
    }

    public double qualidadeAtaque(){
        double high = 0, counth = 0, med = 0, countm = 0, low = 0, countl = 0, resultado = 0;
        for(Jogador j: this.jogadoresCampo){
            if(j instanceof Avancados || j instanceof Laterais){
                high += j.getQualidade();
                counth++;
            }
            else if(j instanceof Medios){
                med += j.getQualidade();
                countm++;
            }
            else if(j instanceof GuardaRedes || j instanceof Defesas){
                low +=
                        j.getQualidade();
                countl++;
            }
        }
        if (counth != 0) resultado += (high/counth)*0.45;
        if (countm != 0) resultado += (med/countm)*0.35;
        if (countl != 0) resultado += (low/countl)*0.20;
        return resultado;
    }

    public Map<Integer, SimpleEntry<Integer, Integer>> getSubstituicao(){
        Map<Integer, SimpleEntry<Integer, Integer>> nova = new TreeMap<>();
        for (Map.Entry<Integer, SimpleEntry<Integer, Integer>> entry : this.substituicao.entrySet()){
            nova.put(entry.getKey(), entry.getValue());
        }
        return nova;
    }

    public void setSubstituicao(Map<Integer, SimpleEntry<Integer, Integer>> m){
        Map<Integer, SimpleEntry<Integer, Integer>> nova = new TreeMap<>();
        for (Map.Entry<Integer, SimpleEntry<Integer, Integer>> entry : m.entrySet()){
            nova.put(entry.getKey(), entry.getValue());
        }
        this.substituicao = nova;
    }

    public void addSubstituicao(Integer tempo, Integer sai, Integer entra){
        while (this.substituicao.containsKey(tempo)) {
            tempo++;
        }
        this.substituicao.put(tempo, new SimpleEntry(sai, entra));
    }

    public Equipa_Jogo clone(){
        return new Equipa_Jogo(this);
    }

    public boolean existeCampo(int num){
        return this.jogadoresCampo.stream().anyMatch(j->j.getNumeroCamisola() == num);
    }

    public void addCampo(Jogador j){
        this.jogadoresCampo.add(j.clone());
    }

    public boolean existeBanco(int num){
        return this.jogadoresBanco.stream().anyMatch(j->j.getNumeroCamisola() == num);
    }

    public void addBanco(Jogador j){
        this.jogadoresBanco.add(j.clone());
    }

    //indica quantas substituições serão feitas
    public int quantasSubs(){
        return this.substituicao.size();
    }

    public void trocaJogadores(int nSai, int nEntra) throws JogadorInexistente{
        Jogador auxSai = null;
        for (Jogador j: this.jogadoresCampo){
            if (j.getNumeroCamisola() == nSai) auxSai = j.clone();
        }
        if (auxSai == null) throw new JogadorInexistente();
        this.jogadoresCampo.remove(auxSai);
        addBanco(auxSai);

        Jogador auxEntra = null;
        for (Jogador j: this.jogadoresBanco){
            if (j.getNumeroCamisola() == nEntra) auxEntra = j.clone();
        }
        if (auxEntra == null) throw new JogadorInexistente();
        this.jogadoresBanco.remove(auxEntra);
        addCampo(auxEntra);
        this.qualidade_equipa = calculaQualidade();
    }

    public void addListaSubstituições (int minuto, int jSai, int jEntra) throws JogadorInexistente{
        if(!existeCampo(jSai) || !existeBanco(jEntra)) throw new JogadorInexistente();

        if (minuto < 0 || minuto > 90) minuto = 60;

        addSubstituicao(minuto, jSai, jEntra);
    }

    //indica o minuto em que foram feitas as substituições
    public int getMinutoSubs(int i){
        int conta = 0;
        for (Integer j : this.substituicao.keySet()){
            if (conta == i) {
                return j;
            }
            conta++;
        }
        return -1;
    }

    //faz as substituições que devem ocorrer no minuto dado
    public void subMinuto (int minuto){
        try {
            trocaJogadores(this.substituicao.get(minuto).getKey(), this.substituicao.get(minuto).getValue());
        }
        catch (JogadorInexistente j){}
    }

    public void substituiçao(int minuto, int jSai, int jEntra) throws JogadorInexistente{
        try{
            addListaSubstituições(minuto, jSai, jEntra);
            trocaJogadores(jSai, jEntra);
        }
        catch(JogadorInexistente j){
            throw new JogadorInexistente();
        }
        calculaQualidade();
    }
    
    public boolean equals(Object o){
        if (this == o){
            return true;
        }
        if ((o == null) || (o.getClass()!=this.getClass()))
            return false;
        Equipa_Jogo e = (Equipa_Jogo) o;
        return super.equals(e) && this.jogadoresCampo.equals(e.getJogadoresCampo())
                && this.jogadoresBanco.equals(e.getJogadoresBanco()) && this.tactic.equals(e.getTactic()) && this.qualidade_equipa == e.getQualidadeEquipa()
                && this.substituicao == e.getSubstituicao();//this.substituicaoEntra.equals(e.getSubstituicaoEntra()) && this.substituicaoSai.equals(e.getSubstituicaoSai());
    }

    public String titularesToString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Nome: ").append(super.getNome()).append("\nEquipa Técnica: ").append(super.getTreinador().toString()).append(" \nFormação: ").append(this.getTactic()).append(" \nJogadores em Campo:\n");
        for (Jogador j : this.getJogadoresCampo()){
            sb.append(j.getClass().getSimpleName()+": "+j.toString() +"\n");
        }
        return sb.toString();
    }

    public String substituicoesToString(){
        StringBuilder sb = new StringBuilder();
        for (int i : this.substituicao.keySet()){
            sb.append("Minuto: "+i+"|"+this.substituicao.get(i).getKey()+"->"+this.substituicao.get(i).getValue()+"\n");
        }
        return sb.toString();
    }

    public String toString (){
        StringBuilder sb = new StringBuilder();
        sb.append("Nome: ").append(super.getNome()).append("\nEquipa Técnica: ").append(super.getTreinador().toString()).append(" \nFormação: ").append(this.getTactic()).append(" \nJogadores em Campo:\n");
        for (Jogador j : this.getJogadoresCampo()){
            sb.append(j.getClass().getSimpleName()+": "+j.toString() +"\n");
        }
        sb.append(" \nJogadores no Banco:\n");
        for (Jogador j : this.getJogadoresBanco()){
            sb.append(j.getClass().getSimpleName()+": "+j.toString() +"\n");
        }
        sb.append(" \nSubstituiçoes:\n");
        for (int i : this.substituicao.keySet()){
            sb.append("Minuto: "+i+"|"+this.substituicao.get(i).getKey()+"->"+this.substituicao.get(i).getValue()+"\n");
        }
        sb.append("\n");
        return sb.toString();
    }

    public List<Jogador> startinglineup(String t){
        String[] tactic = t.split("-");
        List<Jogador> team = super.getJogadores();
        team.sort(Comparator.comparing(Jogador::getQualidade).reversed());
        List<Jogador> ret = new LinkedList<>();
        ret = adicionaMelhorGR(team, ret);
        if(Integer.parseInt(tactic[0]) == 3) ret = adicionaMelhoresDefesas(team, ret, Integer.parseInt(tactic[0])); //defesas = 3, laterais = 0;
        else {
            ret = adicionaMelhoresDefesas(team, ret, Integer.parseInt(tactic[0]) - 2); //defesas = tactic[0] - 2 laterais (obrigatorio)
            ret = adicionaMelhoresLaterais(team, ret, 2); // 2 laterais obrigatoriamente
        }
        ret = adicionaMelhoresMedios(team, ret, Integer.parseInt(tactic[1]));
        ret = adicionaMelhoresAvançados(team, ret, Integer.parseInt(tactic[2]));
        return ret;
    }

    public List<Jogador> adicionaMelhorGR(List<Jogador> team, List<Jogador> titulares){
        for (Jogador j : team){
            if(j instanceof GuardaRedes){
                titulares.add(j);
                break;
            }
        }
        return titulares;
    }

    public List<Jogador> adicionaMelhoresDefesas(List<Jogador> team, List<Jogador> titulares, Integer n){
        int adicionados = 0;
        for (Jogador j : team){
            if (j instanceof Defesas) {
                titulares.add(j);
                adicionados++;
            }
            if (adicionados == n) break;
        }
        return titulares;
    }

    public List<Jogador> adicionaMelhoresLaterais(List<Jogador> team, List<Jogador> titulares, Integer n){
        int adicionados = 0;
        for (Jogador j : team){
            if (j instanceof Laterais) {
                titulares.add(j);
                adicionados++;
            }
            if (adicionados == n) break;
        }
        return titulares;
    }

    public List<Jogador> adicionaMelhoresMedios(List<Jogador> team, List<Jogador> titulares, Integer n){
        int adicionados = 0;
        for (Jogador j : team){
            if (j instanceof Medios) {
                titulares.add(j);
                adicionados++;
            }
            if (adicionados == n) break;
        }
        return titulares;
    }

    public List<Jogador> adicionaMelhoresAvançados(List<Jogador> team, List<Jogador> titulares, Integer n){
        int adicionados = 0;
        for (Jogador j : team){
            if (j instanceof Avancados) {
                titulares.add(j);
                adicionados++;
            }
            if (adicionados == n) break;
        }
        return titulares;
    }

    public List<Jogador> atualizarBanco(){
        List <Jogador> res = new ArrayList<>();
        for (Jogador j: super.getJogadores()) {
            if (this.jogadoresCampo.stream().noneMatch(r -> r.equals(j))) {
                res.add(j);
            }
        }
        return res;
    }

    public void fadiga(){
        for(Jogador j: this.jogadoresCampo){
            j.fadiga();
        }
        this.qualidade_equipa = calculaQualidade();
    }

    public static Equipa_Jogo parse(Equipa a, List<String> s){
        Equipa_Jogo resultado = new Equipa_Jogo();
        ArrayList<Jogador> aux = new ArrayList<>();
        for (int i = 0; i < 11 ; i++){
            aux.add(a.procuraJogador(Integer.parseInt(s.get(i))));
        }

        resultado = new Equipa_Jogo(a, aux, new ArrayList<>(),new TreeMap<>());
        resultado.jogadoresBanco = resultado.atualizarBanco();

        for (int i = 11; i < s.size(); i++){
            String[] subs = s.get(i).split("->");

            try {
                resultado.substituiçao(60,Integer.parseInt(subs[0]), Integer.parseInt(subs[1]));
            }
            catch(JogadorInexistente e){System.out.println("Ups");}
        }
        return resultado;
    }
    
}
