<?php

require_once './CouchSimple.php';

$couch = new CouchSimple($options);
$connect = 0;

try {
    $resp = $couch->send("GET", "/");
    $connect = 1;
} catch (Exception $e) {
    echo "error";
    exit(1);
}
try {
    $limit = 100;
    $start = 0;
    if ( isset($_GET['start']) ){
        $start = $_GET['start'];
    }
    
    $resp = $couch->send("GET", "/wall/_all_docs?limit=100&descending=true");
    $resp = json_decode($resp, true);
    $item = $resp["rows"];

    echo "[";
    foreach ($item as $item) {
        $item = $item["key"];
        $resp = $couch->send("GET", ("/wall/" . $item));
        echo $resp . ",";
    }
    echo "{}]";
} catch (Exception $e) {
    echo "error";
    exit(1);
}
