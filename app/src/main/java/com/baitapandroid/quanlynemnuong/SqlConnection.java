package com.baitapandroid.quanlynemnuong;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.baitapandroid.quanlynemnuong.model.ChiTietGiaoDichModel;
import com.baitapandroid.quanlynemnuong.model.GiaoDichModel;
import com.baitapandroid.quanlynemnuong.model.GioHangModel;
import com.baitapandroid.quanlynemnuong.model.KhachHangModel;
import com.baitapandroid.quanlynemnuong.model.NemNuongModel;
import com.baitapandroid.quanlynemnuong.model.UserModel;
import com.baitapandroid.quanlynemnuong.ui.activity.MainActivity;
import com.baitapandroid.quanlynemnuong.ui.activity.NhaPhanPhoiModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SqlConnection extends SQLiteOpenHelper {
    private Context context;

    public SqlConnection(Context ctx) {
        super(ctx, "quanly_nemnuong", null, 1);
        context = ctx;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        System.out.println("Initializing database");
        String[] statements = new String[]{
                "CREATE TABLE NGUOIDUNG (\n" +
                "  MAND INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "  HOTEN NVARCHAR(50) NOT NULL,\n" +
                "  GIOITINH BIT NOT NULL,\n" +
                "  NGAYSINH DATE NOT NULL,\n" +
                "  EMAIL VARCHAR(50) NOT NULL,\n" +
                "  SDT VARCHAR(15) NOT NULL,\n" +
                "  TAIKHOAN VARCHAR(50) NOT NULL,\n" +
                "  MATKHAU VARCHAR(50) NOT NULL,\n" +
                "  NHANVIEN BIT NOT NULL\n" +
                ");",
                "CREATE TABLE NHAPHANPHOI (\n" +
                "  MANPP INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "  TENNPP NVARCHAR(50) NOT NULL,\n" +
                "  DIACHI NVARCHAR(100) NOT NULL\n" +
                ");",
                "CREATE TABLE SANPHAM (\n" +
                "  MASP INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "  TENSP NVARCHAR(50) NOT NULL,\n" +
                "  HINHANH VARCHAR(100) NOT NULL,\n" +
                "  MOTA NVARCHAR(100),\n" +
                "  DONGIA INT CHECK (DONGIA > 0) NOT NULL,\n" +
                "  SAO FLOAT CHECK (SAO BETWEEN 0 AND 5) NOT NULL,\n" +
                "  MANPP INTEGER NOT NULL,\n" +
                "  FOREIGN KEY (MANPP) REFERENCES NHAPHANPHOI(MANPP)\n" +
                ");",
                "CREATE TABLE GIAODICH (\n" +
                "  MAGD INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "  MAKH INTEGER NOT NULL,\n" +
                "  MANV INTEGER NOT NULL,\n" +
                "  PHIVANCHUYEN INT CHECK (PHIVANCHUYEN >= 0) NOT NULL,\n" +
                "  THOIGIANDAT DATETIME NOT NULL,\n" +
                "  THOIGIANGIAO DATETIME NOT NULL,\n" +
                "  FOREIGN KEY (MAKH) REFERENCES NGUOIDUNG(MAND),\n" +
                "  FOREIGN KEY (MANV) REFERENCES NGUOIDUNG(MAND),\n" +
                "  CHECK (THOIGIANDAT < THOIGIANGIAO)\n" +
                ");",
                "CREATE TABLE CTGIAODICH (\n" +
                "  MAGD INTEGER,\n" +
                "  MASP INTEGER,\n" +
                "  SOLUONG INT CHECK (SOLUONG > 0) NOT NULL,\n" +
                "  PRIMARY KEY (MAGD, MASP),\n" +
                "  FOREIGN KEY (MAGD) REFERENCES GIAODICH(MAGD),\n" +
                "  FOREIGN KEY (MASP) REFERENCES SANPHAM(MASP)\n" +
                ");",
                "CREATE TABLE GIOHANG (\n" +
                "  MAND INTEGER,\n" +
                "  MASP INTEGER,\n" +
                "  SOLUONG INT CHECK (SOLUONG > 0) NOT NULL,\n" +
                "  PRIMARY KEY (MAND, MASP),\n" +
                "  FOREIGN KEY (MAND) REFERENCES NGUOIDUNG(MAND),\n" +
                "  FOREIGN KEY (MASP) REFERENCES SANPHAM(MASP)\n" +
                ");",
                "INSERT INTO NGUOIDUNG VALUES\n" +
                "  (0, 'Hà Công Tấn',                0, DATE('2003-06-07'), 'hacongtan218@gmail.com',            '0955461929', 'tk0', 'mk0', 0),\n" +
                "  (1, 'Trần Thị Ngọc Tuyết',        1, DATE('1993-05-20'), 'ngoctuyet12@gmail.com',             '0925726141', 'tk1', 'mk1', 0),\n" +
                "  (2, 'Lập Trường',                 0, DATE('2000-05-18'), 'laptruong45@gmail.com',             '0929479210', 'tk2', 'mk2', 0),\n" +
                "  (3, 'Nguyễn Kha Lâm',             1, DATE('2003-12-17'), 'khalam285@gmail.com',               '0992541278', 'tk3', 'mk3', 0),\n" +
                "  (4, 'Nguyễn Công Phượng',         0, DATE('2004-11-15'), 'congphuong@gmail.com',              '0904677976', 'tk4', 'mk4', 0),\n" +
                "  (5, 'Phương Mỹ Quỳnh',            1, DATE('2004-01-20'), 'phuongmyquynh@gmail.com',           '0984625894', 'tk5', 'mk5', 0),\n" +
                "  (6, 'Ngô Quang Nhật',             1, DATE('2006-02-28'), 'quanhat@gmail.com',                 '0950117212', 'tk6', 'mk6', 0),\n" +
                "  (7, 'Trí Khai Quốc',              0, DATE('1997-02-18'), 'khaiquoctri150@gmail.com',          '0964580273', 'tk7', 'mk7', 0),\n" +
                "  (8, 'Ngô Khắc Duy',               0, DATE('1996-01-31'), 'khacduy42@gmail.com',               '0934874207', 'tk8', 'mk8', 0),\n" +
                "  (9, 'Hồ Thành',                   0, DATE('2002-12-24'), 'hothanh959@gmail.com',              '0962244274', 'tk9', 'mk9', 0),\n" +
                "  (10, 'Vô Lương Tâm',              0, DATE('1997-10-20'), 'voluongtam@gmail.com',              '0967656247', 'tk10', 'mk10', 0),\n" +
                "  (11, 'Khai Phong Phủ',            0, DATE('2004-06-24'), 'khaiphongphu651@gmail.com',         '0968369859', 'tk11', 'mk11', 0),\n" +
                "  (12, 'Đinh Hồ Gia Tuệ',           1, DATE('1996-06-23'), 'giatue9445@gmail.com',              '0910915299', 'tk12', 'mk12', 0),\n" +
                "  (13, 'Ngô Bảo Châu',              0, DATE('2000-07-11'), 'baochau5623@gmail.com',             '0981496865', 'tk13', 'mk13', 0),\n" +
                "  (14, 'Lâm Khế Châu',              0, DATE('1994-02-20'), 'khe_chau_556@gmail.com',            '0984606293', 'tk14', 'mk14', 0),\n" +
                "  (15, 'Hoàng Thùy Linh',           1, DATE('1993-04-09'), 'thuylinh290@gmail.com',             '0908598408', 'tk15', 'mk15', 0),\n" +
                "  (16, 'Nguyễn Đức Cường',          0, DATE('1996-09-10'), 'nguyenduccuong@gmail.com',          '0988996804', 'tk16', 'mk16', 0),\n" +
                "  (17, 'Mạnh Thường Quân',          0, DATE('1998-03-22'), 'manhthuongquan922569@gmail.com',    '0986767735', 'tk17', 'mk17', 0),\n" +
                "  (18, 'Nguyễn Thị Thu Hà',         1, DATE('1992-10-24'), 'thu_ha78@gmail.com',                '0978065433', 'tk18', 'mk18', 0),\n" +
                "  (19, 'Nguyễn Công Phượng',        0, DATE('2001-05-21'), 'congphuong4265@gmail.com',          '0987691080', 'tk19', 'mk19', 0),\n" +
                "  (20, 'Kevin Porter',              0, DATE('1997-03-17'), 'kporter_42@gmail.com',              '0933275059', 'tk20', 'mk20', 0),\n" +
                "  (21, 'Vạn Lý Trường Thành',       0, DATE('2006-06-25'), 'truongthanh60@gmail.com',           '0962338296', 'tk21', 'mk21', 0),\n" +
                "  (22, 'Nguyễn Nhật Ánh',           1, DATE('1993-12-21'), 'nguyennhatanh@gmail.com',           '0941284446', 'tk22', 'mk22', 0),\n" +
                "  (23, 'Kylian Mbappé',             0, DATE('2000-03-03'), 'mbappe229@gmail.com',               '0972190188', 'tk23', 'mk23', 0),\n" +
                "  (24, 'Cẩm Thạch',                 0, DATE('1997-09-21'), 'cam_thach@gmail.com',               '0950162757', 'tk24', 'mk24', 0),\n" +
                "  (25, 'Trần Thủ Đoạn',             0, DATE('1992-04-25'), 'thudoan10@gmail.com',               '0950792197', 'tk25', 'mk25', 0),\n" +
                "  (26, 'Đặng Thị Xảo Quyệt',        0, DATE('1992-10-10'), 'xaoquyet90@gmail.com',              '0993674540', 'tk26', 'mk26', 0),\n" +
                "  (27, 'Khôi Ngô Tuấn Tú',          0, DATE('2000-03-21'), 'tuantu25@gmail.com',                '0946622149', 'tk27', 'mk27', 0),\n" +
                "  (28, 'Lâm Hoàng Kha',             0, DATE('2005-01-20'), 'lamhoangkha@gmail.com',             '0954263625', 'tk28', 'mk28', 0),\n" +
                "  (29, 'Lê Lai',                    0, DATE('2003-10-17'), 'lelai_10@gmail.com',                '0989974655', 'tk29', 'mk29', 0),\n" +
                "  (30, 'Lê Thị Đào',                1, DATE('2002-06-21'), 'daolethi@gmail.com',                '0968591448', 'tk30', 'mk30', 0),\n" +
                "  (31, 'Cao Kiên Cường',            0, DATE('2001-06-10'), 'kiencuong_15@gmail.com',            '0974161824', 'tk31', 'mk31', 0),\n" +
                "  (32, 'Hoa Anh Đào',               1, DATE('1996-02-07'), 'hoaanhdao@gmail.com',               '0985208143', 'tk32', 'mk32', 0),\n" +
                "  (33, 'Cao Bá Quát',               0, DATE('1994-10-23'), 'caobawatt2456@gmail.com',           '0978457791', 'tk33', 'mk33', 0),\n" +
                "  (34, 'Phương Hoàng Bình Minh',    1, DATE('1995-04-04'), 'binhminh4254@gmail.com',            '0959466890', 'tk34', 'mk34', 0),\n" +
                "  (35, 'Trần Công Nghiệp',          0, DATE('1972-12-15'), 'congnghiep@gmail.com',              '0443649617', 'chacon',              'abc123',           1),\n" +
                "  (36, 'Văn Mạnh Cường',            0, DATE('1992-05-30'), 'manhcuong128@gmail.com',            '0302951279', 'manhcuong128',        'iphone6plus',      1),\n" +
                "  (37, 'Trần Quốc Toản',            0, DATE('1997-11-15'), 'camlo50@gmail.com',                 '0935887635', 'tranquoctoan936',     'lmht1234',         1),\n" +
                "  (38, 'Ngô Thị Yến Vy',            1, DATE('1998-11-20'), 'vyen1@gmail.com',                   '0882646651', 'vyen1',               'password123',      1),\n" +
                "  (39, 'Đặng Hà Nam',               1, DATE('2000-01-02'), 'hanam52@gmail.com',                 '0279010802', 'hanam52',             'choconxinhxan111', 1),\n" +
                "  (40, 'Trần Độc Thân',             0, DATE('1982-10-09'), 'docthan51@gmail.com',               '0693690969', 'danthan123',          'a4V7sKLa',         1),\n" +
                "  (41, 'Khải Hoàng Bảo Vy',         1, DATE('1998-08-18'), 'baovy26@gmail.com',                 '0581941963', 'baov1',               'vietnamvodich',    1),\n" +
                "  (42, 'Ngỏ Ái Nỉ',                 1, DATE('1990-10-20'), 'ngoaini@gmail.com',                 '0751001479', 'ngoainii',            'ngo4ai3ni4',       1),\n" +
                "  (43, 'Hà Khắc Công Tâm',          0, DATE('1996-05-04'), 'congtamhakhac555@gmail.com',        '0995095472', 'congtamhakhac6996',   '3siruoi',          1),\n" +
                "  (44, 'Trường Chinh',              0, DATE('1997-09-18'), 'truongchinh2468@gmail.com',         '0599981314', 'truongchinh2468',     '9truong',          1),\n" +
                "  (45, 'Gia Lai',                   0, DATE('2000-10-12'), 'gala@gmail.com',                    '0859498299', 'gialai',              'giarai',           1),\n" +
                "  (46, 'Gấu Con Khả Ái',            1, DATE('2000-02-20'), 'gauconkhaai@gmail.com',             '0109613030', 'gauconkhaai',         'KAAI51',           1),\n" +
                "  (47, 'Tâm Lương Phúc Hạnh',       1, DATE('1999-10-04'), 'phucanh@gmail.com',                 '0022068299', 'phuchanh312',         'hanhanhan',        1),\n" +
                "  (48, 'Tâm Phục Khẩu Phục',        1, DATE('2000-12-05'), 'tamphuckhauphuc0125@gmail.com',     '0932750845', 'tamphuckhauphuc0125', '0125tpkp',         1),\n" +
                "  (49, 'Thế Thái Hoàng Anh',        0, DATE('2000-02-20'), 'thethaihoanganh2000@gmail.com',     '0491062420', 'hoanganh2k',          'ggw6njsd5',        1),\n" +
                "  (50, 'Khai Dân Trí',              0, DATE('1993-03-30'), 'khaidantri@gmail.com',              '0988063781', 'khaidantri',          'password',         1),\n" +
                "  (51, 'William Smith',             0, DATE('1995-01-05'), 'williamsmith@gmail.com',            '0991965350', 'wsmith512',           'smithwilliam12',   1),\n" +
                "  (52, 'Phan Châu Trinh',           0, DATE('1982-11-12'), 'trinhpahn@gmail.com',               '0647233372', 'trinhphan500',        'vcs_Mua_xuan',     1);\n" +
                "\n",
                "INSERT INTO NHAPHANPHOI VALUES (0, 'Quán ăn 257', '257 đường Lê Trọng Tấn, P.6, Q.8'),\n" +
                "  (1, 'Quán nem nướng Hoàng Châu', '1520 đường hoa Nguyễn Huệ, P.Bến Nghé, Q.1'),\n" +
                "  (2, 'Quán ăn 128', '128 đường 3/2, P.8, Q.11');\n" +
                        "\n",
                "INSERT INTO SANPHAM VALUES (0, 'Nem nướng AQ', 'https://cuongthinhfood.com/wp-content/uploads/2017/09/nem-nuong-cuong-thinh-loai-1.jpg', 'Nem nướng AQ FOODS 500gr là một sản phẩm nem nướng đến từ Việt Nam. Sản phẩm này được đóng gói trong túi 500gr.', 42000, 4.8, 0),\n" +
                "  (1, 'Nem nướng Lũng Cú', 'https://i.ytimg.com/vi/I_n1IQggIQ4/maxresdefault.jpg', 'Đến Lũng Cú không thể bỏ qua món nem nướng Lũng Cú. Khác biệt với nem truyền thống, món nem này mang một nét riêng của núi rừng.', 25000, 4.2, 0),\n" +
                "  (2, 'Nem nướng Hột Xoàn', 'https://www.wokandkin.com/wp-content/uploads/2020/10/Nem-Nuong-Close-Up-saved-for-web-500x500.png', 'Với vị ngon tuyệt hảo của món nem nướng Hột Xoàn, món này hứa hẹn làm hài lòng khẩu vị của nhiều thực khách.', 100000, 4.3, 1),\n" +
                "  (3, 'Nem nướng Đồng Bằng Sông Cửu Long', 'https://cdn.tgdd.vn/2021/05/CookRecipe/Avatar/nem-nuong-chay-thumbnail.gif', 'Mỗi lần bạn thưởng thức món này là mỗi lần bạn được nếm trải cuộc sống nhộn nhịp của những người dân vùng Đồng bằng sông Cửu Long miệt mài làm việc trên vùng biển mênh mông rộng lớn.', 80000, 4.1, 1),\n" +
                "  (4, 'Nem nướng Hoàng Hôn', 'https://danviet.mediacdn.vn/296231569849192448/2022/11/16/3019912714642772389253688904486918813752641n-16686074196391266879921.jpg', 'Một buổi chiều tà thơ mộng kèm theo một gói nem nướng Hoàng Hôn đem lại một cảm giác không thể nào vui sướng bằng! Ôi, những hàng cây xanh đung đưa giữa những núi rừng thơ mộng cùng với những miếng nem nướng vô cùng ngọt ngào cầm trên cây! Đó sẽ thật là một trải nghiệm đáng nhớ trong đời!', 70000, 4.9, 1),\n" +
                "  (5, 'Nem nướng Nha Trang', 'https://thamhiemmekong.com/wp-content/uploads/2019/07/nem-nuong-cai-rang.jpg', 'Nem được xiên nướng với bếp than hồng, hút chân không, Khách về bảo quản ngăn đông, đã chín sẵn nên về khách chỉ cần lấy ra trước quay sơ hoặc nướng áp chảo sơ, thêm ít bún, rau sống, dưa leo, mắm tỏi, là đã có món ngon quê mà không cần phải đặt chân đến Nha Trang mới thưởng thức được!', 118000, 5, 2),\n" +
                "  (6, 'Nem nướng hương pizza xúc xích Bình Minh', 'https://www.nhahangquangon.com/wp-content/uploads/2015/05/nem-nuong-lui-2.jpg', 'Điều gì sẽ xảy ra khi bạn muốn có sự kết hợp giữa hương vị thơm ngon của nem nướng và hương vị truyền thống của món pizza xúc xích? Vậy thì xin chúc mừng! Bạn đã có cơ hội trải nghiệm món đó với giá cả phải chăng đến từ thương hiệu nhà làm Bình Minh.', 79000, 4.6, 2),\n" +
                "  (7, 'Nem nướng Mười Điểm', 'https://www.nemnuonghainhat.com/wp-content/uploads/2021/06/Nem-Nuong-Hai-Nhat-mien-nam-2.jpg', 'Món này giúp con cái của các quý phụ huynh làm bài luôn được điểm cao trong học tập, xứng đáng làm con ngon trò giỏi trong lớp và trong trường.', 52000, 1.6, 2),\n" +
                "  (8, 'Nem nướng Tiếng Anh', 'https://www.banhdanemlangcheu.com.vn/images/2020/nem_nuong_nha_trang.jpg', 'Nem nướng này vừa làm tăng hương vị vừa giúp cải thiện trình độ tiếng Anh của bạn.', 45000, 1, 2),\n" +
                "  (9, 'Nem nướng 7-up', 'https://cachlamheo.com/wp-content/uploads/2016/10/nem-nuong-450x298.jpg', 'Ăn món này 7 lần bạn cũng sẽ ợ ra 7 lần luôn đó. Ghê chưa?', 77777, 1.7, 2);\n" +
                        "\n",
                "INSERT INTO GIAODICH VALUES\n" +
                "  (0,  20, 3,  46000, DATETIME('2023-01-20 14:02:20'), DATETIME('2023-01-20 14:05:20')),\n" +
                "  (1,  15, 13, 32000, DATETIME('2022-05-12 14:03:06'), DATETIME('2022-05-12 14:04:06')),\n" +
                "  (2,  13, 17, 43000, DATETIME('2023-05-01 12:42:30'), DATETIME('2023-05-01 12:46:30')),\n" +
                "  (3,  1,  17, 28000, DATETIME('2023-12-12 17:26:42'), DATETIME('2023-12-12 17:30:42')),\n" +
                "  (4,  6,  8,  35000, DATETIME('2022-12-30 13:40:09'), DATETIME('2022-12-30 13:50:09')),\n" +
                "  (5,  8,  3,  12000, DATETIME('2023-04-06 16:07:15'), DATETIME('2023-04-06 16:08:15')),\n" +
                "  (6,  16, 9,  11000, DATETIME('2022-02-28 18:10:26'), DATETIME('2022-02-28 18:12:26')),\n" +
                "  (7,  21, 11, 45000, DATETIME('2023-04-19 20:07:15'), DATETIME('2023-04-19 20:09:15')),\n" +
                "  (8,  24, 11, 36000, DATETIME('2022-05-30 21:32:09'), DATETIME('2022-05-30 21:35:09')),\n" +
                "  (9,  8,  12, 43000, DATETIME('2022-11-15 19:22:48'), DATETIME('2022-11-15 19:25:48')),\n" +
                "  (10, 16, 1,  36000, DATETIME('2023-02-17 12:29:44'), DATETIME('2023-02-17 12:32:44')),\n" +
                "  (11, 12, 17, 34000, DATETIME('2023-12-12 10:06:20'), DATETIME('2023-12-12 10:08:20')),\n" +
                "  (12, 15, 6,  0,     DATETIME('2023-11-05 15:26:50'), DATETIME('2023-11-05 15:30:50')),\n" +
                "  (13, 2,  3,  15000, DATETIME('2022-12-30 16:05:49'), DATETIME('2022-12-30 16:15:49')),\n" +
                "  (14, 30, 13, 33000, DATETIME('2022-07-07 16:47:50'), DATETIME('2022-07-07 16:49:50')),\n" +
                "  (15, 0,  3,  37000, DATETIME('2023-05-14 18:41:48'), DATETIME('2023-05-14 18:45:48')),\n" +
                "  (16, 5,  11, 35000, DATETIME('2023-08-28 22:22:54'), DATETIME('2023-08-28 22:28:54')),\n" +
                "  (17, 2,  16, 30000, DATETIME('2022-08-12 16:43:15'), DATETIME('2022-08-12 16:45:15')),\n" +
                "  (18, 4,  3,  44000, DATETIME('2022-08-16 12:34:51'), DATETIME('2022-08-16 12:36:51')),\n" +
                "  (19, 2,  12, 47000, DATETIME('2022-10-10 17:29:09'), DATETIME('2022-10-10 17:32:09')),\n" +
                "  (20, 31, 1,  8000,  DATETIME('2023-03-04 13:40:36'), DATETIME('2023-03-04 13:45:36')),\n" +
                "  (21, 08, 17, 49000, DATETIME('2023-03-30 12:43:15'), DATETIME('2023-03-30 12:49:15')),\n" +
                "  (22, 24, 14, 36000, DATETIME('2023-02-26 21:35:14'), DATETIME('2023-02-26 21:40:14')),\n" +
                "  (23, 7,  9,  44000, DATETIME('2022-06-29 21:40:06'), DATETIME('2022-06-29 21:45:06')),\n" +
                "  (24, 16, 1,  21000, DATETIME('2023-02-13 12:30:20'), DATETIME('2023-02-13 12:35:20')),\n" +
                "  (25, 15, 0,  20000, DATETIME('2022-02-07 13:34:01'), DATETIME('2022-02-07 13:36:01')),\n" +
                "  (26, 5,  7,  27000, DATETIME('2023-07-27 11:47:36'), DATETIME('2023-07-27 11:49:36')),\n" +
                "  (27, 23, 5,  0,     DATETIME('2023-06-23 13:44:29'), DATETIME('2023-06-23 13:48:29')),\n" +
                "  (28, 12, 2,  15000, DATETIME('2022-05-24 16:47:26'), DATETIME('2022-05-24 16:49:26')),\n" +
                "  (29, 08, 13, 23000, DATETIME('2023-05-04 14:14:55'), DATETIME('2023-05-04 14:20:55')),\n" +
                "  (30, 10, 6,  0,     DATETIME('2022-06-29 16:50:39'), DATETIME('2022-06-29 16:54:39')),\n" +
                "  (31, 21, 7,  3000,  DATETIME('2022-01-15 13:08:10'), DATETIME('2022-01-15 13:12:10')),\n" +
                "  (32, 12, 9,  25000, DATETIME('2023-11-28 21:13:33'), DATETIME('2023-11-28 21:19:33')),\n" +
                "  (33, 24, 5,  24000, DATETIME('2022-02-17 17:58:46'), DATETIME('2022-02-17 18:00:46')),\n" +
                "  (34, 32, 15, 34000, DATETIME('2022-10-08 20:27:35'), DATETIME('2022-10-08 20:29:35')),\n" +
                "  (35, 11, 11, 36000, DATETIME('2022-04-01 14:31:09'), DATETIME('2022-04-01 14:39:09')),\n" +
                "  (36, 08, 17, 46000, DATETIME('2023-03-12 17:46:27'), DATETIME('2023-03-12 17:48:27')),\n" +
                "  (37, 7,  16, 24000, DATETIME('2023-01-31 18:54:31'), DATETIME('2023-01-31 18:59:31')),\n" +
                "  (38, 34, 15, 18000, DATETIME('2023-02-19 12:45:01'), DATETIME('2023-02-19 12:50:01')),\n" +
                "  (39, 21, 8,  20000, DATETIME('2022-11-07 19:08:52'), DATETIME('2022-11-07 19:10:52')),\n" +
                "  (40, 26, 10, 49000, DATETIME('2022-12-11 10:56:15'), DATETIME('2022-12-11 11:00:15')),\n" +
                "  (41, 16, 0,  14000, DATETIME('2022-07-25 14:14:09'), DATETIME('2022-07-25 14:24:09')),\n" +
                "  (42, 21, 1,  36000, DATETIME('2023-07-02 21:30:18'), DATETIME('2023-07-02 21:35:18')),\n" +
                "  (43, 13, 3,  21000, DATETIME('2023-12-18 12:19:14'), DATETIME('2023-12-18 12:25:14')),\n" +
                "  (44, 11, 7,  18000, DATETIME('2022-11-21 22:37:09'), DATETIME('2022-11-21 22:47:09')),\n" +
                "  (45, 34, 0,  43000, DATETIME('2022-10-23 16:43:52'), DATETIME('2022-10-23 16:49:52')),\n" +
                "  (46, 15, 11, 4000,  DATETIME('2022-12-03 11:19:25'), DATETIME('2022-12-03 11:25:25')),\n" +
                "  (47, 32, 7,  23000, DATETIME('2023-03-25 16:56:55'), DATETIME('2023-03-25 17:02:55')),\n" +
                "  (48, 26, 10, 49000, DATETIME('2022-03-07 17:28:43'), DATETIME('2022-03-07 17:32:43')),\n" +
                "  (49, 9,  0,  13000, DATETIME('2022-07-06 20:02:18'), DATETIME('2022-07-06 20:12:18')),\n" +
                "  (50, 14, 10, 49000, DATETIME('2023-11-13 11:05:42'), DATETIME('2023-11-13 11:10:42')),\n" +
                "  (51, 11, 4,  22000, DATETIME('2022-08-12 20:10:38'), DATETIME('2022-08-12 20:19:38')),\n" +
                "  (52, 24, 1,  39000, DATETIME('2023-02-16 14:23:55'), DATETIME('2023-02-16 14:28:55')),\n" +
                "  (53, 4,  15, 47000, DATETIME('2023-03-10 13:51:01'), DATETIME('2023-03-10 13:59:01')),\n" +
                "  (54, 1,  15, 6000,  DATETIME('2023-05-24 21:42:13'), DATETIME('2023-05-24 21:45:13')),\n" +
                "  (55, 27, 6,  39000, DATETIME('2022-09-20 16:56:05'), DATETIME('2022-09-20 16:58:05')),\n" +
                "  (56, 0,  3,  21000, DATETIME('2023-08-29 17:30:41'), DATETIME('2023-08-29 17:32:41')),\n" +
                "  (57, 10, 11, 29000, DATETIME('2022-06-14 11:30:00'), DATETIME('2022-06-14 11:39:00')),\n" +
                "  (58, 10, 0,  35000, DATETIME('2022-07-05 20:08:52'), DATETIME('2022-07-05 20:10:52')),\n" +
                "  (59, 5,  12, 45000, DATETIME('2022-12-29 12:36:11'), DATETIME('2022-12-29 12:39:11')),\n" +
                "  (60, 30, 17, 50000, DATETIME('2023-07-07 20:32:18'), DATETIME('2023-07-07 20:35:18')),\n" +
                "  (61, 13, 5,  22000, DATETIME('2023-02-05 16:31:59'), DATETIME('2023-02-05 16:38:59')),\n" +
                "  (62, 30, 5,  1000,  DATETIME('2022-01-06 18:32:41'), DATETIME('2022-01-06 18:36:41')),\n" +
                "  (63, 11, 17, 18000, DATETIME('2022-02-15 15:08:59'), DATETIME('2022-02-15 15:15:59'));\n" +
                        "\n",
                "INSERT INTO CTGIAODICH VALUES (0, 1, 1),\n" +
                "  (1,  8, 1),\n" +
                "  (2,  2, 2),\n" +
                "  (3,  6, 1),\n" +
                "  (4,  5, 3),\n" +
                "  (4,  3, 1),\n" +
                "  (5,  4, 1),\n" +
                "  (6,  4, 2),\n" +
                "  (7,  2, 1),\n" +
                "  (8,  9, 1),\n" +
                "  (9,  2, 1),\n" +
                "  (10, 8, 1),\n" +
                "  (11, 0, 3),\n" +
                "  (11, 7, 1),\n" +
                "  (12, 4, 1),\n" +
                "  (12, 3, 1),\n" +
                "  (13, 7, 1),\n" +
                "  (14, 0, 3),\n" +
                "  (15, 4, 1),\n" +
                "  (16, 2, 1),\n" +
                "  (16, 4, 1),\n" +
                "  (17, 1, 1),\n" +
                "  (18, 8, 1),\n" +
                "  (19, 3, 3),\n" +
                "  (20, 3, 1),\n" +
                "  (21, 5, 1),\n" +
                "  (21, 3, 1),\n" +
                "  (21, 1, 2),\n" +
                "  (22, 6, 2),\n" +
                "  (23, 6, 1),\n" +
                "  (24, 4, 1),\n" +
                "  (25, 3, 1),\n" +
                "  (26, 3, 3),\n" +
                "  (26, 4, 1),\n" +
                "  (27, 7, 1),\n" +
                "  (28, 4, 3),\n" +
                "  (29, 1, 1),\n" +
                "  (30, 8, 1),\n" +
                "  (31, 8, 2),\n" +
                "  (32, 7, 3),\n" +
                "  (33, 4, 1),\n" +
                "  (34, 2, 2),\n" +
                "  (35, 6, 2),\n" +
                "  (36, 7, 1),\n" +
                "  (37, 7, 1),\n" +
                "  (37, 6, 2),\n" +
                "  (38, 6, 1),\n" +
                "  (39, 1, 2),\n" +
                "  (40, 0, 1),\n" +
                "  (41, 0, 1),\n" +
                "  (42, 9, 1),\n" +
                "  (42, 1, 2),\n" +
                "  (43, 5, 1),\n" +
                "  (44, 7, 1),\n" +
                "  (45, 7, 1),\n" +
                "  (46, 8, 2),\n" +
                "  (47, 8, 1),\n" +
                "  (48, 1, 1),\n" +
                "  (48, 8, 1),\n" +
                "  (49, 5, 3),\n" +
                "  (50, 2, 1),\n" +
                "  (51, 0, 1),\n" +
                "  (52, 4, 1),\n" +
                "  (53, 6, 2),\n" +
                "  (54, 3, 2),\n" +
                "  (55, 5, 1),\n" +
                "  (56, 4, 1),\n" +
                "  (57, 0, 1),\n" +
                "  (58, 5, 1),\n" +
                "  (58, 1, 1),\n" +
                "  (59, 2, 2),\n" +
                "  (60, 4, 1),\n" +
                "  (61, 0, 1),\n" +
                "  (62, 9, 1),\n" +
                "  (62, 0, 1),\n" +
                "  (63, 5, 2);\n" +
                        "\n",
                "INSERT INTO GIOHANG VALUES\n" +
                "  (35, 1, 1),\n" +
                "  (35, 6, 2),\n" +
                "  (35, 4, 1);\n"};
        for (String statement : statements)
            sqLiteDatabase.execSQL(statement);
        System.out.println("Database created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public List<KhachHangModel> getAllKhachHang() {
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM NGUOIDUNG WHERE NHANVIEN = 0", null);
        List<KhachHangModel> result = new ArrayList<>();
        while (cursor.moveToNext()) {
            result.add(new KhachHangModel(
                    cursor.getInt(cursor.getColumnIndexOrThrow("MAND")),
                    cursor.getString(cursor.getColumnIndexOrThrow("HOTEN")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("GIOITINH")) == 1,
                    new Date(cursor.getLong(cursor.getColumnIndexOrThrow("NGAYSINH"))),
                    cursor.getString(cursor.getColumnIndexOrThrow("EMAIL")),
                    cursor.getString(cursor.getColumnIndexOrThrow("SDT")),
                    cursor.getString(cursor.getColumnIndexOrThrow("TAIKHOAN")),
                    cursor.getString(cursor.getColumnIndexOrThrow("MATKHAU"))
            ));
        }
        cursor.close();
        return result;
    }

    public List<KhachHangModel> getAllNhanVien() {
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM NGUOIDUNG WHERE NHANVIEN = 1", null);
        List<KhachHangModel> result = new ArrayList<>();
        while (cursor.moveToNext())
            result.add(new KhachHangModel(
                    cursor.getInt(cursor.getColumnIndexOrThrow("MAND")),
                    cursor.getString(cursor.getColumnIndexOrThrow("HOTEN")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("GIOITINH")) == 1,
                    new Date(cursor.getLong(cursor.getColumnIndexOrThrow("NGAYSINH"))),
                    cursor.getString(cursor.getColumnIndexOrThrow("EMAIL")),
                    cursor.getString(cursor.getColumnIndexOrThrow("SDT")),
                    cursor.getString(cursor.getColumnIndexOrThrow("TAIKHOAN")),
                    cursor.getString(cursor.getColumnIndexOrThrow("MATKHAU")),
                    true
            ));
        cursor.close();
        return result;
    }

    public List<NhaPhanPhoiModel> getAllNhaPhanPhoi() {
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM NHAPHANPHOI", null);
        List<NhaPhanPhoiModel> result = new ArrayList<>();
        while (cursor.moveToNext())
            result.add(new NhaPhanPhoiModel(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2)
            ));
        cursor.close();
        return result;
    }

    public List<NemNuongModel> getAllSanPham() {
        Cursor cursor = getReadableDatabase().rawQuery("SELECT SANPHAM.*, TENNPP, DIACHI FROM SANPHAM\n" +
                "JOIN NHAPHANPHOI ON SANPHAM.MANPP = NHAPHANPHOI.MANPP", null);
        List<NemNuongModel> result = new ArrayList<>();
        while (cursor.moveToNext())
            result.add(new NemNuongModel(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getInt(4),
                    cursor.getFloat(5),
                    cursor.getInt(6),
                    cursor.getString(7),
                    cursor.getString(8)
            ));
        cursor.close();
        return result;
    }

    public List<GiaoDichModel> getGiaoDich(Date start, Date end) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Cursor cursor = getReadableDatabase().rawQuery(
                "SELECT GIAODICH.*, KH.HOTEN AS HOTENKH, NV.HOTEN AS HOTENND FROM GIAODICH\n" +
                        "JOIN NGUOIDUNG KH ON GIAODICH.MAKH = KH.MAND\n" +
                        "JOIN NGUOIDUNG NV ON GIAODICH.MANV = NV.MAND\n" +
                        "WHERE THOIGIANDAT >= ? AND THOIGIANDAT <= ?",
                new String[]{
                        dateFormat.format(start.getTime()),
                        dateFormat.format(end.getTime())
                }
        );
        List<GiaoDichModel> result = new ArrayList<>();
        while (cursor.moveToNext())
            try {
                result.add(new GiaoDichModel(
                        cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getInt(2),
                        cursor.getInt(3),
                        dateFormat.parse(cursor.getString(4)),
                        dateFormat.parse(cursor.getString(5)),
                        cursor.getString(6),
                        cursor.getString(7)
                ));
            }
            catch (ParseException e) { }
        cursor.close();
        return result;
    }

    public List<ChiTietGiaoDichModel> getChiTietGiaoDich(int maGd) {
        Cursor cursor = getReadableDatabase().rawQuery("SELECT CTGIAODICH.*, TENSP, DONGIA FROM CTGIAODICH\n" +
                "JOIN SANPHAM ON CTGIAODICH.MASP = SANPHAM.MASP\n" +
                "WHERE MAGD = ?", new String[]{Integer.toString(maGd)}
        );
        List<ChiTietGiaoDichModel> result = new ArrayList<>();
        while (cursor.moveToNext())
            result.add(new ChiTietGiaoDichModel(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getString(3),
                    cursor.getInt(2),
                    cursor.getInt(4)
            ));
        cursor.close();
        return result;
    }

    public List<GioHangModel> getGioHangByNguoiDung(int maNd) {
        Cursor cursor = getReadableDatabase().rawQuery("SELECT SANPHAM.*, TENNPP, DIACHI, SOLUONG FROM SANPHAM\n" +
                "JOIN NHAPHANPHOI ON SANPHAM.MANPP = NHAPHANPHOI.MANPP " +
                "JOIN GIOHANG ON GIOHANG.MASP = SANPHAM.MASP " +
                "WHERE MAND = ?", new String[]{Integer.toString(maNd)}
        );
        List<GioHangModel> result = new ArrayList<>();
        while (cursor.moveToNext())
            result.add(new GioHangModel(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getInt(4),
                    cursor.getFloat(5),
                    cursor.getInt(6),
                    cursor.getString(7),
                    cursor.getString(8),
                    MainActivity.taiKhoanHienTai.getMaKh(),
                    cursor.getInt(9)
            ));
        cursor.close();
        return result;
    }

    public GioHangModel getGioHang(int maNd, int maSp) {
        Cursor cursor = getReadableDatabase().rawQuery("SELECT SANPHAM.*, TENNPP, DIACHI, SOLUONG FROM SANPHAM\n" +
                "JOIN NHAPHANPHOI ON SANPHAM.MANPP = NHAPHANPHOI.MANPP " +
                "JOIN GIOHANG ON GIOHANG.MASP = SANPHAM.MASP " +
                "WHERE MAND = ? AND SANPHAM.MASP = ?", new String[]{Integer.toString(maNd), Integer.toString(maSp)}
        );
        GioHangModel result = null;
        if (cursor.moveToNext())
            result = new GioHangModel(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getInt(4),
                    cursor.getFloat(5),
                    cursor.getInt(6),
                    cursor.getString(7),
                    cursor.getString(8),
                    MainActivity.taiKhoanHienTai.getMaKh(),
                    cursor.getInt(9)
            );
        cursor.close();
        return result;
    }

    public KhachHangModel getNguoiDungByUser(UserModel model) {
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM NGUOIDUNG WHERE TAIKHOAN = ? AND MATKHAU = ?", new String[]{
                model.getTaiKhoan(),
                model.getMatKhau()
        });
        if (cursor.getCount() == 0) return null;
        cursor.moveToNext();
        KhachHangModel result = new KhachHangModel(
                cursor.getInt(cursor.getColumnIndexOrThrow("MAND")),
                cursor.getString(cursor.getColumnIndexOrThrow("HOTEN")),
                cursor.getInt(cursor.getColumnIndexOrThrow("GIOITINH")) == 1,
                new Date(cursor.getLong(cursor.getColumnIndexOrThrow("NGAYSINH"))),
                cursor.getString(cursor.getColumnIndexOrThrow("EMAIL")),
                cursor.getString(cursor.getColumnIndexOrThrow("SDT")),
                cursor.getString(cursor.getColumnIndexOrThrow("TAIKHOAN")),
                cursor.getString(cursor.getColumnIndexOrThrow("MATKHAU")),
                cursor.getInt(cursor.getColumnIndexOrThrow("NHANVIEN")) != 0
        );
        cursor.close();
        return result;
    }

    public void insertKhachHang(KhachHangModel model, boolean nhanVien) {
        getWritableDatabase().execSQL("INSERT INTO NGUOIDUNG(HOTEN, GIOITINH, NGAYSINH, EMAIL, SDT, TAIKHOAN, MATKHAU, NHANVIEN) VALUES (?, ?, ?, ?, ?, ?, ?, ?)", new Object[]{
                model.getHoTen(),
                model.isGioiTinh(),
                model.getNgaySinh(),
                model.getEmail(),
                model.getSdt(),
                model.getTaiKhoan(),
                model.getMatKhau(),
                nhanVien
        });
    }

    public void insertGioHang(GioHangModel model) {
        getWritableDatabase().execSQL("INSERT INTO GIOHANG(MAND, MASP, SOLUONG) VALUES (?, ?, ?)", new Object[]{
                model.getMaNd(),
                model.getMaSp(),
                model.getSoLuong()
        });
    }

    public void deleteGioHang(GioHangModel model) {
        getWritableDatabase().execSQL("DELETE FROM GIOHANG WHERE MAND = ? AND MASP = ?", new Object[]{
                model.getMaNd(),
                model.getMaSp()
        });
    }

    public void clearGioHang(GioHangModel model) {
        getWritableDatabase().execSQL("DELETE FROM GIOHANG WHERE MAND = ?", new Object[]{
                model.getMaNd()
        });
    }

    public int insertGiaoDich(GiaoDichModel model) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        getWritableDatabase().execSQL("INSERT INTO GIAODICH(MAKH, MANV, PHIVANCHUYEN, THOIGIANDAT, THOIGIANGIAO) VALUES (?, ?, ?, ?, ?)", new Object[]{
                model.getMaKh(),
                model.getMaNv(),
                model.getPhiVanChuyen(),
                dateFormat.format(model.getThoiGianDat()),
                dateFormat.format(model.getThoiGianGiao())
        });
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM GIAODICH WHERE rowid = last_insert_rowid()", null);
        int result = cursor.moveToNext() ? cursor.getInt(0) : -1;
        cursor.close();
        return result;
    }

    public void insertChiTietGiaoDich(ChiTietGiaoDichModel model) {
        getWritableDatabase().execSQL("INSERT INTO CTGIAODICH(MAGD, MASP, SOLUONG) VALUES (?, ?, ?)", new Object[]{
                model.getMaGd(),
                model.getMaSp(),
                model.getSoLuong()
        });
    }

    public void updatePassword(KhachHangModel model, String newPassword) {
        getWritableDatabase().execSQL("UPDATE NGUOIDUNG SET MATKHAU = ? WHERE MAND = ?", new Object[] {
                model.getMatKhau(),
                model.getMaKh()
        });
    }

    public void updateKhachHang(int maKh, KhachHangModel model) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        getWritableDatabase().execSQL("UPDATE NGUOIDUNG SET HOTEN = ?, GIOITINH = ?, NGAYSINH = ?, EMAIL = ?, SDT = ?, TAIKHOAN = ?, MATKHAU = ? WHERE MAND = ?", new Object[] {
                model.getHoTen(),
                model.isGioiTinh() ? 1 : 0,
                dateFormat.format(model.getNgaySinh()),
                model.getEmail(),
                model.getSdt(),
                model.getTaiKhoan(),
                model.getMatKhau(),
                maKh,
        });
    }

    public void updateNhanVien(int maNv, KhachHangModel model) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        getWritableDatabase().execSQL("UPDATE NGUOIDUNG SET HOTEN = ?, GIOITINH = ?, NGAYSINH = ?, EMAIL = ?, SDT = ?, TAIKHOAN = ?, MATKHAU = ? WHERE MANV = ?", new Object[] {
                model.getHoTen(),
                model.isGioiTinh() ? 1 : 0,
                dateFormat.format(model.getNgaySinh()),
                model.getEmail(),
                model.getSdt(),
                model.getTaiKhoan(),
                model.getMatKhau(),
                model.getMaKh()
        });
    }

    public void updateGioHang(GioHangModel model) {
        getWritableDatabase().execSQL("UPDATE GIOHANG SET SOLUONG = ? WHERE MAND = ? AND MASP = ?;", new Object[]{
                model.getSoLuong(),
                model.getMaNd(),
                model.getMaSp(),
        });
    }
}
