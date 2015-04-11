<?php
require_once './CouchSimple.php';

$couch = new CouchSimple($options);

try {
    $couch->send("GET", "/");
} catch (Exception $e) {
    echo "{'erro':'true','message':'Erro :('}";
    exit(1);
}

if (!isset($_GET['user']) && !isset($_GET['password']) && !isset($_GET['name'])) {
    echo "{'erro':'true','message':'Não esta disponível'}";
    exit(1);
}

if (strlen($_GET['user']) > 150){
    echo "{'erro':'true','message':'Não esta disponível'}";
    exit(1);
}
if (strlen($_GET['password']) > 8){
    echo "{'erro':'true','message':'Não esta disponível'}";
    exit(1);
}
    
$bool = checkIfExists($_GET['user'],$couch);
if ( $bool ) {
    echo "{'erro':'true','message':'Não esta disponível'}";
    exit(1);
}
$sSend = "{\"_id\": \"" . $_GET['user'] . "\",\"password\": \"" . $_GET['password'] . "\", \"name\": \"".$_GET['name'] ."\"}";
$resp = $couch->send("PUT", "/logins/" . $_GET['user'], $sSend);
echo "{'erro':'false','message':'Cadastrado com sucesso!'}";
function checkIfExists($user,$couchDb){
    $resp = $couchDb->send("GET", "/logins/$user");      
    $json = json_decode($resp, true);
    return isset($json['_id']);
}