

//ie10以下不支持placeholder属性
   $(function(){  
 
  //判断浏览器是否支持placeholder属性
  supportPlaceholder='placeholder'in document.createElement('input'),
 
  placeholder=function(input){
 
    var text = input.attr('placeholder'),
    defaultValue = input.defaultValue;
 
    if(!defaultValue){
 
      input.val(text).addClass("phcolor");
    }
 
    input.focus(function(){
 
      if(input.val() == text){
   
        $(this).val("");
      }
    });
 
  
    input.blur(function(){
 
      if(input.val() == ""){
       
        $(this).val(text).addClass("phcolor");
      }
    });
 
    //输入的字符不为灰色
    input.keydown(function(){
  
      $(this).removeClass("phcolor");
    });
  };
 
  //当浏览器不支持placeholder属性时，调用placeholder函数
  if(!supportPlaceholder){
 
    $('input').each(function(){
 
      text = $(this).attr("placeholder");
 
      if($(this).attr("type") == "text" || $(this).attr("type") == "password"){
 
        placeholder($(this));
      }
    });
  }
  
//鼠标经过现二级菜单栏
 $('.navlist li').hover( //鼠标滑过导航栏目时 
   function(){ 
     $(this).find("dl").show();
     $(this).addClass("curr");
   }, 
     function(){ 
       $(this).find("dl").hide();
       $(this).removeClass("curr");
     } 
 );
});


