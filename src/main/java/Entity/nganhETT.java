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
@Table(name = "xt_nganh")
public class nganhETT {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idnganh")
    private int idnganh;

    @Column(name = "manganh")
    private String manganh;

    @Column(name = "tennganh")
    private String tennganh;

    @Column(name = "n_tohopgoc")
    private String n_tohopgoc;

    @Column(name = "n_chitieu")
    private Integer n_chitieu;

    @Column(name = "n_diemsan")
    private Double n_diemsan;

    @Column(name = "n_diemtrungtuyen")
    private Double n_diemtrungtuyen;

    @Column(name = "n_tuyenthang")
    private String n_tuyenthang;

    @Column(name = "n_dgnl")
    private String n_dgnl;

    @Column(name = "n_thpt")
    private String n_thpt;

    @Column(name = "n_vsat")
    private String n_vsat;

    @Column(name = "sl_xtt")
    private Integer sl_xtt;     

    @Column(name = "sl_dgnl")
    private Integer sl_dgnl;

    @Column(name = "sl_vsat")
    private Integer sl_vsat;

    @Column(name = "sl_thpt")
    private String sl_thpt;

    // ===== Getter & Setter =====

    public int getIdnganh() {
        return idnganh;
    }

    public String getManganh() {
        return manganh;
    }

    public String getTennganh() {
        return tennganh;
    }

    public String getN_tohopgoc() {
        return n_tohopgoc;
    }

    public Integer getN_chitieu() {
        return n_chitieu;
    }

    public Double getN_diemsan() {
        return n_diemsan;
    }

    public Double getN_diemtrungtuyen() {
        return n_diemtrungtuyen;
    }

    public String getN_tuyenthang() {
        return n_tuyenthang;
    }

    public String getN_dgnl() {
        return n_dgnl;
    }

    public String getN_thpt() {
        return n_thpt;
    }

    public String getN_vsat() {
        return n_vsat;
    }

    public Integer getSl_xtt() {
        return sl_xtt;
    }

    public Integer getSl_dgnl() {
        return sl_dgnl;
    }

    public Integer getSl_vsat() {
        return sl_vsat;
    }

    public String getSl_thpt() {
        return sl_thpt;
    }

    public void setIdnganh(int idnganh) {
        this.idnganh = idnganh;
    }

    public void setManganh(String manganh) {
        this.manganh = manganh;
    }

    public void setTennganh(String tennganh) {
        this.tennganh = tennganh;
    }

    public void setN_tohopgoc(String n_tohopgoc) {
        this.n_tohopgoc = n_tohopgoc;
    }

    public void setN_chitieu(Integer n_chitieu) {
        this.n_chitieu = n_chitieu;
    }

    public void setN_diemsan(Double n_diemsan) {
        this.n_diemsan = n_diemsan;
    }

    public void setN_diemtrungtuyen(Double n_diemtrungtuyen) {
        this.n_diemtrungtuyen = n_diemtrungtuyen;
    }

    public void setN_tuyenthang(String n_tuyenthang) {
        this.n_tuyenthang = n_tuyenthang;
    }

    public void setN_dgnl(String n_dgnl) {
        this.n_dgnl = n_dgnl;
    }

    public void setN_thpt(String n_thpt) {
        this.n_thpt = n_thpt;
    }

    public void setN_vsat(String n_vsat) {
        this.n_vsat = n_vsat;
    }

    public void setSl_xtt(Integer sl_xtt) {
        this.sl_xtt = sl_xtt;
    }

    public void setSl_dgnl(Integer sl_dgnl) {
        this.sl_dgnl = sl_dgnl;
    }

    public void setSl_vsat(Integer sl_vsat) {
        this.sl_vsat = sl_vsat;
    }

    public void setSl_thpt(String sl_thpt) {
        this.sl_thpt = sl_thpt;
    }

    
}