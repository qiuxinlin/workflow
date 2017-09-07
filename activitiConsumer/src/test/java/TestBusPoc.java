import com.alibaba.dubbo.config.annotation.Reference;
import com.scxys.activiti.bean.businessBean.BusPoc;
import com.scxys.activiti.service.BusPocService;

/**
 * @Author: qiuxinlin
 * @Dercription:
 * @Date: 14:08 2017/9/6
 */
public class TestBusPoc {
    @Reference(version = "1.0.0")
    static BusPocService busPocService;
    public static void main(String[] args) {
        Long i=9L;
        BusPoc busPoc=busPocService.findOne(i);
        System.out.println(busPoc.getDepartment());
    }
}
