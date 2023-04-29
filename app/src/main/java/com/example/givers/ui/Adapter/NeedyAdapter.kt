package com.example.givers.ui.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.givers.databinding.NeedyItemsBinding
import com.example.givers.ui.Model.NeedyModel
import com.example.givers.ui.exts.BaseItemAdapter
import com.example.givers.ui.exts.MyViewHolder

class NeedyAdapter(
    listNeedyModel: MutableList<NeedyModel> = mutableListOf<NeedyModel>(),
    private val onClickListener: (NeedyModel) -> Unit
) :
    BaseItemAdapter<NeedyModel>() {
    var list = listNeedyModel

    inner class NeedyViewHolder(val binding: NeedyItemsBinding) :
        MyViewHolder(binding.root) {

        /**
         * Set item data
         */
        fun setData(needyModel: NeedyModel) =
            binding.apply {
                val context = itemView.context
                Glide.with(context)
                    .load(needyModel.imageData)
                    .circleCrop()
                    .into(imgNeedy)
                tvNeedy.text = needyModel.userData
            }
    }

    override fun adapterItems(): java.util.ArrayList<NeedyModel> = ArrayList(list)
    override fun hasMore(): Boolean {
        return false
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NeedyViewHolder {
        val binding =
            NeedyItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NeedyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val itemHolder = holder as NeedyViewHolder

        holder.setData(list[position])
        holder.itemView.setOnClickListener {
            //setDistance(dis)
            onClickListener(list[position])
        }
    }


    override fun getItemCount(): Int = list.size


}
