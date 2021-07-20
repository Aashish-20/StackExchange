package com.example.qa.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.qa.AnswerActivity
import com.example.qa.R
import com.example.qa.model.Question

class QuestionsAdapter(private val context: Context, private val questionList:ArrayList<Question>) :
    RecyclerView.Adapter<QuestionsAdapter.QuestionViewHolder>() {

    class QuestionViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val txtQuestion:TextView = itemView.findViewById(R.id.txtAnswer)
        val clQuesAndAnsRow: ConstraintLayout = itemView.findViewById(R.id.clQuesAndAnsRow)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.questions_row,parent,false)

        return QuestionViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val questions = questionList[position]
        holder.txtQuestion.text = "Q. ${questions.question}"

        holder.clQuesAndAnsRow.setOnClickListener {
            val intent = Intent(context, AnswerActivity::class.java)
            intent.putExtra("question",questions.question)
            intent.putExtra("question_id",questions.questionId)
            intent.putExtra("is_answered",questions.is_answered)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return questionList.size
    }
}