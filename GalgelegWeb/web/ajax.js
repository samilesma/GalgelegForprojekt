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
                alert(data);
            }
        });
    });
});