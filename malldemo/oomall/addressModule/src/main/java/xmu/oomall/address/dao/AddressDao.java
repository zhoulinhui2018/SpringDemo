package xmu.oomall.address.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import xmu.oomall.address.domain.Address;
import xmu.oomall.address.domain.AddressPo;
import xmu.oomall.address.domain.Region;
import xmu.oomall.address.mapper.AddressMapper;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Address模块Dao层
 * @author Zhang Yaqing
 * @date 2019/12/18
 */
@Repository
public class AddressDao {
    private final AddressMapper addressMapper;

    public AddressDao(AddressMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    /**
     * 查看用户地址
     * @param userId true
     * @author Zhang Yaqing
     * @date 2019/12/18
     */
    public List<Address> getUserAddressList(Integer userId) {
        return addressMapper.getUserAddressList(userId);
    }

    /**
     * 查看地址详情
     * @param id true
     * @author Zhang Yaqing
     * @date 2019/12/18
     */
    public Address getAddressDetail(Integer id) {
        return addressMapper.getAddressDetail(id);
    }

    /**
     * 新增地址
     * @param  addressPo true
     * @author Zhang Yaqing
     * @return AddressPo
     * @date 2019/12/18
     */
    public AddressPo addNewAddress(AddressPo addressPo) {
        addressPo.setGmtCreate(LocalDateTime.now());
        addressPo.setGmtModified(LocalDateTime.now());
        addressPo.setBeDeleted(false);

        System.out.println(addressPo);
        if(addressPo.getBeDefault()){
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

    /**
     * 更换默认地址
     * @param  userId true
     * @author Zhang Yaqing
     * @date 2019/12/18
     */
    private void changeDefaultAddress(Integer userId){
        List<Address> addressList= getUserAddressList(userId);
        for (Address address : addressList) {
            AddressPo outAddressPo = getAddressDetail(address.getId());
            if (outAddressPo.getBeDefault()) {
                outAddressPo.setBeDefault(false);
                updateAddress(outAddressPo);
            }
        }
    }
    public AddressPo updateAddress(AddressPo addressPo) {
        addressPo.setGmtModified(LocalDateTime.now());
        //如果是默认收货地址，要把该用户原来的默认地址isDefault设为0
        if(addressPo.getBeDefault()){
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
        return addressMapper.adminFindUserAddress(userId,name);
    }

    public Region getRegion(Integer id) {
        return addressMapper.getRegion(id);
    }
}
