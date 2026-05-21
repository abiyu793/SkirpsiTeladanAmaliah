package com.teladan.amaliah.helper

import com.teladan.amaliah.data.local.entity.KriteriaMatrixEntity
import kotlin.math.max
import kotlin.math.min

/**
 * FULL FUZZY AHP (Metode Chang 1996) dengan Bobot Dinamis Crisp AHP
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

    // Helper untuk matriks crisp
    private fun getCrispMatrix(): Array<DoubleArray> {
        val safe = { value: Double -> if (value == 0.0) 0.0001 else value }
        return arrayOf(
            // Baris 0 (Akademik)
            doubleArrayOf(1.0, 1.0 / safe(matrixData.praktik_vs_akademik), matrixData.akademik_vs_hadir, 1.0 / safe(matrixData.disiplin_vs_akademik)),
            // Baris 1 (Praktik)
            doubleArrayOf(matrixData.praktik_vs_akademik, 1.0, matrixData.praktik_vs_hadir, matrixData.praktik_vs_disiplin),
            // Baris 2 (Hadir)
            doubleArrayOf(1.0 / safe(matrixData.akademik_vs_hadir), 1.0 / safe(matrixData.praktik_vs_hadir), 1.0, matrixData.hadir_vs_disiplin),
            // Baris 3 (Disiplin)
            doubleArrayOf(matrixData.disiplin_vs_akademik, 1.0 / safe(matrixData.praktik_vs_disiplin), 1.0 / safe(matrixData.hadir_vs_disiplin), 1.0)
        )
    }

    // =======================================================
    // FASE MENGHITUNG BOBOT (CRISP AHP)
    // =======================================================
    private fun getNormalizedWeights(): List<Double> {
        if (cachedWeights != null) return cachedWeights!!

        val crispMatrix = getCrispMatrix()
        val n = crispMatrix.size

        // Hitung total nilai per kolom (Column Sums)
        val colSums = DoubleArray(n)
        for (j in 0 until n) {
            for (i in 0 until n) {
                colSums[j] += crispMatrix[i][j]
            }
        }

        // Lakukan normalisasi kolom
        val normalizedMatrix = Array(n) { DoubleArray(n) }
        for (i in 0 until n) {
            for (j in 0 until n) {
                normalizedMatrix[i][j] = crispMatrix[i][j] / colSums[j]
            }
        }

        // Hitung rata-rata tiap baris untuk mendapatkan bobot dinamis
        val weights = DoubleArray(n)
        for (i in 0 until n) {
            weights[i] = normalizedMatrix[i].sum() / n
        }

        cachedWeights = weights.toList()
        return cachedWeights!!
    }

    // =======================================================
    // UJI KONSISTENSI (CR)
    // =======================================================
    fun checkConsistencyRatio(): Double {
        val crispMatrix = getCrispMatrix()
        val n = crispMatrix.size

        val colSums = DoubleArray(n)
        for (j in 0 until n) {
            for (i in 0 until n) {
                colSums[j] += crispMatrix[i][j]
            }
        }

        val crispWeights = getNormalizedWeights()

        var lambdaMax = 0.0
        for (j in 0 until n) {
            lambdaMax += colSums[j] * crispWeights[j]
        }

        val ci = (lambdaMax - n) / (n - 1)
        val ri = 0.90 // Random Index untuk matriks 4x4

        return ci / ri
    }

    // =======================================================
    // FASE EVALUASI SISWA
    // =======================================================

    private fun fuzzifyScore(score: Double): TFN {
        val safeScore = score.coerceIn(0.0, 100.0)
        val lower = max(0.0, safeScore - 1.0)
        val upper = min(100.0, safeScore + 1.0)
        return TFN(lower, safeScore, upper)
    }

    fun akademik(rapor: Double, teori: Double): Double = (rapor + teori) / 2.0
    fun praktik(lab: Double, pkl: Double): Double = if (pkl > 0) (lab + pkl) / 2.0 else lab
    fun hadir(hadir: Double, terlambat: Double): Double = (hadir + (100 - terlambat)) / 2.0
    fun disiplin(pelanggaran: Double, sikap: Double): Double = ((100 - pelanggaran) + sikap) / 2.0

    fun calculateFinalScore(
        nilaiAkademik: Double,
        nilaiPraktik: Double,
        nilaiHadir: Double,
        nilaiDisiplin: Double
    ): Double {
        val weights = getNormalizedWeights()

        val fuzzyAkademik = fuzzifyScore(nilaiAkademik)
        val fuzzyPraktik = fuzzifyScore(nilaiPraktik)
        val fuzzyHadir = fuzzifyScore(nilaiHadir)
        val fuzzyDisiplin = fuzzifyScore(nilaiDisiplin)

        val scoreAkademik = fuzzyAkademik * weights[0]
        val scorePraktik = fuzzyPraktik * weights[1]
        val scoreHadir = fuzzyHadir * weights[2]
        val scoreDisiplin = fuzzyDisiplin * weights[3]

        val finalFuzzy = scoreAkademik + scorePraktik + scoreHadir + scoreDisiplin

        val finalScore = (finalFuzzy.l + finalFuzzy.m + finalFuzzy.u) / 3.0

        if (finalScore.isNaN() || finalScore.isInfinite()) {
            return 0.0
        }

        return String.format("%.2f", finalScore).replace(',', '.').toDouble()
    }

    fun getWeights(): List<Double> = getNormalizedWeights()
}