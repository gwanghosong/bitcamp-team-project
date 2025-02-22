<%@page import="com.eomcs.lms.domain.Free"%>
<%@page import="com.eomcs.lms.domain.Member"%>
<%@page import="com.eomcs.lms.domain.Comment"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
  trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<body>
   
  
    <div class="comment-list">
      <table class="table table-hover">
        <tbody class="cmtlist">
           <c:forEach items="${list}" var="comment">
            <span style="font-size: 15px; margin-right: 10px;"><b>${comment.member.name}</b></span>
            <span style="font-size: 12px;">${comment.modifierDate}</span>
            <br> 
            <div class="conts" style="word-break: break-all;">      
            <c:if test="${sessionScope.loginUser.name eq comment.member.name}">    
              <div class="btnsave" style="margin-bottom: 10px;">
                  <%-- <a class="deletebtn btn btn-outline-dark" 
                    href='${contextRootPath}/app/comment/delete/${comment.no}'>삭제</a> --%>   
                    <input id="cmtNo" type="hidden" value="${comment.no}">
                    <a class="deletebtn" style="font-size: 13px; cursor: pointer;">삭제</a>
                  <a class="update-btn" style="font-size: 13px; cursor: pointer;">/ 수정</a>   
                  <div class="save-cancel" style="display: none;">  
                   <form id="form-save" action="../comment/update" method="post">
                    <input type="text" class="form-control-plaintext" id="no" name='no' value='${comment.no}'style="display:none;">
                   <input class="form-control contentsF" name='contents' style="margin-top:10px; font-size: 20px; height:53px; margin-bottom: 5px;" value="${comment.contents}"/>
                    <span class="counter2">0/150</span>       
                    <button class="save-btn btn btn-outline-dark">저장</button>       
                     </form>
                    <button class="cancel-btn btn btn-outline-dark">취소</button>     
                  </div>
                </div>    
              </c:if>
              <input type="text" class="no" name="no" value="${comment.no}" style="display: none;">
              <label class="cmtcontents" style="font-size: 18px; margin-bottom:5px; padding:10px;" >${comment.contents}</label>
              <input class="cmtcontents2" type="hidden" value="${comment.contents}"
                style="font-size: 20px; display:inline;  margin-bottom:5px;" >
              
              
              <hr style="margin-top: 0px;">
            </div>
          </c:forEach>
        </tbody>
      </table>
    </div>
 

<!-- JQuery -->
  <jsp:include page="../commonSideHeaderFooter/commonSidebarBottom.jsp"/> 

  <script type="text/javascript">

  
      $(document).ready(function() {
        
        $('.update-btn').click(function() {
          $(this).hide();
          $(this).prev().hide();
          $.fn.btnFuntion($(this));
          $.fn.contsFunction($(this));
        });

        $.fn.btnFuntion = function(updatebtn) {
          var state = updatebtn.next();
          if (state.css('display') == 'none') {
            state.show();
            
          }
        };

        $.fn.contsFunction = function(updatebtn) {
          var state = updatebtn.parent().parent().children('.cmtcontents');
          if (state.css('style') != 'none') {
            state.attr("style", "display:none;");
          }
        };
           
        $('.contentsF').on('keyup', function() {  
          if($(this).val().length > 150) {
              $(this).val($(this).val().substring(0, 150));
          }
          
          var content = $(this).val();
          $(this).height(((content.split('\n').length + 1) * 1.5) + 'em');
          $('.counter2').html(content.length + '/150');
      }); 
      $('.contentsF').keyup(); 
        

        $('.cancel-btn').click(function() {
          var up = $(this).parent().parent().parent().children().next().next($(".cmtcontents2")); 
          var prevText = up.val();
          $(this).parent().children($('.contentsF')).val(prevText);
          $('.update-btn').show();
           $('.deletebtn').show();  
         
           var state = $('.save-cancel').css('display');

          if (state == 'none') {
            $('.save-cancel').hide();
          } else {
            $('.save-cancel').hide();
          }

          var state = $('.cmtcontents').css('style');
          if (state != 'none') {
            $('.cmtcontents').attr("style", "font-size: 20px;");
          }
        });

      $('.cancel-btn').on('click', function() {
        $('.cmtcontents').attr('readonly', 'true');
      });
      
        $(".save-btn").click(function(){
            /* $(".cmt-list").load("list.jsp"); */
            
              var conts = $('.contentsF').val();  
              
              $.post('../comment/update', 
                  { 
                contents: conts
                  },
                function(obj) {
                  if (obj.status == 'success') { 
                    
                 document.location.reload(true); 
                  }
            })
        });
        
        $(".deletebtn").click(function(){
          
          var cmtNo = $(this).prev().val();
          
            $.ajax({
             type:"GET",
             url:'${contextRootPath}/app/comment/delete/' + cmtNo,
             success:function(obj){ 
                if (obj.status == 'success') {   
               document.location.reload(true); 
                } 
             }
      });
      });
      });
      
    </script>

</body>
</html>







