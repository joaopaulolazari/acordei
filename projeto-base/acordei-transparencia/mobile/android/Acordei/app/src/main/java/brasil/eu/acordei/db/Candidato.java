package brasil.eu.acordei.db;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author deivison
 */
public class Candidato {

    private String sqCand;
    private String nome;
    private String nomeUrna;
    private String numero;
    private String foto;
    private String situacao;
    private String partido;
    private String coligacao;
    private String candidato;
    private String dataNascimento;
    private String estadoCivil;
    private String nacionalidade;
    private String naturalidade;
    private String instrucao;
    private String ocupacao;
    private String limiteGasto;
    private String estado;
    private String site;
    private String fonte;
    private String cargo;

    public Candidato() {
    }

    public Candidato(String sqCand,String nome, String nomeUrna, String numero, String situacao, String partido, String coligacao,String candidato,String cargo) {
        this.sqCand = sqCand;
        this.nome = nome;
        this.nomeUrna = nomeUrna;
        this.numero = numero;
        this.situacao = situacao;
        this.partido = partido;
        this.coligacao = coligacao;
        this.candidato = candidato;
        this.cargo = cargo;
    }


    public String getColigacao() {
        return coligacao;
    }

    public String getNome() {
        return nome;
    }

    public String getNomeUrna() {
        return nomeUrna;
    }

    public String getNumero() {
        return numero;
    }

    public String getPartido() {
        return partido;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setColigacao(String coligacao) {
        this.coligacao = coligacao;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setNomeUrna(String nomeUrna) {
        this.nomeUrna = nomeUrna;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setPartido(String partido) {
        this.partido = partido;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public String getCandidato() {
        return candidato;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }


    public void setCandidato(String candidato) {
        this.candidato = candidato;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public void setFonte(String fonte) {
        this.fonte = fonte;
    }

    public void setInstrucao(String instrucao) {
        this.instrucao = instrucao;
    }

    public void setLimiteGasto(String limiteGasto) {
        this.limiteGasto = limiteGasto;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public void setNaturalidade(String naturalidade) {
        this.naturalidade = naturalidade;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public void setSqCand(String sqCand) {
        this.sqCand = sqCand;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public String getFonte() {
        return fonte;
    }

    public String getInstrucao() {
        return instrucao;
    }

    public String getLimiteGasto() {
        return limiteGasto;
    }

    public String getNacionalidade() {
        if ( nacionalidade == null || nacionalidade.equals("null") )
            nacionalidade = "Brasileira";
        return nacionalidade;
    }

    public String getNaturalidade() {
        if ( naturalidade == null || nacionalidade.equals("null") )
            naturalidade = "Brasileira";

        return naturalidade;
    }

    public String getSite() {
        return site;
    }

    public String getSqCand() {
        return sqCand;
    }

    public void setOcupacao(String ocupacao) {
        this.ocupacao = ocupacao;
    }

    public String getOcupacao() {
        return ocupacao;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEstado() {
        return estado;
    }

    public String getCargo() {
        return cargo;
    }


    public static class CandidatoRows {
        public final static  String NOME = "nome";
        public final static  String NOME_URNA = "nome_urna";
        public final static  String NUMERO = "numero";
        public final static  String FOTO = "foto";
        public final static  String RESULTADO = "RESULTADO";
        public final static  String PARTIDO = "partido";
        public final static  String DATA_NASCIMENTO = "data_nascimento";
        public final static  String INSTRUCAO = "instrucao";
        public final static  String OCUPACAO = "ocupacao";
        public final static  String SITE = "sites";
        public final static  String CARGO = "cargo";
        public final static  String ESTADO = "estado_eleicao";

    }
}
