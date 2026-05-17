package com.teladan.amaliah.helper

import com.teladan.amaliah.data.local.entity.KriteriaMatrixEntity
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

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
class CalculatorFAHP(
    private val matrixData: KriteriaMatrixEntity
) {

    // =======================================================
    // 1. TRIANGULAR FUZZY NUMBER (TFN)
    // =======================================================
    data class TFN(
        val l: Double,
        val m: Double,
        val u: Double
    ) {
        operator fun plus(other: TFN): TFN {
            return TFN(l + other.l, m + other.m, u + other.u)
        }

        operator fun times(weight: Double): TFN {
            return TFN(l * weight, m * weight, u * weight)
        }
    }

    private var cachedWeights: List<Double>? = null
    
    // =======================================================
    // BOBOT TETAP UNTUK TESTING (PERBAIKAN 3)
    // Aktifkan dengan mengganti comment di calculateFinalScore
    // =======================================================
    private val fixedWeights = listOf(
        0.163,  // Akademik
        0.488,  // Praktik
        0.127,  // Kehadiran
        0.222   // Kedisiplinan
    )

    // =======================================================
    // FASE MENGHITUNG BOBOT (LANGKAH 3 HINGGA 7)
    // =======================================================
    private fun getNormalizedWeights(): List<Double> {

        if (cachedWeights != null) return cachedWeights!!

        val safe = { value: Double -> if (value == 0.0) 0.0001 else value }

        val crispToTFN = { value: Double ->
            when {
                value == 1.0 -> TFN(1.0, 1.0, 1.0)
                value == 2.0 -> TFN(1.0, 2.0, 3.0)
                value == 3.0 -> TFN(2.0, 3.0, 4.0)
                value == 4.0 -> TFN(3.0, 4.0, 5.0)
                value == 5.0 -> TFN(4.0, 5.0, 6.0)
                value == 6.0 -> TFN(5.0, 6.0, 7.0)
                value == 7.0 -> TFN(6.0, 7.0, 8.0)
                value == 8.0 -> TFN(7.0, 8.0, 9.0)
                value == 9.0 -> TFN(8.0, 9.0, 9.0)
                else -> {
                    // Reciprocal stabil
                    val inv = 1.0 / value
                    TFN(inv, inv, inv)
                }
            }
        }

        // LANGKAH 2: Membuat matriks perbandingan (Input dari matrixData)
        // LANGKAH 3: Konversi ke TFN (menggunakan crispToTFN)
        val fuzzyMatrix = arrayOf(
            // Akademik
            arrayOf(
                TFN(1.0, 1.0, 1.0),
                crispToTFN(1.0 / safe(matrixData.praktik_vs_akademik)),
                crispToTFN(matrixData.akademik_vs_hadir),
                crispToTFN(1.0 / safe(matrixData.disiplin_vs_akademik))
            ),
            // Praktik
            arrayOf(
                crispToTFN(matrixData.praktik_vs_akademik),
                TFN(1.0, 1.0, 1.0),
                crispToTFN(matrixData.praktik_vs_hadir),
                crispToTFN(matrixData.praktik_vs_disiplin)
            ),
            // Kehadiran
            arrayOf(
                crispToTFN(1.0 / safe(matrixData.akademik_vs_hadir)),
                crispToTFN(1.0 / safe(matrixData.praktik_vs_hadir)),
                TFN(1.0, 1.0, 1.0),
                crispToTFN(matrixData.hadir_vs_disiplin)
            ),
            // Disiplin
            arrayOf(
                crispToTFN(matrixData.disiplin_vs_akademik),
                crispToTFN(1.0 / safe(matrixData.praktik_vs_disiplin)),
                crispToTFN(1.0 / safe(matrixData.hadir_vs_disiplin)),
                TFN(1.0, 1.0, 1.0)
            )
        )

        val n = fuzzyMatrix.size

        // LANGKAH 4: Hitung Row Sum
        val rowSums = Array(n) { TFN(0.0, 0.0, 0.0) }
        var totalSum = TFN(0.0, 0.0, 0.0)

        for (i in 0 until n) {
            var sum = TFN(0.0, 0.0, 0.0)
            for (j in 0 until n) {
                sum += fuzzyMatrix[i][j]
            }
            rowSums[i] = sum
            totalSum += sum
        }

        // LANGKAH 5: Hitung Synthetic Extent (Si)
        val s = Array(n) { i ->
            TFN(
                rowSums[i].l / totalSum.u,
                rowSums[i].m / totalSum.m,
                rowSums[i].u / totalSum.l
            )
        }

        // LANGKAH 6: Degree of Possibility V(M1 >= M2) - DIPERBAIKI (PERBAIKAN 4)
        val dPrime = DoubleArray(n)
        for (i in 0 until n) {
            var minV = 1.0
            for (j in 0 until n) {
                if (i != j) {
                    val m1 = s[i]
                    val m2 = s[j]
                    val vValue = when {
                        m1.m >= m2.m -> 1.0
                        m2.l >= m1.u -> 0.0
                        else -> {
                            val numerator = m2.l - m1.u
                            val denominator = (m2.m - m2.u) - (m1.m - m1.l)
                            // PERBAIKAN 4: Gunakan epsilon untuk menghindari floating point error
                            if (abs(denominator) < 0.000001) 0.0 
                            else max(0.0, min(1.0, numerator / denominator))
                        }
                    }
                    if (vValue < minV) {
                        minV = vValue
                    }
                }
            }
            dPrime[i] = minV
        }

        // LANGKAH 7: Normalisasi bobot
        val totalDPrime = dPrime.sum()
        cachedWeights = if (totalDPrime > 0) {
            dPrime.map { it / totalDPrime }
        } else {
            dPrime.map { 1.0 / n }
        }

        return cachedWeights!!
    }

    // =======================================================
    // UJI KONSISTENSI (CR) - Dilakukan di Fase 2
    // =======================================================
    fun checkConsistencyRatio(): Double {
        val safe = { value: Double -> if (value == 0.0) 0.0001 else value }

        val crispMatrix = arrayOf(
            doubleArrayOf(1.0, 1.0 / safe(matrixData.praktik_vs_akademik), matrixData.akademik_vs_hadir, 1.0 / safe(matrixData.disiplin_vs_akademik)),
            doubleArrayOf(matrixData.praktik_vs_akademik, 1.0, matrixData.praktik_vs_hadir, matrixData.praktik_vs_disiplin),
            doubleArrayOf(1.0 / safe(matrixData.akademik_vs_hadir), 1.0 / safe(matrixData.praktik_vs_hadir), 1.0, matrixData.hadir_vs_disiplin),
            doubleArrayOf(matrixData.disiplin_vs_akademik, 1.0 / safe(matrixData.praktik_vs_disiplin), 1.0 / safe(matrixData.hadir_vs_disiplin), 1.0)
        )

        val n = 4

        val colSums = DoubleArray(n)
        for (j in 0 until n) {
            for (i in 0 until n) {
                colSums[j] += crispMatrix[i][j]
            }
        }

        val normalizedMatrix = Array(n) { DoubleArray(n) }
        for (i in 0 until n) {
            for (j in 0 until n) {
                normalizedMatrix[i][j] = crispMatrix[i][j] / colSums[j]
            }
        }

        val crispWeights = DoubleArray(n)
        for (i in 0 until n) {
            crispWeights[i] = normalizedMatrix[i].sum() / n
        }

        var lambdaMax = 0.0
        for (j in 0 until n) {
            lambdaMax += colSums[j] * crispWeights[j]
        }

        val ci = (lambdaMax - n) / (n - 1)
        val ri = 0.90 // Random Index untuk matriks 4x4

        return ci / ri
    }

    // =======================================================
    // FASE EVALUASI SISWA (LANGKAH 8 HINGGA 11)
    // =======================================================
    
    // LANGKAH 9: Fuzzifikasi nilai siswa - DIPERBAIKI (PERBAIKAN 1)
    // Margin ±1 untuk hasil lebih dekat dengan Excel
    private fun fuzzifyScore(score: Double): TFN {
        val safeScore = score.coerceIn(0.0, 100.0)
        // PERBAIKAN 1: Margin ±1 (dari sebelumnya ±2)
        val lower = max(0.0, safeScore - 1.0)
        val upper = min(100.0, safeScore + 1.0)
        return TFN(lower, safeScore, upper)
    }

    // LANGKAH 8: Hitung nilai siswa (Fungsi bantu)
    fun akademik(rapor: Double, teori: Double): Double = (rapor + teori) / 2.0
    fun praktik(lab: Double, pkl: Double): Double = if (pkl > 0) (lab + pkl) / 2.0 else lab
    fun hadir(hadir: Double, terlambat: Double): Double = (hadir + (100 - terlambat)) / 2.0
    fun disiplin(pelanggaran: Double, sikap: Double): Double = ((100 - pelanggaran) + sikap) / 2.0

    // LANGKAH 10 & 11: Perkalian, Penjumlahan, dan Defuzzifikasi - DIPERBAIKI (PERBAIKAN 2 & 3)
    fun calculateFinalScore(
        nilaiAkademik: Double,
        nilaiPraktik: Double,
        nilaiHadir: Double,
        nilaiDisiplin: Double
    ): Double {
        // PERBAIKAN 3: Gunakan bobot tetap untuk validasi skripsi
        // Untuk menggunakan bobot dinamis, ganti fixedWeights dengan getNormalizedWeights()
        val weights = fixedWeights  // ← Pakai bobot tetap
        // val weights = getNormalizedWeights()  // ← Pakai bobot dinamis (uncomment jika ingin)

        // LANGKAH 9: Fuzzifikasi nilai siswa
        val fuzzyAkademik = fuzzifyScore(nilaiAkademik)
        val fuzzyPraktik = fuzzifyScore(nilaiPraktik)
        val fuzzyHadir = fuzzifyScore(nilaiHadir)
        val fuzzyDisiplin = fuzzifyScore(nilaiDisiplin)

        // LANGKAH 10: Perkalian fuzzy dengan bobot
        val scoreAkademik = fuzzyAkademik * weights[0]
        val scorePraktik = fuzzyPraktik * weights[1]
        val scoreHadir = fuzzyHadir * weights[2]
        val scoreDisiplin = fuzzyDisiplin * weights[3]

        // Penjumlahan
        val finalFuzzy = scoreAkademik + scorePraktik + scoreHadir + scoreDisiplin

        // LANGKAH 11: Defuzzifikasi
        var finalScore = (finalFuzzy.l + finalFuzzy.m + finalFuzzy.u) / 3.0

        if (finalScore.isNaN()) {
            return 0.0
        }
        
        // PERBAIKAN 2: Bulatkan ke 2 desimal agar persis seperti Excel
        return String.format("%.2f", finalScore).toDouble()
    }

    fun getWeights(): List<Double> = getNormalizedWeights()
}