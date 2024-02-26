-- --------------------------------------------------------
-- Host:                         localhost
-- Server version:               8.0.33 - MySQL Community Server - GPL
-- Server OS:                    Win64
-- HeidiSQL Version:             12.5.0.6677
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for android3
CREATE DATABASE IF NOT EXISTS `android3` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `android3`;

-- Dumping structure for table android3.images
CREATE TABLE IF NOT EXISTS `images` (
  `image_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table android3.images: ~5 rows (approximately)
REPLACE INTO `images` (`image_url`) VALUES
	('https://cdn.discordapp.com/attachments/1091303129858969620/1166393838802309140/346622651_279847937714838_4090213783140776374_n.jpg?ex=65e73669&is=65d4c169&hm=eecc1c9cb783cb43f89c90167485d901c52b67f52f842ab90c78c14ccf68b074&'),
	('https://cdn.discordapp.com/attachments/1091303129858969620/1211664554853933067/4686183_imgbin-shiba-inu-firefox-doge-computer-icons-doge-iBh2p3Jyi1BMT0xpb4pB8uJzM.jpg?ex=65ef058d&is=65dc908d&hm=3935572828c680fe447176150aa3fcd8f04c7d272ba02ccca9748d9ae7d085a4&'),
	('https://cdn.discordapp.com/attachments/1091303129858969620/1211664555109916672/meme_nhu_y_truyen_18.webp?ex=65ef058d&is=65dc908d&hm=47143d4b9f1cbad94a00c61a99b93c871f94be4c2c1c99c701737c633b24157c&'),
	('https://cdn.discordapp.com/attachments/1091303129858969620/1211664555433005056/meme-meo-bua-6.jpg?ex=65ef058d&is=65dc908d&hm=5abf968dfc89f0552a4d0578e1f57adc7615f8b906bdebbea210d8610126814a&'),
	('https://cdn.discordapp.com/attachments/1091303129858969620/1211664555676012605/meo-cam-bong-hoa-tren-tay-manh-me-len.jpg?ex=65ef058d&is=65dc908d&hm=d89310eee8d62cfe5da3d928d51d9a1d96b9a7a8d8197b537cc2251769742896&');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
