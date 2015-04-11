<?php
    include './parse-insert-candidate.php';
    require_once('./cache.php');
    require_once('mongo.php');

    $cache = new Cache();

    $candidates =0;
    $candis = candidatos($collection);
    $total = 0.0;
    $total_2014 = 0.0;
    foreach ($candis as $can) {
      if ( isset($can["total"]) ){
        $total += $can["total"];
      }
      if ( isset($can["total_2014"]) ){
        $total_2014 += $can["total_2014"];
      }
    }
    $gasto_total = $total+$total_2014;
  $cache->write("total"."comp",$gasto_total);
  echo "gasto total_2015 :".$total."\n";
  echo "gasto total_2014 :".$total_2014."\n";
  echo "gasto total :".$gasto_total."\n";

?>
