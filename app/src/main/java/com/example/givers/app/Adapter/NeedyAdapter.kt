package com.example.givers.app.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.givers.databinding.NeedyItemsBinding
import com.example.givers.app.Model.DonationModel
import com.example.givers.app.exts.BaseItemAdapter
import com.example.givers.app.exts.MyViewHolder

class NeedyAdapter(
    listDonationModels: MutableList<DonationModel> = mutableListOf<DonationModel>(),
    private val onClickListener: (DonationModel) -> Unit
) :
    BaseItemAdapter<DonationModel>() {
    var list = listDonationModels

    inner class NeedyViewHolder(val binding: NeedyItemsBinding) :
        MyViewHolder(binding.root) {

        /**
         * Set item data
         */
        fun setData(donationModel: DonationModel) =
            binding.apply {
                if (!donationModel.isTake) {
                    val context = itemView.context
                    Glide.with(context)
                        .load(donationModel.itemImage)
                        .circleCrop()
                        .into(imgNeedy)
                    tvNeedy.text = donationModel.itemDescription
                }
            }
    }

    override fun adapterItems(): java.util.ArrayList<DonationModel> = ArrayList(list)
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
        holder.binding.btnSelect.setOnClickListener {
            //setDistance(dis)
            onClickListener(list[position])
        }
    }


    override fun getItemCount(): Int = list.size


}
