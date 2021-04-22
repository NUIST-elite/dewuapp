package com.youkeda.dewu.api;

import com.youkeda.dewu.model.Paging;
import com.youkeda.dewu.model.Product;
import com.youkeda.dewu.model.Result;
import com.youkeda.dewu.param.BasePageParam;
import com.youkeda.dewu.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
public class ProductApi {
    @Autowired
    private ProductService productService;

    @GetMapping("/page")
    public Result<Paging<Product>> page(@RequestBody BasePageParam param) {
        Result<Paging<Product>> result = new Result<>();

        result.setSuccess(true);
        result.setData(productService.pageQueryProduct(param));
        return result;
    }

    @GetMapping("/get")
    public Result<Product> get(@RequestParam("productId") String productId) {
        Result<Product> result = new Result<>();

        result.setSuccess(true);
        result.setData(productService.get(productId));
        return result;
    }
}
