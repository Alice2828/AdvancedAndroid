package com.example.mvvm.view.adapter.viewholder

import androidx.databinding.ViewDataBinding
import com.example.mvvm.BR
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm.data.model.Item
import com.example.mvvm.viewmodel.RepoListViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.view_repo_list_item.view.*

class RepoListViewHolder constructor(
    private val dataBinding: ViewDataBinding,
    private val repoListViewModel: RepoListViewModel
) : RecyclerView.ViewHolder(dataBinding.root) {

    val avatarImage = itemView.item_avatar
    fun setup(itemData: Item) {
        dataBinding.setVariable(BR.itemData, itemData)
        dataBinding.executePendingBindings()

        Picasso.get().load(itemData.owner.avatar_url).into(avatarImage);

//        itemView.onClick {
//            val bundle = bundleOf("url" to itemData.html_url)
//            itemView.findNavController()
//                .navigate(R.id.action_repoListFragment_to_repoDetailFragment, bundle)
//        }
    }
}