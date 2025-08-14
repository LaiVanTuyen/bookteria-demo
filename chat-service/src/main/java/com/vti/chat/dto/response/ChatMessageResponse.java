package com.vti.chat.dto.response;

import com.vti.chat.entity.ParticipantInfo;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChatMessageResponse {
    // ID của tin nhắn
    String id;
    // ID của cuộc trò chuyện mà tin nhắn này thuộc về
    String conversationId;
    // Đánh dấu tin nhắn này có phải do người dùng hiện tại gửi hay không
    boolean me;
    // Nội dung tin nhắn
    String message;
    // Thông tin người gửi tin nhắn
    ParticipantInfo sender;
    // Thời điểm tin nhắn được tạo
    Instant createdDate;
}
