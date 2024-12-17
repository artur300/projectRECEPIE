package com.example.ap

import android.icu.text.Transliterator.Position
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ap.databinding.RecipeCardBinding

class itemAdapter(val items : List<Item>) : RecyclerView.Adapter<itemAdapter.itemViewHolder>()
{
                                      //קישור לכרטיסיה שלי
    class itemViewHolder(private val binding:RecipeCardBinding) :RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Item){
                 //מזהה ב-class           מזהה ב-xml
            binding.foodName.text=item.foodName
            binding.authorName.text=item.authorName
            //תמונה

        }
    }

    //בשביל במכה ליצור control+ i
    //עבור התאים בהתחלה אחרי שיצרה כמה לא נקראית יותר
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=
        itemViewHolder(RecipeCardBinding.inflate(LayoutInflater.from(parent.context),parent,false))


    //זה מה שתמיד יעבוד
    override fun onBindViewHolder(holder: itemViewHolder, position: Int) =
        holder.bind(items[position])

    //זה במקרה ואין כלום
    override fun getItemCount()=items.size

}


