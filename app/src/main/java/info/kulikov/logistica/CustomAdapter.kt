package info.kulikov.logistica

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView


class CustomAdapter() :
    BaseAdapter() {

    private lateinit var context: Context
    private lateinit var inflater: LayoutInflater
    private val dataSource: ArrayList<Somewhat> = arrayListOf()

    constructor(ctx: Context, items: ArrayList<Somewhat>?) : this() {
        context = ctx
        dataSource.clear()
        if (items != null) {
            dataSource.addAll(items)
        }
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(position: Int): Any? {
        return dataSource?.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val rowView = inflater.inflate(R.layout.list_item, parent, false)

        val image: ImageView = rowView.findViewById(R.id.ivImage)
        val item: Somewhat = dataSource[position]
        when (item.image) {
            "image_1.png" -> {
                image.setImageResource(R.drawable.image_1)
            }
            "image_2.png" -> {
                image.setImageResource(R.drawable.image_2)
            }
            "image_3.png" -> {
                image.setImageResource(R.drawable.image_3)
            }
        }

        val text: TextView = rowView.findViewById(R.id.tvText)
        text.text = item.image

        return rowView
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup) =
        getImageForPosition(position)

    private fun getImageForPosition(position: Int) = ImageView(context).apply {
        val item: Somewhat = dataSource[position]
        when (item.image) {
            "image_1.png" -> {
                setBackgroundResource(R.drawable.image_1)
            }
            "image_2.png" -> {
                setBackgroundResource(R.drawable.image_2)
            }
            "image_3.png" -> {
                setBackgroundResource(R.drawable.image_3)
            }
        }
        layoutParams = ViewGroup.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
    }

    fun setItems(it: List<Somewhat>?) {
        dataSource.clear()
        if (it != null) {
            dataSource.addAll(it)
        }
    }

}