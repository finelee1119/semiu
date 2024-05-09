function check(){
    if(document.getElementById("name").value.length == 0){
        alert("강의명이 입력되지 않았습니다.")
        document.getElementById("name").focus();
        return false
    }
    if(document.getElementById("professorName").value.length == 0){
        alert("강사 이름이 입력되지 않았습니다.")
        document.getElementById("professorName").focus();
        return false
    }
    if(document.getElementById("classroom").value.length == 0){
        alert("강의실이 선택되지 않았습니다.")
        document.getElementById("classroom").focus();
        return false
    }
    if(document.getElementById("subjectType").value.length == 0){
        alert("강의 구분이 선택되지 않았습니다.")
        document.getElementById("subjectType").focus();
        return false
    }
    if(document.getElementById("credit").value.length == 0){
        alert("이수학점이 입력되지 않았습니다.")
        document.getElementById("credit").focus();
        return false
    }
    var academicYearInput = document.getElementById("academicYear").value;
    if(academicYearInput.length == 0){
        alert("연도가 입력되지 않았습니다.")
        document.getElementById("academicYear").focus();
        return false
    }
    var academicYearPattern = /^2\d{3}$/;
    if(!academicYearInput.match(academicYearPattern)){
        alert("연도를 정확하게 입력해주세요. (4자리 숫자만 입력)")
        document.getElementById("academicYear").focus();
        return false
    }
    var semesterInput = document.getElementById("semester").value;
    if(semesterInput.length == 0){
        alert("학기가 입력되지 않았습니다.")
        document.getElementById("semester").focus();
        return false
    }
    var semesterPattern = /^[1-4]{1}$/
    if(!semesterPattern.test(semesterInput)){
        alert("학기는 1~4만 입력 가능합니다.")
        document.getElementById("semester").focus();
        return false
    }
    if(document.getElementById("dayOfWeek").value.length == 0){
        alert("강의 요일이 선택되지 않았습니다.")
        document.getElementById("dayOfWeek").focus();
        return false
    }
    var startTimeInput = document.getElementById("startTime").value;
    if(startTimeInput.length == 0){
        alert("강의 시작시간이 입력되지 않았습니다.")
        document.getElementById("startTime").focus();
        return false
    }
    if(startTimeInput.length <= 2 || !startTimeInput.includes(":")){
        alert("강의 시간은 HH:mm 형식으로 입력해야 합니다.")
        document.getElementById("startTime").focus();
        return false
    }
    var endTimeInput = document.getElementById("endTime").value;
    if(endTimeInput.length == 0){
        alert("강의 종료시간이 입력되지 않았습니다.")
        document.getElementById("endTime").focus();
        return false
    }
    if(endTimeInput.length <= 2 || !endTimeInput.includes(":")){
        alert("강의 시간은 HH:mm 형식으로 입력해야 합니다.")
        document.getElementById("endTime").focus();
        return false
    }
    var maxStudentInput = document.getElementById("maxStudent").value;
    if(maxStudentInput.length == 0){
        alert("강의 정원이 입력되지 않았습니다.")
        document.getElementById("maxStudent").focus();
        return false
    }
    var maxStudentPattern = /^(?:[0-9]|[12]\d|30)$/;
    if(!maxStudentPattern.test(maxStudentInput)){
        alert("강의실 최대 수용인원은 30명입니다.")
        document.getElementById("maxStudent").focus();
        return false
    }
    alert("강의 등록이 완료되었습니다.")
    document.getElementById("frm").submit()
    return true
}


function professorCheck() {
    var selectedProfessorId = document.getElementById('professor').value;
    var selectedOption = document.getElementById('professor').options[document.getElementById('professor').selectedIndex];
    var selectedProfessorName = selectedOption.getAttribute('data-name');

    // 선택된 교수의 이름을 화면에 표시
    document.getElementById('selectedProfessorName').innerText = "선택된 교수 :  " + selectedProfessorName;
}

    function allSelected() {
        document.getElementById('selectAllCheckbox')
            var checkboxes = document.querySelectorAll('input[name="selectedIds"]');
            checkboxes.forEach(function(checkbox) {
                checkbox.checked = event.target.checked;
            });
        }



    function deleteSelectedSubjects() {
        var form = document.getElementById("deleteForm");
        var selectedItems = form.querySelectorAll('input[name="selectedIds"]:checked');
        var selectedIds = [];
        for (var i = 0; i < selectedItems.length; i++) {
            selectedIds.push(selectedItems[i].value);
        }
        if(selectedIds.length === 0) {
            alert("삭제할 데이터가 없습니다.")
            return false
        }
        // Now you can send selectedIds array to your controller using Ajax or form submission
        console.log("SelectedIDs:", selectedIds);
        // Uncomment below line to submit form with selected IDs
        form.submit();
        return true
    }