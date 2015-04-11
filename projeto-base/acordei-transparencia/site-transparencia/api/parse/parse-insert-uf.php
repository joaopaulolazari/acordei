<?php
 function updateCandidateToUf($couch,$DATABASE,$candidate,$uf){
 		if (in_array(json_decode("{\"nome\":\"".makeName($candidate)."\"}",true),$uf['candidates'])){
            echo "Alery added :".$candidate."\n";
            return;
        }
 		echo "############( ".$DATABASE . $uf['_id']." )############\n";
 		echo "# OLD : ".json_encode($uf,true)."#\n";
 		$uf['candidates'][] = json_decode("{\"nome\":\"".makeName($candidate)."\"}",true);
 		echo "# NOW : ".json_encode($uf,true)."#\n";
 		//var_dump($del);
 		echo "########################\n";
        $json = json_encode($uf,true);
        $resp = json_decode($couch->send("PUT", $DATABASE . $uf['_id'], $json),true);
        if ( isset($resp['error']) ) {
            echo "error to update candidate to uf: ".json_encode($uf,true)."\n";
            var_dump($resp);
        }
    }
    function insertCandidateToUf($couch,$DATABASE,$candidate,$uf){
    	$json = "{\"candidates\":[{\"nome\":\"".makeName($candidate)."\"}]}";
		$resp = json_decode($couch->send("PUT", $DATABASE . $uf, $json),true);
    	if ( isset($resp['error']) ) {
            echo "error to insert candidate to uf:".$json." \n";
            var_dump($resp);
        }
    }
    function getUF($couch,$DATABASE,$sgUF){
    	$resp = json_decode($couch->send("GET", $DATABASE . $sgUF),true);
    	if ( isset($resp['error']) ) {
    		return null;
    	}
		return $resp;
    }
?>