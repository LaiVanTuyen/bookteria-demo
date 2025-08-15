# Giới thiệu về WebSocket trong Dự án Bookteria

## WebSocket là gì?
WebSocket là một giao thức mạng cho phép thiết lập một kết nối hai chiều (full-duplex) giữa client (trình duyệt, ứng dụng di động, ...) và server. Khác với HTTP truyền thống (request/response), WebSocket cho phép server chủ động gửi dữ liệu về client bất cứ lúc nào mà không cần client phải gửi request trước. Điều này rất phù hợp cho các ứng dụng thời gian thực như chat, thông báo, game, ...

## Ưu điểm của WebSocket
- **Giao tiếp hai chiều (full-duplex):** Client và server có thể gửi/nhận dữ liệu bất cứ lúc nào.
- **Độ trễ thấp:** Không cần thiết lập lại kết nối cho mỗi lần gửi dữ liệu như HTTP.
- **Tiết kiệm băng thông:** Giảm overhead so với HTTP vì không phải gửi header lặp lại nhiều lần.
- **Thích hợp cho ứng dụng thời gian thực:** Chat, thông báo, cập nhật trạng thái, ...

## Ứng dụng WebSocket trong Bookteria
Trong dự án Bookteria, WebSocket được sử dụng chủ yếu cho các tính năng:
- **Chat thời gian thực:** Người dùng có thể nhắn tin và nhận tin nhắn ngay lập tức mà không cần reload trang.
- **Thông báo (notification):** Khi có sự kiện mới (tin nhắn, bình luận, ...), server có thể chủ động gửi thông báo đến client.

## Cách hoạt động cơ bản
1. **Client** gửi yêu cầu kết nối WebSocket đến **server**.
2. **Server** chấp nhận kết nối và giữ kết nối mở.
3. Khi có sự kiện mới (ví dụ: tin nhắn mới), **server** gửi dữ liệu về **client** qua kết nối WebSocket.
4. **Client** nhận dữ liệu và cập nhật giao diện ngay lập tức.

## Ví dụ luồng chat sử dụng WebSocket
1. Người dùng A gửi tin nhắn cho người dùng B.
2. Server nhận tin nhắn và lưu vào database.
3. Server gửi tin nhắn này đến người dùng B (nếu đang online) qua WebSocket.
4. Người dùng B nhận được tin nhắn ngay lập tức trên giao diện.

## Công nghệ sử dụng
- **Spring Boot WebSocket** (ở backend Java)
- **Socket.IO** hoặc **WebSocket API** (ở frontend web/mobile)

## Tài liệu tham khảo
- [MDN WebSocket](https://developer.mozilla.org/en-US/docs/Web/API/WebSocket)
- [Spring WebSocket Documentation](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#websocket)

---

> Nếu bạn muốn tìm hiểu chi tiết về cách triển khai WebSocket trong từng service, hãy xem README.md của từng service liên quan (ví dụ: chat-service, notification-service).

