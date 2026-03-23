package Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "xt_diemcongxetuyen")
public class diemCongETT {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iddiemcong")
    private int idDiemCong;

    @Column(name = "ts_cccd")
    private String tsCccd;

    @Column(name = "manganh")
    private String maNganh;

    @Column(name = "matohop")
    private String maToHop;

    @Column(name = "phuongthuc")
    private String phuongThuc;

    @Column(name = "diemCC")
    private Double diemCC;

    @Column(name = "diemUtxt")
    private Double diemUtxt;

    @Column(name = "diemTong")
    private Double diemTong;

    @Column(name = "ghichu")
    private String ghiChu;

    @Column(name = "dc_keys")
    private String dcKeys;

    // ================= GETTER =================

    public int getIdDiemCong() {
        return idDiemCong;
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

    // ================= SETTER =================

    public void setIdDiemCong(int idDiemCong) {
        this.idDiemCong = idDiemCong;
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
        tinhDiemTong(); // 🔥 auto tính
    }

    public void setDiemUtxt(Double diemUtxt) {
        this.diemUtxt = diemUtxt;
        tinhDiemTong(); // 🔥 auto tính
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

    // ================= AUTO TÍNH =================

    private void tinhDiemTong() {
        if (diemCC != null && diemUtxt != null) {
            this.diemTong = diemCC + diemUtxt;
        }
    }
}