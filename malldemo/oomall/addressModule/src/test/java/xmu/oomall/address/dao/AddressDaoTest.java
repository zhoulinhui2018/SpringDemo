package xmu.oomall.address.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xmu.oomall.address.AddressApplication;
import xmu.oomall.address.domain.Address;

import java.time.LocalDateTime;
import java.util.List;


@SpringBootTest(classes = AddressApplication.class)
class AddressDaoTest {
    @Autowired
    private AddressDao addressDao;

   @Test
   public void getUserAdresslistTest()
   {
       List<Address> addressList=addressDao.getUserAddressList(1);
       for(int i=0;i<addressList.size();++i){
           Address address=addressList.get(i);
           System.out.println(address);
       }
   }

    @Test
    public void adminFindUserAddressTest()
    {
        List<Address> addressList=addressDao.adminFindUserAddress(1,"张雅晴");
        for(int i=0;i<addressList.size();++i){
            Address address=addressList.get(i);
            System.out.println(address.getId());
        }
    }

    @Test
    public void getAddressDetailTest()
    {
        Address address=addressDao.getAddressDetail(100001);
        System.out.println(address);
    }

    @Test
    public void addNewAddressTest()
    {
        Address address=new Address();
//        address.setGmtCreate(LocalDateTime.now());
//        address.setGmtModified(LocalDateTime.now());
        address.setCountyId(1);
        address.setProvinceId(1);
        address.setCityId(1);
        address.setAddressDetail("某街道");
        address.setPostalCode("123456");
        address.setMobile("19032");
        address.setConsignee("zyq");
        address.setBeDefault(false);
        address.setUserId(1);
        addressDao.addNewAddress(address);
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
        address.setConsignee("zyq");
        address.setBeDefault(false);
        address.setUserId(1);
        address.setId(3);
        addressDao.updateAddress(address);
        System.out.println(address);
    }

}