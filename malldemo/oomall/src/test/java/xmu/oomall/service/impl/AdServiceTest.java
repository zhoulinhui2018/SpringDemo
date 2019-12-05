package xmu.oomall.service.impl;

import org.apache.commons.lang3.ObjectUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sound.midi.Soundbank;

public class AdServiceTest {

    @Autowired
    private AdService adService;

    @Test
    public void test(){
        System.out.println("test zhou");
        if (adService.findAdById(1)!=null){
            System.out.println(adService.findAdById(1));
        }else {
            System.out.println("查无此项");
        }
    }

}
