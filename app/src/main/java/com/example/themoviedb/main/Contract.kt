package com.example.themoviedb.main

import com.example.themoviedb.pojos.Person

interface Contract {
    interface MainModel {
        fun fetchJson(currentPage: Int, searchedWord: String?, fetchedData: (String?) -> Unit)
        fun fetchImage(path: String, fetchedImage: (Any?) -> Unit)
        fun saveImage(arr: Array<Any>)
    }

    interface MainView {
        fun setSearchFlag(b: Boolean)
        fun getSearchFlag(): Boolean
        fun getSearchText(): String
        fun clearEditTextFocus()
        fun clearSearchText()
        fun hideKeyBoard()
        fun removeRefreshingIcon()
        fun instantiateNewAdapter()
        fun notifyItemRemovedFromRecyclerView(index: Int)
        fun notifyItemRangeInsertedFromRecyclerView(start: Int, itemCount: Int)
        fun notifyItemRangeChangedInRecyclerView(itemCount: Int)
        fun navigateToPersonDetailsActivity(person: Person)
    }
}