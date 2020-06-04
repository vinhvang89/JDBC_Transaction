package Controller;

import Model.User;
import Model.UserDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "UserServlet",urlPatterns = "/users")
public class UserServlet  extends HttpServlet {
    private static final long serialVersionUID = 1l;
    private UserDAO userDAO;
    public void  init(){
        userDAO = new UserDAO();
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
        String action = req.getParameter("action");
        if(action == null)
            action = "";
        switch (action){
            case "create":
                insertUser(req,resp);
                break;
            case "edit":
                try {
                    updateUser(req,resp);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
    public void insertUser(HttpServletRequest req , HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String country = req.getParameter("country");
        User newUser = new User(name,email,country);
        userDAO.insertUser(newUser);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("user/create.jsp");
        requestDispatcher.forward(req,resp);
    }
    public void  updateUser(HttpServletRequest req , HttpServletResponse resp) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String country = req.getParameter("country");
        User fuck = new User(id,name,email,country);
        userDAO.updateUser(fuck);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("users/edit.jsp");
        requestDispatcher.forward(req,resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if(action == null)
            action = "";
        switch (action){
            case "creat":
                showNewForm(req,resp);
                break;
            case "edit":
                showEditForm(req,resp);
                break;
            case "delete":
                try {
                    deleteUser(req,resp);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                break;
            default:
                listUser(req,resp);
                break;
        }
    }
    public void showNewForm(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("user/create.jsp");
            requestDispatcher.forward(request,response);
    }
    public void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        User existingUser = userDAO.selectUser(id);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("user/edit.jsp");
        request.setAttribute("user",existingUser);
        requestDispatcher.forward(request,response);
    }
    public void deleteUser(HttpServletRequest request,HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        userDAO.deleteUser(id);
        List<User> users  = userDAO.selectAllUsers();
        request.setAttribute("listUser",users);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/users");
        requestDispatcher.forward(request,response);
    }
    public void listUser(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        List<User> users = userDAO.selectAllUsers();
        request.setAttribute("listUser",users);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("user/list.jsp");
        requestDispatcher.forward(request,response);
    }
}
