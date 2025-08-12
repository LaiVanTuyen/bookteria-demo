# File Service

## Sequence Diagram
![img.png](img.png)

## 📄 API GET /media/xxxx Handling Process

### Vietnamese
1. Người dùng gửi request GET /media/xxxx đến FileController.
2. FileController gọi phương thức get() của FileService.
3. FileService gọi lấy thông tin file từ FileManagementRepository để lấy metadata (thông tin file).
4. FileManagementRepository truy vấn Database (find) để lấy metadata và trả về cho FileService.
5. FileService tiếp tục gọi đọc file từ FileRepository.
6. FileRepository đọc file từ File Storage và trả nội dung file về.
7. FileService tổng hợp cả file và metadata, trả về cho FileController.
8. FileController trả response cho người dùng, gồm file và metadata.

### English
1. User sends a GET /media/xxxx request to FileController.
2. FileController calls the get() method of FileService.
3. FileService requests file info from FileManagementRepository to get metadata.
4. FileManagementRepository queries the database (find) to retrieve metadata and returns it to FileService.
5. FileService then calls read file from FileRepository.
6. FileRepository reads the file from File Storage and returns the file content.
7. FileService combines both file and metadata, and returns them to FileController.
8. FileController responds to the user with the file and metadata.
