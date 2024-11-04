package com.example.convidados.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.convidados.R
import com.example.convidados.constants.DataBaseConstants
import com.example.convidados.databinding.ActivityGuestFormBinding
import com.example.convidados.model.GuestModel
import com.example.convidados.viewmodel.GuestFormViewModel

class GuestFormActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityGuestFormBinding
    private lateinit var viewModel: GuestFormViewModel
    private var guestId = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityGuestFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonSave.setOnClickListener(this)
        binding.groupRadio.check(R.id.radio_present)
        viewModel = ViewModelProvider(this).get(GuestFormViewModel::class.java)

        observer()
        loadData()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun observer() {
        viewModel.guest.observe(this, Observer {
            binding.editName.setText(it.name)
            val radio = it.presence

            when (radio) {
                true -> binding.radioPresent.isChecked = true
                false -> binding.radioAbsent.isChecked = true
            }
        })
        viewModel.saveGuest.observe(this, Observer {
            if(it != "") {
                Toast.makeText(this,it,Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this,it,Toast.LENGTH_SHORT).show()
            }

        })
    }

    override fun onClick(v: View) {

        if (v.id == R.id.button_save) {

            val name = binding.editName.text.toString()
            val presence = binding.radioPresent.isChecked

            val model = GuestModel().apply {
                this.id = guestId
                this.name = name
                this.presence = presence
            }
            viewModel.save(model)

        }
    }

    private fun loadData() {
        val bundle = intent.extras
        if (bundle != null) {
            guestId = bundle.getInt(DataBaseConstants.GUESt.ID)
            viewModel.get(guestId)
        }
    }
}