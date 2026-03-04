    <%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html>
    <head>
        <title>Login System</title>
    </head>
    <body>
    <h2>System Login</h2>

    <form action="login" method="POST">

        <div>
            <label>Username:</label>
            <input type="text" name="username" required>
        </div>
        <br>

        <div>
            <label>Password:</label>
            <input type="password" name="password" required>
        </div>
        <br>

        <div>
            <label>Select Role:</label>
            <input type="radio" id="admin" name="role" value="admin" required>
            <label for="admin">Admin</label>

            <input type="radio" id="user" name="role" value="user">
            <label for="user">User</label>
        </div>
        <br>

        <button type="submit">Login</button>
    </form>
    </body>
    </html>