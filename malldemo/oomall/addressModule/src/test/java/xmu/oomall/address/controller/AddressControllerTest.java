package xmu.oomall.address.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import xmu.oomall.address.AddressApplication;
import xmu.oomall.address.domain.Address;
import xmu.oomall.address.domain.AddressPo;

import java.time.LocalDateTime;

@SpringBootTest(classes = AddressApplication.class)
@Transactional
public class AddressControllerTest {
    @Autowired
    private AddressController addressController;

    /**
     * 测试：获取某用户的收货地址列表
     * @Author: Zhang Yaqing
     * @Date: 2019/12/12
     */
    @Test
    public void getUserAddresslistTest(){
//        Object addressObject=addressController.getUserAddressList(1);
//        System.out.println(addressObject);
    }

    /**
     * 测试：获取收货地址详情
     * @Author: Zhang Yaqing
     * @Date: 2019/12/12
     */
    @Test
    public void getAddressDetailTest(){
        Object addressObject=addressController.getAddressDetail(100001);
        System.out.println(addressObject);
    }

    /**
     * 测试：新增收货地址
     * @Author: Zhang Yaqing
     * @Date: 2019/12/12
     */
    @Rollback
    @Test
    public void addNewAddressTest(){
        AddressPo addressPo=new AddressPo();
        addressPo.setCountyId(1);
        addressPo.setProvinceId(2);
        addressPo.setCityId(5);
        addressPo.setAddressDetail("某街道");
        addressPo.setPostalCode("123456");
        addressPo.setMobile("19032");
        addressPo.setBeDefault(0);
        addressPo.setUserId(1);
        addressPo.setConsignee("张雅晴");

        Object addressObject=addressController.addNewAddress(addressPo);
        System.out.println(addressObject);
    }

    /**
     * 测试：删除收货地址
     * @Author: Zhang Yaqing
     * @Date: 2019/12/12
     */
    @Rollback
    @Test
    public void deleteAddressTest(){
        Object addressObject=addressController.deleteAddress(100001);
        System.out.println(addressObject);
    }

    /**
     * 测试：更新收货地址
     * @Author: Zhang Yaqing
     * @Date: 2019/12/12
     */
    @Rollback
    @Test
    public void updateAddressTest(){
        Address address=new Address();
        address.setGmtCreate(LocalDateTime.now());
        address.setCounty("赵县");
        address.setProvince("河北省");
        address.setCity("石家庄市");
        address.setAddressDetail("某街道");
        address.setPostalCode("123456");
        address.setMobile("19032");
        address.setBeDefault(0);
        address.setUserId(1);
        address.setCityId(0);
        Object addressObject=addressController.updateAddress(100001,address);
        System.out.println(addressObject);
    }


    /**
     * 测试：获取全部地址列表
     * @Author: Zhang Yaqing
     * @Date: 2019/12/12
     */
    @Test
    public void adminFindUserAddressTest(){
//        Object addressObject=addressController.adminFindUserAddress(1,"",1,10);
//        System.out.println(addressObject);

    }

}

