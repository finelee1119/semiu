$(function(){
    $("#submit_btn").on("click", function(){
        alert("등록/수정이 완료되었습니다.")
    })
})

$(function(){
  $("#selectAllCheckbox").on("click", function(){
    if($("#selectAllCheckbox").is(":checked")){
      $('input[name="selectedIds"]').each(function(){
        $(this).prop('checked', true);
      });
    } else {
      $('input[name="selectedIds"]').each(function(){
        $(this).prop('checked', false);
      });
    }
  });
});

$(function(){
   $("#delete_btn").on("click", function(){
    var selectedIds = [];
    $("input[name='selectedIds']:checked").each(function(){
        selectedIds.push($(this).val());
        });
        $("#deleteForm").submit();
   });
});

 function goBack() {
        window.history.back();
    }

//function updateSubject(subjectId) {
//    var updateForm = document.createElement("form");
//    updateForm.setAttribute("method", "get");
//    updateForm.setAttribute("action", "/semi/admin/subject/updateSubject");
//
//    var input = document.createElement("input");
//    input.setAttribute("type", "hidden");
//    input.setAttribute("name", "updateId");
//    input.setAttribute("value", subjectId);
//
//    updateForm.appendChild(input);
//    document.body.appendChild(updateForm);
//
//    updateForm.submit();
//}

$(function(){
    $("").on("click", function(subjectId){
    const updateForm = $('<form></form>')
    .attr({'method': 'get',
           'action' : "/semi/admin/subject/updateSubject"});
    const input = $('<input>')
    .attr({'type' : 'hidden',
           'name' : 'updateId',
           'value' : subjectId});

           $(updateForm).append(input);
           $('body').append(updateForm);
           $(updateForm).submit();
        })
    })


function professorCheck() {
    var selectedProfessorId = document.getElementById('professor').value;
    var selectedOption = document.getElementById('professor').options[document.getElementById('professor').selectedIndex];
    var selectedProfessorName = selectedOption.getAttribute('data-name');

    // 선택된 교수의 이름을 화면에 표시
    document.getElementById('selectedProfessorName').innerText = "　선택된 교수 :  " + selectedProfessorName;
}
