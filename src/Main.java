
import com.pyshankov.lab1.dao.UserDao;
import com.pyshankov.lab1.domain.User;
import com.pyshankov.lab1.service.UserService;
import com.pyshankov.lab1.ui.EntranceDesktop;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;



/**
 * Created by pyshankov on 25.01.2016.
 */
public class Main {



    public static void main(String[] args) throws Exception {

        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");

        EntranceDesktop entranceDesktop = (EntranceDesktop) context.getBean("entrance");

        UserDao dao = (UserDao) context.getBean("userDaoRepository");

//        dao.addUser(new User(1,"Pavlo",false,"1234", User.Role.ADMIN));

        entranceDesktop.showMenu();


    }
}
