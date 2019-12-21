package xmu.oomall.address.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;
import xmu.oomall.address.domain.Address;
import xmu.oomall.address.domain.AddressPo;
import xmu.oomall.address.domain.Region;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Mapper
public interface AddressMapper {

    List<Address> adminFindUserAddress(Integer userId, String name);

    List<Address> getUserAddressList(Integer userId);

    boolean addNewAddress(AddressPo address);

    boolean updateAddress(AddressPo address);

    Address getAddressDetail(Integer id);

    Region getRegion(Integer id);

    void setDefaultAddress(Integer id);
}