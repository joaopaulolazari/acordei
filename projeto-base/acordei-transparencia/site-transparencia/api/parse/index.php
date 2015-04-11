
<?php
require_once './CouchSimple.php';
//Connection parameters to connect to your Cloudant service deployment.


// Instantiate the CouchSimple class you use to make your requests
$couch = new CouchSimple($options);
$connect = 0;

// See if we can make a connection
try {
    $resp = $couch->send("GET", "/");
    $connect = 1;
} catch (Exception $e) {
    echo "error";
    exit(1);
}

$user_name = $_GET['user_name'];
$user_mail = $_GET['user_mail'];
$user_message = $_GET['user_message'];
if (strlen($user_message) > 150)
    $user_message = substr($user_message, 0, 150);

$id = 0;
if ($_GET['a'] == "u") {
    $id = time();
    $sSend = "{\"_id\": \"" . $id . "\",\"name\": \"" . $user_name . "\",\"mail\":\"" . $user_mail . "\",\"message\":\"" . $user_message . "\"}";
    $resp = $couch->send("PUT", "/wall/" . $id, $sSend);
} else {
    $id = $_GET['id'];
    $sSend = json_decode($couch->send("GET", "/wall/" . $id), true);
    $sSend['message'] = $user_message;
    if ( isset($sSend["total"]) )
        $sSend['total'] = $sSend["total"]+1;
    else
        $sSend['total'] = 0;
    $resp = $couch->send("PUT", "/wall/" . $id, json_encode($sSend));
    var_dump($resp);
    echo "<br/>";
}
global $options, $couch;

