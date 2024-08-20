//package com.cloth.service;
//
//import java.util.ArrayList;
//import java.util.Base64;
//import java.util.Date;
//import java.util.List;
//import java.util.Optional;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.cloth.Repository.ActivityRepository;
//import com.cloth.Repository.ProductRepository;
//import com.cloth.model.Activity;
//import com.cloth.model.Product;
//
//
//@Service
//@Transactional
//public class ProductService {
//	@Autowired
//	private ProductRepository productRepository;
//	
//	@Autowired
//	private ActivityRepository activityRepository;
//
//	public boolean existsByName(String name) {
//		if(name!=null && name.length()!=0) {
//			return productRepository.countByName(name)!=0;
//		}
//		return false;
//	}
//
//	public Product modify(String json) {
//		try {
//			JSONObject obj = new JSONObject(json);
//			
//			Integer id = obj.isNull("id") ? null : obj.getInt("id");
//			String name = obj.isNull("name") ? null : obj.getString("name");			
//			String description = obj.isNull("description") ? null : obj.getString("description");			
//			String category = obj.isNull("category") ? null : obj.getString("category");					    
//			String activityName = obj.isNull("activity") ? null : obj.getString("activity");
//			Integer quantity = obj.isNull("quantity") ? null : obj.getInt("quantity");			
//			String size = obj.isNull("size") ? null : obj.getString("size");			
//			Integer price = obj.isNull("price") ? null : obj.getInt("price");			
//			Integer discount_price = obj.isNull("discount_price") ? null : obj.getInt("discount_price");			
//			byte[] img = obj.isNull("img") ? null : Base64.getDecoder().decode(obj.getString("img"));			
//			byte[] img2 = obj.isNull("img2") ? null : Base64.getDecoder().decode(obj.getString("img2"));
//			byte[] img3 = obj.isNull("img3") ? null : Base64.getDecoder().decode(obj.getString("img3"));			
//			byte[] img4 = obj.isNull("img4") ? null : Base64.getDecoder().decode(obj.getString("img4"));		
//			String status = obj.isNull("status") ? null : obj.getString("status");			
//			String color = obj.isNull("color") ? null : obj.getString("color");			
//			String type = obj.isNull("type") ? null : obj.getString("type");			
//			String gender = obj.isNull("gender") ? null : obj.getString("gender");			
//			String style = obj.isNull("style") ? null : obj.getString("style");			
//			String updated_by = obj.isNull("updated_by") ? null : obj.getString("updated_by");
//		 
//
//			Optional<Product> optional = productRepository.findById(id);
//			if(optional.isPresent()) {
//				Product update = optional.get();
//			
//				update.setName(name);
//				update.setDescription(description);
//				update.setCategory(category);
//				Activity activity =activityRepository.findOrCreateActivity(activityName);
//		        update.setActivity(activity);
//				update.setQuantity(quantity);
//				update.setSize(size);
//				update.setPrice(price);
//				update.setDiscount_price(discount_price);
//				update.setImg(img);
//				update.setImg2(img2);
//				update.setImg3(img3);
//				update.setImg4(img4);
//				update.setStatus(status);
//				update.setColor(color);
//				update.setType(type);
//				update.setGender(gender);
//				update.setStyle(style);
//				update.setUpdated_at(new Date());
//				update.setUpdated_by(updated_by);
//
//				return productRepository.save(update);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	public Product create(String json,MultipartFile photoFile, MultipartFile photoFile2, MultipartFile photoFile3, MultipartFile photoFile4) {
//		try {
//			JSONObject obj = new JSONObject(json);
//			Integer id = obj.isNull("id") ? null : obj.getInt("id");
//			String name = obj.isNull("name") ? null : obj.getString("name");			
//			String description = obj.isNull("description") ? null : obj.getString("description");			
//			String category = obj.isNull("category") ? null : obj.getString("category");					    
//			String activityName = obj.isNull("activity") ? null : obj.getString("activity");
//			Integer quantity = obj.isNull("quantity") ? null : obj.getInt("quantity");			
//			String size = obj.isNull("size") ? null : obj.getString("size");			
//			Integer price = obj.isNull("price") ? null : obj.getInt("price");			
//			Integer discount_price = obj.isNull("discount_price") ? null : obj.getInt("discount_price");		
//			String status = obj.isNull("status") ? null : obj.getString("status");			
//			String color = obj.isNull("color") ? null : obj.getString("color");			
//			String type = obj.isNull("type") ? null : obj.getString("type");			
//			String gender = obj.isNull("gender") ? null : obj.getString("gender");			
//			String style = obj.isNull("style") ? null : obj.getString("style");			
//			String created_by = obj.isNull("created_by") ? null : obj.getString("created_by");
//			
//			Optional<Product> optional = productRepository.findById(id);
//			if(optional.isEmpty()) {
//				Product insert = new Product();
//				insert.setName(name);
//				insert.setDescription(description);
//				insert.setCategory(category);
//				Activity activity =activityRepository.findOrCreateActivity(activityName);
//				insert.setActivity(activity);
//				insert.setActivity(activity);
//				insert.setQuantity(quantity);
//				insert.setSize(size);
//				insert.setPrice(price);
//				insert.setDiscount_price(discount_price);
//				insert.setStatus(status);
//				insert.setColor(color);
//				insert.setType(type);
//				insert.setGender(gender);
//				insert.setStyle(style);
//				insert.setCreated_at(null);
//				insert.setCreated_by(created_by);
//				
//				if (photoFile != null && !photoFile.isEmpty()) {
//                    insert.setImg(photoFile.getBytes());
//                }
//                if (photoFile2 != null && !photoFile2.isEmpty()) {
//                    insert.setImg2(photoFile2.getBytes());
//                }
//                if (photoFile3 != null && !photoFile3.isEmpty()) {
//                    insert.setImg3(photoFile3.getBytes());
//                }
//                if (photoFile4 != null && !photoFile4.isEmpty()) {
//                    insert.setImg4(photoFile4.getBytes());
//                }
//
//				return productRepository.save(insert);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//	public boolean remove(Integer id) {
//		if(id!=null) {
//			Optional<Product> optional = productRepository.findById(id);
//			if(optional.isPresent()) {
//				productRepository.deleteById(id);
//				return true;
//			}
//		}
//		return false;
//	}
//	public boolean exists(Integer id) {
//		if(id!=null) {
//			return productRepository.existsById(id);
//		}
//		return false;
//	}
//	public Product findById(Integer id) {
//		if(id!=null) {
//			Optional<Product> optional = productRepository.findById(id);
//			if(optional.isPresent()) {
//				return optional.get();
//			}
//		}
//		return null;
//	}
//	public long count(String json) {
//		try {
//			JSONObject obj = new JSONObject(json);
//			return productRepository.count(obj);
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//		return 0;
//	}
//	public List<Product> find(String json) {
//		try {
//			JSONObject obj = new JSONObject(json);
//			return productRepository.find(obj);
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	public List<Product> select(Product bean) {
//		List<Product> result = null;
//		if(bean!=null && bean.getId()!=null && !bean.getId().equals(0)) {
//			Optional<Product> optional = productRepository.findById(bean.getId());
//			if(optional.isPresent()) {
//				result = new ArrayList<Product>();
//				result.add(optional.get());
//			}
//		} else {
//			result = productRepository.findAll();
//		}
//		return result;
//	}
//	public Product insert(Product bean) {
//		if(bean!=null && bean.getId()!=null) {
//			Optional<Product> optional = productRepository.findById(bean.getId());
//			if(optional.isEmpty()) {
//				return productRepository.save(bean);
//			}
//		}
//		return null;
//	}
//	public Product update(Product bean) {
//		if(bean!=null && bean.getId()!=null) {
//			Optional<Product> optional = productRepository.findById(bean.getId());
//			if(optional.isPresent()) {
//				return productRepository.save(bean);
//			}
//		}
//		return null;
//	}
//	public boolean delete(Product bean) {
//		if(bean!=null && bean.getId()!=null) {
//			Optional<Product> optional = productRepository.findById(bean.getId());
//			if(optional.isPresent()) {
//				productRepository.deleteById(bean.getId());
//				return true;
//			}
//		}
//		return false;
//	}
//}
