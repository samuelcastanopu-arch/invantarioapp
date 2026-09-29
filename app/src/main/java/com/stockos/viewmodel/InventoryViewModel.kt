package com.stockos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.stockos.data.local.ProductEntity
import com.stockos.data.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class InventoryStats(
    val totalProducts: Int = 0,
    val outOfStockCount: Int = 0,
    val lowStockCount: Int = 0,
    val totalInventoryValue: Double = 0.0,
)

data class AddProductFormState(
    val name: String = "",
    val nameError: String? = null,
    val sku: String = "",
    val skuError: String? = null,
    val category: String = "General",
    val location: String = "",
    val locationError: String? = null,
    val currentStock: Int = 0,
    val minimumStock: String = "5",
    val minimumStockError: String? = null,
    val unitPrice: String = "0",
    val unitPriceError: String? = null,
    val inventoryValue: Double = 0.0,
    val isSaving: Boolean = false
)

class InventoryViewModel(
    private val repository: ProductRepository
) : ViewModel() {

    // Available categories for filtering
    val categories = listOf("TODOS", "HERRAMIENTAS", "CABLES", "ELÉCTRICO", "PINTURAS", "PLOMERÍA")
    val formCategories = listOf("General", "Herramientas", "Cables", "Eléctrico", "Pinturas", "Plomería")

    // Filter states
    val searchQuery = MutableStateFlow("")
    val selectedCategory = MutableStateFlow("TODOS")

    // Products flow from repository
    private val allProducts = repository.allProducts

    // Combined filtered products
    val filteredProducts: StateFlow<List<ProductEntity>> = combine(
        allProducts,
        searchQuery,
        selectedCategory
    ) { products, query, category ->
        val trimmedQuery = query.trim().lowercase()
        products.filter { product ->
            val matchesCategory = if (category == "TODOS") {
                true
            } else {
                product.category.equals(category, ignoreCase = true)
            }

            val matchesQuery = if (trimmedQuery.isEmpty()) {
                true
            } else {
                product.name.lowercase().contains(trimmedQuery) ||
                        product.sku.lowercase().contains(trimmedQuery)
            }

            matchesCategory && matchesQuery
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Calculated Inventory Stats
    val inventoryStats: StateFlow<InventoryStats> = allProducts.map { products ->
        val total = products.size
        val outOfStock = products.count { it.isOutOfStock }
        val lowStock = products.count { it.isLowStock }
        val totalValue = products.sumOf { it.totalValue }

        InventoryStats(
            totalProducts = total,
            outOfStockCount = outOfStock,
            lowStockCount = lowStock,
            totalInventoryValue = totalValue
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), InventoryStats())

    // Form state for AddProductScreen
    private val _formState = MutableStateFlow(AddProductFormState())
    val formState: StateFlow<AddProductFormState> = _formState

    fun onSearchQueryChange(query: String) {
        searchQuery.value = query
    }

    fun onCategorySelect(category: String) {
        selectedCategory.value = category
    }

    // Form handlers
    fun onNameChange(name: String) {
        _formState.update { it.copy(name = name, nameError = null) }
    }

    fun onSkuChange(sku: String) {
        _formState.update { it.copy(sku = sku.uppercase(), skuError = null) }
    }

    fun onFormCategoryChange(category: String) {
        _formState.update { it.copy(category = category) }
    }

    fun onLocationChange(location: String) {
        _formState.update { it.copy(location = location, locationError = null) }
    }

    fun incrementStock() {
        _formState.update { current ->
            val newStock = current.currentStock + 1
            val price = current.unitPrice.toDoubleOrNull() ?: 0.0
            current.copy(
                currentStock = newStock,
                inventoryValue = newStock * price
            )
        }
    }

    fun decrementStock() {
        _formState.update { current ->
            val newStock = (current.currentStock - 1).coerceAtLeast(0)
            val price = current.unitPrice.toDoubleOrNull() ?: 0.0
            current.copy(
                currentStock = newStock,
                inventoryValue = newStock * price
            )
        }
    }

    fun onMinimumStockChange(value: String) {
        val filtered = value.filter { it.isDigit() }
        _formState.update { it.copy(minimumStock = filtered, minimumStockError = null) }
    }

    fun onUnitPriceChange(value: String) {
        // Allow numeric and one decimal dot
        val sanitized = value.filter { (it.isDigit()) || (it == '.') }
        val price = sanitized.toDoubleOrNull() ?: 0.0
        _formState.update { current ->
            current.copy(
                unitPrice = sanitized,
                unitPriceError = null,
                inventoryValue = current.currentStock * price
            )
        }
    }

    fun resetForm() {
        _formState.value = AddProductFormState()
    }

    fun saveProduct(onSuccess: () -> Unit) {
        val current = _formState.value

        var hasError = false
        var nameErr: String? = null
        var skuErr: String? = null
        var locErr: String? = null
        var minStockErr: String? = null
        var priceErr: String? = null

        if (current.name.trim().isEmpty()) {
            nameErr = "El nombre es obligatorio"
            hasError = true
        }

        val trimmedSku = current.sku.trim()
        if (trimmedSku.isEmpty()) {
            skuErr = "El SKU / Código es obligatorio"
            hasError = true
        }

        if (current.location.trim().isEmpty()) {
            locErr = "La ubicación es obligatoria"
            hasError = true
        }

        val minStockInt = current.minimumStock.toIntOrNull()
        if (minStockInt == null || minStockInt < 0) {
            minStockErr = "El stock mínimo debe ser un número entero >= 0"
            hasError = true
        }

        val unitPriceDouble = current.unitPrice.toDoubleOrNull()
        if (unitPriceDouble == null || unitPriceDouble < 0.0) {
            priceErr = "El precio debe ser un número válido >= 0"
            hasError = true
        }

        if (hasError) {
            _formState.update {
                it.copy(
                    nameError = nameErr,
                    skuError = skuErr,
                    locationError = locErr,
                    minimumStockError = minStockErr,
                    unitPriceError = priceErr
                )
            }
            return
        }

        // Validate SKU uniqueness asynchronously
        viewModelScope.launch {
            _formState.update { it.copy(isSaving = true) }

            val isDuplicate = repository.isSkuTaken(trimmedSku)
            if (isDuplicate) {
                _formState.update {
                    it.copy(
                        skuError = "Este SKU ya está registrado",
                        isSaving = false
                    )
                }
                return@launch
            }

            val newProduct = ProductEntity(
                name = current.name.trim(),
                sku = trimmedSku,
                category = current.category,
                location = current.location.trim(),
                currentStock = current.currentStock,
                minimumStock = minStockInt ?: 0,
                unitPrice = unitPriceDouble ?: 0.0
            )

            repository.insertProduct(newProduct)
            resetForm()
            onSuccess()
        }
    }
}

class InventoryViewModelFactory(
    private val repository: ProductRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InventoryViewModel::class.java)) {
            return InventoryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
