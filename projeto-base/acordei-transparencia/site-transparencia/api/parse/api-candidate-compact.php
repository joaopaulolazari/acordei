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
	$VIEW = null;
	if ( isset($_GET['view']) ) $VIEW = $_GET['view'];

	$data = $cache->read($UF."comp");
	if ( isset($data) ){
		if (strcmp($VIEW, "html") ==0 ) showHtml($data,$UF);
		if (strcmp($VIEW, "single") == 0) showSingle($couch,$DATABASE,$_GET["nome"],$UF);
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
		$candidate = $candidato["doc"];
		$compact["nome"] = $candidate["nome"];
		$compact["sgUF"] = $candidate["sgUF"];
		$compact["foto"] = $candidate["foto"];
		if ( isset($candidate["total"]) ) $compact["total_expense"] = number_format($candidate["total"], 2, ',', '.');
		else $compact["total_expense"] = 0;
		$candidatos_compact[] = $compact;
	}
	function sortByOrder($a, $b) {
    	return $a['total_expense'] - $b['total_expense'];
	}

	usort($candidatos_compact, 'sortByOrder');
	$candidatos_compact = array_reverse($candidatos_compact);
	$json = json_encode($candidatos_compact);
	$cache->write($UF."comp",$json);
	if (isset($VIEW) ) {
		if (strcmp($VIEW, "html") ==0 ) showHtml($json,$UF);
		if (strcmp($VIEW, "single") == 0) showSingle($couch,$DATABASE,$_GET["nome"],$UF);
	}
	else echo $json;
	
	function showSingle($couch,$DATABASE,$nome,$UF){
		
		$candidato = getCandidate($couch,$DATABASE,$nome,$UF);

		if (strcmp($candidato['nome'], $nome) == 0){

			echo "<div class='col-xs-6 '>";
			echo "<div class='item'>";
			echo "	<div class='col-xs-6 col-centered '>";
			echo "		<img src='".$candidato['foto'][0]."'/>&nbsp;";
			echo "		<h3>".$candidato['nome']."</h3><br/>";
			echo "		<h2> R$&nbsp;".number_format($candidato["total"], 2, ',', '.')."</h2><br/>";
			echo "	</div>";
			echo "</div>";
			echo "<div class=\"shadow\">&nbsp;</div>";
			echo "<div class='fb-like' data-show-faces='false' data-action='like' data-share='true'  data-href='http://transparencia.acordei.com.br/index.php?uf=".$UF."&view=single&nome=".$candidato['nome']."'></div>";
			echo "<div class='fb-comments' data-width='100%' data-href='http://transparencia.acordei.com.br/index.php?uf=".$UF."&view=single&nome=".$candidato['nome']."' data-numposts='5' data-colorscheme='light'></div>";
			echo "</div>";
			$lastType = "";
			$lastExpense = 0;
			foreach ($candidato['gastos'] as $gasto) {
				if ( strcmp($gasto['type'], $lastType) != 0 ){
					echo "	<div class='col-xs-6 col-centered expense' style='text-align:left!important;'>";
					$lastType =$gasto['type'];
					if ( $lastExpense != 0 ) {
						echo "<h2>R$&nbsp;".number_format($lastExpense, 2, ',', '.')."</h2>";
						echo "</div>";
						echo "<div class=\"shadow\">&nbsp;</div>";
					}
					echo "	<div class='col-xs-6 col-centered expense' style='text-align:left!important;'>";
					echo $gasto['type'];
					echo "</div>";
					$lastExpense = 0;
				}
				$lastExpense += $gasto['expend'];
			}
			echo "	<div class='col-xs-6 col-centered expense' style='text-align:left!important;'>";
			echo "<h2>R$&nbsp;".number_format($lastExpense, 2, ',', '.')."</h2>";
			echo "</div>";
			echo "<div class=\"shadow\">&nbsp;</div>";
			echo "	</div>";
			echo "</div>";
			echo "</div>";

			exit(1);
		}
	}
	function showHtml($candidates,$UF){
		if ( !isset($candidates)){
			echo "Nada";
		 	exit(1);
		}
		$indice = 1;
		$candidates = json_decode($candidates,true);
		foreach ($candidates as $candidato) {
			if ( !isset($candidato['foto'][0])) continue;
			echo "<div class='col-xs-6 col-centered ' style='cursor:pointer'";
			echo " onmousedown='down(\"".$indice."\")'";
			echo " onmouseup='up(\"".$indice."\")'";
			echo" onclick=\"window.location='http://transparencia.acordei.com.br/index.php?uf=".$UF."&view=single&nome=".$candidato['nome']."'\">";
			echo "<div class='item'>";
			echo "<img src='".$candidato['foto'][0]."'/>&nbsp;";
			echo "<h3>".$candidato['nome']."</h3><br/>";
			echo "<h2 > R$ ".$candidato['total_expense']."</h2><br/>";
			echo "</div>";
			echo "<div class=\"shadow\" id=\"".$indice."\">&nbsp;</div>";
			echo "<div class='col-xs-12 col-centered '>";
			echo "</div>";
			echo "</div>";
			$indice += 1;
			
		}
	}
?>

	