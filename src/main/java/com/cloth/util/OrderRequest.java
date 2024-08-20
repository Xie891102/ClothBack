package com.cloth.util;

import java.util.List;

import com.cloth.model.Orderdetail;
import com.cloth.model.Orders;

public class OrderRequest {
	private Orders order;
    private List<Orderdetail> orderDetails;
	public Orders getOrder() {
		return order;
	}
	public void setOrder(Orders order) {
		this.order = order;
	}
	public List<Orderdetail> getOrderDetails() {
		return orderDetails;
	}
	public void setOrderDetails(List<Orderdetail> orderDetails) {
		this.orderDetails = orderDetails;
	}
    

}
