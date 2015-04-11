<?php
header('Content-type: application/json; charset=UTF-8');

  class Candidate {

    public static $candidatos = NULL;
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
        self::$candidatos = self::$db->politicos;
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

    private function existsCandidate($ret){
        return (isset($ret) && isset($ret['_id']));
    }

    public function getCandidates(){
      $ret = self::$candidatos->find();
      $cands = array();
      foreach($ret as $c){
          if ( self::existsCandidate($c)) {
            unset($c['_id']);
            $cands[] = $c;
          }
      }
      return self::raw_json_encode($cands);
    }

    public function getCandidatesByUf($uf){
      $filter = array('eleicao.estado' => $uf);
      $ret = self::$candidatos->find($filter);
      $cands = array();
      foreach($ret as $c){
          if ( self::existsCandidate($c)) {
            unset($c['_id']);
            $cands[] = $c;
          }
      }
      return self::raw_json_encode($cands);
    }



    public function getCandidatesByCargoAndUfAndName($uf,$cargo,$name){
      $filter = array('eleicao.cargo' => $cargo,'naturalidade.siglaUF' => $uf,'nome' => $name);
      $ret = self::$candidatos->findOne($filter);

      return self::raw_json_encode($ret);
    }

    public function getCandidatesByCargoAndUf($cargo,$uf){
      $filter = array('eleicao.cargo' => $cargo,'eleicao.estado' => $uf);
      $ret = self::$candidatos->find($filter);
      $cands = array();
      foreach($ret as $c){
        if ( self::existsCandidate($c)) {
          unset($c['_id']);
          $cands[] = $c;
        }
      }
      return self::raw_json_encode($cands);
    }

    public function getCandidatesByCargo($cargo){
      $filter = array('eleicao.cargo' => $cargo);
      $ret = self::$candidatos->find($filter);
      $cands = array();
      foreach($ret as $c){
        if ( self::existsCandidate($c)) {
          unset($c['_id']);
          $cands[] = $c;
        }
      }
      return self::raw_json_encode($cands);
    }
  }


?>
