package com.example.qa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.qa.adapter.AnswersAdapter
import com.example.qa.model.Answer
import kotlinx.android.synthetic.main.activity_answer.*
import kotlinx.android.synthetic.main.activity_answer.recyclerView
import kotlinx.android.synthetic.main.activity_answer.txtQuestion
import kotlinx.android.synthetic.main.activity_search.*
import java.lang.Exception

class AnswerActivity : AppCompatActivity() {
    private var question_id:String = ""
    private var is_answered:Boolean = false
    private var question:String = ""

    val answersList = arrayListOf<Answer>()
    lateinit var answersAdapter:AnswersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answer)

        if (intent!=null){


            is_answered = intent.getBooleanExtra("is_answered",false)

            if (!is_answered){

                llNothingFound.visibility = View.VISIBLE
                Toast.makeText(this, "NO ANSWERS FOUND!!!", Toast.LENGTH_LONG).show()

            }else{
                llNothingFound.visibility = View.GONE
                question_id = intent.getStringExtra("question_id").toString()
                question = intent.getStringExtra("question").toString()

                txtQuestion.text = question

                getAnswers(question_id)
            }
        }
    }

    private fun getAnswers(questionId: String) {

        val queue = Volley.newRequestQueue(this)
        val url = "https://api.stackexchange.com/2.3/questions/${questionId}/answers?site=stackoverflow&filter=!azbR7v4hiSjQQY"

        val jsonObjectRequest = object : JsonObjectRequest(Request.Method.GET, url, null,
        Response.Listener {
            try {

                val items = it.getJSONArray("items")

                for (i in 0 until items.length()){

                    val itemsObject = items.getJSONObject(i)

                    val answer = itemsObject.getString("body_markdown")

                    val answerDetails = Answer(answer)

                    answersList.add(answerDetails)

                    answersAdapter = AnswersAdapter(this,answersList)
                    recyclerView.adapter = answersAdapter
                    recyclerView.layoutManager = LinearLayoutManager(this)

                }

                }
            catch (e:Exception){
                Toast.makeText(this, "Exception:${e.message}", Toast.LENGTH_SHORT).show()
            }
        },
        Response.ErrorListener {
            Toast.makeText(this, "Error:${it.message}", Toast.LENGTH_SHORT).show()
        })
        {

        }
        queue.add(jsonObjectRequest)
    }
}