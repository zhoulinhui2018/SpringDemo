package xmu.oomall.address.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import xmu.oomall.address.domain.Address;
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

    public Address addNewAddress(Address address) {
        address.setGmtCreate(LocalDateTime.now());
        address.setGmtModified(LocalDateTime.now());
        boolean resultMsg=addressMapper.addNewAddress(address);
        if(resultMsg)
            return address;
        else
            return null;

    }

    public Address updateAddress(Address address) {
        address.setGmtModified(LocalDateTime.now());
        boolean result=addressMapper.updateAddress(address);
        if(result)
            return address;
        else
            return null;
    }

    public List<Address> getAllAddressList() {
        List<Address> allAddresses = addressMapper.getAllAddressList();
        return allAddresses;
    }
}
