
    function allSelected() {
        document.getElementById('selectAllCheckbox')
            var checkboxes = document.querySelectorAll('input[name="selectedIds"]');
            checkboxes.forEach(function(checkbox) {
                checkbox.checked = event.target.checked;
            });
        }


    function deleteSelectedNotice() {
        if( confirm('정말로 게시글을 삭제하시겠습니까?') ){
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
        console.log("SelectedIDs:", selectedIds);
        form.submit();
        return true; } else { return false;}
    }

    function updateNotice(id) {
        var updateForm = document.createElement("form");
        updateForm.setAttribute("method", "get");
        updateForm.setAttribute("action", "/semi/notice/update/" + id);

        var input = document.createElement("input");
        input.setAttribute("type", "hidden");
        input.setAttribute("name", "updateId");
        input.setAttribute("value", id);

        updateForm.appendChild(input);
        document.body.appendChild(updateForm);

        updateForm.submit();
    }