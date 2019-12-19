package xmu.oomall.address.service;

import org.springframework.stereotype.Service;
import xmu.oomall.address.domain.Address;
import xmu.oomall.address.domain.AddressPo;
import xmu.oomall.address.domain.Log;
import xmu.oomall.address.domain.Region;

import java.util.List;

@Service
public interface IAddressService {
    void log(Log log);

    /**
     * 查看某用户的收货地址列表
     *
     * @param page
     * @param limit
     * @param userId 用户ID
     * @return 收货地址列表
     * @Author: Zhang Yaqing
     * @Date: 2019/12/12
     */
    public List<Address> getUserAddresslist(Integer page, Integer limit, Integer userId);

    /**
     * 收货地址详情
     * @param id 收货地址ID
     * @return 收货地址详情
     * @Author: Zhang Yaqing
     * @Date: 2019/12/12
     */
    public Address getAddressDetail(Integer id);

    /**
     * 新增收货地址
     * @param address 用户收货地址
     * @return 新增操作结果
     * @Author: Zhang Yaqing
     * @Date: 2019/12/12
     */
    public AddressPo addNewAddress(AddressPo address);

    /**
     * 更新收货地址
     * @param address 用户收货地址
     * @return 更新操作结果
     * @Author: Zhang Yaqing
     * @Date: 2019/12/12
     */
    public AddressPo updateAddress(AddressPo address);

    /**
     * 管理员获取全部地址列表
     * @param
     * @param page
     * @param limit
     * @param userId
     * @param name
     * @return 全部地址列表
     * @Author: Zhang Yaqing
     * @Date: 2019/12/12
     */
    public List<Address> adminFindUserAddress(Integer page, Integer limit, Integer userId, String name);


    Region getRegion(Integer id);
}
