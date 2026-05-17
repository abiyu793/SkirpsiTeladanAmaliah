package com.teladan.amaliah.data.local.entity;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0006\n\u0002\b1\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0087\b\u0018\u00002\u00020\u0001Bw\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0005\u0012\u0006\u0010\b\u001a\u00020\u0005\u0012\u0006\u0010\t\u001a\u00020\u0005\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\u000b\u0012\u0006\u0010\r\u001a\u00020\u000b\u0012\u0006\u0010\u000e\u001a\u00020\u000b\u0012\u0006\u0010\u000f\u001a\u00020\u000b\u0012\u0006\u0010\u0010\u001a\u00020\u000b\u0012\u0006\u0010\u0011\u001a\u00020\u000b\u0012\u0006\u0010\u0012\u001a\u00020\u000b\u00a2\u0006\u0002\u0010\u0013J\t\u0010-\u001a\u00020\u0003H\u00c6\u0003J\t\u0010.\u001a\u00020\u000bH\u00c6\u0003J\t\u0010/\u001a\u00020\u000bH\u00c6\u0003J\t\u00100\u001a\u00020\u000bH\u00c6\u0003J\t\u00101\u001a\u00020\u000bH\u00c6\u0003J\t\u00102\u001a\u00020\u000bH\u00c6\u0003J\t\u00103\u001a\u00020\u0005H\u00c6\u0003J\t\u00104\u001a\u00020\u0005H\u00c6\u0003J\t\u00105\u001a\u00020\u0005H\u00c6\u0003J\t\u00106\u001a\u00020\u0005H\u00c6\u0003J\t\u00107\u001a\u00020\u0005H\u00c6\u0003J\t\u00108\u001a\u00020\u000bH\u00c6\u0003J\t\u00109\u001a\u00020\u000bH\u00c6\u0003J\t\u0010:\u001a\u00020\u000bH\u00c6\u0003J\u0095\u0001\u0010;\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00052\b\b\u0002\u0010\b\u001a\u00020\u00052\b\b\u0002\u0010\t\u001a\u00020\u00052\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\u000b2\b\b\u0002\u0010\r\u001a\u00020\u000b2\b\b\u0002\u0010\u000e\u001a\u00020\u000b2\b\b\u0002\u0010\u000f\u001a\u00020\u000b2\b\b\u0002\u0010\u0010\u001a\u00020\u000b2\b\b\u0002\u0010\u0011\u001a\u00020\u000b2\b\b\u0002\u0010\u0012\u001a\u00020\u000bH\u00c6\u0001J\u0013\u0010<\u001a\u00020=2\b\u0010>\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010?\u001a\u00020\u0003H\u00d6\u0001J\t\u0010@\u001a\u00020\u0005H\u00d6\u0001R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\u0010\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\u0007\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0011\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0019R\u0011\u0010\r\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0017R\u0011\u0010\u000e\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0017R\u0011\u0010\n\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0017R\u0011\u0010\f\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0017R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0019R\u0011\u0010\u000f\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u0017R\u0011\u0010\u0011\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u0017R\u0011\u0010\"\u001a\u00020\u000b8F\u00a2\u0006\u0006\u001a\u0004\b#\u0010\u0017R\u0011\u0010$\u001a\u00020\u000b8F\u00a2\u0006\u0006\u001a\u0004\b%\u0010\u0017R\u0011\u0010&\u001a\u00020\u000b8F\u00a2\u0006\u0006\u001a\u0004\b\'\u0010\u0017R\u0011\u0010(\u001a\u00020\u000b8F\u00a2\u0006\u0006\u001a\u0004\b)\u0010\u0017R\u0011\u0010\u0012\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010\u0017R\u0011\u0010\t\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b+\u0010\u0019R\u0011\u0010\b\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b,\u0010\u0019\u00a8\u0006A"}, d2 = {"Lcom/teladan/amaliah/data/local/entity/SiswaEntity;", "", "id", "", "nis", "", "nama", "jurusan", "tingkat_kelas", "tahun_ajaran", "nilai_rapor", "", "nilai_teori", "nilai_lab", "nilai_pkl", "persentase_hadir", "jam_terlambat", "poin_pelanggaran", "skor_sikap", "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;DDDDDDDD)V", "getId", "()I", "getJam_terlambat", "()D", "getJurusan", "()Ljava/lang/String;", "getNama", "getNilai_lab", "getNilai_pkl", "getNilai_rapor", "getNilai_teori", "getNis", "getPersentase_hadir", "getPoin_pelanggaran", "rataAkademik", "getRataAkademik", "rataDisiplin", "getRataDisiplin", "rataHadir", "getRataHadir", "rataPraktik", "getRataPraktik", "getSkor_sikap", "getTahun_ajaran", "getTingkat_kelas", "component1", "component10", "component11", "component12", "component13", "component14", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "toString", "app_debug"})
@androidx.room.Entity(tableName = "siswa_table")
public final class SiswaEntity {
    @androidx.room.PrimaryKey(autoGenerate = true)
    private final int id = 0;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String nis = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String nama = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String jurusan = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String tingkat_kelas = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String tahun_ajaran = null;
    private final double nilai_rapor = 0.0;
    private final double nilai_teori = 0.0;
    private final double nilai_lab = 0.0;
    private final double nilai_pkl = 0.0;
    private final double persentase_hadir = 0.0;
    private final double jam_terlambat = 0.0;
    private final double poin_pelanggaran = 0.0;
    private final double skor_sikap = 0.0;
    
    public SiswaEntity(int id, @org.jetbrains.annotations.NotNull
    java.lang.String nis, @org.jetbrains.annotations.NotNull
    java.lang.String nama, @org.jetbrains.annotations.NotNull
    java.lang.String jurusan, @org.jetbrains.annotations.NotNull
    java.lang.String tingkat_kelas, @org.jetbrains.annotations.NotNull
    java.lang.String tahun_ajaran, double nilai_rapor, double nilai_teori, double nilai_lab, double nilai_pkl, double persentase_hadir, double jam_terlambat, double poin_pelanggaran, double skor_sikap) {
        super();
    }
    
    public final int getId() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getNis() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getNama() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getJurusan() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getTingkat_kelas() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getTahun_ajaran() {
        return null;
    }
    
    public final double getNilai_rapor() {
        return 0.0;
    }
    
    public final double getNilai_teori() {
        return 0.0;
    }
    
    public final double getNilai_lab() {
        return 0.0;
    }
    
    public final double getNilai_pkl() {
        return 0.0;
    }
    
    public final double getPersentase_hadir() {
        return 0.0;
    }
    
    public final double getJam_terlambat() {
        return 0.0;
    }
    
    public final double getPoin_pelanggaran() {
        return 0.0;
    }
    
    public final double getSkor_sikap() {
        return 0.0;
    }
    
    public final double getRataAkademik() {
        return 0.0;
    }
    
    public final double getRataPraktik() {
        return 0.0;
    }
    
    public final double getRataHadir() {
        return 0.0;
    }
    
    public final double getRataDisiplin() {
        return 0.0;
    }
    
    public final int component1() {
        return 0;
    }
    
    public final double component10() {
        return 0.0;
    }
    
    public final double component11() {
        return 0.0;
    }
    
    public final double component12() {
        return 0.0;
    }
    
    public final double component13() {
        return 0.0;
    }
    
    public final double component14() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component6() {
        return null;
    }
    
    public final double component7() {
        return 0.0;
    }
    
    public final double component8() {
        return 0.0;
    }
    
    public final double component9() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.teladan.amaliah.data.local.entity.SiswaEntity copy(int id, @org.jetbrains.annotations.NotNull
    java.lang.String nis, @org.jetbrains.annotations.NotNull
    java.lang.String nama, @org.jetbrains.annotations.NotNull
    java.lang.String jurusan, @org.jetbrains.annotations.NotNull
    java.lang.String tingkat_kelas, @org.jetbrains.annotations.NotNull
    java.lang.String tahun_ajaran, double nilai_rapor, double nilai_teori, double nilai_lab, double nilai_pkl, double persentase_hadir, double jam_terlambat, double poin_pelanggaran, double skor_sikap) {
        return null;
    }
    
    @java.lang.Override
    public boolean equals(@org.jetbrains.annotations.Nullable
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public java.lang.String toString() {
        return null;
    }
}