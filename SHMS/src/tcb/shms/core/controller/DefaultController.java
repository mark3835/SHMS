package tcb.shms.core.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class DefaultController {
	
	static Connection connection;
	static int times = 0;
	static {
		times++;
		System.out.println(times);
		try {
			Class.forName("org.h2.Driver");
			connection = DriverManager.getConnection("jdbc:h2:~/test;DB_CLOSE_DELAY=-1");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/tttt", method=RequestMethod.GET)
	public String showTTTT(Model model){
		model.addAttribute("msg", "Fuck you all!");//
		return "index";
	}
	
	public static boolean firstTime = true;
	
	@RequestMapping(value="/testJdbc", method=RequestMethod.GET)
	public String testJdbc(Model model) throws ClassNotFoundException, SQLException{
				
        Statement stmt = null;
        try {
            connection.setAutoCommit(false);
            stmt = connection.createStatement();
            if(firstTime) {
            	firstTime = false;
//            	stmt.execute("CREATE TABLE PERSON(id int primary key, name varchar(255))");
                stmt.execute("INSERT INTO PERSON(id, name) VALUES(1, 'Anju')");
                stmt.execute("INSERT INTO PERSON(id, name) VALUES(2, 'Sonia')");
                stmt.execute("INSERT INTO PERSON(id, name) VALUES(3, 'Asha')");               
            }
            
            ResultSet rs = stmt.executeQuery("select * from PERSON");
            System.out.println("H2 In-Memory Database inserted through Statement");
            while (rs.next()) {
                System.out.println("Id " + rs.getInt("id") + " Name " + rs.getString("name"));
            }

//            stmt.execute("DROP TABLE PERSON");
            stmt.close();
            connection.commit();
        } catch (SQLException e) {
            System.out.println("Exception Message " + e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            connection.close();
        }
        
		model.addAttribute("msg", "Fuck you all!");//
		return "index";
	}
}	