package xmu.oomall.address.service;

import org.springframework.stereotype.Service;
import xmu.oomall.address.domain.Address;
import java.util.List;

@Service
public interface IAddressService {
    /**
     * 查看某用户的收货地址列表
     * @param userId 用户ID
     * @return 收货地址列表
     * @Author: Zhang Yaqing
     * @Date: 2019/12/12
     */
    public List<Address> getUserAddresslist(Integer userId);

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
    public Address addNewAddress(Address address);

    /**
     * 删除收货地址
     * @param id
     * @return 用boolean表示删除操作是否成功
     * @Author: Zhang Yaqing
     * @Date: 2019/12/12
     */
    public boolean deleteAddress(Integer id);

    /**
     * 更新收货地址
     * @param id  地址id
     * @param address 用户收货地址
     * @return 更新操作结果
     * @Author: Zhang Yaqing
     * @Date: 2019/12/12
     */
    public Address updateAddress(Integer id, Address address);

    /**
     * 管理员获取全部地址列表
     * @param
     * @return 全部地址列表
     * @Author: Zhang Yaqing
     * @Date: 2019/12/12
     */
    public List<Address> getAllAddressList();


}
