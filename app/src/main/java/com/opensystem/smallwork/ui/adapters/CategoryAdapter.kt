package com.opensystem.smallwork.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.opensystem.smallwork.R
import com.opensystem.smallwork.databinding.AdapterCategoryBinding
import com.opensystem.smallwork.models.Category
import com.opensystem.smallwork.utils.appCategories
import com.squareup.picasso.Callback
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import java.lang.Exception

class CategoryAdapter(
   private val onClickListener:(Category) -> Unit
): RecyclerView.Adapter<CategoryAdapter.CategoryHolder>() {

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
      return CategoryHolder(
         LayoutInflater.from(parent.context).inflate(R.layout.adapter_category, parent, false)
      )
   }

   override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
      val category = appCategories[position]

      with(holder.binding){
         nameTxt.text = category.name

         Picasso.get().load(category.icon)
            .networkPolicy(NetworkPolicy.OFFLINE)
            .into(image, object : Callback {
               override fun onSuccess() {}
               override fun onError(e: Exception?) {
                  Picasso.get().load(category.icon).into(holder.binding.image)
               }
            })

         root.setOnClickListener { onClickListener(category) }
      }
   }

   override fun getItemCount(): Int {
      return appCategories.size
   }

   class CategoryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
      val binding = AdapterCategoryBinding.bind(itemView)
   }

}