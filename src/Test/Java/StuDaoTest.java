import com.saosao.dao.StuDao;
import com.saosao.po.Stu;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: liyang117
 * @Date: 2018/4/18 17:25
 * @Description:
 */
public class StuDaoTest extends AbstractTestCase {

    @Autowired
    private StuDao stuDao;

    @Test
    public void selectByPrimaryKey() {
        Stu stu = stuDao.selectByPrimaryKey(1655);
        System.out.println(stu);
    }

    @Test
    public void insertSelective() {
        Stu stu = new Stu();
        stu.setName("daada");
        stu.setScore(10);
        stuDao.insertSelective(stu);
        System.out.println(stu);
    }



}
