package dev.mayglo.repo;

import dev.mayglo.DAO.MainDAO;
import dev.mayglo.model.User;
import dev.mayglo.util.ConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class UserRepository implements MainDAO<User> {

    private static final Logger logger = LogManager.getLogger(UserRepository.class.getName());

    @Override
    public void create(User user) {
        Connection connection = null;

        try {
            connection = ConnectionFactory.getConnection();
            String sql = "insert into ERS_USER (username, password, email, given_name, " +
                    "surname, is_active, role_id) values (?, ?, ?, ?, ?, ?, ?)";


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

    public User getByID(Integer i) {
        return null;
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