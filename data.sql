/* category
id    |	name		     |description
1	Văn học hiện đại	Tác phẩm của các tác giả đương đại, phản ánh những vấn đề xã hội hiện tại với lối kể chuyện sáng tạo và phong phú.
2	Văn học kinh điển	Những tác phẩm văn học đã vượt qua thử thách của thời gian, mang giá trị văn hóa và triết lý sâu sắc.
3	Tản văn			    Những mẩu truyện ngắn, suy tư và trải nghiệm đời thường, mang lại sự thư giãn và chiêm nghiệm cuộc sống.

- pubisher
id | name
1    Nhà xuất bản Hội Nhà Văn
2    Nhà xuất bản Văn Học
2    Nhà xuất bản Phụ Nữ
3    Nhà xuất bản Thế Giới
- author
authorID | name 
1          Salman Rushdie
2          Haruki Murakami
3          Vladmir Nabokov
4          Harper Lee
5		   W.somerset Maugham
6 		   Kawabata Yasunari
7          Charles Dickens
8          Frances Hodgson Burnett
9		   Edmondo De Amicis
10 		   Hiên 
11 		   TRƯƠNG ANH NGỌC
- book
booID |  authorID |publisherID  |  categoryID | title			            | description | ISBN 	      | costPrice | sellingPrice | stock | imageURL 			               | publisherYear | language   | 
 1 	    	1		 1              1           NHỮNG ĐỨA CON CỦA NỬA ĐÊM                   8935235201828                                100    nhung_dua_co_cua_nua_dem.jpg             2024		  Tiếng Việt
 2       	2		 2              1           KAFKA BÊN BỜ BIỂN				            9786043722352                                100    kafka_ben_bo_bien.jpg                    2024		  Tiếng Việt
 3       	2	     1              1           NHỮNG NGƯỜI ĐÀN ÔNG KHÔNG CÓ ĐÀN BÀ         8935235230347                                101    nhung_nguoi_dan_ong_khong_co_dan_ba.jpg  2021         Tiếng Việt
 4      	2	     1              1           RỪNG NA UY                                  8935235242425                                100    rung_na_uy.jpg                           2010         Tiếng việt
 5       	3		 1              1           LOLITA                                      9780679723165                                100    lolita.jpg                               2012         Tiếng việt
 6		    4	     1              2           Giết CON CHIM NHẠI                          8935235241831                                       giet_con_chim_nhai.jpg                    
 7          5        1              2           MẶT TRĂNG VÀ ĐỒNG XU                        9786041259140                                       mat_trang_va_dong_xu.jpg
 8		   	6		 2				2           XỨ TUYẾT                                    8935250701594                                       xu_tuyet.jpg
 9			7		 1 				2	        NHỮNG KỲ VỌNG LỚN LAO						8935212365987						                nhung_ky_vong_lon_lao.jpg
 10			8		 1			    2           CÔNG CHÚA NHỎ                               9786042271981 										cong_chua_nho.jpg
 11			9		 3				2           TÂM HỒN CAO THƯỢNG                          8935235208780                                       tam_hon_cao_thuong.jpg
 12         10       2              3           VẪN LÀ MÙA HẠ NHƯNG KHÔNG CÒN CHÚNG TA      8935325012907										van_la_mua_ha_nhung_khong_con_chung_ta.jpg
 13         10       2              3           DEAR DARLING  								9781511923125										dear_darling.jpg
 14         11       3              3           ĐI KHI TA CÒN TRẺ 							8935235235380										di_khi_ta_con_tre.jpg
 - authordetail
booID |  authorID  		
 1 	    	1	
 2       	2		
 3       	2	    
 4      	2	    
 5       	3		
 6		    4	   
 7          5      
 8		   	6		
 9			7			
 10			8		
 11			9		 
 12         10     
 13         10       
 14         11  
 */
 use bookstore;
 -- category
 INSERT INTO category (id, name, description) VALUES
(1, 'Văn học hiện đại', 'Tác phẩm của các tác giả đương đại, phản ánh những vấn đề xã hội hiện tại với lối kể chuyện sáng tạo và phong phú.'),
(2, 'Văn học kinh điển', 'Những tác phẩm văn học đã vượt qua thử thách của thời gian, mang giá trị văn hóa và triết lý sâu sắc.'),
(3, 'Tản văn', 'Những mẩu truyện ngắn, suy tư và trải nghiệm đời thường, mang lại sự thư giãn và chiêm nghiệm cuộc sống.');
-- publisher
INSERT INTO publisher (id, name) VALUES
(1, 'Nhà xuất bản Hội Nhà Văn'),
(2, 'Nhà xuất bản Văn Học'),
(3, 'Nhà xuất bản Phụ Nữ'),
(4, 'Nhà xuất bản Thế Giới');
-- author
INSERT INTO author (authorID, name) VALUES
(1, 'Salman Rushdie'),
(2, 'Haruki Murakami'),
(3, 'Vladimir Nabokov'),
(4, 'Harper Lee'),
(5, 'W. Somerset Maugham'),
(6, 'Kawabata Yasunari'),
(7, 'Charles Dickens'),
(8, 'Frances Hodgson Burnett'),
(9, 'Edmondo De Amicis'),
(10, 'Hiên'),
(11, 'Trương Anh Ngọc');
-- book 
INSERT INTO book (bookID, publisherID, categoryID, title, description, Isbn, costPrice,sellingPrice, imageURL, publisherYear, language) VALUES
(1, 1, 1, 'NHỮNG ĐỨA CON CỦA NỬA ĐÊM', NULL, '8935235201828', 100, 'nhung_dua_co_cua_nua_dem.jpg', 2024, 'Tiếng Việt'),
(2, 2, 1, 'KAFKA BÊN BỜ BIỂN', NULL, '9786043722352', 100, 'kafka_ben_bo_bien.jpg', 2024, 'Tiếng Việt'),
(3, 1, 1, 'NHỮNG NGƯỜI ĐÀN ÔNG KHÔNG CÓ ĐÀN BÀ', NULL, '8935235230347', 101, 'nhung_nguoi_dan_ong_khong_co_dan_ba.jpg', 2021, 'Tiếng Việt'),
(4, 1, 1, 'RỪNG NA UY', NULL, '8935235242425', 100, 'rung_na_uy.jpg', 2010, 'Tiếng Việt'),
(5, 1, 1, 'LOLITA', NULL, '9780679723165', 100, 'lolita.jpg', 2012, 'Tiếng Việt'),
(6, 1, 2, 'Giết CON CHIM NHẠI', NULL, '8935235241831', NULL, 'giet_con_chim_nhai.jpg', NULL, 'Tiếng Việt'),
(7, 1, 2, 'MẶT TRĂNG VÀ ĐỒNG XU', NULL, '9786041259140', NULL, 'mat_trang_va_dong_xu.jpg', NULL, 'Tiếng Việt'),
(8, 2, 2, 'XỨ TUYẾT', NULL, '8935250701594', NULL, 'xu_tuyet.jpg', NULL, 'Tiếng Việt'),
(9, 1, 2, 'NHỮNG KỲ VỌNG LỚN LAO', NULL, '8935212365987', NULL, 'nhung_ky_vong_lon_lao.jpg', NULL, 'Tiếng Việt'),
(10, 1, 2, 'CÔNG CHÚA NHỎ', NULL, '9786042271981', NULL, 'cong_chua_nho.jpg', NULL, 'Tiếng Việt'),
(11, 3, 2, 'TÂM HỒN CAO THƯỢNG', NULL, '8935235208780', NULL, 'tam_hon_cao_thuong.jpg', NULL, 'Tiếng Việt'),
(12, 2, 3, 'VẪN LÀ MÙA HẠ NHƯNG KHÔNG CÒN CHÚNG TA', NULL, '8935325012907', NULL, 'van_la_mua_ha_nhung_khong_con_chung_ta.jpg', NULL, 'Tiếng Việt'),
(13, 2, 3, 'DEAR DARLING', NULL, '9781511923125', NULL, 'dear_darling.jpg', NULL, 'Tiếng Việt'),
(14, 3, 3, 'ĐI KHI TA CÒN TRẺ', NULL, '8935235235380', NULL, 'di_khi_ta_con_tre.jpg', NULL, 'Tiếng Việt');
-- authordetail 
INSERT INTO authordetail (bookID, authorID) VALUES
(1, 1),
(2, 2),
(3, 2),
(4, 2),
(5, 3),
(6, 4),
(7, 5),
(8, 6),
(9, 7),
(10, 8),
(11, 9),
(12, 10),
(13, 10),
(14, 11);






 
											