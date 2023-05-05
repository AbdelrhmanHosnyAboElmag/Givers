package com.example.givers.app.exts

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

abstract class BaseItemAdapter<T> : RecyclerView.Adapter<MyViewHolder>() {
    /**
     * Adapter items list
     * @return List items
     */
    abstract fun adapterItems(): ArrayList<T>

    /**
     * Adapter list has more data or not
     * @return True if adapter can load more data
     */
    abstract fun hasMore(): Boolean

    /**
     * Add new data to adapter
     */
    open fun addItems(items: List<T>) {
        val myList = adapterItems()
        var count = myList.size
        // Remove loading indicator dummy item
        if (count > 0 && hasMore()) {
            count--
            myList.removeAt(count)
            notifyItemRemoved(count)
        }
        // Insert extra data
        myList.addAll(items)
        notifyItemRangeInserted(count, items.size)
    }

    /**
     * Clear adapter items
     */
    open fun clearList() {
        val myList = adapterItems()
        val count = myList.size
        myList.clear()
        notifyItemRangeRemoved(0, count)
    }

    /**
     * Clear adapter items
     */
    open fun replaceList(items: List<T>) {
        val myList = adapterItems()
        val count = myList.size
        var x = 0
        Log.i("TAG", "replaceList:ddddddddddd1  "+myList.size)
        for (i in myList) {
            myList.set(x, items.get(x))
            notifyItemChanged(x)
            x++
        }
        Log.i("TAG", "replaceList:ddddddddddd2  "+myList.size)

        // notifyItemRangeChanged(0, count)

    }
//    private open fun replaceOldListWithNewList() {
//        // clear old list
//        data.clear()
//
//        // add new list
//        val newList: ArrayList<String> = ArrayList()
//        newList.add("Lion")
//        newList.add("Wolf")
//        newList.add("Bear")
//        data.addAll(newList)
//
//        // notify adapter
//        adapter.notifyDataSetChanged()
//    }

    /**
     * Add item to adapter at given index
     * @param item new item to be inserted
     * @param index insertion index
     */
    open fun addItem(item: T, index: Int) {
        adapterItems().add(index, item)
        notifyItemInserted(index)
    }

    /**
     * Remove item at given index
     * @param index removal index
     */
    open fun removeItem(index: Int) {
        adapterItems().removeAt(index)
        notifyItemRemoved(index)
    }

    /**
     * Move item at index [from] to index [to]
     * @param from Item initial position
     * @param to Item final position
     */
    open fun moveItem(from: Int, to: Int) {
        val myList = adapterItems()
        val temp = myList[from]
        myList[from] = myList[to]
        myList[to] = temp
        notifyItemMoved(from, to)
    }

    /**
     * Update [item] at index [index]
     * @param item new item
     * @param index index to put new item
     */
    open fun updateItem(item: T, index: Int) {
        val myList = adapterItems()
        myList[index] = item
        notifyItemChanged(index)
    }

    /**
     * Get [item] at index [index]
     * @param index index of item to return
     */
    open fun getItemAt(index: Int): T {
        return adapterItems()[index]
    }
}