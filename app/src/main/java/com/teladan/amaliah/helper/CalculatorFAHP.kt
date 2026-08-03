package com.teladan.amaliah.helper

import com.teladan.amaliah.data.local.entity.KriteriaMatrixEntity

/**
 * Kalkulator F-AHP Dinamis yang menghitung bobot matriks secara dinamis, 
 * namun melakukan evaluasi skor menggunakan rumus linear (presisi Excel).
 */
class CalculatorFAHP(
    private val matrixData: KriteriaMatrixEntity
) {
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

    // 1. Rumus Rata-Rata Sub-Kriteria (Sesuai Excel)
    fun akademik(rapor: Double, teori: Double): Double = (rapor + teori) / 2.0
    
    fun praktik(lab: Double, pkl: Double): Double = if (pkl == 0.0) lab else (lab + pkl) / 2.0
    
    fun hadir(hadir: Double, terlambat: Double): Double = (hadir + (100.0 - terlambat)) / 2.0
    
    fun disiplin(pelanggaran: Double, sikap: Double): Double = ((100.0 - pelanggaran) + sikap) / 2.0

    // 3. Rumus Total Skor Akhir
    fun calculateFinalScore(
        nilaiAkademik: Double,
        nilaiPraktik: Double,
        nilaiHadir: Double,
        nilaiDisiplin: Double
    ): Double {
        val weights = getNormalizedWeights()
        val totalScore = (nilaiAkademik * weights[0]) + 
                         (nilaiPraktik * weights[1]) + 
                         (nilaiHadir * weights[2]) + 
                         (nilaiDisiplin * weights[3])

        // Mengembalikan presisi penuh tanpa dibulatkan
        return if (totalScore.isNaN() || totalScore.isInfinite()) 0.0 else totalScore
    }

    fun getWeights(): List<Double> = getNormalizedWeights()
}