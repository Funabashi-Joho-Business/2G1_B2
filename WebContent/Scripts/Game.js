//ページ読み込みイベントに登録
document.addEventListener("DOMContentLoaded", Main, false);

function Main(){
}
	// 初めからか、続きからかの選択およびデータ送信
function kakunin(btnNo) {
	if (btnNo == 1) {
		link = "はじめから";
		href = "story.html";
		location.href = href+"?p=0";
	} else {
		link = "つづきから";
		href = "story.html";
		location.href = href+"?p=1";
	}
}

