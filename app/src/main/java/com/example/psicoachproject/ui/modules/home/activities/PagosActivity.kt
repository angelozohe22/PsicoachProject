package com.example.psicoachproject.ui.modules.home.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.psicoachproject.R
import com.example.psicoachproject.databinding.ActivityDatosBinding
import com.example.psicoachproject.databinding.ActivityPagosBinding

class PagosActivity : AppCompatActivity() {


    private lateinit var binding            : ActivityPagosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPagosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSendFoto.setOnClickListener {
            Toast.makeText(this, "Muy pronto!", Toast.LENGTH_SHORT).show()
        }
    }
}