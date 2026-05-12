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

    @Column(name = "n_diemsanthpt")
    private Double n_diemsanthpt;
    
    @Column(name = "n_diemsanvsat")
    private Double n_diemsanvsat;
    
    @Column(name = "n_diemsandgnl")
    private Double n_diemsandgnl;

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
    private Integer sl_thpt;
    
    @Column(name = "diemchuan_thpt")
    private Double diemchuan_thpt;

    @Column(name = "diemchuan_dgnl")
    private Double diemchuan_dgnl;

    @Column(name = "diemchuan_vsat")
    private Double diemchuan_vsat;

    @Column(name = "diemchuan_xtt")
    private Double diemchuan_xtt;
    
    @Column(name = "sl_dangky")
    private Integer slDangKy;

    // Getter và Setter
    public Integer getSlDangKy() { return slDangKy; }
    public void setSlDangKy(Integer slDangKy) { this.slDangKy = slDangKy; }
    
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

    public Double getN_diemsanthpt() {
        return n_diemsanthpt;
    }
    
    public Double getN_diemsanvsat() {
        return n_diemsanvsat;
    }
    
    public Double getN_diemsandgnl() {
        return n_diemsandgnl;
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

    public Integer getSl_thpt() {
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

    public void setN_diemsanthpt(Double n_diemsanthpt) {
        this.n_diemsanthpt = n_diemsanthpt;
    }
    
    public void setN_diemsanvsat(Double n_diemsanvsat) {
        this.n_diemsanvsat = n_diemsanvsat;
    }
    
    public void setN_diemsandgnl(Double n_diemsandgnl) {
        this.n_diemsandgnl = n_diemsandgnl;
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

    public void setSl_thpt(Integer sl_thpt) {
        this.sl_thpt = sl_thpt;
    }

    public Double getDiemchuan_thpt() {
        return diemchuan_thpt;
    }

    public Double getDiemchuan_dgnl() {
        return diemchuan_dgnl;
    }

    public Double getDiemchuan_vsat() {
        return diemchuan_vsat;
    }

    public void setDiemchuan_thpt(Double diemchuan_thpt) {
        this.diemchuan_thpt = diemchuan_thpt;
    }

    public void setDiemchuan_dgnl(Double diemchuan_dgnl) {
        this.diemchuan_dgnl = diemchuan_dgnl;
    }

    public void setDiemchuan_vsat(Double diemchuan_vsat) {
        this.diemchuan_vsat = diemchuan_vsat;
    }

    public Double getDiemchuan_xtt() {
        return diemchuan_xtt;
    }

    public void setDiemchuan_xtt(Double diemchuan_xtt) {
        this.diemchuan_xtt = diemchuan_xtt;
    }


}