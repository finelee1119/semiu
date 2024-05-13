function toggleEdit(buttonElement) {
    var parentTd = buttonElement.parentElement;
    var selectElement = parentTd.querySelector('select');
    var spanElement = parentTd.querySelector('span');

    if (selectElement) {
        if (selectElement.style.display === "none") {
            selectElement.style.display = "inline-block";
            selectElement.value = spanElement.textContent;
            spanElement.style.display = "none";
            buttonElement.style.display = "none";

            var saveButton = parentTd.querySelector('.save-button');
            if (saveButton) {
                saveButton.style.display = "inline-block";
            }
        } else {
            selectElement.style.display = "none";
            spanElement.style.display = "inline-block";

            var saveButton = parentTd.querySelector('.save-button');
            if (saveButton) {
                saveButton.style.display = "none";
            }
        }
    }
}


function saveValue(buttonElement) {
    var parentTd = buttonElement.parentElement; // 해당 버튼의 부모 td 요소를 찾음
    var selectElement = parentTd.querySelector('select');
    var gradeInput = document.getElementById('grade'); // hidden 요소에 대한 참조

    if (selectElement && gradeInput) {
        var selectedOption = selectElement.options[selectElement.selectedIndex];
        var selectedValue = selectedOption.value; // 선택된 옵션의 값 가져오기

        // 선택된 값이 없는 경우에 대한 처리 추가
        if (!selectedValue) {
            alert("성적을 선택해주세요.");
            return;
        }

        // hidden 요소에 선택된 값 설정
        gradeInput.value = selectedValue;

        // 폼 제출
        var insertForm = document.getElementById('insertForm');
        insertForm.submit();
    }
}


