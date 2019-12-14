package xmu.oomall.address.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author: 数据库与对象模型标准组
 * @Description:地址对象
 * @Data:Created in 14:50 2019/12/11
 **/
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class Address extends AddressPo {

    private String province;

    private String city;

    private String county;

    public String getProvince() { return province; }

    public String getCity() { return city; }

    public String getCounty() {
        return county;
    }

    public void setProvince(String province) { this.province = province; }

    public void setCity(String city) { this.city = city; }

    public void setCounty(String county) { this.county = county; }

}
