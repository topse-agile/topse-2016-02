package jp.topse.agile2016;

import static org.junit.Assert.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.mockito.Mockito.*;

import jp.topse.agile2016.MainServlet;

public class MainServletTest {

    private MainServlet servlet = null;
    private MockHttpServletRequest request = new MockHttpServletRequest();
    private MockHttpServletResponse response = new MockHttpServletResponse();


    
    private ServletContext getServletContext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Before
    public void setup() {
    	servlet = spy(new MainServlet());
   		ServletContext context = mock(ServletContext.class);
   		String dir = System.getProperty("user.dir") + "/src/main/webapp/WEB-INF/weight.db";
		when(context.getRealPath("/WEB-INF/weight.db")).thenReturn(dir);
   		doReturn(context).when(servlet).getServletContext();
    }
    
    @Test
    public void test_doGetShouldReturnsNoArgsAndResult() throws IOException, ServletException {
        this.servlet.doGet(this.request, this.response);
        
        Calendar c = Calendar.getInstance();

        //フォーマットパターンを指定して表示する
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        
        assertEquals(this.request.getAttribute("date") , sdf.format(c.getTime()));
        assertNull(this.request.getAttribute("weight"));
    }

    @Test
    public void test_doPostReturnsErrorMessageIfweightIsInteger() throws IOException, ServletException {
    	
    	Calendar c = Calendar.getInstance();
        //フォーマットパターンを指定して表示する
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    	
        this.request.addParameter("date", sdf.format(c.getTime()));
        this.request.addParameter("weight", "100");

        this.servlet.doPost(this.request, this.response);

        assertEquals(sdf.format(c.getTime()), this.request.getAttribute("date"));
        assertEquals("100", this.request.getAttribute("weight"));
        assertEquals("", this.request.getAttribute("result"));
    }

    @Test
    public void test_doPostReturnsErrorMessageIfArg1IsNotInteger() throws IOException, ServletException {
    	
    	Calendar c = Calendar.getInstance();
        //フォーマットパターンを指定して表示する
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    	
        this.request.addParameter("date", sdf.format(c.getTime()));
        this.request.addParameter("weight", "100.1");
        
//        String stringDate = sdf.format(c.getTime()).replaceAll("/", "") ;
//        Integer sysToday = Integer.parseInt(stringDate);
//        delete(sysToday);
        
        this.servlet.doPost(this.request, this.response);
        
        assertEquals(sdf.format(c.getTime()), this.request.getAttribute("date"));
        assertEquals("100.1", this.request.getAttribute("weight"));
        assertEquals("", this.request.getAttribute("result"));
    }
    @Test
    public void test_doPostReturnsErrorMessageIfIsweightLessthan2() throws IOException, ServletException {
    	
    	Calendar c = Calendar.getInstance();
        //フォーマットパターンを指定して表示する
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    	
        this.request.addParameter("date", sdf.format(c.getTime()));
        this.request.addParameter("weight", "100.12");
        
//        String stringDate = sdf.format(c.getTime()).replaceAll("/", "") ;
//        Integer sysToday = Integer.parseInt(stringDate);
//        delete(sysToday);
       
        this.servlet.doPost(this.request, this.response);

        assertEquals(sdf.format(c.getTime()), this.request.getAttribute("date"));
        assertEquals("小数点1桁で入力してください。", this.request.getAttribute("result"));
    }
 /*

    @Test
    public void test_doPostReturnsErrorMessageIfArg2IsNotNumber() throws IOException, ServletException {
        this.request.addParameter("arg1", "ABC");
        this.request.addParameter("arg2", "200");

        this.servlet.doPost(this.request, this.response);

        assertEquals("ABC", this.request.getAttribute("arg1"));
        assertEquals("200", this.request.getAttribute("arg2"));
        assertEquals("N/A", this.request.getAttribute("result"));
    }
   
    @Test
    public void test_doPostReturnsAnswerIfBothArgsAreNumber() throws IOException, ServletException {
        this.request.addParameter("arg1", "100");
        this.request.addParameter("arg2", "200");

        this.servlet.doPost(this.request, this.response);

        assertEquals("100", this.request.getAttribute("arg1"));
        assertEquals("200", this.request.getAttribute("arg2"));
        assertEquals("300", this.request.getAttribute("result"));
    }
    */
}
