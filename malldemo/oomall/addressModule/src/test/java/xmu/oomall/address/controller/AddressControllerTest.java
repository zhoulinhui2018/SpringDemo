package xmu.oomall.address.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import xmu.oomall.address.AddressApplication;
import xmu.oomall.address.domain.Address;

import java.time.LocalDateTime;
import java.util.List;

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
        Object addressObject=addressController.getUserAddressList(1);
        System.out.println(addressObject);
//        List<Address> addressList = addressObject["data"];
//        for (int i = 0; i < addressList.size(); i++) {
//            Address address = addressList.get(i);
//            System.out.println(address);
//        }
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
//        Address address =((Address)addressObject);
//        System.out.println(address);
    }
//
//    /**
//     * 测试：测试地址是否合法，比如是否有country/province等
//     * @Author: Zhang Yaqing
//     * @Date: 2019/12/12
//     */
//    @Test
//    private void validateTest(){
//        Address address=new Address();
//        address.setCounty(" ");
//        address.setProvince("河北省");
//        address.setCity(" ");
//        address.setAddressDetail("某街道");
//        address.setPostalCode("123456");
//        address.setMobile("19032");
//        address.setBeDefault(1);
//        address.setUserId(10000);
//        address.setGmtCreate(LocalDateTime.now());
//        address.setGmtModified(LocalDateTime.now());
//
//        Object addressObject=addressController.validate(address);
//        System.out.println(addressObject);
//    }

    /**
     * 测试：新增收货地址
     * @Author: Zhang Yaqing
     * @Date: 2019/12/12
     */
    @Rollback
    @Test
    public void addNewAddressTest(){
        Address address=new Address();
        address.setCounty(" ");
        address.setProvince("河北省");
        address.setCity(" ");
        address.setAddressDetail("某街道");
        address.setPostalCode("123456");
        address.setMobile("19032");
        address.setBeDefault(0);
        address.setUserId(1);
        address.setGmtCreate(LocalDateTime.now());
        address.setGmtModified(LocalDateTime.now());

        Object addressObject=addressController.addNewAddress(address);
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
    public void allAddressList(){
        Object addressObject=addressController.allAddressList();
        System.out.println(addressObject);
//        List<Address> addressList= ((List<Address>) addressObject);
//        for (int i = 0; i < addressList.size(); i++) {
//            Address address = addressList.get(i);
//            System.out.println(address);
//        }
    }

}

