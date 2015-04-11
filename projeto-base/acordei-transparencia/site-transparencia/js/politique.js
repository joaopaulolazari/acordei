function edit() {
    $("#row_text").fadeOut("slow", function() {
        $("#row_text").hide();
        $("#row_user").show();
        $("textarea#proposta").val($("#proposta_label").html().replace("<h1> <b>Proposta</b></h1><br/>", ""));
        $("textarea#sobre").val($("#sobre_label").html().replace("<h1> <b>Sobre</b></h1><br/>", ""));
    });
}
function hh() {
    $("#row_text").hide();
    $("#row_user").show()
}
function ss() {
    $("#row_text").show();
    $("#row_user").hide()
}
function save() {
    var proposta = $("textarea#proposta").val();
    var sobre = $("textarea#sobre").val();
    $.ajax({
        url: "/portal-politico/php/save.php",
        type: "POST",
        data: {
            proposta: proposta,
            sobre: sobre
        },
        cache: false,
        success: function() {
            $("#row_user").fadeOut("slow", function() {
                $("#row_user").hide();
                $("#row_text").show();
                $("#proposta_label").html(proposta);
                $("#sobre_label").html(sobre);
            });
        },
        error: function(e) {
            // Fail message
            $('#success').html("<div class='alert alert-danger'>");
            $('#success > .alert-danger').html("<button type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;")
                    .append("</button>");
            $('#success > .alert-danger').append("<strong>Lamento " + firstName + ", parece que ocorreu um problema. Tente novamente mais tarde!");
            $('#success > .alert-danger').append('</div>');
            //clear all fields
            $('#loginForm').trigger("reset");
        }
    });
}
function logout() {
    $.ajax({
        url: "/portal-politico/logout.php",
        type: "POST",
        cache: false,
        success: function() {
            window.location.replace("http://acordei.com.br");
        },
        error: function(e) {
            window.location.replace("http://acordei.com.br");
        }
    });
}
function removeMessage(li) {
    if (confirm("Deseja remover esta mensagem?")) {
        $(li).parent().fadeOut("slow", function() {

            var id = $(li).attr("id");
            $(li).remove();

            $.ajax({
                url: "/portal-politico/php/remove_message.php",
                type: "POST",
                data: {
                    id: id
                },
                cache: false,
                success: function() {
                },
                error: function(e) {
                }
            });

        });
    }
}
function success() {
    $(".intro-text").html("");
    $('#success').html("");
    loadAbout();
    loadAboutMe();
    loadMessage();
    loadEstatisticas();
    $("#nav-bar").show();
}
function loadEstatisticas() {
    $.ajax({
        url: "/portal-politico/estatisticas.php",
        type: "POST",
        cache: false,
        success: function(data) {
            $("#data").html(data);
        },
        error: function(e) {
            // Fail message
        }
    });
}
function loadMessage() {
    $.ajax({
        url: "/portal-politico/messages.php",
        type: "POST",
        cache: false,
        success: function(data) {
            $("#messages").html(data);
        },
        error: function(e) {
            // Fail message
        }
    });
}

function loadAbout() {
    $.ajax({
        url: "/portal-politico/about.php",
        type: "POST",
        cache: false,
        success: function(data) {
            $(".intro-text").html(data);
        },
        error: function(e) {
            // Fail message
        }
    });
}
function loadAboutMe() {
    $.ajax({
        url: "/portal-politico/about_me.php",
        type: "POST",
        cache: false,
        success: function(data) {
            $("#about").html(data);
        },
        error: function(e) {
            // Fail message
        }
    });
}
$("input,textarea").jqBootstrapValidation({
    preventSubmit: true,
    submitError: function($form, event, errors) {
        // additional error messages or events
    },
    submitSuccess: function($form, event) {
        event.preventDefault(); // prevent default submit behaviour
        // get values from FORM
        var email = $("input#email").val();
        var password = $("input#password").val();

        $.ajax({
            url: "/portal-politico/php/includes/process_login.php",
            type: "POST",
            data: {
                email: email,
                p: hex_sha512(password)
            },
            cache: false,
            success: function(data) {

                if (data === 'true') {
                    success();
                } else {
                    $("#nav-bar").hide();
                    $('#success').html("<div class='alert alert-danger'>");
                    $('#success > .alert-danger').html("<button type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;")
                            .append("</button>");
                    $('#success > .alert-danger').append("<strong>usuário ou senha inválidos!");
                    $('#success > .alert-danger').append('</div>');
                    //clear all fields
                }
            },
            error: function(e) {
                // Fail message
                $('#success').html("<div class='alert alert-danger'>");
                $('#success > .alert-danger').html("<button type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;")
                        .append("</button>");
                $('#success > .alert-danger').append("<strong>Lamento " + firstName + ", parece que ocorreu um problema. Tente novamente mais tarde!");
                $('#success > .alert-danger').append('</div>');
                //clear all fields
                $('#loginForm').trigger("reset");
            }
        });
    },
    filter: function() {
        return $(this).is(":visible");
    }
});