package com.opensystem.smallwork.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.opensystem.smallwork.R
import com.opensystem.smallwork.databinding.AdapterWorkerBinding
import com.opensystem.smallwork.models.Worker
import com.squareup.picasso.Picasso

class WorkerAdapter(
   private val workerList: List<Worker>,
   private val onClickListener: (Worker) -> Unit
) : RecyclerView.Adapter<WorkerAdapter.WorkerHolder>() {

   class WorkerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
      val binding = AdapterWorkerBinding.bind(itemView)
   }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkerHolder {
      return WorkerHolder(
         LayoutInflater.from(parent.context).inflate(R.layout.adapter_worker, parent, false)
      )
   }

   override fun onBindViewHolder(holder: WorkerHolder, position: Int) {
      val worker = workerList[position]
      val context = holder.itemView.context
      with(holder.binding) {
         nameTxt.text = worker.name
         professionTxt.text = worker.profession
         localeTxt.text = if (worker.addressName == null) context.getString(R.string.uninformed) else worker.addressName
         if (worker.avatar != null) Picasso.get().load(worker.avatar).into(avatarImg)
         root.setOnClickListener {
            onClickListener(worker)
         }
         priceTxt.text = worker.dailyValue
         ratingBar.rating = if (worker.rating != null) worker.rating!! else 5f
      }
   }

   override fun getItemCount(): Int {
      return workerList.size
   }

}