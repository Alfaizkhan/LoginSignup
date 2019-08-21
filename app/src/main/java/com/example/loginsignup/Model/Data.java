package com.example.loginsignup.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

        @SerializedName("id")
        @Expose
        private String id;

        @SerializedName("email")
        @Expose
        private String email;

        @SerializedName("first_name")
        @Expose
        private String firstName;

        @SerializedName("last_name")
        @Expose
        private String lastName;

        @SerializedName("avatar")
        @Expose
        private String  avatar;

        public Data(String id, String email, String firstName, String lastName, String avatar) {
                this.id = id;
                this.email = email;
                this.firstName = firstName;
                this.lastName = lastName;
                this.avatar = avatar;
        }

        public String getId() {

                return id;
        }

        public void setId(String id) {

                this.id = id;
        }

        public String getEmail() {

                return email;
        }

        public void setEmail(String email) {

                this.email = email;
        }

        public String getFirstName() {
                return firstName;
        }

        public void setFirstName(String firstName) {

                this.firstName = firstName;
        }

        public String getLastName() {

                return lastName;
        }

        public void setLastName(String lastName) {

                this.lastName = lastName;
        }

        public String getAvatar() {

                return avatar;
        }

        public void setAvatar(String avatar) {
                this.avatar = avatar;
        }

}