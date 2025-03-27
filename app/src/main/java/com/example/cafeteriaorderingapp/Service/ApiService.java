package com.example.cafeteriaorderingapp.Service;

import com.example.cafeteriaorderingapp.Dto.DelivererOrder;
import com.example.cafeteriaorderingapp.Dto.Delivery;
import com.example.cafeteriaorderingapp.Dto.LoginRequest;
import com.example.cafeteriaorderingapp.Dto.LoginResponse;
import com.example.cafeteriaorderingapp.Dto.MealDetail;
import com.example.cafeteriaorderingapp.Dto.MealRecommendation;
import com.example.cafeteriaorderingapp.Dto.Order;
import com.example.cafeteriaorderingapp.Dto.RestaurantDetail;
import com.example.cafeteriaorderingapp.Dto.RestaurantRecommendation;
import com.example.cafeteriaorderingapp.Dto.UserAddressCuisineResponse;
import com.example.cafeteriaorderingapp.Vnpay.PaymentOrderRequest;
import com.example.cafeteriaorderingapp.Vnpay.PaymentVNPAYResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("recommendMeals")
    Call<List<MealRecommendation>> getRecommendedMeals(@Query("user_id") int userId);

    @GET("api/Patron/item/{itemId}")
    Call<MealDetail> getMealDetail(@Path("itemId") int itemId);

    @GET("recommendRestaurants")
    Call<List<RestaurantRecommendation>> getRecommendedRestaurants(@Query("user_id") int userId);

    @GET("api/Patron/restaurant/menu/{menuId}")
    Call<RestaurantDetail> getRestaurantDetail(@Path("menuId") int menuId);

    @POST("api/Checkout/VnpayTransfer")
    Call<PaymentVNPAYResponse> createVnpayPayment(@Body PaymentOrderRequest request);

    @POST("api/Accounts/login")
    Call<LoginResponse> login(@Body LoginRequest request);

    @GET("api/Patron/GetDefaultAddress/{userId}")
    Call<UserAddressCuisineResponse> getDefaultAddress(@Path("userId") int userId);

    @GET("api/Patron/MyOrder/{userId}")
    Call<List<Order>> getUserOrders(@Path("userId") int userId);

    @GET("api/v1/delivery/GetAllOrders")
    Call<List<DelivererOrder>> getAllOrders();

    @GET("api/v1/delivery/GetOrderInProgessDeliveries/{delivererUserId}")
    Call<List<DelivererOrder.DeliveryInProgressOrder>> getOrderInProgressDeliveries(@Path("delivererUserId") int delivererUserId);

    @PUT("api/v1/delivery/UpdateOrderStatus/{orderId}")
    Call<Void> updateOrderStatus(@Path("orderId") int orderId, @Body String newStatus);

    @POST("api/v1/delivery/CreateDelivery")
    Call<Delivery.DeliveryResponse> createDelivery(@Body Delivery.DeliveryCreateDto deliveryCreateDto);
}

