package com.example.bookfinder_reviewer.UI

import AndroidBug5497Workaround
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.bookfinder_reviewer.R


class Fragment1 : Fragment() {
    private lateinit var rootView: View
//    var arrays = arrayOf<String>()
    var count: Int = 0

    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View {
        activity?.window?.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
        )
        Toast.makeText(activity, "test", Toast.LENGTH_SHORT).show()
        rootView= inflater.inflate(R.layout.fragment1, container, false)
//        return inflater.inflate(R.layout.fragment1, container, false)
        return rootView
//         alertDialog: AlertDialog = builder.create
//         dlg.setCancelable(false);
//         alertDialog.show();
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val mode = requireActivity().window.attributes.softInputMode
        Log.d("Input mode? ", mode.toString())
//        activity?.window?.setSoftInputMode(
//            WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN
//        )
//        Log.d("Input mode after? ",mode.toString())
//        val call= AndroidBug5497Workaround()
//        rootView.getViewTreeObserver()
//            .addOnGlobalLayoutListener(ViewTreeObserver.OnGlobalLayoutListener {
//                Log.d("layout change","fragm")
//                call.checkHeightDifference(rootView as ViewGroup)
//            })


        var arrays = getResources().getStringArray(R.array.my_array);
        val spinner: Spinner = view.findViewById(R.id.spinner)
        ArrayAdapter.createFromResource(
            this.requireActivity(), R.array.my_array, android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }
//        val adapterSpinner = AdapterSpinner(this,arrays)
//        spinner.adapter = adapter
//        spinner.adapter=adapterSpinner

        var ret: Boolean = false
        spinner.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(view: View?, event: MotionEvent?): Boolean {
                when (event!!.action) {
                    MotionEvent.ACTION_UP -> basicAlert(view!!, arrays)
                }
                return true
//              return v?.onTouchEvent(event) ?: true
            }
        })

        // Set an on item selected listener for spinner object
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.d("nothing selected", "no selection")
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>, view: View, position: Int, id: Long
            ) {
                Log.d("spinner clicked? :", count.toString())
                if (count > 0) {
                    count = 0
                    // Display the selected item text on text view
                    Toast.makeText(view.context, arrays[position], Toast.LENGTH_SHORT).show()
//                     (parent.getChildAt(0) as TextView).setTextColor(getResources().getColor(R.color.white))
//                     val externalFont = Typeface.createFromAsset(assets, "font/nanumsquarel.ttf")
//                     (v as TextView).setTypeface(externalFont)
//                     (view as TextView).setTypeface(null, Typeface.BOLD)
//                     Log.d("item selected", "selected")
                }
            }
        }
    }

    val positiveButtonClick = { dialog: DialogInterface, which: Int ->
        Toast.makeText(requireView().context, "ok", Toast.LENGTH_SHORT).show()
    }
    val negativeButtonClick = { dialog: DialogInterface, which: Int ->
        Toast.makeText(requireView().context, "close", Toast.LENGTH_SHORT).show()
    }

    fun basicAlert(v: View, arrays: Array<String>) {
        val Dialog: AlertDialog.Builder = AlertDialog.Builder(v.context)
        with(Dialog) {
            setTitle("Select Online bookstore")
            setItems(arrays) { dialog: DialogInterface, which ->
                Toast.makeText(
                    v.context, arrays[which] + " is clicked", Toast.LENGTH_SHORT
                ).show()
            }
            setPositiveButton(
                "ok",
                DialogInterface.OnClickListener(function = positiveButtonClick)
            )
            setNegativeButton("close", negativeButtonClick)
            show()
        }
    }

    override fun onResume() {
        super.onResume()
//        AndroidBug5497Workaround(this.requireActivity()).addListener()
        Toast.makeText(requireView().context, "frag Resume", Toast.LENGTH_SHORT).show()
    }

}
