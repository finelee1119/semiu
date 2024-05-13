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


//function saveValue(buttonElement) {
//    var parentTd = buttonElement.parentElement; // 해당 버튼의 부모 td 요소를 찾음
//    var selectElement = parentTd.querySelector('select');
//    var spanElement = parentTd.querySelector('span');
//
//    if (selectElement) {
//        var selectedOption = selectElement.options[selectElement.selectedIndex];
//        var selectedValue = selectedOption.value; // 선택된 옵션의 값 가져오기
//
//        // 선택된 값이 없는 경우에 대한 처리 추가
//        if (!selectedValue) {
//            alert("성적을 선택해주세요.");
//            return;
//        }
//
//        // span 요소에 선택된 값을 설정
//        spanElement.textContent = selectedValue;
//
//        // 폼 제출
//        var insertForm = document.getElementById('insertForm');
//        insertForm.submit();
//    }
//}



//function saveValue(buttonElement) {
//    var parentTd = buttonElement.parentElement; // 해당 버튼의 부모 td 요소를 찾음
//    var selectElement = parentTd.querySelector('select');
//    var hiddenInputElement = parentTd.querySelector('input[type="hidden"]');
//    var spanElement = parentTd.querySelector('span');
//
//    if (selectElement) {
//        var selectedOption = selectElement.options[selectElement.selectedIndex];
//        var selectedValue = selectedOption.value; // 선택된 옵션의 값 가져오기
//
//        // 선택된 값이 없는 경우에 대한 처리 추가
//        if (!selectedValue) {
//            alert("성적을 선택해주세요.");
//            return;
//        }
//
//        // hidden input 요소에 선택된 값 설정
//        hiddenInputElement.value = selectedValue;
//
//        // span 요소에 선택된 값 설정 (이 부분이 필요한지에 따라 선택)
//        spanElement.textContent = selectedOption.text;
//
//        // 저장 버튼 숨기기
//        buttonElement.style.display = "none";
//
//        // 폼 제출
//        var insertForm = document.getElementById('insertForm');
//        insertForm.submit();
//    }
//}






//function saveValue(buttonElement) {
//    var parentTd = buttonElement.parentElement; // 해당 버튼의 부모 td 요소를 찾음
//    var selectElement = parentTd.querySelector('select');
////    var gradeId = parentTd.getAttribute('data-id');
//    var spanElement = parentTd.querySelector('span');
//
//    if (selectElement) {
//       var selectedOption = selectElement.options[selectElement.selectedIndex];
//       var selectedValue = selectedOption.value; // 선택된 옵션의 값 가져오기
//
//        // 선택된 값이 없는 경우에 대한 처리 추가
//        if (!selectedValue) {
//            alert("성적을 선택해주세요.");
//            return;
//        }
//
//        // 폼 데이터에 선택된 셀렉트 박스의 값 설정
//        spanElement.value = selectedValue;
////        var gradeInput = document.getElementById('grade-' + gradeId) // 해당 식별자에 해당하는 폼 데이터 가져오기
////        gradeInput.value = selectedValue; // 해당 폼 데이터에 선택된 값 설정
//
//        // 저장 버튼 숨기기
//        buttonElement.style.display = "none";
//
//        // 폼 제출
//        var insertForm = document.getElementById('insertForm');
//        insertForm.submit();
//    }
//}

//
//function toggleInput(buttonElement) {
//    // 버튼의 부모 요소(td)를 찾음
//    var parentTd = buttonElement.parentElement;
//    // 해당 td 내부의 input 요소를 찾음
//    var inputElement = parentTd.getElementById("inputGrade")
//    var spanElement = parentTd.querySelector('span');
//
//    // input 요소가 존재하면, 현재 표시된 요소를 토글
//    if (inputElement) {
//            inputElement.style.display = "none";
//            spanElement.style.display = "inline-block";
//            // 인풋 상자에 입력된 값을 가져와서 출력
//            var inputValue = inputElement.value;
//            // 성적 출력하는 span 요소에 반영
//            spanElement.textContent = inputValue;
//            }
//            // 저장 버튼 숨김
//            var saveButton = parentTd.querySelector('.save-button');
//            if (saveButton) {
//                saveButton.style.display = "none";
//            }
//        }