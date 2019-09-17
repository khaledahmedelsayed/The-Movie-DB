package com.example.themoviedb.main

import com.example.themoviedb.Person


class MainPresenter(private val view: Contract.MainView, private val model: Contract.MainModel) {
    private var isLoading = false
    private var currentPage = 1

    internal var resultList = ArrayList<Person?>()

    private fun loadData(isDataFetched: (Boolean) -> Unit) {
        isLoading = true
        if (view.getSearchFlag()) {
            model.fetchJson(currentPage,view.getSearchText()){model.fetchJson(currentPage,null){
                onDataFetched(it)
                isDataFetched(true)
            }
            }
        } else {
            model.fetchJson(currentPage,null){
                onDataFetched(it)
                isDataFetched(true)
            }
        }
    }

    private fun onDataFetched(it: ArrayList<Person>?) {
        isLoading = false
        resultList.addAll(it!!)
        view.notifyItemRangeChangedInRecyclerView(it.size)
    }


    private fun clearData() {
        currentPage = 1
        resultList.clear()
        view.instantiateNewAdapter() //To remove cached and unrecycled itemViews
    }

    private fun removeProgressBar() {
        resultList.remove(null)
        view.notifyItemRemovedFromRecyclerView(resultList.size)
    }

    private fun addProgressBar() {
        //Adapter will check if the the object is null then it will add ProgressViewHolder instead of PopularPeopleViewHolder
        resultList.add(null)
        view.notifyItemRangeInsertedFromRecyclerView(resultList.size, 1)
    }

    fun viewOnCreated() {
        view.clearEditTextFocus()

        loadData {
            isLoading = false
        }
    }

    fun itemViewOnClick(arr: Array<Any>, person: Person) {
        model.saveImage(arr)
        view.navigateToPersonDetailsActivity(person)
    }

    fun recyclerViewOnScrolled(pos: Int, numItems: Int) {
        if (pos >= numItems && !isLoading) //Reached end of screen
        {
            isLoading = true
            currentPage++

            if (resultList.size != 0) //Avoid adding progress bar when the list is empty i.e when using search after clearing data
                addProgressBar()

            loadData {
                isLoading = false
                removeProgressBar()
            }
        }
    }

    fun layoutOnRefreshed() {
        clearData()
        loadData {
            view.removeRefreshingIcon()
        }
    }

    fun searchOnClicked() {
        if (view.getSearchText().trim().isNotEmpty()) {
            view.setSearchFlag(true)
            view.hideKeyBoard()
            view.clearEditTextFocus()

            clearData()
            loadData {
                isLoading = false
            }

        }
    }

    fun finishSearchOnClicked() {
        view.clearSearchText()
        view.clearEditTextFocus()
        view.hideKeyBoard()
        if (view.getSearchFlag()) {
            view.setSearchFlag(false)
            clearData()
            loadData {
                isLoading = false
            }
        }
    }

}







