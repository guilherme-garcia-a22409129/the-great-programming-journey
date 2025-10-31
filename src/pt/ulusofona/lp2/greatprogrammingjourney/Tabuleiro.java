package pt.ulusofona.lp2.greatprogrammingjourney;

public class Tabuleiro {
    private int tamanho;

    public Tabuleiro(int tamanho) {
        this.tamanho = tamanho;
    }

    public int tamanho() {
        return this.tamanho;
    }

    public String slotImage(int slot) {
        if (slot == 1) {
            return "start.png";
        } else if (slot == this.tamanho-1) {
            return "finish.png";
        } else if (slot > 0 && slot < this.tamanho-1) {
            return "normal.png";
        } else {
            return null;
        }
    }
}
