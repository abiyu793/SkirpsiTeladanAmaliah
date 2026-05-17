package com.teladan.amaliah.ui.fragment;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u000f\u001a\u00020\u0010H\u0002J\u0010\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\fH\u0002J\b\u0010\u0013\u001a\u00020\u0010H\u0002J$\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u0016J\b\u0010\u001c\u001a\u00020\u0010H\u0016J\b\u0010\u001d\u001a\u00020\u0010H\u0016J\u001a\u0010\u001e\u001a\u00020\u00102\u0006\u0010\u001f\u001a\u00020\u00152\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u0016J\b\u0010 \u001a\u00020\u0010H\u0002J\b\u0010!\u001a\u00020\u0010H\u0002J\u0010\u0010\"\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\fH\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\u00020\u00048BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u000e\u0010\b\u001a\u00020\tX\u0082.\u00a2\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006#"}, d2 = {"Lcom/teladan/amaliah/ui/fragment/SiswaFragment;", "Landroidx/fragment/app/Fragment;", "()V", "_binding", "Lcom/teladan/amaliah/databinding/FragmentSiswaBinding;", "binding", "getBinding", "()Lcom/teladan/amaliah/databinding/FragmentSiswaBinding;", "database", "Lcom/teladan/amaliah/data/local/AppDatabase;", "originalList", "", "Lcom/teladan/amaliah/data/local/entity/SiswaEntity;", "siswaAdapter", "Lcom/teladan/amaliah/ui/adapter/SiswaAdapter;", "applyFilterAndSort", "", "deleteSiswa", "siswa", "loadDataSiswa", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onDestroyView", "onResume", "onViewCreated", "view", "setupRecyclerView", "setupSearchAndSort", "showDeleteConfirmationDialog", "app_debug"})
public final class SiswaFragment extends androidx.fragment.app.Fragment {
    @org.jetbrains.annotations.Nullable
    private com.teladan.amaliah.databinding.FragmentSiswaBinding _binding;
    private com.teladan.amaliah.data.local.AppDatabase database;
    private com.teladan.amaliah.ui.adapter.SiswaAdapter siswaAdapter;
    @org.jetbrains.annotations.NotNull
    private java.util.List<com.teladan.amaliah.data.local.entity.SiswaEntity> originalList;
    
    public SiswaFragment() {
        super();
    }
    
    private final com.teladan.amaliah.databinding.FragmentSiswaBinding getBinding() {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public android.view.View onCreateView(@org.jetbrains.annotations.NotNull
    android.view.LayoutInflater inflater, @org.jetbrains.annotations.Nullable
    android.view.ViewGroup container, @org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
        return null;
    }
    
    @java.lang.Override
    public void onViewCreated(@org.jetbrains.annotations.NotNull
    android.view.View view, @org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override
    public void onResume() {
    }
    
    private final void setupRecyclerView() {
    }
    
    private final void setupSearchAndSort() {
    }
    
    private final void applyFilterAndSort() {
    }
    
    private final void loadDataSiswa() {
    }
    
    private final void showDeleteConfirmationDialog(com.teladan.amaliah.data.local.entity.SiswaEntity siswa) {
    }
    
    private final void deleteSiswa(com.teladan.amaliah.data.local.entity.SiswaEntity siswa) {
    }
    
    @java.lang.Override
    public void onDestroyView() {
    }
}