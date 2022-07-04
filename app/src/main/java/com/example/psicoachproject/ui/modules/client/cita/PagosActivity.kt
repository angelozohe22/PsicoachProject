package com.example.psicoachproject.ui.modules.client.cita

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import com.example.psicoachproject.databinding.ActivityPagosBinding

class PagosActivity : AppCompatActivity() {

    private lateinit var binding : ActivityPagosBinding
    val REQUEST_CODE_PICK = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPagosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSendFoto.setOnClickListener {
            takePick()
        }

        binding.btnExit.setOnClickListener {
            finish()
        }

    }

    private fun takePick(){
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePicture->
            takePicture.resolveActivity(packageManager)?.also {
                startActivityForResult(takePicture, REQUEST_CODE_PICK)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_PICK && resultCode == RESULT_OK){
            data?.extras?.let {
                val imageBitmap = it.get("data") as Bitmap
                Toast.makeText(this, "Voucher enviado", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

    }


}