package com.example.mvvm.model;

import com.example.mvvm.auth.Token;
import com.example.mvvm.auth.SuccessRepose;
import com.example.mvvm.details.Colors;
import com.example.mvvm.details.CommentProduct;
import com.example.mvvm.home.Banners;
import com.example.mvvm.home.Categories;
import com.example.mvvm.home.CountProduct;
import com.example.mvvm.home.Products;
import com.example.mvvm.listcart.CartItem;
import com.example.mvvm.listcart.ChangeModelItem;
import com.example.mvvm.order.OrderHistory;
import com.example.mvvm.orderDetails.OrderDetials;
import com.google.gson.JsonObject;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    @GET("product.php")
    Single<List<Products>> getLatestProducts(@Query("sort") Integer sort);

    @GET("product.php")
    Single<List<Products>> getPopularProducts(@Query("sort") Integer sort);

    @GET("get_category.php")
    Single<List<Categories>> getCategory();

    @GET("banner.php")
    Single<List<Banners>> getBanners();

    @FormUrlEncoded
    @POST("single_product.php")
    Single<Products> getSingleProduct(@Field("id") int idProduct);

    @FormUrlEncoded
    @POST("get_color.php")
    Single<List<Colors>> getColor(@Field("product_id") int idProduct);

    @GET("get_comment.php")
    Single<List<CommentProduct>> getCommentProduct(@Query("product_id") int idProduct);


    @FormUrlEncoded
    @POST("rg.php")
    Single<SuccessRepose> getRegisterUser(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("login.php")
    Single<Token> getLoginUserToken(@Field("email") String email, @Field("password") String password);


    @POST("insert_comment.php")
    Single<SuccessRepose> addComment(@Body JsonObject jsonObject);

    @GET("banner_action.php")
    Single<List<Products>> getCategoryList(@Query("id_category") int idCategory);


    @POST("add_tocart.php")
    Single<SuccessRepose> addToCart(@Body JsonObject jsonObject);


    @GET("get_list_cart.php")
    Single<CartItem> getListCart();

    @POST("remove_cart.php")
    Single<SuccessRepose> removeToCart(@Body JsonObject jsonObject);

    @POST("change_count.php")
    Single<ChangeModelItem> changeCountCart(@Body JsonObject jsonObject);

    @GET("get_count_product.php")
    Single<CountProduct> countProduct();


    @POST("orders.php")
    Single<SuccessRepose> addOrder(@Body JsonObject jsonObject);


    @GET("get_order_history.php")
    Single<List<OrderHistory>> orderHistory();


    @FormUrlEncoded
    @POST("get_order_detaile.php")
    Single<List<OrderDetials>> getOrderDetails(@Field("ref_id") String refId);


    @FormUrlEncoded
    @POST("search.php")
    Single<List<Products>>   getResultProduct(@Field("search") String search);
}
