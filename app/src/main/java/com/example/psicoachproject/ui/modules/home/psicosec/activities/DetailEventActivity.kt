package com.example.psicoachproject.ui.modules.home.psicosec.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.psicoachproject.R
import com.example.psicoachproject.common.utils.afterTextChanged
import com.example.psicoachproject.common.utils.getColorPackage
import com.example.psicoachproject.common.utils.onRightDrawableClicked
import com.example.psicoachproject.common.utils.showSnackBar
import com.example.psicoachproject.core.Resource
import com.example.psicoachproject.data.remote.source.home.HomeRemoteDataSourceImpl
import com.example.psicoachproject.databinding.ActivityDetailEventBinding
import com.example.psicoachproject.domain.model.Comment
import com.example.psicoachproject.domain.model.MeetingEvent
import com.example.psicoachproject.domain.repository.home.HomeRepositoryImpl
import com.example.psicoachproject.ui.modules.home.client.activities.viewmodel.HomeViewModel
import com.example.psicoachproject.ui.modules.home.client.activities.viewmodel.HomeViewModelFactory
import com.example.psicoachproject.ui.modules.home.psicosec.activities.adapters.CommentAdapter

class DetailEventActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailEventBinding
    private lateinit var meeting     : MeetingEvent

    private val commentAdapter by lazy { CommentAdapter() }

    private val viewModel by viewModels<HomeViewModel>{
        HomeViewModelFactory(
            HomeRepositoryImpl(
                remote = HomeRemoteDataSourceImpl()
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.extras?.let {
            meeting = intent.getParcelableExtra<MeetingEvent>("event") as MeetingEvent
        }

        setupViews()
    }

    private fun setupViews(){
        binding.apply {
            btnPackageType.setCardBackgroundColor(ContextCompat.getColor(this@DetailEventActivity, getColorPackage(meeting.packageName)))
            lblPackageName.text = "${meeting.packageName}"

            lblIssuee.text = "${meeting.issue}"
            lblHour.text = "${meeting.startTime} - ${meeting.endTime}"

            val linkEvent = "<a href='${meeting.link}'> ${meeting.link} </a>"
            lblLink.text    = "${Html.fromHtml(linkEvent, Html.FROM_HTML_MODE_COMPACT)}"

            lblDate.text = "${meeting.dateFormat}"
            lblDesc.text = if (meeting.description.isNotEmpty()) "${meeting.description}" else "-"
            btnBackAppointment.setOnClickListener {
                onBackPressed()
            }
        }
        setUpRecycler()
        setupInputChat()
    }

    private fun setUpRecycler(){
        binding.rvComments.apply {
            adapter = commentAdapter
            layoutManager = LinearLayoutManager( context,
                LinearLayoutManager.VERTICAL,false)
        }
        commentAdapter.setData(meeting.comments.toMutableList())
    }

    private fun setupInputChat(){
        var message = ""
        val clearDrawable = ContextCompat.getDrawable(this, R.drawable.ic_send)
        clearDrawable?.setBounds(0,0, clearDrawable.intrinsicWidth, clearDrawable.intrinsicHeight)

        val validation = afterTextChanged {
            message = binding.etInputChat.text.toString().trim()

            binding.etInputChat.setCompoundDrawables(null, null,
                if (message.isNotEmpty()) clearDrawable else null,
                null)
        }
        binding.etInputChat.addTextChangedListener(validation)

        binding.etInputChat.onRightDrawableClicked { input ->
//            val comment = Comment(0, "", message, currentTask.idTask)
//            viewModel.insertComment(comment)
            sendMessage(message)
            input.text.clear()
            input.setCompoundDrawables(null, null, null, null)
            input.requestFocus()
        }
    }

    private fun sendMessage(message: String){
        viewModel.sendComment(meeting.idAppointment, message).observe(this){
            it?.let { result->
                when(result){
                    is Resource.Loading -> {
                        binding.lyContainer.showSnackBar("Cargando...")
                    }
                    is Resource.Success -> {
                        commentAdapter.addComment(Comment(
                            id = 0,
                            comment = message
                        ))
                    }
                    is Resource.Failure -> {
                        binding.lyContainer.showSnackBar("Ocurrió un error")
                    }
                }
            }
        }
    }

}