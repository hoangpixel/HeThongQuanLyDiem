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
@Table(name = "xt_nganh_tohop")
public class nganhToHopETT {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int idNganhToHop;

    @Column(name = "manganh", nullable = false)
    private String maNganh;

    @Column(name = "matohop", nullable = false)
    private String maToHop;

    @Column(name = "th_mon1")
    private String mon1;

    @Column(name = "hsmon1")
    private Integer heSoMon1;

    @Column(name = "th_mon2")
    private String mon2;

    @Column(name = "hsmon2")
    private Integer heSoMon2;

    @Column(name = "th_mon3")
    private String mon3;

    @Column(name = "hsmon3")
    private Integer heSoMon3;

    @Column(name = "tb_keys", unique = true)
    private String key;

    @Column(name = "N1")
    private Integer N1;

    @Column(name = "`TO`")
    private Integer TO;

    @Column(name = "LI")
    private Integer LI;

    @Column(name = "HO")
    private Integer HO;

    @Column(name = "SI")
    private Integer SI;

    @Column(name = "VA")
    private Integer VA;

    @Column(name = "SU")
    private Integer SU;

    @Column(name = "DI")
    private Integer DI;

    @Column(name = "TI")
    private Integer TI;

    @Column(name = "KHAC")
    private Integer KHAC;

    @Column(name = "KTPL")
    private Integer KTPL;

    @Column(name = "dolech")
    private Double doLech;

    public int getIdNganhToHop() {
        return idNganhToHop;
    }

    public String getMaNganh() {
        return maNganh;
    }

    public String getMaToHop() {
        return maToHop;
    }

    public String getMon1() {
        return mon1;
    }

    public Integer getHeSoMon1() {
        return heSoMon1;
    }

    public String getMon2() {
        return mon2;
    }

    public Integer getHeSoMon2() {
        return heSoMon2;
    }

    public String getMon3() {
        return mon3;
    }

    public Integer getHeSoMon3() {
        return heSoMon3;
    }

    public String getKey() {
        return key;
    }

    public Integer getN1() {
        return N1;
    }

    public Integer getTO() {
        return TO;
    }

    public Integer getLI() {
        return LI;
    }

    public Integer getHO() {
        return HO;
    }

    public Integer getSI() {
        return SI;
    }

    public Integer getVA() {
        return VA;
    }

    public Integer getSU() {
        return SU;
    }

    public Integer getDI() {
        return DI;
    }

    public Integer getTI() {
        return TI;
    }

    public Integer getKHAC() {
        return KHAC;
    }

    public Integer getKTPL() {
        return KTPL;
    }

    public Double getDoLech() {
        return doLech;
    }

    public void setIdNganhToHop(int idNganhToHop) {
        this.idNganhToHop = idNganhToHop;
    }

    public void setMaNganh(String maNganh) {
        this.maNganh = maNganh;
    }

    public void setMaToHop(String maToHop) {
        this.maToHop = maToHop;
    }

    public void setMon1(String mon1) {
        this.mon1 = mon1;
    }

    public void setHeSoMon1(Integer heSoMon1) {
        this.heSoMon1 = heSoMon1;
    }

    public void setMon2(String mon2) {
        this.mon2 = mon2;
    }

    public void setHeSoMon2(Integer heSoMon2) {
        this.heSoMon2 = heSoMon2;
    }

    public void setMon3(String mon3) {
        this.mon3 = mon3;
    }

    public void setHeSoMon3(Integer heSoMon3) {
        this.heSoMon3 = heSoMon3;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setN1(Integer N1) {
        this.N1 = N1;
    }

    public void setTO(Integer TO) {
        this.TO = TO;
    }

    public void setLI(Integer LI) {
        this.LI = LI;
    }

    public void setHO(Integer HO) {
        this.HO = HO;
    }

    public void setSI(Integer SI) {
        this.SI = SI;
    }

    public void setVA(Integer VA) {
        this.VA = VA;
    }

    public void setSU(Integer SU) {
        this.SU = SU;
    }

    public void setDI(Integer DI) {
        this.DI = DI;
    }

    public void setTI(Integer TI) {
        this.TI = TI;
    }

    public void setKHAC(Integer KHAC) {
        this.KHAC = KHAC;
    }

    public void setKTPL(Integer KTPL) {
        this.KTPL = KTPL;
    }

    public void setDoLech(Double doLech) {
        this.doLech = doLech;
    }

    
}