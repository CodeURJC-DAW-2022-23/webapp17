/*!
* Start Bootstrap - Shop Homepage v5.0.5 (https://startbootstrap.com/template/shop-homepage)
* Copyright 2013-2022 Start Bootstrap
* Licensed under MIT (https://github.com/StartBootstrap/startbootstrap-shop-homepage/blob/master/LICENSE)
*/
// This file is intentionally blank
// Use this file to add JavaScript to your project

function moreResults(page, totalPages) {
    $('#spinner').show();
    $('#buttonMoreResults').hide();
    $.ajax({
        type: 'GET',
        url: '/products?page=' + page,
        success: function(data) {
            
            var listProducts = $(data).find('.container').eq(2).html();
            $('#containerMoreResults').replaceWith($(data).find("#containerMoreResults"));
            $('#listProducts').append(listProducts);
            console.log(data);
            console.log(listProducts);
            sleep(3000);
            $('#spinner').hide();
            
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

onload=function()
{
    // Cargamos una imagen aleatoria
    rotarImagenes();
    //tiempo en milisegundos
    setInterval(rotarImagenes,4000);
};
