package Controller;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Scanner;
import Model.*;
import Model.Exceptions.*;
import View.Menu;
import java.util.InputMismatchException;
import java.io.FileNotFoundException;

public class Controller
{
    private Scanner scan;
    private Campeonato championship;
    private Menu view;
    private String[] filesDefault;
    
    public Controller(){
        this.scan = new Scanner(System.in);
        this.championship = new Campeonato();
        this.view = new Menu();
        this.filesDefault = new String [2];
        this.filesDefault[0] = "logsV2.txt";
        this.filesDefault[1] = "Campeonato.dat";
    }

    private int lerOpcao(){
        int op;
        this.view.waitingInstructions();
        try {
            op = this.scan.nextInt();
            this.scan.nextLine();
        }
        catch (InputMismatchException e) {
            op = -1;
            this.view.OpcaoInexistente();
        }
        return op;
    }

    public void start(){
        int instruction;
        this.view.inicio();
        do{
            instruction = lerOpcao();
            switch(instruction){
                case 0:
                    this.view.inicio();
                    break;

                case 1:
                    MenuLeitura();
                    break;

                case 2:
                    GravarObjectStream();
                    break;

                case 3:
                    MenuEquipas();
                    break;

                case 4:
                    MenuJogos();
                    break;
                
                case 5:
                    NovoJogo();
                    break;

                case -1:
                    gravarAux(this.filesDefault[1]);
                    break;

                default:
                    this.view.OpcaoInexistente();
            }
        }while(instruction != -1);
    }

    public void MenuLeitura() {
        boolean sucesso = false;
        while (!sucesso) {
            this.view.MenuLeitura();
            int op = lerOpcao();
            if (op == 1) {
                sucesso = LeituraLogs();
            } else if (op == 2) {
                sucesso = LeituraObjectStream();
            } else {
                if (op != 0) this.view.OpcaoInexistente();
                sucesso = true;
            }
            if (!sucesso){
                this.view.leituraImpossivel();
            }
        }
        this.view.LeituraConcluida();
        this.view.VoltarMenuAnterior();
    }

    public boolean LeituraLogs(){
        String s;
        boolean r = false;
        this.view.LeituraAux();
        int op = lerOpcao();

        if (op == 1){
            s = this.filesDefault[0];
        }
        else if (op == 2) {
            this.view.ImprimeString("Introduza o nome do file");
            this.view.waitingInstructions();
            s = this.scan.nextLine();
        }
        else{
            this.view.OpcaoInexistente();
            return false;
        }
        try{
            this.championship = this.championship.parse(s);
            r = true;
        }
        catch(FileNotFoundException fnf){
            this.view.errorFileNotFound();
        }
        return r;
    }

    public boolean LeituraObjectStream(){
        String s;
        boolean r = false;
        this.view.LeituraAux();
        int op = lerOpcao();

        if (op == 1){
            s = this.filesDefault[1];
        }
        else if (op == 2) {
            this.view.ImprimeString("Introduza o nome do file");
            this.view.waitingInstructions();
            s = this.scan.nextLine();
        }
        else{
            this.view.OpcaoInexistente();
            return false;
        }
        try{
            this.championship = this.championship.loadFromBinary(s);
            r = true;
        }
        catch (java.io.FileNotFoundException fnfe) {
            this.view.errorFileNotFound();
        }
        catch (java.io.IOException fnfe) {
        }
        catch (ClassNotFoundException cnfe) {
        }
        return r;
    }

    public void gravarAux(String s){
        try{
            this.championship.saveToBinary(s);
        }
        catch (java.io.FileNotFoundException fnfe){
            this.view.ImprimeString("Não consegui gravar os dados...!");
        }
        catch (java.io.IOException fnfe) {
            this.view.ImprimeString("Não consegui gravar os dados...!");
        }
    }

    public void GravarObjectStream(){
        this.view.Gravacao();
        int op = lerOpcao();
        String s = "";

        if (op == 0);
        else if (op == 1) gravarAux(this.filesDefault[1]);
        else if(op == 2){
            this.view.ImprimeString("Introduza o nome do file");
            this.view.waitingInstructions();
            s = this.scan.nextLine();
            gravarAux(s);
        }
        else{
            this.view.OpcaoInexistente();
        }
        this.view.VoltarMenuAnterior();
    }

    public void MenuEquipas(){
        int instruction;
        this.view.MenuEquipas();
        do{
            instruction = lerOpcao();
            switch (instruction) {
                case 0:
                    this.view.MenuEquipas();
                    break;

                case 1:
                    this.view.ImprimeString(this.championship.equipasToString());
                    break;

                case 2:
                    try{
                        this.view.ImprimeString("Indique o numero da equipa.");
                        Equipa aux = this.championship.consultaEquipa(lerOpcao());
                        this.view.ImprimeString(aux.toString());
                    }
                    catch (EquipaInexistente e){
                        this.view.equipaInexistente();
                    }
                    break;

                case 3:
                    this.view.InstrucaoNovaEquipa();
                    String nome = this.scan.nextLine();
                    int resultado = this.championship.criarEquipa(nome);
                    this.view.EquipaCriada(resultado);
                    break;

                case 4:
                    Transferencias();
                    break;

                case 5:
                    MenuContratacoes();
                    break;

                case -1:
                    this.view.VoltarMenuAnterior();
                    break;

                default:
                    this.view.OpcaoInexistente();
            }
        }while(instruction != -1);
    }

    public void Transferencias(){
        this.view.transferencia();
        int eSai = lerOpcao();
        int eEntra = lerOpcao();
        int numCamisola = lerOpcao();
        try{
            this.championship.transferencia(eSai,eEntra,numCamisola);
            this.view.ImprimeString("Transferência feita com sucesso.");
        }
        catch(EquipaInexistente e){
            this.view.equipaInexistente();
        }
        catch(JogadorInexistente j) {
            this.view.ImprimeString("o Jogador " + numCamisola + " nao existe na equipa nº: " + eSai);
        }
    }

    public void MenuContratacoes(){
        int instruction;
        this.view.MenuContratacoes();
        do{
            instruction = lerOpcao();
            switch(instruction){
                case 0:
                    this.view.MenuContratacoes();
                    break;

                case 1:
                    MenuNovoJogador();
                    break;

                case 2:
                    this.view.JogadoresParaContrato(this.championship.JogadoresParaContratar());
                    break;

                case 3:
                    this.view.JogadoresParaContrato(this.championship.JogadoresParaContratar());
                    this.view.ImprimeString("Numero da equipa no campeonato e numero do jogador na Lista.");
                    int equipa = lerOpcao();
                    int jogador = lerOpcao();
                    try{
                        this.championship.adicionarJogadorAEquipa(equipa,jogador);
                    }
                    catch(JogadorInexistente j){
                        this.view.jogadorInexistente();
                    }
                    catch(EquipaInexistente e){
                        this.view.equipaInexistente();
                    }
                    break;

                case -1:
                    this.view.VoltarMenuAnterior();
                    break;

                default:
                    this.view.OpcaoInexistente();
            }
        }while(instruction != -1);
    }

    public void MenuNovoJogador(){
        this.view.tipoJogador();
        int tipo = lerOpcao();
        if (tipo < 1 || tipo > 5) System.out.println("error!");

        this.view.CaracteristicasComuns();
        if (tipo == 1) this.view.novoGuardaRedes();
        else if (tipo == 2) this.view.novoDefesas();
        else if (tipo == 3) this.view.novoMedios();
        else if (tipo == 4) this.view.novoLaterais();
        else if (tipo == 5) this.view.novoAvançados();

        String nome = this.scan.nextLine();

        int valores [] = new int[9];
        int i = 0;
        while (i < 9){
            valores[i] = lerOpcao();
            i++;
        }

        Jogador j = null;
        if (tipo == 1) j = new GuardaRedes (nome, valores[0],valores[1],valores[2],valores[3],valores[4],valores[5],valores[6],valores[7],valores[8]);
        else if (tipo == 2) j = new Defesas (nome, valores[0],valores[1],valores[2],valores[3],valores[4],valores[5],valores[6],valores[7],valores[8]);
        else if (tipo == 3) j = new Medios (nome, valores[0],valores[1],valores[2],valores[3],valores[4],valores[5],valores[6],valores[7],valores[8]);
        else if (tipo == 4) j = new Laterais (nome, valores[0],valores[1],valores[2],valores[3],valores[4],valores[5],valores[6],valores[7],valores[8]);
        else if (tipo == 5) j = new Avancados (nome, valores[0],valores[1],valores[2],valores[3],valores[4],valores[5],valores[6],valores[7],valores[8]);

        this.championship.addJogador(j);
        this.view.ImprimeString("Jogador criado com sucesso.");
    }

    public void MenuJogos(){
        int instruction;
        this.view.MenuJogos();
        do {
            instruction = lerOpcao();
            switch (instruction) {
                case 0:
                    this.view.MenuJogos();
                    break;

                case 1:
                    this.view.ImprimeString(this.championship.jogosToString());
                    break;

                case 2:
                    this.view.ImprimeString("Introduza o numero do jogo");
                    int j = lerOpcao();
                    try{
                        Jogo resultado = this.championship.obterJogo(j);
                        this.view.ImprimeString(resultado.toString());
                    }
                    catch (JogoInexistente e){
                        this.view.ImprimeString("Jogo "+j+" não existe.");
                    }
                    break;

                case -1:
                    this.view.VoltarMenuAnterior();
                    break;

                default:
                    this.view.OpcaoInexistente();
            }
        }while(instruction != -1);
    }

    public void gestaoEquipas(Equipa_Jogo a){
        this.view.EquipaEmCampo(a.toString());
        this.view.ImprimeString("Esta é a nossa sugestão. Pode fazer agora as sugestões que achar necessárias.");
        this.view.gestaoEquipa();
        int op = -1;
        while (op != 0){
            op = lerOpcao();
            switch (op){
                case 0:
                    break;
                case 1:
                    this.view.ImprimeString("Adicione a nova tatíca que pretende usar. Formato: ?-?-?.");
                    String tatica = this.scan.nextLine();
                    try{
                        a.atualizaTatica(tatica);
                    }
                    catch(TacticInvalid t){
                        this.view.ImprimeString("Ups! Essa tática não é válida.\n");
                    }
                    break;
                case 2:
                    this.view.ImprimeString("Indique o numero camisola do jogador titular e o numero da camisola do jogador que pretende que o substitua. ");
                    int nSai = lerOpcao();
                    int nEntra = lerOpcao();
                    try {
                        a.trocaJogadores(nSai, nEntra);
                    }
                    catch(JogadorInexistente j){
                        this.view.jogadorInexistente();
                    }
                    break;

                case 3:
                    if ( a.quantasSubs() < 3) {
                        this.view.ImprimeString("Pode definir até 3 substituições.\nSe não quiser definir nenhuma substituição,escreva _, senão escreva *");
                        for (int i = 0; i < 3 && a.quantasSubs() < 3 ; i++) {

                            if (!this.scan.nextLine().equals("_")) {
                                this.view.ImprimeString("Indique quando quer fazer a substituição, o numero camisola do jogador titular e o numero da camisola do jogador que pretende que o substitua. ");
                                int min = lerOpcao();
                                int nS = lerOpcao();
                                int nE = lerOpcao();
                                try {
                                    a.addListaSubstituições(min, nS, nE);
                                } catch (JogadorInexistente j) {
                                    this.view.jogadorInexistente();
                                    i--;
                                }
                                if (i != 2) this.view.ImprimeString("ok, proxima substituição:");
                                else {
                                    this.view.ImprimeString("Done\n");
                                }
                            }
                        }
                    }
                    else{
                        this.view.ImprimeString("Numero máximo de sustituições é 3.");
                    }
                    break;
                case 4:
                    this.view.EquipaEmCampo(a.toString());
                    break;

                default:
                    this.view.OpcaoInexistente();
                    break;
            }
            if (op != 0) this.view.gestaoEquipa();
        }
    }

    public void simulaçãoJogo(Equipa_Jogo casa, Equipa_Jogo fora){
        this.view.ImprimeString("--Começo do Jogo--");
        Jogo novo = new Jogo(casa, fora);

        this.view.EquipaTitular(novo.getEquipaCasa().titularesToString());
        this.view.versus();
        this.view.EquipaTitular(novo.getEquipaFora().titularesToString());
        novo.boostEquipaCasa();
        double homequality = novo.getEquipaCasa().getQualidadeEquipa(), awayquality = novo.getEquipaFora().getQualidadeEquipa();
        int winner = novo.simulaResultado();
        Remates remates = novo.getRemates();
        int percentagemRemPerigosoCasa = (remates.getRematesPerigososEquipaCasa()*100) / remates.getRematesEquipaCasa();
        int percentagemRemPerigosoFora = (remates.getRematesPerigososEquipaFora()*100) / remates.getRematesEquipaFora();
        int percentagemGoloCasa = (novo.getGolosCasa()*100) / remates.getRematesEquipaCasa();
        int percentagemGoloFora = (novo.getGolosFora()*100) / remates.getRematesEquipaFora();

        this.view.ImprimeString(novo.substituicoesToString());

        this.view.resultado(novo.getEquipaCasa().getNome(), homequality, novo.getEquipaFora().getNome(), awayquality, novo.getGolosCasa(), novo.getGolosFora(), novo.getGolosCasaIntervalo(), novo.getGolosForaIntervalo());
        this.view.MinutosGolos(novo.getMinutosGolosCasa(), novo.getMinutosGolosFora());
        this.view.vencedor(winner, novo.getEquipaCasa().getNome(), novo.getEquipaFora().getNome());
        this.view.EstatísticasRemates(remates.toString());
        this.view.EstatísticasRematesPercentagens(percentagemRemPerigosoCasa, percentagemRemPerigosoFora, percentagemGoloCasa, percentagemGoloFora);
        this.championship.addJogo(novo);
    }

    public void NovoJogo(){
        this.view.NovoJogo();
        int equipa[] = new int[2];
        Equipa a, b;
        
        for(int i = 0; i < 2; i++){
            equipa[i] = lerOpcao();
        }
        try{
            a = this.championship.consultaEquipa(equipa[0]);
            b = this.championship.consultaEquipa(equipa[1]);
        }
        catch (EquipaInexistente e){
            this.view.equipaInexistente();
            this.view.VoltarMenuAnterior();
            return;
        }

        if(a.numeroJogadores() < 11){
            this.view.equipaPoucosJogadores(a.getNome());
            return;
        }
        if(b.numeroJogadores() < 11){
            this.view.equipaPoucosJogadores(b.getNome());
            return;
        }

        Equipa_Jogo casa = new Equipa_Jogo(a);
        Equipa_Jogo fora = new Equipa_Jogo(b);

        this.view.AvisoEquipasJogo();
        this.view.opEquipasJogo();
        int op = -1;
        while(op != 0){
            op = lerOpcao();
            switch (op){
                case 1:
                    gestaoEquipas(casa);
                    break;
                case 2:
                    gestaoEquipas(fora);
                    break;
                case 0:
                    break;
                default:
                    this.view.OpcaoInexistente();
                    break;
            }
            if (op != 0) this.view.opEquipasJogo();
        }
        simulaçãoJogo(casa,fora);
        this.view.VoltarMenuAnterior();
    }
}
