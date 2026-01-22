package com.vitalstraker.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vitalstraker.data.VitalRepository
import com.vitalstraker.data.local.model.VitalItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class VitalViewModel(private val repo: VitalRepository) : ViewModel() {

    val _vitalList = MutableStateFlow<List<VitalItem>>(emptyList())
    val vitalList = _vitalList.asStateFlow()

    fun getVitals() {
        viewModelScope.launch {
            repo.getAllVitals().collect {
                _vitalList.value = it
            }
        }
    }

    fun addVital(vital: VitalItem) {
        viewModelScope.launch {
            repo.insert(vital)
        }
    }
}
