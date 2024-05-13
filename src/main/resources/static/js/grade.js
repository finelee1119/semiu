function toggleEdit(buttonElement) {
    // 버튼의 부모 요소(td)를 찾음
    var parentTd = buttonElement.parentElement;
    // 해당 td 내부의 input 요소를 찾음
    var inputElement = parentTd.querySelector('input[type="text"]');
    var spanElement = parentTd.querySelector('span');

    // input 요소가 존재하면, 현재 표시된 요소를 토글
    if (inputElement) {
        if (inputElement.style.display === "none") {
            inputElement.style.display = "inline-block";
            inputElement.value = spanElement.textContent; // 이미 있는 값을 설정
            spanElement.style.display = "none";
            buttonElement.style.display = "none";

            // 저장 버튼 표시
            var saveButton = parentTd.querySelector('.save-button');
            if (saveButton) {
                saveButton.style.display = "inline-block";
            }

        } else {
            inputElement.style.display = "none";
            spanElement.style.display = "inline-block";
            // 인풋 상자에 입력된 값을 가져와서 출력
            var inputValue = inputElement.value;
            // 성적 출력하는 span 요소에 반영
            spanElement.textContent = inputValue;

            // 저장 버튼 숨김
            var saveButton = parentTd.querySelector('.save-button');
            if (saveButton) {
                saveButton.style.display = "none";
            }
        }
    }
}

function toggleInput(buttonElement) {
    // 버튼의 부모 요소(td)를 찾음
    var parentTd = buttonElement.parentElement;
    // 해당 td 내부의 input 요소를 찾음
    var inputElement = parentTd.getElementById("inputGrade")
    var spanElement = parentTd.querySelector('span');

    // input 요소가 존재하면, 현재 표시된 요소를 토글
    if (inputElement) {
            inputElement.style.display = "none";
            spanElement.style.display = "inline-block";
            // 인풋 상자에 입력된 값을 가져와서 출력
            var inputValue = inputElement.value;
            // 성적 출력하는 span 요소에 반영
            spanElement.textContent = inputValue;
            }
            // 저장 버튼 숨김
            var saveButton = parentTd.querySelector('.save-button');
            if (saveButton) {
                saveButton.style.display = "none";
            }
        }



function saveValue(buttonElement) {
    var parentTd = buttonElement.parentElement;
    // 해당 td 내부의 input 요소를 찾음
    var inputElement = parentTd.querySelector('input[type="text"]');
    var spanElement = parentTd.querySelector('span');

    // 인풋 상자가 존재하면, 값을 저장하고 상태를 변경
    if (inputElement) {
        // 인풋 상자에 입력된 값을 가져옴
        var inputValue = inputElement.value;
        // 성적을 출력하는 span 요소에 반영
        spanElement.textContent = inputValue;
        // 인풋 상자를 숨기고 span 요소를 보이도록 설정
        inputElement.style.display = "none";
        spanElement.style.display = "inline-block";
        // 수정 버튼 표시
        var editButton = parentTd.querySelector('.edit-button');
        if (editButton) {
            editButton.style.display = "inline-block";
        }
        // 현재 클릭된 버튼을 숨김
        buttonElement.style.display = "none";

        insertForm.submit()
    }
}
