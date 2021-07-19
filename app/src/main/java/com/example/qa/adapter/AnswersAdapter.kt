package com.example.qa.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.qa.R
import com.example.qa.model.QuesAndAns

class AnswersAdapter(private val context: Context, private val answerList: ArrayList<QuesAndAns>):
    RecyclerView.Adapter<AnswersAdapter.AnswersViewHolder>() {

    class AnswersViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        val txtAnswer:TextView = itemView.findViewById(R.id.txtAnswer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnswersViewHolder {
       return AnswersViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.question_answer_row,parent,false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: AnswersViewHolder, position: Int) {
        val ansList = answerList[position]
        holder.txtAnswer.text = "Ans${position+1} " + ansList.answer
    }

    override fun getItemCount(): Int {
       return answerList.size
    }
}