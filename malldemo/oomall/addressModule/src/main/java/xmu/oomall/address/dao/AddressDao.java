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
        return addressMapper.addNewAddress(address);
    }

    public Address updateAddress(Integer id,Address address) {
        address.setGmtModified(LocalDateTime.now());
        return addressMapper.updateAddress(id,address);
    }

    public List<Address> getAllAddressList() {
        List<Address> allAddresses = addressMapper.getAllAddressList();
        return allAddresses;
    }
}
