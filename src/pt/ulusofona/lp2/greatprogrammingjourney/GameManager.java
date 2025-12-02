package pt.ulusofona.lp2.greatprogrammingjourney;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.lang.Integer.parseInt;

public class GameManager {
    static HashMap <Integer, Jogador> jogadores; // initialize map inside createInitialBoard to avoid persistent data during tests
    static Jogador vencedor;
    static Tabuleiro tabuleiro;
    static int nrTurnos = 0;

    public GameManager() {}

    public boolean createInitialBoard(String[][] playerInfo, int worldSize){
        if (playerInfo.length < 2 || playerInfo.length > 4) {
            return false;
        }

        if (worldSize < playerInfo.length*2) {
            return false;
        }

        jogadores = new HashMap<>();
        tabuleiro = new Tabuleiro(worldSize);
        vencedor = null;
        nrTurnos = 1;

        for (String[] j : playerInfo) {
            if (!Jogador.valida(j)) {
                return false;
            }

            Jogador jogador = new Jogador(j);
            jogadores.put(jogador.id(), jogador);
        }

        return true;
    }

    public boolean createInitialBoard(String[][] playerInfo, int worldSize, String[][] abyssesAndTools) {
        return true;
    }

    public String getImagePng(int nrSquare) {
        return tabuleiro.slotImage(nrSquare);
    }

    public String[] getProgrammerInfo(int id){
        Jogador jogador = jogadores.get(id);
        if (jogador != null) {
            return jogador.toArray();
        }
        return null;
    }

    public String getProgrammerInfoAsStr(int id){
        Jogador jogador = jogadores.get(id);
        if (jogador != null) {
            return jogador.toString();
        }
        return null;
    }

    public String getProgrammersInfo() {
        return "";
    }

    public String[] getSlotInfo(int slot){
        if (slot < 1 || slot > tabuleiro.tamanho()) {
            return null;
        }

        String[] res = new String[]{""};
        List<String> ids = new ArrayList<>();

        // grab ids...
        for (Integer id : jogadores.keySet()) {
            if (jogadores.get(id).posicao() == slot) {
                ids.add(String.valueOf(id));
            }
        }

        res[0] = String.join(",", ids);

        return res;
    }

    public int getCurrentPlayerID(){
        ArrayList<Integer> ids = new ArrayList<>(jogadores.keySet());
        ids.sort(Integer::compareTo);

        int index = (nrTurnos-1) % ids.size();

        return ids.get(index);
    }

    public boolean moveCurrentPlayer(int spaces){
        if (spaces < 1 || spaces > 6) {
            return false;
        }

        Jogador jogador = jogadores.get(getCurrentPlayerID());
        int pos = jogador.posicao();

        if (pos + spaces > tabuleiro.tamanho()) {
            spaces = tabuleiro.tamanho() - pos;
        }

        jogador.avanca(spaces);
        nrTurnos++;

        // set winner
        if (jogador.posicao() == tabuleiro.tamanho()) {
            if (vencedor == null) {
                vencedor = jogador;
            }
        }

        return true;
    }

    public String reactToAbyssOrTool() {
        return "";
    }

    public boolean gameIsOver(){
        return !getSlotInfo(tabuleiro.tamanho())[0].isEmpty();
    }

    public ArrayList<String> getGameResults(){
        ArrayList<String> res = new ArrayList<>();

        res.add("THE GREAT PROGRAMMING JOURNEY");
        res.add("");
        res.add("NR. DE TURNOS");
        res.add(String.valueOf(nrTurnos));
        res.add("");
        res.add("VENCEDOR");
        res.add(((vencedor != null) ? vencedor.nome() : ""));
        res.add("");
        res.add("RESTANTES");

        List<Jogador> rem = new ArrayList<>(jogadores.values());
        if (vencedor != null) {
            rem.removeIf(v -> v.id() == vencedor.id());
        }

        rem.sort((a, b) -> {
            int cmp = Integer.compare(b.posicao(), a.posicao());
            if (cmp == 0) {
                cmp = a.nome().compareToIgnoreCase(b.nome());
            }
            return cmp;
        });

        for (Jogador jogador : rem) {
            res.add(jogador.nome() + " " +  jogador.posicao());
        }

        return res;
    }

    public void loadGame(File file) throws InvalidFileException, FileNotFoundException {

    }

    public boolean saveGame(File file) {
        return false;
    }

    // TODO...
    public JPanel getAuthorsPanel() {
        return new JPanel();
    }

    // TODO...
    public HashMap<String, String> customizeBoard() {
        return new HashMap<>();
    }
}
