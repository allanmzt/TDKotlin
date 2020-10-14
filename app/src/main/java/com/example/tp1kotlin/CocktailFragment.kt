package com.example.tp1kotlin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CocktailFragment : Fragment() {
    lateinit var listeCocktails:ListeCocktails
    lateinit var btnback: Button
    lateinit var btnnext: Button
    lateinit var nom: TextView
    lateinit var alccol: TextView
    lateinit var linearLayout: LinearLayout
    lateinit var currentIngredients:ArrayList<TextView>
    var index:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun resetLayout(){
        if(currentIngredients.size==0)
            return;
        else{
            for(TextView in currentIngredients){
                linearLayout.removeView(TextView)
            }
            currentIngredients.clear()
        }
    }


    fun updateAffichage(){
        nom.text=listeCocktails.cocktails[index].name
        if(listeCocktails.cocktails[index].alcool)
            alccol.setTextColor(resources.getColor(R.color.green))
        else
            alccol.setTextColor(resources.getColor(R.color.red))
        resetLayout()
        for(String in listeCocktails.cocktails[index].ingredients){
            val tv=TextView(activity)
            tv.text=String
            linearLayout.addView(tv)
            currentIngredients.add(tv)
        }
    }

    fun initElements(view:View){
        btnback= view.findViewById(R.id.btn_back) as Button
        currentIngredients= ArrayList()
        btnnext= view.findViewById(R.id.btn_next) as Button
        nom= view.findViewById(R.id.txt_nomCocktail)
        alccol= view.findViewById(R.id.txt_alcool)
        linearLayout= view.findViewById(R.id.linearLayout_top)
        btnback.isEnabled=false
        listeCocktails= activity!!.intent.getParcelableExtra<ListeCocktails>("listeCocktails")!!
        btnback.setOnClickListener {
            if (index > 0) {
                index--
                if (!btnnext.isEnabled)
                    btnnext.isEnabled = true
                if (index == 0)
                    btnback.isEnabled = false
                updateAffichage()
            }
        }
        btnnext.setOnClickListener{
            if(index<listeCocktails.cocktails.size-1){
                index++
                if(!btnback.isEnabled)
                    btnback.isEnabled=true
                if(index==listeCocktails.cocktails.size-1)
                    btnnext.isEnabled=false
                updateAffichage()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        (activity as AppCompatActivity).supportActionBar?.title ="Cocktails"
        val root = inflater.inflate(R.layout.fragment_cocktail, container, false)
        initElements(root)
        updateAffichage()
        return root
    }

    companion object {
        @JvmStatic
        fun newInstance() = CocktailFragment()

    }
}