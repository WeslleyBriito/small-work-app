package com.opensystem.smallwork.ui.adapters

import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.opensystem.smallwork.R
import com.opensystem.smallwork.databinding.AdapterContractBinding
import com.opensystem.smallwork.getColorCompat
import com.opensystem.smallwork.models.Contract
import com.opensystem.smallwork.utils.AppConstants.RequestStatus
import com.opensystem.smallwork.utils.Preferences
import com.squareup.picasso.Picasso

class ContractAdapter(
   private val contractList: List<Contract>,
   private val itemClickListener: (Contract) -> Unit,
   private val acceptClickListener: (Boolean, Contract) -> Unit
) : RecyclerView.Adapter<ContractAdapter.ContractHolder>() {

   class ContractHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
      val binding = AdapterContractBinding.bind(itemView)
   }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContractHolder {
      return ContractHolder(
         LayoutInflater.from(parent.context).inflate(R.layout.adapter_contract, parent, false)
      )
   }

   override fun onBindViewHolder(holder: ContractHolder, position: Int) {
      val contract = contractList[position]
      val context = holder.itemView.context
      val pref = Preferences(context)

      with(holder.binding){
         if (contract.user.avatar != null && pref.proActivated()) {
            Picasso.get().load(contract.user.avatar).into(imgAvatar)
         }else{
            if (contract.worker.avatar != null){
               Picasso.get().load(contract.worker.avatar).into(imgAvatar)
            }
         }
         txtName.text = if (pref.proActivated()) contract.user.name else contract.worker.name
         txtDaily.text = contract.daily
         txtEmail.text = if (pref.proActivated()) contract.user.email else contract.worker.email
         txtDate.text = DateUtils.getRelativeTimeSpanString(contract.requestDate)
         txtStatus.apply{ when(contract.status){
            RequestStatus.PENDING -> {
               text = context.getString(R.string.pending)
               setTextColor(getColorCompat(R.color.orange))
            }
            RequestStatus.ACCEPTED -> {
               text = context.getString(R.string.accepted)
               setTextColor(getColorCompat(R.color.luminous_blue))
            }
            RequestStatus.DECLINED -> {
               text = context.getString(R.string.declined)
               setTextColor(getColorCompat(R.color.red))
            }
            RequestStatus.CANCELED -> {
               text = context.getString(R.string.canceled)
               setTextColor(getColorCompat(R.color.red))
            }
            RequestStatus.COMPLETED -> {
               text = context.getString(R.string.completed)
               setTextColor(getColorCompat(R.color.green))
            }
            else -> {}
         }}
         llListContainer.setOnClickListener {
            itemClickListener(contract)
         }
         llCancel.setOnClickListener { acceptClickListener(false, contract) }
         txtAccept.setOnClickListener { acceptClickListener(true, contract) }
         llActionContainer.isVisible = contract.status == RequestStatus.PENDING
         if (!Preferences(context).proActivated()){
            llActionContainer.isVisible = contract.status == RequestStatus.ACCEPTED || contract.status == RequestStatus.PENDING
            txtAccept.text = context.getString(R.string.finish)
            txtDecline.text = context.getString(R.string.cancel)
            txtAccept.isVisible = contract.status != RequestStatus.PENDING
         }
      }
   }

   override fun getItemCount(): Int {
      return contractList.size
   }

}