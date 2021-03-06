package xmu.oomall.address.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xmu.oomall.address.domain.Address;
import xmu.oomall.address.domain.AddressPo;
import xmu.oomall.address.domain.Region;
import xmu.oomall.address.service.impl.AddressServiceImpl;
import xmu.oomall.address.util.FomatUtil;
import xmu.oomall.address.util.ResponseUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Address模块
 * @author Zhang Yaqing
 * @date 2019/12/18
 */
@RestController
@RequestMapping("")
@Validated
public class AddressController {
    @Autowired
    private AddressServiceImpl addressServiceImpl;

    static final int TYPE_1= 1;
    static final int TYPE_2= 2;
    static final int TYPE_3= 3;
    /**
     * 由AddressPo生成Address
     * @param address
     * @author Zhang Yaqing
     * @date 2019/12/18
     */
    private void changePoToAddress(Address address){
            Integer countyId=address.getCountyId();
            Integer provinceId=address.getProvinceId();
            Integer cityId=address.getCityId();
            String county= addressServiceImpl.getRegion(countyId).getName();
            String province= addressServiceImpl.getRegion(provinceId).getName();
            String city= addressServiceImpl.getRegion(cityId).getName();
            address.setCity(city);
            address.setProvince(province);
            address.setCounty(county);
    }
    /**
     * 用户查看收货地址列表
     * @param request
     * @param page
     * @param limit
     * @return 收货地址列表
     * @author Zhang Yaqing
     * @date 2019/12/16
     */
    @GetMapping("/addresses")
    public Object getUserAddressList(HttpServletRequest request,
                                     @RequestParam(defaultValue = "1") Integer page,
                                     @RequestParam(defaultValue = "10") Integer limit){
        String id= request.getHeader("id");
        if(id==null){
            return ResponseUtil.unlogin();
        }
        if (page<=0||limit<=0){
            return ResponseUtil.fail(580,"参数不对");
        }
        List<Address> addressList;
        Integer userId= Integer.valueOf(id);
        try{
            addressList= addressServiceImpl.getUserAddresslist(page,limit,userId);
        }catch (Exception e){
            return ResponseUtil.serious();
        }
        for(int i=0;i<addressList.size();i++) {
            changePoToAddress(addressList.get(i));
        }
        return ResponseUtil.ok(addressList);
    }

    /**
     * 收货地址详情
     * @param id 收货地址ID
     * @return 收货地址详情
     * @author Zhang Yaqing
     * @date 2019/12/12
     */
    @GetMapping("/addresses/{id}")
    public Object getAddressDetail(@PathVariable Integer id){
        if (id<=0){
            return ResponseUtil.fail(580,"参数错误");
        }
        Address address= addressServiceImpl.getAddressDetail(id);
        if(address==null||address.getBeDeleted()==true){
            return ResponseUtil.addressNotExist();
        }else{
            changePoToAddress(address);
            return ResponseUtil.ok(address);
        }
    }

    /**
     * 测试地址是否合法，比如是否有country/province等
     * @param addressPo
     * @return 0-合法，1-参数错误(为空)，2-参数值错误(类型等错误)
     * @author Zhang Yaqing
     * @date 2019/12/12
     */
    private Integer validate(AddressPo addressPo){
        Integer countryId=addressPo.getCountyId();
        Integer provinceId=addressPo.getProvinceId();
        Integer cityId=addressPo.getCityId();
        Integer userId=addressPo.getUserId();
        String addressDetail=addressPo.getAddressDetail();
        String postalCode=addressPo.getPostalCode();
        String mobile=addressPo.getMobile();
        String consignee=addressPo.getConsignee();

        //判断省市县是否为空
        if(countryId==0||provinceId==0||cityId==0) {
            return TYPE_1;
        }
        //判断userId是否为空
        if(userId==0){
            return TYPE_1;
        }
        //判断各个string字段是否为空
        if (StringUtils.isEmpty(addressDetail)) {
            return TYPE_1;
        }
        if (StringUtils.isEmpty(postalCode)) {
            return TYPE_1;
        }
        if (StringUtils.isEmpty(mobile)) {
            return TYPE_1;
        }
        if (StringUtils.isEmpty(consignee)) {
            return TYPE_1;
        }

        //判断省市县是否合法，且有正确的层级关系
        Region provinceRegion= addressServiceImpl.getRegion(provinceId);
        Region cityRegion= addressServiceImpl.getRegion(cityId);
        Region countryRegion= addressServiceImpl.getRegion(countryId);
        if(provinceRegion.getType()!=TYPE_1){
            return TYPE_2;
        }
        if(cityRegion.getType()!=TYPE_2){
            return TYPE_2;
        }
        if(countryRegion.getType()!=TYPE_3){
            return TYPE_2;
        }
        if(!cityRegion.getPid().equals(provinceRegion.getId())){
            return TYPE_2;
        }
        if(!countryRegion.getPid().equals(cityRegion.getId())){
            return TYPE_2;
        }

        //判断电话号码、邮政编码是否合法
        if(!FomatUtil.isValidateMobile(mobile)){
            return TYPE_2;
        }
        if(!FomatUtil.isValidatePostcode(postalCode)){
            return TYPE_2;
        }
        return 0;
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
        if(validate(addressPo)==TYPE_1){
            return ResponseUtil.addAddressFailed();
        }
        if(validate(addressPo)==TYPE_2){
            return ResponseUtil.addAddressFailed();
        }
        AddressPo newAddressPo;

        try {
            newAddressPo= addressServiceImpl.addNewAddress(addressPo);
        } catch (Exception e) {
            return ResponseUtil.addAddressFailed();
        }
        return ResponseUtil.ok(newAddressPo);
    }

    /**
     * 删除收货地址
     * @param id
     * @return 用boolean表示删除操作是否成功
     * @Author: Zhang Yaqing
     * @Date: 2019/12/12
     */
    @DeleteMapping("/addresses/{id}")
    public Object deleteAddress(@PathVariable Integer id){
        if (id<=0){
            return ResponseUtil.fail(580,"参数错误");
        }
        Address address= addressServiceImpl.getAddressDetail(id);
        if(address==null||address.getBeDeleted()==true){
            return ResponseUtil.addressNotExist();
        }else{
            address.setBeDeleted(true);
            address.setBeDefault(false);
            AddressPo newAddressPo= addressServiceImpl.updateAddress(address);
            if(newAddressPo==null) {
                return ResponseUtil.deleteAddressFailed();
            }else{
                return ResponseUtil.ok();
            }
        }
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
        if (id<=0){
            return ResponseUtil.fail(580,"参数错误");
        }
        if(validate(addressPo)==TYPE_1){
            return ResponseUtil.updateAddressFailed();
        }
        if(validate(addressPo)==TYPE_2){
            return ResponseUtil.updateAddressFailed();
        }
        addressPo.setId(id);
        AddressPo newAddressPo= addressServiceImpl.updateAddress(addressPo);
        if(newAddressPo==null) {
            return ResponseUtil.updateAddressFailed();
        }else{
            return ResponseUtil.ok(addressPo);
        }
    }

}
