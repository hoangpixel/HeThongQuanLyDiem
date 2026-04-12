/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;
import jakarta.persistence.*;
import java.io.Serializable;
/**
 *
 * @author mhoang
 */
@Entity
@Table(name = "xt_chungchi")
public class chungChiETT{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cc")
    private int idCc;

    @Column(name = "cccd")
    private String cccd;

    @Column(name = "loai_chung_chi")
    private String loaiChungChi;

    @Column(name = "diem_chung_chi")
    private String diemChungChi;

    @Column(name = "diem_quydoi")
    private Double diemQuyDoi;

    @Column(name = "diem_cong")
    private Double diemCong;

    // ===== Constructor =====
    public chungChiETT() {}

    public int getIdCc() {
        return idCc;
    }

    public String getCccd() {
        return cccd;
    }

    public String getLoaiChungChi() {
        return loaiChungChi;
    }

    public String getDiemChungChi() {
        return diemChungChi;
    }

    public Double getDiemQuyDoi() {
        return diemQuyDoi;
    }

    public Double getDiemCong() {
        return diemCong;
    }

    public void setIdCc(int idCc) {
        this.idCc = idCc;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }

    public void setLoaiChungChi(String loaiChungChi) {
        this.loaiChungChi = loaiChungChi;
    }

    public void setDiemChungChi(String diemChungChi) {
        this.diemChungChi = diemChungChi;
    }

    public void setDiemQuyDoi(Double diemQuyDoi) {
        this.diemQuyDoi = diemQuyDoi;
    }

    public void setDiemCong(Double diemCong) {
        this.diemCong = diemCong;
    }
    
}