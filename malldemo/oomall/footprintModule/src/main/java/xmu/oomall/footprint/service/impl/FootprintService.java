package xmu.oomall.footprint.service.impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import xmu.oomall.footprint.dao.FootprintDao;
import xmu.oomall.footprint.domain.FootprintItem;
import xmu.oomall.footprint.domain.FootprintItemPo;
import xmu.oomall.footprint.domain.Log;
import xmu.oomall.footprint.service.IFootprintService;

import java.util.List;

@Transactional
@Service
public class FootprintService implements IFootprintService {
    @Autowired
    private FootprintDao footprintDao;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    /**
     * 管理员操作日志
     * @param log
     */
    @Override
    public void log(Log log){
        System.out.println("进入FootprintService.log");
        RestTemplate restTemplate = new RestTemplate();
        ServiceInstance instance = loadBalancerClient.choose("logService");
        System.out.println(instance.getHost());
        System.out.println(instance.getPort());
        String reqURL = String.format("http://%s:%s", instance.getHost(), instance.getPort() + "/logs");
        restTemplate.postForObject(reqURL,log,Log.class);
    }

    @Override
    public List<FootprintItem> getUserFootprintList(Integer page, Integer limit, Integer userId) {
        PageHelper.startPage(page,limit);
        return footprintDao.getUserFootprintList(userId);
    }

    @Override
    public boolean deleteFootprint(Integer id) {
        return footprintDao.deleteFootprint(id);
    }

    @Override
    public List<FootprintItem> listFootprintByCondition(Integer page, Integer limit, Integer userId, Integer goodsId) {
        PageHelper.startPage(page,limit);
        List<FootprintItem> userFootprintList=footprintDao.listFootprintByCondition(userId,goodsId);
        return userFootprintList;
    }

    @Override
    public boolean addFootprint(FootprintItemPo footprintItemPo) {
        return footprintDao.addFootprint(footprintItemPo);
    }
}
