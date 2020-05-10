<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html lang="en">
<head>
    <title>Patient Application</title>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
            crossorigin="anonymous"></script>
</head>
<body>
<div class="navbar  navbar-expand-lg navbar-dark bg-dark">
    <div class="container">
        <div class="navbar-brand"> Patient Database Application</div>

        <ul class="navbar-nav mr-auto">
            <li class="nav-item"><a class="nav-link" href="/">Patients</a></li>
        </ul>
		<ul>
			<li>
                <a class="nav-link" href="/check-connection">
                    <svg class="bi bi-server" width="1em" height="1em" viewBox="0 0 16 16" fill="white" xmlns="http://www.w3.org/2000/svg">
                        <path d="M13 3.75c-.322.24-.698.435-1.093.593C10.857 4.763 9.475 5 8 5s-2.857-.237-3.907-.657A4.881 4.881 0 013 3.75V6c0 1.105 2.239 2 5 2s5-.895 5-2V3.75z"/>
                        <path d="M13 7.75c-.322.24-.698.435-1.093.593C10.857 8.763 9.475 9 8 9s-2.857-.237-3.907-.657A4.881 4.881 0 013 7.75V10c0 1.105 2.239 2 5 2s5-.895 5-2V7.75z"/>
                        <path d="M13 11.75c-.322.24-.698.435-1.093.593-1.05.42-2.432.657-3.907.657s-2.857-.237-3.907-.657A4.883 4.883 0 013 11.75V14c0 1.105 2.239 2 5 2s5-.895 5-2v-2.25z"/>
                    </svg>
                </a>
			</li>
		</ul>
    </div>
</div>

<c:import url="/${page}.jsp"/>
</body>
</html>
