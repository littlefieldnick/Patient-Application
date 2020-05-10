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
            <li>
                <a class="nav-link" href="/check-connection">
                    Check Database Connection
                </a>
            </li>
        </ul>

    </div>
</div>

<c:import url="/${page}.jsp"/>
</body>
</html>
