<?php

  $m = new MongoClient();
  $db = $m->transparencia_gastos;
  $gastos = $db->gastos_politicos;

  function toUpper($text){
    return strtoupper(strtr("".$text,"áéíóúâêôãõàèìòùç","ÁÉÍÓÚÂÊÔÃÕÀÈÌÒÙÇ"));
  }
?>
