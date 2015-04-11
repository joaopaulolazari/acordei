<?php
require_once('site-transparencia/api/parse/cache.php');
require_once('cl-text.php');
header('Content-Type: text/html; charset=utf-8');
?>
<!--
matricula = numero parlamentar
-->
<!doctype html>
<html>

<head>

  <title>Transparência Brasil - Acordei</title>

  <meta name="viewport" content="width=device-width, minimum-scale=1.0, initial-scale=1.0, user-scalable=yes">

  <script src="site-transparencia/components/platform/platform.js">
  </script>

  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <meta property="og:title" content="Controle os gastos dos nossos deputados parlamentares!" />
  <meta property="og:url" content=" http://transparencia.acordei.com.br/" />
  <meta property="og:image" content="http://www.acordei.com.br/img/logo.png"/>
  <meta property="og:description" content="Monitore seu deputado! Aqui você pode ver como a verba dos parlamentares esta sendo gasta!!" />
  <meta property="og:site_name" content="Acordei - Eleições 2014" />
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta name="author" content="Deivison Servat Sporteman">


  <link rel="import" href="site-transparencia/components/font-roboto/roboto.html">
  <link rel="import"
    href="site-transparencia/components/core-header-panel/core-header-panel.html">
  <link rel="import"
    href="site-transparencia/components/core-toolbar/core-toolbar.html">
  <link rel="import"
    href="site-transparencia/components/paper-tabs/paper-tabs.html">

  <!-- bootstrap -->
  <!-- Bootstrap Core CSS -->
        <link href="site-transparencia/css/bootstrap.min.css" rel="stylesheet">

        <!-- Custom CSS -->
        <link href="site-transparencia/css/agency.css" rel="stylesheet">

        <!-- Custom Fonts -->
        <link href="site-transparencia/font-awesome-4.1.0/css/font-awesome.min.css" rel="stylesheet" type="text/css">
        <link href="http://fonts.googleapis.com/css?family=Montserrat:400,700" rel="stylesheet" type="text/css">
        <link href='http://fonts.googleapis.com/css?family=Kaushan+Script' rel='stylesheet' type='text/css'>
        <link href='http://fonts.googleapis.com/css?family=Droid+Serif:400,700,400italic,700italic' rel='stylesheet' type='text/css'>
        <link href='http://fonts.googleapis.com/css?family=Roboto+Slab:400,100,300,700' rel='stylesheet' type='text/css'>
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>

        <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
            <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
            <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
        <![endif]-->
  <style>
   html,body {
    height: 100%;
    margin: 0;
    background-color: #E5E5E5;
    font-family: 'RobotoDraft', sans-serif;
  }
  .gasto-total{
   font-family: 'RobotoDraft', sans-serif;
   font-size: 8rem;
  }
.centered {
    float: none;
    margin-left: auto;
    margin-right: auto;
}
  .cards{
     display: block;
      position: relative;
      background-color: white;
      margin-left: 20px;
      font-size: 1.2rem;
      height: 180px;
      text-align: center;
      font-weight: 300;
      margin-bottom: 20px;
  }
  .cards * {
      color: blue;
  }

  .card {
    width: 80%;
    margin-left: auto;
    margin-right: auto;
    background-color: white;
    margin-top: 20px;
    padding: 20px;
    text-align: center;
  }
  .maring-top{
    margin-top: 20px;
  }
  .shadow {
    height: 6px;
    box-shadow: inset 0px 5px 6px -3px rgba(0, 0, 0, 0.4);
  }
  core-header-panel {
    height: 100%;
    overflow: auto;
    -webkit-overflow-scrolling: touch;
  }
  core-toolbar {
    background: #03a9f4;
    color: white;
  }
.vcenter {
    display: inline-block;
    vertical-align: middle;
    float: none;
}
  #tabs {
    width: 100%;
    margin: 0;
    -webkit-user-select: none;
    -moz-user-select: none;
    -ms-user-select: none;
    user-select: none;
    text-transform: uppercase;
  }

  @media (min-width: 481px) {
    #tabs {
      width: 100%;
    }

  }
.container{
    max-width:99%
}
  .navbar-default{
    background-color: transparent;
  }

  .row-centered {
    text-align:center;
}
.col-centered {
    display:inline-block;
    float:none;
    /* reset the text-align */
    text-align:left;
    /* inline-block space fix */
    margin-right:-4px;
    height: 350px!import;
}
.col-fixed {
    /* custom width */
    width:320px;
}
.col-min {
    /* custom min width */
    min-width:320px;
}
.col-max {
    /* custom max width */
    max-width:320px;
}

[class*="col-"] {
    padding-top:10px;
    padding-bottom:15px;
    text-align: center;
}
[class*="col-"]:before {
    display:block;position:relative;
    margin-bottom:8px;
    font-family:sans-serif;
    font-size:10px;
    letter-spacing:1px;
    background:#85CE30;
    text-align:left;
}
.item {
    width:100%;
    height:100%;
    background:#85CE30;
  /*border:1px solid #cecece;*/
    padding:16px 8px;

  /*background:-webkit-gradient(linear, left top, left bottom,color-stop(0%, #f4f4f4), color-stop(100%, #ededed));
  background:-moz-linear-gradient(top, #f4f4f4 0%, #ededed 100%);
  background:-ms-linear-gradient(top, #f4f4f4 0%, #ededed 100%);*/
}

/* content styles */
.item {
  display:table;
  background:#85CE30;
}
.item img{
  border-radius: 50%;
  width: 70px;
  margin: 10px;
}
.item h2 {
  color: white;
}
.content {
  display:table-cell;
  vertical-align:middle;
  text-align:center;
}
.content:before {
    content:"Content";
    font-family:sans-serif;
    font-size:12px;
    letter-spacing:1px;
    color:#747474;
}
.descritivo{
  font-size:14px;
}
/* centering styles for jsbin */
html,
body {
    width:100%;
    height:100%;
}
html {
    display:table;
}
body {
    display:table-cell;
    vertical-align:middle;
}
h1 {display: inline;}
h2 {display: inline;}

.expense{
  background:#fed136;
}

.footer {
  position: absolute;
  bottom: 0;
  width: 100%;
  /* Set the fixed height of the footer here */
  height: 60px;
  background-color: #03a9f4;
}
.footer div span{
  color: white;
}


/* Custom page CSS
-------------------------------------------------- */
/* Not required for template or sticky footer method. */

.fcontainer {
  width: auto;
  max-width: 680px;
  padding: 0 15px;
}
.fcontainer .text-muted {
  margin: 20px 0;
}
</style>
<script>
function down(nome){
      $("#"+nome).hide();
    }
    function up(nome){
      $("#"+nome).show();
    }
</script>
</head>

<body unresolved>
  <script>
  window.fbAsyncInit = function() {
    FB.init({
      appId      : '1514622105462200',
      xfbml      : true,
      version    : 'v2.1'
    });
  };

  (function(d, s, id){
     var js, fjs = d.getElementsByTagName(s)[0];
     if (d.getElementById(id)) {return;}
     js = d.createElement(s); js.id = id;
     js.src = "//connect.facebook.net/en_US/sdk.js";
     fjs.parentNode.insertBefore(js, fjs);
   }(document, 'script', 'facebook-jssdk'));
</script>
  <core-header-panel>
    <core-toolbar >
    <div class="navbar-default ">
          <a class="navbar-brand page-scroll" href="/index.php">Acordei</a>
      </div>
      <paper-tabs id="tabs" self-end class="maring-top">
        <paper-tab name="AC">AC</paper-tab>
        <paper-tab name="AL">AL</paper-tab>
        <paper-tab name="AP">AP</paper-tab>
        <paper-tab name="AM">AM</paper-tab>
        <paper-tab name="BA">BA</paper-tab>
        <paper-tab name="CE">CE</paper-tab>
        <paper-tab name="DF">DF</paper-tab>
        <paper-tab name="ES">ES</paper-tab>
        <paper-tab name="GO">GO</paper-tab>
        <paper-tab name="MA">MA</paper-tab>
        <paper-tab name="MT">MT</paper-tab>
        <paper-tab name="MS">MS</paper-tab>
        <paper-tab name="MG">MG</paper-tab>
        <paper-tab name="PA">PA</paper-tab>
        <paper-tab name="PB">PB</paper-tab>
        <paper-tab name="PR">PR</paper-tab>
        <paper-tab name="PE">PE</paper-tab>
        <paper-tab name="PI">PI</paper-tab>
        <paper-tab name="RJ">RJ</paper-tab>
        <paper-tab name="RN">RN</paper-tab>
        <paper-tab name="RS">RS</paper-tab>
        <paper-tab name="RO">RO</paper-tab>
        <paper-tab name="RR">RR</paper-tab>
        <paper-tab name="SC">SC</paper-tab>
        <paper-tab name="SP">SP</paper-tab>
        <paper-tab name="SE">SE</paper-tab>
        <paper-tab name="TO">TO</paper-tab>
    </paper-tabs>
  </core-toolbar>

  <div id="fb-root"></div>
    <div class="card">
      <?php if ( !isset( $_GET["view"])) {
        $cache = new Cache();
        $gasto_total = $cache->read("total"."comp");

        echo "<h1><p>No portal da transparência #acordei, você encontra o gasto de todos os deputados federais , parlamentares e senadores neste ano! Para ver a lista de parlamentares de um estado, basta clicar no estado no menu superior, já se quiser detalhar os gastos de um candidato basta apenas clicar nele :).";
        echo "  Em um futuro próximo, vamos mostrar também os gastos do executivo de cada estado.</p></h1>";
        echo "  <div class=\"shadow\">&nbsp;</div>";
        echo "  <h1>Já gastamos até o mês dezembro:<br/></h1><span class=\"gasto-total\"> R$ ".number_format($gasto_total, 2, ',', '.')." </span>";
        echo "<br>";
        echo "<span class='descritivo'>".clsTexto::valorPorExtenso($gasto_total, true, true)."</span>";
        echo "  <div class=\"shadow\">&nbsp;</div>";
        echo "<div class='fb-like' data-show-faces='false' data-action='like' data-share='true'  data-href='http://transparencia.acordei.com.br/index.php'></div>";
      }?>
    </div>

</core-header-panel>
  <script>
    var tabs = document.querySelector('paper-tabs');
    var list = document.querySelector('post-list');

    tabs.addEventListener('core-select', function() {
      $( ".card" ).html("");
      $.get("/site-transparencia/api/parse/api-candidate-compact_temp.php?uf="+tabs.selected+"&view=html",
        function( data ) {
          $( ".card" ).html("<div class='row row-centered'>"+data+"</div>" );
        });
    });
    function searchUnic(nome,uf){
      $.get("/site-transparencia/api/parse/api-candidate-compact_temp.php?uf="+uf+"&view=single&nome="+nome,
        function( data ) {
          $( ".card" ).html("<div class='row row-centered'>"+data+"</div>" );
        });
    }
<?php
  if ( isset( $_GET["view"]) && strcmp($_GET["view"],"single")==0){
    echo "searchUnic(\"".$_GET["nome"]."\",\"".$_GET["uf"]."\");";
  }
?>
</script>
<script>
            (function(i, s, o, g, r, a, m) {
                i['GoogleAnalyticsObject'] = r;
                i[r] = i[r] || function() {
                    (i[r].q = i[r].q || []).push(arguments)
                }, i[r].l = 1 * new Date();
                a = s.createElement(o),
                        m = s.getElementsByTagName(o)[0];
                a.async = 1;
                a.src = g;
                m.parentNode.insertBefore(a, m)
            })(window, document, 'script', '//www.google-analytics.com/analytics.js', 'ga');
            ga('create', 'UA-52981402-1', 'auto');
            ga('require', 'displayfeatures');
            ga('send', 'pageview');
        </script>
    <div class="footer">
       <div class="row">
                    <div class="col-md-4">
                        <span class="copyright">Copyright &copy; Acordei 2014 -
                        <a href="http://www.acordei.com.br/#contact">Contato</a></span>
                    </div>
                    <div class="col-md-4">
                        <ul class="list-inline social-buttons">
                            <li>
                            </li>
                            <li><a href="https://www.facebook.com/acordeiapp"><i class="fa fa-facebook"></i></a>
                            </li>
                            <li>&nbsp;
                            </li>
                        </ul>
                    </div>
                    <div class="col-md-4" >
                        <ul class="list-inline quicklinks">
                            <li>
                            </li>
                        </ul>
                    </div>
                </div>

    </div>

</body>
</html>
