package xmu.oomall.address.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xmu.oomall.address.domain.Address;
import xmu.oomall.address.domain.AddressPo;
//import xmu.oomall.address.domain.Log;
import xmu.oomall.address.domain.Region;
import xmu.oomall.address.service.impl.AddressService;
import xmu.oomall.address.util.FomatUtil;
import xmu.oomall.address.util.ResponseUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("")
@Validated
public class AddressController {
    @Autowired
    private AddressService addressService;

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
            String county=addressService.getRegion(countyId).getName();
            String province=addressService.getRegion(provinceId).getName();
            String city=addressService.getRegion(cityId).getName();
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
        Integer userId= Integer.valueOf(request.getHeader("userid"));
        if(userId==0){
            return ResponseUtil.unlogin();
        }
        List<Address> addressList;
        try{
            addressList=addressService.getUserAddresslist(page,limit,userId);
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
        Address address=addressService.getAddressDetail(id);
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
    private Integer validate(@RequestBody AddressPo addressPo){
        Integer countryId=addressPo.getCountyId();
        Integer provinceId=addressPo.getProvinceId();
        Integer cityId=addressPo.getCityId();
        Integer userId=addressPo.getUserId();
        String address_detail=addressPo.getAddressDetail();
        String postal_code=addressPo.getPostalCode();
        String mobile=addressPo.getMobile();
        String consignee=addressPo.getConsignee();

        //判断省市县是否为空
        if(countryId==0||provinceId==0||cityId==0) {
            return 1;
        }
        //判断userId是否为空
        if(userId==0){
            return 1;
        }
        //判断各个string字段是否为空
        if (StringUtils.isEmpty(address_detail)) {
            return 1;
        }
        if (StringUtils.isEmpty(postal_code)) {
            return 1;
        }
        if (StringUtils.isEmpty(mobile)) {
            return 1;
        }
        if (StringUtils.isEmpty(consignee)) {
            return 1;
        }

        //判断省市县是否合法，且有正确的层级关系
        Region provinceRegion=addressService.getRegion(provinceId);
        Region cityRegion=addressService.getRegion(cityId);
        Region countryRegion=addressService.getRegion(countryId);
        if(provinceRegion.getType()!=1){
            return 2;
        }
        if(cityRegion.getType()!=2){
            return 2;
        }
        if(countryRegion.getType()!=3){
            return 2;
        }
        if(!cityRegion.getPid().equals(provinceRegion.getId())){
            return 2;
        }
        if(!countryRegion.getPid().equals(cityRegion.getId())){
            return 2;
        }

        //判断电话号码、邮政编码是否合法
        if(!FomatUtil.isValidateMobile(mobile)){
            return 2;
        }
        if(!FomatUtil.isValidatePostcode(postal_code)){
            return 2;
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
        if(validate(addressPo)==1){
            return ResponseUtil.badArgument();
        }
        if(validate(addressPo)==2){
            return ResponseUtil.badArgumentValue();
        }
        AddressPo newAddressPo;

        try {
            newAddressPo=addressService.addNewAddress(addressPo);
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
        Address address=addressService.getAddressDetail(id);
        if(address==null||address.getBeDeleted()==true){
            return ResponseUtil.addressNotExist();
        }else{
            address.setBeDeleted(true);
            address.setBeDefault(0);
            AddressPo newAddressPo=addressService.updateAddress(address);
            if(newAddressPo==null) {
                return ResponseUtil.deleteAddressFailed();
            }else{
                return ResponseUtil.ok(true);
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
        if(validate(addressPo)==1){
            return ResponseUtil.badArgument();
        }
        if(validate(addressPo)==2){
            return ResponseUtil.badArgumentValue();
        }
        addressPo.setId(id);
        AddressPo newAddressPo=addressService.updateAddress(addressPo);
        if(newAddressPo==null) {
            return ResponseUtil.updateAddressFailed();
        }else{
            return ResponseUtil.ok(addressPo);
        }
    }

//    /**
//     * 管理员根据条件查找地址
//     * @param
//     * @return 全部地址列表
//     * @Author: Zhang Yaqing
//     * @Date: 2019/12/12
//     */
//    @GetMapping("/admin/addresses")
//    public Object adminFindUserAddress(HttpServletRequest request,
//                                       @RequestParam Integer userId,
//                                       @RequestParam String name,
//                                       @RequestParam(defaultValue = "1") Integer page,
//                                       @RequestParam(defaultValue = "10") Integer limit){
//        String adminid= request.getHeader("id");
//        if (adminid==null){
//            return ResponseUtil.unlogin();
//        }
//        Log log=new Log();
//        log.setAdminId(Integer.valueOf(adminid));
//        log.setIp(request.getRemoteAddr());
//        log.setType(0);
//        log.setStatusCode(1);
//        log.setActions("获取根据条件查找地址");
//        List<Address> addressList;
//        try {
//            addressList=addressService.adminFindUserAddress(page,limit,userId,name);
//        } catch (Exception e) {
//            log.setStatusCode(0);
//            addressService.log(log);
//            return ResponseUtil.updatedDataFailed();
//        }
//        addressService.log(log);
//        return ResponseUtil.ok(addressList);
//    }
}
