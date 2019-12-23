package xmu.oomall.footprint.domain.vo;
import xmu.oomall.footprint.domain.FootprintItem;
import xmu.oomall.footprint.domain.Goods;


/**
 * Demo class FootprintItemVo
 *admin端的 GET /footprints 请求VO
 * @author Zhang Yaqing
 * @date 2019/12/20
 */
public class FootprintItemVo {
    /**
     * 商品
     */
    private Goods goods;

    /**
     * 足迹
     */
    private FootprintItem footprintItem;

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public FootprintItem getFootprintItem() {
        return footprintItem;
    }

    public void setFootprintItem(FootprintItem footprintItem) {
        this.footprintItem = footprintItem;
    }
}
