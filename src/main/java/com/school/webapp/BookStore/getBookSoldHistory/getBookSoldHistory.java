package com.school.webapp.BookStore.getBookSoldHistory;

import com.school.webapp.JDBC.JDBCConnection;
import com.school.webapp.Repository.BookHistory;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class getBookSoldHistory {
    private ArrayList<BookHistory> bookHistories;
    public ArrayList<BookHistory> getHistories(String session, int term, String date){
        Connection connection= JDBCConnection.connector();

        if (connection!=null){
            ResultSet resultSet;
            String QUERY="select * from book_history where datesold=? and  year=? and term=?";
            try {
                PreparedStatement preparedStatement=connection.prepareStatement(QUERY);
                preparedStatement.setString(1,date);
                preparedStatement.setString(2,session);
                preparedStatement.setInt(3,term);
                resultSet=preparedStatement.executeQuery();
                bookHistories=new ArrayList<>();
                while (resultSet.next()){
                    BookHistory bookHistory=new BookHistory();
                    bookHistory.setBuyer(resultSet.getString("buyer"));
                    bookHistory.setAmountsold(resultSet.getString("amountsold"));
                    bookHistory.setAuthor(resultSet.getString("author"));
                    bookHistory.setDate(resultSet.getString("datesold"));
                    bookHistory.setId(resultSet.getInt("id"));
                    bookHistory.setTerm(resultSet.getInt("term"));
                    bookHistory.setSession(resultSet.getString("year"));
                    bookHistory.setTitle(resultSet.getString("title"));
                    bookHistories.add(bookHistory);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
            if (resultSet!=null){
                return bookHistories;
            }else {
                return null;
            }
        }else {
            return null;
        }
    }
}