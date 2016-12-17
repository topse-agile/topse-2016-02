package jp.topse.agile2016;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainServlet extends HttpServlet {

    private static final long serialVersionUID = 6961400581681209885L;

    //フォーマットパターンを指定して表示する
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
        Calendar c = Calendar.getInstance();
        
    	request.setAttribute("date", sdf.format(c.getTime()));
        request.setAttribute("weight", null);

        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String date = request.getParameter("date");
        request.setAttribute("date", date);
        String weight = request.getParameter("weight");
        request.setAttribute("weight", weight);

        try {
            Float v = Float.parseFloat(weight);
            String stringDate = date.replaceAll("/", "") ;
            Integer sysToday = Integer.parseInt(stringDate);
            // 100.1はindexOfで3が返る lengthは5が返る
            // 100.12はindexOfで3 lengthは6
            if(weight.indexOf(".") != -1 && (weight.indexOf(".") < weight.length() - 2)){
            	throw new NumberFormatException();
            }
            
            request.setAttribute("result", "");
            delete(sysToday);
            record(sysToday, v);
            
//            request.setAttribute("result", String.valueOf(value));
        } catch (NumberFormatException e) {
            request.setAttribute("result", "小数点1桁で入力してください。");
        }
  
  //        request.setAttribute("history", null);

        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
    
    private void record(Integer sysToday, float v) {
		Connection connection = null;
        Statement statement = null;
        try {
            String dbPath = getServletContext().getRealPath("/WEB-INF/weight.db");

            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
            statement = connection.createStatement();
            String sql = "INSERT INTO weight (date, weight) VALUES (" + sysToday +", " + v + ")";
            statement.execute(sql);
        } catch (ClassNotFoundException e) {
          e.printStackTrace();
        } catch (SQLException e) {
          e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void delete(Integer sysToday) {
		Connection connection = null;
        Statement statement = null;
        try {
            String dbPath = getServletContext().getRealPath("/WEB-INF/weight.db");

            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
            statement = connection.createStatement();
            String sql = "DELETE FROM weight WHERE date =" + sysToday;
            statement.execute(sql);
        } catch (ClassNotFoundException e) {
          e.printStackTrace();
        } catch (SQLException e) {
          e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

  /*  
    private List<String> loadHistory() {
        List<String> history = new LinkedList<String>();

        Connection connection = null;
        Statement statement = null;
        try {
            String dbPath = getServletContext().getRealPath("/WEB-INF/logs.db");

            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
            statement = connection.createStatement();
            String sql = "SELECT * FROM logs ORDER BY id DESC";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
            	int id = resultSet.getInt(1);
            	int v1 = resultSet.getInt(2);
            	int v2 = resultSet.getInt(3);
            	int result = resultSet.getInt(4);
            	history.add("" + id + " : " + v1 + " + " + v2 + " = " + result);
            }
        } catch (ClassNotFoundException e) {
          e.printStackTrace();
        } catch (SQLException e) {
          e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }	
        
        return history;
    }
    */

}
