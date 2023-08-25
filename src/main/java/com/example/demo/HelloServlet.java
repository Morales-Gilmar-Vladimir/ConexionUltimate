package com.example.demo;

import java.io.*;
import java.sql.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;
    public Connection connection;

    public void init() {
        message = "Bienvenido a la base de datos Estudiantes";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        try {
            String url = "jdbc:mysql://localhost:3306/Almacenamiento";
            String user = "root";
            String password = "root_bas3";
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (connection != null) {
            try {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM Estudiantes");
                while (resultSet.next()) {
                    int id = resultSet.getInt("ID");
                    String nombre = resultSet.getString("Nombre");
                    int edad = resultSet.getInt("edad");
                    String ciudad = resultSet.getString("ciudad");
                    int cedula = resultSet.getInt("cedula");

                    out.println("<p>ID: " + id + ", Nombre: " + nombre + ", Edad: " + edad + ", Ciudad: " + ciudad + ", Cédula: " + cedula + "</p>");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            out.println("<p>Error: No se pudo establecer la conexión a la base de datos</p>");
        }

        out.println("</body></html>");
    }
}