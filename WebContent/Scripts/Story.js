//ページ読み込みイベントに登録
document.addEventListener("DOMContentLoaded", Main, false);

function Main(){

	if(location.search=="?p=0"){
		var sendData = {};
		sendData.cmd = "read1"
		AFL.sendJson("Ajax10", sendData, game);
	}else if(location.search=="?p=1"){
		var sendData = {};
		sendData.cmd = "read2"
		sendData.name =AFL.getCookie("USER_NAME")
		AFL.sendJson("Ajax10", sendData, game);
	}

	var bbb = document.querySelector("div#bbb");
	bbb.addEventListener("click", onClick);
}

function onClick(){
	var sendData = {};
	sendData.cmd = "read3";
	AFL.sendJson("Ajax10", sendData, game);
	}

function Click(btnNo) {
	 if(btnNo == 1){
	var sendData = {};
	sendData.cmd = "read5";
	sendData.name =  AFL.getCookie("USER_NAME")
	AFL.sendJson("Ajax10", sendData,fate);
}else{
	var sendData = {};
	sendData.cmd = "read4";
	AFL.sendJson("Ajax10", sendData, game);
	}
}

	// データ受信処理
function game(datas) {
	var bbb = document.querySelector("div#bbb");
	var aaa = document.querySelector("div#aaa");

	for ( var index in datas) {
		var data = datas[index];
//		bbb.innerHTML = AFL.sprintf("%s<br>", data.scenario);

		if(data.id <= 6){
			aaa.style.backgroundImage = "url(\"Image/BG00h_80.jpg\")";
			bbb.innerHTML = AFL.sprintf("%s<br>", data.scenario);
		}else if(data.id == 7){
			aaa.style.backgroundImage = "url(\"Image/machi.jpg\")";
			bbb.innerHTML = AFL.sprintf("%s<br>", data.scenario);
		}else if(data.id <= 13){
			aaa.style.backgroundImage = "url(\"Image/gousei3.jpg\")";
			bbb.innerHTML = AFL.sprintf("%s<br>", data.scenario);
		}else if(data.id == 14){
			aaa.style.backgroundImage = "url(\"Image/gousei3.jpg\")";
			//bbb.innerHTML = AFL.sprintf("<div id =\"ddd\">選択肢１ : ありがとうございます</div><br><div id =\"eee\">選択肢２ : いえ、大丈夫です</div>");
			bbb.innerHTML = "";

			var n1 = document.createElement("div");
			n1.innerHTML = "選択肢１ : ありがとうございます";
			bbb.appendChild(n1);

			n1.addEventListener("click", onClick_1);
			function onClick_1(event) {
				var sendData = {};
				sendData.id = 16;
				sendData.cmd = "read3";
				AFL.sendJson("Ajax10", sendData, game);
				if(event.preeventDefault)
					event.preeventDefault();
				if(event.stopPropagation)
					event.stopPropagation();
				return false;
			}


			var n2 = document.createElement("div");
			n2.innerHTML = "選択肢２ : いえ、大丈夫です";
			bbb.appendChild(n2);

			n2.addEventListener("click", onClick_2);
			function onClick_2()
			{
				var sendData = {};
				sendData.id =56;
				sendData.cmd = "read3";
				AFL.sendJson("Ajax10", sendData, game);
				if(event.preeventDefault)
					event.preeventDefault();
				if(event.stopPropagation)
					event.stopPropagation();
				return false;
			}

		}else if(data.id >= 16){
			aaa.style.backgroundImage = "url(\"Image/gousei3.jpg\")";
			bbb.innerHTML = AFL.sprintf("%s<br>", data.scenario);

		}

	}

}


function fate(){

}