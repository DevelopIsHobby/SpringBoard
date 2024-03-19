<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ include file="../includes/header.jsp" %>
<div class="row">
    <div class="col-lg-12">
        <h1 class="page-header">Board Modify</h1>
    </div>
    <!-- /.col-lg-12 -->
</div>
<!-- /.row -->
<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                Board Modify Page
            </div>
            <!-- /.panel-heading -->
            <div class="panel-body">
                <form action="/board/modify" role="form" method="post">
                    <input type="hidden" name="${_csrf.paramterName}" vlaue="${_csef.token}">

                    <input type="hidden" name="pageNum" value="<c:out value='${cri.pageNum}'/>">
                    <input type="hidden" name="amount" value="<c:out value='${cri.amount}'/>">
                    <input type="hidden" name="type" value="<c:out value='${cri.type}'/>">
                    <input type="hidden" name="keyword" value="<c:out value='${cri.keyword}'/>">

                    <div class="form-group">
                        <label for="">Bno</label>
                        <input type="text" class="form-control" name="bno" value="<c:out value='${board.bno}'/>" readonly="readonly">
                    </div>
                    <div class="form-group">
                        <label for="">Title</label>
                        <input type="text" class="form-control" name="title" value="<c:out value='${board.title}'/>">
                    </div>
                    <div class="form-group">
                        <label for="">Text area</label>
                        <textarea class="form-control" name="content" rows="3" >${board.content}</textarea>
                    </div>
                    <div class="form-group">
                        <label for="">Writer</label>
                        <input type="text" class="form-control" name="writer" value="<c:out value='${board.writer}'/>">
                    </div>
                    <div class="form-group">
                        <label for="">RegDate</label>
                        <input type="text" class="form-control" name="regDate" value="<fmt:formatDate pattern="yyyy/MM/dd" value="${board.regdate}"/>" readonly="readonly">
                    </div>
                    <div class="form-group">
                        <label for="">Update Date</label>
                        <input type="text" class="form-control" name="updateDate" value="<fmt:formatDate pattern="yyyy/MM/dd" value="${board.updateDate}"/>" readonly="readonly">
                    </div>

                    <sec:authentication property="principal" var="pinfo"/>
                    <sec:authorize access="isAuthenticated()">
                        <c:if test="${pinfo.username eq board.writer}">
                            <button type="submit" data-oper="modify" class="btn btn-default">Modify</button>
                            <button type="submit" data-oper="remove" class="btn btn-danger">Remove</button>
                        </c:if>
                    </sec:authorize>

                    <button type="submit" data-oper="list" class="btn btn-info">List</button>
                </form>
            </div>
            <!-- /.panel-body -->
        </div>
        <!-- /.panel -->
    </div>
    <!-- /.col-lg-6 -->
</div>
<!-- /.row -->
<div class="bigPictureWrapper">
    <div class="bigPicture"></div>
</div>

<style>
    .uploadResult {
        width : 100%;
        background-color : grey;
    }
    .uploadResult ul {
        display: flex;
        flex-flow : row;
        justify-content: center;
        align-items: center;
    }

    .uploadResult ul li {
        list-style : none;
        padding : 10px;
        align-content: center;
        text-align: center;
    }

    .uploadResult ul li img {
        width : 100px;
    }

    .uploadResult ul li span {
        color : white;
    }

    .bigPictureWrapper {
        position : absolute;
        display : none;
        justify-content: center;
        align-items: center;
        top: 0%;
        width : 100%;
        height : 100%;
        background-color: grey;
        z-index : 100;
        background : rgba(255, 255, 255, 0.5);
    }

    .bigPicture {
        position : relative;
        display : flex;
        justify-content: center;
        align-items: center;
    }
    .bigPicture img {
        width : 600px;
    }
</style>

<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">Files</div>
            <!-- /.panel-heading end -->
            <div class="panel-body">
                <div class="form-group uploadDiv">
                    <input type="file" name="uploadFile" multiple="multiple">
                </div>

                <div class="uploadResult"></div>
            </div>
            <!-- /.panel-body end -->
        </div>
        <!-- /.panel -->
    </div>
    <!-- /.col end -->
</div>
<!-- /.row end -->
<script type="text/javascript">
    $(document).ready(function() {
        (function() {
            const bno = '<c:url value="${board.bno}"/>';

            $.getJSON("/board/getAttachList", {bno:bno}, function(arr) {
                console.log(arr);

                let str = "";

                $(arr).each(function(i, attach) {
                    if(attach.fileType) {
                        let fileCallPath = encodeURIComponent(attach.uploadPath + "/s_" + attach.uuid + "_" + attach.fileName);

                        str += "<ul><li data-path='" + attach.uploadPath + "' data-uuid='" + attach.uuid + "' data-fileName='" +
                            attach.fileName + "' data-type='" + attach.fileType + "' ></div>";
                        str += "<span> " + attach.fileName + "</span>";
                        str += "<button type='button' data-file=\'" + fileCallPath + "\' data-type='image' ";
                        str += "class='btn btn-warning btn-circle'><i class='fa fa-times'></i></button><br>";
                        str += "<img src='/display?fileName=" + fileCallPath + "'>";
                        str += "</div>";
                        str += "</li></ul>";
                    } else {
                        str += "<ul><li data-path='" + attach.uploadPath + "' data-uuid='" + attach.uuid + "' data-fileName='" +
                            attach.fileName + "' data-type='" + attach.fileType + "' ></div>";
                        str += "<span> " + attach.fileName + "</span>";
                        str += "<button type='button' data-file=\'" + fileCallPath + "\' data-type='image' ";
                        str += "class='btn btn-warning btn-circle'><i class='fa fa-times'></i></button><br>";
                        str += "<img src='/resources/img/attatch.png'>";
                        str += "</div>";
                        str += "</li></ul>";
                    }
                    $(".uploadResult").html(str);
                })

            }); // end getJSON
        })(); // end function

        $(".uploadResult").on("click", "button", function(e) {
            console.log("delete file");

            if(confirm("Remove this file?")) {
                let targetLi = $(this).closest("li");
                targetLi.remove();
            }
        })

        const regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$");
        const maxSize = 5242880;

        function checkExtension(fileName, fileSize) {
            if(fileSize >= maxSize) {
                alert("파일 사이즈 초과");
                return false;
            }
            if(regex.test(fileName)) {
                alert("해당 종류의 파일은 업로드할 수 없습니다.");
                return false;
            }
            return true;
        }

        let csrfHeaderName = "${_csrf.headerName}";
        let csrfTokenValue = "${_csrf.token}";

        $("input[type='file']").change(function(e){
            let formData = new FormData();
            let inputFile = $("input[name='uploadFile']");
            let files = inputFile[0].files;

            for(let i=0; i<files.length; i++) {
                if(!checkExtension(files[i].name, files[i].size)) {
                    return false;
                }
                formData.append("uploadFile", files[i]);
            }
            $.ajax({
                url : '/uploadAjaxAction',
                processData : false,
                contentType : false,
                beforeSend : function(xhr) {
                    xhr.setRequestHeader(csrfHeaderName, csrfTokenValue);
                },
                data : formData,
                type : 'post',
                dataType : 'json',
                success : function(result) {
                    console.log(result);
                    showUploadResult(result);
                }
            })
        })

        const uploadResult = $(".uploadResult");

        function showUploadResult(uploadResultArr) {
            if (!uploadResultArr || uploadResultArr.length == 0) {
                return;
            }
            let uploadUL = $(".uploadResult ul");

            let str = "<ul>";

            $(uploadResultArr).each(function (i, obj) {
                // image type
                if (obj.fileType) {
                    let fileCallPath = encodeURIComponent(obj.uploadPath + "/s_" + obj.uuid + "_" + obj.fileName);

                    str += "<li data-path='" + obj.uploadPath + "'";
                    str += " data-uuid=" + obj.uuid + "' data-filename='" + obj.fileName + "' + data-type='" + obj.fileType + "'";
                    str += "><div>";
                    str += "<span> " + obj.fileName + "</span>";
                    str += "<button type='button' data-file=\'" + fileCallPath + "\'data-type='image' class='btn btn-warning btn-circle'>" +
                        "<i class='fa fa-times'></i></button><br>";
                    str += "<img src='/display?fileName=" + fileCallPath + "'>";
                    str += "</div>";
                    str += "</li></ul>"
                } else {
                    let fileCallPath = encodeURIComponent(obj.uploadPath + "/" + obj.uuid + "_" + obj.fileName);
                    let fileLink = fileCallPath.replace(new RegExp(/\\/g), "/");

                    str += "<li data-path='" + obj.uploadPath + "'";
                    str += " data-uuid=" + obj.uuid + "' data-filename='" + obj.fileName + "' + data-type='" + obj.fileType + "'";
                    str += "><div>";
                    str += "<span> " + obj.fileName + "</span>";
                    str += "<button type='button' data-file=\'" + fileCallPath + "\' data-type='file' class='btn btn-warning btn-circle'><i class='fa fa-times'></i></button><br>";
                    str += "<img src='/resources/img/attatch.png'></a>";
                    str += "</div>";
                    str += "</li></ul>";
                }
                uploadResult.append(str);
            })
        }


        const formObj = $("form")

        $("button").on("click", function(e) {
            e.preventDefault();

            const operation = $(this).attr("data-oper");

            console.log(operation);

            if(operation=="remove") {
                formObj.attr("action", "/board/remove");
            } else if(operation=="list") {
                formObj.attr("action", "/board/list").attr("method", "get");
                const pageNumTag = $("input[name='pageNum']").clone();
                const amountTag = $("input[name='amount']").clone();
                const keywordTag = $("input[name='keyword']").clone();
                const typeTag = $("input[name='type']").clone();
                if(!confirm("수정된 내용이 삭제됩니다. 목록으로 돌아가시겠습니까?")) return;
                formObj.empty();
                formObj.append(pageNumTag);
                formObj.append(amountTag);
                formObj.append(keywordTag);
                formObj.append(typeTag);
            } else if(operation =="modify") {
                console.log("submit clicked");

                let str = "";

                $(".uploadResult ul li").each(function(i, obj) {
                    let jobj = $(obj);
                    console.dir(jobj);

                    str += "<input type='hidden' name='attachList["+i+"].fileName' value='" + jobj.data("filename")+"'>";
                    str += "<input type='hidden' name='attachList["+i+"].uuid' value='" + jobj.data("uuid")+"'>";
                    str += "<input type='hidden' name='attachList["+i+"].uploadPath' value='" + jobj.data("path")+"'>";
                    str += "<input type='hidden' name='attachList["+i+"].fileType' value='" + jobj.data("type")+"'>";
                })
                formObj.append(str).submit();

            }
            formObj.submit();
        })
    })
</script>
<%@ include file="../includes/footer.jsp" %>