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

    public List<Address> adminFindUserAddress(Integer userId, String name);

    public List<Address> getUserAddressList(Integer userId);

    public boolean addNewAddress(AddressPo address);

    public boolean updateAddress(AddressPo address);

    public Address getAddressDetail(Integer id);

    public Region getRegion(Integer id);

    void setDefaultAddress(Integer id);
}