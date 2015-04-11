<?php
require_once('./lib/nusoap.php');
require_once('mongo.php');

$proxyhost = isset($_POST['proxyhost']) ? $_POST['proxyhost'] : '';
$proxyport = isset($_POST['proxyport']) ? $_POST['proxyport'] : '';
$proxyusername = isset($_POST['proxyusername']) ? $_POST['proxyusername'] : '';
$proxypassword = isset($_POST['proxypassword']) ? $_POST['proxypassword'] : '';
$client = new soapclientNusoap('http://www.camara.gov.br/SitCamaraWS/SessoesReunioes.asmx?wsdl',true,
								$proxyhost, $proxyport, $proxyusername, $proxypassword);
$err = $client->getError();
if ($err) {
	echo 'Error:' . $err . '\n';
}
$idMatricula = "73483";

  $parlamentares = $collection->find();
  $sen =0;
  $tot =0;
  $tote =0;
  foreach($parlamentares as $parlamentar){
    if ( isset($parlamentar['senador']) ) {
      $sen +=1;
      continue;
    }

    $in = parseParlamentar($parlamentar,$client,$collection);
    if ( $in  ){
      $tot +=1;
    }else{
      $tote +=1;
    }
  }
  echo "Total senadores pulados: ".$sen."\n";
  echo "Total parlamentares buscados: ".$tot."\n";
  echo "Total parlamentares erro: ".$tote."\n";

  function parseParlamentar($candidato,$client,$collection){
    $nome = $candidato['nome'];
      $param = array('dataIni' => "01/02/2014",'dataFim'=>'31/12/2015','numMatriculaParlamentar'=>$candidato['nuCarteiraParlamentar']);
      $result = $client->call('ListarPresencasParlamentar', array('parameters' => $param), '', '', false, true);
    	if ($client->fault) {
    		echo $nome.' => Error:' . $client->getError() . '\n';
        return false;
    	} else {
    		// Check for errors
    			$result = $result["ListarPresencasParlamentarResult"]["parlamentar"]["diasDeSessoes2"]["dia"];
          $presenca = array();
    			$presenca["Presença"]=0;
          $presenca["Presença (~)"]=0;
			    $presenca["Ausência justificada"]=0;
			    $presenca["Ausência"]=0;
    			foreach ($result as $dia) {
    				$presenca[$dia["frequencianoDia"]]++;
    			}
          $tot_gastos = 0;
          if ( !isset($candidato['total_gasto']) && isset($candidato['gastos']) ){
            foreach($candidato['gastos'] as $gasto){
              $tot_gastos+= $gasto['valor'];
            }
          }else if ( isset($candidato['total_gasto']) ){
            $tot_gastos = $candidato['total_gasto'];
          }
          $documentupdate = $collection->update(
              array('_id'=> $candidato["_id"]),
              array('nome' => "".$candidato["nome"],
              'photo' => $candidato['photo'],
              'fone' => $candidato['fone'],
              'email' => $candidato['email'],
              'total_gasto' => $tot_gastos,
              'uf' => "".$candidato["uf"],
              'idCadastro' => "".$candidato["idCadastro"],
              'nuCarteiraParlamentar' => "".$candidato["nuCarteiraParlamentar"],
              'partido' => "".$candidato["partido"],
              'presencas' => $presenca,
              'gastos' => $candidato["gastos"])
          );
          if (isset($documentupdate["err"])){
						var_dump($documentupdate["err"]);
						return false;
					}
					return true;
      }
  }
?>
