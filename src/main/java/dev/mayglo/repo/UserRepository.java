package dev.mayglo.repo;

import dev.mayglo.DAO.DatabaseRef;
import dev.mayglo.DAO.MainDAO;
import dev.mayglo.model.User;
import dev.mayglo.util.ConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserRepository implements MainDAO<User>, DatabaseRef {

    private static final Logger logger = LogManager.getLogger(UserRepository.class.getName());

    @Override
    public void create(User user) {
        Connection connection = null;

        try {
            connection = ConnectionFactory.getConnection();
            String sql = String.format("insert into %s (%s, %s, %s, %s, " +
                            "%s, %s, %s) values (?, ?, ?, ?, ?, ?, ?)", USER_TABLE, COL_USER_USERNAME, COL_USER_PASSWORD,
                    COL_USER_EMAIL, COL_USER_GIVEN_NAME, COL_USER_SURNAME, COL_USER_IS_ACTIVE,COL_USER_ROLE_ID);


            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getGiven_name());
            stmt.setString(5, user.getSurname());
            stmt.setBoolean(6, user.getIs_Active());
            stmt.setInt(7, user.getRole_ID());

            stmt.executeUpdate();
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
        } finally {
            if(connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public User get(User user) {
        return null;
    }

    public User getByID(Integer id) {

        User user = null;
        String sql = "select * from ers_user where id = ?";
        Connection connection;

        try{
            connection = ConnectionFactory.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                user = new User(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("given_name"),
                        rs.getString("surname"),
                        rs.getBoolean("is_active"),
                        rs.getInt("role_id")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public void update(User user) {

    }

    public void updateByID(Integer i) {

    }

    @Override
    public void delete(User user) {

    }

    public void delete(String s) {

    }
}