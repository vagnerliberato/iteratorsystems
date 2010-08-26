// Preciso ver se funciona!
function excluir() {
    var answer = confirm("Deseja Realmente Excluir?")
    if (answer){
        alert("Registro Excluido Com Sucesso")
        window.location = "http://www.cps.com/";
    }
    else{
        alert("Registro Não Excluido!")
    }
}

function alterar() {
    var answer = confirm("Deseja Realmente Alterar?")
    if (answer){
        alert("Registro Alterado Com Sucesso")
        window.location = "http://www.cps.com/";
    }
    else{
        alert("Registro Não Alterado!")
    }
}

function confirmar() {
    var answer = confirm("Deseja Realmente Confirmar?")
    if (answer){
        alert("Confirmado Com Sucesso")
        window.location = "http://www.cps.com/";
    }
    else{
        alert("Não Confirmado!")
    }
}

