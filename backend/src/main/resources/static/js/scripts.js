/*!
* Start Bootstrap - Shop Homepage v5.0.5 (https://startbootstrap.com/template/shop-homepage)
* Copyright 2013-2022 Start Bootstrap
* Licensed under MIT (https://github.com/StartBootstrap/startbootstrap-shop-homepage/blob/master/LICENSE)
*/
// This file is intentionally blank
// Use this file to add JavaScript to your project

function moreResults(page) {
    $('#spinner').show();
    $('#buttonMoreResults').hide();
    $.ajax({
        type: 'GET',
        url: '/products?page=' + page,
        success: function(data) {
            
            var listProducts = $(data).find('.container').eq(2).html();
            $('#containerMoreResults').replaceWith($(data).find("#containerMoreResults"));
            $('#listProducts').append(listProducts);
            //console.log(data);
            //console.log(listProducts);
            sleep(3000);
            $('#spinner').hide();
            
        }
    });
}

function moreComments(page,id) {
    $('#spinner').show();
    $('#buttonMoreResults').hide();
    $.ajax({
        type: 'GET',
        url: '/description?page=' + page + '&id=' + id,
        success: function(data) {
            
            var listComments = $(data).find("#listComments").html();
            $('#containerMoreResults').replaceWith($(data).find("#containerMoreResults"));
            $('#listComments').append(listComments);
            console.log(data);
            console.log(listComments);
            sleep(3000);
            $('#spinner').hide();
            
        }
    });
}

function pagMoreResults(pages, name, urlto){
    $('#spinner'+name).show();
    $('#buttonMoreResults'+name).hide();
    $.ajax({
        type: 'GET',
        url: urlto + pages,
        success: function(data) {
            var listProducts = $(data).find("#listProducts"+name).html();
            $('#containerMoreResults'+name).replaceWith($(data).find("#containerMoreResults"+name));
            $('#listProducts'+name).append(listProducts);
            console.log(data);
            console.log(listProducts);
            sleep(3000);
            $('#spinner'+name).hide();
            
        }
    });
}

function sleep(milliseconds) {
    var start = new Date().getTime();
    for (var i = 0; i < 1e7; i++) {
     if ((new Date().getTime() - start) > milliseconds) {
      break;
     }
    }
   }

$(document).ready(function() {
    // Ocultar el spinner después de que se cargue el documento
    $('#spinner').hide();
});

  /** 
   * Variables index
   */
  var imagenesindex=new Array(
        
    'images/index1.png',

    'images/index2.png',

    'images/index3.png',
);

/*** Funcion para cambiar la imagen con desvanecimiento*/

  /*Cambio de imágenes*/

function rotarImagenes(){
    // obtenemos un numero aleatorio entre 0 y la cantidad de imagenes que hay
    var index=Math.floor((Math.random()*imagenesindex.length));
    // cambiamos la imagen
    
    document.getElementById("imagenindex").src=imagenesindex[index];
  
}

    /*wow init*/
    new WOW().init();
    
onload=function()
{

    // Cargamos una imagen aleatoria
    rotarImagenes();
    //tiempo en milisegundos
    setInterval(rotarImagenes,4000);
    
};

function redeemCode(){
    $.ajax({ 
        url:"/redeem?code="+$("#redeemText").val(),    
        type:"POST",
        contentType: "application/json; charset=utf-8",
        success: function(responseData){
            console.log(responseData);        
            location.reload();
        },error: function (xhr, ajaxOptions, thrownError){
            alert("No se puede canjear ese código")
        }
    });
}
function unredeemCode(){
    $.ajax({ 
        url:"/unredeem",
        type:"POST",
        contentType: "application/json; charset=utf-8",
        success: function(responseData){
            console.log(responseData);        
            location.reload();
        },error: function (xhr, ajaxOptions, thrownError){
            alert("No fue posible devolver nada")
        }
    });
}