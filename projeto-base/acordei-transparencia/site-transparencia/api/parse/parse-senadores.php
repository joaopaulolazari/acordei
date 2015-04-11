
<?php
require_once('mongo.php');

function getFromRow($row,$i){
  if ( isset($row[$i]) ){
    return str_replace("\r\n",'',utf8_encode(str_replace('"','',$row[$i])));
  }else{
    var_dump($row);
  }
}
function formatNumber($number){
  $n1 = str_replace('.','',$number);
  return (float) str_replace(',','.',$n1);
}
function candidato($collection,$nome,$uf){
    $user = array(
      'nome' => $nome,
      'uf' => $uf
    );
    return $collection->findOne($user);
}
function update($collection,$candidato,$row,$senador){
  $id = preg_replace('/\D/', '', $senador['nome']['href']);
  $foto = "http://www.senado.leg.br/senadores/img/fotos/bemv".$id.".jpg";

  $gasto = array(
              'tipo' => getFromRow($row,3),
              'data' => getFromRow($row,7),
              'valor' => formatNumber(getFromRow($row,9))
  );
  $candidato["gastos"][] = $gasto;
  $tot_gastos = 0;
  foreach( $candidato["gastos"] as $val){
    $tot_gastos += $val["valor"];
  }
  $documentupdate = $collection->update(
      array('_id'=> $candidato["_id"]),
      array('nome' => "".$senador['nome']['text'],
      'uf' => "".str_replace('   ','',$senador['uf']['text']),
      'photo' => "".$foto,
      'total_gasto' => (float) $tot_gastos,
      'senador' => 'true',
      'idCadastro' => "".$id,
      'partido' => "".$senador['partido']['text'],
      'telefone' => "".$senador['telefone'],
      'email' => "".$senador['email']['href'],
      'gastos' => $candidato["gastos"])
  );
  echo "\n\ncandidato ".$candidato["_id"]." atualizado ";
  if ( isset($documentupdate["err"]) ) echo " com erro\n\n";
  else echo " com sucesso!!\n\n ";
}

function findIdByName($nome){
  $senadores = Senador::init();
  $ss = $senadores["results"]["collection1"];
    foreach ($ss as $senador){
      $senador["nome"]["text"] = strtoupper(strtr("".$senador["nome"]["text"],"áéíóúâêôãõàèìòùç","ÁÉÍÓÚÂÊÔÃÕÀÈÌÒÙÇ"));
      //echo "NOT ".$senador["nome"]["text"]." =".strtoupper("".$nome)." | => ".strcmp($senador["nome"]["text"],strtoupper("".$nome))."\n";
      if ( strcmp($senador["nome"]["text"],strtoupper("".$nome)) == 0 ){
        return $senador;
      }
    }
    echo "NOT Found! ".$nome."\n";
    return null;
}


function insert($collection,$row,$senador){


  $gasto = array(
                'tipo' => getFromRow($row,3),
                'data' => getFromRow($row,7),
                'valor' => formatNumber(getFromRow($row,9))
  );
  $id = preg_replace('/\D/', '', $senador['nome']['href']);
  $foto = "http://www.senado.leg.br/senadores/img/fotos/bemv".$id.".jpg";
  $user = array(
    'nome' => "".$senador['nome']['text'],
    'uf' => "".str_replace('   ','',$senador['uf']['text']),
    'photo' => "".$foto,
    'senador' => 'true',
    'idCadastro' => "".$id,
    'partido' => "".$senador['partido']['text'],
    'telefone' => "".$senador['telefone'],
    'email' => "".$senador['email']['href'],
    'gastos' => array($gasto)
  );
  $documentupdate = $collection->save($user);
  echo "Candidato salvo ";
  if ( isset($documentupdate["err"]) ) echo " com erro\n\n";
  else echo " com sucesso!!\n\n ";
}

  $file = new SplFileObject("../../data/2015.csv");
  $i = 0;
  $d = array();
  while (!$file->eof()) {
      $row = explode(";",$file->fgets());

      $senador = findIdByName(getFromRow($row,2));
      if ( !isset($senador)) {
        echo "Não esta mais no poder : ".getFromRow($row,2)."\n";
        continue;
      }
      $candidate = candidato($collection,getFromRow($row,2),str_replace('   ','',$senador['uf']['text']));
      if ( isset($candidate) ){
          update($collection,$candidate,$row,$senador);
      }else{
        $i += 1;
        insert($collection,$row,$senador);
        echo "\ntotal:".$i."\n";
        echo "\nNão achei o candidato ".getFromRow($row,2)."\n Vou inserir...\n";
      }
      /*  $ano = $row[0];
      $mes = $row[1];
      $senador = $row[2];
      $tipodespesa = $row[3];
      $cpfcnpj = $row[4];
      $fornecedor = $row[5];
      $documento = $row[6];
      $data = $row[7];
      $detalhe = $row[8];
      $valor = $row[9];*/

  }
  echo "FIM!";
  $file = null;
?>
