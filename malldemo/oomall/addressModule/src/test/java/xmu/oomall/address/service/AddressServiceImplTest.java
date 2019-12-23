package xmu.oomall.address.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xmu.oomall.address.AddressApplication;
import xmu.oomall.address.domain.Address;
import xmu.oomall.address.service.impl.AddressServiceImpl;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest(classes = AddressApplication.class)
public class AddressServiceImplTest {

    @Autowired
    private AddressServiceImpl addressServiceImpl;

    @Test
    public void getUserAdresslistTest()
    {
        List<Address> addressList= addressServiceImpl.getUserAddresslist(1, 10, 1);
        for(int i=0;i<addressList.size();++i){
            Address address=addressList.get(i);
            System.out.println(address);
        }
    }

    @Test
    public void getAllAddressListTest()
    {
        List<Address> addressList= addressServiceImpl.adminFindUserAddress(1, 10, 1, "");
        for(int i=0;i<addressList.size();++i){
            Address address=addressList.get(i);
            System.out.println(address);
        }
    }

    @Test
    public void getAddressDetailTest()
    {
        Address address= addressServiceImpl.getAddressDetail(100001);
        System.out.println(address);
    }

    @Test
    public void addNewAddressTest()
    {
        Address address=new Address();
        address.setGmtCreate(LocalDateTime.now());
        address.setGmtModified(LocalDateTime.now());
        address.setCountyId(1);
        address.setProvinceId(1);
        address.setCityId(1);
        address.setAddressDetail("某街道");
        address.setPostalCode("123456");
        address.setMobile("19032");
        address.setConsignee("虎大王");
        address.setBeDefault(false);
        address.setUserId(1);
        addressServiceImpl.addNewAddress(address);
        System.out.println(address);
    }

    @Test
    public void updateAddressTest()
    {
        Address address=new Address();
        address.setGmtCreate(LocalDateTime.now());
        address.setGmtModified(LocalDateTime.now());
        address.setCountyId(1);
        address.setProvinceId(1);
        address.setCityId(1);
        address.setAddressDetail("某街道");
        address.setPostalCode("123456");
        address.setMobile("19032");
        address.setConsignee("虎大王");
        address.setBeDefault(false);
        address.setUserId(1);
        addressServiceImpl.updateAddress(address);
        System.out.println(address);
    }

}
