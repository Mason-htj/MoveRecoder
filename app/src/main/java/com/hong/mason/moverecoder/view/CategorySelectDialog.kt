package com.hong.mason.moverecoder.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.arch.persistence.room.Room
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.hong.mason.moverecoder.base.BaseDialogFragment
import com.hong.mason.moverecoder.R
import com.hong.mason.moverecoder.data.Category
import com.hong.mason.moverecoder.room.AppDatabase
import com.hong.mason.moverecoder.room.CategoryDao

class CategorySelectDialog : BaseDialogFragment() {
    private lateinit var containerContent: View
    private lateinit var viewDim: View
    private lateinit var recyclerView: RecyclerView
    private lateinit var buttonOk: View
    private var isClosing = false
    private var onSelectCategoryListener: OnSelectCategoryListener? = null
    private val adapter = CategoryAdapter()
    private lateinit var categories: List<Category>
    private lateinit var categoryDao: CategoryDao

    interface OnSelectCategoryListener {
        fun onSelectCategory(category: Category)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnSelectCategoryListener) {
            onSelectCategoryListener = context
        }
    }

    override fun initArguments(args: Bundle) {
        isCancelable = false
        val database = Room.databaseBuilder(context, AppDatabase::class.java, "AppDatabase")
                .allowMainThreadQueries()
                .build()
        categoryDao = database.categoryDao()
        categories = categoryDao.getAll()
    }

    override fun initView(view: View) {
        containerContent = view.findViewById(R.id.container_content)
        viewDim = view.findViewById(R.id.view_dim)
        recyclerView = view.findViewById(R.id.recycler_view)
        buttonOk = view.findViewById(R.id.button_ok)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.itemAnimator = null
        recyclerView.adapter = adapter
        adapter.setItems(categories, 0)

        buttonOk.setOnClickListener {
            val category = adapter.getSelectedCategory()
            onSelectCategoryListener?.onSelectCategory(category)
            close()
        }
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.dialog_category_select
    }

    override fun open() {
        viewDim.alpha = 0F
        viewDim.animate()
                .setDuration(500)
                .alpha(1F)
                .start()
        containerContent.measure(View.MeasureSpec.AT_MOST, View.MeasureSpec.UNSPECIFIED)
        containerContent.alpha = 0F
        containerContent.translationY = containerContent.measuredHeight.toFloat()
        containerContent.animate()
                .setDuration(500)
                .alpha(1F)
                .translationY(0F)
                .start()
    }

    override fun close() {
        if (isClosing) {
            return
        }
        isClosing = true
        val dimAnimator = viewDim.animate()
        dimAnimator.cancel()
        dimAnimator.setDuration(500)
                .alpha(0F)
                .start()
        containerContent.measure(View.MeasureSpec.AT_MOST, View.MeasureSpec.UNSPECIFIED)
        val contentAnimator = containerContent.animate()
        contentAnimator.cancel()
        contentAnimator.setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                dismiss()
            }
        })
        contentAnimator.setDuration(500)
                .alpha(0F)
                .translationY(containerContent.measuredHeight.toFloat())
                .start()
    }

    companion object {
        fun newInstance(): CategorySelectDialog {
            val args = Bundle()
            val dialog = CategorySelectDialog()
            dialog.arguments = args
            return dialog
        }
    }
}