package com.opensystem.smallwork.ui.fragments.main.menu.profile.sub

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.opensystem.smallwork.databinding.FragmentRatingBinding
import com.opensystem.smallwork.models.Rating
import com.opensystem.smallwork.ui.adapters.RatingAdapter

/**
 * create an instance of this fragment.
 */
class RatingFrag : Fragment() {

    private var _binding: FragmentRatingBinding? = null
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
        _binding = FragmentRatingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val ratingList = ArrayList<Rating>()

        viewManager = LinearLayoutManager(context)
        viewAdapter = RatingAdapter(ratingList)

        _binding!!.ratingRecycler.apply {
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