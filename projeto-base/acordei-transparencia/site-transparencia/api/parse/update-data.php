<?php
	  include './parse-insert-candidate.php';
		require_once('mongo.php');

?>
<?php

    $candidates =0;
    $candis = candidatos($collection);
    $xml = simplexml_load_file('./update.xml');
		$gasto_total = 0;
    foreach ($candis as $can) {
			$candida = findCandidato($xml,$can["nome"]);
			if ( isset($candida) ){
				updatePic($collection,$can,$candida);
				$candidates += 1;
			}else{
				echo "Removido => ".$can."\n";
				removeCan($collection,$can);
			}
    }

    function findCandidato($xml,$nome){
			  foreach ($xml->deputados as $candidato) {
					  foreach ($candidato as $c) {
							//var_dump($c);
                if ( strcmp(makeName($c->nomeParlamentar), makeName($nome)) == 0  ) {
                    return $c;
                }
            }
        }
				return null;
    }
    echo "Total de candidatos inseridos: ".$candidates;

?>
