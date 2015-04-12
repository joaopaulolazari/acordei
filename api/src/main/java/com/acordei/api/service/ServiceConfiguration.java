package com.acordei.api.service;

import org.springframework.stereotype.Component;

@Component
public class ServiceConfiguration {

    public String getUriProjetosDeLei() {
        return "http://www.camara.gov.br/SitCamaraWS/Proposicoes.asmx/ListarProposicoes?sigla=PL&numero=&ano=&datApresentacaoIni=&datApresentacaoFim=&parteNomeAutor=$nomeAutor&idTipoAutor=&siglaPartidoAutor=&siglaUFAutor=&generoAutor=&codEstado=&codOrgaoEstado=&emTramitacao=";
    }
}
