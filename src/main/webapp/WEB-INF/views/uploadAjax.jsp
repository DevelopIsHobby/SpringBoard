<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html"; charset="utf-8">
    <title>Insert Title Here</title>
    <script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>


</head>
<body>
<h1>Upload with Ajax</h1>
<div class="uploadDiv">
    <input type="file" name="uploadFile" multiple>
</div>
<button id="uploadBtn">Upload</button>

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
        /*justify-content: center;*/
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


<div class="uploadResult">
    <ul></ul>
</div>

<script>
    function showImage(fileCallPath) {
        alert(fileCallPath)
        $(".bigPictureWrapper").css("display", "flex").show();
        $(".bigPicture").html("<img src='/display?fileName=" + encodeURI(fileCallPath) + "'>").animate({width : '100%', height : '100%'},1000);
        $(".bigPictureWrapper").on("click", function(e) {
            $(".bigPicture").animate({width : '0%', height : '0%'},1000)
            setTimeout(() => {
                $(".bigPictureWrapper").hide()
            },1000)
        })


    }
    $(document).ready(function() {
        let regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$");
        let maxSize = 5242880;

        function checkExtension(fileName, fileSize) {
            if(fileSize>=maxSize) {
                alert("파일 사이즈 초과");
                return false;
            }
            if(regex.test(fileName)) {
                alert("해당 종류의 파일은 업로드할 수 없습니다.");
                return false;
            }
            return true;
        }

        const cloneObj = $(".uploadDiv").clone();

        $("#uploadBtn").on("click", function(e) {
            let formData = new FormData();
            let inputFile = $("input[name='uploadFile']");
            let files = inputFile[0].files;

            console.log(files);

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
                data : formData,
                type : "post",
                dataType : 'json',
                success : function (result) {
                    console.log(result);
                    showUploadFile(result);
                    $(".uploadDiv").html(cloneObj.html());
                }
            })// $.ajax
        })

        const uploadResult = $(".uploadResult");

        function showUploadFile(uploadResultArr) {
            let str = "";

            $(uploadResultArr).each(function(i,obj) {
                if(!obj.image) {
                    // str += "<li><img src='resources/img/attatch.png'/>" + obj.fileName + "</li>"
                    let fileCallPath = encodeURIComponent(obj.uploadPath + "/" + obj.uuid + "_" + obj.fileName);

                    let fileLink = fileCallPath.replace(new RegExp(/\\/g),"/");

                    str += "<li><div><a href='/download?fileName=" + fileCallPath +"'>" + "<img src='/resources/img/attatch.png'>"+obj.fileName+"</a>"+
                        "<span data-file=\'"+fileCallPath+"\' data-type='file'> x </span>" +"</div></li>"
                } else {
                    // str += "<li>" + obj.fileName + "</li>";
                    let fileCallPath = encodeURIComponent(obj.uploadPath + "/s_" + obj.uuid+"_"+obj.fileName);
                    let originPath = obj.uploadPath + "\\" + obj.uuid + "_" + obj.fileName;

                    originPath = originPath.replace(new RegExp(/\\/g),"/");

                    str += "<li><a href=\"javascript:showImage(\'"+originPath+"\')\"><img src='\display?fileName="+fileCallPath+"'></a>"
                        +  "<span data-file=\'"+fileCallPath+"\' data-type='image'> x </span>"+ "</li>";
                    // str += "<li><img src='/display?fileName=" + fileCallPath+"'></li>"
                }
            })
            uploadResult.append(str);
        }

        $(".uploadResult").on("click", "span", function(e) {
            let targetFile = $(this).data("file");
            let type = $(this).data("type");
            console.log(targetFile);

            $.ajax({
                url : '/deleteFile',
                data : {fileName : targetFile, type : type},
                dataType : 'text',
                type : 'post',
                success : function(result) {
                    alert(result);
                }
            })
        })


    })


</script>
</body>
</html>
