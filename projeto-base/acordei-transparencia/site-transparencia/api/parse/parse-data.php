<?php
	include './parse-insert-candidate.php';
	require_once('mongo.php');
?>
<?php


	//2015
	$xml = simplexml_load_file('../../data/AnoAtual.xml');
	$i=0;
	foreach ($xml->DESPESAS[0] as $despesa) {
		$nome = $despesa->txNomeParlamentar;
		$uf = $despesa->sgUF;
		$candidate = candidato($collection,$despesa);
		if ( $nome == "EDMAR MOREIRA" ){
				echo "Ta ai o Edmar...";
		}
		if ( isset($candidate) ){
				echo "achei o candidato ".$nome."\n";
				update($collection,$candidate,$despesa);
		}else{
			$i += 1;
			insert($collection,$despesa);
			echo "\ntotal:".$i."\n";
			echo "\nNão achei o candidato ".$nome."\n Vou inserir...\n";
		}
	}
	$xml = null;
	echo "Total de candidatos inseridos ".$i." \n";

?>
