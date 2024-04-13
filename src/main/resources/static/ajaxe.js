$(document).ready(function(){
    $('#example7').change(function(){
        var id=$(this).val();
        $.ajax({
            type:'GET',
            url:'/states/'+id,
            success:function(data){
                $('#example8').empty();
                $('#city').empty();

                $.each(data,function(index,state){
                    $('#example8').append('<option value="'+state.stateid+'">'+state.statename+'</option>');
                });
            }
        });
    });

    $('#example8').change(function(){
        var stateid=$(this).val();
        $.ajax({ // Fixed typo here, should be $.ajax instead of $ajax
            type:'GET',
            url:'/cities/'+stateid,
            success:function(data){
                $('#example9').empty();
                $.each(data,function(index,city){
                    $('#example9').append('<option value="'+city.cityid+'">'+city.cityname+'</option>');
                });
            }
        });
    });
});
