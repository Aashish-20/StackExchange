package com.example.qa

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.qa.adapter.AnswersAdapter
import com.example.qa.model.QuesAndAns
import kotlinx.android.synthetic.main.activity_search.*
import java.lang.Exception

class SearchActivity : AppCompatActivity() {

    val answersList = arrayListOf<QuesAndAns>()
    lateinit var answersAdapter:AnswersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        getAnswers()
    }

    private fun getAnswers(){

        val queue = Volley.newRequestQueue(this)
        val url = "https://api.stackexchange.com/2.3/questions/123/answers?site=stackoverflow&filter=!azbR7v4hiSjQQY"

        val jsonObjectRequest = @SuppressLint("SetTextI18n")
        object : JsonObjectRequest(Request.Method.GET, url, null,

            Response.Listener {
                try {

                    val items = it.getJSONArray("items")
                    val obj = items.getJSONObject(0)
                    val ques = obj.getString("title")
                    txtQuestion.text ="Q. $ques"

                    for (i in 0 until items.length()){

                        val itemsObject = items.getJSONObject(i)

                        val answer = itemsObject.getString("body_markdown")

                        val answerDetails = QuesAndAns(answer)

                        answersList.add(answerDetails)

                        answersAdapter = AnswersAdapter(this,answersList)
                        recyclerView.adapter = answersAdapter
                        recyclerView.layoutManager = LinearLayoutManager(this)

                    }

                }catch (e:Exception){
                    Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
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