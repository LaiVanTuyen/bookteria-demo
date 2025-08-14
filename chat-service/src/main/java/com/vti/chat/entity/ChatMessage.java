package com.vti.chat.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.Instant;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "chat_message")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChatMessage {
    @MongoId
    String id;

    @Indexed
    String conversationId;

    String message;

    // Thông tin của người gửi tin nhắn
    ParticipantInfo sender;

    @Indexed
    Instant createdDate;
}

/*
  @Indexed là annotation của Spring Data MongoDB dùng để tạo chỉ mục (index) cho một trường trong entity.
 * Khi trường được đánh dấu @Indexed, MongoDB sẽ tự động tạo index cho trường đó trong collection tương ứng.
 * Index giúp tăng tốc độ truy vấn (tìm kiếm, lọc, sắp xếp) trên trường này, đặc biệt khi dữ liệu lớn.
 * Tuy nhiên, việc tạo quá nhiều index có thể làm chậm quá trình ghi (insert/update) và tốn thêm dung lượng lưu trữ.
 */
