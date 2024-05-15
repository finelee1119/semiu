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
    if (spanElement == null) {
        if (selectElement.style.display === "none") {
            selectElement.style.display = "inline-block";
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
    var parentTr = buttonElement.closest('tr'); // 버튼이 속한 tr 찾기
    var parentForm = parentTr.querySelector('form'); // 폼 찾기
    var gradeInput = parentTr.querySelector('input[name="grade"]'); // grade 값 저장할 input (Hidden) 찾기
    var selectElement = parentTr.querySelector('select');
    var spanElement = parentTr.querySelector('span');

    if (selectElement && gradeInput) {
        var selectedOption = selectElement.options[selectElement.selectedIndex];
        var selectedValue = selectedOption.value;

        if (!selectedValue) {
            alert("성적을 선택해주세요.");
            return;
        }

        gradeInput.value = selectedValue;
        parentForm.submit();
    }
}

function allSelected() {
        document.getElementById('selectAllCheckbox')
            var checkboxes = document.querySelectorAll('input[name="selectedIds"]');
            checkboxes.forEach(function(checkbox) {
                checkbox.checked = event.target.checked;
            });
        }


    function deleteSelectedSubjects() {
    if (confirm('정말로 이 과목을 삭제하시겠습니까?')) {
        var form = document.getElementById("deleteForm");
        var selectedItems = form.querySelectorAll('input[name="selectedIds"]:checked');
        var selectedIds = [];
        for (var i = 0; i < selectedItems.length; i++) {
            selectedIds.push(selectedItems[i].value);
        }
        if(selectedIds.length === 0) {
            alert("삭제할 데이터가 없습니다.")
            return false;
        }
        console.log("SelectedIDs:", selectedIds);
        form.submit();
        return true;
    } else {
        return false;}
    }