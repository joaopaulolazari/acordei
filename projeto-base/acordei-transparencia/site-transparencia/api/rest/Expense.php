<?php
header('Content-type: application/json; charset=UTF-8');

  class Expense {

    public static $gastos = NULL;
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
        self::$gastos = self::$db->gastos_politicos;
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

    public function getExpenseByNameDetail($candidate,$uf){
      $nome = self::toUpper($candidate);
      $user = array(
        'nome' => "".$nome,
        'uf' => $uf
      );
      $ret = self::$gastos->findOne($user);
      if ( self::existsCandidate($ret)) {
        unset($ret['_id']);
        $types = array();
        foreach ($ret['gastos'] as $gasto) {
          if (!in_array($gasto['tipo'],$types) ){
            $types[] = $gasto['tipo'];
          }
        }
        foreach ($ret['gastos'] as $gasto) {
          $gas = array(
            'valor'=>$gasto['valor'],
            'data'=>$gasto['data']
          );
          $types[$gasto['tipo']][] = $gas;
          $index = array_search($gasto['tipo'],$types);
          unset($types[$index]);
        }
        $ret['gastos'] = $types;
        return self::raw_json_encode($ret);
      }
      else return "{'message':'not-found'}";
    }
    private function existsCandidate($ret){
        return (isset($ret) && isset($ret['_id']));
    }
    public function getExpenseByName($candidate,$uf){
      $nome = self::toUpper($candidate);
      $user = array(
        'nome' => "".$nome,
        'uf' => $uf
      );
      $ret = self::$gastos->findOne($user);
      if ( self::existsCandidate($ret)) {
        unset($ret['_id']);
        $lastType = NULL;
        $lastExpense = 0;
        $ret['gastos_resumo'] = array();
        foreach ($ret['gastos'] as $gasto) {
          if ( strcmp($gasto['tipo'], $lastType) != 0 ){
            if ( $lastExpense != 0 ) {
              $ret['gastos_resumo'][] = array(
                'tipo'=>$lastType,
                'valor'=> $lastExpense
              );
              $lastExpense = 0;
            }
            $lastType = $gasto['tipo'];
          }
          $lastExpense += $gasto['valor'];
        }
        $ret['gastos_resumo'][] = array(
          'tipo'=>$lastType,
          'valor'=>$lastExpense
        );
        unset($ret['gastos']);
        return self::raw_json_encode($ret);
      }
      else return "{'message':'not-found'}";
    }
  }


?>
