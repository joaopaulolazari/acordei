<?php
// Check for empty fields
if(empty($_POST['name'])  		||
   empty($_POST['email']) 		||
  empty($_POST['phone']) 		||
  empty($_POST['partido']) 		||
   empty($_POST['message'])	||
   !filter_var($_POST['email'],FILTER_VALIDATE_EMAIL))
   {
	echo "No arguments Provided!";
	return false;
   }
	
$name = $_POST['name'];
$email_address = $_POST['email'];
$message = $_POST['message'];
$phone = $_POST['phone'];
$partido = $_POST['partido'];
	
// Create the email and send the message
$to = ''; // Add your email address inbetween the '' replacing yourname@yourdomain.com - This is where the form will send a message to.
$email_subject = "Cadastro de candidato :  $name";
$email_body = "Você recebeu uma nova cadastro de candidato em seu site.\n\n"."Aqui estão os detalhes:\n\nNome: $name\n\nPartido: $partido\n\nTelefone: $phone\n\nEmail: $email_address\n\nMensagem:\n$message";
$headers = "From: noreply@acordei.com.br";
mail("suporte@acordei.com.br",$email_subject,$email_body,$headers);
return true;			
?>