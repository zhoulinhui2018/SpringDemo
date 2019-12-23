package xmu.oomall.address.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;
import xmu.oomall.address.domain.Address;
import xmu.oomall.address.domain.AddressPo;
import xmu.oomall.address.domain.Region;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Address模块
 * @author Zhang Yaqing
 * @date 2019/12/18
 */
@Service
@Mapper
public interface AddressMapper {

    /**
     * 管理员查看用户地址
     * @param  userId true
     * @param  name
     * @author Zhang Yaqing
     * @return List<Address>
     * @date 2019/12/18
     */
    List<Address> adminFindUserAddress(Integer userId, String name);

    /**
     * 管理员查看用户地址
     * @param  userId true, name true
     * @author Zhang Yaqing
     * @return List<Address>
     * @date 2019/12/18
     */
    List<Address> getUserAddressList(Integer userId);

    /**
     * 新增地址
     * @param  address true
     * @author Zhang Yaqing
     * @return boolean
     * @date 2019/12/18
     */
    boolean addNewAddress(AddressPo address);

    /**
     * 更新地址
     * @param  address true
     * @author Zhang Yaqing
     * @return boolean
     * @date 2019/12/18
     */
    boolean updateAddress(AddressPo address);

    /**
     * 查看地址详情
     * @param  id true
     * @author Zhang Yaqing
     * @return Address
     * @date 2019/12/18
     */
    Address getAddressDetail(Integer id);

    /**
     * 查看地区
     * @param id true
     * @author Zhang Yaqing
     * @return Region
     * @date 2019/12/18
     */
    Region getRegion(Integer id);

    /**
     * 设置默认地址
     * @param  id true
     * @author Zhang Yaqing
     * @return void
     * @date 2019/12/18
     */
    void setDefaultAddress(Integer id);
}