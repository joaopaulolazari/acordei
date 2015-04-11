<?php

  $m = new MongoClient();
  $db = $m->transparencia;
  $collection = $db->gastos_politicos;
  $politicos = $db->politicos;


class Senador{

  public static  $senadores = NULL;

  public function __construct() {
    if (!isset(self::$senadores)) {
      self::init();
    }
  }

  public static function init() {
    echo "Call init\n";
    if (!isset(self::$senadores)) {
      echo "Find senadores...\n";
       $service_url = 'https://www.kimonolabs.com/api/28d5kkdo?apikey=10deb955005b151ee7f6d2d2c796cde6';
       $json = file_get_contents($service_url);
       self::$senadores = json_decode($json,true);
    }
    return self::$senadores;
  }

}

?>
