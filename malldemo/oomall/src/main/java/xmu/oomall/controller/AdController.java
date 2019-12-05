package xmu.oomall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xmu.oomall.domain.Ad;
import xmu.oomall.service.impl.AdService;


@RestController
@RequestMapping("")
public class AdController {
    @Autowired
    private AdService adService;

    @GetMapping("/ads/{id}")
    public Ad getAdDetail(Integer id){
        Ad adById = adService.findAdById(id);
        return adById;
    }
}
