package com.example.json

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.BaseExpandableListAdapter
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

class ExpanderListAdapter(
    private val context: Context,
    private val seasons: List<String>,
    private val zodiacSigns: List<List<String>>
) : BaseExpandableListAdapter() {
    public var selectedGroupPosition: Int = -1
     public var selectedChildPosition: Int = -1
    override fun getGroupCount(): Int {
        return seasons.size
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return zodiacSigns[groupPosition].size
    }

    override fun getGroup(groupPosition: Int): Any {
        return seasons[groupPosition]
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return zodiacSigns[groupPosition][childPosition]
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun hasStableIds(): Boolean {
        return false    }

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.list_group, parent, false)

        val groupText = view.findViewById<TextView>(R.id.groupHeader)
        groupText.text = seasons[groupPosition]

        return view
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.list_items, parent, false)

        val childText = view.findViewById<TextView>(R.id.childitem)
        childText.text = zodiacSigns[groupPosition][childPosition]

        if (groupPosition == selectedGroupPosition && childPosition == selectedChildPosition) {
            view.setBackgroundResource(R.drawable.button3)
        } else {
            view.setBackgroundResource(R.drawable.button)
        }

        view.setOnClickListener {
            selectedGroupPosition = groupPosition
            selectedChildPosition = childPosition
            notifyDataSetChanged()
            (context as? MainActivity)?.onZodiacSelected(zodiacSigns[groupPosition][childPosition])
        }

        return view
    }





    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }
}

