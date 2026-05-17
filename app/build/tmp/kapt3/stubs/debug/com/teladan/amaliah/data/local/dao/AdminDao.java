package com.teladan.amaliah.data.local.dao;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\bg\u0018\u00002\u00020\u0001J\u0019\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006J#\u0010\u0007\u001a\u0004\u0018\u00010\u00052\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\tH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000b\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\f"}, d2 = {"Lcom/teladan/amaliah/data/local/dao/AdminDao;", "", "insertAdmin", "", "admin", "Lcom/teladan/amaliah/data/local/entity/Admin;", "(Lcom/teladan/amaliah/data/local/entity/Admin;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "loginAdmin", "user", "", "pass", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@androidx.room.Dao
public abstract interface AdminDao {
    
    @androidx.room.Query(value = "SELECT * FROM admin_table WHERE username = :user AND password = :pass LIMIT 1")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object loginAdmin(@org.jetbrains.annotations.NotNull
    java.lang.String user, @org.jetbrains.annotations.NotNull
    java.lang.String pass, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.teladan.amaliah.data.local.entity.Admin> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object insertAdmin(@org.jetbrains.annotations.NotNull
    com.teladan.amaliah.data.local.entity.Admin admin, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}