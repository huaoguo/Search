<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>ËÑË÷</title>
		<style type="text/css">
			.center {
				margin-left: 150px;
			}
			#query {
				width: 450px;
				height: 22px;
				font: 16px/22px arial;
				background: white;
				outline: none;
				-webkit-appearance: none;
				padding: 3px;
			}
			.btn {
				width: 95px;
				height: 32px;
				font-size: 14px;
				background: #DDD url(http://s1.bdstatic.com/r/www/img/i-1.0.0.png);
				cursor: pointer;
				border: 0px;
			}
			.btn_h {
				background-position: -100px 0;
			}
			.item {
				margin-top: 30px;
				width: 550px;
				font-size: 13px;
			}
		</style>
	</head>
	<body>
		<div class="center">
			<div>
				<form action="/search" method="GET">
					<input id="query" name="q" value="${RequestParameters.q!''}"/>
					<input type="submit" value="ËÑË÷" class="btn" 
						onmousedown="this.className='btn btn_h'"
						onmouseout="this.className='btn'"/>
				</form>
			</div>
			<div>
				<#list docs as d>
					<div class="item">
						<a href="${d.url}" target="_blank" style="font-size: 16px">
						<#if d.title?length &gt; 40>
							${d.title?substring(0,40)}
						<#else>
							${d.title}
						</#if>
						</a>
						<br />
						<#if d.text?length &gt; 100>
							${d.text?substring(0,100)}
						<#else>
							${d.text}
						</#if>
						<br/>
						<span style="color:green">
						<#if d.url?length &gt; 50>
						${d.url?substring(0,50)}...
						<#else>
						${d.url}
						</#if>
						</span>
						<a href="/snapshot?id=${d.id?c}" target="_blank" style="color:#666">ÍøÒ³¿ìÕÕ</a>
					</div>
				</#list>
			</div>
		</div>
	</body>
</html>