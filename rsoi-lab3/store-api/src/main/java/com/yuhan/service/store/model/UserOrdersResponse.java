package com.yuhan.service.store.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yuhan
 * @date 07.11.2020 - 13:11
 * @purpose
 */
public class UserOrdersResponse extends ArrayList<UserOrderResponse> {

//    public UserOrdersResponse(List<UserOrderResponse> orders) {
//    }

    private List<UserOrderResponse> list;

    public UserOrdersResponse(List<UserOrderResponse> list) {
        this.list = list;
    }
}
