$('document').ready(function(){
    $('.ajax').on('submit',function(e){
        e.preventDefault();
        var url=$(this).attr("action");
        var form=$(':input:visible',this).serialize()+"&"+$('input[type=hidden]',this).serialize();
        $.ajax({
            cache:false,
            type:"POST",
            dataType: "json",
            url:url,
            data:form,
            success:function(data)
            {
                if(data.hasOwnProperty("refresh")) location.reload();
                if(data.hasOwnProperty("nulstill")) {
                    document.getElementById("hangmanpic").src = 'grafik/forkert0.png';
                    document.getElementById("finalMessage").innerHTML = "";
                    document.getElementById("finalMessage").style.color = 'black';
                    document.getElementById("guessForm").hidden = false;
                    document.getElementById("newGameForm").hidden = true;
                    document.getElementById("brugteBogstaver").innerHTML = "[]";
                    document.getElementById("synligtOrd").innerHTML = data.synligtOrd;
                    document.getElementById("time").innerHTML = 0;
                }
            }
        });
    });
});

$('document').ready(function(){
    $('.sendmsg').on('submit',function(e){
        e.preventDefault();
        var url=$(this).attr("action");
        var bogstav=$('input#letter').val();
        var form=$(':input:visible',this).serialize()+"&"+$('input[type=hidden]',this).serialize();
        $.ajax({
            cache:false,
            type:"POST",
            dataType: "json",
            url:url,
            data:form,
            success:function(data)
            {
                $('input#letter').val("");
                if($("span#brugteBogstaver").html().indexOf(bogstav)>=0) $("div#alert").html('<div class="alert alert-warning" role="alert">Du har gættet på dette bogstav før!</div>');
                else if($("span#numbErrors").html()==data.antalForkerteBogstaver) $("div#alert").html('<div class="alert alert-success" role="alert">Korrekt gæt!</div>');
                else $("div#alert").html('<div class="alert alert-danger" role="alert">Forkert gæt!</div>');
                
                $("span#synligtOrd").html(data.synligtOrd);
                $("span#brugteBogstaver").html("["+data.brugteBogstaver.join(", ")+"]");
                $("span#numbErrors").html(data.antalForkerteBogstaver);
                
                if(data.antalForkerteBogstaver==7)
                {
                    document.getElementById("hangmanpic").src = 'grafik/tabt.png';
                    document.getElementById("finalMessage").innerHTML = "Øv du har tabt!";
                    document.getElementById("finalMessage").style.color = 'red';
                    document.getElementById("ordetLabel").innerHTML = "Ordet var "+data.ordet;
                    document.getElementById("guessForm").hidden = true;
                    document.getElementById("newGameForm").hidden = false;
                }
                else if(data.synligtOrd.indexOf("*")==-1)
                {
                    document.getElementById("hangmanpic").src = 'grafik/vundet.png';
                    document.getElementById("finalMessage").innerHTML = "Tillykke du har vundet!";
                    document.getElementById("finalMessage").style.color = 'green';
                    document.getElementById("guessForm").hidden = true;
                    document.getElementById("newGameForm").hidden = false;
                }
                else document.getElementById("hangmanpic").src = 'grafik/forkert'+data.antalForkerteBogstaver+'.png';
            }
        });
    });
});
