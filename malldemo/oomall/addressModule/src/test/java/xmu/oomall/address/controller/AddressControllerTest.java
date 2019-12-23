package xmu.oomall.address.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import xmu.oomall.address.AddressApplication;
import xmu.oomall.address.domain.Address;
import xmu.oomall.address.domain.AddressPo;
import xmu.oomall.util.JacksonUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = AddressApplication.class)
@Transactional
public class AddressControllerTest {
    @Autowired
    private AddressController addressController;

//    @Autowired
//    private UserAccount userAccount;

    @Value("http://localhost:8805/addresses")
    String url;

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

//    /**
//     * 测试：新增收货地址
//     * @Author: Zhang Yaqing
//     * @Date: 2019/12/12
//     */
//    @Rollback
//    @Test
//    public void addNewAddressTest() throws URISyntaxException {
//
//        AddressPo addressPo=new AddressPo();
//
//        addressPo.setUserId(10);//domain中是String字段
//        addressPo.setCityId(100);
//        addressPo.setProvinceId(22);
//        addressPo.setCountyId(33);
//        addressPo.setAddressDetail("新疆维吾尔自治区乌鲁木齐市乌鲁木齐县");
//        addressPo.setMobile("139463254");//错误的电话号码，只有9位（测试能不能插入成功）
//        addressPo.setPostalCode("830063");
//        addressPo.setConsignee("神无月");
//        addressPo.setBeDefault(true);
//        addressPo.setGmtCreate(LocalDateTime.now());
//        addressPo.setGmtModified(LocalDateTime.now());
//
//        URI uri = new URI(url);
//        HttpHeaders httpHeaders = userAccount.createHeaderWithToken();
//        HttpEntity httpEntity = new HttpEntity<>(addressPo, httpHeaders);
//
//        ResponseEntity<String> responseEntity= restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
//        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
//        String result=responseEntity.getBody();
//        Integer errno= JacksonUtil.parseInteger(result,"errno");
//        AddressPo testAddressPo=JacksonUtil.parseObject(result,"data",AddressPo.class);
//
//        assertEquals(0,errno);
//        assertEquals(addressPo.getUserId(),testAddressPo.getUserId());
//        assertEquals(addressPo.getCountyId(),testAddressPo.getCountyId());
//    }

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
        address.setBeDefault(false);
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

