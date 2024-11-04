package com.example.convidados.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.convidados.constants.DataBaseConstants
import com.example.convidados.databinding.FragmentPresentBinding
import com.example.convidados.view.adapter.GuestsAdapter
import com.example.convidados.view.listener.OnGuestListener
import com.example.convidados.viewmodel.GuestsViewModel

class PresentFragment : Fragment() {

    private var _binding: FragmentPresentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: GuestsViewModel
    private val adapter = GuestsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        b: Bundle?
    ): View {

        viewModel = ViewModelProvider(this).get(GuestsViewModel::class.java)
        _binding = FragmentPresentBinding.inflate(inflater, container, false)

        binding.recyclerGuests.layoutManager = LinearLayoutManager(context) // setando o linear como layout
        binding.recyclerGuests.adapter = adapter // Setando o adapter para o RecyclerView

        val listener = object : OnGuestListener { // Class anonima

            override fun onClick(id: Int) {
                val intent = Intent(context, GuestFormActivity::class.java)

                val bundle = Bundle()
                bundle.putInt(DataBaseConstants.GUESt.ID, id)

                intent.putExtras(bundle) // Adicionando dados extras à intent para a próxima pagina ou atividade
                startActivity(intent)
            }

            override fun onDelete(id: Int) {
                viewModel.delete(id)
                viewModel.getPresence()
            }

        }

        observe()

        adapter.attachListener(listener)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getPresence()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observe() {
        viewModel.guests.observe(viewLifecycleOwner) {
            adapter.updateGuests(it)
        }
    }
}