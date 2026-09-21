package com.owlbuy.owlbuy.dao.impl;

import com.owlbuy.owlbuy.dao.CartDao;
import com.owlbuy.owlbuy.dto.CartItemResponse;
import com.owlbuy.owlbuy.dto.CartRequest;
import com.owlbuy.owlbuy.model.Cart;
import com.owlbuy.owlbuy.model.CartItem;
import com.owlbuy.owlbuy.rowmapper.CartItemResponseRowMapper;
import com.owlbuy.owlbuy.rowmapper.CartItemRowMapper;
import com.owlbuy.owlbuy.rowmapper.CartRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CartDaoImpl implements CartDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Cart getCartByMemberId(Integer memberId) {
        String sql="SELECT cart_id, member_id, created_date FROM cart WHERE member_id = :member_id";
        Map<String,Object> map=new HashMap<>();
        map.put("member_id",memberId);

        List<Cart> cart=namedParameterJdbcTemplate.query(sql,map,new CartRowMapper());
        if (cart.size()==0){
            return null;
        }else{
            return cart.get(0);
        }
    }

    @Override
    public CartItem getCartItemByCartItemId(Integer cartItemId, Integer memberId) {
        String sql="""
            SELECT ci.cart_item_id, ci.cart_id, ci.product_id, ci.quantity, ci.created_date, ci.updated_date, c.member_id 
            FROM cart_item AS ci 
            JOIN cart AS c ON c.cart_id=ci.cart_id 
            WHERE ci.cart_item_id = :cart_item_id AND c.member_id = :member_id""";
        Map<String,Object> map=new HashMap<>();
        map.put("cart_item_id",cartItemId);
        map.put("member_id",memberId);

        List<CartItem> cartItemList=namedParameterJdbcTemplate.query(sql,map,new CartItemRowMapper());
        if (cartItemList.isEmpty()){
            return null;
        }else{
            return cartItemList.get(0);
        }
    }

    @Override
    public List<CartItemResponse> getCartItemsByMemberId(Integer memberId) {
        String sql= """
                SELECT ci.cart_item_id, ci.quantity, p.product_id, p.product_name, p.price, p.image_url, p.status ,(ci.quantity * p.price) AS subtotal
                FROM cart AS c
                JOIN cart_item AS ci ON c.cart_id= ci.cart_id
                JOIN product AS p ON ci.product_id= p.product_id
                WHERE c.member_id= :member_id
                """;

        Map<String,Object> map=new HashMap<>();
        map.put("member_id",memberId);

        List<CartItemResponse>cartItems=namedParameterJdbcTemplate.query(sql,map,new CartItemResponseRowMapper());
        return cartItems;
    }

    @Override
    public Integer createCart(Integer memberId) {
        String sql="INSERT INTO cart(member_id)VALUES(:member_id)";
        Map<String,Object> map=new HashMap<>();
        map.put("member_id",memberId);

        KeyHolder keyHolder=new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource(map),keyHolder);
        Integer cartId=keyHolder.getKey().intValue();
        return cartId;
    }

    @Override
    public CartItem findCartItem(Integer cartId, Integer productId) {
        String sql="SELECT cart_item_id, cart_id, product_id, quantity, created_date, updated_date FROM cart_item WHERE cart_id = :cart_id AND product_id = :product_id";
        Map<String,Object> map=new HashMap<>();
        map.put("cart_id",cartId);
        map.put("product_id",productId);

        List<CartItem>cartItem=namedParameterJdbcTemplate.query(sql,map,new CartItemRowMapper());
        if (cartItem.isEmpty()){
            return null;
        }else{
            return cartItem.get(0);
        }

    }

    @Override
    public void createCartItem(Integer cartId, Integer productId, Integer quantity) {
        String sql="INSERT INTO cart_item(cart_id, product_id, quantity) VALUES(:cart_id, :product_id, :quantity)";
        Map<String,Object> map=new HashMap<>();
        map.put("cart_id",cartId);
        map.put("product_id",productId);
        map.put("quantity",quantity);

        namedParameterJdbcTemplate.update(sql,map);
    }

    @Override
    public void updateCartQuantity(Integer cartItemId, Integer quantity) {
        String sql="UPDATE cart_item SET quantity= :quantity WHERE cart_item_id = :cartItemId";
        Map<String,Object> map=new HashMap<>();
        map.put("cartItemId",cartItemId);
        map.put("quantity",quantity);
        namedParameterJdbcTemplate.update(sql,map);
    }

    @Override
    public void deleteCartItem(Integer cartId, Integer productId) {
        String sql="DELETE FROM cart_item WHERE cart_id = :cartId and product_id = :productId";
        Map<String,Object> map=new HashMap<>();
        map.put("cartId",cartId);
        map.put("productId",productId);
        namedParameterJdbcTemplate.update(sql,map);
    }

    @Override
    public void deleteCartItemList(List<Integer> cartItemIdList) {
        String sql="DELETE FROM cart_item WHERE cart_item_id IN (:cartItemIdList)";
        Map<String,Object> map=new HashMap<>();
        map.put("cartItemIdList",cartItemIdList);

        namedParameterJdbcTemplate.update(sql,map);
    }
}
