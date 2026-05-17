package com.teladan.amaliah.ui;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0007\u001a\u00020\bH\u0002J\u0012\u0010\t\u001a\u00020\b2\b\u0010\n\u001a\u0004\u0018\u00010\u000bH\u0014J\b\u0010\f\u001a\u00020\bH\u0002J\b\u0010\r\u001a\u00020\bH\u0002J\u0018\u0010\u000e\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\u0018\u0010\u0013\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\b\u0010\u0014\u001a\u00020\u0015H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0016"}, d2 = {"Lcom/teladan/amaliah/ui/SettingKriteriaActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "binding", "Lcom/teladan/amaliah/databinding/ActivitySettingKriteriaBinding;", "database", "Lcom/teladan/amaliah/data/local/AppDatabase;", "loadCurrentMatrix", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "resetToDefault", "saveMatrix", "saveMatrixToDatabase", "matrix", "Lcom/teladan/amaliah/data/local/entity/KriteriaMatrixEntity;", "cr", "", "showConsistencyWarningDialog", "validateInputs", "", "app_debug"})
public final class SettingKriteriaActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.teladan.amaliah.databinding.ActivitySettingKriteriaBinding binding;
    private com.teladan.amaliah.data.local.AppDatabase database;
    
    public SettingKriteriaActivity() {
        super();
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    private final void loadCurrentMatrix() {
    }
    
    private final void resetToDefault() {
    }
    
    private final boolean validateInputs() {
        return false;
    }
    
    private final void saveMatrix() {
    }
    
    private final void showConsistencyWarningDialog(com.teladan.amaliah.data.local.entity.KriteriaMatrixEntity matrix, double cr) {
    }
    
    private final void saveMatrixToDatabase(com.teladan.amaliah.data.local.entity.KriteriaMatrixEntity matrix, double cr) {
    }
}