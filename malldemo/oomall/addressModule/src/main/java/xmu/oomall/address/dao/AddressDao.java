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

    public List<Address> getUserAdresslist(Integer userId) {
        return addressMapper.getUserAddressList(userId);
    }

    public Address getAddressDetail(Integer id) {
        return addressMapper.getAddressDetail(id);
    }

    public boolean deleteAddress(Integer id) {
        return addressMapper.deleteAddress(id);
    }

    public AddressPo addNewAddress(AddressPo addressPo) {
        addressPo.setGmtCreate(LocalDateTime.now());
        addressPo.setGmtModified(LocalDateTime.now());
        addressPo.setBeDeleted(false);
        boolean resultMsg=addressMapper.addNewAddress(addressPo);
        if(resultMsg){
            return addressPo;
        }
        else{
            return null;
        }

    }

    public AddressPo updateAddress(AddressPo addressPo) {
        addressPo.setGmtModified(LocalDateTime.now());
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
