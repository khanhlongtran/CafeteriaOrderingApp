package com.example.cafeteriaorderingapp.Dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RestaurantDetail {

    @SerializedName("addressId")
    private int addressId;

    @SerializedName("userId")
    private int userId;

    @SerializedName("fullName")
    private String fullName;

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

    @SerializedName("phone")
    private String phone;

    @SerializedName("menus")
    private List<Menu> menus;


    // Getters và Setters
    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getGeoLocation() {
        return geoLocation;
    }

    public void setGeoLocation(String geoLocation) {
        this.geoLocation = geoLocation;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

    // Lớp Menu
    public static class Menu {
        @SerializedName("menuId")
        private int menuId;

        @SerializedName("menuName")
        private String menuName;

        @SerializedName("isStatus")
        private boolean isStatus;

        @SerializedName("menuItems")
        private List<MenuItem> menuItems;

        public int getMenuId() {
            return menuId;
        }

        public void setMenuId(int menuId) {
            this.menuId = menuId;
        }

        public String getMenuName() {
            return menuName;
        }

        public void setMenuName(String menuName) {
            this.menuName = menuName;
        }

        public boolean isStatus() {
            return isStatus;
        }

        public void setStatus(boolean status) {
            isStatus = status;
        }

        public List<MenuItem> getMenuItems() {
            return menuItems;
        }

        public void setMenuItems(List<MenuItem> menuItems) {
            this.menuItems = menuItems;
        }
    }

    // Lớp MenuItem
    public static class MenuItem {
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

        public int getItemId() {
            return itemId;
        }

        public void setItemId(int itemId) {
            this.itemId = itemId;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getItemType() {
            return itemType;
        }

        public void setItemType(String itemType) {
            this.itemType = itemType;
        }

        public boolean isStatus() {
            return isStatus;
        }

        public void setStatus(boolean status) {
            isStatus = status;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}
