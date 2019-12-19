package xmu.oomall.address.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import xmu.oomall.address.domain.Address;
import xmu.oomall.address.domain.AddressPo;
import xmu.oomall.address.domain.Region;
import xmu.oomall.address.mapper.AddressMapper;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class AddressDao {
    @Autowired
    private AddressMapper addressMapper;

    public List<Address> getUserAddresslist(Integer userId) {
        return addressMapper.getUserAddressList(userId);
    }

    public Address getAddressDetail(Integer id) {
        return addressMapper.getAddressDetail(id);
    }

    public AddressPo addNewAddress(AddressPo addressPo) {
        addressPo.setGmtCreate(LocalDateTime.now());
        addressPo.setGmtModified(LocalDateTime.now());
        addressPo.setBeDeleted(false);

        System.out.println(addressPo);
        //如果是默认收货地址，要把该用户原来的默认地址isDefault设为0
        if(addressPo.getBeDefault()==true){
            changeDefaultAddress(addressPo.getUserId());
        }
        boolean resultMsg=addressMapper.addNewAddress(addressPo);
        if(resultMsg){
            return addressPo;
        }
        else{
            return null;
        }
    }

    private void changeDefaultAddress(Integer userId){
        List<Address> addressList=getUserAddresslist(userId);
        for(int i=0;i<addressList.size();i++){
            AddressPo outAddressPo=getAddressDetail(addressList.get(i).getId());
            if(outAddressPo.getBeDefault()==true){
                outAddressPo.setBeDefault(false);
                updateAddress(outAddressPo);
            }
        }
    }
    public AddressPo updateAddress(AddressPo addressPo) {
        addressPo.setGmtModified(LocalDateTime.now());
        //如果是默认收货地址，要把该用户原来的默认地址isDefault设为0
        if(addressPo.getBeDefault()==true){
            changeDefaultAddress(addressPo.getUserId());
        }
        boolean result=addressMapper.updateAddress(addressPo);
        if(result){
            return addressPo;
        }
        else{
            return null;
        }
    }

    public List<Address> adminFindUserAddress(Integer userId, String name) {
        List<Address> addresses = addressMapper.adminFindUserAddress(userId,name);
        return addresses;
    }

    public Region getRegion(Integer id) {
        return addressMapper.getRegion(id);
    }
}
