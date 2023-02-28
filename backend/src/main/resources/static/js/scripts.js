/*!
* Start Bootstrap - Shop Homepage v5.0.5 (https://startbootstrap.com/template/shop-homepage)
* Copyright 2013-2022 Start Bootstrap
* Licensed under MIT (https://github.com/StartBootstrap/startbootstrap-shop-homepage/blob/master/LICENSE)
*/
// This file is intentionally blank
// Use this file to add JavaScript to your project

function moreResults(page, totalPages) {
    $('#spinner').show();
    $('#buttonMoreResults').text('').prop('disabled', true);
    $.ajax({
        type: 'GET',
        url: '/products?page=' + page,
        success: function(data) {
            const container = document.querySelector('.containerMoreResults');
            var newContainer = $(data).find('.containerMoreResults').html();
            var listProducts = $(data).find('.container').eq(2).html();
            $('#containerMoreResults').replaceWith('<div id="containerMoreResults" class="containerMoreResults">'+newContainer+'</div>');
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
