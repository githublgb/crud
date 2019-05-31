package test.zookeeperTest;

import com.lgb.domain.Person;
import com.lgb.service.PersonService;
import com.lgb.zookeeper.DubboConfigBean;
import com.lgb.zookeeper.DubboServiceUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class zookeeperTest {

    private final Logger LOGGER = LoggerFactory.getLogger(zookeeperTest.class);

    private PersonService service = null;

    @Test
    public void getDubboProxy() {

        DubboConfigBean dubboConfig = new DubboConfigBean();
        dubboConfig.setGroup("lgb_personService");
        dubboConfig.setServiceId("1");//配置表的id,根据配置来获取代理对象
        dubboConfig.setVersion("1.0.0");

        //注册的服务
        service =  DubboServiceUtils.getDubboProxy(PersonService.class, dubboConfig);


        Person person = new Person();
        person.setAddress("孝感");
        person.setAge(4);
        person.setHobby("打球");
        person.setName("李好");


    }


}
