package makert.makert_demo.dao;

import makert.makert_demo.entity.Charge;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChargeMapper {
    public List<Charge> findpayinfo(@Param("oldid") int oldid, @Param("ispay") int ispay, @Param("startRow") int startRow, @Param("endRow") int endRow);
    public int findpaycount(@Param("oldid") int oldid, @Param("ispay") int ispay);
    public void delcharge(int id);
    public void addchargesave(Charge charge);
    public Charge findchargebyid(int id);
    public void editchargesave(Charge charge);
}
