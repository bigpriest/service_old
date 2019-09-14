package makert.makert_demo.dao;

import makert.makert_demo.entity.Health;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HealthMapper {
    //查找老人每日健康状况
    public List<Health> findoldhealth(@Param("oldid") int oldid, @Param("startRow")int startRow, @Param("endRow")int endRow);
    //添加一条检查记录
    public void addhealth(Health health);
    //根据id回显信息
    public Health findbyid(int id);
    //删除
    public void delhealth(int id);
    //编辑
    public void edithealth(Health health);
    //查找总记录数
    public int findhealthcount(int oldid);
}
