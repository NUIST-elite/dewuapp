package com.youkeda.dewu.dao;

import com.youkeda.dewu.dataobject.ProductDetailDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductDetailDAO {
    int add(ProductDetailDO productDetailDO);

    int delete(@Param("id") String id);

    int update(ProductDetailDO productDetailDO);

    List<ProductDetailDO> findAll();

    ProductDetailDO selectByPrimaryKey(String id);

    List<ProductDetailDO> findByProductId(String productId);

    List<ProductDetailDO> findByIds(@Param("list") List<String> productDetailIds);
}
