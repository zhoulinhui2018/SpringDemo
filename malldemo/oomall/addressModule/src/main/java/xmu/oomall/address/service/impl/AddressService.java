package xmu.oomall.address.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xmu.oomall.address.dao.AddressDao;
import xmu.oomall.address.domain.Address;
import xmu.oomall.address.service.IAddressService;

import java.util.List;

@Transactional
@Service
public class AddressService implements IAddressService {
    @Autowired
    private AddressDao addressDao;

    @Override
    public List<Address> getUserAddresslist(Integer userId) {
        return addressDao.getUserAdresslist(userId);
    }
    @Override
    public Address getAddressDetail(Integer id) {
        return addressDao.getAddressDetail(id);
    }
    @Override
    public boolean deleteAddress(Integer id) {
        return addressDao.deleteAddress(id);
    }
    @Override
    public Address addNewAddress(Address address) {
        return addressDao.addNewAddress(address);
    }
    @Override
    public Address updateAddress(Address address) {
        return addressDao.updateAddress(address);
    }
    @Override
    public List<Address> getAllAddressList() {
        return addressDao.getAllAddressList();
    }
}
