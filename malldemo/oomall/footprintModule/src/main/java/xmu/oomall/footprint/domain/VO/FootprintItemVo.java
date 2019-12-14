package xmu.oomall.footprint.domain.VO;
import xmu.oomall.domain.*;
import xmu.oomall.domain.goods.Goods;
import xmu.oomall.footprint.domain.FootprintItem;

/**
 * admin端的 GET /footprints 请求VO
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
