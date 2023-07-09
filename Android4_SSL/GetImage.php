<?php
$hostname = "10.0.139.42";
$username = "pengu";
$password = "123456";
$database = "android3";

try {
    $conn = new PDO("mysql:host=$hostname;dbname=$database", $username, $password);
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    // Thực hiện truy vấn cơ sở dữ liệu để lấy danh sách hình ảnh
    $query = "SELECT image_url FROM images";
    $stmt = $conn->prepare($query);
    $stmt->execute();
    $result = $stmt->fetchAll(PDO::FETCH_ASSOC);

    // Trả về danh sách liên kết hình ảnh dưới dạng JSON
    echo json_encode($result);
} catch (PDOException $e) {
    echo "Lỗi kết nối cơ sở dữ liệu: " . $e->getMessage();
}
?>
