var timer = true;
$('document').ready(function(){
    setInterval(function(){
        if(timer) $(".timer").html(parseInt($(".timer").html())+1);
    },1000);
});
