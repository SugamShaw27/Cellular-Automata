package com.example.cellularautomata.HomeFragments

import android.Manifest
import android.R
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.request.RequestOptions
import com.chaquo.python.PyObject
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import com.example.cellularautomata.databinding.FragmentExperimentDisplayBinding
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

class ExperimentDisplayFragment : Fragment() {

    private var experimentNumber: Int = 0
    private lateinit var experimentName: String
    private lateinit var binding: FragmentExperimentDisplayBinding
    private var globalGeneration: Int = 100
    private var globalSize: Int = 100
    private lateinit var selectedColour :String
    private var bitmap_picture1: Bitmap? = null
    private var bitmap_picture2: Bitmap? = null
    private var bitmap_picture3: Bitmap? = null
    private var bitmap_picture4: Bitmap? = null
    private var bitmap_picture5: Bitmap? = null
    private var bitmap_picture6: Bitmap? = null
    // Request code for permission request
    private val PERMISSION_REQUEST_CODE = 101

    // Permission launcher for requestPermission contract
    private val requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                // Permission granted, proceed to download image
                downloadImage()
            } else {
                Log.e("Permission", "WRITE_EXTERNAL_STORAGE permission denied")
                Toast.makeText(requireContext(), "Storage permission denied", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Python with AndroidPlatform
        if (!Python.isStarted()) {
            Python.start(AndroidPlatform(requireContext()))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExperimentDisplayBinding.inflate(inflater, container, false)

        experimentNumber = arguments?.getInt("experiment_number") ?: 0
        val value_name = arguments?.getString("experiment_name") ?: ""

        val regex = Regex("^\\d+\\.\\s*")
        experimentName = regex.replaceFirst(value_name, "")
        experimentNumber += 1
        binding.experimentNumber.text = "Experiment No.: $experimentNumber"
        binding.experimentName.text = experimentName+"\nUniform Cellular Automata: These are cellular automata where the initial state of the grid is set uniformly, often with all cells initialized to the same value (typically all zeros or all ones). This initial state is consistent across all simulations or runs of the automaton.\n" +
                "\n" +
                "No Random Initial State: In the context of cellular automata, this refers to scenarios where the initial configuration of the automaton's grid is deterministic and not subject to randomness. Instead of randomly assigning initial values to cells, a specific pattern or configuration is chosen deliberately, often to study the evolution of the automaton under controlled conditions or to observe specific behaviors or patterns that arise from the chosen initial state."

        if (experimentNumber == 1 || experimentNumber == 2) {
            binding.ruleNumber.visibility = View.VISIBLE
            binding.generationNumber.visibility = View.VISIBLE
            binding.sizeOfGeneration.visibility = View.VISIBLE
            binding.generatebtn.visibility = View.VISIBLE
            binding.generationNumber.hint = "Enter Generation number (1-$globalGeneration)"
            binding.sizeOfGeneration.hint = "Enter Column Size (1-$globalSize)"
            binding.TextInputLayout.visibility = View.VISIBLE
            binding.AutoCompleteTextView.visibility = View.VISIBLE

            val Colours = listOf( "binary","inferno","viridis", "plasma", "magma", "cividis", "Greys", "Blues", "Reds", "hot", "cool")

//            val adapter = ArrayAdapter(requireContext(), R.layout.simple_dropdown_item_1line, Colours)
            val adapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_dropdown_item, Colours)
            binding.AutoCompleteTextView.setAdapter(adapter)

            selectedColour="binary"

            binding.AutoCompleteTextView.setOnItemClickListener { parent, view, position, id ->
                selectedColour= parent.getItemAtPosition(position).toString()
            }

            binding.generatebtn.setOnClickListener {
                val ruleNumber = binding.ruleNumber.text.toString().toIntOrNull()
                val sizeOfGeneration = binding.sizeOfGeneration.text.toString().toIntOrNull()
                val generationNumber = binding.generationNumber.text.toString().toIntOrNull()
                val randomInitialState = experimentNumber == 2


                if (ruleNumber != null && sizeOfGeneration != null && generationNumber != null &&
                    ruleNumber in 0..255 && generationNumber in 1..globalGeneration && sizeOfGeneration in 1..globalSize
                ) {
                    generateAutomataExp1And2(ruleNumber, sizeOfGeneration, generationNumber, randomInitialState,selectedColour)
                } else {
                    Toast.makeText(requireContext(), "Invalid entry", Toast.LENGTH_SHORT).show()
                }
            }
        }

        if (experimentNumber == 3|| experimentNumber == 4) {
            binding.ruleNumber.visibility = View.VISIBLE
            binding.generationNumber.visibility = View.VISIBLE
            binding.sizeOfGeneration.visibility = View.VISIBLE
            binding.generatebtn.visibility = View.VISIBLE
            binding.generationNumber.hint = "Enter Generation number (1-$globalGeneration)"
            binding.sizeOfGeneration.hint = "Enter Column Size (1-$globalSize)"
            binding.TextInputLayout.visibility = View.VISIBLE
            binding.AutoCompleteTextView.visibility = View.VISIBLE

            val languages = listOf( "binary","inferno","viridis", "plasma", "magma", "cividis", "Greys", "Blues", "Reds", "hot", "cool")

            val adapter = ArrayAdapter(requireContext(), R.layout.simple_dropdown_item_1line, languages)
            binding.AutoCompleteTextView.setAdapter(adapter)

            selectedColour="binary"

            binding.AutoCompleteTextView.setOnItemClickListener { parent, view, position, id ->
                selectedColour= parent.getItemAtPosition(position).toString()
            }

            binding.generatebtn.setOnClickListener {
                val ruleNumber = binding.ruleNumber.text.toString().toIntOrNull()
                val sizeOfGeneration = binding.sizeOfGeneration.text.toString().toIntOrNull()
                val generationNumber = binding.generationNumber.text.toString().toIntOrNull()
                val randomInitialState = experimentNumber == 4


                if (ruleNumber != null && sizeOfGeneration != null && generationNumber != null &&
                    ruleNumber in 0..255 && generationNumber in 1..globalGeneration && sizeOfGeneration in 1..globalSize
                ) {
                    generateAutomataExp3and4(ruleNumber, sizeOfGeneration, generationNumber, randomInitialState,selectedColour)
                } else {
                    Toast.makeText(requireContext(), "Invalid entry", Toast.LENGTH_SHORT).show()
                }
            }
        }
        if (experimentNumber == 5|| experimentNumber == 6||experimentNumber==7) {
            binding.generationNumber.visibility = View.VISIBLE
            binding.sizeOfGeneration.visibility = View.VISIBLE
            binding.generatebtn.visibility = View.VISIBLE
            binding.generationNumber.hint = "Enter Generation number (1-$globalGeneration)"
            binding.sizeOfGeneration.hint = "Enter Column Size (1-$globalSize)"
            binding.TextInputLayout.visibility = View.VISIBLE
            binding.AutoCompleteTextView.visibility = View.VISIBLE

            val languages = listOf( "binary","inferno","viridis", "plasma", "magma", "cividis", "Greys", "Blues", "Reds", "hot", "cool")

            val adapter = ArrayAdapter(requireContext(), R.layout.simple_dropdown_item_1line, languages)
            binding.AutoCompleteTextView.setAdapter(adapter)

            selectedColour="binary"

            binding.AutoCompleteTextView.setOnItemClickListener { parent, view, position, id ->
                selectedColour= parent.getItemAtPosition(position).toString()
            }

            binding.generatebtn.setOnClickListener {
                val sizeOfGeneration = binding.sizeOfGeneration.text.toString().toIntOrNull()
                val generationNumber = binding.generationNumber.text.toString().toIntOrNull()
                val randomPosition = experimentNumber == 6


                if (generationNumber in 1..globalGeneration && sizeOfGeneration in 1..globalSize && sizeOfGeneration != null && generationNumber != null) {
                    generateAutomataExp5and6and7( sizeOfGeneration, generationNumber, randomPosition,selectedColour)
                } else {
                    Toast.makeText(requireContext(), "Invalid entry", Toast.LENGTH_SHORT).show()
                }
            }
        }

        return binding.root
    }

    private fun generateAutomataExp1And2(ruleNumber: Int, sizeOfGeneration: Int, generationNumber: Int, randomInitialState: Boolean, selectedColour: String) {
        val python = Python.getInstance()
        val pythonFile = python.getModule("cellular_automaton_generator")

//       generate_and_return_image(rule_number, size, generations, random_initial_state,title="",X_axis="",Y_axiz="", colormap='binary', cell_size=10)
        val result: PyObject = pythonFile.callAttr(
            "generate_and_return_image",
            ruleNumber,
            sizeOfGeneration,
            generationNumber,
            randomInitialState,
            experimentName,
            "Size",
            "Generation",
            selectedColour,
            10
        )

//        img_str_automaton_inital,img_str_automaton_final, img_str_concentric_circle ,img_str_cylindrical
//        image1: initial image of rule image
        val imgStrAutomatonInitalState = result.asList().get(0).toString()
        Log.e("value", imgStrAutomatonInitalState)
        val decodedStringAutomatonFunction: ByteArray = Base64.decode(imgStrAutomatonInitalState, Base64.DEFAULT)
        val bitmapAutomatonInitalState = BitmapFactory.decodeByteArray(decodedStringAutomatonFunction, 0, decodedStringAutomatonFunction.size)
        bitmap_picture1 = bitmapAutomatonInitalState
        binding.imageGenerateInitialState.visibility = View.VISIBLE
        binding.imageGenerateInitialState.setImageBitmap(bitmapAutomatonInitalState)

//        image 2:  final image of rule image
        val imgStrAutomatonFinalState = result.asList().get(1).toString()
        Log.e("value", imgStrAutomatonFinalState)
        val decodedStringAutomatonFinal: ByteArray = Base64.decode(imgStrAutomatonFinalState, Base64.DEFAULT)
        val bitmapAutomaton = BitmapFactory.decodeByteArray(decodedStringAutomatonFinal, 0, decodedStringAutomatonFinal.size)
        bitmap_picture2 = bitmapAutomaton
        binding.imageGeneratePicture.visibility = View.VISIBLE
        binding.imageGeneratePicture.setImageBitmap(bitmapAutomaton)

//        image 3:  Concentric Circle Graphs image
//        val imgStrConcentricCircle = result.asList().get(2).toString()
//        Log.e("value", imgStrConcentricCircle)
//        val decodedStringConcentricCircle: ByteArray = Base64.decode(imgStrConcentricCircle, Base64.DEFAULT)
//        val bitmapConcentricCircle = BitmapFactory.decodeByteArray(decodedStringConcentricCircle, 0, decodedStringConcentricCircle.size)
//        bitmap_picture2 = bitmapConcentricCircle
//        binding.imageConcentricCircle.visibility = View.VISIBLE
//        binding.imageConcentricCircle.setImageBitmap(bitmapConcentricCircle)

//        image 4:  Cylindrical Space-Time Diagram image
//        val imgStrCylindricalDiagram = result.asList()[3].toString()
//        Log.e("value", imgStrCylindricalDiagram)
//        val decodedStringCylindricalDiagram : ByteArray = Base64.decode(imgStrCylindricalDiagram, Base64.DEFAULT)
//        val bitmapCylindricalDiagram  = BitmapFactory.decodeByteArray(decodedStringCylindricalDiagram, 0, decodedStringCylindricalDiagram.size)
//        bitmap_picture2 = bitmapCylindricalDiagram
//        binding.imageCylindricalDiagram.visibility = View.VISIBLE
//        binding.imageCylindricalDiagram.setImageBitmap(bitmapCylindricalDiagram)
//        Glide.with(binding.imageCylindricalDiagram.context)
//            .load(bitmapCylindricalDiagram)
//            .apply(RequestOptions().centerCrop())
//            .into(binding.imageCylindricalDiagram)


        binding.downloadbtn.visibility = View.VISIBLE
        binding.downloadbtn.setOnClickListener {
            checkAndRequestPermission()
        }

    }

    private fun generateAutomataExp3and4(ruleNumber: Int, sizeOfGeneration: Int, generationNumber: Int, randomInitialState: Boolean, selectedColour: String) {
        val python = Python.getInstance()
        val pythonFile = python.getModule("cellular_automaton_generator2")

        val result: PyObject = pythonFile.callAttr(
            "generate_and_return_image",
            ruleNumber,
            sizeOfGeneration,
            generationNumber,
            randomInitialState,
            experimentName,
            "Size",
            "Generation",
            selectedColour,
            10
        )

        val imgStrAutomatonInitalState = result.asList().get(0).toString() //initial state
        val decodedStringAutomatonFunction: ByteArray = Base64.decode(imgStrAutomatonInitalState, Base64.DEFAULT)
        val bitmapAutomatonInitalState = BitmapFactory.decodeByteArray(decodedStringAutomatonFunction, 0, decodedStringAutomatonFunction.size)
        bitmap_picture1 = bitmapAutomatonInitalState
        binding.imageGenerateInitialState.visibility = View.VISIBLE
        binding.imageGenerateInitialState.setImageBitmap(bitmapAutomatonInitalState)


        val imgStrAutomaton = result.asList().get(1).toString() //final automata
        val decodedStringAutomaton: ByteArray = Base64.decode(imgStrAutomaton, Base64.DEFAULT)
        val bitmapAutomaton = BitmapFactory.decodeByteArray(decodedStringAutomaton, 0, decodedStringAutomaton.size)
        bitmap_picture2 = bitmapAutomaton
        binding.imageGeneratePicture.visibility = View.VISIBLE
        binding.imageGeneratePicture.setImageBitmap(bitmapAutomaton)


        val imgStrCombined = result.asList().get(2).toString() //matrix
        val decodedStringCombined: ByteArray = Base64.decode(imgStrCombined, Base64.DEFAULT)
        val bitmapCombined = BitmapFactory.decodeByteArray(decodedStringCombined, 0, decodedStringCombined.size)
        bitmap_picture3 = bitmapCombined
        binding.imageGenerateMatrix.visibility = View.VISIBLE
        binding.imageGenerateMatrix.setImageBitmap(bitmapCombined)


        binding.downloadbtn.visibility = View.VISIBLE
        binding.downloadbtn.setOnClickListener {
            checkAndRequestPermission()
        }
    }

    private fun generateAutomataExp5and6and7(sizeOfGeneration: Int, generationNumber: Int, randomPosition: Boolean, selectedColour: String) {
        val python = Python.getInstance()
        var pythonFile:PyObject
        if(experimentNumber==7)
        {
            pythonFile = python.getModule("cellular_automaton_generator4")
        }
        else{
            pythonFile = python.getModule("cellular_automaton_generator3")
        }


        val result: PyObject = pythonFile.callAttr(
            "generate_and_return_image",
            sizeOfGeneration,
            generationNumber,
            randomPosition,
            experimentName,
            "Size",
            "Generation",
            selectedColour,
            10
        )

        val imgStrAutomatonInitalState = result.asList().get(0).toString() //initial state
        val decodedStringAutomatonFunction: ByteArray = Base64.decode(imgStrAutomatonInitalState, Base64.DEFAULT)
        val bitmapAutomatonInitalState = BitmapFactory.decodeByteArray(decodedStringAutomatonFunction, 0, decodedStringAutomatonFunction.size)
        bitmap_picture1 = bitmapAutomatonInitalState
        binding.imageGenerateInitialState.visibility = View.VISIBLE
        binding.imageGenerateInitialState.setImageBitmap(bitmapAutomatonInitalState)


        val imgStrAutomaton = result.asList().get(1).toString() //final automata
        val decodedStringAutomaton: ByteArray = Base64.decode(imgStrAutomaton, Base64.DEFAULT)
        val bitmapAutomaton = BitmapFactory.decodeByteArray(decodedStringAutomaton, 0, decodedStringAutomaton.size)
        bitmap_picture2 = bitmapAutomaton
        binding.imageGeneratePicture.visibility = View.VISIBLE
        binding.imageGeneratePicture.setImageBitmap(bitmapAutomaton)


        val imgStrCombined = result.asList().get(2).toString() //matrix
        val decodedStringCombined: ByteArray = Base64.decode(imgStrCombined, Base64.DEFAULT)
        val bitmapCombined = BitmapFactory.decodeByteArray(decodedStringCombined, 0, decodedStringCombined.size)
        bitmap_picture3 = bitmapCombined
        binding.imageGenerateMatrix.visibility = View.VISIBLE
        binding.imageGenerateMatrix.setImageBitmap(bitmapCombined)


        val imgStrRuleVectorMatrix = result.asList().get(3).toString() //matrix
        val decodedStringRuleVectorMatrix: ByteArray = Base64.decode(imgStrRuleVectorMatrix, Base64.DEFAULT)
        val bitmapRuleVectorMatrix = BitmapFactory.decodeByteArray(decodedStringRuleVectorMatrix, 0, decodedStringRuleVectorMatrix.size)
        bitmap_picture4 = bitmapRuleVectorMatrix
        binding.imageGenerateRuleVector.visibility = View.VISIBLE
        binding.imageGenerateRuleVector.setImageBitmap(bitmapRuleVectorMatrix)

        binding.downloadbtn.visibility = View.VISIBLE
        binding.downloadbtn.setOnClickListener {
            checkAndRequestPermission()
        }
    }


    private fun checkAndRequestPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED -> {
                // Permission already granted, proceed to download
                downloadImage()
            }
            ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) -> {
                // Explain to the user why you need the permission and then request it
                requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
            else -> {
                // Request the permission directly
                requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }
    }

    private fun downloadImage() {
        // Get the external storage directory
        val picturesDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        // Create a custom directory
        val customDir = File(picturesDirectory, "MyCellularAutomaton")
        if (!customDir.exists()) {
            if (customDir.mkdirs()) {
                Toast.makeText(requireContext(), "Directory created", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Failed to create directory", Toast.LENGTH_SHORT).show()
                return
            }
        }

        // Function to save bitmap to custom directory
        fun saveBitmapToCustomDirectory(context: Context, bitmap: Bitmap, imageName: String): Boolean {
            val imageFile = File(customDir, "$imageName.jpg")
            var outputStream: FileOutputStream? = null
            return try {
                outputStream = FileOutputStream(imageFile)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                outputStream.flush()
                // Scan the file so it appears in the gallery
                MediaStore.Images.Media.insertImage(context.contentResolver, imageFile.absolutePath, imageFile.name, "Image of Cellular Automaton")
                true
            } catch (e: IOException) {
                e.printStackTrace()
                false
            } finally {
                outputStream?.close()
            }
        }

        // Save bitmap_picture1 to custom directory
        if (bitmap_picture1 != null) {
            if (saveBitmapToCustomDirectory(requireContext(), bitmap_picture1!!, "CellularAutomaton1")) {
                Toast.makeText(requireContext(), "Image1 saved to gallery", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Image save failed", Toast.LENGTH_SHORT).show()
            }
        }

        // Save bitmap_picture2 to custom directory
        if (bitmap_picture2 != null) {
            if (saveBitmapToCustomDirectory(requireContext(), bitmap_picture2!!, "CellularAutomaton2")) {
                Toast.makeText(requireContext(), "Image2 saved to gallery", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Image save failed", Toast.LENGTH_SHORT).show()
            }
        }
        if (bitmap_picture3 != null) {
            if (saveBitmapToCustomDirectory(requireContext(), bitmap_picture3!!, "CellularAutomaton3")) {
                Toast.makeText(requireContext(), "Image3 saved to gallery", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Image save failed", Toast.LENGTH_SHORT).show()
            }
        }
        if (bitmap_picture4 != null) {
            if (saveBitmapToCustomDirectory(requireContext(), bitmap_picture4!!, "CellularAutomaton4")) {
                Toast.makeText(requireContext(), "Image4 saved to gallery", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Image save failed", Toast.LENGTH_SHORT).show()
            }
        }
    }


//    private fun downloadImage() {
//        // Save image to the device's gallery
//        if(bitmap_picture1!=null)
//        {
//            val savedImageURL = MediaStore.Images.Media.insertImage(
//                requireContext().contentResolver,
//                bitmap_picture1,
//                "Cellular Automaton",
//                "Image of Cellular Automaton"
//            )
//            if (savedImageURL != null) {
//                Toast.makeText(requireContext(), "Image1 saved to gallery", Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(requireContext(), "Image save failed", Toast.LENGTH_SHORT).show()
//            }
//        }
//        if(bitmap_picture2!=null)
//        {
//            val savedImageURL2 = MediaStore.Images.Media.insertImage(
//                requireContext().contentResolver,
//                bitmap_picture2,
//                "Cellular Automaton",
//                "Image of Cellular Automaton"
//            )
//            if (savedImageURL2 != null) {
//                Toast.makeText(requireContext(), "Image2 saved to gallery", Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(requireContext(), "Image save failed", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
}
