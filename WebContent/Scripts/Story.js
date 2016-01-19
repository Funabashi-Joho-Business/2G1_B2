//ページ読み込みイベントに登録
document.addEventListener("DOMContentLoaded", Main, false);

function Main(){

}

	// データ受信処理
function game(datas) {

	var bbb = document.querySelector("div#bbb");

	for ( var index in datas) {
		var data = datas[index];
		bbb.innerHTML = AFL.sprintf("[%d]%s<br>%s", data.id, data.scenario);
	}
}