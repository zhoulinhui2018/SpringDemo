package xmu.oomall.address.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xmu.oomall.address.domain.Address;
import xmu.oomall.address.domain.AddressPo;
import xmu.oomall.address.service.impl.AddressService;
import xmu.oomall.util.ResponseUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("")
@Validated
public class AddressController {
    @Autowired
    private AddressService addressService;

    /**
     * 用户查看自己的收货地址列表
     * @param request
     * @param page
     * @param limit
     * @return 收货地址列表
     * @Author: Zhang Yaqing
     * @Date: 2019/12/16
     */
    @GetMapping("/addresses")
    public Object getUserAddressList(HttpServletRequest request,
                                     @RequestParam(defaultValue = "1") Integer page,
                                     @RequestParam(defaultValue = "10") Integer limit){
        Integer userId= Integer.valueOf(request.getHeader("userid"));
        if(userId==0){
            return ResponseUtil.unlogin();
        }
        List<Address> addressList= addressService.getUserAddresslist(page,limit,userId);
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
     * @param addressPo
     * @return 返回值表示成功与否
     * @Author: Zhang Yaqing
     * @Date: 2019/12/12
     */
    private Object validate(@RequestBody AddressPo addressPo){
        Integer countryId=addressPo.getCountyId();
        Integer provinceId=addressPo.getProvinceId();
        Integer cityId=addressPo.getCityId();
        String address_detail=addressPo.getAddressDetail();
        String postal_code=addressPo.getPostalCode();
        String mobile=addressPo.getMobile();
        if(countryId==0)
        {
            return ResponseUtil.badArgument();
        }
        if (countryId==0) {
            return ResponseUtil.badArgument();
        }
        if (countryId==0) {
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
     * @param addressPo 用户收货地址
     * @return 新增操作结果
     * @Author: Zhang Yaqing
     * @Date: 2019/12/12
     */
    @PostMapping("/addresses")
    public Object addNewAddress(@RequestBody AddressPo addressPo){
        AddressPo newAddressPo=addressService.addNewAddress(addressPo);
        return ResponseUtil.ok(newAddressPo);
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
     * @param id  地址id
     * @param addressPo 用户收货地址
     * @return 更新操作结果
     * @Author: Zhang Yaqing
     * @Date: 2019/12/12
     */
    @PutMapping("/addresses/{id}")
    public Object updateAddress(@PathVariable Integer id, @RequestBody AddressPo addressPo){
        Object error=validate(addressPo);
        if (error != null) {
            return error;
        }
        addressPo.setId(id);
        if(addressService.updateAddress(addressPo)==null)
        {
            return ResponseUtil.updatedDataFailed();
        }
        return ResponseUtil.ok(addressPo);
    }

    /**
     * 管理员获取根据条件查找地址
     * @param
     * @return 全部地址列表
     * @Author: Zhang Yaqing
     * @Date: 2019/12/12
     */
    @GetMapping("/admin/addresses")
    public Object adminFindUserAddress(@RequestParam Integer userId,
                                 @RequestParam String name,
                                 @RequestParam(defaultValue = "1") Integer page,
                                 @RequestParam(defaultValue = "10") Integer limit){
        List<Address> addressList=addressService.adminFindUserAddress(page,limit,userId,name);
        return ResponseUtil.ok(addressList);
    }

}
