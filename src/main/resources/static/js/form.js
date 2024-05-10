
//조건 확인========================
//수강 신청 선택 과목 안보이게 하는 상태

//수강 신청 선택 과목 내역에 추가
function add(id,professor,classroom,subjectType,credit,dayOfWeek,startTime,endTime,maxStudent,totalStudent){
    const table = document.getElementById('lecture');
    alert(table);
    const addLecture = document.getElementById('addLecture');
    const new_row = addLecture.insertRow();
    const cell_length = table.rows[1].cells.length;
   for(let i = 0; i < cell_length; i++) {
           const new_cell = new_row.insertCell(i);
           let temp_html = ``;
           if(i === 0) {
                   temp_html = `<td>`+ id + `</td>`;
           } else if(i === 1) {
                   temp_html = `<td>`+ professor + `</td>`;
           } else if(i === 2) {
                    temp_html = `<td>`+ classroom + `</td>`;
           } else if(i === 3) {
                    temp_html = `<td>`+ subjectType + `</td>`;
           } else if(i === 4) {
                    temp_html = `<td>`+ credit + `</td>`;
           } else if(i === 5) {
                    temp_html = `<td>`+ dayOfWeek + `</td>`;
           } else if(i === 6) {
                     temp_html = `<td>`+ startTime + `</td>`;
           } else if(i === 7) {
                     temp_html = `<td>`+ endTime + `</td>`;
           } else if(i === 8) {
                      temp_html = `<td>`+ maxStudent + `</td>`;
           } else if(i === 9) {
                      temp_html = ` <td>`+ totalStudent + `</td>`;
           } else {
                     temp_html = `<td><button onClick={handleRegister} class="btn btn-outline-primary me-2">취소</button></td>`;
           };
           alert(temp_html);
           new_cell.insertAdjacentHTML('beforeend', temp_html);
    }
    document.getElementById('add_lecture').innerText="신청완료";
    document.getElementById('add_lecture').innerText="신청완료";
}

