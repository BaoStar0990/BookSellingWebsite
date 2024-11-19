<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container mt-5 author-section pt-4">
    <h2 class="text-center mb-4 font-bold">Các tác giả</h2>

    <div class="row justify-content-center">

<%--        attribute : authors List<Author>--%>

        <c:forEach items="${authors}" var="author">
            <div class="col-6 col-md-4 col-lg-2 text-center mb-4">
                <a href="${pageContext.request.contextPath}/authordetail/${author.id}" class="text-decoration-none text-dark">
                    <div class="author-card">
                        <img src="${author.imageURL}"   alt="${author.name}" class="author-image img-fluid rounded-circle" />
                        <p class="fw-bold mt-2">${author.name}</p>
                    </div>
                </a>
            </div>
        </c:forEach>
    </div>
</div>
