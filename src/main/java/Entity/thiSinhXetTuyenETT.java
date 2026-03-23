/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;
import java.util.Date;
import jakarta.persistence.*;
/**
 *
 * @author LE MINH HUY
 */

@Entity
@Table(name = "xt_thisinhxettuyen25")
public class thiSinhXetTuyenETT {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
private int idThiSinh;
    private String cccd;
    private String soBaoDanh;
    private String ho;
    private String ten;
    private Date ngaySinh;
    private String dienThoai;
    private String password;
    private String gioiTinh;
    private String email;
    private String noiSinh;
    private Date updatedAt;
    private String doiTuong;
    private String khuVuc;

    // Constructor rỗng
    public thiSinhXetTuyenETT() {
    }

    // Các hàm Getters và Setters
    public int getIdThiSinh() { return idThiSinh; }
    public void setIdThiSinh(int idThiSinh) { this.idThiSinh = idThiSinh; }

    public String getCccd() { return cccd; }
    public void setCccd(String cccd) { this.cccd = cccd; }

    public String getSoBaoDanh() { return soBaoDanh; }
    public void setSoBaoDanh(String soBaoDanh) { this.soBaoDanh = soBaoDanh; }

    public String getHo() { return ho; }
    public void setHo(String ho) { this.ho = ho; }

    public String getTen() { return ten; }
    public void setTen(String ten) { this.ten = ten; }

    public Date getNgaySinh() {
        return ngaySinh;
    }
    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }
    public String getDienThoai() { return dienThoai; }
    public void setDienThoai(String dienThoai) { this.dienThoai = dienThoai; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getGioiTinh() { return gioiTinh; }
    public void setGioiTinh(String gioiTinh) { this.gioiTinh = gioiTinh; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getNoiSinh() { return noiSinh; }
    public void setNoiSinh(String noiSinh) { this.noiSinh = noiSinh; }

    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }

    public String getDoiTuong() { return doiTuong; }
    public void setDoiTuong(String doiTuong) { this.doiTuong = doiTuong; }

    public String getKhuVuc() { return khuVuc; }
    public void setKhuVuc(String khuVuc) { this.khuVuc = khuVuc; }
}