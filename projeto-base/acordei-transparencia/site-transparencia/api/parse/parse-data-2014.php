<?php
include './parse-insert-candidate.php';
require_once('class.chunk.php');
require_once('mongo.php');
ini_set('memory_limit', '-1');
?>
<?php
$file = new Chunk('AnoAnterior.xml', array('path'=>'/var/www/html/acordei/acordei-transparencia/site-transparencia/data/'));

echo "\nstart reading..\n";
while ($xml = $file->read()) {
  echo "Enter to while..\n";
  $obj = simplexml_load_string($xml);
  $i=0;
  foreach ($obj->DESPESAS[0] as $despesa) {
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
      echo "\nNão achei o candidato ".$nome."\n Vou inserir...\n";
      echo "\ntotal:".$i."\n";
    }
  }
  $obj = null;
  echo "Total de candidatos inseridos ".$i." \n";

}
?>
