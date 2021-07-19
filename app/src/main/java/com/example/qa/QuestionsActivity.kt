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
import com.example.qa.adapter.QuestionsAdapter
import com.example.qa.model.Question
import kotlinx.android.synthetic.main.activity_questions.*
import java.lang.Exception

class QuestionsActivity : AppCompatActivity() {

    private var question:String = ""
    lateinit var questionAdapter: QuestionsAdapter
    val questionsList = arrayListOf<Question>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questions)

        if (intent!=null){
            question = intent.getStringExtra("question").toString()

            getQuestions(question)

        }
    }

    private fun getQuestions(question: String) {
        val queue = Volley.newRequestQueue(this)
        val url = "https://api.stackexchange.com/2.3/search?intitle=${question}&site=stackoverflow"

        val jsonObjectRequest = object :JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener {
                try {
                    val items = it.getJSONArray("items")

                    val has_more = it.getBoolean("has_more")

                    if (has_more){
                        animation.visibility = View.GONE
                        for (i in 0 until items.length()){

                            val objects = items.getJSONObject(i)

                            //for question title
                            val title = objects.getString("title")

                            //for question id
                            val question_id = objects.getInt("question_id")

                            val questionDetails = Question(title,question_id.toString())
                            questionsList.add(questionDetails)

                            questionAdapter = QuestionsAdapter(this,questionsList)
                            recyclerView.adapter = questionAdapter
                            recyclerView.layoutManager = LinearLayoutManager(this)
                        }
                    }else{
                        animation.visibility = View.VISIBLE
                        Toast.makeText(this, "found nothing!!!", Toast.LENGTH_SHORT).show()
                    }


                }catch (e:Exception){
                    Toast.makeText(this, "Exception:${e.message}", Toast.LENGTH_SHORT).show()
                }

            },
            Response.ErrorListener {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()

            })
        {
        }
        queue.add(jsonObjectRequest)
    }
}