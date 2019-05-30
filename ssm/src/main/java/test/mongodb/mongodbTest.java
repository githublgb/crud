package test.mongodb;

import com.lgb.domain.Person;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Order;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import test.zookeeperTest.zookeeperTest;

import java.util.List;


import static com.alibaba.dubbo.common.utils.LogUtil.printList;
import static org.springframework.data.mongodb.core.query.Criteria.where;

public class mongodbTest {
    private final Logger LOGGER = LoggerFactory.getLogger(mongodbTest.class);


    private MongoTemplate mongoTemplate;

    @Before
    public void setMongoTemplate() {
        ClassPathXmlApplicationContext cxt = new ClassPathXmlApplicationContext("mongodb.xml");
        mongoTemplate = (MongoTemplate) cxt.getBean("mongoTemplate");
    }


    //插曲数据库
    @Test
    public void testAddPerson() {

        Person ls = new Person();

        for (int i = 1; i < 6; i++) {
            // ls.setName("李好不");
            ls.setAddress("湖北孝感孝昌" + i);
            ls.setHobby("乒乓球and篮球and羽毛球" + i);
            ls.setAge(i + 20);
          
            // 插入数据
            mongoTemplate.insert(ls, "test");
        }

    }


    /**
     * 测试查询
     */
    @Test
    public void testQueryPerson() {
        // 查询主要用到Query和Criteria两个对象
        Query query = new Query();
        Criteria criteria = where("age").gt(22);    // 大于

        // criteria.and("name").is("cuichongfei");等于
        // List<String> interests = new ArrayList<String>();
        // interests.add("study");
        // interests.add("linux");
        // criteria.and("interest").in(interests); in查询
        // criteria.and("home.address").is("henan"); 内嵌文档查询
        // criteria.and("").exists(false); 列存在
        // criteria.and("").lte(); 小于等于
        // criteria.and("").regex(""); 正则表达式
        // criteria.and("").ne(""); 不等于
        // 多条件查询
        // criteria.orOperator(Criteria.where("key1").is("0"),Criteria.where("key1").is(null));

        query.addCriteria(criteria);
        List<Person> personList1 = mongoTemplate.find(query, Person.class, "test");
        System.out.println(personList1.get(0).toString());


        LOGGER.debug("按照age排序查询的结果=================================");
        //排序查询sort方法,按照age降序排列
        Query query1 = new Query();
        query1.addCriteria(criteria);
        query1.with(new Sort("age"));
        List<Person> personList2 = mongoTemplate.find(query1, Person.class, "test");
        for (Person person : personList2) {
            System.out.println(person.toString());
        }

        LOGGER.debug("指定字段查询,只查询age和name两个字段其他字段为空=================================");
        // 指定字段查询,只查询age和name两个字段
        Query query2 = new Query();
        query2.addCriteria(criteria);
        query2.fields().include("age").include("name");
        List<Person> personList3 = mongoTemplate.find(query2, Person.class, "test");
        for (Person person : personList3) {
            System.out.println(person.toString());
        }

        LOGGER.debug("分页查询=================================");
        Query query3 = new Query();
        query3.addCriteria(criteria);
        query3.skip(1).limit(2);
        List<Person> personList4 = mongoTemplate.find(query3, Person.class, "test");
        for (Person person : personList4) {
            System.out.println(person.toString());
        }


        // 查询所有
        // printList(mongoTemplate.findAll(User.class));

        // 统计数据量
        // System.out.println(mongoTemplate.count(query, User.class));

    }


    //更新数据集
    @Test
    public void testUpdatePerson() {
        // update(query,update,class)
        // Query query:需要更新哪些用户,查询参数
        // Update update:操作符,需要对数据做什么更新
        // Class class:实体类

        // 更新age大于24的用户信息
        Query query = new Query();
        query.addCriteria(where("age").gt(24));


        Update update = new Update();
        // age值加2
        update.inc("age", 6);
        // update.set("name", "zhangsan"); 直接赋值
        // update.unset("name"); 删去字段
        // update.push("interest", "java"); 把java追加到interest里面,interest一定得是数组
        // update.pushAll("interest", new String[]{".net","mq"})
        // 用法同push,只是pushAll一定可以追加多个值到一个数组字段内
        // update.pull("interest", "study"); 作用和push相反,从interest字段中删除一个等于value的值
        // update.pullAll("interest", new String[]{"sing","dota"})作用和pushAll相反
        // update.addToSet("interest", "study") 把一个值添加到数组字段中,而且只有当这个值不在数组内的时候才增加
        // update.rename("oldName", "newName") 字段重命名

        // 只更新第一条记录,age加2,name值更新为zhangsan
        mongoTemplate.updateFirst(query, new Update().inc("age", 2).set("name", "zhangsan"), Person.class, "test");

        // 批量更新,更新所有查询到的数据
        mongoTemplate.updateMulti(query, update, Person.class, "test");

    }

    //删除
    @Test
    public void testRemovePerson() {
        Query query = new Query();
        Criteria criteria = where("age").gt(28);
        query.addCriteria(criteria);
        mongoTemplate.remove(query, Person.class, "test");


    }

}



