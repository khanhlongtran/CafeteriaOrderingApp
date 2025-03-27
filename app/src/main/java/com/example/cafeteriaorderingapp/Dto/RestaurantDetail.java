package com.example.cafeteriaorderingapp.Dto;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class RestaurantDetail {
    @SerializedName("menuId")
    private int menuId;

    @SerializedName("menuName")
    private String menuName;

    @SerializedName("isStatus")
    private boolean isStatus;

    @SerializedName("userId")
    private int userId;

    @SerializedName("fullName")
    private String fullName;

    @SerializedName("phone")
    private String phone;

    @SerializedName("addresses")
    private List<Address> addresses;

    @SerializedName("menus")  // Cập nhật: Sửa từ menuItems thành menus
    private List<Menu> menus;

    // Getters và Setters
    public int getMenuId() { return menuId; }
    public void setMenuId(int menuId) { this.menuId = menuId; }

    public String getMenuName() { return menuName; }
    public void setMenuName(String menuName) { this.menuName = menuName; }

    public boolean isStatus() { return isStatus; }
    public void setStatus(boolean status) { isStatus = status; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public List<Address> getAddresses() { return addresses; }
    public void setAddresses(List<Address> addresses) { this.addresses = addresses; }

    public List<Menu> getMenus() { return menus; }  // Thêm getter cho menus
    public void setMenus(List<Menu> menus) { this.menus = menus; }

    // Lớp Address (địa chỉ nhà hàng)
    public static class Address {
        @SerializedName("addressId")
        private int addressId;
        @SerializedName("addressLine")
        private String addressLine;
        @SerializedName("city")
        private String city;
        @SerializedName("state")
        private String state;
        @SerializedName("zipCode")
        private String zipCode;
        @SerializedName("geoLocation")
        private String geoLocation;
        @SerializedName("image")
        private String image;

        // Getters và Setters
        public int getAddressId() { return addressId; }
        public void setAddressId(int addressId) { this.addressId = addressId; }
        public String getAddressLine() { return addressLine; }
        public void setAddressLine(String addressLine) { this.addressLine = addressLine; }
        public String getCity() { return city; }
        public void setCity(String city) { this.city = city; }
        public String getState() { return state; }
        public void setState(String state) { this.state = state; }
        public String getZipCode() { return zipCode; }
        public void setZipCode(String zipCode) { this.zipCode = zipCode; }
        public String getGeoLocation() { return geoLocation; }
        public void setGeoLocation(String geoLocation) { this.geoLocation = geoLocation; }
        public String getImage() { return image; }
        public void setImage(String image) { this.image = image; }
    }

    // Lớp Menu (danh sách món ăn)
    public static class Menu implements Serializable {
        @SerializedName("itemId")
        private int itemId;
        @SerializedName("itemName")
        private String itemName;
        @SerializedName("price")
        private double price;
        @SerializedName("description")
        private String description;
        @SerializedName("itemType")
        private String itemType;
        @SerializedName("isStatus")
        private boolean isStatus;
        @SerializedName("image")
        private String image;
        // Danh sách món ăn riêng của từng nhà hàng
        private int numberInCart;

        public int getNumberInCart() {
            return numberInCart;
        }

        public void setNumberInCart(int numberInCart) {
            this.numberInCart = numberInCart;
        }

        private List<Menu> menuList;

        public List<Menu> getMenuList() {
            return menuList;
        }

        public void setMenuList(List<Menu> menuList) {
            this.menuList = menuList;
        }

        // Getters và Setters
        public int getItemId() { return itemId; }
        public void setItemId(int itemId) { this.itemId = itemId; }
        public String getItemName() { return itemName; }
        public void setItemName(String itemName) { this.itemName = itemName; }
        public double getPrice() { return price; }
        public void setPrice(double price) { this.price = price; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public String getItemType() { return itemType; }

        @Override
        public String toString() {
            return "Menu{" +
                    "description='" + description + '\'' +
                    ", itemId=" + itemId +
                    ", itemName='" + itemName + '\'' +
                    ", price=" + price +
                    ", itemType='" + itemType + '\'' +
                    ", isStatus=" + isStatus +
                    ", image='" + image + '\'' +
                    '}';
        }

        public void setItemType(String itemType) { this.itemType = itemType; }
        public boolean isStatus() { return isStatus; }
        public void setStatus(boolean status) { isStatus = status; }
        public String getImage() { return image; }
        public void setImage(String image) { this.image = image; }
    }
}
