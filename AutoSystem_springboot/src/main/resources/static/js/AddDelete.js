function appendRow()
{
    var objTBL = document.getElementById("tbl");
    if (!objTBL)
        return;

    var count = objTBL.rows.length;

    // 最終行に新しい行を追加
    var row = objTBL.insertRow(count);

    // 列の追加
    var c1 = row.insertCell(0);
    var c2 = row.insertCell(1);
    var c3 = row.insertCell(2);

    // 各列に表示内容を設定
    c1.innerHTML = '&emsp;&emsp;&emsp;';
    c2.innerHTML = '<input class="inpval" type="text"   id="answer' + count + '"  name="answer"placeholder="Answer" autofocus="autofocus" >';
    c3.innerHTML = '<input class="delbtn" type="button" id="delBtn' + count + '"  value="delete" onclick="deleteRow(this)">';

    // 追加した行の入力フィールドへフォーカスを設定
    var objInp = document.getElementById("answer[' + count + ']" );
    if (objInp)
        objInp.focus();
}

/*
 * deleteRow: 削除ボタン該当行を削除
 */
function deleteRow(obj)
{
    var objTR = obj.parentNode.parentNode;
    var objTBL = objTR.parentNode;

    if (objTBL)
        objTBL.deleteRow(objTR.sectionRowIndex);

    // id/name ふり直し
    var tagElements = document.getElementsByTagName("input");
    if (!tagElements)
        return false;

    // <input type="text" id="answerN">
    var seq = 1;
    for (var i = 0; i < tagElements.length; i++)
    {
        if (tagElements[i].className.match("inpval"))
        {
            tagElements[i].setAttribute("id", "answer" + seq);
            ++seq;
        }
    }

    // <input type="button" id="delBtnN">
    seq = 1;
    for (var i = 0; i < tagElements.length; i++)
    {
        if (tagElements[i].className.match("delbtn"))
        {
            tagElements[i].setAttribute("id", "delBtn" + seq);
            ++seq;
        }
    }
}