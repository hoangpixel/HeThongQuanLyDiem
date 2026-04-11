/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import jakarta.persistence.*;


/**
 *
 * @author mhoang
 */
@Entity
@Table(name = "xt_quytac_giaithuong")
public class quyTacGiaiThuongETT {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_quytac")
    private int idQuyTac;

    @Column(name = "cap_giai")
    private String capGiai;

    @Column(name = "loai_giai")
    private String loaiGiai;

    @Column(name = "xet_tuyen_thang")
    private int xetTuyenThang;

    @Column(name = "do_uu_tien")
    private int doUuTien;

    // Constructors, Getters và Setters
    public quyTacGiaiThuongETT() {}

    public int getIdQuyTac() { return idQuyTac; }
    public void setIdQuyTac(int idQuyTac) { this.idQuyTac = idQuyTac; }

    public String getCapGiai() { return capGiai; }
    public void setCapGiai(String capGiai) { this.capGiai = capGiai; }

    public String getLoaiGiai() { return loaiGiai; }
    public void setLoaiGiai(String loaiGiai) { this.loaiGiai = loaiGiai; }

    public int getXetTuyenThang() { return xetTuyenThang; }
    public void setXetTuyenThang(int xetTuyenThang) { this.xetTuyenThang = xetTuyenThang; }

    public int getDoUuTien() { return doUuTien; }
    public void setDoUuTien(int doUuTien) { this.doUuTien = doUuTien; }
}