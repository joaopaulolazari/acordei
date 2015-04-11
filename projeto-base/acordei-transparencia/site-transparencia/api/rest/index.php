<?php
header('Content-type: application/json; charset=UTF-8');

$API_VERSION = 'v1';

require 'flight/Flight.php';
require 'Expense.php';
require 'Candidate.php';
require 'News.php';
require 'auth/Auth.php';


Flight::register('auth', 'Auth');


Flight::route('/', function(){
    echo 'Acordei - transparencia Api v1.0! Com esta API é possível acompanhar os gatos públicos de nossos deputados e senadores, assim como suas faltas e projetos votados!. Quer ter acesso ? entre em contato: deivison.sporteman@acordei.com.br';
});

Flight::route('/api/'.$API_VERSION.'/gastos/@candidate/@uf', function($candidate,$uf){
    if ( !Flight::auth()->shouldAllow() ){
      echo Auth::deny();
      exit(1);
    }
    $expense = new Expense;
    echo $expense->getExpenseByName($candidate,$uf);
});
Flight::route('/api/'.$API_VERSION.'/gastos/@candidate/@uf/detalhe', function($candidate,$uf){
    if ( !Flight::auth()->shouldAllow() ){
      echo Auth::deny();
      exit(1);
    }
    $expense = new Expense;
    echo $expense->getExpenseByNameDetail($candidate,$uf);
});

Flight::route('/api/'.$API_VERSION.'/candidatos/eleitos', function(){
    if ( !Flight::auth()->shouldAllow() ){
      echo Auth::deny();
      exit(1);
    }
    $candidate = new Candidate;
    echo $candidate->getCandidates();
});

Flight::route('/api/'.$API_VERSION.'/candidatos/eleitos/cargo/@cargo', function($cargo){
  if ( !Flight::auth()->shouldAllow() ){
    echo Auth::deny();
    exit(1);
  }
  $candidate = new Candidate;
  echo $candidate->getCandidatesByCargo($cargo);
});

Flight::route('/api/'.$API_VERSION.'/candidatos/eleitos/@uf/cargo/@cargo', function($cargo){
  if ( !Flight::auth()->shouldAllow() ){
    echo Auth::deny();
    exit(1);
  }
  $candidate = new Candidate;
  echo $candidate->getCandidatesByCargo($cargo);
});

Flight::route('/api/'.$API_VERSION.'/candidatos/eleitos/@uf', function($uf){
    if ( !Flight::auth()->shouldAllow() ){
      echo Auth::deny();
      exit(1);
    }
    $candidate = new Candidate;
    echo $candidate->getCandidatesByUf($uf);
});


/*
 Android API
*/
Flight::route('/api/'.$API_VERSION.'/news', function(){
  if ( !Flight::auth()->shouldAllow() ) echo Auth::deny();

  $news = new News;
  echo $news->getLast50News();
});

Flight::route('/api/'.$API_VERSION.'/eleito/@uf/@cargo/@nome', function($uf,$cargo,$nome){
  //if ( !Flight::auth()->shouldAllow() ) echo Auth::deny();

  $candidate = new Candidate;
  echo  $candidate->getCandidatesByCargoAndUfAndName($uf,$cargo,$nome);
  #echo $news->getLast50News();
});

Flight::start();
?>
