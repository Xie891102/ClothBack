package com.cloth.dto;


import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class ProductDTO {

	private Integer id;
	private String name;
	private String description;
	private Integer quantity;
	private String size;
	private Integer price;
	private Integer discount_price;
	private byte[] img;
	private byte[] img2;
	private byte[] img3;
	private byte[] img4;
	private String status;
	private String color;
	private String type;
	private String gender;
	private String style;
	private Date createdAt;
	private String updatedBy;
	private Integer activityId;
	
	
}
