//package com.cloth.Controller;
//
//import java.util.Date;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.format.annotation.DateTimeFormat;
//import org.springframework.web.bind.annotation.*;
//
//import com.cloth.model.Customerservice;
//import com.cloth.service.CostomerServService;
//
//
//@RestController
//@RequestMapping("/customerservice")
//public class CostomerServController {
//
//    @Autowired
//    private CostomerServService service;
//
//// 獲取指定使用者的未讀訊息數量‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵
//    @GetMapping("/unread-count/{userId}")
//    public long getUnreadMessageCount(@PathVariable Integer userId) {
//        return service.getUnreadMessageCount(userId);
//    }
//
//// 獲取指定使用者的聊天歷史紀錄‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵
//    @GetMapping("/history/{userId}")
//    public List<Customerservice> getChatHistory(@PathVariable Integer userId) {
//        return service.getChatHistory(userId);
//    }
//
//// 獲取指定使用者在某個日期範圍內的聊天歷史紀錄‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵
//    @GetMapping("/history/date/{userId}")
//    public List<Customerservice> getChatHistoryByDateRange(
//        @PathVariable Integer userId,
//        @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
//        @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate
//    ) {
//        return service.getChatHistoryByDateRange(userId, startDate, endDate);
//    }
//
//// 發送訊息‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵
//    @PostMapping("/send")
//    public Customerservice sendMessage(
//        @RequestParam Integer userId,
//        @RequestParam Integer adminId,
//        @RequestParam String messageText,
//        @RequestParam(required = false) byte[] img1,
//        @RequestParam(required = false) byte[] img2,
//        @RequestParam(required = false) byte[] img3
//    ) {
//        return service.sendMessage(userId, adminId, messageText, img1, img2, img3);
//    }
//
//}
