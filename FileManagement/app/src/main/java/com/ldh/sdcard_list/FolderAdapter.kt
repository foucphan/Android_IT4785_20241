package com.ldh.sdcard_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FoldertAdapter(
    private val folders: ArrayList<Folder>,
    private val folderClickListener: FolderClickListener?
) : RecyclerView.Adapter<FoldertAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.layout_folder_item, parent, false)
        return ItemViewHolder(view, folderClickListener)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val folder = folders[position]
        holder.textView.text = folder.name
        // Nếu cần set ảnh, bạn có thể gọi:
        // holder.imageView.setImageResource(folder.avatar)
    }

    override fun getItemCount(): Int {
        return folders.size
    }

    class ItemViewHolder(
        itemView: View,
        private val folderClickListener: FolderClickListener?
    ) : RecyclerView.ViewHolder(itemView) {

        val imageView: ImageView = itemView.findViewById(R.id.item_avatar)
        val textView: TextView = itemView.findViewById(R.id.item_name)

        init {
            itemView.setOnClickListener {
                folderClickListener?.onItemClick(adapterPosition)
            }
        }
    }
}
