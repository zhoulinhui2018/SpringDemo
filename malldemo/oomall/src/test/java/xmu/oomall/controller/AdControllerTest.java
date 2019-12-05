package xmu.oomall.controller;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import xmu.oomall.domain.Ad;

public class AdControllerTest {
    @Autowired
    private AdController adController;

    @Test
    public void test1(){
        Ad ad=adController.getAdDetail(1);
        System.out.println("ad name"+ad.getName());
        System.out.println("ad content"+ad.getContent());
    }
}
