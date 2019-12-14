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
       List<Address> addressList=addressDao.getUserAdresslist(1);
       for(int i=0;i<addressList.size();++i){
           Address address=addressList.get(i);
           System.out.println(address);
       }
   }

    @Test
    public void getAllAddressListTest()
    {
        List<Address> addressList=addressDao.getAllAddressList();
        for(int i=0;i<addressList.size();++i){
            Address address=addressList.get(i);
            System.out.println(address);
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
        address.setGmtCreate(LocalDateTime.now());
        address.setGmtModified(LocalDateTime.now());
        address.setCounty("赵县");
        address.setProvince("河北省");
        address.setCity("石家庄市");
        address.setAddressDetail("某街道");
        address.setPostalCode("123456");
        address.setMobile("19032");
        address.setBeDefault(0);
        address.setUserId(1);
        address.setCityId(0);
        addressDao.addNewAddress(address);
        System.out.println(address);
    }

    @Test
    public void updateAddressTest()
    {
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
        addressDao.updateAddress(address);
        System.out.println(address);
    }

    @Test
    public void deleteAddressTest()
    {
        boolean result=addressDao.deleteAddress(100005);
        System.out.println(result);
    }
}