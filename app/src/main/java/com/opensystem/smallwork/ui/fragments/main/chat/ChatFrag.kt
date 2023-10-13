package com.opensystem.smallwork.ui.fragments.main.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.opensystem.smallwork.databinding.FragmentChatBinding
import com.opensystem.smallwork.models.Chat
import com.opensystem.smallwork.ui.adapters.ChatAdapter

class ChatFrag : Fragment(){

   private var _binding: FragmentChatBinding? = null
   // This property is only valid between onCreateView and
   // onDestroyView.
   private val binding get() = _binding!!

   private lateinit var viewAdapter: RecyclerView.Adapter<*>
   private lateinit var viewManager: RecyclerView.LayoutManager

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View {
      // Inflate the layout for this fragment
      _binding = FragmentChatBinding.inflate(inflater, container, false)
      return binding.root
   }

   private fun startComponents() = with(binding) {
      val chatList = ArrayList<Chat>()

      viewManager = LinearLayoutManager(context)
      viewAdapter = ChatAdapter(chatList){}

      recycler.apply {
         setHasFixedSize(true)
         layoutManager = viewManager
         adapter = viewAdapter
      }
   }

   override fun onDestroyView() {
      super.onDestroyView()
      _binding = null
   }
}