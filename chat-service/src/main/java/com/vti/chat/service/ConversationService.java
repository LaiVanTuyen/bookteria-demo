package com.vti.chat.service;

import com.vti.chat.dto.request.ConversationRequest;
import com.vti.chat.dto.response.ConversationResponse;
import com.vti.chat.entity.Conversation;
import com.vti.chat.entity.ParticipantInfo;
import com.vti.chat.exception.AppException;
import com.vti.chat.exception.ErrorCode;
import com.vti.chat.mapper.ConversationMapper;
import com.vti.chat.repository.ConversationRepository;
import com.vti.chat.repository.httpclient.ProfileClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ConversationService {
    // Repository thao tác với dữ liệu cuộc trò chuyện
    ConversationRepository conversationRepository;
    // Client để lấy thông tin hồ sơ người dùng từ service khác
    ProfileClient profileClient;

    // Mapper chuyển đổi giữa entity và response DTO
    ConversationMapper conversationMapper;

    /**
     * Lấy danh sách các cuộc trò chuyện mà người dùng hiện tại tham gia
     * @return Danh sách ConversationResponse
     */
    public List<ConversationResponse> myConversations() {
        String userID = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Conversation> conversations = conversationRepository.findAllByParticipantIdsContains(userID);
        // Nếu conversations là null thì trả về danh sách rỗng để tránh lỗi
        if (conversations == null) {
            return List.of();
        }
        return conversations.stream().map(this::toConversationResponse).toList();
        //return conversations.stream().map(conversationMapper::toConversationResponse).toList();
    }

    /**
     * Tạo mới một cuộc trò chuyện giữa người dùng hiện tại và người tham gia khác
     * @param request Thông tin tạo cuộc trò chuyện
     * @return ConversationResponse
     */
    public ConversationResponse create(ConversationRequest request) {
        // Lấy thông tin người dùng hiện tại và người tham gia
        String userID = SecurityContextHolder.getContext().getAuthentication().getName();
        var userInfoResponse = profileClient.getProfile(userID);
        var participantInfoResponse = profileClient.getProfile(
                request.getParticipantIds().getFirst());
        if (Objects.isNull(userInfoResponse) || Objects.isNull(participantInfoResponse)){
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }

        var userInfo = userInfoResponse.getResult();
        var participantInfo = participantInfoResponse.getResult();

        // Tạo danh sách ID người tham gia và sinh ra hash
        List<String> userIds = new ArrayList<>();
        userIds.add(userID);
        userIds.add(participantInfo.getUserId());

        var sortedIds = userIds.stream().sorted().toList();
        String userIdsHash = generateParticipantHash(sortedIds);

        var conversation = conversationRepository.findByParticipantsHash(userIdsHash)
                .orElseGet(() ->{
                    // Tạo danh sách thông tin người tham gia
                    List<ParticipantInfo> participantInfos = List.of(
                            ParticipantInfo.builder()
                                    .userId(userInfo.getUserId())
                                    .username(userInfo.getUsername())
                                    .firstName(userInfo.getFirstName())
                                    .lastName(userInfo.getLastName())
                                    .avatar(userInfo.getAvatar())
                                    .build(),
                            ParticipantInfo.builder()
                                    .userId(participantInfo.getUserId())
                                    .username(participantInfo.getUsername())
                                    .firstName(participantInfo.getFirstName())
                                    .lastName(participantInfo.getLastName())
                                    .avatar(participantInfo.getAvatar())
                                    .build()
                    );

                    // Khởi tạo đối tượng Conversation và lưu vào database
                    Conversation newConversation = Conversation.builder()
                            .type(request.getType())
                            .participantsHash(userIdsHash)
                            .createdDate(Instant.now())
                            .modifiedDate(Instant.now())
                            .participants(participantInfos)
                            .build();
                    return conversationRepository.save(newConversation);
                });

        return toConversationResponse(conversation);
    }

    /**
     * Sinh ra chuỗi hash từ danh sách ID người tham gia (phục vụ kiểm tra trùng lặp cuộc trò chuyện)
     */
    private String generateParticipantHash(List<String> ids) {
        StringJoiner stringJoiner = new StringJoiner("_");
        ids.forEach(stringJoiner::add);
        return stringJoiner.toString();
    }

    /**
     * Chuyển đổi entity Conversation sang ConversationResponse, đồng thời lấy tên và avatar của đối phương
     */
    private ConversationResponse toConversationResponse(Conversation conversation) {
        String currentUserId = SecurityContextHolder.getContext().getAuthentication().getName();

        ConversationResponse conversationResponse = conversationMapper.toConversationResponse(conversation);

        conversation.getParticipants().stream()
                .filter(participantInfo -> !participantInfo.getUserId().equals(currentUserId))
                .findFirst().ifPresent(participantInfo -> {
                    conversationResponse.setConversationName(participantInfo.getUsername());
                    conversationResponse.setConversationAvatar(participantInfo.getAvatar());
                });

        return conversationResponse;
    }
}
