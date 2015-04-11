<?php

  class Auth {

    public static $applications = NULL;
    public static $m = NULL;
    public static $db = NULL;

    public function __construct() {
        self::init();
    }

    public static function toUpper($text){
      return strtoupper(strtr("".$text,"áéíóúâêôãõàèìòùç","ÁÉÍÓÚÂÊÔÃÕÀÈÌÒÙÇ"));
    }

    private static function init() {
      if ( !isset(self::$gastos) ){
        self::$m = new MongoClient();
        self::$db = self::$m->transparencia;
        self::$applications = self::$db->applications;
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

  public function parseRequestHeaders() {
      $headers = array();
      foreach($_SERVER as $key => $value) {
          $header = str_replace(' ', '-', ucwords(str_replace('_', ' ', strtolower(substr($key, 5)))));
          $headers[$header] = $value;
      }
      return $headers;
  }
    public function shouldAllow(){
      $headers = self::parseRequestHeaders();
      if ( !isset($headers['Authorization']) ) return false;
      $key = $headers['Authorization'];
      $user = array(
        'secret_key' => $key
      );
      $ret = self::$applications->findOne($user);
      if ( isset($ret["_id"]) ) return true;
      return false;
    }
    public static function deny(){
      header ('HTTP/1.1 403 Forbidden');
      exit(1);
    }
  }


?>
