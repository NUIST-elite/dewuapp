package com.youkeda.dewu.api;

import com.youkeda.dewu.model.ProductDetail;
import com.youkeda.dewu.model.Result;
import com.youkeda.dewu.service.ProductDetailService;
import com.youkeda.dewu.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/productdetail")
public class ProductDetailApi {
    @Autowired
    private ProductDetailService productDetailService;

    @Autowired
    private ProductService productService;

    @GetMapping("/page")
    public Result<List<ProductDetail>> page(@RequestParam(value = "productId") String productId) {
        Result<List<ProductDetail>> result = new Result<>();
        result.setSuccess(true);

        if(productService.get(productId) == null) {
            return result;
        }

        List<ProductDetail> productDetails = productDetailService.getByProductId(productId);
        result.setData(productDetails);
        return result;
    }
}
