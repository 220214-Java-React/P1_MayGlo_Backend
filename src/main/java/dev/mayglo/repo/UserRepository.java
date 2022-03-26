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
import java.util.ArrayList;
import java.util.List;

public class UserRepository implements MainDAO<User>, DatabaseRef {

    private static final Logger logger = LogManager.getLogger(UserRepository.class.getName());

    @Override
    public void create(User user) {

        try (Connection connection = ConnectionFactory.getConnection()) {

            String sql = String.format("insert into %s (%s, %s, %s, %s, %s, %s, %s)" +
                            " values (?, ?, ?, ?, ?, ?, ?)",
                    USER_TABLE, COL_USER_USERNAME, COL_USER_PASSWORD, COL_USER_EMAIL,
                    COL_USER_GIVEN_NAME, COL_USER_SURNAME, COL_USER_IS_ACTIVE, COL_USER_ROLE_ID);


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
        }
    }

    @Override
    public User get(User user) {
        return null;
    }

    public User getByID(Integer id) {
        User user = null;
        String sql = "select * from " + USER_TABLE + " where " + COL_USER_ID + " = ?";

        try (Connection connection = ConnectionFactory.getConnection()) {

            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                user = new User(
                        resultSet.getInt(COL_USER_ID),
                        resultSet.getString(COL_USER_USERNAME),
                        resultSet.getString(COL_USER_PASSWORD),
                        resultSet.getString(COL_USER_EMAIL),
                        resultSet.getString(COL_USER_GIVEN_NAME),
                        resultSet.getString(COL_USER_SURNAME),
                        resultSet.getBoolean(COL_USER_IS_ACTIVE),
                        resultSet.getInt(COL_USER_ROLE_ID));
                logger.info("Retrieved user.");
            }
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        String sql = String.format("select * from %s", USER_TABLE);
        try (Connection connection = ConnectionFactory.getConnection()) {

            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                users.add(new User(
                        resultSet.getInt(COL_USER_ID),
                        resultSet.getString(COL_USER_USERNAME),
                        resultSet.getString(COL_USER_PASSWORD),
                        resultSet.getString(COL_USER_EMAIL),
                        resultSet.getString(COL_USER_GIVEN_NAME),
                        resultSet.getString(COL_USER_SURNAME),
                        resultSet.getBoolean(COL_USER_IS_ACTIVE),
                        resultSet.getInt(COL_USER_ROLE_ID)));
            }
            logger.info("Retrieved user list.");

        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
            e.printStackTrace();
        }

        return users;
    }

    @Override
    public void update(User user) {

        try (Connection connection = ConnectionFactory.getConnection()) {

            String sql = "update " + USER_TABLE + " set " + COL_USER_USERNAME + " = ?, " +
                    COL_USER_PASSWORD + " = ?, " + COL_USER_EMAIL + " = ?, " + COL_USER_GIVEN_NAME +
                    " = ?, " + COL_USER_SURNAME + " = ?, " + COL_USER_IS_ACTIVE + " = ?, "

                    + COL_USER_ROLE_ID + " = ? where " + COL_USER_ID + " = ?";


            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getGiven_name());
            stmt.setString(5, user.getSurname());
            stmt.setBoolean(6, user.getIs_Active());
            stmt.setInt(7, user.getRole_ID());

            stmt.setInt(8, user.getID());
            stmt.executeUpdate();
            logger.info("Updated user.");

        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
        }
    }

    // May not be needed. Use get -> delete(User user)
    public void updateByID(Integer id) {
    }

    @Override
    public void delete(User user) {

        try (Connection connection = ConnectionFactory.getConnection()) {

            String sql = "delete from " + USER_TABLE + " where " + COL_USER_ID + " = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, user.getID());
            stmt.executeUpdate();

            logger.info("User ID #" + user.getID() + " deleted.");

        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
        }
    }

    // May not be needed
    public void delete(String s) {

    }
}