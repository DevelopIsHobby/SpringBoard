<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" language="java" %>
<%@ include file="../includes/header.jsp" %>
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">Tables</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <!-- /.row -->
        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        Board List Page
                        <button id="regBtn" type="button" class="btn btn-xs pull-right">Register New Board</button>
                    </div>
                    <!-- /.panel-heading -->
                    <div class="panel-body">
                        <table width="100%" class="table table-striped table-bordered table-hover" id="dataTables-example">
                            <thead>
                            <tr>
                                <th>#번호</th>
                                <th>제목</th>
                                <th>작성자</th>
                                <th>작성일</th>
                                <th>수정일</th>
                            </tr>
                            </thead>
                            <c:forEach var="board" items="${list}">
                                <tr>
                                    <td><c:out value="${board.bno}"/></td>
                                    <td>
                                        <a class="move" href="<c:out value='${board.bno}'/>"><c:out value="${board.title}"/>
                                        <b>[ <c:out value='${board.replyCnt}'/> ]</b>
                                        </a>
                                    </td>
                                    <td><c:out value="${board.writer}"/></td>
                                    <td><fmt:formatDate pattern="yyyy-MM-dd" value="${board.regdate}"/></td>
                                    <td><fmt:formatDate pattern="yyyy-MM-dd" value="${board.updateDate}"/></td>
                                </tr>
                            </c:forEach>
                            </table>
                            <!-- /.table -->
                        <!-- 검색조건 시작 -->
                        <div class="row">
                            <div class="col-lg-12">
                                <form action="/board/list" id="searchForm" method="get">
                                    <select name="type">
                                        <option value="" <c:out value="${pageMaker.cri.type==null? 'selected' : ''}"/>>--</option>
                                        <option value="T" <c:out value="${pageMaker.cri.type=='T'? 'selected' : ''}"/>>제목</option>
                                        <option value="C" <c:out value="${pageMaker.cri.type=='C'? 'selected' : ''}"/>>내용</option>
                                        <option value="W" <c:out value="${pageMaker.cri.type=='W'? 'selected' : ''}"/>>작성자</option>
                                        <option value="TC" <c:out value="${pageMaker.cri.type=='TC'? 'selected' : ''}"/>>제목 or 내용</option>
                                        <option value="TW" <c:out value="${pageMaker.cri.type=='TW'? 'selected' : ''}"/>>제목 or 작성자</option>
                                        <option value="TWC" <c:out value="${pageMaker.cri.type=='TWC'? 'selected' : ''}"/>>제목 or 작성자 or 내용</option>
                                    </select>
                                    <input type="text" name="keyword" value="<c:out value='${pageMaker.cri.keyword}'/>"/>
                                    <input type="hidden" name="pageNum" value="${pageMaker.cri.pageNum}">
                                    <input type="hidden" name="amount" value="${pageMaker.cri.amount}">
                                    <button class="btn btn-default">Search</button>
                                </form>
                            </div>
                        </div>
                        <!-- 검색조건 끝 -->

                        <!-- Pagination 시작 -->
                        <div class="pull-right">
                            <ul class="pagination">
                                <c:if test="${pageMaker.prev}">
                                    <li class="pagination_button previous"><a href="${pageMaker.startPage-1}">Previous</a></li>
                                </c:if>
                                <c:forEach var="num" begin="${pageMaker.startPage}" end="${pageMaker.endPage}">
                                    <li class="pagination_button" ${pageMaker.cri.pageNum==num?"active":""}><a href="${num}">${num}</a></li>
                                </c:forEach>
                                <c:if test="${pageMaker.next}">
                                    <li class="pagination_button previous"><a href="${pageMaker.endPage+1}">Next</a></li>
                                </c:if>
                            </ul>
                        </div>
                        <!-- end Pagination -->
                        <form action="/board/list" id="actionForm" method="get">
                            <input type="hidden" name="pageNum" value="${pageMaker.cri.pageNum}">
                            <input type="hidden" name="amount" value="${pageMaker.cri.amount}">
                            <input type="hidden" name="type" value="<c:out value='${pageMaker.cri.type}'/>">
                            <input type="hidden" name="keyword" value="<c:out value='${pageMaker.cri.keyword}'/>">
                        </form>
                        <!-- Modal 추가 -->
                        <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                        <h4 class="modal-title" id="myModalLabel">Modal title</h4>
                                    </div>
                                    <div class="modal-body">처리가 완료되었습니다.</div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                        <button type="button" class="btn btn-primary">Save changes</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- Modal 끝 -->
                    </div>
                    <!-- /.panel-body -->
                </div>
                <!-- /.panel -->
            </div>
            <!-- /.col-lg-6 -->
        </div>
        <!-- /.row -->

<script type="text/javascript">
    $(document).ready(function() {

        const result = '<c:out value="${result}"/>'

        checkModal(result);

        history.replaceState({}, null, null);

        function checkModal(result) {
            if(result=='' || history.state) {
                return;
            }
            if(parseInt(result)>0) {
                $(".modal-body").html("게시글" + parseInt(result) + " 번이 등록되었습니다.");
            }
            $("#myModal").modal("show");
        }
        $("#regBtn").on("click", function() {
            self.location ="/board/register";
        })

        const actionForm = $("#actionForm");

        $(".pagination_button a").on("click", function(e) {
            e.preventDefault();

            console.log("click");
            actionForm.find("input[name='pageNum']").val($(this).attr("href"));
            actionForm.submit();
        })

        $(".move").on("click", function(e) {
            e.preventDefault();

            const bno = actionForm.find("input[name='bno']").val();
            if(bno!=''){
                actionForm.find("input[name='bno']").remove();
            }

            actionForm.append("<input type='hidden' name='bno' value='" + $(this).attr("href") + "'>");
            actionForm.attr("action", "/board/get");
            actionForm.submit();
        })

        const searchForm = $("#searchForm")

        $("#searchForm button").on("click", function(e) {
            if(!searchForm.find("option:selected").val()) {
                alert("검색 종류를 선택하세요");
                return false;
            }
            if(!searchForm.find("input[name='keyword']").val()) {
                alert("키워드를 입력하세요");
                return false;
            }
            searchForm.find("input[name='pageNum']").val("1");
            e.preventDefault();

            searchForm.submit();
        })
    })
</script>

<%@ include file="../includes/footer.jsp" %>
