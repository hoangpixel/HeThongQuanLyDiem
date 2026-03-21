/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
/**
 *
 * @author mhoang
 */
@Entity
@Table(name = "xt_diemcongxetuyen") // Chú ý: Tên bảng của thầy có 1 chữ t ở chữ xetuyen
public class diemCongETT {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iddiemcong")
    private int iddiemcong;

    @Column(name = "ts_cccd")
    private String tsCccd;

    @Column(name = "manganh")
    private String maNganh;

    @Column(name = "matohop")
    private String maToHop;

    @Column(name = "phuongthuc")
    private String phuongThuc;

    @Column(name = "diemCC")
    private Double diemCC; // Điểm cộng chứng chỉ

    @Column(name = "diemUtxt")
    private Double diemUtxt; // Điểm ưu tiên quy đổi

    @Column(name = "diemTong")
    private Double diemTong;

    @Column(name = "ghichu")
    private String ghiChu;

    @Column(name = "dc_keys")
    private String dcKeys;

    public int getIddiemcong() {
        return iddiemcong;
    }

    public String getTsCccd() {
        return tsCccd;
    }

    public String getMaNganh() {
        return maNganh;
    }

    public String getMaToHop() {
        return maToHop;
    }

    public String getPhuongThuc() {
        return phuongThuc;
    }

    public Double getDiemCC() {
        return diemCC;
    }

    public Double getDiemUtxt() {
        return diemUtxt;
    }

    public Double getDiemTong() {
        return diemTong;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public String getDcKeys() {
        return dcKeys;
    }

    public void setIddiemcong(int iddiemcong) {
        this.iddiemcong = iddiemcong;
    }

    public void setTsCccd(String tsCccd) {
        this.tsCccd = tsCccd;
    }

    public void setMaNganh(String maNganh) {
        this.maNganh = maNganh;
    }

    public void setMaToHop(String maToHop) {
        this.maToHop = maToHop;
    }

    public void setPhuongThuc(String phuongThuc) {
        this.phuongThuc = phuongThuc;
    }

    public void setDiemCC(Double diemCC) {
        this.diemCC = diemCC;
    }

    public void setDiemUtxt(Double diemUtxt) {
        this.diemUtxt = diemUtxt;
    }

    public void setDiemTong(Double diemTong) {
        this.diemTong = diemTong;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public void setDcKeys(String dcKeys) {
        this.dcKeys = dcKeys;
    }

    
}