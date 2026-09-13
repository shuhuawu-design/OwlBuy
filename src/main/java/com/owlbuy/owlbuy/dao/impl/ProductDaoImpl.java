package com.owlbuy.owlbuy.dao.impl;

import com.owlbuy.owlbuy.dao.ProductDao;
import com.owlbuy.owlbuy.dto.ProductQueryParam;
import com.owlbuy.owlbuy.dto.ProductRequest;
import com.owlbuy.owlbuy.dto.ProductUpdateRequest;
import com.owlbuy.owlbuy.model.Product;
import com.owlbuy.owlbuy.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProductDaoImpl implements ProductDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        String sql = "INSERT INTO product(product_name,category,price,stock,image_url,description) VALUES (:product_name,:category,:price,:stock,:image_url,:description)";
        Map<String, Object> map = new HashMap<>();
        map.put("product_name", productRequest.getProductName());
        map.put("category", productRequest.getCategory().name());
        map.put("price", productRequest.getPrice());
        map.put("stock", productRequest.getStock());
        map.put("image_url", productRequest.getImageUrl());
        map.put("description", productRequest.getDescription());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        Integer productId = keyHolder.getKey().intValue();
        return productId;
    }

    @Override
    public Product getProductById(Integer productId) {
        String sql = "SELECT product_id, product_name,category, price, stock, image_url, description, status, created_date, updated_date FROM product WHERE product_id=:productId";
        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);

        List<Product> product = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());
        if(product.size()>0){
            return product.get(0);
        }else{
            return null;
        }
    }

    @Override
    public List<Product> getProducts(ProductQueryParam productQueryParam) {
        String sql="SELECT product_id, product_name,category, price, stock, image_url, description, status, created_date, updated_date FROM product WHERE 1=1 ";
        Map<String, Object> map = new HashMap<>();

        sql=addFilteringSql(sql,map,productQueryParam);
        sql=sql+" ORDER BY "+productQueryParam.getOrderBy()+" "+productQueryParam.getSort();
        sql=sql+" LIMIT :limit OFFSET :offset";
        map.put("limit", productQueryParam.getLimit());
        map.put("offset", productQueryParam.getOffset());

        List<Product>productList=namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());
        return productList;
    }

    @Override
    public Integer countProducts(ProductQueryParam productQueryParam) {
        String sql="SELECT count(*) FROM product WHERE 1=1 ";
        Map<String, Object> map = new HashMap<>();
        sql=addFilteringSql(sql,map,productQueryParam);
        Integer total=namedParameterJdbcTemplate.queryForObject(sql,map,Integer.class);
        return total;
    }

    @Override
    public void updateProduct(Integer productId, ProductUpdateRequest productUpdateRequest) {
        StringBuilder sql=new StringBuilder("UPDATE product SET ");
        Map<String, Object> map = new HashMap<>();

        if(productUpdateRequest.getProductName()!=null){
            sql.append("product_name= :product_name, ");
            map.put("product_name", productUpdateRequest.getProductName());
        }

        if(productUpdateRequest.getCategory()!=null){
            sql.append("category= :category, ");
            map.put("category", productUpdateRequest.getCategory().name());
        }
        if(productUpdateRequest.getPrice()!=null){
            sql.append("price= :price, ");
            map.put("price", productUpdateRequest.getPrice());
        }
        if(productUpdateRequest.getStock()!=null){
            sql.append("stock= :stock, ");
            map.put("stock", productUpdateRequest.getStock());
        }
        if(productUpdateRequest.getImageUrl()!=null){
            sql.append("image_url= :image_url, ");
            map.put("image_url", productUpdateRequest.getImageUrl());
        }
        if(productUpdateRequest.getDescription()!=null){
            sql.append("description= :description, ");
            map.put("description", productUpdateRequest.getDescription());
        }
        if(productUpdateRequest.getStatus()!=null) {
            sql.append("status= :status, ");
            map.put("status", productUpdateRequest.getStatus().name());
        }

        sql.append("updated_date= :updated_date ");
        map.put("updated_date", new Date());

        sql.append("WHERE product_id = :product_id ");
        map.put("product_id", productId);

        namedParameterJdbcTemplate.update(sql.toString(), map);
    }

    @Override
    public void deleteProductById(Integer productId) {
        String sql = "UPDATE product SET status='DISCONTINUED' WHERE product_id=:productId";
        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map));
    }

    private String addFilteringSql(String sql,Map<String,Object> map,ProductQueryParam productQueryParam){

        if (productQueryParam.getCategory()!=null){
            sql = sql + "AND category=:category ";
            map.put("category", productQueryParam.getCategory().name());
        }
        if (productQueryParam.getSearch()!=null){
            sql = sql + "AND product_name LIKE :search ";
            map.put("search", productQueryParam.getSearch());
        }
        return sql;
    }
}
