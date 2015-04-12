package com.acordei.api.domain;

import java.util.List;

/**
 * Created by joelcorrea on 12/04/15.
 */
public class PoliticoPropostas {
    private long propostasAprovadas;
    private long propostasRejeitadas;
    private long propostasArquivadas;
    private List<PoliticoProjetoDeLei> projetos;

    public PoliticoPropostas(List<PoliticoProjetoDeLei> projetos) {
        this.projetos = projetos;
    }

    public PoliticoPropostas(int propostasAprovadas, int propostasRejeitadas, int propostasArquivadas, List<PoliticoProjetoDeLei> projetos) {
        this.propostasAprovadas = propostasAprovadas;
        this.propostasRejeitadas = propostasRejeitadas;
        this.propostasArquivadas = propostasArquivadas;
        this.projetos = projetos;
    }

    public long getPropostasAprovadas() {
        return propostasAprovadas;
    }

    public void setPropostasAprovadas(long propostasAprovadas) {
        this.propostasAprovadas = propostasAprovadas;
    }

    public long getPropostasRejeitadas() {
        return propostasRejeitadas;
    }

    public void setPropostasRejeitadas(long propostasRejeitadas) {
        this.propostasRejeitadas = propostasRejeitadas;
    }

    public long getPropostasArquivadas() {
        return propostasArquivadas;
    }

    public void setPropostasArquivadas(long propostasArquivadas) {
        this.propostasArquivadas = propostasArquivadas;
    }

    public List<PoliticoProjetoDeLei> getProjetos() {
        return projetos;
    }

    public void setProjetos(List<PoliticoProjetoDeLei> projetos) {
        this.projetos = projetos;
    }
}
