<?php
	require_once('./cache.php');
	require_once('mongo.php');
  include './parse-insert-candidate.php';
?>
<?php
	$cache = new Cache();
	if ( !isset($_GET['uf']) ) {
		exit(1);
	}

	$UF = $_GET['uf'];
	$user = array(
		'uf' => $UF
	);
	$candidatos =  $collection->find($user);

	$VIEW = null;
	if ( isset($_GET['view']) ) $VIEW = $_GET['view'];

	$data = $cache->read($UF."comp");
	if ( isset($data) ){
		if (strcmp($VIEW, "html") ==0 ) showHtml($data,$UF);
		if (strcmp($VIEW, "single") == 0) showSingle($cache,$collection,$_GET["nome"],$UF);
		exit(1);
	}

	$candidatos_compact = (array) null;

	foreach ($candidatos as $candidato) {
		//if ( isset($candidato["error"]) || !isset($candidato["total"]) ) continue;
		$compact["nome"] = $candidato["nome"];
		$compact["sgUF"] = $candidato["uf"];
		if ( isset($candidato["photo"]) )
			$compact["foto"] = $candidato["photo"];
		if ( isset($candidato["total"]) )
			$compact["total"] = number_format($candidato["total"], 2, ',', '.');
		else $compact["total"] =  0;
		$candidatos_compact[] = $compact;
	}
	function sortByOrder($a, $b) {
    	return $a['total'] - $b['total'];
	}

	usort($candidatos_compact, 'sortByOrder');
	$candidatos_compact = array_reverse($candidatos_compact);
	$json = json_encode($candidatos_compact,true);
	$cache->write($UF."comp",$json);
	if (isset($VIEW) ) {
		if (strcmp($VIEW, "html") ==0 ) showHtml($json,$UF);
		if (strcmp($VIEW, "single") == 0) showSingle($cache,$collection,$_GET["nome"],$UF);
	}
	else echo $json;

	function showSingle($cache,$candidatos,$nome,$UF){
		$candidato = null;
		$data = $cache->read($nome.$UF.".json");
		if ( isset($data) ) $candidato = json_decode($data,true);
		else {
				$candidato = candidatoByUf($candidatos,$nome,$UF);
				$cache->write($nome.$UF.".json",json_encode($candidato,true));
		}

		if (strcmp($candidato['nome'], $nome) == 0){

			echo "<div class='col-xs-6 '>";
			echo "<div class='item'>";
			echo "	<div class='col-xs-6 col-centered '>";
			echo "		<img src='".$candidato['photo']."'/>&nbsp;";
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

				if ( strcmp($gasto['tipo'], $lastType) != 0 ){
					echo "	<div class='col-xs-6 col-centered expense' style='text-align:left!important;'>";
					$lastType =$gasto['tipo'];
					if ( $lastExpense != 0 ) {
						echo "<h2>R$&nbsp;".number_format($lastExpense, 2, ',', '.')."</h2>";
						echo "</div>";
						echo "<div class=\"shadow\">&nbsp;</div>";
					}
					echo "	<div class='col-xs-6 col-centered expense' style='text-align:left!important;'>";
					echo $gasto['tipo'];
					echo "</div>";
					$lastExpense = 0;
				}
				$lastExpense += $gasto['valor'];
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
		var_dump($candidates);
		foreach ($candidates as $candidato) {
			if ( !isset($candidato['foto'])) continue;

			echo "<a href=\"http://transparencia.acordei.com.br/index.php?uf=".$UF."&view=single&nome=".$candidato['nome']."\">";
			echo "<div class='col-xs-6 col-centered ' style='cursor:pointer'";
			echo " onmousedown='down(\"".$indice."\")'";
			echo " onmouseup='up(\"".$indice."\")'>";
			echo "<div class='item'>";
			echo "<img src='".$candidato['foto']."'/>&nbsp;";
			echo "<h3>".$candidato['nome']."</h3><br/>";
			echo "<h2 > R$ ".$candidato['total']."</h2><br/>";
			echo "</div>";
			echo "<div class=\"shadow\" id=\"".$indice."\">&nbsp;</div>";
			echo "<div class='col-xs-12 col-centered '>";
			echo "</div>";
			echo "</div>";
			echo "</a>";
			$indice += 1;

		}
	}
?>
