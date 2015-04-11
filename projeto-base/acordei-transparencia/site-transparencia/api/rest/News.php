<?php
header('Content-type: application/json; charset=UTF-8');

class News {

  public static $nn = NULL;
  public static $m = NULL;
  public static $db = NULL;

  public function __construct() {
    self::init();
  }

  public static function toUpper($text){
    return strtoupper(strtr("".$text,"áéíóúâêôãõàèìòùç","ÁÉÍÓÚÂÊÔÃÕÀÈÌÒÙÇ"));
  }

  private static function init() {

    if ( !isset(self::$news) ){
      self::$m = new MongoClient();
      self::$db = self::$m->transparencia;
      self::$nn = self::$db->news;
    }
  }

  private function raw_json_encode($input) {

    return preg_replace_callback(
    '/\\\\u([0-9a-zA-Z]{4})/',
    function ($matches) {
      return mb_convert_encoding(pack('H*',$matches[1]),'UTF-8','UTF-16');
    },
    json_encode($input)
  );

}

public function getLast50News(){
  $ret = self::$nn->find();
  $cands = array();
  foreach($ret as $c){
      unset($c['_id']);
      $cands[] = $c;
  }
  return self::raw_json_encode($cands);
}



}


?>
