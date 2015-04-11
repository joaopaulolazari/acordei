<?php
// Check for empty fields
if(empty($_POST['name'])  		||
   empty($_POST['email']) 		||
   empty($_POST['message'])	||
   !filter_var($_POST['email'],FILTER_VALIDATE_EMAIL))
   {
	echo "No arguments Provided!";
	return false;
   }
	
$name = $_POST['name'];
$email_address = $_POST['email'];
$message = $_POST['message'];
	
// Create the email and send the message
$to = ''; // Add your email address inbetween the '' replacing yourname@yourdomain.com - This is where the form will send a message to.
$email_subject = "Contato do site de :  $name";
$email_body = "Você recebeu uma nova mensagem de seu site.\n\n"."Aqui estão os detalhes:\n\nNome: $name\n\nEmail: $email_address\n\nMensagem:\n$message";
$headers = "From: noreply@acordei.com.br";
mail("suporte@acordei.com.br",$email_subject,$email_body,$headers);
return true;			
?>