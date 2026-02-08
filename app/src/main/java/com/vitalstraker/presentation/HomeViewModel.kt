package com.vitalstraker.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vitalstraker.data.VitalRepository
import com.vitalstraker.data.local.model.VitalItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class VitalViewModel(private val repo: VitalRepository) : ViewModel() {

    private val _vitalList = MutableStateFlow<List<VitalItem>>(emptyList())
    val vitalList = _vitalList.asStateFlow()


    private val _permissionChecked = MutableStateFlow(false)
    private val _isPermissionGranted = MutableStateFlow(false)
    private val _hasRequestedPermission = MutableStateFlow(false)

    val shouldRequestPermission =
        combine(
            _permissionChecked,
            _isPermissionGranted,
            _hasRequestedPermission
        ) { checked, granted, requested ->
            checked && !granted && !requested
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            false
        )

    fun onPermissionChecked(granted: Boolean) {
        _permissionChecked.value = true
        _isPermissionGranted.value = granted
    }

    fun onPermissionRequested() {
        _hasRequestedPermission.value = true
    }


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
