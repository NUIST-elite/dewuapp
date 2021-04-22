package com.youkeda.dewu.dataobject;

import com.youkeda.dewu.model.Product;
import com.youkeda.dewu.model.ProductDetail;
import com.youkeda.dewu.util.UUIDUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import java.util.Date;

public class ProductDetailDO {
    private String id;

    private String productId;

    private Double price;

    private Double size;

    private Integer stock;

    private Date gmtCreated;

    private Date gmtModified;

    public ProductDetailDO() {}

    public ProductDetailDO(ProductDetail productDetail) {
        BeanUtils.copyProperties(productDetail, this);
        if (StringUtils.isEmpty(productDetail.getId())) {
            this.setId(UUIDUtils.getUUID());
        }
    }

    /**
     * DO 转换为 Model
     *
     * @return productDetail
     */
    public ProductDetail convertToModel() {
        ProductDetail productDetail = new ProductDetail();
        BeanUtils.copyProperties(this, productDetail);
        return productDetail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Date getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    @Override
    public String toString() {
        return "ProductDetail{" +
                "id='" + id + '\'' +
                ", productId='" + productId + '\'' +
                ", price=" + price +
                ", size=" + size +
                ", stock=" + stock +
                ", gmtCreated=" + gmtCreated +
                ", gmtModified=" + gmtModified +
                '}';
    }
}
