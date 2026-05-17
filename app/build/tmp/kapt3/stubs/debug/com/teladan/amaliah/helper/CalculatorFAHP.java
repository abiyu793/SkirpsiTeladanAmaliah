package com.teladan.amaliah.helper;

/**
 * FULL FUZZY AHP (Metode Chang 1996)
 * Alur Perhitungan:
 * 1. Menentukan kriteria (Dari Entity Database)
 * 2. Membuat matriks perbandingan (Dari Pakar/SettingKriteriaActivity)
 * 3. Konversi ke TFN
 * 4. Hitung Row Sum
 * 5. Hitung Synthetic Extent
 * 6. Degree of Possibility
 * 7. Normalisasi bobot
 * 8. Hitung nilai siswa (Fungsi akademik, praktik, hadir, disiplin)
 * 9. Fuzzifikasi nilai siswa
 * 10. Perkalian fuzzy dengan bobot
 * 11. Defuzzifikasi
 * 12. Ranking (Oleh ViewModel/Fragment)
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u0006\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u00002\u00020\u0001:\u0001\u001fB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\t\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\u0007J&\u0010\f\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\u00072\u0006\u0010\u000e\u001a\u00020\u00072\u0006\u0010\u000f\u001a\u00020\u00072\u0006\u0010\u0010\u001a\u00020\u0007J\u0006\u0010\u0011\u001a\u00020\u0007J\u0016\u0010\u0012\u001a\u00020\u00072\u0006\u0010\u0013\u001a\u00020\u00072\u0006\u0010\u0014\u001a\u00020\u0007J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0007H\u0002J\u000e\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0002J\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006J\u0016\u0010\u001a\u001a\u00020\u00072\u0006\u0010\u001a\u001a\u00020\u00072\u0006\u0010\u001b\u001a\u00020\u0007J\u0016\u0010\u001c\u001a\u00020\u00072\u0006\u0010\u001d\u001a\u00020\u00072\u0006\u0010\u001e\u001a\u00020\u0007R\u0016\u0010\u0005\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006 "}, d2 = {"Lcom/teladan/amaliah/helper/CalculatorFAHP;", "", "matrixData", "Lcom/teladan/amaliah/data/local/entity/KriteriaMatrixEntity;", "(Lcom/teladan/amaliah/data/local/entity/KriteriaMatrixEntity;)V", "cachedWeights", "", "", "fixedWeights", "akademik", "rapor", "teori", "calculateFinalScore", "nilaiAkademik", "nilaiPraktik", "nilaiHadir", "nilaiDisiplin", "checkConsistencyRatio", "disiplin", "pelanggaran", "sikap", "fuzzifyScore", "Lcom/teladan/amaliah/helper/CalculatorFAHP$TFN;", "score", "getNormalizedWeights", "getWeights", "hadir", "terlambat", "praktik", "lab", "pkl", "TFN", "app_debug"})
public final class CalculatorFAHP {
    @org.jetbrains.annotations.NotNull
    private final com.teladan.amaliah.data.local.entity.KriteriaMatrixEntity matrixData = null;
    @org.jetbrains.annotations.Nullable
    private java.util.List<java.lang.Double> cachedWeights;
    @org.jetbrains.annotations.NotNull
    private final java.util.List<java.lang.Double> fixedWeights = null;
    
    public CalculatorFAHP(@org.jetbrains.annotations.NotNull
    com.teladan.amaliah.data.local.entity.KriteriaMatrixEntity matrixData) {
        super();
    }
    
    private final java.util.List<java.lang.Double> getNormalizedWeights() {
        return null;
    }
    
    public final double checkConsistencyRatio() {
        return 0.0;
    }
    
    private final com.teladan.amaliah.helper.CalculatorFAHP.TFN fuzzifyScore(double score) {
        return null;
    }
    
    public final double akademik(double rapor, double teori) {
        return 0.0;
    }
    
    public final double praktik(double lab, double pkl) {
        return 0.0;
    }
    
    public final double hadir(double hadir, double terlambat) {
        return 0.0;
    }
    
    public final double disiplin(double pelanggaran, double sikap) {
        return 0.0;
    }
    
    public final double calculateFinalScore(double nilaiAkademik, double nilaiPraktik, double nilaiHadir, double nilaiDisiplin) {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<java.lang.Double> getWeights() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0006\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\r\u001a\u00020\u0003H\u00c6\u0003J\'\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0012\u001a\u00020\u0013H\u00d6\u0001J\u0011\u0010\u0014\u001a\u00020\u00002\u0006\u0010\u0011\u001a\u00020\u0000H\u0086\u0002J\u0011\u0010\u0015\u001a\u00020\u00002\u0006\u0010\u0016\u001a\u00020\u0003H\u0086\u0002J\t\u0010\u0017\u001a\u00020\u0018H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\bR\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\b\u00a8\u0006\u0019"}, d2 = {"Lcom/teladan/amaliah/helper/CalculatorFAHP$TFN;", "", "l", "", "m", "u", "(DDD)V", "getL", "()D", "getM", "getU", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "", "plus", "times", "weight", "toString", "", "app_debug"})
    public static final class TFN {
        private final double l = 0.0;
        private final double m = 0.0;
        private final double u = 0.0;
        
        public TFN(double l, double m, double u) {
            super();
        }
        
        public final double getL() {
            return 0.0;
        }
        
        public final double getM() {
            return 0.0;
        }
        
        public final double getU() {
            return 0.0;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.teladan.amaliah.helper.CalculatorFAHP.TFN plus(@org.jetbrains.annotations.NotNull
        com.teladan.amaliah.helper.CalculatorFAHP.TFN other) {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.teladan.amaliah.helper.CalculatorFAHP.TFN times(double weight) {
            return null;
        }
        
        public final double component1() {
            return 0.0;
        }
        
        public final double component2() {
            return 0.0;
        }
        
        public final double component3() {
            return 0.0;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.teladan.amaliah.helper.CalculatorFAHP.TFN copy(double l, double m, double u) {
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
}