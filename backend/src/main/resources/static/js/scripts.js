/*!
* Start Bootstrap - Shop Homepage v5.0.5 (https://startbootstrap.com/template/shop-homepage)
* Copyright 2013-2022 Start Bootstrap
* Licensed under MIT (https://github.com/StartBootstrap/startbootstrap-shop-homepage/blob/master/LICENSE)
*/
// This file is intentionally blank
// Use this file to add JavaScript to your project


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

function redeemCode1(){
    var elemento = document.getElementById("redeemedCoupon");
    if (elemento) {
        unredeemCode();
    }
    sleep(2000);
    redeemCode();
}

function redeemCode(){
    $.ajax({ 
        url:"/redeem?code="+$("#redeemText").val(),    
        type:"POST",
        contentType: "application/json; charset=utf-8",
        success: function(responseData){
            //console.log(responseData);
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