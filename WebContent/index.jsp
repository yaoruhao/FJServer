<html>
<head>
	<title>FJWB-api-Server</title>
</head>
<body>
<h1>Welcome to FJWB-api-Server</h1>

<h2>Celebration! FJWB-api-Server release milestone 1.0  -- By Ruhao Yao</h2>
<ul>
<li>
/book/type/{typeStr}/start/{startNum}/length/{lengthNum} -- Get book list
<ul>
<li>typeStr = {all, jiaoyijingdian, fofajiedu}</li>
<li>startNum:[0, n] && lengthNum:(0, n] -- otherwise return empty list</li>
<li>Example: {HostAddr}/book/type/fofajiedu/start/0/length/20</li>
</ul>
</li>
<li>/book/summary  -- Get summary info of each book type</li>

<li>/music/type/{typeStr}/start/{startNum}/length/{lengthNum} -- Get music list
<ul>
<li>typeStr = {all, fojingniansong, foyigequ, mingxingyanyi}</li>
<li>startNum:[0, n] && lengthNum:(0, n] -- otherwise return empty list</li>
<li>Example: {HostAddr}/music/type/foyigequ/start/0/length/20</li>
</ul>

</li>
<li>/music/summary  -- Get summary info of each music type</li>

<li>/zenword/start/{startNum}/length/{lengthNum}  -- Get meiriyichan list
<ul>
<li>startNum:[0, n] && lengthNum:(0, n] -- otherwise return empty list</li>
<li>Example: {HostAddr}/zenword/start/0/length/20</li>
</ul>
</li>
<li>/zenword/summary  -- Get summary info of zenword list</li>

<li>/image/platform/{platformStr}/type/{typeStr}/start/{startNum}/length/{lengthNum}  -- Get image list
<ul>
<li>platformStr = {ios, android}  typeStr = {chantu, foxiang}</li>
<li>startNum:[0, n] && lengthNum:(0, n] -- otherwise return empty list</li>
<li>Example: {HostAddr}/image/platform/ios/type/all/start/0/length/20
</ul></li>
<li>/image/summary  -- Get summary info of each image type</li>
</ul>
</body>
</html>