$('document').ready(function(){
    setInterval(function(){
        $(".timer").html(parseInt($(".timer").html())+1);
    },1000);
});
