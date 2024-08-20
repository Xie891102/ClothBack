package com.cloth.Repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cloth.model.Customerservice;

@Repository
public interface CostomerServRepository extends JpaRepository<Customerservice, Integer> {

	
// 根據使用者 ID 計算未讀訊息數量‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵
    @Query("SELECT COUNT(c) FROM Customerservice c WHERE c.users.id = :userId AND c.isRead = false")
    long countUnreadMessagesByUserid(@Param("userId") Integer userId);

    
// 根據使用者 ID 查詢聊天歷史紀錄‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵
    @Query("SELECT c FROM Customerservice c WHERE c.users.id = :userId ORDER BY c.send_at DESC")
    List<Customerservice> findChatHistoryByUserId(@Param("userId") Integer userId);

    
// 根據使用者 ID 和日期範圍查詢聊天歷史紀錄‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵‵
    @Query("SELECT c FROM Customerservice c WHERE c.users.id = :userId AND c.send_at BETWEEN :startDate AND :endDate ORDER BY c.send_at DESC")
    List<Customerservice> findChatHistoryByUserIdAndDateRange(
        @Param("userId") Integer userId, 
        @Param("startDate") Date startDate, 
        @Param("endDate") Date endDate
    );
}
