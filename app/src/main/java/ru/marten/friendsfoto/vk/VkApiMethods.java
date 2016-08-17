package ru.marten.friendsfoto.vk;

/**
 * Created by marten on 17.08.16.
 */
enum VkApiMethods {
    USERS_GET("users.get"),
    FRIENDS_GET("friends.get");

    String method;

    VkApiMethods (String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }
}
