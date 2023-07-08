package com.example.givers.app.Adapter

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.givers.R
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerFrameLayout

class SkeletonList(context: Context, attrs: AttributeSet) : ShimmerFrameLayout(context, attrs) {
    /**
     * Child items count
     */
    private var mChildCount: Int
    /**
     * Child layout resource id
     */
    private var mChildResId: Int
    /**
     * Skeleton recycler view reference
     */
    private var mSkeletonRecycler: RecyclerView
    init {
        val arr = context.theme.obtainStyledAttributes(attrs, R.styleable.SkeletonList, 0, 0)
        mChildResId = arr.getResourceId(R.styleable.SkeletonList_skeletonLayout, 0)
        mChildCount = arr.getResourceId(R.styleable.SkeletonList_skeletonCount, 15)
        mSkeletonRecycler = initRecycler()
        initShimmer()
        addView(mSkeletonRecycler)
        arr.recycle()
    }
    /**
     * Init shimmer properties
     */
    private fun initShimmer() {
        val builder = Shimmer.AlphaHighlightBuilder()
        val duration = resources.getInteger(R.integer.shimmer_duration)
        builder.setDuration(duration.toLong())
        builder.setAutoStart(true)
        builder.setRepeatMode(ValueAnimator.RESTART)
        setShimmer(builder.build())
    }
    /**
     * Initialize skeleton items RecyclerView
     */
    private fun initRecycler(): RecyclerView {
        return RecyclerView(context).apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
            layoutManager = LinearLayoutManager(context)
            adapter = SkeletonAdapter(mChildResId, mChildCount)
            isNestedScrollingEnabled = false
            setHasFixedSize(true)
        }
    }

    /**
     * Reinitialize view with given parameters
     */
    fun reInitView(@LayoutRes childResId: Int = mChildResId, childCount: Int = mChildCount) {
        mSkeletonRecycler.adapter = SkeletonAdapter(childResId, childCount)
        //    mSkeletonRecycler.layoutManager

    }


    /**
     * Skeleton adapter
     */
    private class SkeletonAdapter(
        @LayoutRes private val childResId: Int,
        private val childCount: Int
    ) :
        RecyclerView.Adapter<SkeletonAdapter.SkeletonViewHolder>() {

        class SkeletonViewHolder(view: View) : RecyclerView.ViewHolder(view)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SkeletonViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(childResId, parent, false)
            return SkeletonViewHolder(view)
        }

        override fun onBindViewHolder(holder: SkeletonViewHolder, position: Int) {
        }

        override fun getItemCount(): Int = childCount
    }
}