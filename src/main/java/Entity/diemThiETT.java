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
@Table(name = "xt_diemthixettuyen")
public class diemThiETT {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iddiemthi")
    private int iddiemthi;

    @Column(name = "cccd")
    private String cccd;

    @Column(name = "sobaodanh")
    private String sobaodanh;

    @Column(name = "d_phuongthuc")
    private String dPhuongthuc;

    // --- CÁC CỘT ĐIỂM THÀNH PHẦN ---
    @Column(name = "`TO`") private Double to;   // Toán
    @Column(name = "LI") private Double li;   // Lý
    @Column(name = "HO") private Double ho;   // Hóa
    @Column(name = "SI") private Double si;   // Sinh
    @Column(name = "SU") private Double su;   // Sử
    @Column(name = "DI") private Double di;   // Địa
    @Column(name = "VA") private Double va;   // Văn
    
    @Column(name = "N1_THI") private Double n1Thi; // Tiếng Anh (Thi)
    @Column(name = "N1_CC")  private Double n1Cc;  // Tiếng Anh (Chứng chỉ)
    
    @Column(name = "CNCN") private Double cncn; // Công nghệ công nghiệp
    @Column(name = "CNNN") private Double cnnn; // Công nghệ nông nghiệp
    @Column(name = "TI")   private Double ti;   // Tin học
    @Column(name = "KTPL") private Double ktpl; // Giáo dục KTPL
    
    @Column(name = "NL1") private Double nl1; // Đánh giá năng lực
    @Column(name = "NK1") private Double nk1; // Năng khiếu 1
    @Column(name = "NK2") private Double nk2; // Năng khiếu 2

    // TODO: Nhớ click chuột phải -> Insert Code -> Getter and Setter cho toàn bộ các biến này nha!

    public int getIddiemthi() {
        return iddiemthi;
    }

    public String getCccd() {
        return cccd;
    }

    public String getSobaodanh() {
        return sobaodanh;
    }

    public String getdPhuongthuc() {
        return dPhuongthuc;
    }

    public Double getTo() {
        return to;
    }

    public Double getLi() {
        return li;
    }

    public Double getHo() {
        return ho;
    }

    public Double getSi() {
        return si;
    }

    public Double getSu() {
        return su;
    }

    public Double getDi() {
        return di;
    }

    public Double getVa() {
        return va;
    }

    public Double getN1Thi() {
        return n1Thi;
    }

    public Double getN1Cc() {
        return n1Cc;
    }

    public Double getCncn() {
        return cncn;
    }

    public Double getCnnn() {
        return cnnn;
    }

    public Double getTi() {
        return ti;
    }

    public Double getKtpl() {
        return ktpl;
    }

    public Double getNl1() {
        return nl1;
    }

    public Double getNk1() {
        return nk1;
    }

    public Double getNk2() {
        return nk2;
    }

    public void setIddiemthi(int iddiemthi) {
        this.iddiemthi = iddiemthi;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }

    public void setSobaodanh(String sobaodanh) {
        this.sobaodanh = sobaodanh;
    }

    public void setdPhuongthuc(String dPhuongthuc) {
        this.dPhuongthuc = dPhuongthuc;
    }

    public void setTo(Double to) {
        this.to = to;
    }

    public void setLi(Double li) {
        this.li = li;
    }

    public void setHo(Double ho) {
        this.ho = ho;
    }

    public void setSi(Double si) {
        this.si = si;
    }

    public void setSu(Double su) {
        this.su = su;
    }

    public void setDi(Double di) {
        this.di = di;
    }

    public void setVa(Double va) {
        this.va = va;
    }

    public void setN1Thi(Double n1Thi) {
        this.n1Thi = n1Thi;
    }

    public void setN1Cc(Double n1Cc) {
        this.n1Cc = n1Cc;
    }

    public void setCncn(Double cncn) {
        this.cncn = cncn;
    }

    public void setCnnn(Double cnnn) {
        this.cnnn = cnnn;
    }

    public void setTi(Double ti) {
        this.ti = ti;
    }

    public void setKtpl(Double ktpl) {
        this.ktpl = ktpl;
    }

    public void setNl1(Double nl1) {
        this.nl1 = nl1;
    }

    public void setNk1(Double nk1) {
        this.nk1 = nk1;
    }

    public void setNk2(Double nk2) {
        this.nk2 = nk2;
    }
    
}