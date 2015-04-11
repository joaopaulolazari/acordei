<?php

require 'rest_client.php';
$c = new RestClient();

class MyDB extends SQLite3
   {
      function __construct()
      {
         $this->open('../../data/eleicoes.db');
      }
   }
   $db = new MyDB();
   if(!$db){
      echo $db->lastErrorMsg();
   } else {
      echo "Opened database successfully\n";
   }

   $sql =<<<EOF
      SELECT * from candidato;
EOF;

  $m = new MongoClient();
  $dbm = $m->transparencia;
  $politicos = $dbm->politicos;

   $url = "/{uf}/candidato/{sq}";
   $ret = $db->query($sql);
   $list = array();
   while($row = $ret->fetchArray(SQLITE3_ASSOC) ){
     $list[]= $row;
   }
   $db->close();
   echo "Database load done!\n";
   $ii = 0;

   foreach($list as $row){
     $uf = $row['ESTADO'];
     $nome = $row['NOME'];
     $sq = $row['SQ_CAND'];
     $foto = $row['FOTO'];
     $cargo = $row['CARGO'];
     $result = getFromTSE($uf,$sq,$nome);
     if ( isset($result)){



        $candidato = $result->candidato;
        if ( strpos($candidato->situacaoPosPleito->descricao,'Eleito') !== false
        || strpos($candidato->situacaoPosPleito->descricao,'Suplente') !== false
        || strpos($candidato->situacaoPosPleito->descricao,'turno') !== false ) {

        $ii+=1;
         $user = array(
           'foto'=> "".$foto,
           'nome_urna' => "".$candidato->nomeParaUrna,
           'nome' => "".$candidato->nomeCompleto,
           'numero' => "".$candidato->numero,
           'sites' => "".$candidato->paginaWeb,
           'partido' => "".$candidato->partido->sigla,
           'sexo' => "".$candidato->sexo,
           'data_nascimento' => "".$candidato->dataDeNascimento,
           'naturalidade' => $candidato->naturalidade,
           'instrucao' => "".$candidato->grauDeInstrucao,
           'ocupacao' => "".$candidato->ocupacao->descricao,
           'bens' => $candidato->bens,
           'total_declarado' => (float) $candidato->somaDeValores,
           'eleicoes_anteriores' => $candidato->listaEleicaoAnterior,
           'eleicao' => array(
             'cargo' => $cargo,
             'resultado' => $candidato->situacaoPosPleito->descricao,
             'estado' => $uf
           )

         );
         $documentupdate = $politicos->save($user);

         if ( isset($documentupdate["err"]) ) echo $nome." com erro\n\n";
         else echo "saved [".$ii."]\n";

       }

     }
   }
   echo "Operation done successfully [ ".$ii." ]\n";


  function getFromTSE($uf,$sq,$nome){
    $api = new RestClient(array(
    'base_url' => "http://inter03.tse.jus.br/divulga-cand-2014-rest/mob/3/eleicao/2014/UF/",
    'format' => "json"
    ));
    $result = $api->get($uf."/candidato/".$sq);
    if($result->info->http_code == 200)
        return $result->decode_response();
    else
      echo "Erro to get ".$nome."\n";
    return NULL;
  }
?>
