package xmu.oomall.discount.domain;

import xmu.oomall.domain.goods.PresaleRule;

import java.util.List;

/**
 * @Author: 数据库与对象模型标准组
 * @Description:商品对象
 * @Data:Created in 14:50 2019/12/11
 **/

public class Goods extends GoodsPo {
    private BrandPo brandPo;
    private GoodsCategoryPo goodsCategoryPo;
    private List<ProductPo> productPoList;
    private GrouponRule grouponRule;
    private ShareRule shareRule;
    private PresaleRule presaleRule;

    public BrandPo getBrandPo() {
        return brandPo;
    }

    public void setBrandPo(BrandPo brandPo) {
        this.brandPo = brandPo;
    }

    public GoodsCategoryPo getGoodsCategoryPo() {
        return goodsCategoryPo;
    }

    public void setGoodsCategoryPo(GoodsCategoryPo goodsCategoryPo) {
        this.goodsCategoryPo = goodsCategoryPo;
    }

    public List<ProductPo> getProductPoList() {
        return productPoList;
    }

    public void setProductPoList(List<ProductPo> productPoList) {
        this.productPoList = productPoList;
    }

    public GrouponRule getGrouponRule() {
        return grouponRule;
    }

    public void setGrouponRule(GrouponRule grouponRule) {
        this.grouponRule = grouponRule;
    }

    public ShareRule getShareRule() {
        return shareRule;
    }

    public void setShareRule(ShareRule shareRule) {
        this.shareRule = shareRule;
    }

    public PresaleRule getPresaleRule() {
        return presaleRule;
    }

    public void setPresaleRule(PresaleRule presaleRule) {
        this.presaleRule = presaleRule;
    }
}
