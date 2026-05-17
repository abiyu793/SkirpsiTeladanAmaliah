package com.teladan.amaliah.data.local;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\'\u0018\u0000 \t2\u00020\u0001:\u0001\tB\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&J\b\u0010\u0005\u001a\u00020\u0006H&J\b\u0010\u0007\u001a\u00020\bH&\u00a8\u0006\n"}, d2 = {"Lcom/teladan/amaliah/data/local/AppDatabase;", "Landroidx/room/RoomDatabase;", "()V", "adminDao", "Lcom/teladan/amaliah/data/local/dao/AdminDao;", "matrixDao", "Lcom/teladan/amaliah/data/local/dao/MatrixDao;", "siswaDao", "Lcom/teladan/amaliah/data/local/dao/SiswaDao;", "Companion", "app_debug"})
@androidx.room.Database(entities = {com.teladan.amaliah.data.local.entity.Admin.class, com.teladan.amaliah.data.local.entity.SiswaEntity.class, com.teladan.amaliah.data.local.entity.KriteriaMatrixEntity.class}, version = 4, exportSchema = false)
public abstract class AppDatabase extends androidx.room.RoomDatabase {
    @kotlin.jvm.Volatile
    @org.jetbrains.annotations.Nullable
    private static volatile com.teladan.amaliah.data.local.AppDatabase INSTANCE;
    @org.jetbrains.annotations.NotNull
    public static final com.teladan.amaliah.data.local.AppDatabase.Companion Companion = null;
    
    public AppDatabase() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public abstract com.teladan.amaliah.data.local.dao.AdminDao adminDao();
    
    @org.jetbrains.annotations.NotNull
    public abstract com.teladan.amaliah.data.local.dao.SiswaDao siswaDao();
    
    @org.jetbrains.annotations.NotNull
    public abstract com.teladan.amaliah.data.local.dao.MatrixDao matrixDao();
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001:\u0001\bB\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2 = {"Lcom/teladan/amaliah/data/local/AppDatabase$Companion;", "", "()V", "INSTANCE", "Lcom/teladan/amaliah/data/local/AppDatabase;", "getDatabase", "context", "Landroid/content/Context;", "DatabaseCallback", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.teladan.amaliah.data.local.AppDatabase getDatabase(@org.jetbrains.annotations.NotNull
        android.content.Context context) {
            return null;
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016\u00a8\u0006\u0007"}, d2 = {"Lcom/teladan/amaliah/data/local/AppDatabase$Companion$DatabaseCallback;", "Landroidx/room/RoomDatabase$Callback;", "()V", "onCreate", "", "db", "Landroidx/sqlite/db/SupportSQLiteDatabase;", "app_debug"})
        static final class DatabaseCallback extends androidx.room.RoomDatabase.Callback {
            
            public DatabaseCallback() {
                super();
            }
            
            @java.lang.Override
            public void onCreate(@org.jetbrains.annotations.NotNull
            androidx.sqlite.db.SupportSQLiteDatabase db) {
            }
        }
    }
}