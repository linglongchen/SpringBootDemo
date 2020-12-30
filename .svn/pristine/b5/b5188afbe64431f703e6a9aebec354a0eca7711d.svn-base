// JavaScript Document

function mHeight(){
	//var hei1 = $("body").height() - 275;
	//var hei2 = hei1/2 - 175;
	//var hei3 = hei1 - 80;
	//var hei4 = hei3 - 50;
	//$(".centerbox").height(hei1);
	//$(".loginbox").css({"margin-top":hei2});
	//$(".demobox").height(hei3);
	//$(".mainbox").height(hei3);
	//$(".mainbox .left").height(hei3);
	//$(".mainbox .right").height(hei3);
	//$(".mainbox .dbox_4_list").height(hei4);
	//$(".mainbox .dbox_3_list").height(hei4);
};


$(function(){
	//centerbox
	//mHeight();
	$("img").click(function(){
		var srcs = $(this).attr("src");
		if( srcs == "images/demo_04_1.png" ){
			$(this).attr({"src":"images/demo_04_1_2.png"});
		}else if( srcs == "images/demo_04_1_2.png" ){
			$(this).attr({"src":"images/demo_04_1.png"});
		}
	});
	
	$(".tabl .floors li").click(function(){
		$(this).toggleClass("curr");
		var index = $(this).index();
		$(".floorimg .wdshowbox").eq(index).toggle();
	});
	$(".tabl2 .floors li").click(function(){
		$(this).toggleClass("curr");
		var index = $(this).index();
		$(".floorimg img").eq(index).toggle();
	});
	
	autoAdjustWindow();
});

//自动调整iframe的高度,自适应树的展开和收缩
function autoAdjustWindow(){ 
	try{ 
		//取到窗口的高度 
		var winH = $(window).height(); 
		//取到页面的高度 
		var bodyH = $(document).height(); 
		if(bodyH > winH){ 
			window.frameElement.height = bodyH;
			window.frameElement.parentElement.height = bodyH;
		}else if(winH == bodyH && winH > 850){
			window.frameElement.height = 850;
			window.frameElement.parentElement.height = 850;
			autoAdjustWindow();
		}else{
			window.frameElement.height = winH;
			window.frameElement.parentElement.height = winH;
		}
 	}catch(e){
  
  	}
}
















