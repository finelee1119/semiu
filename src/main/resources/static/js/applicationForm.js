
//수강 신청 선택 과목 내역에 추가
function add(id,name,professor,classroom,subjectType,credit,dayOfWeek,startTime,endTime,maxStudent,totalStudent, event){
    let result = '';
    const table = document.getElementById('lecture');
    const addLecture = document.getElementById('addLecture');
    console.log(addLecture);
    const new_row = addLecture.insertRow();
    const cell_length = table.rows[1].cells.length;
    //check 확인
    console.log(event.target.checked);
   //해당 과목이 체크 되어 있을 때
  if(event.target.checked){
       //해당 과목 > 수강신청내역 table에 추가
       for(let i = 0; i < cell_length; i++) {
               const new_cell = new_row.insertCell(i);
               let temp_html = ``;
               if(i === 0) {
                       temp_html = `<td><span>`+ id + `</span></td>`;
               } else if(i === 1) {
                        temp_html = `<td>`+ name + `</td>`;
               } else if(i === 2) {
                        temp_html = `<td>`+ professor + `</td>`;
               } else if(i === 3) {
                        temp_html = `<td>`+ classroom + `</td>`;
               } else if(i === 4) {
                        temp_html = `<td>`+ subjectType + `</td>`;
               } else if(i === 5) {
                        temp_html = `<td>`+ credit + `</td>`;
               } else if(i === 6) {
                        temp_html = `<td>`+ dayOfWeek + `</td>`;
               } else if(i === 7) {
                         temp_html = `<td>`+ startTime + `</td>`;
               } else if(i === 8) {
                         temp_html = `<td>`+ endTime + `</td>`;
               } else if(i === 9) {
                          temp_html = `<td>`+ maxStudent + `</td>`;
               } else if(i === 10) {
                          temp_html = ` <td>`+ ( Number(totalStudent) +1 )+ `</td>`;
               } else {
                         temp_html = `<td>신청완료</td>`;
               };
//                alert(temp_html);
               new_cell.insertAdjacentHTML('beforeend', temp_html);

        }
     }else{
           //check_ID : span_List
           var applicationItems = null;
           var applicationId = addLecture.querySelectorAll("span");
           for (let i = 0; i < applicationId.length; i++) {
                applicationItems = applicationId[i].textContent;
           }
           console.log("id"+applicationItems);

          //수강신청목록 : tr
          var applicationList = addLecture.querySelectorAll('tr');
          for (let i = 0; i < applicationList.length; i++) {
                   var applicationListIds = applicationList[i];
                   console.log("2", applicationListIds);
                   if(applicationListIds.textContent == ''){
      //                    console.log("첫 if 문");
                      continue;
                   }else{
                         var applicationListId = applicationListIds.querySelector("span").textContent;
                             console.log("text", applicationListId);

                         if(applicationItems == applicationListId){
                            console.log(applicationList);
                            console.log(applicationListIds);
                            applicationList[i].remove();
                            return;
                         }else{

                         }
                   }

               }
         }
  }

//checkBox 선택 신청 과목 내역
function applicationSelectedSubjects() {
    if (confirm('수강 신청을 완료하시겠습니까?')) {
        var form = document.getElementById("applicationForm");
        var selectedItems = form.querySelectorAll('input[name="checkedIds"]:checked');
        var selectedIds = [];
        for (var i = 0; i < selectedItems.length; i++) {
            selectedIds.push(selectedItems[i].value);
        }
        if(selectedIds.length === 0) {
            alert("수강 신청 내역이 없습니다.")
            return false;
        }
         alert(selectedIds);
        console.log("SelectedIDs:", selectedIds);
        form.submit();
        return true;
    } else {
        return false;}
    }

//선택 신청 과목 내역 삭제 버튼
function deleteApplication() {
  alert("삭제버튼");
 }

