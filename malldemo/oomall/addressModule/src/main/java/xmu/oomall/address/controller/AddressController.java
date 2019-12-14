package xmu.oomall.address.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xmu.oomall.address.domain.Address;
import xmu.oomall.address.service.impl.AddressService;
import xmu.oomall.util.ResponseUtil;
import java.util.List;

@RestController
@RequestMapping("")
@Validated
public class AddressController {
    @Autowired
    private AddressService addressService;

    /**
     * 查看某用户的收货地址列表
     * @param userId 用户ID
     * @return 收货地址列表
     * @Author: Zhang Yaqing
     * @Date: 2019/12/12
     */
    @GetMapping("/addresses/{userId}")
    public Object getUserAddressList(@PathVariable Integer userId){
        List<Address> addressList= addressService.getUserAddresslist(userId);
        return ResponseUtil.ok(addressList);
    }

    /**
     * 收货地址详情
     * @param id 收货地址ID
     * @return 收货地址详情
     * @Author: Zhang Yaqing
     * @Date: 2019/12/12
     */
    @GetMapping("/addresses/{id}")
    public Object getAddressDetail(@PathVariable Integer id){
        Address address=addressService.getAddressDetail(id);
        return ResponseUtil.ok(address);
    }

    /**
     * 测试地址是否合法，比如是否有country/province等
     * @param address
     * @return 返回值表示成功与否
     * @Author: Zhang Yaqing
     * @Date: 2019/12/12
     */
    public Object validate(@RequestBody Address address){
        String country=address.getCounty();
        String province=address.getProvince();
        String city=address.getCity();
        String address_detail=address.getAddressDetail();
        String postal_code=address.getPostalCode();
        String mobile=address.getMobile();
        if(StringUtils.isEmpty(country))
        {
            return ResponseUtil.badArgument();
        }
        if (StringUtils.isEmpty(province)) {
            return ResponseUtil.badArgument();
        }
        if (StringUtils.isEmpty(city)) {
            return ResponseUtil.badArgument();
        }
        if (StringUtils.isEmpty(address_detail)) {
            return ResponseUtil.badArgument();
        }
        if (StringUtils.isEmpty(postal_code)) {
            return ResponseUtil.badArgument();
        }
        if (StringUtils.isEmpty(mobile)) {
            return ResponseUtil.badArgument();
        }
        return ResponseUtil.ok();
    }

    /**
     * 新增收货地址
     * @param address 用户收货地址
     * @return 新增操作结果
     * @Author: Zhang Yaqing
     * @Date: 2019/12/12
     */
    @PostMapping("/addresses")
    public Object addNewAddress(@RequestBody Address address){
        Address newAddress=addressService.addNewAddress(address);
        return ResponseUtil.ok(newAddress);
    }

    /**
     * 删除收货地址
     * @param id
     * @return 用boolean表示删除操作是否成功
     * @Author: Zhang Yaqing
     * @Date: 2019/12/12
     */
    @DeleteMapping("/address/{id}")
    public Object deleteAddress(@PathVariable Integer id){
        return ResponseUtil.ok(addressService.deleteAddress(id));
    }

    /**
     * 更新收货地址
     * @param address 用户收货地址
     * @param id  地址id
     * @return 更新操作结果
     * @Author: Zhang Yaqing
     * @Date: 2019/12/12
     */
    @PutMapping("/addresses/{id}")
    public Object updateAddress(@PathVariable Integer id, @RequestBody Address address){
        Object error=validate(address);
        if (error != null) {
            return error;
        }
        address.setId(id);
        if(addressService.updateAddress(address)==null)
        {
            return ResponseUtil.updatedDataFailed();
        }
        return ResponseUtil.ok(address);
    }

    /**
     * 管理员获取全部地址列表
     * @param
     * @return 全部地址列表
     * @Author: Zhang Yaqing
     * @Date: 2019/12/12
     */
    @GetMapping("/addresses")
    public Object allAddressList(){
        List<Address> addressList=addressService.getAllAddressList();
        return ResponseUtil.ok(addressList);
    }

}
