<?php
require_once './CouchSimple.php';

$couch = new CouchSimple($options);

try {
    $couch->send("GET", "/");
} catch (Exception $e) {
    echo "{'erro':'true','message':'Erro :('}";
    exit(1);
}

if (!isset($_GET['user']) && !isset($_GET['password']) ) {
    echo "{'erro':'true','message':'Você errou seus dados, ou não esta cadastrado'}";
    exit(1);
}
if (!checkIfExists($_GET['user'],$couch)){
    echo "{'erro':'true','message':'Você errou seus dados, ou não esta cadastrado'}";
    exit(1);   
}

$resp = $couch->send("GET", "/logins/".$_GET['user']);      

$json = json_decode($resp, true);

$pass = $json['password'];
if (strcmp($pass,$_GET['password']) !== 0){
    echo "{'erro':'true','message':'Você errou seus dados, ou não esta cadastrado'}";
    exit(1);
}
echo "{'erro':'false','message': 'Bem vindo!'}";
exit(1);

function checkIfExists($user,$couchDb){
    $resp = $couchDb->send("GET", "/logins/$user");      
    $json = json_decode($resp, true);
    return isset($json['_id']);
}