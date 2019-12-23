package xmu.oomall.address.service.impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import xmu.oomall.address.dao.AddressDao;
import xmu.oomall.address.domain.Address;
import xmu.oomall.address.domain.AddressPo;
import xmu.oomall.address.domain.Log;
import xmu.oomall.address.domain.Region;
import xmu.oomall.address.service.IAddressService;

import java.util.List;

/**
 * Address模块Service层
 * @return void
 * @Author: Zhang Yaqing
 * @Date: 2019/12/12
 */
@Service
public class AddressServiceImpl implements IAddressService {
    @Autowired
    private AddressDao addressDao;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    /**
     * 管理员操作日志
     * @param log
     */
    @Override
    public void log(Log log){
        RestTemplate restTemplate = new RestTemplate();
        ServiceInstance instance = loadBalancerClient.choose("Log");
        System.out.println(instance.getHost());
        System.out.println(instance.getPort());
        String reqUrl = String.format("http://%s:%s", instance.getHost(), instance.getPort() + "/logs");
        restTemplate.postForObject(reqUrl,log,Log.class);
    }


    @Override
    public List<Address> getUserAddresslist(Integer page, Integer limit, Integer userId) {
        PageHelper.startPage(page,limit);
        return addressDao.getUserAddresslist(userId);
    }
    @Override
    public Address getAddressDetail(Integer id) {
        return addressDao.getAddressDetail(id);
    }

    @Override
    public AddressPo addNewAddress(AddressPo addressPo) {
        return addressDao.addNewAddress(addressPo);
    }
    @Override
    public AddressPo updateAddress(AddressPo addressPo) {
        return addressDao.updateAddress(addressPo);
    }
    @Override
    public List<Address> adminFindUserAddress(Integer page, Integer limit, Integer userId, String name) {
        PageHelper.startPage(page,limit);
        return addressDao.adminFindUserAddress(userId,name);
    }

    @Override
    public Region getRegion(Integer id) {
        return addressDao.getRegion(id);
    }
}
