package com.example.convidados.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.convidados.model.GuestModel
import com.example.convidados.repository.GuestRepository

class GuestFormViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = GuestRepository.getInstancie(application)

    private val guestModel = MutableLiveData<GuestModel>()
    var guest: LiveData<GuestModel> = guestModel

    private val _saveGuest = MutableLiveData<String>()
    var saveGuest: LiveData<String> = _saveGuest

    fun save(guest: GuestModel) {

        if (guest.id == 0) {
            if (repository.insert(guest)) {
                _saveGuest.value = "Usuario ${guest.name} inserido!!"
            } else {
                _saveGuest.value = "Falha!!"
            }
        } else {
            if (repository.update(guest)) {
                _saveGuest.value = "Usuario ${guest.name} atualizado!!"
            } else {
                _saveGuest.value = "Falha!!"
            }
        }
    }


    fun get(id: Int) {
        guestModel.value = repository.get(id)
    }


}