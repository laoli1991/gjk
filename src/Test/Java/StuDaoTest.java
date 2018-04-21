import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.saosao.dao.StuDao;
import com.saosao.po.Stu;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: liyang117
 * @Date: 2018/4/18 17:25
 * @Description:
 */
public class StuDaoTest extends AbstractTestCase {

    private Logger logger = Logger.getLogger(StuDaoTest.class);

    @Autowired
    private StuDao stuDao;

    @Test
    public void selectByPrimaryKey() {
        Stu stu = stuDao.selectByPrimaryKey(1655);
        logger.info("ssdd");
        System.out.println(stu);

        PageHelper.startPage(2, 10);
        List<Stu> stus = stuDao.selectList();
        System.out.println(stus);
    }

    @Test
    public void insertSelective() {
        long s = System.nanoTime() ;
        for(int i = 1 ; i <= 10000 ; i++){
            Stu stu = new Stu();
            stu.setName(i+"s");
            stu.setScore(i);
            stuDao.insert(stu);
        }
        System.out.println(System.nanoTime() - s);

        s = System.nanoTime() ;
        List<Stu> stus = Lists.newArrayList() ;
        for(int i = 1 ; i <= 10000 ; i++){
            Stu stu = new Stu();
            stu.setName(i+"s");
            stu.setScore(i);
            stus.add(stu) ;
        }
        stuDao.insertBatch(stus) ;
        System.out.println(System.nanoTime() - s);

    }



}
