<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>ËÑË÷</title>
		<style type="text/css">
			body {
				text-align: center;
			}
			#center {
				margin-left: auto;
				margin-right: auto;
				margin-top: 200px;
			}
			#query {
				width: 405px;
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
		</style>
	</head>
	<body>
		<div id="center">
			<form action="/search" method="GET">
				<input id="query" name="q"/>
				<input type="submit" value="ËÑË÷" class="btn" 
					onmousedown="this.className='btn btn_h'"
					onmouseout="this.className='btn'"/>
			</form>		
		</div>
	</body>
</html>