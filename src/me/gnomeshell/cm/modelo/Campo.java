package me.gnomeshell.cm.modelo;

import me.gnomeshell.cm.excesao.ExplosaoException;

import java.util.ArrayList;
import java.util.List;

public class Campo {
    private boolean minado;
    private boolean aberto;
    private boolean marcado;

    private final int linha;
    private final int coluna;

    private List<Campo> vizinhos = new ArrayList<>();


    Campo(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
    }

    boolean adicionarVizinho(Campo vizinho) {
        boolean linhaDiferente = linha != vizinho.linha;
        boolean colunaDiferente = coluna != vizinho.coluna;
        boolean diagonal = linhaDiferente && colunaDiferente;

        int deltaLinha = Math.abs(linha - vizinho.linha);
        int deltaColuna = Math.abs(coluna - vizinho.coluna);
        int deltaGeral = deltaColuna + deltaLinha;

        if (deltaGeral == 1 && !diagonal) {
            vizinhos.add(vizinho);
            return true;
        } else if (deltaGeral == 2 && diagonal) {
            vizinhos.add(vizinho);
            return true;
        } else {
            return false;
        }
    }

    void alternarMarcacao() {
        // se não estiver aberto, o marcado recebe o não marcado
        if (!aberto) {
            marcado = !marcado;
        }
    }

    boolean abrir() {
        if (!aberto && !marcado) {
            aberto = true;
            if (minado) {
                throw new ExplosaoException();
            }
            if (vizinhacaSegura()) {
                vizinhos.forEach(v -> v.abrir());
            }
            return true;
        } else {
            return false;
        }
    }
    boolean vizinhacaSegura() {
        // chama a stream para verificar se o campo esta minado, retornando true or false.
        return vizinhos.stream().noneMatch(v -> v.minado);
    }

}
