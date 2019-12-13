package xmu.oomall.address.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;
import xmu.oomall.address.domain.Address;

import java.util.List;

@Service
@Mapper
public interface AddressMapper {

    public List<Address> getAllAddressList();

    public List<Address> getUserAddressList(Integer userId);

    public Address addNewAddress(Address address);

    public boolean deleteAddress(Integer id);

    public Address updateAddress(Integer id,Address address);

    public Address getAddressDetail(Integer id);

}