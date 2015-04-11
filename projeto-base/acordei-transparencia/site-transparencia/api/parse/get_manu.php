<?php
require_once './CouchSimple.php';
	require_once('./cache.php');
    include './parse-insert-candidate.php';
    include './parse-insert-uf.php';

	$DATABASE = "/candidate/";
    $DATABASE_UF = "/candidates_uf/";

$couch = new CouchSimple($options);
  try {
      $resp = $couch->send("GET", "/");
      $connect = 1;
  } catch (Exception $e) {
      echo "error";
      exit(1);
  }
  $cache = new Cache();
  echo "Getting : ".$DATABASE_UF . "_all_docs\n\n###############\n";
  $ufs = json_decode($couch->send("GET", $DATABASE . "_all_docs?include_docs=false"), true);
  $gasto_total = 0;
  foreach ($ufs["rows"] as $uf) {
  	$u = getCandidate($couch,$DATABASE,$uf["id"],"");
  	$gasto_total += $u["total"];
  }
    
    $cache->write("total"."comp",$gasto_total);
    echo "Gasto total :".$gasto_total;
?>