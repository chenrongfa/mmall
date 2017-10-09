import com.yy.mall.dao.UserMapper;
import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by chenrongfa on 2017/10/9.
 * QQ:952786280
 * email:18720979339@163.com
 * company:逸臣有限公司
 * function:
 */
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring*.xml"})
public class DataSourceTest {
  @Autowired
	BasicDataSource basicDataSource;

  @Autowired
	UserMapper userMapper;
	@Test
	public void testData(){

		System.out.println(userMapper.selectByPrimaryKey(1).toString());
	}
}
