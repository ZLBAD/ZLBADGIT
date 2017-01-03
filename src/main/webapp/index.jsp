<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en-US">
  <head>
    
    <title>搜藏</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	
	<link rel="stylesheet" type="text/css" href="styles.css">
	<script type="text/javascript" src="js/jquery-1.9.1.js"></script>
	<script type="text/javascript">
	function add(){
		window.open ('addFavorite.html', '添加', 
		'height=300, width=400, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no');
	}
	</script>
  </head>
  <body>
    <form name="favForm" method="post" action="">
      <input type="hidden" name="op" value="toList" />
	  <div class="banner" valign="top"> 搜藏 </div>
      
	<table cellspacing="0" cellpadding="0" border="0">
		<tr>
			<td style="text-align:right;" valign="top">
			<!-- 左边Tag列表 -->
				<div class="left_labels" >
				 
				
				<table class="labels_table" cellspacing="0" cellpadding="0" border="0" id="left_table">
					<tr>
						<td>
							<a href="#" onclick="add();" style="font-weight:bold;">添加书签</a>
						</td>
					</tr>
					<tr>
						<td class="selected_label" onclick="clickAll(this)">
							<a href="#">全部</a>
						</td>
					</tr>
					<tr>
						<td>
							<a onclick="clickYunTu(this)" style="font-weight:bold;" href="#">云图</a>
						</td>
					</tr>
				</table>
				</div>
			</td>
			<td>
			<!-- 右边fav内容 -->
				<div class="content_links" id="right_div"></div>
			</td>
		</tr>
	</table> 
	
    </form>
  </body>
</html>
<script text="text/javascript">
	$(function(){
		$.ajax({
			url:"index.action",
			data:"op=SelectIndex",
			type:"POST",
			dataType:"json",
			success:function(data){
				if(data.code==0){
					alert("错误原因:"+data.errorMsg);
				}else{
					for(var i=0;i<data.obj[0].length;i++){
						var obj0=data.obj[0];
						var taghtml="<tr><td onclick='clickOne(this)'><a style='font-weight:bold;' href='#'>"
						+obj0[i].tname+"</a></td></tr>";
						$("#left_table").append(taghtml);
					}
					$("#right_div").html("");
					for(var j=0;j<data.obj[1].length;j++){
						var obj1=data.obj[1];
						var favhtml='<div style="padding: 6px 10px;"><div> <a href="'+obj1[j].furl+'" style="color: blue";font-size:18px;"target="_blank">'
						+obj1[j].flabel
						+'</a></div><div style="color:black;font-size:16px;">'
						+obj1[j].fdesc
						+'</div><div style="color:green; font-size:14px;">'
						+obj1[j].furl+'</div></div>';
						$("#right_div").append(favhtml);
					}
				}
			}
		});
	})
	
	function clickAll(index){
		$("#left_table td").each(function(){
			$(this).attr("class","");
		});
		index.className="selected_label";
		$.ajax({
			url:"fav.action",
			data:"op=SelectFavAll",
			type:"POST",
			dataType:"json",
			success:function(data){
				$("#right_div").html("");
				for(var j=0;j<data.obj.length;j++){
					var obj=data.obj;
					var favhtml='<div style="padding:6px 10px;"><div><a href="'+obj[j].furl+'" style="color: blue";font-size:18px;"target="_blank">'
					+obj[j].flabel
					+'</a></div><div style="color:black;font-size:16px;">'
					+obj[j].fdesc
					+'</div><div style="color:green; font-size:14px;">'
					+obj[j].furl+'</div></div>';
					$("#right_div").append(favhtml);
				}
			}
		});
	}
	
	function clickOne(index){
		$("#left_table td").each(function(){
			$(this).attr("class","");
		});
		index.className="selected_label";
		var tagname=index.firstChild.innerHTML;
		$.ajax({
			url:"fav.action",
			data:"op=SelectOne&tagname="+tagname,
			type:"POST",
			dataType:"json",
			success:function(data){
				if(data.code==1){
					$("#right_div").html("");
					for(var j=0;j<data.obj.length;j++){
						var obj=data.obj;
						var favhtml='<div style="padding:6px 10px;"><div><a href="'+obj[j].furl+'" style="color: blue";font-size:18px;"target="_blank">'
						+obj[j].flabel
						+'</a></div><div style="color:black;font-size:16px;">'
						+obj[j].fdesc
						+'</div><div style="color:green; font-size:14px;">'
						+obj[j].furl+'</div></div>';
						$("#right_div").append(favhtml);
					}
				}else{
					$("#right_div").html("还没有信息");
				}
			}
		});
	}
function clickYunTu(index){
	$("#left_table td").each(function(){
		$(this).attr("class","");
	});
	index.className="selected_label";
	$("#right_div").html("");
	$.ajax({
		url:"tag.action",
		data:"op=selectTagAll",
		type:"POST",
		dataType:"json",
		success:function(data){
			if(data.code==1){
				for(var i=0;i<data.obj.length;i++){
					var ytHtml="<a href='#' onclick='clickOnes(this)' style='font-size:"
					+(10 + 6*data.obj[i].tcount)
					+"px;'>"
					+data.obj[i].tname
					+"</a><br>";
					$("#right_div").append(ytHtml);
				}
			}else{
				alert("出错了");
			}
		}
	});
}

function clickOnes(index){
	var tagname=index.innerHtml;
	$("#left_table td").each(function(){
		$(this).attr("class","");
		if($(this).first().text()==tagname){
			$(this).attr("class","selected_label");
		}
	});
	$.ajax({
		url:"fav.action",
		data:"op=SelectOne&tagname="+tagname,
		type:"POST",
		dataType:"json",
		success:function(data){
			if(data.code==1){
				$("right_div").html("");
				for(var j=0;j<data.obj.length;j++){
					var obj=data.obj;
					var favhtml='<div style="padding:6px 10px;"><div><a href="'+obj[j].furl+'" style="color: blue";font-size:18px;"target="_blank">'
					+obj[j].flabel
					+'</a></div><div style="color:black;font-size:16px;">'
					+obj[j].fdesc
					+'</div><div style="color:green; font-size:14px;">'
					+obj[j].furl+'</div></div>';
					$("#right_div").append(favhtml);
				}
			}else{
				$("#right_div").html("还没有信息");
			}
		}
	})
	
}
</script>
