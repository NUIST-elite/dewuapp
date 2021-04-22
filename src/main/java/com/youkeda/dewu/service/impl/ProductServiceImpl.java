package com.youkeda.dewu.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.youkeda.dewu.dao.ProductDAO;
import com.youkeda.dewu.dataobject.ProductDO;
import com.youkeda.dewu.model.Paging;
import com.youkeda.dewu.model.Product;
import com.youkeda.dewu.param.BasePageParam;
import com.youkeda.dewu.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDAO productDAO;

    /**
     * 增加或修改商品
     *
     * @param product 商品
     * @return Product
     */
    @Override
    public int save(Product product) {
        ProductDO productDO = new ProductDO(product);
        if(productDAO.selectByPrimaryKey(productDO.getId()) != null) {
            return productDAO.updateByPrimaryKey(productDO);
        }

        return productDAO.insert(productDO);
    }

    /**
     * 分页查询商品
     *
     * @param param 商品分页参数
     * @return PagingData<Product>
     */
    @Override
    public Paging<Product> pageQueryProduct(BasePageParam param) {
        Paging<Product> result = new Paging<>();
        if (param == null) {
            return result;
        }

        if (param.getPagination() < 0) {
            param.setPagination(0);
        }

        if (param.getPageSize() < 0) {
            param.setPageSize(10);
        }

        //查询数据总数
        int counts = productDAO.selectAllCounts();
        if (counts < 0) {
            return result;
        }

        //组装返回数据
        result.setTotalCount(counts);
        result.setPageSize(param.getPageSize());
        result.setPageNum(param.getPagination());

        int totalPage = (int)Math.ceil(counts / (param.getPageSize() * 1.0));
        result.setTotalPage(totalPage);

        //实际返回的数据
        List<ProductDO> productDOS = productDAO.pageQuery(param);
        List<Product> productList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(productDOS)){
            for (ProductDO productDO : productDOS) {
                productList.add(productDO.convertToModel());
            }
        }

        result.setData(productList);

        return result;
    }

    /**
     * 根据主键获取商品
     *
     * @param productId 商品主键
     * @return Product
     */
    @Override
    public Product get(String productId) {
        ProductDO productDO = productDAO.selectByPrimaryKey(productId);
        return productDO.convertToModel();
    }
}
