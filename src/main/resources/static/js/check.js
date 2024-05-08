function check(){
    alert("버튼이 눌렸습니다.")
    IF(document.getElementById("title").value.trim().length == 0){
        alert("제목이 입려되지 않았습니다.")
        document.getElementById("title").focus();
        return false;
    }
    IF(document.getElementById("contents").value.length == 0){
            alert("제목이 입려되지 않았습니다.")
            document.getElementById("title").focus();
            return false;
        }
        alert("입력이 완료되었습니다.")
        document.getElementById("frm").submit()
        return true;
}

function res(){
    alert("처음부터 다시 입력합니다.")
    document.getElementById("frm").reset();
    document.getElementById("title").focus();
}