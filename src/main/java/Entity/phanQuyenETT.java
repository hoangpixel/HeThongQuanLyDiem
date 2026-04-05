package Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "xt_phanquyen")
public class phanQuyenETT {

    @Id
    @Column(name = "id_tai_khoan")
    private int idTaiKhoan;

    @Id
    @Column(name = "ten_bang")
    private String tenBang;

    @Column(name = "quyen_xem")
    private Integer quyenXem;

    @Column(name = "quyen_them")
    private Integer quyenThem;

    @Column(name = "quyen_sua")
    private Integer quyenSua;

    @Column(name = "quyen_xoa")
    private Integer quyenXoa;

    // ===== Getter & Setter =====

    public int getIdTaiKhoan() {
        return idTaiKhoan;
    }

    public void setIdTaiKhoan(int idTaiKhoan) {
        this.idTaiKhoan = idTaiKhoan;
    }

    public String getTenBang() {
        return tenBang;
    }

    public void setTenBang(String tenBang) {
        this.tenBang = tenBang;
    }

    public Integer getQuyenXem() {
        return quyenXem;
    }

    public void setQuyenXem(Integer quyenXem) {
        this.quyenXem = quyenXem;
    }

    public Integer getQuyenThem() {
        return quyenThem;
    }

    public void setQuyenThem(Integer quyenThem) {
        this.quyenThem = quyenThem;
    }

    public Integer getQuyenSua() {
        return quyenSua;
    }

    public void setQuyenSua(Integer quyenSua) {
        this.quyenSua = quyenSua;
    }

    public Integer getQuyenXoa() {
        return quyenXoa;
    }

    public void setQuyenXoa(Integer quyenXoa) {
        this.quyenXoa = quyenXoa;
    }
}