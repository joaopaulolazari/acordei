<?php
	require_once './CouchSimple.php';
	require_once('./cache.php');
    include './parse-insert-candidate.php';
    include './parse-insert-uf.php';

	$DATABASE = "/candidate/";
    $DATABASE_UF = "/candidates_uf/";

	$couch = new CouchSimple($options);
?>
<?php
	$cache = new Cache();
	$connect = 0;

	try {
	    $resp = $couch->send("GET", "/");
	    $connect = 1;
	} catch (Exception $e) {
	    echo "error";
	    exit(1);
	}

	if ( !isset($_GET['uf']) ) {
		exit(1);
	}
	$UF = $_GET['uf'];
	$data = $cache->read($UF);
	if ( isset($data) ){
		echo $data;
		exit(1);
	}
	$candidatosByUf = json_decode($couch->send("GET", $DATABASE_UF . $UF), true);
	$filterBy = "%5B%22-1%22";
	
	$candidatos_compact = (array) null;
	foreach ($candidatosByUf["candidates"] as $candidato) {
		$filterBy = $filterBy."%2C%22".makeId($candidato["nome"])."%22";
	}
	$filterBy = $filterBy."%5D";
	$candidatos = json_decode($couch->send("GET", $DATABASE . "_all_docs?keys=".$filterBy."&include_docs=true"), true);
	
	foreach ($candidatos["rows"] as $candidato) {
		if ( isset($candidato["error"]) ) continue;
		$candidatos_compact[] = $candidato["doc"];
	}
	$json = json_encode($candidatos_compact);
	$cache->write($UF,$json);
	echo $json;
	
?>

	