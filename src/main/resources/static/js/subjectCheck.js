function check(){

    alert("등록/수정이 완료되었습니다.")
    document.getElementById("frm").submit()
    return true
}



function allSelected() {
    document.getElementById('selectAllCheckbox')
        var checkboxes = document.querySelectorAll('input[name="selectedIds"]');
        checkboxes.forEach(function(checkbox) {
            checkbox.checked = event.target.checked;
        });
    }


function deleteSelectedSubjects() {
if (confirm('선택된 과목을 삭제하시겠습니까?')) {
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

 function goBack() {
        window.history.back();
    }


function updateSubject(subjectId) {
    var updateForm = document.createElement("form");
    updateForm.setAttribute("method", "get");
    updateForm.setAttribute("action", "/semi/admin/subject/updateSubject");

    var input = document.createElement("input");
    input.setAttribute("type", "hidden");
    input.setAttribute("name", "updateId");
    input.setAttribute("value", subjectId);

    updateForm.appendChild(input);
    document.body.appendChild(updateForm);

    updateForm.submit();
}

function professorCheck() {
    var selectedProfessorId = document.getElementById('professor').value;
    var selectedOption = document.getElementById('professor').options[document.getElementById('professor').selectedIndex];
    var selectedProfessorName = selectedOption.getAttribute('data-name');

    // 선택된 교수의 이름을 화면에 표시
    document.getElementById('selectedProfessorName').innerText = "　선택된 교수 :  " + selectedProfessorName;
}
