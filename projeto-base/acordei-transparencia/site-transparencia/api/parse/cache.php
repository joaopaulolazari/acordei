<?php

class Cache 
{
	function read($fileName){
		$fileName = '/var/www/html/acordei/acordei-transparencia/site-transparencia/data/cache/'.$fileName;
		if ( file_exists($fileName)  ){
			$handle = fopen($fileName, 'rb');
			$variable = fread($handle, filesize($fileName));
			fclose($handle);
			return $variable;
		}else{
			return null;
		}
	}
	function write($fileName, $variable){
		$fileName = '/var/www/html/acordei/acordei-transparencia/site-transparencia/data/cache/'.$fileName;
		$handle = fopen($fileName, "a");
		fwrite($handle, $variable);
		fclose($handle);
	}
}
?>
