<?php
  function makeName($nome){
    return $nome;
  }

  function candidato($collection,$despesa){
      $user = array(
        'nome' => "".makeName($despesa->txNomeParlamentar),
        'uf' => "".$despesa->sgUF
      );
      return $collection->findOne($user);
  }

function candidatoByUf($collection,$nome,$uf){
    $user = array(
      'nome' => $nome,
      'uf' => $uf
    );
    return $collection->findOne($user);
}

  function candidatos($collection){
      return $collection->find();
  }

function removeCan($collection,$candidato){
  $documentupdate = $collection->remove(
      array('_id'=> $candidato["_id"])
    );
    echo "\n\ncandidato ".$candidato["nome"]." removido ";
}

function updatePic($collection,$candidato,$cand){
  $tot_gastos = 0;
  foreach ($candidato["gastos"] as $g) {
    $tot_gastos += $g["valor"];
  }
  $documentupdate = $collection->update(
      array('_id'=> $candidato["_id"]),
      array('nome' => "".$candidato["nome"],
      'nome_urna' => "".$candidato["nome_urna"],
      'photo' => "".$cand->urlFoto,
      'fone' => "".$cand->fone,
      'email' => "".$cand->email,
      'total' => (float) $tot_gastos,
      'total_2014' => $candidato['total_2014'],
      'uf' => "".$candidato["uf"],
      'idCadastro' => "".$candidato["idCadastro"],
      'nuCarteiraParlamentar' => "".$candidato["nuCarteiraParlamentar"],
      'partido' => "".$candidato["partido"],
      'gastos' => $candidato["gastos"],
      'gastos_2014' => $candidato["gastos_2014"],
      )
  );
  echo "\n\ncandidato ".$candidato["nome"]." atualizado ";
  if ( isset($documentupdate["err"]) ) echo " com erro\n\n";
  else echo " com sucesso!!\n\n ";
}

  function update($collection,$candidato,$despesa){
    $gasto = array(
                  'tipo' => "".$despesa->txtDescricao,
                  'data' => "".$despesa->datEmissao,
                  'valor' => (float) $despesa->vlrLiquido
    );
    $candidato["gastos"][] = $gasto;
    $documentupdate = $collection->update(
        array('_id'=> $candidato["_id"]),
        array('nome' => "".makeName($despesa->txNomeParlamentar),
        'uf' => "".$despesa->sgUF,
        'idCadastro' => "".$despesa->ideCadastro,
        'nuCarteiraParlamentar' => "".$despesa->nuCarteiraParlamentar,
        'partido' => "".$despesa->sgPartido,
        'gastos' => $candidato["gastos"])
    );
    echo "\n\ncandidato ".$candidato["_id"]." atualizado ";
    if ( isset($documentupdate["err"]) ) echo " com erro\n\n";
    else echo " com sucesso!!\n\n ";
  }

  function insert($collection,$despesa){
    $gasto = array(
                  'tipo' => "".$despesa->txtDescricao,
                  'data' => "".$despesa->datEmissao,
                  'valor' => (float) $despesa->vlrLiquido
    );
    $user = array(
      'nome' => "".makeName($despesa->txNomeParlamentar),
      'uf' => "".$despesa->sgUF,
      'idCadastro' => "".$despesa->ideCadastro,
      'nuCarteiraParlamentar' => "".$despesa->nuCarteiraParlamentar,
      'partido' => "".$despesa->sgPartido,
      'gastos' => array($gasto)
    );
    $documentupdate = $collection->save($user);
    echo "Candidato salvo ";
    if ( isset($documentupdate["err"]) ) echo " com erro\n\n";
    else echo " com sucesso!!\n\n ";
  }
?>
