package com.example.themoviedb.ui.persondetails

import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.themoviedb.R
import com.example.themoviedb.base.BaseActivity
import com.example.themoviedb.ui.image.ImageActivity
import com.example.themoviedb.utils.models.KnownFor
import kotlinx.android.synthetic.main.activity_person_details.*

class PersonDetailsActivity : BaseActivity<PersonDetailsPresenter>(),
    Contract.PersonDetailsView {
    private lateinit var profileId: String

    override val presenter = PersonDetailsPresenter(this, PersonDetailsRepository())

    override fun getLayoutResourceId(): Int {
        return R.layout.activity_person_details
    }

    override fun onViewReady(savedInstanceState: Bundle?) {

        val mRecyclerView = this.rv_pictures
        mRecyclerView.apply {
            layoutManager =
                GridLayoutManager(this@PersonDetailsActivity, 3)
            adapter = PopularPersonAdapter(
                presenter,
                presenter.resultList
            )
            setItemViewCacheSize(50)

            //To remove spaces in grid view
            mRecyclerView.addItemDecoration(
                object : RecyclerView.ItemDecoration() {
                    val spaceInPixels = 10
                    override fun getItemOffsets(
                        outRect: Rect, view: View,
                        parent: RecyclerView, state: RecyclerView.State
                    ) {
                        outRect.left = spaceInPixels
                        outRect.right = spaceInPixels
                        outRect.bottom = spaceInPixels

                        if (parent.getChildLayoutPosition(view) == 0) {
                            outRect.top = spaceInPixels
                        } else {
                            outRect.top = 0
                        }
                    }
                }
            )
        }
    }

    override fun setUiFromIntent() {
        profileId = intent.getStringExtra("profile_id")
        val personName = intent.getStringExtra("person_name")
        val popularity = intent.getStringExtra("popularity")
        val knownFor = intent.getSerializableExtra("known_for") as ArrayList<KnownFor>?
        val knownForDepartment = intent.getStringExtra("known_for_department")

        val photoPath = openFileInput("profile_picture")
        val bitmap = BitmapFactory.decodeStream(photoPath)

        iv_profileImage.setImageBitmap(bitmap)
        tv_name.text = personName

        val knownForArrayList: ArrayList<String> = ArrayList()
        if (knownFor != null) {
            for (i in 0 until knownFor.size)
                knownForArrayList.add(knownFor[i].originalTitle)
        } else knownForArrayList.add("No movies found")

        tv_knownFor.text =
            StringBuilder("$personName is known for $knownForDepartment in $knownForArrayList with popularity score of $popularity")
    }

    override fun getProfileId(): String {
        return profileId
    }

    override fun navigateToImageActivity() {
        val intent = Intent(applicationContext, ImageActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        applicationContext.startActivity(intent)
    }

    override fun notifyItemRangeInsertedFromRecyclerView(start: Int, itemCount: Int) {
        rv_pictures.adapter?.notifyItemRangeInserted(start, itemCount)
    }

    override fun notifyItemRemovedFromRecyclerView(index: Int) {
        rv_pictures.adapter?.notifyItemRemoved(index)
    }

    override fun notifyItemRangeChangedInRecyclerView(itemCount: Int) {
        rv_pictures.adapter?.notifyItemRangeChanged(
            rv_pictures.adapter!!.itemCount,
            itemCount
        )
    }

    override fun showPersonInfo() {
        tv_knownFor.visibility = View.VISIBLE
    }

}