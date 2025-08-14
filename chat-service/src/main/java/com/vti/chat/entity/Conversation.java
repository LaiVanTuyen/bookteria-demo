package com.vti.chat.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.Instant;
import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "conversation")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Conversation {
    @MongoId
    String id;

    /**
     * Conversation type:
     * - DIRECT: One-on-one chat between two users.
     * - GROUP: Chat with multiple participants.
     */
    String type; // GROUP, DIRECT

    /**
     * A unique hash string representing the set of participants in the conversation.
     * Used to quickly identify and prevent duplicate conversations with the same members.
     * For DIRECT, it is the hash of two user IDs. For GROUP, it is the hash of all user IDs in the group.
     */
    @Indexed(unique = true)
    String participantsHash;

    /**
     * Danh sách các thành viên tham gia cuộc trò chuyện.
     * Mỗi ParticipantInfo chứa thông tin về một người dùng (userId, tên, vai trò, trạng thái, ...).
     * Dùng cho cả DIRECT (2 người) và GROUP (nhiều người).
     */
    List<ParticipantInfo> participants;

    Instant createdDate;

    Instant modifiedDate;
}
