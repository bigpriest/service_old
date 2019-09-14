package makert.makert_demo.dao;

import makert.makert_demo.entity.Activity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

//日常活动
public interface ActivityMapper {
    public List<Activity> activeinfo(@Param("dailyactive") String dailyactive, @Param("startRow") int startRow, @Param("endRow") int endRow);
    public int findactivecount(@Param("dailyactive") String dailyactive);
    public void addactive(Activity activity);
    public void delactive(int id);
    public Activity findbyactiveid(int id);
    public void editactive(Activity activity);
}
