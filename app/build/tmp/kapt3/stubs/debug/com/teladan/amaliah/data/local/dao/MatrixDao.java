package com.teladan.amaliah.data.local.dao;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\bg\u0018\u00002\u00020\u0001J\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0004J\u0019\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0003H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\b\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\t"}, d2 = {"Lcom/teladan/amaliah/data/local/dao/MatrixDao;", "", "getMatrix", "Lcom/teladan/amaliah/data/local/entity/KriteriaMatrixEntity;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveMatrix", "", "matrix", "(Lcom/teladan/amaliah/data/local/entity/KriteriaMatrixEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@androidx.room.Dao
public abstract interface MatrixDao {
    
    @androidx.room.Query(value = "SELECT * FROM kriteria_matrix WHERE id = 1 LIMIT 1")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getMatrix(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.teladan.amaliah.data.local.entity.KriteriaMatrixEntity> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object saveMatrix(@org.jetbrains.annotations.NotNull
    com.teladan.amaliah.data.local.entity.KriteriaMatrixEntity matrix, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}