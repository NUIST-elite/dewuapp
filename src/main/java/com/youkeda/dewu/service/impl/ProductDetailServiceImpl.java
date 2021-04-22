package com.youkeda.dewu.service.impl;

import com.youkeda.dewu.dao.ProductDAO;
import com.youkeda.dewu.dao.ProductDetailDAO;
import com.youkeda.dewu.dataobject.ProductDetailDO;
import com.youkeda.dewu.model.ProductDetail;
import com.youkeda.dewu.service.ProductDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductDetailServiceImpl implements ProductDetailService {
    @Autowired
    private ProductDetailDAO productDetailDAO;

    @Autowired
    private ProductDAO productDAO;

    /**
     * 添加或者修改
     *
     * @param productDetail 产品
     * @return int
     */
    @Override
    public int save(ProductDetail productDetail) {
        if(productDetailDAO.selectByPrimaryKey(productDetail.getId()) == null) {
            return productDetailDAO.add(new ProductDetailDO(productDetail));
        }

         return productDetailDAO.update(new ProductDetailDO(productDetail));
    }

    /**
     * 获取商品id获取所有商品详情
     *
     * @param productId 商品主键
     * @return ProductDetail
     */
    @Override
    public List<ProductDetail> getByProductId(String productId) {
        if(productDAO.selectByPrimaryKey(productId) == null) {
            return null;
        }

        List<ProductDetailDO> list = productDetailDAO.findByProductId(productId);

        return list.stream().map(ProductDetailDO::convertToModel).collect(Collectors.toList());
    }

    /**
     * 获取多个商品详情
     *
     * @param productDetailIds 查询参数
     * @return
     */
    @Override
    public List<ProductDetail> queryProductDetail(List<String> productDetailIds) {
        List<ProductDetailDO> detailDOS = productDetailDAO.findByIds(productDetailIds);

        return detailDOS.stream().map(ProductDetailDO::convertToModel).collect(Collectors.toList());
    }
}
