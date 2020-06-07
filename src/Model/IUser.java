package Model;


import java.sql.SQLException;
import java.util.List;

public interface IUser {
   void insertUser(User user) throws SQLException;
   User selectUser(int id);
   List<User> selectAllUsers();
   boolean deleteUser(int id) throws SQLException;
   boolean updateUser(User user) throws SQLException;
   User getUserById(int id) throws SQLException, ClassNotFoundException;
   void insertUserStore(User user) throws SQLException, ClassNotFoundException;
   void addUserTransaction(User user, int[] permision) throws SQLException, ClassNotFoundException;
}
