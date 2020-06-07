package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements IUser {
    private String jdbcURL = "jdbc:mysql://localhost:3306/demo?useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "zxcvb";
    private static final String INSERT_USER_SQL = "insert into users" + " (name,email,country) VALUES "+"(?,?,?);";
    private static final String SELECT_USER_BY_ID = "select id,name,email,country from users where id = ?;";
    private static final String SELECT_ALL_USER = "select * from users;";
    private static final String DELETE_USER_SQL = "delete from users where id = ?; ";
    private static final String UPDATE_USER_SQL = "update users set name=?,email=?,country=? where id=?;";

    public UserDAO() {

    }

    protected Connection getConnection() throws ClassNotFoundException, SQLException {
        Connection connection = null;
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(jdbcURL,jdbcUsername,jdbcPassword);
        System.out.println("OK");
        return connection;
    }
    public void insertUser(User user){
        System.out.println(INSERT_USER_SQL);
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_SQL)) {
            preparedStatement.setString(1,user.getName());
            preparedStatement.setString(2,user.getEmail());
            preparedStatement.setString(3,user.getCountry());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public User selectUser(int id){
        User user = null;
        try(Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID)) {
            preparedStatement.setInt(1,id);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                String name = rs.getString("name");
                String email = rs.getString("email");
                String country = rs.getString("country");
                user = new User(id,name,email,country);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return user;
    }
    public List<User> selectAllUsers(){
        List<User> users = new ArrayList<>();
        try(Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USER)) {
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String country = rs.getString("country");
                users.add(new User(id,name,email,country));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public boolean deleteUser(int id) throws SQLException {
        boolean rowDeleted = false;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_SQL)) {
            preparedStatement.setInt(1, id);
            rowDeleted = preparedStatement.executeUpdate() > 0;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return rowDeleted;
    }

    @Override
    public boolean updateUser(User user) throws SQLException {
        boolean rowUpdates = false;
        try(Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_SQL)){
            preparedStatement.setString(1,user.getName());
            preparedStatement.setString(2,user.getEmail());
            preparedStatement.setString(3,user.getCountry());
            preparedStatement.setInt(4,user.getId());
            rowUpdates = preparedStatement.executeUpdate() > 0 ;

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return  rowUpdates;
    }

    @Override
    public User getUserById(int id) throws SQLException, ClassNotFoundException {
        User user = null;
        String select = "call get_user_by_id(?);";
        Connection connection = getConnection();
        CallableStatement callableStatement = connection.prepareCall(select);
        callableStatement.setInt(1,id);
        ResultSet resultSet = callableStatement.executeQuery();
        while (resultSet.next()){
            String name = resultSet.getString("name");
            String email = resultSet.getString("email");
            String country = resultSet.getString("country");
            user = new User(id,name,email,country);
        }
        return user;
    }

    @Override
    public void insertUserStore(User user) throws SQLException, ClassNotFoundException {
        String insert = "call insert_user(?,?,?);";
        Connection connection = getConnection();
        CallableStatement callableStatement = connection.prepareCall(insert);
        callableStatement.setString(1,user.getName());
        callableStatement.setString(2,user.getEmail());
        callableStatement.setString(3,user.getEmail());
        callableStatement.executeUpdate();

    }

    @Override
    public void addUserTransaction(User user, int[] permisions) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement preparedStatement1 = null;
        PreparedStatement preparedStatement2 = null;
        ResultSet resultSet = null;
        connection = getConnection();
        connection.setAutoCommit(false);

        preparedStatement1 = connection.prepareStatement(INSERT_USER_SQL,Statement.RETURN_GENERATED_KEYS);
        preparedStatement1.setString(1, user.getName());

        preparedStatement1.setString(2, user.getEmail());

        preparedStatement1.setString(3, user.getCountry());

        int rowAffected = preparedStatement1.executeUpdate();
        resultSet = preparedStatement1.getGeneratedKeys();
        int userId = 0;
        if(resultSet.next())
            userId = resultSet.getInt(1);
        if(rowAffected == 1) {
            String sqlPivot = "INSERT INTO user_permision(user_id,permision_id) "

                    + "VALUES(?,?)";

            preparedStatement2 = connection.prepareStatement(sqlPivot);

            for (int permisionId : permisions) {

                preparedStatement2.setInt(1, userId);

                preparedStatement2.setInt(2, permisionId);

                preparedStatement2.executeUpdate();

            }
            connection.commit();
        } else
            connection.rollback();
    }
}
