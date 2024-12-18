package com.example.ap

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.ap.databinding.AddRecipeBinding



class AddItemFragment : Fragment() {

    private var _binding : AddRecipeBinding?=null
    private  val binding get() =_binding!!
    private var imageUri:Uri?=null


        //"אני מבקש מהמשתמש קובץ  אם יש קובץ, אני מציג אותו בתמונה  שומר את ההרשאה כדי שהאפליקציה תוכל להשתמש בו אחר כך.
    //---------------------------------------------------------------
    val pickImageLauncher: ActivityResultLauncher<Array<String>> =
        registerForActivityResult(ActivityResultContracts.OpenDocument()) { it: Uri? ->
            it?.let { uri ->
                binding.foodImagePreview.setImageURI(uri)
                requireActivity().contentResolver.takePersistableUriPermission(
                    uri, Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
                imageUri = uri
            }
        }
//-------------------------------------------------------------------------------------


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding=AddRecipeBinding.inflate(inflater,container,false)
        //מהוספת פריט בחזרה לרשימה ראשית
        binding.btnBack.setOnClickListener(){
            findNavController().navigate(R.id.action_addItemFragment_to_allItemsFragment)
        }

        binding.btnAddFood.setOnClickListener(){

            val item=Item(binding.foodNameInput.text.toString(),
                binding.authorNameInput.text.toString(),
                binding.foodDescriptionInput.text.toString(),
                binding.ingredientsDescriptionInput.text.toString(),
                imageUri)

            ItemManager.add(item)
            findNavController().navigate(R.id.action_addItemFragment_to_allItemsFragment)

        }

binding.btnPickImage.setOnClickListener{
    pickImageLauncher.launch(arrayOf("image/*"))
}
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }



}