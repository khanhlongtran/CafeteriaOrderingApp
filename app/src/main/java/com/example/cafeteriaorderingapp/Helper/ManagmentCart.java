package com.example.cafeteriaorderingapp.Helper;

import android.content.Context;
import android.widget.Toast;


import com.example.cafeteriaorderingapp.Domain.Foods;
import com.example.cafeteriaorderingapp.Dto.RestaurantDetail;

import java.util.ArrayList;


public class ManagmentCart {
    private Context context;
    private TinyDB tinyDB;

    public ManagmentCart(Context context) {
        this.context = context;
        this.tinyDB = new TinyDB(context);
    }


    public void insertFood(RestaurantDetail.Menu foodItem) {
        ArrayList<RestaurantDetail.Menu> cartList = getListCart();
        boolean existAlready = false;
        int index = -1;

        for (int i = 0; i < cartList.size(); i++) {
            if (cartList.get(i).getItemName().equals(foodItem.getItemName())) {
                existAlready = true;
                index = i;
                break;
            }
        }

        if (existAlready) {
            cartList.get(index).setNumberInCart(foodItem.getNumberInCart());
        } else {
            cartList.add(foodItem);
        }

        tinyDB.putListObject("CartList", cartList);
        Toast.makeText(context, "Added to your Cart", Toast.LENGTH_SHORT).show();
    }


    public ArrayList<RestaurantDetail.Menu> getListCart() {
        return tinyDB.getListObject("CartList");
    }

    public double getTotalFee() {
        ArrayList<RestaurantDetail.Menu> cartList = getListCart();
        double totalFee = 0;

        for (RestaurantDetail.Menu foodItem : cartList) {
            totalFee += foodItem.getPrice() * foodItem.getNumberInCart();
        }

        return totalFee;
    }

    public void minusNumberItem(ArrayList<RestaurantDetail.Menu> cartList, int position, ChangeNumberItemsListener changeNumberItemsListener) {
        if (cartList.get(position).getNumberInCart() == 1) {
            cartList.remove(position);
        } else {
            cartList.get(position).setNumberInCart(cartList.get(position).getNumberInCart() - 1);
        }

        tinyDB.putListObject("CartList", cartList);
        changeNumberItemsListener.change();
    }

    public void plusNumberItem(ArrayList<RestaurantDetail.Menu> cartList, int position, ChangeNumberItemsListener changeNumberItemsListener) {
        cartList.get(position).setNumberInCart(cartList.get(position).getNumberInCart() + 1);
        tinyDB.putListObject("CartList", cartList);
        changeNumberItemsListener.change();
    }

    public void removeItem(ArrayList<RestaurantDetail.Menu> cartList, int position, ChangeNumberItemsListener changeNumberItemsListener) {
        cartList.remove(position);
        tinyDB.putListObject("CartList", cartList);
        changeNumberItemsListener.change();
    }

    public void removeAllItems() {
        tinyDB.remove("CartList"); // Xóa toàn bộ danh sách giỏ hàng trong TinyDB
        Toast.makeText(context, "Cart has been cleared", Toast.LENGTH_SHORT).show();
    }

}
