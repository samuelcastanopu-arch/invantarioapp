package com.stockos.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stockos.ui.theme.StockBackground
import com.stockos.ui.theme.StockBorder
import com.stockos.ui.theme.StockDivider
import com.stockos.ui.theme.StockError
import com.stockos.ui.theme.StockLime
import com.stockos.ui.theme.StockOnLime
import com.stockos.ui.theme.StockSurface
import com.stockos.ui.theme.StockSurfaceVariant
import com.stockos.ui.theme.StockTextPrimary
import com.stockos.ui.theme.StockTextSecondary
import com.stockos.ui.theme.StockTextTertiary
import com.stockos.viewmodel.InventoryViewModel
import java.text.NumberFormat
import java.util.Locale

@Composable
fun AddProductScreen(
    viewModel: InventoryViewModel,
    onNavigateBack: () -> Unit,
) {
    val formState by viewModel.formState.collectAsState()
    var categoryDropdownExpanded by remember { mutableStateOf(value = false) }

    val currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US).apply {
        maximumFractionDigits = 0
        minimumFractionDigits = 0
    }
    val formattedInventoryValue = currencyFormatter.format(formState.inventoryValue)

    Scaffold(
        containerColor = StockBackground,
        bottomBar = {
            // Big Full-width Neon Lime Button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(StockBackground)
                    .padding(16.dp)
            ) {
                Button(
                    onClick = {
                        viewModel.saveProduct {
                            onNavigateBack()
                        }
                    },
                    enabled = !formState.isSaving,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = StockLime,
                        contentColor = StockOnLime,
                        disabledContainerColor = StockLime.copy(alpha = 0.5f),
                        disabledContentColor = StockOnLime.copy(alpha = 0.5f)
                    ),
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                ) {
                    Text(
                        text = if (formState.isSaving) "GUARDANDO..." else "AGREGAR PRODUCTO",
                        color = StockOnLime,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.sp,
                        fontFamily = FontFamily.SansSerif
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(StockBackground)
                .verticalScroll(rememberScrollState())
        ) {
            // Top Bar with "< VOLVER" button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(StockSurface)
                        .border(1.dp, StockBorder, RoundedCornerShape(4.dp))
                        .clickable { onNavigateBack() }
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = StockTextSecondary,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "VOLVER",
                            color = StockTextSecondary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Main Form Box with Dividers (Matching Figma screenshot 2)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(StockSurface)
                    .border(1.dp, StockBorder, RoundedCornerShape(6.dp))
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // 1. NOMBRE DEL PRODUCTO
                    FormFieldSection(
                        label = "NOMBRE DEL PRODUCTO",
                        error = formState.nameError
                    ) {
                        DarkUnderlineInput(
                            value = formState.name,
                            onValueChange = { viewModel.onNameChange(it) },
                            placeholder = "Ej. Taladro Percutor 800W",
                            capitalization = KeyboardCapitalization.Sentences
                        )
                    }

                    FormDivider()

                    // 2. SKU / CÓDIGO
                    FormFieldSection(
                        label = "SKU / CÓDIGO",
                        error = formState.skuError
                    ) {
                        DarkUnderlineInput(
                            value = formState.sku,
                            onValueChange = { viewModel.onSkuChange(it) },
                            placeholder = "HRW-0001",
                            capitalization = KeyboardCapitalization.Characters
                        )
                    }

                    FormDivider()

                    // 3. CATEGORÍA (Dropdown)
                    FormFieldSection(
                        label = "CATEGORÍA"
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { categoryDropdownExpanded = true }
                                .padding(vertical = 4.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = formState.category,
                                    color = StockTextPrimary,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Medium
                                )
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowDown,
                                    contentDescription = "Seleccionar Categoría",
                                    tint = StockTextSecondary,
                                    modifier = Modifier.size(20.dp)
                                )
                            }

                            DropdownMenu(
                                expanded = categoryDropdownExpanded,
                                onDismissRequest = { categoryDropdownExpanded = false },
                                modifier = Modifier
                                    .background(StockSurfaceVariant)
                                    .border(1.dp, StockBorder, RoundedCornerShape(4.dp))
                            ) {
                                viewModel.formCategories.forEach { categoryOption ->
                                    DropdownMenuItem(
                                        text = {
                                            Text(
                                                text = categoryOption,
                                                color = if (formState.category == categoryOption) StockLime else StockTextPrimary,
                                                fontWeight = if (formState.category == categoryOption) FontWeight.Bold else FontWeight.Normal
                                            )
                                        },
                                        onClick = {
                                            viewModel.onFormCategoryChange(categoryOption)
                                            categoryDropdownExpanded = false
                                        },
                                        colors = MenuDefaults.itemColors(
                                            textColor = StockTextPrimary
                                        )
                                    )
                                }
                            }
                        }
                    }

                    FormDivider()

                    // 4. UBICACIÓN
                    FormFieldSection(
                        label = "UBICACIÓN",
                        error = formState.locationError
                    ) {
                        DarkUnderlineInput(
                            value = formState.location,
                            onValueChange = { viewModel.onLocationChange(it) },
                            placeholder = "Ej. Bodega A-3",
                            capitalization = KeyboardCapitalization.Words
                        )
                    }

                    FormDivider()

                    // 5. STOCK ACTUAL ([-] 0 [+])
                    FormFieldSection(
                        label = "STOCK ACTUAL"
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Minus Button
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(StockSurfaceVariant)
                                    .border(1.dp, StockBorder, RoundedCornerShape(4.dp))
                                    .clickable { viewModel.decrementStock() },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Remove,
                                    contentDescription = "Disminuir",
                                    tint = StockTextPrimary,
                                    modifier = Modifier.size(16.dp)
                                )
                            }

                            // Centered Stock Number with Underline
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(horizontal = 16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = formState.currentStock.toString(),
                                    color = StockTextPrimary,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Black,
                                    textAlign = TextAlign.Center
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(1.dp)
                                        .background(StockBorder)
                                )
                            }

                            // Plus Button
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(StockSurfaceVariant)
                                    .border(1.dp, StockBorder, RoundedCornerShape(4.dp))
                                    .clickable { viewModel.incrementStock() },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Aumentar",
                                    tint = StockTextPrimary,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }

                    FormDivider()

                    // 6. STOCK MÍNIMO
                    FormFieldSection(
                        label = "STOCK MÍNIMO",
                        error = formState.minimumStockError
                    ) {
                        DarkUnderlineInput(
                            value = formState.minimumStock,
                            onValueChange = { viewModel.onMinimumStockChange(it) },
                            placeholder = "5",
                            keyboardType = KeyboardType.Number
                        )
                    }

                    FormDivider()

                    // 7. PRECIO UNITARIO ($)
                    FormFieldSection(
                        label = "PRECIO UNITARIO ($)",
                        error = formState.unitPriceError
                    ) {
                        DarkUnderlineInput(
                            value = formState.unitPrice,
                            onValueChange = { viewModel.onUnitPriceChange(it) },
                            placeholder = "0",
                            keyboardType = KeyboardType.Decimal
                        )
                    }

                    FormDivider()

                    // 8. VALOR EN INVENTARIO (Calculated & Highlighted)
                    FormFieldSection(
                        label = "VALOR EN INVENTARIO"
                    ) {
                        Text(
                            text = formattedInventoryValue,
                            color = StockLime,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Black,
                            fontFamily = FontFamily.SansSerif,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun FormFieldSection(
    label: String,
    error: String? = null,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Text(
            text = label,
            color = StockTextTertiary,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp,
            fontFamily = FontFamily.Monospace
        )
        Spacer(modifier = Modifier.height(6.dp))
        content()
        if (error != null) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = error,
                color = StockError,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun DarkUnderlineInput(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    capitalization: KeyboardCapitalization = KeyboardCapitalization.None
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterStart
        ) {
            if (value.isEmpty()) {
                Text(
                    text = placeholder,
                    color = StockTextTertiary,
                    fontSize = 14.sp,
                    fontFamily = FontFamily.SansSerif
                )
            }
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                singleLine = true,
                textStyle = TextStyle(
                    color = StockTextPrimary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = FontFamily.SansSerif
                ),
                cursorBrush = SolidColor(StockLime),
                keyboardOptions = KeyboardOptions(
                    keyboardType = keyboardType,
                    capitalization = capitalization
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(StockBorder)
        )
    }
}

@Composable
private fun FormDivider() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(StockDivider)
    )
}
